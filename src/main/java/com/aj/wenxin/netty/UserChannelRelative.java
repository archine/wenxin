package com.aj.wenxin.netty;

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * @author Archine
 **/
public class UserChannelRelative {
    private static HashMap<Long, Object> map = new HashMap<>();

    public static void put(Long sendUserId, Channel channel) {
        map.put(sendUserId, channel);
    }

    public static Channel get(Long sendUserId) {
        return (Channel) map.get(sendUserId);
    }
}
