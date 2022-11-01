package com.neu.dao.entity;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Account {

    private String id;

    private String name;

    private String password;

    private int identity;

    private String phone;

    private String info;

    private int state;
}
