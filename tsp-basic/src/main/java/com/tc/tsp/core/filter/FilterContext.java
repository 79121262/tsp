package com.tc.tsp.core.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * 支持异步传递的Filter上下文.
 * Created by cai.tian on 2017/11/30.
 */
public class FilterContext {

    private Map<String, Object> filterContext = new HashMap<String, Object>();

    @SuppressWarnings("unchecked")
    public <T> T getContext(String key) {
        return (T) filterContext.get(key);
    }

    public <T> T getContext(String key, Class<T> valueType) {
        return valueType.cast(filterContext.get(key));
    }

    public void setContext(String key, Object value) {
        filterContext.put(key, value);
    }

    public void clearContext() {
        filterContext.clear();
    }

    @Override
    public String toString() {
        return "[filterContext=" + filterContext + "]";
    }

}