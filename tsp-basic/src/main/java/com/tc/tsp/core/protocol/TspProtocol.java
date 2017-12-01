package com.tc.tsp.core.protocol;

import com.tc.tsp.core.base.TBaseWrapper;
import com.tc.tsp.core.context.*;
import com.tc.tsp.core.entity.Header;
import com.tc.tsp.core.entity.HeaderHelper;
import com.tc.tsp.core.support.TFramedByteBufTransport;

import io.netty.buffer.ByteBuf;
import io.netty.util.concurrent.FastThreadLocal;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.*;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 *  * TSP协议格式大致分为：basic protocl + content protocol + ETX
 *
 * 1. basic protocl包括：len + STX + protocolType + sequence<br>
 * A、len是数据包的长度<br>
 * B、STX是开始标志<br>
 * C、protocolType为content Protocol的协议类型，可选范围：TBinaryProtocol、TCompactProtocol、TJSONProtocol<br>
 * D、sequenct为消息序列号，做唯一标志，便于消息区分<br>
 * 2. content protocol包括： head + body<br>
 * 3. ETX为协议结束标志<br>
 *
 *
 * basic protocol用默认的TBinaryProtocol，目的是确保反序列化时能正确获取其中的内容
 *
 * Created by cai.tian on 2017/11/30.
 */
public class TspProtocol extends TProtocol {
    private static Logger logger = LoggerFactory.getLogger(TspProtocol.class);

    public static final byte STX = 0x02;
    public static final byte ETX = 0x03;

    // extFields keys
    private static final String EFK_ERRORGROUP = "errorGroup";
    private static final String EFK_ERRORLEVEL = "errorLevel";
    private static final String EFK_ERRORLOCATION = "errorLocation";
    private static final String EFK_CIRCUITBROKEN = "circuitBroken";
    private static final String EFK_RETRY = "retry"; // 是否重试

    private TFramedByteBufTransport trans;

    private TProtocol basicProtocol = new TBinaryProtocol(this.getTransport());
    private TProtocol contentProtocol;

    private final TProtocol binaryContentProtocol;
    private final TProtocol compactContentProtocol;
    private final TProtocol jsonContentProtocol;
    //private final TProtocol ospJsonContentProtocol;

    public static class Factory {
        static final FastThreadLocal<TspProtocol> ospProtocol4Client = new FastThreadLocal<TspProtocol>() {
            @Override
            protected TspProtocol initialValue() {
                return new TspProtocol(TFramedByteBufTransport.getTFramedByteBufTransport4Client());
            }
        };

        static final FastThreadLocal<TspProtocol> ospProtocol4Server = new FastThreadLocal<TspProtocol>() {
            @Override
            protected TspProtocol initialValue() {
                return new TspProtocol(TFramedByteBufTransport.getTFramedByteBufTransport4Server());
            }
        };

        public static TspProtocol getOspProtocol4Client() {
            return ospProtocol4Client.get();
        }

        public static TspProtocol getOspProtocol4Server() {
            return ospProtocol4Server.get();
        }
    }

    protected TspProtocol(TTransport trans) {
        super(trans);
        this.trans = (TFramedByteBufTransport) trans;

        binaryContentProtocol = new TBinaryProtocol(trans);
        compactContentProtocol = new TCompactProtocol(trans);
        jsonContentProtocol = new TJSONProtocol(trans);
    }

    public int getWriteLength() {
        return trans.getWriteLength();
    }

    // Begin Restful项目，对Transport方法扩展
    public int getWriteIndex() {
        return trans.getWriteIndex();
    }

    public void writerIndex(int index) {
        trans.writerIndex(index);
    }

    public void writeInt(int value) {
        if (contentProtocol instanceof TBinaryProtocol) {
            try {
                contentProtocol.writeI32(value);
            } catch (TException e) {
                logger.error("osp binary protocol write int error", e);
            }
        } else if (contentProtocol instanceof TJSONProtocol) {
            try {
                String str = Long.toString(value);
                byte[] buf = str.getBytes("UTF-8");
                trans.write(buf);
            } catch (Exception e) {
                logger.error("osp json protocol write int error", e);
            }
        }

    }

    // End Restful项目，对Transport方法扩展

