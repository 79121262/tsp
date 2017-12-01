package com.tc.tsp.core.entity;

import com.alibaba.fastjson.JSON;
import com.tc.tsp.core.base.TBaseWrapper;
import com.tc.tsp.core.protocol.TFieldDescriptor;
import org.apache.thrift.TException;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cai.tian on 2017/11/30.
 */
public class HeaderHelper implements TBaseWrapper<Header, HeaderHelper._Fields> {
    public static final HeaderHelper OBJ = new HeaderHelper();

    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct(
            "Header");

    private static final TFieldDescriptor SERVICENAME_FIELD_DESC = new TFieldDescriptor("serviceName",
            org.apache.thrift.protocol.TType.STRING, (short) 1, true);
    private static final TFieldDescriptor METHOD_FIELD_DESC = new TFieldDescriptor("method",
            org.apache.thrift.protocol.TType.STRING, (short) 2, true);
    private static final TFieldDescriptor VERSION_FIELD_DESC = new TFieldDescriptor("version",
            org.apache.thrift.protocol.TType.STRING, (short) 3, true);
    private static final TFieldDescriptor OPTIONS_FIELD_DESC = new TFieldDescriptor("options",
            org.apache.thrift.protocol.TType.I32, (short) 4, true);
    private static final TFieldDescriptor CALLERID_FIELD_DESC = new TFieldDescriptor("callerId",
            org.apache.thrift.protocol.TType.STRING, (short) 6, true);
    private static final TFieldDescriptor CALLERTID_FIELD_DESC = new TFieldDescriptor("callerTid",
            org.apache.thrift.protocol.TType.I64, (short) 11, true);
    private static final TFieldDescriptor SESSIONTID_FIELD_DESC = new TFieldDescriptor("sessionTid",
            org.apache.thrift.protocol.TType.I64, (short) 10, true);
    private static final TFieldDescriptor FORWARDEDFOR_FIELD_DESC = new TFieldDescriptor("forwardedFor",
            org.apache.thrift.protocol.TType.STRING, (short) 13, true);
    private static final TFieldDescriptor UID_FIELD_DESC = new TFieldDescriptor("uid",
            org.apache.thrift.protocol.TType.I64, (short) 14, true);
    private static final TFieldDescriptor UIP_FIELD_DESC = new TFieldDescriptor("uip",
            org.apache.thrift.protocol.TType.I32, (short) 15, true);
    private static final TFieldDescriptor CALLERSIGN_FIELD_DESC = new TFieldDescriptor("callerSign",
            org.apache.thrift.protocol.TType.STRING, (short) 30, true);
    private static final TFieldDescriptor COOKIE_FIELD_DESC = new TFieldDescriptor("cookie",
            org.apache.thrift.protocol.TType.MAP, (short) 16, true);
    private static final TFieldDescriptor RETCODE_FIELD_DESC = new TFieldDescriptor("retCode",
            org.apache.thrift.protocol.TType.STRING, (short) 7, true);
    private static final TFieldDescriptor RETMESSAGE_FIELD_DESC = new TFieldDescriptor("retMessage",
            org.apache.thrift.protocol.TType.STRING, (short) 8, true);
    private static final TFieldDescriptor CALLEETIME1_FIELD_DESC = new TFieldDescriptor("calleeTime1",
            org.apache.thrift.protocol.TType.I32, (short) 21, true);
    private static final TFieldDescriptor CALLEETIME2_FIELD_DESC = new TFieldDescriptor("calleeTime2",
            org.apache.thrift.protocol.TType.I32, (short) 22, true);
    private static final TFieldDescriptor CALLEETID_FIELD_DESC = new TFieldDescriptor("calleeTid",
            org.apache.thrift.protocol.TType.I64, (short) 23, true);
    private static final TFieldDescriptor MSCTRACEID_FIELD_DESC = new TFieldDescriptor("mscTraceId",
            org.apache.thrift.protocol.TType.I64, (short) 24, true);
    private static final TFieldDescriptor MSCSPANID_FIELD_DESC = new TFieldDescriptor("mscSpanId",
            org.apache.thrift.protocol.TType.I64, (short) 25, true);
    private static final TFieldDescriptor EXTFIELDS_FIELD_DESC = new TFieldDescriptor("extFields",
            org.apache.thrift.protocol.TType.MAP, (short) 40, true);

