package com.tc.tsp.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tc.tsp.core.base.MethodDispatcher;
import com.tc.tsp.core.base.TBaseWrapper;
import com.tc.tsp.core.base.TspServiceStub;
import com.tc.tsp.core.context.*;
import com.tc.tsp.core.processor.TspProcessor;
import com.tc.tsp.core.protocol.TFieldDescriptor;
import com.tc.tsp.core.protocol.TspProtocol;
import com.tc.tsp.core.support.TFramedByteBufTransport;
import io.netty.buffer.ByteBuf;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cai.tian on 2017/12/4.
 */
public class UserServiceHelper {
    public static class sysHello_args implements java.io.Serializable {

        private static final long serialVersionUID = 1L;

        private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String value) {
            this.name = value;
        }

        public String toString() {

            return "name:" + this.name;
        }
    }


    public static class sysHello_result implements java.io.Serializable {

        private static final long serialVersionUID = 1L;

        private String success;

        public String getSuccess() {
            return this.success;
        }

        public void setSuccess(String value) {
            this.success = value;
        }

        public String toString() {
            return "success:" + this.success;
        }
    }


    public static class sysHello_argsHelper implements TBaseWrapper<sysHello_args, sysHello_argsHelper._Fields> {
        public static final sysHello_argsHelper OBJ = new sysHello_argsHelper();

        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("updateByName_args");

        private static final TFieldDescriptor NAME_FIELD_DESC = new TFieldDescriptor("name", TType.STRING, (short) 1, true);

        private static final TFieldDescriptor JSON_BODY_DESC = new TFieldDescriptor("ospJson", (byte) -1, (short) -1, true);

        private static final TFieldDescriptor[] fieldDescriptors = {
                NAME_FIELD_DESC
        };

        public TFieldDescriptor[] getFieldDescriptors() {
            return fieldDescriptors;
        }


        public enum _Fields implements org.apache.thrift.TFieldIdEnum {
            NAME(NAME_FIELD_DESC);
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

            public short getThriftFieldId() {
                return fieldDescriptor.id;
            }

            public String getFieldName() {
                return fieldDescriptor.name;
            }

        }

        public static sysHello_argsHelper getInstance() {
            return OBJ;
        }


        public void read(sysHello_args struct, org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {

            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while (true) {
                schemeField = iprot.readFieldBegin();
                if (schemeField.type == org.apache.thrift.protocol.TType.STOP) break;
                switch (schemeField.id) {
                    case -1: {
                        if (schemeField.type == -1) {
                            String bodyJson = iprot.readString();
                            sysHello_args fromJson = JSON.parseObject(bodyJson, struct.getClass());
                            struct.setName(fromJson.getName());
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }

                        break;
                    }
                    case 1: {
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            String value = iprot.readString();
                            struct.setName(value);
                        } else {
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


        public void write(sysHello_args struct, org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
            validate(struct);
            oprot.writeStructBegin(STRUCT_DESC);
            EncodeProtocol protocol = InvocationContext.Factory.getInstance().getLastInvocation().getProtocol();
            if (protocol != EncodeProtocol.Binary && protocol != EncodeProtocol.CompactBinary && protocol != EncodeProtocol.JSON) {
                oprot.writeFieldBegin(JSON_BODY_DESC);
                byte[] b = JSON.toJSONBytes(struct, SerializerFeature.DisableCircularReferenceDetect);
                if (b == null) {
                    b = "null".getBytes(Charset.forName("UTF-8"));
                }
                oprot.writeBinary(ByteBuffer.wrap(b, 0, b.length));
                oprot.writeFieldEnd();
            } else {
                if (struct.getName() != null) {
                    oprot.writeFieldBegin(NAME_FIELD_DESC);
                    oprot.writeString(struct.getName());
                    oprot.writeFieldEnd();
                }
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        public _Fields fieldForId(short fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public boolean isSet(sysHello_args bean, _Fields field) {
            if (field == null) throw new IllegalArgumentException();
            switch (field) {
                case NAME:
                    return bean.getName() != null;
            }
            throw new IllegalStateException();
        }

        public Object getFieldValue(sysHello_args bean, _Fields field) {
            switch (field) {
                case NAME:
                    return bean.getName();
            }
            throw new IllegalStateException();
        }

        public void setFieldValue(sysHello_args bean, _Fields field, Object value) {
            switch (field) {
                case NAME:
                    bean.setName((String) value);
                    break;
            }
        }

        public void validate(sysHello_args bean) throws TException {
        }

        public org.apache.thrift.TFieldIdEnum[] getFields() {
            return _Fields.values();
        }
    }





    public static class sysHello_resultHelper implements TBaseWrapper < sysHello_result, sysHello_resultHelper._Fields >
    {
        public static final sysHello_resultHelper OBJ = new sysHello_resultHelper();

        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("sysHello_result");

        private static final TFieldDescriptor SUCCESS_FIELD_DESC = new TFieldDescriptor("success", org.apache.thrift.protocol.TType.STRING, (short) 0, true);

        private static final TFieldDescriptor JSON_BODY_DESC = new TFieldDescriptor("ospJson", (byte)-1, (short) -1, true);

        private static final TFieldDescriptor[] fieldDescriptors = {
                SUCCESS_FIELD_DESC
        };
        public TFieldDescriptor[] getFieldDescriptors() {
            return fieldDescriptors;
        }

        public enum _Fields implements org.apache.thrift.TFieldIdEnum
        {
            SUCCESS(SUCCESS_FIELD_DESC);
            private static final Map < String, _Fields > byName = new HashMap<String, _Fields>();
            private static final Map < Short, _Fields > byId = new HashMap<Short, _Fields>();
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
            public short getThriftFieldId() {
                return fieldDescriptor.id;
            }
            public String getFieldName() {
                return fieldDescriptor.name;
            }
        }


        public static sysHello_resultHelper getInstance() {
            return OBJ;
        }


        public void read(sysHello_result struct, org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while(true) {
                schemeField = iprot.readFieldBegin();
                if(schemeField.type == org.apache.thrift.protocol.TType.STOP) break;
                switch(schemeField.id) {
                    case -1: {
                        if(schemeField.type == -1) {
                            String bodyJson = iprot.readString();
                            String fromJson = JSON.parseObject(bodyJson, String.class);
                            struct.setSuccess(fromJson);
                        }
                        else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    }

                    case 0: {
                        if(schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            String value;
                            value = iprot.readString();
                            struct.setSuccess(value);
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


        public void write(sysHello_result struct, org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
            validate(struct);
            oprot.writeStructBegin(STRUCT_DESC);
            EncodeProtocol protocol = TransactionContext.Factory.getInstance().getProtocol();
            if(protocol != EncodeProtocol.Binary && protocol != EncodeProtocol.CompactBinary && protocol != EncodeProtocol.JSON){
                oprot.writeFieldBegin(JSON_BODY_DESC);
                byte[] b = JSON.toJSONBytes(struct.getSuccess(), SerializerFeature.DisableCircularReferenceDetect);
                oprot.writeBinary(ByteBuffer.wrap(b, 0, b.length));
                oprot.writeFieldEnd();
            }
            else {
                if(struct.getSuccess() != null) {
                    oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
                    oprot.writeString(struct.getSuccess());
                    oprot.writeFieldEnd();
                }
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        public _Fields fieldForId(short fieldId) {

            return _Fields.findByThriftId(fieldId);
        }

        public boolean isSet(sysHello_result bean, _Fields field) {
            if(field == null) throw new IllegalArgumentException();
            switch(field){
                case SUCCESS: return bean.getSuccess() != null;
            }
            throw new IllegalStateException();
        }


        public Object getFieldValue(sysHello_result bean, _Fields field) {
            switch(field){
                case SUCCESS: return bean.getSuccess();
            }
            throw new IllegalStateException();
        }

        public void setFieldValue(sysHello_result bean, _Fields field, Object value) {
            switch(field) {
                case SUCCESS:
                    bean.setSuccess((String)value);
                    break;
            }
        }
        public void validate(sysHello_result bean) throws TException {
        }
        public org.apache.thrift.TFieldIdEnum[] getFields() {
            return _Fields.values();
        }
    }

    public static interface UserServiceStub extends UserService{
        public void asyncIsBufferMerchant(String name);
    }

    public static class UserServiceClient extends TspServiceStub implements UserServiceStub {

        public UserServiceClient() {
            super("", UserService.class, "");
        }

        @Override
        public String sysHello(String name) {

            InvocationContextImpl invocationContext = (InvocationContextImpl) InvocationContext.Factory.getInstance();
            InvocationContextImpl.InvocationInfoImpl lastInvocation = (InvocationContextImpl.InvocationInfoImpl) (invocationContext.getLastInvocation());

            lastInvocation.reset();
            lastInvocation.setServiceName("UserService");
            lastInvocation.setMethod("sysHello");
            lastInvocation.setCallerVersion("");


            ClientSendMessageFilter c=new ClientSendMessageFilter();
            sysHello_args args = new sysHello_args();
            args.setName("abc");



            try {
                ByteBuf byteBuf = c.prepareRequestByteBuf("", args, sysHello_argsHelper.getInstance());
                TspProtocol iprot = TspProtocol.Factory.getOspProtocol4Server();
                iprot.setInputByteBuf(byteBuf);
                ThreadLocalProperty.setRequestFlag(Boolean.TRUE);
                iprot.readMessageBegin();
                TransactionContextImpl transactionContext = (TransactionContextImpl) TransactionContext.Factory.getInstance();
                System.out.println(JSON.toJSONString(transactionContext));



                sysHello_args a =new sysHello_args();
                sysHello_argsHelper.getInstance().read(a,iprot);
                System.out.println(JSON.toJSONString(a));

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void asyncIsBufferMerchant(String name) {

        }
    }


    public static class Processor <I extends UserService> extends TspProcessor<I> implements org.apache.thrift.TProcessor {

        private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class.getName());

        public Processor(I iface) {
            super(iface, getProcessMap(new HashMap<String, MethodDispatcher>()));
        }

        private static Map<String, MethodDispatcher> getProcessMap(Map<String, MethodDispatcher> processMap) {
            processMap.put("sysHello", new sysHello_Dispatcher());
            return processMap;
        }
        public String getDomain(){
            return "tc-test";
        }
    }


    public static class sysHello_Dispatcher < I extends UserService> extends MethodDispatcher<I,
            sysHello_args, sysHello_argsHelper,
            sysHello_result, sysHello_resultHelper > {

        public sysHello_Dispatcher() {
            super("sysHello", sysHello_argsHelper.getInstance(), sysHello_resultHelper.getInstance());
        }

        public sysHello_args getEmptyArgsInstance() {
            return new sysHello_args();
        }

        protected boolean isOneway() {
            return false;
        }

        public sysHello_result getResult(I iface, sysHello_args args)  throws Throwable{
            sysHello_result result = new sysHello_result();
            TransactionContext tc = TransactionContext.Factory.getInstance();
            try{
                result.setSuccess( iface.sysHello(args.getName()) );
                tc.setReturnCode("0");
            }
            catch(Exception ex){

                throw ex;
            }
            return result;
        }

    }


}
