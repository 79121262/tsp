package com.tc.tsp.core.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by cai.tian on 2017/11/30.
 */
public class TspServiceNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("service", new TspServiceBeanDefinitionParser());
    }
}
