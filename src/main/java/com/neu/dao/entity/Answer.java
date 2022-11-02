package com.neu.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
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