    /**
     * 开始写header
     */
    @Override
    public void writeMessageBegin(TMessage message) throws TException {
        InvocationContext invocationContext = InvocationContext.Factory.getInstance();
        boolean requestFlag = ThreadLocalProperty.getRequestFlag();
        byte protocol = requestFlag ? invocationContext.getProtocol().getCode()
                : TransactionContext.Factory.getInstance().getProtocol().getCode();

        // 给contentProtocol指向当前Context指定的Protocol
        defineProtocol(protocol);

        int sequence = message.seqid;

        Header header = new Header();
        if (requestFlag) { // 写请求包
            InvocationContext.InvocationInfo lastInvocation = invocationContext.getLastInvocation();
            header.setServiceName(lastInvocation.getServiceName());
            header.setMethod(lastInvocation.getMethod());
            header.setVersion(lastInvocation.getCallerVersion());

            header.setOptions(lastInvocation.getOptions());
            // header.setUid(lastInvocation.getUid());

            //header.setUip(IPUtils.IPV42Integer(lastInvocation.getUip()));

            header.setForwardedFor(lastInvocation.getForwardedFor());

            header.setCalleeTid(lastInvocation.getCalleeTid());

            header.setCallerId(lastInvocation.getCallerId());
            header.setCallerTid(lastInvocation.getCallerTid());

            header.setSessionTid(lastInvocation.getSessionTid());
            header.setCookie(lastInvocation.getCookie());
            // header.setMscTraceId(0L);
            // header.setMscSpanId(0L);

            header.setCallerSign(lastInvocation.getUserSign());
        } else { // 写返回包
            TransactionContextImpl transactionContextImpl = (TransactionContextImpl) TransactionContext.Factory
                    .getInstance();
            String returnCode = transactionContextImpl.getReturnCode();

            header.setServiceName(transactionContextImpl.getServiceName());
            header.setMethod(transactionContextImpl.getMethod());
            header.setVersion(transactionContextImpl.getVersion());
            header.setRetCode(returnCode);
            header.setRetMessage(transactionContextImpl.getReturnMessage());

            header.setCalleeTid(transactionContextImpl.getCalleeTid());
            header.setCalleeTime1(0);
            header.setCalleeTime2(0);

            header.setCallerId(transactionContextImpl.getCallerId());
            header.setCallerTid(transactionContextImpl.getCallerTid());

            header.setSessionTid(transactionContextImpl.getSessionTid());
            header.setCookie(transactionContextImpl.getCookie());
            // header.setMscTraceId(0L);
            // header.setMscSpanId(0L);

            if (!"0".equals(returnCode)) {
                // Be careful, map cannot contains null.
                Map<String, String> extFields = new HashMap<String, String>(5);
                String errorGroup = transactionContextImpl.getErrorGroup();
                if (errorGroup != null) {
                    extFields.put(EFK_ERRORGROUP, errorGroup);
                }
                String errorLevel = transactionContextImpl.getErrorLevel();
                if (errorLevel != null) {
                    extFields.put(EFK_ERRORLEVEL, errorLevel);
                }
                String errorLocation = transactionContextImpl.getErrorLocation();
                if (errorLocation != null) {
                    extFields.put(EFK_ERRORLOCATION, errorLocation);
                }
                boolean circuitBroken = transactionContextImpl.isCircuitBroken();
                extFields.put(EFK_CIRCUITBROKEN, String.valueOf(circuitBroken));
                boolean retry = transactionContextImpl.isRetry();
                extFields.put(EFK_RETRY, String.valueOf(retry));
                header.setExtFields(extFields);
            }

        }

        basicProtocol.writeByte(STX);
        basicProtocol.writeByte(protocol);
        basicProtocol.writeI32(sequence);

        HeaderHelper headerHelper = HeaderHelper.getInstance();
        headerHelper.write(header, contentProtocol);

        TransactionContextImpl transactionContext = (TransactionContextImpl) TransactionContext.Factory.getInstance();

        Header _header = transactionContext.getContextData("header", Header.class);
        Integer _headerEndIndex = transactionContext.getContextData("headerEndIndex", Integer.class);

        if (_header == null) {
            transactionContext.setContextData("header", header);
        }

        if (_headerEndIndex == null) {
            int headerEndIndex = this.trans.getOutputByteBuf().writerIndex();
            transactionContext.setContextData("headerEndIndex", headerEndIndex);
        }
    }