    private static final TFieldDescriptor[] fieldDescriptors = {

            SERVICENAME_FIELD_DESC, METHOD_FIELD_DESC, VERSION_FIELD_DESC, OPTIONS_FIELD_DESC, CALLERID_FIELD_DESC,
            CALLERTID_FIELD_DESC, SESSIONTID_FIELD_DESC, FORWARDEDFOR_FIELD_DESC, UID_FIELD_DESC, UIP_FIELD_DESC,
            CALLERSIGN_FIELD_DESC, COOKIE_FIELD_DESC, RETCODE_FIELD_DESC, RETMESSAGE_FIELD_DESC, CALLEETIME1_FIELD_DESC,
            CALLEETIME2_FIELD_DESC, CALLEETID_FIELD_DESC, MSCTRACEID_FIELD_DESC, MSCSPANID_FIELD_DESC,
            EXTFIELDS_FIELD_DESC };

    //@Override
    public TFieldDescriptor[] getFieldDescriptors() {

        return fieldDescriptors;
    }

    public enum _Fields implements org.apache.thrift.TFieldIdEnum {

        SERVICENAME(SERVICENAME_FIELD_DESC), METHOD(METHOD_FIELD_DESC), VERSION(VERSION_FIELD_DESC), OPTIONS(
                OPTIONS_FIELD_DESC), CALLERID(CALLERID_FIELD_DESC), CALLERTID(CALLERTID_FIELD_DESC), SESSIONTID(
                SESSIONTID_FIELD_DESC), FORWARDEDFOR(FORWARDEDFOR_FIELD_DESC), UID(UID_FIELD_DESC), UIP(
                UIP_FIELD_DESC), CALLERSIGN(CALLERSIGN_FIELD_DESC), COOKIE(COOKIE_FIELD_DESC), RETCODE(
                RETCODE_FIELD_DESC), RETMESSAGE(RETMESSAGE_FIELD_DESC), CALLEETIME1(
                CALLEETIME1_FIELD_DESC), CALLEETIME2(CALLEETIME2_FIELD_DESC), CALLEETID(
                CALLEETID_FIELD_DESC), MSCTRACEID(
                MSCTRACEID_FIELD_DESC), MSCSPANID(
                MSCSPANID_FIELD_DESC), EXTFIELDS(
                EXTFIELDS_FIELD_DESC);
        private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();
        private static final Map<Short, _Fields> byId = new HashMap<Short, _Fields>();

        static {

            for (_Fields field : EnumSet.allOf(_Fields.class)) {

                byName.put(field.getFieldName(), field);
                byId.put(field.getThriftFieldId(), field);
            }

        }

        public static _Fields findByThriftId(int fieldId) {

            return byId.get(fieldId);
        }

        public static _Fields findByName(String name) {

            return byName.get(name);
        }

        private TFieldDescriptor fieldDescriptor;

        _Fields(TFieldDescriptor fieldDescriptor) {

            this.fieldDescriptor = fieldDescriptor;
        }

        @Override
        public short getThriftFieldId() {

            return fieldDescriptor.id;
        }

        @Override
        public String getFieldName() {

            return fieldDescriptor.name;
        }

    }

    public static HeaderHelper getInstance() {

        return OBJ;
    }

