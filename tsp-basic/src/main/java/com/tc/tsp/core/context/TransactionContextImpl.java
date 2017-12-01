package com.tc.tsp.core.context;

import io.netty.util.concurrent.FastThreadLocal;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 处理服务请求的Context，保存在ThreadLocal中, 对应所监听端口接收到的数据.
 * Created by cai.tian on 2017/11/30.
 */
public class TransactionContextImpl implements TransactionContext {
    private static final long serialVersionUID = -6612034483265904469L;

    private static final Pattern ONE_PATTERN = Pattern.compile("1");

    private static FastThreadLocal<TransactionContext> threadLocal = new FastThreadLocal<TransactionContext>();

    private EncodeProtocol protocol = EncodeProtocol.Binary;
    private String serviceName;
    private String method;
    private String version;
    private int options;
    private int sequence = -1;
    private String callerId;
    private long sessionTid;
    private long callerTid;
    // private long uid;
    private String uip;
    private String forwardedFor;
    private String proxyIP;
    private InetAddress callerIP;
    private int callerPort;
    private String userSign;
    private long timeout;
    private long calleeTid;
    private String returnCode;
    private String returnMessage;
    private String errorGroup = "500";
    private String errorLevel;
    private String errorLocation;
    private boolean circuitBroken;
    private boolean retry;
    // private long receivedTime;
    // private int calleeTime1;
    // private int calleeTime2; // 服务业务处理时间
    // private int sendLength;
    // private int recvLength;
    // private int concurrentCount;

    private long startTime;

    // private Long mscTraceId;
    // private Long mscSpanId;

    private String remoteAddress;
    private String localAddress;

    private String domain;

    private Map<String, Object> contextData;
    //private ImmutableProtectedHashMap cookie = ImmutableProtectedHashMap.cookieProtectedHashMap();

    @Override
    public void setContextData(String key, Object value) {
        if (contextData == null) {
            contextData = new HashMap<String, Object>();
        }

        contextData.put(key, value);
    }

    @Override
    public <T> T getContextData(String key, Class<T> valueType) {
        if (contextData != null) {
            Object value = contextData.get(key);
            if (value != null) {
                return valueType.cast(value);
            }
        }

        return null;
    }

    public void reset() {
        protocol = EncodeProtocol.Binary;
        serviceName = null;
        method = null;
        version = null;
        options = 0;
        sequence = 0;
        callerId = null;
        sessionTid = 0;
        callerTid = 0;
        // uid = 0;
        uip = null;
        forwardedFor = null;
        proxyIP = null;
        callerIP = null;
        callerPort = 0;
        remoteAddress = null;
        domain = null;



        userSign = null;
        timeout = 0;
        calleeTid = 0;
        returnCode = "0";
        returnMessage = null;
        errorGroup = "500";
        errorLevel = null;
        errorLocation = null;
        circuitBroken = false;
        retry = false;
        // receivedTime = 0;
        // calleeTime1 = 0;
        // calleeTime2 = 0;
        // sendLength = 0;
        // recvLength = 0;
        // concurrentCount = 0;

        // mscTraceId = null;
        // mscSpanId = null;

        contextData = null;

    }

    @Override
    public EncodeProtocol getProtocol() {
        return this.protocol;
    }

    public TransactionContextImpl setProtocol(EncodeProtocol protocol) {
        this.protocol = protocol;
        return this;
    }

    @Override
    public String getServiceName() {
        return this.serviceName;
    }

