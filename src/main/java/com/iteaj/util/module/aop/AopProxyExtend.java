package com.iteaj.util.module.aop;

import com.iteaj.util.module.aop.factory.time.TimeWeaveActionFactory;
import com.iteaj.util.module.aop.record.TimeRecord;
import com.iteaj.util.module.aop.record.TimeWebSupport;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Create Date By 2016/10/27
 *  <p>spring aop 代理扩展</p><br>
 *      {@link AopProxyExtend}继承{@link StaticMethodMatcherPointcutAdvisor}而作为spring aop的代理通知器.
 *      需要在spring配置文件里面指定<aop:aspectj-autoproxy />来开启自动代理.
 *      @see #matches(Method, Class) 被匹配到的方法将会被代理
 * @author iteaj
 * @since jdk 1.7
 * @since spring 3.1
 */
public class AopProxyExtend extends StaticMethodMatcherPointcutAdvisor implements
        InitializingBean, BeanFactoryAware, ApplicationListener<ContextRefreshedEvent> {

    /**
     * 是否启用aop织入功能
     */
    private boolean isStart;
    private ListableBeanFactory beanFactory;
    private Logger logger = LoggerFactory.getLogger(getClass());

    protected static Map<ActionOutput, ActionOutput> _OUTPUTS = new HashMap<>();
    /**
     * 监控动作集合
     */
    private Collection<AbstractWeaveActionFactory> _weaveActionFactories;

    /**
     * 每个被织入的方法对应的织入动作
     * 由于每个被织入的方法都会有多个织入动作,所以用{@link HashMap}来保存它们之间的对应关系,
     * @see #matches(Method, Class) 在此方法初始化此属性
     * 请参考：{@link RoundAdviceAbstract}
     */
    protected Map<Method, Set<AbstractWeaveActionFactory>> methodActionFactoryMapping = new HashMap<>();

    /**
     * 不能对所有的方法进行织入,我们更希望只对我们关注的方法进行代理,spring对此提供了支持
     * @see #matches(Method, Class) 用来匹配我们关注的方法,从而来实现上面的需求
     * 详情请参考：{@link org.springframework.aop.MethodMatcher}
     * {@link org.springframework.aop.support.StaticMethodMatcher}
     * @param method
     * @param targetClass
     * @return
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        //不进行监控
        if(!isStart) return false;

        boolean isMatching = false;
        for (AbstractWeaveActionFactory candidate : _weaveActionFactories) {
            //候选监控动作没有开启
            if (!candidate.isStart()) continue;

            //候选监控动作不匹配此方法
            if (!candidate.isMonitoring(method, targetClass)) continue;

            //保存其一对多的对应关系 监控方法(一)->监控动作(多)
            if (methodActionFactoryMapping.containsKey(method)) {
                Set<AbstractWeaveActionFactory> handleAbstractSet = methodActionFactoryMapping.get(method);
                handleAbstractSet.add(candidate);
            } else {
                TreeSet<AbstractWeaveActionFactory> treeSet = new TreeSet<>();
                treeSet.add(candidate);
                methodActionFactoryMapping.put(method, treeSet);
            }

            isMatching = true;
        }
        return isMatching;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, AbstractWeaveActionFactory> beansOfType = this.beanFactory
                .getBeansOfType(AbstractWeaveActionFactory.class);

        Map<String, ActionOutput> outputMap = beanFactory
                .getBeansOfType(ActionOutput.class);


        //如果织入工厂为空或者输出为空则手动关闭这个功能
        if(MapUtils.isEmpty(beansOfType) || MapUtils.isEmpty(outputMap)) {
            this.isStart = false; return;
        }

        //必须要存在一个动作
        Map<AbstractWeaveActionFactory, AbstractWeaveActionFactory> distinct = new HashMap();
        for(AbstractWeaveActionFactory item : beansOfType.values()){
            if(item.isStart()) {
                distinct.put(item, item);
            }
        }

        if(MapUtils.isNotEmpty(distinct)) {
            this._weaveActionFactories = distinct.values();
        }

        //必须要存在一个输出
        for(ActionOutput item : outputMap.values()){
            _OUTPUTS.put(item, item);
        }

        //只要有一种没有存在则直接手动关闭
        if(CollectionUtils.isEmpty(_weaveActionFactories)
                || MapUtils.isEmpty(_OUTPUTS)){

            this.isStart = false; return;
        }

        /**
         * 如果没有指定通知{@link org.springframework.aop.framework.Advised}
         * 则初始化为默认{@link AopExtendRoundAdvice}
         */
        if(null == getAdvice()){
            setAdvice(new AopExtendRoundAdvice(_OUTPUTS.values(), methodActionFactoryMapping));
        }

        if(getAdvice() instanceof RoundAdviceAbstract) {
            RoundAdviceAbstract adviceAbstract = (RoundAdviceAbstract) getAdvice();
            if(null == adviceAbstract.getMethodActionMapping())
                adviceAbstract.setMethodActionMapping(methodActionFactoryMapping);
        } else {
            throw new BeanInitializationException("Advice 必须是 RoundAdviceAbstract的子类");
        }
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if(beanFactory instanceof ListableBeanFactory)
            this.beanFactory = (ListableBeanFactory) beanFactory;
        else
            throw new BeanCreationException("创建bean失败："+AopProxyExtend.class.getName());
    }

    /**
     * 返回所有的输出
     * @return
     */
    public static <T extends ActionOutput> ActionOutput getOutput(Class<T> clazz) {
        return _OUTPUTS.get(clazz);
    }

    public static <T extends ActionOutput> boolean contain(Class<T> clazz){
        return _OUTPUTS.containsKey(clazz);
    }

    /**
     * 用来验证项目是否是Web应用, 是否需要提供Web支持<br>
     *     输出记录里面包含Web层的一些信息, 比如RequestMapper 等
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        if(applicationContext instanceof WebApplicationContext) {
            AbstractWeaveActionFactory timeWeaveActionFactory = null;
            Iterator<AbstractWeaveActionFactory> iterator = _weaveActionFactories.iterator();
            while (iterator.hasNext()) {
                timeWeaveActionFactory = iterator.next();
                if(timeWeaveActionFactory instanceof TimeWeaveActionFactory)
                    break;
                else timeWeaveActionFactory = null;
            }

            if(null != timeWeaveActionFactory) {

                //默认的时间记录
                boolean timeRecord = TimeRecord.class == timeWeaveActionFactory.getRecord();

                if (timeRecord) {
                    logger.info("类别：AopExt - 动作：升级记录 - 描述：检测此应用为Web应用,时间记录将从：{} 升级到：{}"
                            , TimeRecord.class.getSimpleName(), TimeWebSupport.class.getSimpleName());

                    timeWeaveActionFactory.setRecord(TimeWebSupport.class);
                }
            }
        }
    }
}
