package com.aj.wenxin.netty;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Archine
 **/
@Getter
@Setter
public class DataContent implements Serializable {
    private static final long serialVersionUID = -3792439032447163630L;
    /**
     * 消息类型
     */
    private Integer dataType;
    /**
     * 消息
     */
    private ChatMsg chatMsg;

    /**
     * 额外的内容
     */
    private String otherContent;

}
