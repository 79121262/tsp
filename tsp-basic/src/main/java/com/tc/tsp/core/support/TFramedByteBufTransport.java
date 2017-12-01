package com.tc.tsp.core.support;

import io.netty.buffer.ByteBuf;
import io.netty.util.concurrent.FastThreadLocal;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.nio.ByteOrder;

/**
 * wrapper a Netty's ByteBuf to a Transport, avoid duplicate allocation
 * Created by cai.tian on 2017/11/30.
 */
public final class TFramedByteBufTransport extends TTransport {

    private static final int INIT = 0;
    private static final int WRITING_FRAME = 2;

    private ByteBuf inputByteBuf;
    private ByteBuf outputByteBuf;
    private int initWriterIndex;
    private int writeStatus;
    private int writeLength;// 不包含length自身4字节的frameLength

    static FastThreadLocal<TFramedByteBufTransport> serviceTrasport4Client = new FastThreadLocal<TFramedByteBufTransport>() {

        @Override
        protected TFramedByteBufTransport initialValue() {
            return new TFramedByteBufTransport();
        }
    };

    static FastThreadLocal<TFramedByteBufTransport> serviceTrasport4Server = new FastThreadLocal<TFramedByteBufTransport>() {

        @Override
        protected TFramedByteBufTransport initialValue() {
            return new TFramedByteBufTransport();
        }
    };

    public static TFramedByteBufTransport getTFramedByteBufTransport4Client() {
        return serviceTrasport4Client.get();
    }

    public static TFramedByteBufTransport getTFramedByteBufTransport4Server() {
        return serviceTrasport4Server.get();
    }

    private TFramedByteBufTransport() {
    }

    public void setInputByteBuf(ByteBuf inputByteBuf) {
        this.inputByteBuf = inputByteBuf.order(ByteOrder.BIG_ENDIAN);
    }

    public void setOutputByteBuf(ByteBuf outputByteBuf) {
        this.outputByteBuf = outputByteBuf.order(ByteOrder.BIG_ENDIAN);
        this.initWriterIndex = outputByteBuf.writerIndex();
        this.writeStatus = INIT;
    }

    public int getWriteIndex() {
        return outputByteBuf.writerIndex();
    }

    public void writerIndex(int index) {
        outputByteBuf.writerIndex(index);
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public void open() throws TTransportException {
    }

    @Override
    public void close() {
    }

    @Override
    public int read(byte[] buf, int off, int len) throws TTransportException {
        if (len < 0) {
            throw new IllegalArgumentException();
        }

        int readable = Math.min(inputByteBuf.readableBytes(), len);
        inputByteBuf.readBytes(buf, off, readable);
        return readable;
    }

    @Override
    public void write(byte[] buf, int off, int len) throws TTransportException {
        if (writeStatus == INIT) {
            // 空白占位length字段
            outputByteBuf.writeInt(0);
            writeStatus = WRITING_FRAME;
        }

        if (writeStatus != WRITING_FRAME) {
            throw new TTransportException("try to write to readonly transport");
        }

        outputByteBuf.writeBytes(buf, off, len);
    }

    /**
     * flush时写入Frame Length.
     */
    @Override
    public void flush() throws TTransportException {
        if (writeStatus == INIT) {
            outputByteBuf.writeInt(0);
            writeStatus = WRITING_FRAME;
        }

        if (writeStatus != WRITING_FRAME) {
            throw new TTransportException("try to flush a readonly transport");
        }

        int currentWriteIndex = outputByteBuf.writerIndex();
        writeLength = currentWriteIndex - initWriterIndex - 4;
        outputByteBuf.setInt(initWriterIndex, writeLength);
    }

    public ByteBuf getInputByteBuf() {
        return inputByteBuf;
    }

    public ByteBuf getOutputByteBuf() {
        return outputByteBuf;
    }

    public int getWriteLength() {
        return writeLength;
    }

    public boolean isInit() {
        return writeStatus == INIT;
    }
}
