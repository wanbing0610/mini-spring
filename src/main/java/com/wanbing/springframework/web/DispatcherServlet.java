package com.wanbing.springframework.web;

import com.wanbing.springframework.core.ClassPathXmlResource;
import com.wanbing.springframework.core.Resource;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    private String contextConfigLocation;

//    private WebApplicationContext webApplicationContext;

    // 所有扫描的包名
    private List<String> packageNames = new ArrayList<>();
    // 所有扫描的controller全类限定名
    private List<String> controllerNames = new ArrayList<>();
    // 所有的controller实例
    private Map<String, Object> controllerObjs = new HashMap<>();

    private Map<String, Class<?>> controllerClasses = new HashMap<>();



    private List<String> urlMappingNames = new ArrayList<>();
    private Map<String,Object> mappingObjs = new HashMap<>();
    private Map<String,Method> mappingMethods = new HashMap<>();


    public DispatcherServlet() {
        super();
        System.out.println("DispatcherServlet 实例化");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("servlet init 初始化");

        // 获取SpringContext和WebContext
//        this.webApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        // 获取dispatcherServlet的配置信息
        this.contextConfigLocation = config.getInitParameter("contextConfigLocation");
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(this.contextConfigLocation);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);

        refresh();
    }

    protected void refresh(){
        initController();
        initMapping();
    }

    private void initMapping() {
        for (String controllerName : this.controllerNames) {
            Class<?> clazz = this.controllerClasses.get(controllerName);
            Object obj = this.controllerObjs.get(controllerName);
            Method[] methods = clazz.getDeclaredMethods();
            if(methods != null){
                for(Method method : methods){
                    boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                    if (isRequestMapping){
//                        String methodName = method.getName();
                        String urlmapping = method.getAnnotation(RequestMapping.class).value();
                        this.urlMappingNames.add(urlmapping);
                        this.mappingObjs.put(urlmapping, obj);
                        this.mappingMethods.put(urlmapping, method);
                    }
                }
            }
        }
    }

    private void initController() {
        this.controllerNames= scanPackages(packageNames);

        for(String controllerName : this.controllerNames ){
            Object obj = null;
            Class<?> clz = null;

            try {
                clz = Class.forName(controllerName);
                this.controllerClasses.put(controllerName, clz);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                obj = clz.newInstance();
                this.controllerObjs.put(controllerName, obj);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

    }
    private List<String> scanPackages(List<String> packages) {
        List<String> tempControllerNames = new ArrayList<>();
        for (String packageName : packages) {
            tempControllerNames.addAll(scanPackage(packageName));
        }
        return tempControllerNames;
    }

    /**
     * 扫描包下面的controller
     * @param packageName
     * @return
     */
    private List<String> scanPackage(String packageName){
        List<String> tempControllerNames = new ArrayList<>();
        URL url  =this.getClass().getResource("/"+packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if(file.isDirectory()){
                scanPackage(packageName+"."+file.getName());
            }else{
                String controllerName = packageName +"." +file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }
        return tempControllerNames;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String servletPath = request.getServletPath();
        System.out.println("url是" + servletPath);
        if( !this.urlMappingNames.contains(servletPath) ){
            return;
        }

        Object obj;
        Object objResult;

        Method method = this.mappingMethods.get(servletPath);
        obj = this.mappingObjs.get(servletPath);
        try {
            objResult = method.invoke(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        response.getWriter().append(objResult.toString());
    }
    
    
}
