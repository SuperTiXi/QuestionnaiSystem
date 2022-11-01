package com.neu.dao.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Question {

    private String id;

    /**
     * 问题由谁创建
     */
    private String userId;

    /**
     * 问题信息
     */
    private String info;

    /**
     * 问题类型：诸如数学，社会，政治之类
     */
    private String type;
}
