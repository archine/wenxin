package com.aj.wenxin.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * @author colin
 * @date 2019-03-10
 * 处理消息的handler
 * <p>
 * 'TextWebSocketFrame' 在netty中,用于websocket处理文本的对象,frame是消息的载体
 **/
public class SocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 用于管理所有客户端channel
     */
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        //获取客户端传输过来的消息
        final String clientMsg = textWebSocketFrame.text();
        System.out.println("客户端说: " + clientMsg);
        clients.writeAndFlush(new TextWebSocketFrame("当前时间: " + LocalDateTime.now() + "接收到消息: " + clientMsg));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //客户端连接服务端之后, 获取客户端的channel并且放到channelGroup中进行统一管理
        Channel channel = ctx.channel();
        clients.add(channel);
        System.out.println("新的客户端连接: "+channel.id().asLongText());
        System.out.println("客户端数量: "+clients.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //触发handlerRemoved方法时, 会自动的移除掉
        Channel channel = ctx.channel();
//        clients.remove(channel);
        System.out.println("客户端ID: " + channel.id().asLongText() + "断开连接");
    }
}
