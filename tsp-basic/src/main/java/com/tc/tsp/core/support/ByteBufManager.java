package com.tc.tsp.core.support;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;

/**
 * Created by cai.tian on 2017/12/4.
 */
public class ByteBufManager {
    public static final int DEFAULT_BUF_SIZE = 8192;
    public static ByteBufAllocator byteBufAllocator = PooledByteBufAllocator.DEFAULT;
    /**
     * 克隆ByteBuf，共享同一段ByteBuf，引用数加1
     */
    public static ByteBuf duplicateByteBuf(ByteBuf oldBuf) {
        ByteBuf newBuf = oldBuf.duplicate();
        newBuf.retain();
        return newBuf;
    }

    /**
     * 分配pooled direct ByteBuf
     */
    public static ByteBuf directBuffer(int initialCapacity) {
        return byteBufAllocator.directBuffer(initialCapacity);
    }


    /**
     * 安全释放ByteBuf
     */
    public static void release(ByteBuf buf) {
        if (buf != null && buf.refCnt() > 0) {
            buf.release();
        }
    }
}
