package com.aj.wenxin.enums;

import lombok.Getter;

/**
 * @author Archine
 **/
@Getter
public enum MsgEnum {
    /**
     * 消息类型枚举
     */
    CONNECT(1, "初始化连接"),
    CHAT(2, "聊天"),
    SIGNED(3,"消息签收"),
    KEEP_ALLIVE(4, "心跳");
    private Integer type;
    private String tip;

    MsgEnum(Integer type, String tip) {
        this.type = type;
        this.tip = tip;
    }}
