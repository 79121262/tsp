package com.tc.tsp.core.context;

import io.netty.util.concurrent.FastThreadLocal;

/**
 * 记录是否请求包的ThreadLocal类. true表示client-->server false相反 相对proxy而言
 *
 * 该标志用于OspProtocol读写消息时与TransactionContext的交互
 * Created by cai.tian on 2017/11/30.
 */
public class ThreadLocalProperty {
    private static final FastThreadLocal<Boolean> REQUEST_FLAG = new FastThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    };

    public static boolean getRequestFlag() {
        return REQUEST_FLAG.get();
    }

    public static void setRequestFlag(Boolean newValue) {
        REQUEST_FLAG.set(newValue);
    }
}
