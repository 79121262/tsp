package com.tc.container;

import com.tc.container.connection.ContainerServer;
import com.tc.tsp.core.processor.TspProcessor;
import com.tc.tsp.core.processor.TspUniteProcessor;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * Created by cai.tian on 2017/11/30.
 */
public class StartContainer {

    /**
     * 开启服务容器，启动服务
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void startUp(int port, ClassLoader containerClassLoader, ClassLoader appClassLoader)
            throws Exception {
        // create Spring FileSystemXmlApplicationContext instance
        final Object springApplicationContext = createSpringContext(appClassLoader);

        Method method = springApplicationContext.getClass().getMethod("getBeansOfType", Class.class);
        Map<String, TspProcessor> processorBeans = (Map<String, TspProcessor>) method.invoke(springApplicationContext,
                appClassLoader.loadClass("com.tc.tsp.core.processor.TspProcessor"));

        for (Map.Entry<String, TspProcessor> entry : processorBeans.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

        // 分析所有的processor并将它们的meta信息发布到zk配置中心
        TspUniteProcessor uniteProcessor = new TspUniteProcessor(processorBeans);

        ContainerServer containerServer = new ContainerServer();

        // Start the server finally
        containerServer.startServer(port, false, uniteProcessor);
    }


    private static Object createSpringContext(ClassLoader classloader)
            throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException,
            InvocationTargetException, IOException {

        List<String> xmlPaths = new ArrayList<String>();

        Enumeration<URL> resources = classloader.getResources("META-INF/service-conf/service.xml");
        if (resources.hasMoreElements()) {
            while (resources.hasMoreElements()) {
                URL nextElement = resources.nextElement();
                xmlPaths.add(nextElement.toString());
            }
        }

        Class<?> contextClass = classloader
                .loadClass("org.springframework.context.support.FileSystemXmlApplicationContext");
        Class<?>[] parameterTypes = new Class[] { String[].class };
        Constructor<?> constructor = contextClass.getConstructor(parameterTypes);
        final Object fileSystemXmlApplicationContext = constructor
                .newInstance(new Object[] { xmlPaths.toArray(new String[0]) });
        return fileSystemXmlApplicationContext;
    }
}
