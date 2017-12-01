package com.tc.container.connection;

import com.alibaba.fastjson.JSON;
import com.tc.tsp.core.processor.TspUniteProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by cai.tian on 2017/11/30.
 */
public class ContainerConnectionHandler extends ChannelInboundHandlerAdapter {

    private static ContainerConnectionHandler instance;

    private ContainerConnectionHandler(TspUniteProcessor uniteProcessor, int port, boolean sslFlag) {
        super();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println(JSON.toJSONString(msg));
    }

    /**
     * 创建一个新的实例
     */
    public static ContainerConnectionHandler create(TspUniteProcessor uniteProcessor, int port, boolean sslFlag) {
        instance = new ContainerConnectionHandler(uniteProcessor, port, sslFlag);
        return instance;
    }
}
