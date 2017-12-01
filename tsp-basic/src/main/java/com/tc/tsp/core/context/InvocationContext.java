package com.tc.tsp.core.context;

import com.tc.tsp.core.enums.LoadBalanceStratage;
import com.tc.tsp.core.filter.FilterContext;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Map;

/**
 * 发送服务请求的Context
 * Created by cai.tian on 2017/11/30.
 */
public interface InvocationContext extends Serializable {

    class Factory {
        public static InvocationContext getInstance() {
            InvocationContext instance = InvocationContextImpl.getInstance();
            if (instance == null) {
                instance = InvocationContextImpl.newInstance();
            }
            return instance;
        }
    }

    /**
     * 封装一次调用的基本上下文信息
     */
    interface InvocationInfo extends Serializable {

        EncodeProtocol getProtocol();

        String getServiceName();

        String getMethod();

        String getCallerVersion();

        /** 获取option整个值(int型) */
        int getOptions();

        /** 判断option中二进制第index位是否为1 */
        boolean getOption(int index);

        int getSequence();

        String getCallerId();

        long getSessionTid();

        long getCallerTid();

        long getCalleeTid();

        long getUid();

        String getUip();

        String getForwardedFor();

        String getProxyIP();

        Map<String, String> getCookie();

        String getUserSign();

        @Deprecated
        LoadBalanceStratage getLoadBalance();

        @Deprecated
        String getHashKey();

        int getFailTryCount();

        long getTimeout();

        InetAddress getCallerIp();

        String getCalleeIP();

        int getCalleePort();

        String getCalleeVersion();

        String getReturnCode();

        String getReturnMessage();

        String getErrorGroup();

        String getErrorLevel();

        String getErrorLocation();

        boolean isCircuitBroken();

        /** 是否重试 */
        boolean isRetry();

        @Deprecated
        int getCalleeTime1();

        @Deprecated
        int getCalleeTime2();

        @Deprecated
        long getSendLength();

        /**
         * 获取接收的数据包长度
         */
        long getRecvLength();

        @Deprecated
        int getCallerTime();

        String getLocalAddress();

        String getRemoteAddress();

        /**
         * 清除本次调用信息，从InvocationContext中获取缺省信息
         */
        void reset();

        @Deprecated
        Long getMscTraceId();

        @Deprecated
        Long getMscSpanId();

        @Deprecated
        long getCallerTimeMillis();

        String getDomain();

        FilterContext getFilterContext();

        void setFilterContext(FilterContext filterContext);

        void copyFrom(InvocationContextImpl.InvocationInfoImpl target);
    }

    EncodeProtocol getProtocol();

    InvocationContext setProtocol(EncodeProtocol protocol);

    /** 获取option整个值int型 */
    int getOptions();

    /** 判断option中二进制第index位是否为1 */
    boolean getOption(int index);

    String getCallerId();

    InvocationContext setCallerId(String callerId);

    long getSessionTid();

    void reset();

    long getCallerTid();

    @Deprecated
    long getUid();

    @Deprecated
    InvocationContext setUid(long uid);

    String getUip();

    InvocationContext setUip(String uip);

    Map<String, String> getCookie();

    InvocationContext setCookie(Map<String, String> cookie);

    InvocationContext setCookie(String key, String value);

    String getUserSign();

    InvocationContext setUserSign(String sign);

    @Deprecated
    LoadBalanceStratage getLoadBalance();

    @Deprecated
    InvocationContext setLoadBalance(LoadBalanceStratage loadbalance);

    @Deprecated
    String getHashKey();

    @Deprecated
    InvocationContext setHashKey(String hashKey);

    int getFailTryCount();

    boolean isSetFailTryCount();

    InvocationContext setFailTryCount(int count);

    long getTimeout();

    InvocationContext setTimeout(long timeout);

    /** 被调用方IP */
    String getCalleeIP();

    /**
     * 设置服务的给定IP地址，如果未设置，则通过注册中心获取
     */
    InvocationContext setCalleeIP(String ip);

    /** 被调用方Port */
    int getCalleePort();

    /**
     * 设置服务的给定端口，如果未设置，则通过注册中心获取
     */
    InvocationContext setCalleePort(int port);

    /**
     * 获取最后一次调用的信息，在每次Stub调用前，会对上次的InvocationInfo进行reset操作。
     */
    InvocationInfo getLastInvocation();

    long getCalleeTid();

    String getDomain();

    /** 设置SSL状态（监听的端口是否为SSL安全通道） */
    InvocationContext setSSLFlag(boolean sslFlag);

    /** 获取SSL状态（监听的端口是否为SSL安全通道） */
    boolean isSSLFlag();

}