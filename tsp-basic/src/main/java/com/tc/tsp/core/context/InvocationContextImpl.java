package com.tc.tsp.core.context;

import com.tc.tsp.core.enums.LoadBalanceStratage;
import com.tc.tsp.core.filter.FilterContext;
import io.netty.buffer.ByteBuf;
import io.netty.util.concurrent.FastThreadLocal;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送服务请求的Context，保存在ThreadLocal中, 对应待发送到某个server的数据
 *
 * Created by cai.tian on 2017/11/30.
 */
public final class InvocationContextImpl implements InvocationContext {

    private static final long serialVersionUID = 5998622460591158259L;

    private static FastThreadLocal<InvocationContext> threadLocal = new FastThreadLocal<InvocationContext>();

    private EncodeProtocol protocol = EncodeProtocol.Binary;

    private int options;

    private String callerId;

    private long sessionTid;

    private long callerTid;

    private int sequence;

    // private long uid;

    private String uip;

    private String forwardedFor;

    private String proxyIP;

    private InetAddress callerIp;

    private Map<String, String> cookie = new HashMap<String, String>();

    private String userSign;

    private int failTryCount;

    private boolean failTryCountIsSeted;

    private long timeout;

    private String calleeIP;

    private int calleePort;

    private long calleeTid;

    private String domain;

    private boolean sslFlag; // 是否为SSL安全通道

    private final InvocationInfo lastInvocation = new InvocationInfoImpl();

    @Override
    public EncodeProtocol getProtocol() {
        return this.protocol;
    }

    @Override
    public InvocationContext setProtocol(EncodeProtocol protocol) {
        this.protocol = protocol;
        return this;
    }

    @Override
    public int getOptions() {
        return this.options;
    }

    /** interface中未定义，目的是不面向应用层开放 */
    public InvocationContext setOptions(int options) {
        this.options |= options;

        return this;
    }

    @Override
    public String getCallerId() {
        return this.callerId;
    }

    @Override
    public InvocationContext setCallerId(String callerId) {
        this.callerId = callerId;
        return this;
    }

    @Override
    public long getSessionTid() {
        return this.sessionTid;
    }

    public InvocationContext setSessionTid(long sessionTid) {
        this.sessionTid = sessionTid;
        return this;
    }

    @Override
    public long getCallerTid() {
        return this.callerTid;
    }

    public InvocationContext setCallerTid(long tid) {
        this.callerTid = tid;
        return this;
    }

    @Override
    @Deprecated
    public long getUid() {
        // return uid;
        return 0;
    }

    @Override
    @Deprecated
    public InvocationContext setUid(long uid) {
        // this.uid = uid;
        return this;
    }

    @Override
    public String getUip() {
        return this.uip;
    }

    @Override
    public InvocationContext setUip(String uip) {
        this.uip = uip;
        return this;
    }

    @Override
    public Map<String, String> getCookie() {
        return this.cookie;
    }

    @Override
    public InvocationContext setCookie(Map<String, String> cookie) {
        this.cookie = cookie;
        return this;
    }

    @Override
    public InvocationContext setCookie(String key, String value) {
        if (this.cookie == null) {
            this.cookie = new HashMap<String, String>();
        }
        this.cookie.put(key, value);
        return this;
    }

    @Override
    public String getUserSign() {
        return this.userSign;
    }

    @Override
    public InvocationContext setUserSign(String sign) {
        this.userSign = sign;
        return this;
    }

    @Override
    @Deprecated
    public LoadBalanceStratage getLoadBalance() {
        return LoadBalanceStratage.RandomRobin;
    }

    @Override
    @Deprecated
    public InvocationContext setLoadBalance(LoadBalanceStratage loadbalance) {
        return this;
    }

    @Override
    @Deprecated
    public String getHashKey() {
        // return hashKey;
        return null;
    }

    @Override
    @Deprecated
    public InvocationContext setHashKey(String hashKey) {
        return this;
    }

    @Override
    public InvocationContext setFailTryCount(int count) {
        this.failTryCountIsSeted = true;
        this.failTryCount = Math.abs(count);
        return this;
    }

