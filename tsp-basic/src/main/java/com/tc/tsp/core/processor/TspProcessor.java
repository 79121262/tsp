package com.tc.tsp.core.processor;

import org.apache.thrift.protocol.TProtocol;

/**
 * Created by cai.tian on 2017/11/30.
 */
public class TspProcessor<I>  {

    private I iface;
    private Class<I> iClass;

    /**
     * 发布meta信息到服务注册中心, 启动zk监听该服务的配置动态变化
     */
    public void publishService() throws Exception {

    }

    /**
     * 准备好执行filterChain的一切，执行FilterChain
     */
    public boolean process(TProtocol in, TProtocol out) throws Exception {

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
