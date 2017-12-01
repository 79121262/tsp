package com.tc.tsp.core.spring;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by cai.tian on 2017/11/30.
 */
public class TspServiceBeanDefinitionParser implements BeanDefinitionParser {
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        String id = element.getAttribute("id");
        String refId = element.getAttribute("ref");
        if (StringUtils.isEmpty(id)) {
            id = refId + "_OspServiceProcessor$";
        }

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(TspProcessorFactory.class);
        builder.addConstructorArgReference(refId);
        builder.addConstructorArgValue(refId);

        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);

        return beanDefinition;
    }
}
