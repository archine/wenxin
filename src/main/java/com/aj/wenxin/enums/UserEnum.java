package com.aj.wenxin.enums;

import lombok.Getter;

/**
 * @author Archine
 **/
@Getter
public enum UserEnum {
    /**
     * 用户枚举
     */
    IS_ME("不能添加自己为好友"),
    NO_EXIST("搜索的用户不存在"),
    ALREADY_FRIEND("对方已经是您的好友");

    private String tip;

    UserEnum(String tip) {
        this.tip = tip;
    }}