    public TransactionContextImpl setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    public TransactionContextImpl setMethod(String method) {
        this.method = method;
        return this;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    public TransactionContextImpl setVersion(String version) {
        this.version = version;
        return this;
    }

    // ---------------------------------option操作 start---------------------------------------
    @Override
    public int getOptions() {
        return this.options;
    }

    /** interface中未定义，目的是不面向应用层开放 */
    public TransactionContextImpl setOptions(int options) {
        String binaryStr = Integer.toBinaryString(options);
        while (true) {
            int index = binaryStr.indexOf('1');

            if (index == -1) {
                break;
            }

            index = binaryStr.length() - index;

            this.setOption(true, index);
            binaryStr = ONE_PATTERN.matcher(binaryStr).replaceFirst("0");
        }

        return this;
    }

    /**
     * set option by index
     *
     * interface中未定义，目的是不面向应用层开放
     *
     * @param option option value.false mean 0, true mean 1
     * @param option index
     */
    public TransactionContextImpl setOption(boolean option, int index) {
        int initBit = 1;
        if (index > 32 || index < 1) {
            return this;
        }

        int temp = (initBit & 0xff) << (index - 1);

        if (option) {
            this.options |= temp;
        } else {
            this.options &= ~temp;
        }

        return this;
    }

    // ---------------------------------option操作 end---------------------------------------
    @Override
    public int getSequence() {
        return this.sequence;
    }

    public TransactionContextImpl setSequence(int sequence) {
        this.sequence = sequence;
        return this;
    }

    @Override
    public String getCallerId() {
        return this.callerId;
    }

    public TransactionContextImpl setCallerId(String callerId) {
        this.callerId = callerId;
        return this;
    }

    @Override
    public long getSessionTid() {
        return this.sessionTid;
    }

    public TransactionContextImpl setSessionTid(long sessionTid) {
        this.sessionTid = sessionTid;
        return this;
    }

    @Override
    public long getCallerTid() {
        return this.callerTid;
    }

    public TransactionContextImpl setCallerTid(long callerTid) {
        this.callerTid = callerTid;
        return this;
    }

    @Override
    @Deprecated
    public long getUid() {
        // return this.uid;
        return 0;
    }

    @Deprecated
    public TransactionContextImpl setUid(long uid) {
        // this.uid = uid;
        return this;
    }

    @Override
    public String getUip() {
        return this.uip;
    }

    public TransactionContextImpl setUip(String uip) {
        this.uip = uip;
        return this;
    }

    @Override
    public Map<String, String> getCookie() {
        return null;
    }

    @Override
    public TransactionContextImpl setCookie(Map<String, String> cookie) {

        return this;
    }

    @Override
    public TransactionContextImpl setCookie(String key, String value) {

        return this;
    }

    @Override
    public String getUserSign() {
        return this.userSign;
    }

    public TransactionContextImpl setUserSign(String userSign) {
        this.userSign = userSign;
        return this;
    }

    @Override
    public TransactionContextImpl setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    @Override
    public long getTimeout() {
        return this.timeout;
    }

    @Override
    public long getCalleeTid() {
        return this.calleeTid;
    }

    public TransactionContextImpl setCalleeTid(long calleeTid) {
        this.calleeTid = calleeTid;
        return this;
    }

    @Override
    public String getReturnCode() {
        return this.returnCode;
    }

    @Override
    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    @Override
    public String getReturnMessage() {
        return this.returnMessage;
    }

    @Override
    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    @Override
    public String getErrorGroup() {
        return this.errorGroup;
    }

    @Override
    public void setErrorGroup(String errorGroup) {
        this.errorGroup = errorGroup;
    }

    @Override
    public String getErrorLevel() {
        return this.errorLevel;
    }

    public TransactionContext setErrorLevel(String errorLevel) {
        this.errorLevel = errorLevel;
        return this;
    }

    @Override
    public String getErrorLocation() {
        return this.errorLocation;
    }

    public TransactionContext setErrorLocation(String errorLocation) {
        this.errorLocation = errorLocation;
        return this;
    }

    @Override
    public boolean isCircuitBroken() {
        return this.circuitBroken;
    }

    @Override
    public void setCircuitBroken(boolean circuitBroken) {
        this.circuitBroken = circuitBroken;
    }

    @Override
    public boolean isRetry() {
        return retry;
    }

    @Override
    public void setRetry(boolean retry) {
        this.retry = retry;
    }

    @Override
    @Deprecated
    public long getReceivedTime() {
        // return this.receivedTime;
        return 0;
    }

    @Deprecated
    public TransactionContextImpl setReceivedTime(long receivedTime) {
        // this.receivedTime = receivedTime;
        return this;
    }

    @Override
    @Deprecated
    public int getCalleeTime1() {
        // return this.calleeTime1;
        return 0;
    }

    @Deprecated
    public TransactionContextImpl setCalleeTime1(int calleeTime1) {
        // this.calleeTime1 = calleeTime1;
        return this;
    }

    /**
     * 服务业务处理时间
     */
    @Override
    @Deprecated
    public int getCalleeTime2() {
        // return this.calleeTime2;
        return 0;
    }

    /**
     * 服务业务处理时间
     */
    @Deprecated
    public TransactionContextImpl setCalleeTime2(int calleeTime2) {
        // this.calleeTime2 = calleeTime2;
        return this;
    }

    @Override
    @Deprecated
    public int getSendLength() {
        // return this.sendLength;
        return 0;
    }

    @Deprecated
    public TransactionContextImpl setSendLength(int sendLength) {
        // this.sendLength = sendLength;
        return this;
    }

    @Override
    @Deprecated
    public int getRecvLength() {
        // return this.recvLength;
        return 0;
    }

    @Deprecated
    public TransactionContextImpl setRecvLength(int recvLength) {
        // this.recvLength = recvLength;
        return this;
    }

    @Override
    @Deprecated
    public int getConcurrentCount() {
        // return this.concurrentCount;
        return 0;
    }

    @Deprecated
    public TransactionContextImpl setConcurrentCount(int concurrentCount) {
        // this.concurrentCount = concurrentCount;
        return this;
    }

    public static TransactionContext getInstance() {
        return threadLocal.get();
    }

    public static void setInstance(TransactionContext context) {
        threadLocal.set(context);
    }

    public static TransactionContext newInstance() {
        TransactionContext context = new TransactionContextImpl();
        threadLocal.set(context);
        return context;
    }

    public static TransactionContext setTransactionContext(TransactionContextImpl context) {
        threadLocal.set(context);
        return context;
    }

    @Override
    public long getStartTime() {
        return this.startTime;
    }

    public TransactionContextImpl setStartTime(long time) {
        this.startTime = time;
        return this;
    }

    @Override
    @Deprecated
    public Long getMscTraceId() {
        // return this.mscTraceId;
        return 0L;
    }

    @Override
    @Deprecated
    public Long getMscSpanId() {
        // return this.mscSpanId;
        return 0L;
    }

    @Deprecated
    public TransactionContextImpl setMscTraceId(Long mscTraceId) {
        // this.mscTraceId = mscTraceId;
        return this;
    }

    @Deprecated
    public TransactionContextImpl setMscSpanId(Long mscSpanId) {
        // this.mscSpanId = mscSpanId;
        return this;
    }

    public void copyFromContext(TransactionContext source) {
        this.reset();

        this.protocol = source.getProtocol();
        this.serviceName = source.getServiceName();
        this.method = source.getMethod();
        this.version = source.getVersion();
        this.options = source.getOptions();
        this.sequence = source.getSequence();
        this.callerId = source.getCallerId();
        this.sessionTid = source.getSessionTid();
        this.callerTid = source.getCallerTid();
        // this.uid = source.getUid();
        this.uip = source.getUip();
        this.forwardedFor = source.getForwardedFor();
        this.proxyIP = source.getProxyIP();
        this.callerIP = source.getCallerIP();
        this.callerPort = source.getCallerPort();

       /* if (OspCollections.isNotEmpty(source.getCookie())) {
            for (Map.Entry<String, String> entry : source.getCookie().entrySet()) {
                this.cookie.put(entry.getKey(), entry.getValue());
            }
        }*/

        this.userSign = source.getUserSign();
        this.timeout = source.getTimeout();
        this.calleeTid = source.getCalleeTid();
        this.returnCode = source.getReturnCode();
        this.returnMessage = source.getReturnMessage();
        this.errorGroup = source.getErrorGroup();
        this.errorLevel = source.getErrorLevel();
        this.errorLocation = source.getErrorLocation();
        this.circuitBroken = source.isCircuitBroken();
        this.retry = source.isRetry();
        // this.receivedTime = source.getReceivedTime();
        // this.calleeTime1 = source.getCalleeTime1();
        // this.calleeTime2 = source.getCalleeTime2();
        // this.sendLength = source.getSendLength();
        // this.recvLength = source.getRecvLength();
        // this.concurrentCount = source.getConcurrentCount();
        this.startTime = source.getStartTime();
        // this.mscTraceId = source.getMscTraceId();
        // this.mscSpanId = source.getMscSpanId();
        this.remoteAddress = source.getRemoteAddress();
        this.localAddress = source.getLocalAddress();
        this.domain = source.getDomain();
    }

    @Override
    public String getRemoteAddress() {
        return remoteAddress;
    }

    public TransactionContextImpl setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
        return this;
    }

