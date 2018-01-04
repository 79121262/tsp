package com.tc.tsp.test;

import com.tc.tsp.core.base.TBaseWrapper;
import com.tc.tsp.core.context.ThreadLocalProperty;
import com.tc.tsp.core.protocol.TspProtocol;
import com.tc.tsp.core.support.ByteBufManager;
import io.netty.buffer.ByteBuf;
import org.apache.thrift.protocol.TMessage;
import org.apache.thrift.protocol.TMessageType;

/**
 * Created by cai.tian on 2017/12/4.
 */
public class ClientSendMessageFilter {

    public <T> void sendMessage(T args, TBaseWrapper<T, ?> helper){


    }

    public  <T> ByteBuf prepareRequestByteBuf(String serviceName, T args, TBaseWrapper<T, ?> helper) throws Exception{
        TspProtocol oprot = TspProtocol.Factory.getOspProtocol4Client();
        ByteBuf requestByteBuf = ByteBufManager.directBuffer(ByteBufManager.DEFAULT_BUF_SIZE);;
        ByteBuf bodyBuf = serializeArgs(args,helper);
        ThreadLocalProperty.setRequestFlag(true);
        oprot.setOutputByteBuf(requestByteBuf);
        oprot.writeMessageBegin(new TMessage(serviceName, TMessageType.CALL, 0)); // 在这里write header
        oprot.writeBodyDirectly(bodyBuf);
        //helper.write(args,oprot);

        oprot.writeMessageEnd(); // write the ending byte(0x03)
        oprot.getTransport().flush(); // correct length
        return requestByteBuf;

    }


    private static <T> ByteBuf serializeArgs(T args, TBaseWrapper<T, ?> helper) throws Exception {
        ByteBuf byteBuf = null;
        ByteBuf bodyBuf = null;
        try {
            ThreadLocalProperty.setRequestFlag(Boolean.TRUE);
            byteBuf = ByteBufManager.directBuffer(ByteBufManager.DEFAULT_BUF_SIZE);
            TspProtocol oprot = TspProtocol.Factory.getOspProtocol4Client();
            oprot.setOutputByteBuf(byteBuf);
            bodyBuf = oprot.writeAndGetBody(args, helper);
            return bodyBuf;
        } catch (Exception e) { // fix, if OspException, release bodyBuf and throw it directly.
            ByteBufManager.release(byteBuf);
            throw e;
        }finally {
            ThreadLocalProperty.setRequestFlag(false);
        }
    }



}
