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
@Builder
@Table(name = "my_friend",indexes = {@Index(name = "谁的好友",columnList = "me_id")})
public class MyFriend extends BaseModel {
    @Column(name = "me_id", columnDefinition = "bigint not null comment '自己的id'")
    private Long meId;
    @Column(name = "friend_id", columnDefinition = "bigint not null comment '好友的id'")
    private Long friendId;
}