    @Override
    public int getFailTryCount() {
        return this.failTryCount;
    }

    @Override
    public long getTimeout() {
        return this.timeout;
    }

    @Override
    public InvocationContext setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    @Override
    public String getCalleeIP() {
        return this.calleeIP;
    }

    @Override
    public InvocationContext setCalleeIP(String calleeIP) {
        this.calleeIP = calleeIP;
        return this;
    }

    @Override
    public int getCalleePort() {
        return this.calleePort;
    }

    @Override
    public InvocationContext setCalleePort(int port) {
        this.calleePort = port;
        return this;
    }

    // 从threadLocal中获取InvocationContext
    public static InvocationContext getInstance() {
        return threadLocal.get();
    }

    public static void setInstance(InvocationContext context) {
        threadLocal.set(context);
    }

    public static InvocationContext newInstance() {
        InvocationContext context = new InvocationContextImpl();
        threadLocal.set(context);
        return context;
    }

    @Override
    public InvocationInfo getLastInvocation() {
        return lastInvocation;
    }

    public int getSequence() {
        return sequence;
    }

    public InvocationContext setSequence(int sequence) {
        this.sequence = sequence;
        return this;
    }

    @Override
    public boolean isSetFailTryCount() {
        return failTryCountIsSeted;
    }

    @Override
    public long getCalleeTid() {
        return calleeTid;
    }

    public InvocationContext setCalleeTid(long calleeTid) {
        this.calleeTid = calleeTid;
        return this;
    }

    @Override
    public void reset() {
        protocol = EncodeProtocol.Binary;
        options = 0;
        callerId = null;
        sessionTid = 0;
        callerTid = 0;
        sequence = 0;
        // uid = 0;
        uip = null;
        forwardedFor = null;
        proxyIP = null;
        callerIp = null;
        cookie.clear();
        userSign = null;
        failTryCount = 0;
        failTryCountIsSeted = false;
        timeout = 0;
        calleeIP = null;
        calleePort = 0;
        calleeTid = 0;
        domain = null;
    }

    /**
     * 该方法只会在Proxy被调用，因为Proxy既用到TransactionContext，也用到InvocationContext。
     */
    public InvocationContextImpl copyFromTransactionContext(TransactionContext transactionContext) {
        reset();

        protocol = transactionContext.getProtocol();
        options = transactionContext.getOptions();
        callerId = transactionContext.getCallerId();
        sessionTid = transactionContext.getSessionTid();
        callerTid = transactionContext.getCallerTid();
        // uid = transactionContext.getUid();
        uip = transactionContext.getUip();
        forwardedFor = transactionContext.getForwardedFor();
        proxyIP = transactionContext.getProxyIP();
        cookie.putAll(transactionContext.getCookie()); // Fix, not set, but put.
        userSign = transactionContext.getUserSign();
        calleeTid = transactionContext.getCalleeTid();
        callerIp = transactionContext.getCallerIP();
        domain = transactionContext.getDomain();

        return this;
    }

    public class InvocationInfoImpl implements InvocationInfo {
        private static final long serialVersionUID = -8638889433427719717L;
        private EncodeProtocol protocol;
        private String serviceName;
        private String method;
        private String callerVersion;
        private int options;
        private int sequence;
        private String callerId;
        private long sessionTid;
        private long callerTid;
        private long calleeTid;
        // private long uid;
        private String uip;
        private String forwardedFor;
        private String proxyIP;
        private Map<String, String> cookie = new HashMap<String, String>();
        private String userSign;
        private int failTryCount;
        private long timeout;

        private InetAddress callerIp;

        private String calleeIp; // 不能改为InetAddress，有兼容问题
        private int calleePort;
        private String calleeVersion;
        private String returnCode;
        private String returnMessage;
        private String errorGroup = "500";
        private String errorLevel;
        private String errorLocation;
        private boolean circuitBroken;
        private boolean retry;
        // private int calleeTime1;
        // private int calleeTime2;
        private long recvLength;
        private ByteBuf responseByteBuf;
        private String localAddress;
        private String remoteAddress;
        private int callerPort;

        private String domain;

        private FilterContext filterContext;

