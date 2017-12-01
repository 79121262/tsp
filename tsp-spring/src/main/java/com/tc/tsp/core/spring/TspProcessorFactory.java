package com.tc.tsp.core.spring;


import com.tc.tsp.core.annotation.processor;
import com.tc.tsp.core.annotation.service;
import com.tc.tsp.core.processor.TspProcessor;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created by cai.tian on 2017/11/30.
 */
public class TspProcessorFactory implements FactoryBean<TspProcessor<?>> {

    private Object serviceRef;
    private String refId;

    public TspProcessorFactory(Object serviceRef, String refId) {
        this.serviceRef = serviceRef;
        this.refId = refId;
    }

    @Override
    public TspProcessor<?> getObject() throws Exception {
        TspProcessor processor = parse2GetOspProcessor();
        return processor;
    }

    private TspProcessor parse2GetOspProcessor() throws Exception {
        Class<TspProcessor> processorClass = null;
        Class<? extends Object> serviceClass = serviceRef.getClass();
        Class<?> serviceInterface = findServiceInterface(serviceClass);
        if (serviceInterface == null) {
            throw new Exception( "please implement a service interface for " + refId);
        }
        processor processorAnn = serviceInterface.getAnnotation(processor.class);
        String classname = processorAnn.classname();
        processorClass = (Class<TspProcessor>) Class.forName(classname, true, serviceInterface.getClassLoader());
        Constructor<TspProcessor> constructor = processorClass.getConstructor(serviceInterface);
        TspProcessor processor = constructor.newInstance(serviceRef);
        processor.setiClass(serviceInterface);
        return processor;
    }

    private Class<?> findServiceInterface(Class<?> serviceClass) throws Exception {
        List<Class<?>> interfaces = ClassUtils.getAllInterfaces(serviceClass);
        Class<?> serviceInterface = null;
        for (Class<?> interfaceI : interfaces) {
            service serviceAnn = interfaceI.getAnnotation(service.class);
            processor processorAnn = interfaceI.getAnnotation(processor.class);
            if (serviceAnn != null && processorAnn != null) {
                if (serviceInterface != null) {
                    throw new Exception("Service name " + refId + " implement should only implement a service interface...");
                }
                serviceInterface = interfaceI;
            }
        }
        return serviceInterface;
    }

    @Override
    public Class<?> getObjectType() {
        return TspProcessor.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
