package com.tc.tsp.core.entity;

import java.util.Map;

/**
 * Created by cai.tian on 2017/11/30.
 */
public class Header {

    private static final long serialVersionUID = 1L;
    private String serviceName;
    private String method;
    private String version;
    private Integer options;
    private String callerId;
    private Long callerTid;
    private Long sessionTid;
    private String forwardedFor;
    private Long uid;
    private Integer uip;
    private String callerSign;
    private Map<String, String> cookie;
    private String retCode;
    private String retMessage;
    private Integer calleeTime1;
    private Integer calleeTime2;
    private Long calleeTid;
    private Long mscTraceId;
    private Long mscSpanId;

    /**
     * <p>
     * such as, errorGroup, errorLevel, errorLocation, circuitBroken, etc
     */

    private Map<String, String> extFields;

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String value) {
        this.serviceName = value;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String value) {
        this.method = value;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String value) {
        this.version = value;
    }

    public Integer getOptions() {
        return this.options;
    }

    public void setOptions(Integer value) {
        this.options = value;
    }

    public String getCallerId() {
        return this.callerId;
    }

    public void setCallerId(String value) {
        this.callerId = value;
    }

    public Long getCallerTid() {
        return this.callerTid;
    }

    public void setCallerTid(Long value) {
        this.callerTid = value;
    }

    public Long getSessionTid() {
        return this.sessionTid;
    }

    public void setSessionTid(Long value) {
        this.sessionTid = value;
    }

    public String getForwardedFor() {
        return this.forwardedFor;
    }

    public void setForwardedFor(String value) {
        this.forwardedFor = value;
    }

    public Long getUid() {
        return this.uid;
    }

    public void setUid(Long value) {
        this.uid = value;
    }

    public Integer getUip() {
        return this.uip;
    }

    public void setUip(Integer value) {
        this.uip = value;
    }

    public String getCallerSign() {
        return this.callerSign;
    }

    public void setCallerSign(String value) {
        this.callerSign = value;
    }

    public Map<String, String> getCookie() {
        return this.cookie;
    }

    public void setCookie(Map<String, String> value) {
        this.cookie = value;
    }

    public String getRetCode() {
        return this.retCode;
    }

    public void setRetCode(String value) {
        this.retCode = value;
    }

    public String getRetMessage() {
        return this.retMessage;
    }

    public void setRetMessage(String value) {
        this.retMessage = value;
    }

    public Integer getCalleeTime1() {
        return this.calleeTime1;
    }

    public void setCalleeTime1(Integer value) {
        this.calleeTime1 = value;
    }

    public Integer getCalleeTime2() {
        return this.calleeTime2;
    }

    public void setCalleeTime2(Integer value) {
        this.calleeTime2 = value;
    }

    public Long getCalleeTid() {
        return this.calleeTid;
    }

    public void setCalleeTid(Long value) {
        this.calleeTid = value;
    }

    public Long getMscTraceId() {
        return this.mscTraceId;
    }

    public void setMscTraceId(Long value) {
        this.mscTraceId = value;
    }

    public Long getMscSpanId() {
        return this.mscSpanId;
    }

    public void setMscSpanId(Long value) {
        this.mscSpanId = value;
    }

    public Map<String, String> getExtFields() {
        return this.extFields;
    }

    public void setExtFields(Map<String, String> value) {
        this.extFields = value;
    }
}
