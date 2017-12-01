package com.tc.tsp.core.context;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Map;

/**
 * Created by cai.tian on 2017/11/30.
 */
public interface TransactionContext extends Serializable {


    class Factory {
        public static TransactionContext getInstance() {
            TransactionContext instance = TransactionContextImpl.getInstance();
            if (instance == null) {
                instance = TransactionContextImpl.newInstance();
            }
            return instance;
        }
    }

    void setContextData(String key, Object value);

    <T> T getContextData(String key, Class<T> valueType);

    EncodeProtocol getProtocol();

    String getServiceName();

    String getMethod();

    String getVersion();

    /** 获取option整个值(int型) */
    int getOptions();

    /** 判断option中二进制第index位是否为1 */
    boolean getOption(int index);

    int getSequence();

    String getCallerId();

    long getSessionTid();

    long getCallerTid();

    long getCalleeTid();

    @Deprecated
    long getUid();

    long getStartTime();

    @Deprecated
    int getConcurrentCount();

    String getUip();

    String getForwardedFor();

    String getProxyIP();

    InetAddress getCallerIP();

    int getCallerPort();

    Map<String, String> getCookie();

    TransactionContext setCookie(Map<String, String> cookie);

    TransactionContext setCookie(String key, String value);

    String getUserSign();

    TransactionContext setTimeout(long timeout);

    long getTimeout();

    String getReturnCode();

    void setReturnCode(String returnCode);

    String getReturnMessage();

    void setReturnMessage(String returnMessage);

    String getErrorGroup();

    void setErrorGroup(String errorGroup);

    String getErrorLevel();

    String getErrorLocation();

    boolean isCircuitBroken();

    void setCircuitBroken(boolean circuitBroken);

    /** 是否重试 */
    boolean isRetry();

    void setRetry(boolean retry);

    @Deprecated
    long getReceivedTime();

    @Deprecated
    int getCalleeTime1();

    @Deprecated
    int getCalleeTime2();

    @Deprecated
    int getSendLength();

    @Deprecated
    int getRecvLength();

    @Deprecated
    Long getMscTraceId();

    @Deprecated
    Long getMscSpanId();

    String getRemoteAddress();

    String getLocalAddress();

    String getDomain();
}
