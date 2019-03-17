package com.aj.wenxin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author colin
 * @date 2019-03-11
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user")
public class User extends BaseModel {
    @Column(name = "user_name", columnDefinition = "varchar(20) not null comment '用户名'")
    private String userName;
    @Column(name = "password", columnDefinition = "varchar(100) not null comment '密码'")
    private String password;
    @Column(name = "face_img", columnDefinition = "varchar(150) comment '用户头像'")
    private String faceImg;
    @Column(name = "face_img_big", columnDefinition = "varchar(150) comment '用户大头像'")
    private String faceImgBig;
    @Column(name = "qr_code", columnDefinition = "varchar(150) comment '用户二维码'")
    private String qrCode;
    @Column(name = "client_id", columnDefinition = "varchar(100) comment '客户端唯一标识符'")
    private String clientId;
}
