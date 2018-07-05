package com.iteaj.util.core.task;

import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * create time: 2018/4/6
 *  超时任务管理器
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class TimeoutTaskManager {

    private Thread timerThread;
    private static Object lock = new Object();
    private static TimeoutTaskManager taskManager;
    private static SortedSet<TimeoutTask> timeoutTasks;
    private static Logger logger = LoggerFactory.getLogger("UtilsTimeoutManager");
    private static boolean debugEnabled = logger.isDebugEnabled();

    static {
        timeoutTasks = Collections.synchronizedSortedSet(new TreeSet<TimeoutTask>());
    }

    public static TimeoutTaskManager instance() {
        if(null != taskManager) {
            return taskManager;
        }
        synchronized (timeoutTasks) {
            if(null != taskManager) return taskManager;

            taskManager = new TimeoutTaskManager();
            taskManager.timerThread = new Thread(new TimerThread(),"Utils-Timer");
            taskManager.timerThread.start();

            return taskManager;
        }
    }

    public void addTask(TimeoutTask task) {
        if(null == task || task.getCreateTime() == 0)
            throw new UtilsException("任务未正常构建", UtilsType.TimeoutTask);

        if(timeoutTasks.size() == 0) {
            synchronized (lock) {
                timeoutTasks.add(task);
                if (timerThread.getState() == Thread.State.WAITING)
                    lock.notify();
            }
        } else {
            long currentTime = System.currentTimeMillis();

            //新任务已经超时则直接执行
            if(currentTime-task.getCreateTime() > task.getTimeout()){
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                /**
                 * 将新增任务和任务队列的第一个任务进行比较, 看谁离超时的时间更长<br>
                 *     如果新增任务离
                 *     超时时间比第一个任务更长,则直接放入有序任务队列进行排序,找到自己的队列位置
                 *     如果队列的第一个任务离超时时间比新增的更长,则新增任务, 并且唤醒定时器线程,修改等待时间为新增任务离超时的时间
                 */

                synchronized (lock) {
                    TimeoutTask first = timeoutTasks.first();
                    currentTime = System.currentTimeMillis(); //重新获取当前时间
                    long newTaskRemain = task.getTimeout() - (currentTime - task.getCreateTime()); //新任务离超时剩余时间
                    long firstTaskRemain = first.getTimeout() - (currentTime - first.getCreateTime()); //第一个任务离超时剩余时间

                    //如果第一个任务已经超时
                    Thread.State state = timerThread.getState();
                    if(firstTaskRemain <= 0 && (state == Thread.State.TIMED_WAITING
                            || state == Thread.State.WAITING)) {

                        lock.notify();
                    }

                    timeoutTasks.add(task);
                    /**
                     * 一下两种情况必须重新唤醒线程
                     * 1.第一个任务离超时的时间大于新增的任务 需要把新增的任务放进任务队列,然后唤醒定时线程,<br>
                     *     将等待时间修改为新增任务离超时的时间
                     * 2.如果定时器线程在做无任务的等待, 则新增一个任务之后必须先唤醒
                     */
                    if(state == Thread.State.WAITING || newTaskRemain < firstTaskRemain) {
                        lock.notify();
                        if(debugEnabled)
                            logger.debug("类别：定时任务 - 动作：唤醒定时线程 - 描述：收到新任务,新任务的超时时间小于第一个任务" +
                                    ",唤醒定时线程更改等待时间为新任务时间 - 新任务(ms)：{} - 第一个任务(ms)：{}", newTaskRemain, firstTaskRemain);
                    }
                }
            }
        }
    }

    static class TimerThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    if (timeoutTasks.size() == 0) {
                        try {
                            synchronized (lock) {
                                lock.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    TimeoutTask firstTask = timeoutTasks.first();

                    //当前时间
                    long currentTime = System.currentTimeMillis();
                    //流逝的时间
                    long elapseTime = currentTime - firstTask.getCreateTime();

                    //流逝的时间大于指定的超时时间, 说明第一个任务已经超时
                    boolean alreadyTimeout = elapseTime >= firstTask.getTimeout();
                    if (alreadyTimeout) {
                        if(debugEnabled)
                            logger.debug("类别：定时任务 - 动作：执行定时任务 - 描述：任务定时时间已到,开始执行任务 - 定时(ms)：{} - 误差(ms)：{}"
                                    , firstTask.getTimeout(), elapseTime-firstTask.getTimeout());
                        try {
                            firstTask.run();
                        } catch (Throwable e) {
                            logger.error("类别：定时管理 - 动作：执行任务失败 - 描述：{}", e.getMessage(), e);
                        }
                        //移除任务, 必须同步 即在同步的时候不能新增任务
                        synchronized (lock) {
                            Iterator<TimeoutTask> iterator = timeoutTasks.iterator();
                            while (iterator.hasNext()) {
                                TimeoutTask next = iterator.next();
                                if (next == firstTask) {
                                    iterator.remove();
                                    break;
                                }
                            }
                        }

                        continue;
                    } else { //第一个任务还未超时
                        synchronized (lock) {
                            //获取离超时时间还有多久, 并且线程等待
                            long timeoutRemain = firstTask.getTimeout() - elapseTime;
                            if(debugEnabled)
                                logger.debug("类别：定时任务 - 动作：定时线程等待 - 描述：线程等待直到下一个任务超时 - 等待时间(ms)：{}", timeoutRemain);
                            lock.wait(timeoutRemain);
                        }
                    }
                } catch (InterruptedException e) {
                    logger.error("超时管理器中断异常："+e.getMessage(), e);
                } catch (Exception e) {
                    logger.error("超时管理器未知异常："+e.getMessage(), e);
                }
            }
        }
    }
}
