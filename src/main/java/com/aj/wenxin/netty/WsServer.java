package com.aj.wenxin.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author colin
 * @date 2019-03-11
 **/
@Component
@Slf4j
public class WsServer {
    private static class SingleServer {
        static final WsServer WS_SERVER = new WsServer();
    }

    /**
     * 获取ws实例
     *
     * @return 实例对象
     */
    public static WsServer getInstance() {
        return SingleServer.WS_SERVER;
    }

    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private ServerBootstrap bootstrap;
    private ChannelFuture future;

    public WsServer() {
        bossGroup = new NioEventLoopGroup();
        workGroup = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SocketServerInitializer());
    }

    public void start() {
        this.future = bootstrap.bind(8090);
        log.info("-----------------netty启动成功------------------");
    }
}