    @Override
    public void writeMessageEnd() throws TException {
        basicProtocol.writeByte(ETX);
    }

    @Override
    public void writeStructBegin(TStruct struct) throws TException {
        contentProtocol.writeStructBegin(struct);
    }

    @Override
    public void writeStructEnd() throws TException {
        contentProtocol.writeStructEnd();
    }

    @Override
    public void writeFieldBegin(TField field) throws TException {
        contentProtocol.writeFieldBegin(field);
    }

    @Override
    public void writeFieldEnd() throws TException {
        contentProtocol.writeFieldEnd();
    }

    @Override
    public void writeFieldStop() throws TException {
        contentProtocol.writeFieldStop();
    }

    @Override
    public void writeMapBegin(TMap map) throws TException {
        contentProtocol.writeMapBegin(map);
    }

    @Override
    public void writeMapEnd() throws TException {
        contentProtocol.writeMapEnd();
    }

    @Override
    public void writeListBegin(TList list) throws TException {
        contentProtocol.writeListBegin(list);
    }

    @Override
    public void writeListEnd() throws TException {
        contentProtocol.writeListEnd();
    }

    @Override
    public void writeSetBegin(TSet set) throws TException {
        contentProtocol.writeSetBegin(set);
    }

    @Override
    public void writeSetEnd() throws TException {
        contentProtocol.writeSetEnd();
    }

    @Override
    public void writeBool(boolean b) throws TException {
        contentProtocol.writeBool(b);
    }

    @Override
    public void writeByte(byte b) throws TException {
        contentProtocol.writeByte(b);
    }

    @Override
    public void writeI16(short i16) throws TException {
        contentProtocol.writeI16(i16);
    }

    @Override
    public void writeI32(int i32) throws TException {
        contentProtocol.writeI32(i32);
    }

    @Override
    public void writeI64(long i64) throws TException {
        contentProtocol.writeI64(i64);
    }

    @Override
    public void writeDouble(double dub) throws TException {
        contentProtocol.writeDouble(dub);
    }

    @Override
    public void writeString(String str) throws TException {
        contentProtocol.writeString(str);
    }

    @Override
    public void writeBinary(ByteBuffer buf) throws TException {
        contentProtocol.writeBinary(buf);
    }