    @Override
    public String getLocalAddress() {
        return localAddress;
    }

    public TransactionContextImpl setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
        return this;
    }

    @Override
    public String getForwardedFor() {
        return forwardedFor;
    }

    public TransactionContextImpl setForwardedFor(String forwardedFor) {
        this.forwardedFor = forwardedFor;
        return this;
    }

    @Override
    public String getProxyIP() {
        return proxyIP;
    }

    public TransactionContextImpl setProxyIP(String proxyIp) {
        this.proxyIP = proxyIp;
        return this;
    }

    @Override
    public InetAddress getCallerIP() {
        return callerIP;
    }

    public TransactionContextImpl setCallerIP(InetAddress callerIP) {
        this.callerIP = callerIP;
        return this;
    }

    @Override
    public int getCallerPort() {
        return callerPort;
    }

    public TransactionContextImpl setCallerPort(int callerPort) {
        this.callerPort = callerPort;
        return this;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    public TransactionContextImpl setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    @Override
    public String toString() {
        return "TransactionContextImpl [protocol=" + protocol + ", serviceName=" + serviceName + ", method=" + method
                + ", version=" + version + ", options=" + options + ", sequence=" + sequence + ", callerId=" + callerId
                + ", sessionTid=" + sessionTid + ", callerTid=" + callerTid + ", forwardedFor=" + forwardedFor
                + ", proxyIP=" + proxyIP + ", callerIP=" + callerIP + ", callerPort=" + callerPort + ", cookie="
                + ", userSign=" + userSign + ", timeout=" + timeout + ", calleeTid=" + calleeTid
                + ", returnCode=" + returnCode + ", returnMessage=" + returnMessage + ", errorGroup=" + errorGroup
                + ", errorLevel=" + errorLevel + ", errorLocation=" + errorLocation + ", circuitBroken=" + circuitBroken
                + ", retry=" + retry + ", startTime=" + startTime + ", remoteAddress=" + remoteAddress
                + ", localAddress=" + localAddress + ", domain=" + domain + ", contextData=" + contextData + ']';
    }

    /**
     * 获取option的第几位的值是否为1
     *
     * 注：这里是第几位（从1开始），而不是下标。
     */
    @Override
    public boolean getOption(int index) {
        int temp = this.options >>> (index - 1) & 1;
        return temp > 0;
    }



    public String getTransactionInfo() {
        return "service:" + serviceName + ",method:" + method + ",version:" + version;
    }
}
