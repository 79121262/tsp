package com.tc.tsp.core.processor;

import com.tc.tsp.core.base.MethodDispatcher;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;

import java.util.Map;

/**
 * Created by cai.tian on 2017/11/30.
 */
public class TspProcessor<I>  {

    private I iface;
    private Class<I> iClass;

    private Map<String, MethodDispatcher> functionProcessorMap;

    protected TspProcessor(I iface, Map<String, MethodDispatcher> functionProcessorMap) {
        this.iface = iface;
        this.functionProcessorMap = functionProcessorMap;
    }

    /**
     * 发布meta信息到服务注册中心, 启动zk监听该服务的配置动态变化
     */
    public void publishService() throws Exception {

    }

    /**
     * 准备好执行filterChain的一切，执行FilterChain
     */
    public boolean process(TProtocol in, TProtocol out) throws TException {

        return true;
    }

    public void setIface(I iface) {
        this.iface = iface;
    }

    public void setiClass(Class<I> iClass) {
        this.iClass = iClass;
    }

    public I getIface() {
        return iface;
    }

    public Class<I> getiClass() {
        return iClass;
    }
}
