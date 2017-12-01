package com.tc.container.connection;

import com.tc.tsp.core.netty.client.TspMessageDecoder;
import com.tc.tsp.core.processor.TspUniteProcessor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.util.concurrent.DefaultThreadFactory;

/**
 * Created by cai.tian on 2017/11/30.
 */
public class ContainerServer {
    // 服务端 Netty boss group大小
    private static int bossGroupSize = 2;
    // 服务端 Netty worker group大小
    private static int workerGroupSize = 3;

    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;

    public ContainerServer() {
    }

    /**
     * @param port           监听端口号
     * @param sslFlag        是否为SSL安全通道
     * @param uniteProcessor
     */
    public void startServer(int port, final boolean sslFlag, final TspUniteProcessor uniteProcessor) throws Exception {
        // Configure the server.
        bossGroup = new NioEventLoopGroup(bossGroupSize, new DefaultThreadFactory("OSP-Server-Boss", true)); // IO监听线程池
        workerGroup = new NioEventLoopGroup(workerGroupSize, new DefaultThreadFactory("OSP-Server-Worker", true)); // worker工作线程池


        ContainerConnectionHandler containerConnectionHandler = ContainerConnectionHandler.create(uniteProcessor, port, false);

        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);//
        bootstrap.option(ChannelOption.SO_REUSEADDR, Boolean.TRUE).option(ChannelOption.ALLOCATOR,
                PooledByteBufAllocator.DEFAULT);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new TspMessageDecoder());
                pipeline.addLast(containerConnectionHandler);
            }
        });

        // Start the server.
        ChannelFuture future = null;
        try {
            future = bootstrap.bind(port).sync();
        } catch (Exception e) {
            e.printStackTrace();// NOSONAR
        }

        // Block to wait until the server socket is closed.
        if (future != null) {
            future.channel().closeFuture().sync();
        }
        System.out.println("----");

    }
}
