package com.neu.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Questionnaire {
    private String id;

    private String name;

    /**
     * 格式为 yyyy:MM:dd HH:mm:ss
     */
    private String createTime;

    /**
     * 问题类型：诸如数学，社会，政治之类
     */
    private String type;

    private String info;

    private String creatorId;

    /**
     * 属于哪个租户
     */
    private String tenantId;

    /**
     * 是否高质量问卷
     * 1:高质量
     * 0:非高质量
     */
    private int highQuality;

    /**
     * 是否发布
     * 0：未发布
     * 1：已发布
     */
    private int state;
}
