package com.tc.tsp.core.base;

import com.tc.tsp.core.context.InvocationContext;
import com.tc.tsp.core.context.InvocationContextImpl;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.List;

/**
 * Created by cai.tian on 2017/12/4.
 */
public class TspServiceStub {

    protected Class<? extends Object> ifaceClass;

    private String version;
    private String serviceName;
   //private int flag = OspFilterManagerClient.OSP_FLAG;

    public TspServiceStub(String version, Class<? extends Object> ifaceClass, String serviceName) {
        this.serviceName = serviceName;
        this.ifaceClass = ifaceClass;
        this.version = version;

        //OspFilterManagerClient.getInstance(flag).initStubFilter(ifaceClass);
    }

    public TspServiceStub(String version, Class<? extends Object> ifaceClass, String serviceName,int flag) {
        this.serviceName = serviceName;
        this.ifaceClass = ifaceClass;
        this.version = version;

    }

    /**
     * 接收返回
     */
    public final <T> void receiveBase(T result, TBaseWrapper<T, ?> helper) throws Exception {

    }

    /**
     * 准备上下文信息
     *
     * @param method 接口名
     */
    public void initInvocation(String method) throws Exception {
        InvocationContextImpl invocationContext = (InvocationContextImpl) InvocationContext.Factory.getInstance();
        InvocationContextImpl.InvocationInfoImpl lastInvocation = (InvocationContextImpl.InvocationInfoImpl) (invocationContext.getLastInvocation());

        lastInvocation.reset();

        lastInvocation.setServiceName(serviceName);
        lastInvocation.setMethod(method);
        lastInvocation.setCallerVersion(this.version);

        lastInvocation.setDomain(getDomain());

        if (lastInvocation.getCallerId() == null) {
            //lastInvocation.setCallerId(OspFilterManagerClient.getInstance().getAppName());
        }
    }

    /**
     * 数据异步发送
     */
    public <T> void asyncSendBase(T args, TBaseWrapper<T, ?> helper) throws Exception {
        try {
            sendBase(args, helper, false);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 数据发送
     */
    public <T> void sendBase(T args, TBaseWrapper<T, ?> helper) throws Exception {
        try {
            sendBase(args, helper, true);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 数据发送实现
     */
    protected <T> void sendBase(T args, TBaseWrapper<T, ?> helper, boolean needWait)
            throws Exception {




    }

    protected String getDomain() {
        return null;
    }

    public String _getVersion() {
        return version;
    }

    public String _getServiceName() {
        return serviceName;
    }
}
