package com.neu.dao.entity;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Account {

    private String id;

    /**
       用来登录的用户名
     */
    private String userName;


    private String name;

    private String password;

    /**
     * 该账户身份
     * 0：管理员
     * 1：租户
     * 2：用户
     * 3：答者
     */
    private int identity;

    /**
     * 用来登录的手机号
     */
    private String phone;

    private String info;


    /**
     * 账号状态
     * 0：停用
     * 1：使用中
     */
    private int state;
}
