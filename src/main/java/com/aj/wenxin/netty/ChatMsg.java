package com.aj.wenxin.netty;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Archine
 **/
@Getter
@Setter
public class ChatMsg implements Serializable {
    private static final long serialVersionUID = 2821121492119158093L;
    /**
     * 发送者id
     */
    private Long sendUserId;
    /**
     * 接受者id
     */
    private Long receiverUserId;
    /**
     * 消息内容
     */
    private String msg;
    /**
     * 消息id
     */
    private Long msgId;
}
