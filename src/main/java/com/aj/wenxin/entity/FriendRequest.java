package com.aj.wenxin.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author colin
 * @date 2019-03-11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "friend_request",indexes = {@Index(name = "谁的好友",columnList = "accept_user_id")})
@Builder
public class FriendRequest extends BaseModel {
    @Column(name = "send_user_id", columnDefinition = "bigint not null comment '发送请求方'")
    private Long sendUserId;
    @Column(name = "accept_user_id", columnDefinition = "bigint not null comment '接收请求方'")
    private Long acceptUserId;
}