        /**
         * reset values from invocationContext
         */
        @Override
        public void reset() {
            this.protocol = InvocationContextImpl.this.protocol;
            this.options = InvocationContextImpl.this.options;
            this.sequence = InvocationContextImpl.this.sequence;
            this.callerId = InvocationContextImpl.this.callerId;
            this.sessionTid = InvocationContextImpl.this.sessionTid;

            this.callerTid = InvocationContextImpl.this.callerTid;
            // this.uid = InvocationContextImpl.this.uid;
            this.uip = InvocationContextImpl.this.uip;
            this.forwardedFor = InvocationContextImpl.this.forwardedFor;
            this.proxyIP = InvocationContextImpl.this.proxyIP;

            this.cookie = InvocationContextImpl.this.cookie;
            this.userSign = InvocationContextImpl.this.userSign;
            this.failTryCount = InvocationContextImpl.this.failTryCount;
            this.timeout = InvocationContextImpl.this.timeout;
            this.callerIp = InvocationContextImpl.this.callerIp != null ? InvocationContextImpl.this.callerIp
                    : null;

            this.calleeIp = InvocationContextImpl.this.calleeIP;
            this.calleeTid = InvocationContextImpl.this.calleeTid;
            this.calleePort = InvocationContextImpl.this.calleePort;
            this.calleeVersion = null;
            this.returnCode = "";
            this.returnMessage = null;
            this.errorGroup = "500";
            this.errorLevel = null;
            this.errorLocation = null;
            this.circuitBroken = false;
            this.retry = false;
            // this.calleeTime1 = 0;
            // this.calleeTime2 = 0;
            this.recvLength = 0;
            this.callerPort = 0;
            this.domain = InvocationContextImpl.this.domain;
            this.filterContext = null;
            this.responseByteBuf = null;
        }

        @Override
        public void copyFrom(InvocationInfoImpl target) {
            this.protocol = target.protocol;
            this.options = target.options;
            this.sequence = target.sequence;
            this.callerId = target.callerId;
            this.sessionTid = target.sessionTid;

            this.callerTid = target.callerTid;
            // this.uid = target.uid;
            this.uip = target.uip;
            this.forwardedFor = target.forwardedFor;
            this.proxyIP = target.proxyIP;

            this.cookie = target.cookie;
            this.userSign = target.userSign;
            this.failTryCount = target.failTryCount;
            this.timeout = target.timeout;
            this.callerIp = target.callerIp != null ? target.callerIp : null;

            this.calleeIp = target.calleeIp;
            this.calleeTid = target.calleeTid;
            this.calleePort = target.calleePort;
            this.calleeVersion = null;
            this.returnCode = "";
            this.returnMessage = null;
            this.errorGroup = "500";
            this.errorLevel = null;
            this.errorLocation = null;
            this.circuitBroken = false;
            this.retry = false;
            // this.calleeTime1 = 0;
            // this.calleeTime2 = 0;
            this.recvLength = 0;
            this.callerPort = 0;
            this.domain = InvocationContextImpl.this.domain;
            this.filterContext = null;
        }

        @Override
        public FilterContext getFilterContext() {
            return filterContext;
        }

        @Override
        public void setFilterContext(FilterContext filterContext) {
            this.filterContext = filterContext;
        }

        @Deprecated
        public void setLoadbalance(LoadBalanceStratage loadbalance) {
            // this.loadbalance = loadbalance;
        }

        public void setCallerIp(InetAddress callerIp) {
            this.callerIp = callerIp;
        }

        @Override
        public EncodeProtocol getProtocol() {
            return this.protocol;
        }

        @Override
        public String getServiceName() {
            return this.serviceName;
        }

        @Override
        public String getMethod() {
            return this.method;
        }

        @Override
        public String getCallerVersion() {
            return this.callerVersion;
        }

        // ---------------------------------option操作 start---------------------------------------
        @Override
        public int getOptions() {
            return this.options;
        }

        @Override
        public boolean getOption(int index) {
            int temp = this.options >>> (index - 1) & 1;
            return temp > 0;
        }

