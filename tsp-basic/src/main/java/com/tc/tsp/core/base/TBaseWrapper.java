package com.tc.tsp.core.base;

import org.apache.thrift.TException;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.protocol.TProtocol;

/**
 * 基础封装接口,定义结构体的读写接口
 * @author jon.liang
 *
 * @param <T> 结构体类
 * @param <F> 结构体属性描述枚举
 *
 */
public interface TBaseWrapper<T, F extends TFieldIdEnum> {

    void read(T bean, TProtocol iprot) throws TException;

    void write(T bean, TProtocol oprot) throws TException;

    F fieldForId(short fieldId);

    TFieldIdEnum[] getFields();

    //TFieldDescriptor[] getFieldDescriptors();

    boolean isSet(T bean, F field) throws TException;

    Object getFieldValue(T bean, F field);

    void setFieldValue(T bean, F field, Object value);

    void validate(T bean) throws TException;
}
