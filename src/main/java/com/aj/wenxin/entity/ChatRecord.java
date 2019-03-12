package com.aj.wenxin.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author colin
 * @date 2019-03-11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "chat_record")
@Builder
public class ChatRecord extends BaseModel{
    @Column(name = "send_user_id", columnDefinition = "bigint not null comment '发送者id'")
    private Long sendUserId;
    @Column(name = "accept_user_id", columnDefinition = "bigint not null comment '接收者id'")
    private Long acceptUserId;
    @Column(name = "chat_content", columnDefinition = "varchar(200) not null comment '聊天内容'")
    private String charContent;
    @Column(name = "sign_flag", columnDefinition = "tinyint(1) default 1 comment '是否已读:1未读,2已读'")
    private Integer signFlag;
}