    @Override
    public void read(Header struct, org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {

        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true) {

            schemeField = iprot.readFieldBegin();
            if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                break;
            }
            switch (schemeField.id) {
                case -1: {
                    if (schemeField.type == -1) {
                        String bodyJson = iprot.readString();
                        Header fromJson = JSON.parseObject(bodyJson, Header.class);

                        struct.setServiceName(fromJson.getServiceName());
                        struct.setMethod(fromJson.getMethod());
                        struct.setVersion(fromJson.getVersion());
                        struct.setOptions(fromJson.getOptions());
                        struct.setCallerId(fromJson.getCallerId());
                        struct.setCallerTid(fromJson.getCallerTid());
                        struct.setSessionTid(fromJson.getSessionTid());
                        struct.setForwardedFor(fromJson.getForwardedFor());
                        // struct.setUid(fromJson.getUid());
                        struct.setUip(fromJson.getUip());
                        struct.setCallerSign(fromJson.getCallerSign());
                        struct.setCookie(fromJson.getCookie());
                        struct.setRetCode(fromJson.getRetCode());
                        struct.setRetMessage(fromJson.getRetMessage());
                        struct.setCalleeTime1(fromJson.getCalleeTime1());
                        struct.setCalleeTime2(fromJson.getCalleeTime2());
                        struct.setCalleeTid(fromJson.getCalleeTid());
                        // struct.setMscTraceId(fromJson.getMscTraceId());
                        // struct.setMscSpanId(fromJson.getMscSpanId());
                        struct.setExtFields(fromJson.getExtFields());

                    } else {
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;
                }
                case 1: {

                    struct.setServiceName(iprot.readString());
                    break;
                }

                case 2: {
                    struct.setMethod(iprot.readString());

                    break;
                }

                case 3: {
                    if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                        struct.setVersion(iprot.readString());
                    } else {
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                case 4: {

                    if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                        struct.setOptions(iprot.readI32());
                    } else {
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                case 6: {

                    if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                        struct.setCallerId(iprot.readString());
                    } else {
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                case 11: {

                    if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
                        struct.setCallerTid(iprot.readI64());
                    } else {
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                case 10: {

                    if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
                        struct.setSessionTid(iprot.readI64());
                    } else {
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                case 13: {

                    if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {

                        struct.setForwardedFor(iprot.readString());
                    }

                    else {

                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                case 14: {

                    if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
                        struct.setUid(iprot.readI64());
                    }

                    else {

                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                case 15: {

                    if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                        struct.setUip(iprot.readI32());
                    }

                    else {

                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                case 30: {

                    if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {

                        struct.setCallerSign(iprot.readString());
                    }

                    else {

                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                case 16: {

                    if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {

                        Map<String, String> value;

                        org.apache.thrift.protocol.TMap _map0 = iprot.readMapBegin();
                        value = new HashMap<String, String>(_map0.size);
                        for (int i0 = 0; i0 < _map0.size; i0++) {
                            value.put(iprot.readString(), iprot.readString());
                        }

                        iprot.readMapEnd();

                        struct.setCookie(value);
                    }

                    else {

                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                case 7: {

                    if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {

                        struct.setRetCode(iprot.readString());
                    }

                    else {

                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                case 8: {

                    if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                        struct.setRetMessage(iprot.readString());
                    }

                    else {

                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                case 21: {

                    if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                        struct.setCalleeTime1(iprot.readI32());
                    }

                    else {

                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                case 22: {

                    if (schemeField.type == org.apache.thrift.protocol.TType.I32) {

                        struct.setCalleeTime2(iprot.readI32());
                    }

                    else {

                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                case 23: {

                    if (schemeField.type == org.apache.thrift.protocol.TType.I64) {

                        struct.setCalleeTid(iprot.readI64());
                    }

                    else {

                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                case 24: {

                    if (schemeField.type == org.apache.thrift.protocol.TType.I64) {

                        struct.setMscTraceId(iprot.readI64());
                    }

                    else {

                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                case 25: {

                    if (schemeField.type == org.apache.thrift.protocol.TType.I64) {

                        struct.setMscSpanId(iprot.readI64());
                    }

                    else {

                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                case 40: {

                    if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {

                        Map<String, String> value;

                        org.apache.thrift.protocol.TMap _map1 = iprot.readMapBegin();
                        value = new HashMap<String, String>(_map1.size);
                        for (int i1 = 0; i1 < _map1.size; i1++) {
                            value.put(iprot.readString(), iprot.readString());
                        }

                        iprot.readMapEnd();

                        struct.setExtFields(value);
                    }

                    else {

                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }

                    break;
                }

                default:
                    org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }

            iprot.readFieldEnd();
        }

        iprot.readStructEnd();

        validate(struct);
    }

    @Override
    public void write(Header struct, org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {

        validate(struct);
        oprot.writeStructBegin(STRUCT_DESC);

        oprot.writeFieldBegin(SERVICENAME_FIELD_DESC);
        oprot.writeString(struct.getServiceName());

        oprot.writeFieldEnd();

        oprot.writeFieldBegin(METHOD_FIELD_DESC);
        oprot.writeString(struct.getMethod());

        oprot.writeFieldEnd();

        if (struct.getVersion() != null) {

            oprot.writeFieldBegin(VERSION_FIELD_DESC);
            oprot.writeString(struct.getVersion());

            oprot.writeFieldEnd();
        }

        if (struct.getOptions() != null) {

            oprot.writeFieldBegin(OPTIONS_FIELD_DESC);
            oprot.writeI32(struct.getOptions());

            oprot.writeFieldEnd();
        }

        if (struct.getCallerId() != null) {

            oprot.writeFieldBegin(CALLERID_FIELD_DESC);
            oprot.writeString(struct.getCallerId());

            oprot.writeFieldEnd();
        }

        if (struct.getCallerTid() != null) {

            oprot.writeFieldBegin(CALLERTID_FIELD_DESC);
            oprot.writeI64(struct.getCallerTid());

            oprot.writeFieldEnd();
        }

        if (struct.getSessionTid() != null) {

            oprot.writeFieldBegin(SESSIONTID_FIELD_DESC);
            oprot.writeI64(struct.getSessionTid());

            oprot.writeFieldEnd();
        }

        if (struct.getForwardedFor() != null) {

            oprot.writeFieldBegin(FORWARDEDFOR_FIELD_DESC);
            oprot.writeString(struct.getForwardedFor());

            oprot.writeFieldEnd();
        }

        if (struct.getUid() != null) {

            oprot.writeFieldBegin(UID_FIELD_DESC);
            oprot.writeI64(struct.getUid());

            oprot.writeFieldEnd();
        }

        if (struct.getUip() != null) {

            oprot.writeFieldBegin(UIP_FIELD_DESC);
            oprot.writeI32(struct.getUip());

            oprot.writeFieldEnd();
        }

        if (struct.getCallerSign() != null) {

            oprot.writeFieldBegin(CALLERSIGN_FIELD_DESC);
            oprot.writeString(struct.getCallerSign());

            oprot.writeFieldEnd();
        }

        if (struct.getCookie() != null) {

            oprot.writeFieldBegin(COOKIE_FIELD_DESC);

            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING,
                    org.apache.thrift.protocol.TType.STRING, struct.getCookie().size()));
            for (Map.Entry<String, String> _ir0 : struct.getCookie().entrySet()) {

                String _key0 = _ir0.getKey();
                String _value0 = _ir0.getValue();

                if (_key0 == null || _value0 == null) {

                    //throw new OspException(OspSysErrorCode.OSP_SERIALIZE_FAILED, "collection element can't be null");
                }

                oprot.writeString(_key0);

                oprot.writeString(_value0);

            }

            oprot.writeMapEnd();

            oprot.writeFieldEnd();
        }

        if (struct.getRetCode() != null) {

            oprot.writeFieldBegin(RETCODE_FIELD_DESC);
            oprot.writeString(struct.getRetCode());

            oprot.writeFieldEnd();
        }

        if (struct.getRetMessage() != null) {

            oprot.writeFieldBegin(RETMESSAGE_FIELD_DESC);
            oprot.writeString(struct.getRetMessage());

            oprot.writeFieldEnd();
        }

        if (struct.getCalleeTime1() != null) {

            oprot.writeFieldBegin(CALLEETIME1_FIELD_DESC);
            oprot.writeI32(struct.getCalleeTime1());

            oprot.writeFieldEnd();
        }

        if (struct.getCalleeTime2() != null) {

            oprot.writeFieldBegin(CALLEETIME2_FIELD_DESC);
            oprot.writeI32(struct.getCalleeTime2());

            oprot.writeFieldEnd();
        }

        if (struct.getCalleeTid() != null) {

            oprot.writeFieldBegin(CALLEETID_FIELD_DESC);
            oprot.writeI64(struct.getCalleeTid());

            oprot.writeFieldEnd();
        }

        if (struct.getMscTraceId() != null) {

            oprot.writeFieldBegin(MSCTRACEID_FIELD_DESC);
            oprot.writeI64(struct.getMscTraceId());

            oprot.writeFieldEnd();
        }

        if (struct.getMscSpanId() != null) {

            oprot.writeFieldBegin(MSCSPANID_FIELD_DESC);
            oprot.writeI64(struct.getMscSpanId());

            oprot.writeFieldEnd();
        }

        if (struct.getExtFields() != null) {

            oprot.writeFieldBegin(EXTFIELDS_FIELD_DESC);

            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING,
                    org.apache.thrift.protocol.TType.STRING, struct.getExtFields().size()));
            for (Map.Entry<String, String> _ir0 : struct.getExtFields().entrySet()) {

                String _key0 = _ir0.getKey();
                String _value0 = _ir0.getValue();

                if (_key0 == null || _value0 == null) {

                    //throw new OspException(OspSysErrorCode.OSP_SERIALIZE_FAILED, "collection element can't be null");
                }

                oprot.writeString(_key0);

                oprot.writeString(_value0);

            }

            oprot.writeMapEnd();

            oprot.writeFieldEnd();
        }

        oprot.writeFieldStop();
        oprot.writeStructEnd();
    }

    @Override
    public _Fields fieldForId(short fieldId) {

        return _Fields.findByThriftId(fieldId);
    }

    @Override
    public boolean isSet(Header bean, _Fields field) {

        if (field == null) {
            throw new IllegalArgumentException();
        }

        switch (field) {

            case SERVICENAME:
                return bean.getServiceName() != null;
            case METHOD:
                return bean.getMethod() != null;
            case VERSION:
                return bean.getVersion() != null;
            case OPTIONS:
                return bean.getOptions() != null;
            case CALLERID:
                return bean.getCallerId() != null;
            case CALLERTID:
                return bean.getCallerTid() != null;
            case SESSIONTID:
                return bean.getSessionTid() != null;
            case FORWARDEDFOR:
                return bean.getForwardedFor() != null;
            case UID:
                return bean.getUid() != null;
            case UIP:
                return bean.getUip() != null;
            case CALLERSIGN:
                return bean.getCallerSign() != null;
            case COOKIE:
                return bean.getCookie() != null;
            case RETCODE:
                return bean.getRetCode() != null;
            case RETMESSAGE:
                return bean.getRetMessage() != null;
            case CALLEETIME1:
                return bean.getCalleeTime1() != null;
            case CALLEETIME2:
                return bean.getCalleeTime2() != null;
            case CALLEETID:
                return bean.getCalleeTid() != null;
            case MSCTRACEID:
                return bean.getMscTraceId() != null;
            case MSCSPANID:
                return bean.getMscSpanId() != null;
            case EXTFIELDS:
                return bean.getExtFields() != null;

        }

        throw new IllegalStateException();
    }

    @Override
    public Object getFieldValue(Header bean, _Fields field) {

        switch (field) {

            case SERVICENAME:
                return bean.getServiceName();
            case METHOD:
                return bean.getMethod();
            case VERSION:
                return bean.getVersion();
            case OPTIONS:
                return bean.getOptions();
            case CALLERID:
                return bean.getCallerId();
            case CALLERTID:
                return bean.getCallerTid();
            case SESSIONTID:
                return bean.getSessionTid();
            case FORWARDEDFOR:
                return bean.getForwardedFor();
            case UID:
                return bean.getUid();
            case UIP:
                return bean.getUip();
            case CALLERSIGN:
                return bean.getCallerSign();
            case COOKIE:
                return bean.getCookie();
            case RETCODE:
                return bean.getRetCode();
            case RETMESSAGE:
                return bean.getRetMessage();
            case CALLEETIME1:
                return bean.getCalleeTime1();
            case CALLEETIME2:
                return bean.getCalleeTime2();
            case CALLEETID:
                return bean.getCalleeTid();
            case MSCTRACEID:
                return bean.getMscTraceId();
            case MSCSPANID:
                return bean.getMscSpanId();
            case EXTFIELDS:
                return bean.getExtFields();

        }

        throw new IllegalStateException();
    }

    @Override
    public void setFieldValue(Header bean, _Fields field, Object value) {

        switch (field) {

            case SERVICENAME:
                bean.setServiceName((String) value);
                break;

            case METHOD:
                bean.setMethod((String) value);
                break;

            case VERSION:
                bean.setVersion((String) value);
                break;

            case OPTIONS:
                bean.setOptions((Integer) value);
                break;

            case CALLERID:
                bean.setCallerId((String) value);
                break;

            case CALLERTID:
                bean.setCallerTid((Long) value);
                break;

            case SESSIONTID:
                bean.setSessionTid((Long) value);
                break;

            case FORWARDEDFOR:
                bean.setForwardedFor((String) value);
                break;

            case UID:
                bean.setUid((Long) value);
                break;

            case UIP:
                bean.setUip((Integer) value);
                break;

            case CALLERSIGN:
                bean.setCallerSign((String) value);
                break;

            case COOKIE:
                bean.setCookie((Map<String, String>) value);
                break;

            case RETCODE:
                bean.setRetCode((String) value);
                break;

            case RETMESSAGE:
                bean.setRetMessage((String) value);
                break;

            case CALLEETIME1:
                bean.setCalleeTime1((Integer) value);
                break;

            case CALLEETIME2:
                bean.setCalleeTime2((Integer) value);
                break;

            case CALLEETID:
                bean.setCalleeTid((Long) value);
                break;

            case MSCTRACEID:
                bean.setMscTraceId((Long) value);
                break;

            case MSCSPANID:
                bean.setMscSpanId((Long) value);
                break;

            case EXTFIELDS:
                bean.setExtFields((Map<String, String>) value);
                break;

        }

    }

    @Override
    public void validate(Header bean) throws TException {

    }

    @Override
    public org.apache.thrift.TFieldIdEnum[] getFields() {
        return _Fields.values();
    }
}
