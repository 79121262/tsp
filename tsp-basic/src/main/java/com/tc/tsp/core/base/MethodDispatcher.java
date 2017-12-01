package com.tc.tsp.core.base;

import org.apache.thrift.protocol.TProtocol;

/**
 * Created by cai.tian on 2017/11/30.
 */
public abstract class MethodDispatcher <I, REQ, REQH extends TBaseWrapper<REQ, ?>, RESP, RESPH extends TBaseWrapper<RESP, ?>>  {

    private String methodName;
    private REQH reqHelper;
    private RESPH respHelper;

    public MethodDispatcher(String methodName, REQH reqHelper, RESPH respHelper) {
        this.methodName = methodName;
        this.reqHelper = reqHelper;
        this.respHelper = respHelper;
    }

    public void process(TProtocol iprot, TProtocol oprot, I iface) throws Exception {

    }

    @Deprecated
    protected abstract boolean isOneway();

    public abstract RESP getResult(I iface, REQ args) throws Throwable;

    public abstract REQ getEmptyArgsInstance();
}
