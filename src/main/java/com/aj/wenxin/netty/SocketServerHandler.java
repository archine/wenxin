package com.aj.wenxin.netty;

import com.aj.wenxin.entity.ChatRecord;
import com.aj.wenxin.enums.MsgEnum;
import com.alibaba.fastjson.JSONObject;
import com.gj.utils.GjUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        //获取客户端传输过来的消息
        String msg = textWebSocketFrame.text();
        //判断客户端传来的消息的消息类型,根据不同类型处理不同业务
        DataContent dataContent = JSONObject.parseObject(msg, DataContent.class);
        Channel channel = ctx.channel();
        int dataType = dataContent.getDataType();
        if (dataType == MsgEnum.CONNECT.getType()) {
            //当前websocket第一次open的时候.初始化了channel,把用户的channel和userId关联起来
            Long senderId = dataContent.getChatMsg().getSendUserId();
            UserChannelRelative.put(senderId, channel);
        } else if (dataType == MsgEnum.CHAT.getType()) {
            //聊天类型的消息,保存到数据库,同时标记为[未读],
            ChatMsg chatMsg = dataContent.getChatMsg();
            String message = chatMsg.getMsg();
            Long receiverId = chatMsg.getReceiverUserId();
            Long sendUserId = chatMsg.getSendUserId();
            ChatRecord chatRecord = new ChatRecord();
            chatRecord.setSignFlag(1);
            chatRecord.setSendUserId(sendUserId);
            chatRecord.setAcceptUserId(receiverId);
            chatRecord.setCharContent(message);
            chatRecord.insert();
            //发送消息
            Channel receiverChannel = UserChannelRelative.get(receiverId);
            if (receiverChannel == null) {
                //用户离线,可以采取推送
            } else {
                Channel channel1 = users.find(receiverChannel.id());
                if (channel1 != null) {
                    //用户在线
                    receiverChannel.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSON(chatMsg).toString()));
                } else {
                    // TODO: 2019-03-17 用户离线,进行推送
                }
            }
        } else if (dataType == MsgEnum.SIGNED.getType()) {
            //签收消息类型,针对具体的消息进行签收,修改数据库对应的聊天记录为[已读]
            String[] otherContent = GjUtil.spilt(dataContent.getOtherContent(), ",");
            List<String> msgIdList = Arrays.stream(otherContent).collect(Collectors.toList());
            if (!msgIdList.isEmpty()) {
                //批量签收
                for (String s : msgIdList) {
                    ChatRecord chatRecord = new ChatRecord();
                    chatRecord.setSignFlag(2);
                    chatRecord.setId(Long.valueOf(s));
                    chatRecord.updateById();
                }
            }
        } else if (dataType == MsgEnum.KEEP_ALLIVE.getType()) {

        }

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //客户端连接服务端之后, 获取客户端的channel并且放到channelGroup中进行统一管理
        Channel channel = ctx.channel();
        users.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //当触发handlerRemoved,channelGroup会自动移除对应客户端的channel
        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //发生异常之后,关闭连接(channel),随后从channelGroup中移除
        ctx.channel().close();
        users.remove(ctx.channel());
    }
}
