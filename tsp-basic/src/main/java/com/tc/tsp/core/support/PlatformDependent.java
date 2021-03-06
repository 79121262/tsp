package com.tc.tsp.core.support;

import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.LongCounter;
import io.netty.util.internal.ThreadLocalRandom;

import java.util.concurrent.ConcurrentMap;

/**
 * Created by cai.tian on 2017/12/4.
 */
public class PlatformDependent {

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap() {
        return io.netty.util.internal.PlatformDependent.newConcurrentHashMap();
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int initialCapacity) {
        return io.netty.util.internal.PlatformDependent.newConcurrentHashMap(initialCapacity);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int initialCapacity, float loadFactor,
                                                                  int concurrencyLevel) {
        return io.netty.util.internal.PlatformDependent.newConcurrentHashMap(initialCapacity, loadFactor,
                concurrencyLevel);
    }

    public static LongCounter newLongCounter() {
        return io.netty.util.internal.PlatformDependent.newLongCounter();
    }

    public static StringBuilder stringBuilder() {
        return InternalThreadLocalMap.get().stringBuilder();
    }

    public static ThreadLocalRandom threadLocalRandom() {
        return InternalThreadLocalMap.get().random();
    }

   /* public static CachingDateFormatter dateFormatter(String pattern) {
        return new CachingDateFormatter(pattern);
    }*/
}