        /** interface中未定义，目的是不面向应用层开放 */
        public InvocationInfo setOptionsByInt(int options) {
            this.options = options;
            return this;
        }

        /**
         * 注: interface中未定义，目的是不面向应用层开放
         */
        public void setOptions(int options) {
            this.options |= options;
        }

        /**
         * set option by index
         *
         * 注:interface中未定义，目的是不面向应用层开放
         *
         * @param option option value.false mean 0, true mean 1
         * @param option index
         */
        public InvocationInfo setOption(boolean option, int index) {
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
        public String getCallerId() {
            return this.callerId;
        }

        @Override
        public long getSessionTid() {
            return this.sessionTid;
        }

        @Override
        public long getCallerTid() {
            return this.callerTid;
        }

        @Override
        public long getCalleeTid() {
            return this.calleeTid;
        }

        @Override
        @Deprecated
        public long getUid() {
            // return this.uid;
            return 0;
        }

        @Override
        public String getUip() {
            return this.uip;
        }

        @Override
        public Map<String, String> getCookie() {
            return this.cookie;
        }

        @Override
        public String getUserSign() {
            return this.userSign;
        }

        @Override
        @Deprecated
        public LoadBalanceStratage getLoadBalance() {
            // return this.loadbalance;
            return LoadBalanceStratage.RandomRobin;
        }

        @Override
        @Deprecated
        public String getHashKey() {
            // return hashKey;
            return null;
        }

        @Override
        public int getFailTryCount() {
            return this.failTryCount;
        }

        @Override
        public long getTimeout() {
            return this.timeout;
        }

        @Override
        public InetAddress getCallerIp() {
            return this.callerIp;
        }

        @Override
        public String getCalleeIP() {
            return this.calleeIp;
        }

        @Override
        public int getCalleePort() {
            return this.calleePort;
        }

        @Override
        public String getCalleeVersion() {
            return this.calleeVersion;
        }

        @Override
        public String getReturnCode() {
            return this.returnCode;
        }

        @Override
        public String getReturnMessage() {
            return this.returnMessage;
        }

        @Override
        public String getErrorGroup() {
            return this.errorGroup;
        }

        @Override
        public String getErrorLevel() {
            return this.errorLevel;
        }

        @Override
        public String getErrorLocation() {
            return this.errorLocation;
        }

        @Override
        public boolean isCircuitBroken() {
            return circuitBroken;
        }

        /** 是否重试 */
        @Override
        public boolean isRetry() {
            return retry;
        }

        @Override
        @Deprecated
        public int getCalleeTime1() {
            // return this.calleeTime1;
            return 0;
        }

        @Override
        @Deprecated
        public int getCalleeTime2() {
            // return this.calleeTime2;
            return 0;
        }

        @Override
        @Deprecated
        public long getSendLength() {
            // return sendLength;
            return 0;
        }

        @Override
        public long getRecvLength() {
            return this.recvLength;
        }

        @Override
        @Deprecated
        public int getCallerTime() {
            // return callerTime;
            return 0;
        }

        @Deprecated
        public void setLoadBalance(LoadBalanceStratage loadbalance) {
            // this.loadBalance = loadBalance;
        }

        public void setProtocol(EncodeProtocol protocol) {
            this.protocol = protocol;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public void setCallerVersion(String callerVersion) {
            this.callerVersion = callerVersion;
        }

        public void setCallerId(String callerId) {
            this.callerId = callerId;
        }

        public void setSessionTid(long sessionTid) {
            this.sessionTid = sessionTid;
        }

        public void setCallerTid(long callerTid) {
            this.callerTid = callerTid;
        }

        public void setCalleeTid(long calleeTid) {
            this.calleeTid = calleeTid;
        }

        @Deprecated
        public void setUid(long uid) {
            // this.uid = uid;
        }

        public void setUip(String uip) {
            this.uip = uip;
        }

        public void setCookie(Map<String, String> cookie) {
            this.cookie = cookie;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        @Deprecated
        public void setHashKey(String hashKey) {
            // this.hashKey=hashKey
        }

        public void setFailTryCount(int failTryCount) {
            this.failTryCount = Math.abs(failTryCount);
        }

        public void setTimeout(long timeout) {
            this.timeout = timeout;
        }

        public void setCalleePort(int calleePort) {
            this.calleePort = calleePort;
        }

        public void setCalleeVersion(String calleeVersion) {
            this.calleeVersion = calleeVersion;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public void setReturnMessage(String returnMessage) {
            this.returnMessage = returnMessage;
        }

        public void setErrorLevel(String errorLevel) {
            this.errorLevel = errorLevel;
        }

        public void setErrorGroup(String errorGroup) {
            this.errorGroup = errorGroup;
        }

        public void setErrorLocation(String errorLocation) {
            this.errorLocation = errorLocation;
        }

        public void setCircuitBroken(boolean circuitBroken) {
            this.circuitBroken = circuitBroken;
        }

        public void setRetry(boolean retry) {
            this.retry = retry;
        }

        @Deprecated
        public void setCalleeTime1(int calleeTime1) {
            // this.calleeTime1 = calleeTime1;
        }

        @Deprecated
        public void setSendLength(long sendLength) {
            // this.sendLength = sendLength
        }

        @Deprecated
        public void setCalleeTime2(int calleeTime2) {
            // this.calleeTime2 = calleeTime2;
        }

        public void setRecvLength(long recvLength) {
            this.recvLength = recvLength;
        }

        @Deprecated
        public void setCallerTime(int callerTime) {
            // this.callerTime = callerTime;
        }

        public void setCalleeIp(String calleeIp) {
            this.calleeIp = calleeIp;
        }

        @Override
        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public void setResponseByteBuf(ByteBuf responseBuf) {
            this.responseByteBuf = responseBuf;
        }

        public ByteBuf getResponseByteBuf() {
            return this.responseByteBuf;
        }

        @Override
        @Deprecated
        public Long getMscTraceId() {
            // return mscTraceId;
            return 0L;
        }

        @Override
        @Deprecated
        public Long getMscSpanId() {
            // return mscSpanId;
            return 0L;
        }

        @Deprecated
        public void setMscTraceId(Long mscTraceId) {
            // this.mscTraceId = mscTraceId;
        }

        @Deprecated
        public void setMscSpanId(Long mscSpanId) {
            // this.mscSpanId=mscSpanId;
        }

        @Override
        public String getLocalAddress() {
            return this.localAddress;
        }

        public void setLocalAddress(String localAddress) {
            this.localAddress = localAddress;
        }

        @Override
        public String getRemoteAddress() {
            return this.remoteAddress;
        }

        public void setRemoteAddress(String remoteAddress) {
            this.remoteAddress = remoteAddress;
        }

        @Override
        @Deprecated
        public long getCallerTimeMillis() {
            // return callerTimeMillis;
            return 0L;
        }

        @Deprecated
        public void setCallerTimeMillis(long callerTimeMillis) {
            // this.callerTimeMillis = callerTimeMillis;
        }

        public int getCallerPort() {
            return callerPort;
        }

        public void setCallerPort(int callerPort) {
            this.callerPort = callerPort;
        }

        @Override
        public String getForwardedFor() {
            return forwardedFor;
        }

        public void setForwardedFor(String forwardedFor) {
            this.forwardedFor = forwardedFor;
        }

        @Override
        public String getProxyIP() {
            return proxyIP;
        }

        public void setProxyIP(String proxyIp) {
            this.proxyIP = proxyIp;
        }

        @Override
        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

    }

    @Override
    public String getDomain() {
        return domain;
    }

    /**
     * set option by index
     * @param option option value.false mean 0, true mean 1
     * @param option index
     */
    public InvocationContext setOption(boolean option, int index) {
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

    @Deprecated
    public InvocationContext setOptionsByInt(int options) {
        this.options = options;
        return this;
    }

    @Override
    public boolean getOption(int index) {
        int temp = this.options >>> (index - 1) & 1;
        return temp > 0;
    }

    @Override
    public InvocationContext setSSLFlag(boolean sslFlag) {
        this.sslFlag = sslFlag;
        return this;
    }

    @Override
    public boolean isSSLFlag() {
        return sslFlag;
    }
}