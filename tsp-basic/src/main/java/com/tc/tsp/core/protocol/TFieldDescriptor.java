package com.tc.tsp.core.protocol;

import org.apache.thrift.protocol.TField;

/**
 * Created by cai.tian on 2017/11/30.
 */
public final class TFieldDescriptor extends TField {

    public final boolean optional;

    public TFieldDescriptor(String name, byte type, short id, boolean optional) {
        super(name, type, id);
        this.optional = optional;
    }

}