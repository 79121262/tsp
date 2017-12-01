package com.tc.tsp.core.context;

/**
 * 序列化方式的枚举
 * Created by cai.tian on 2017/11/30.
 */
public enum EncodeProtocol {

    Binary((byte) 0), CompactBinary((byte) 1), JSON((byte) 2), OspJson((byte) 3);

    private static EncodeProtocol[] values = EncodeProtocol.values();

    private byte code;

    private EncodeProtocol(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public static EncodeProtocol getProtocolByCode(byte code) {
        for (EncodeProtocol ep : values) {
            if (ep.getCode() == code) {
                return ep;
            }
        }
        return null;
    }
}
