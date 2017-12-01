package com.tc.tsp.core.processor;

import org.apache.thrift.protocol.TProtocol;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cai.tian on 2017/11/30.
 */
public class TspUniteProcessor {

    // key: serviceName, 由接口类的包名＋类名组成.
    private Map<String, TspProcessor> processorMap = new HashMap<String, TspProcessor>();

    /**
     * 把从spring中获得的所有processor逐个发布服务到注册中心，并保存为map
     */
    public TspUniteProcessor(Map<String, TspProcessor> processorMap) throws Exception {
        for (Map.Entry<String, TspProcessor> entry : processorMap.entrySet()) {
            TspProcessor ospProcessor = entry.getValue();
            ospProcessor.publishService();
            Class<?> ifaceClass = ospProcessor.getiClass();
            this.processorMap.put(ifaceClass.getName(), ospProcessor);
        }
    }

    /**
     * 从TransactionContext中得到服务名，调用相应的OspProcessor处理. 所有异常将转换为OspException
     */
    public void process(TProtocol in, TProtocol out) throws Exception {

        //String serviceName = TransactionContext.Factory.getInstance().getServiceName();

        TspProcessor tspProcessor = processorMap.get("");


        tspProcessor.process(in, out);
    }

    public void addProcessor(String key,TspProcessor ospProcessor){
        processorMap.put(key, ospProcessor);
    }

    public Map<String, TspProcessor> getRegisteredProcessorMap() {
        return processorMap;
    }
}
