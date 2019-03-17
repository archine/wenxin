package com.aj.wenxin.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author colin
 * @date 2019-03-10
 **/
public class SocketServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // ------------------------以下是支持http协议-----------------------
        //http请求编解码器
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        //进行大数据流的写入
        pipeline.addLast("chunk",new ChunkedWriteHandler());
        //对httpMessage进行聚合,聚合成FullHttpRequest或FullHttpResponse,几乎在netty编程中,都会用到这个
        pipeline.addLast("aggregator",new HttpObjectAggregator(1024 * 64));
        //--------------------------以下是webSocket协议----------------------
        // 指定客户端连接访问的路由,该handler主要处理closing、ping、pong,对于websocket来讲,传输都是通过frames来传输,不同数据类型
        //frames也不同
        pipeline.addLast("websocketProtocol",new WebSocketServerProtocolHandler("/ws"));
        //自定义handler
        pipeline.addLast("customerHandler", new SocketServerHandler());
        pipeline.addLast("idleStateHandler", new IdleStateHandler(2, 4, 6));
        //自定义空闲状态监测
        pipeline.addLast("heartBeatHandler", new HeartBeatHandler());
    }
}
