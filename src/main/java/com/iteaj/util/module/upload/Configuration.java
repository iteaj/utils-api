package com.iteaj.util.module.upload;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.Generator;
import com.iteaj.util.module.upload.Resource.MultipartFileResource;
import com.iteaj.util.module.upload.Resource.UploadResource;
import com.iteaj.util.module.upload.process.FileSizeFilter;
import com.iteaj.util.module.upload.process.FileTypeFilter;
import com.iteaj.util.module.upload.process.MvcResourceProcess;
import com.iteaj.util.module.upload.process.RootExistsFilter;
import com.iteaj.util.module.upload.transfer.LocationTarget;
import com.iteaj.util.module.upload.transfer.TransferTarget;
import com.iteaj.util.module.upload.utils.FileNameGenerator;
import com.iteaj.util.module.upload.utils.UploadUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Create Date By 2016/8/30
 *  文件上传配置
 * @author iteaj
 * @since 1.7
 */
public class Configuration implements ResourceProcessRegistry,FilePropertyFilterRegistry {

    public static String SUFFIX_SEPARATOR = "\\|";
    public static String PATH_SEPARATOR = "/";

    private File rootDir;
    /**指定上传根目录(eg: D:/upload/)*/
    protected String root;
    /**指定前端URL访问的前缀(eg: /image)*/
    protected String prefix;
    /**后缀类型(.jpg,.png,.doc默认用"|"隔开,默认为".*"表示全部)*/
    private String suffixTypes = ".*";
    /**指定文件上传类型(jpg,png,doc等)*/
    private List<String> patterns = new ArrayList<>();
    /**指定文件上传的默认过滤器*/
    private Set<Filter> filters = new HashSet<>();
    /**源处理器*/
    private Map<Class<? extends UploadResource>, ResourceProcess> processMap = new HashMap<>();

    /**文件名生成器(默认UUID生成器)*/
    private Generator generator = new FileNameGenerator();
    /**文件上传后的输出目标(默认服务器本地)*/
    private TransferTarget target = new LocationTarget();

    /**如果上传目录不存在是否创建*/
    private boolean isCreate = true;
    /**上传目录是否存在*/
    private boolean isExists;

    public Configuration(){

    }

    /**
     * 创建默认的上传配置对象
     * @param root  上传的根目录
     * @return
     * @throws IOException
     */
    public static Configuration instanceDefault(String root) throws IOException {
        Configuration configuration = new Configuration();
        configuration._initDefault();
        configuration._validatePath();
        return configuration;
    }

    public void init() throws IOException {
        _initDefault();
        _validatePath();
    }

    protected void _initDefault() {
        //初始化根路径(root),如果以/结尾,则去掉/
        if(CommonUtils.isNotBlank(root) && root.endsWith(PATH_SEPARATOR))
            root = UploadUtils.cutOutPath(root);

        //初始化suffixType到patterns
        if(CommonUtils.isNotBlank(suffixTypes))
            this.patterns = UploadUtils.stringToList(suffixTypes,SUFFIX_SEPARATOR);

        //注册源处理器
        registerResourceProcess();
        //注册文件属性过滤器
        registerFilePropertyFilter();
    }

    protected void _validatePath() throws IOException {
        //如果没有指定上传直接返回
        if(!CommonUtils.isNotBlank(root))
            throw new FileNotFoundException("没有指定上传目录root！");

        rootDir = new File(root);
        isExists = rootDir.exists();

        //根目录不存在并且允许创建,则创建rootDir
        if(!isExists && isCreate){
            rootDir.mkdir();
            isExists = true;
        }
    }

    protected void registerResourceProcess() {
        //注册由springMvc实现的MultipartFile文件源
        register(MultipartFileResource.class, new MvcResourceProcess(this));
    }

    protected void registerFilePropertyFilter(){
        //注册文件大小过滤器
        register(new FileSizeFilter());
        //注册文件类型过滤器
        register(new FileTypeFilter());
        //注册文件上传目录是否存在过滤器
        register(new RootExistsFilter());
    }

    @Override
    public void register(Class<? extends UploadResource> resource, ResourceProcess process) {
        processMap.put(resource, process);
    }

    @Override
    public ResourceProcess getValue(Class<? extends UploadResource> resource) {
        return processMap.get(resource);
    }

    @Override
    public void register(Filter filter) {
        filters.add(filter);
    }

    public Set<Filter> getFilters() {
        return filters;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<String> getPatterns() {
        return patterns;
    }

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public TransferTarget getTarget() {
        return target;
    }

    public void setTarget(TransferTarget target) {
        this.target = target;
    }

    public boolean isCreate() {
        return isCreate;
    }

    public void setCreate(boolean create) {
        isCreate = create;
    }

    public File getRootDir() {
        return rootDir;
    }

    public boolean isExists() {
        return isExists;
    }

    public String getSuffixTypes() {
        return suffixTypes;
    }

    public void setSuffixTypes(String suffixTypes) {
        this.suffixTypes = suffixTypes;
    }

    public void setFilters(Set<Filter> filters) {
        this.filters = filters;
    }

    public void setProcessMap(Map<Class<? extends UploadResource>, ResourceProcess> processMap) {
        this.processMap = processMap;
    }
}