    @Override
    public TMessage readMessageBegin() throws TException {
        boolean requestFlag = ThreadLocalProperty.getRequestFlag();

        TransactionContextImpl transactionContext = (TransactionContextImpl) TransactionContext.Factory.getInstance();
        InvocationContextImpl.InvocationInfoImpl lastInvocation = (InvocationContextImpl.InvocationInfoImpl) InvocationContext.Factory.getInstance()
                .getLastInvocation();
        lastInvocation.reset();

        basicProtocol.readI32();// 把FrameLength读走
        byte stx = basicProtocol.readByte();
        assert (stx == STX);
        byte protocol = basicProtocol.readByte();
        int sequence = basicProtocol.readI32();

        defineProtocol(protocol);

        HeaderHelper headerHelper = HeaderHelper.getInstance();
        Header header = new Header();

        headerHelper.read(header, contentProtocol);
        int headerEndIndex = this.trans.getInputByteBuf().readerIndex();

        transactionContext.setContextData("header", header);
        transactionContext.setContextData("headerEndIndex", headerEndIndex);

        // either a request for Container or a response for Consumer
        if (requestFlag) { // 读请求包
            HostAndPort callerHostAndPort = null;
            //HostAndPort remoteHostAndPort = IPUtils.getHostAndPort(transactionContext.getRemoteAddress());
            if (header.getForwardedFor() != null) {
                //callerHostAndPort = IPUtils.getHostAndPort(header.getForwardedFor());
            } else {
                //callerHostAndPort = remoteHostAndPort;
            }

            InetAddress callerIPAddr = getCallerIP(callerHostAndPort.hostAddress);

            transactionContext.setProtocol(EncodeProtocol.getProtocolByCode(protocol)).setSequence(sequence)
                    .setCallerIP(callerIPAddr)//
                    .setCallerPort(callerHostAndPort.port)//
                    .setServiceName(header.getServiceName())//
                    .setMethod(header.getMethod())//
                    .setVersion(header.getVersion())//
                    .setCallerId(header.getCallerId())//
                    .setOptions(header.getOptions() == null ? 0 : header.getOptions())
                    .setSessionTid(header.getSessionTid() == null ? 0 : header.getSessionTid())
                    .setCallerTid(header.getCallerTid() == null ? 0 : header.getCallerTid())
                    .setCalleeTid(header.getCalleeTid() == null ? 0 : header.getCalleeTid())
                    .setCookie(header.getCookie())
                    // .setUid(header.getUid() == null ? 0 : header.getUid())//
                    /*.setUip(header.getUip() != null ? IPUtils.integer2IPV4(header.getUip())
                            : remoteHostAndPort.hostAddress)*/
                    //.setForwardedFor(remoteHostAndPort.toString())
                    //.setProxyIP(header.getForwardedFor() != null ? remoteHostAndPort.hostAddress : null)// 设置proxyIP的规则，ForwardedFor!=null?Remote
                    .setUserSign(header.getCallerSign());
        } else { // 读返回包
            String returnCode = header.getRetCode();
            lastInvocation.setSequence(sequence);
            lastInvocation.setProtocol(EncodeProtocol.getProtocolByCode(protocol));
            lastInvocation.setCalleeVersion(header.getVersion());
            lastInvocation.setReturnCode(returnCode);
            lastInvocation.setReturnMessage(header.getRetMessage());
            // lastInvocation.setCalleeTime1(header.getCalleeTime1() == null ? 0 : header.getCalleeTime1());
            // lastInvocation.setCalleeTime2(header.getCalleeTime2() == null ? 0 : header.getCalleeTime2());
            lastInvocation.setCookie(header.getCookie());
            lastInvocation.setCalleeTid(header.getCalleeTid() == null ? 0 : header.getCalleeTid());
            lastInvocation.setCallerTid(header.getCallerTid() == null ? 0 : header.getCallerTid());
            lastInvocation.setSessionTid(header.getSessionTid() == null ? 0 : header.getSessionTid());
            lastInvocation.setOptions(header.getOptions() == null ? 0 : header.getOptions());
            lastInvocation.setServiceName(header.getServiceName());
            lastInvocation.setMethod(header.getMethod());
            //lastInvocation.setUip(IPUtils.integer2IPV4(header.getUip()));
            lastInvocation.setForwardedFor(header.getForwardedFor());

            Map<String, String> extFields = header.getExtFields();
            if (extFields != null && !"0".equals(returnCode)) {
                String errorGroup = extFields.get(EFK_ERRORGROUP);
                if (errorGroup != null) { // lastInvocation's errorGroup has default value.
                    lastInvocation.setErrorGroup(errorGroup);
                }
                String errorLevel = extFields.get(EFK_ERRORLEVEL);
                if (errorLevel != null) {
                    lastInvocation.setErrorLevel(errorLevel);
                }
                String errorLocation = extFields.get(EFK_ERRORLOCATION);
                if (errorLocation != null) {
                    lastInvocation.setErrorLocation(errorLocation);
                }
                String circuitBroken = extFields.get(EFK_CIRCUITBROKEN);
                if (circuitBroken != null) {
                    lastInvocation.setCircuitBroken(Boolean.parseBoolean(circuitBroken));
                }
                String retry = extFields.get(EFK_RETRY);
                if (retry != null) {
                    lastInvocation.setRetry(Boolean.parseBoolean(retry));
                }
            }
        }

        return null;
    }

    private static InetAddress getCallerIP(String callerIP) {
        InetAddress callerIPAddr = null;
        try {
            //callerIPAddr = callerIP != null ? IPUtils.getAddresses(callerIP) : IPUtils.localIp();
        } catch (Exception e) {
            logger.warn("callerIP format is worng：" + callerIP + ", use local ip instead", e);
           // callerIPAddr = IPUtils.localIp();
        }
        return callerIPAddr;
    }

    public void defineProtocol(byte protocol) {
        if (protocol == 0x00) {
            contentProtocol = this.binaryContentProtocol;
        } else if (protocol == 0x01) {
            contentProtocol = this.compactContentProtocol;
        } else if (protocol == 0x02) {
            contentProtocol = this.jsonContentProtocol;
        } else if (protocol == 0x03) {
            //contentProtocol = this.ospJsonContentProtocol;
        }
    }

