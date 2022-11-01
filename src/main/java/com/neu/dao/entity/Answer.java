package com.neu.dao.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data

/**
 * 答卷信息
 */
public class Answer {

    private String id;

    private String questionnaireId;

    private String accountId;

    private String info;

    private String createTime;
}
