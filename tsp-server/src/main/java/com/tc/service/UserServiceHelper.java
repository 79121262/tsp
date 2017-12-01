package com.tc.service;

import com.tc.tsp.core.base.MethodDispatcher;
import com.tc.tsp.core.processor.TspProcessor;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by cai.tian on 2017/11/30.
 */
public class UserServiceHelper {

    public static class Processor <I extends UserService> extends TspProcessor<I> implements
            org.apache.thrift.TProcessor {

        private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class.getName());

        public Processor(I iface) {

            //super(iface, getProcessMap(new HashMap<String, MethodDispatcher>()));
        }


        private static Map<String, MethodDispatcher> getProcessMap(Map<String, MethodDispatcher> processMap) {


            return processMap;
        }



        public String getDomain(){

            return "osp-mim.bfintra.com";
        }


        @Override
        public boolean process(TProtocol in, TProtocol out) throws TException {

            return false;
        }
    }
}