    public TProtocol getContentProtocol() {
        return contentProtocol;
    }

    @Override
    public void readMessageEnd() throws TException {
        byte etx = basicProtocol.readByte();
        assert (etx == ETX);
    }

    @Override
    public TStruct readStructBegin() throws TException {
        return contentProtocol.readStructBegin();
    }

    @Override
    public void readStructEnd() throws TException {
        contentProtocol.readStructEnd();
    }

    @Override
    public TField readFieldBegin() throws TException {
        return contentProtocol.readFieldBegin();
    }

    @Override
    public void readFieldEnd() throws TException {
        contentProtocol.readFieldEnd();
    }

    @Override
    public TMap readMapBegin() throws TException {
        return contentProtocol.readMapBegin();
    }

    @Override
    public void readMapEnd() throws TException {
        contentProtocol.readMapEnd();
    }

    @Override
    public TList readListBegin() throws TException {
        return contentProtocol.readListBegin();
    }

    @Override
    public void readListEnd() throws TException {
        contentProtocol.readListEnd();
    }

    @Override
    public TSet readSetBegin() throws TException {
        return contentProtocol.readSetBegin();
    }

    @Override
    public void readSetEnd() throws TException {
        contentProtocol.readSetEnd();
    }

    @Override
    public boolean readBool() throws TException {
        return contentProtocol.readBool();
    }

    @Override
    public byte readByte() throws TException {
        return contentProtocol.readByte();
    }

    @Override
    public short readI16() throws TException {
        return contentProtocol.readI16();
    }

    @Override
    public int readI32() throws TException {
        return contentProtocol.readI32();
    }

    @Override
    public long readI64() throws TException {
        return contentProtocol.readI64();
    }

    @Override
    public double readDouble() throws TException {
        return contentProtocol.readDouble();
    }

    @Override
    public String readString() throws TException {
        return contentProtocol.readString();
    }

    @Override
    public ByteBuffer readBinary() throws TException {
        return contentProtocol.readBinary();
    }

    public void setInputByteBuf(ByteBuf inputByteBuf) {
        this.reset();
        this.trans.setInputByteBuf(inputByteBuf);
    }

    public void setOutputByteBuf(ByteBuf outputByteBuf) {
        this.reset();
        trans.setOutputByteBuf(outputByteBuf);
    }

    /**
     * <p>
     * Write args to body by helper to serialize, return ByteBuf serialized. This method does not modify readerIndex.
     * <p>
     * Also be aware that this method will NOT call {@link ByteBuf#retain()} and so the reference count will NOT be
     * increased.
     */
    public <T> ByteBuf writeAndGetBody(T args, TBaseWrapper<T, ?> helper) throws TException {
        boolean requestFlag = ThreadLocalProperty.getRequestFlag();
        byte protocol = requestFlag ? InvocationContext.Factory.getInstance().getProtocol().getCode()
                : TransactionContext.Factory.getInstance().getProtocol().getCode();
        defineProtocol(protocol);

        ByteBuf byteBuf = trans.getOutputByteBuf();

        int bodyBeginIndex = byteBuf.writerIndex();
        if (trans.isInit()) {
            bodyBeginIndex = bodyBeginIndex + 4; // exclude the length
        }

        if (helper != null) {
            helper.write(args, this);
        } else {
            return null;
        }

        return byteBuf.slice(bodyBeginIndex, byteBuf.writerIndex() - bodyBeginIndex);
    }

    /**
     * append bodyBuf directly
     */
    public void writeBodyDirectly(ByteBuf bodyBuf) {
        trans.getOutputByteBuf().writeBytes(bodyBuf, bodyBuf.readerIndex(), bodyBuf.readableBytes());
    }

    public TFramedByteBufTransport getTrans() {
        return this.trans;
    }

    @Override
    public void reset() {
        basicProtocol.reset();

        if (contentProtocol != null) {
            contentProtocol.reset();
        }

        // binaryContentProtocol.reset();
        // compactContentProtocol.reset();
        // jsonContentProtocol.reset();
        // ospJsonContentProtocol.reset();
    }

    public static class HostAndPort {
        public String hostAddress;
        public int port = -1;

        @Override
        public String toString() {
            return hostAddress + ":" + port;
        }
    }
}
