package com.aj.wenxin.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author colin
 * @date 2019-03-10
 *  继承ChannelInboundHandlerAdapter从而无需实现reader0方法, 该类用于检测channel的心跳
 **/
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //判断事件类型是否为IdleStateEvent(用于触发用户事件,包含读空闲/写空闲/读写空闲)
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("进入读空闲");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                System.out.println("进入写空闲");
            } else if (event.state() == IdleState.ALL_IDLE) {
                System.out.println("进入读写空闲");
                Channel channel = ctx.channel();
                //关闭无用的channel
                channel.close();
                System.out.println("channel还有数量: " + SocketServerHandler.clients.size());
            }
        }
    }
}
