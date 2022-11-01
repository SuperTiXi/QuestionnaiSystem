package com.neu.dao.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
/**
 * 已发布的问卷
 */
public class ReleasedQuestionnaire {

    private String questionnaireId;

    /**
     * 格式为 yyyy:MM:dd HH:mm:ss
     */
    private String releasedTime;

    /**
     * 格式为 yyyy:MM:dd HH:mm:ss
     */
    private String finishedTime;

    private String style;

    private String type;

    /**
     * 答卷数量
     */
    private int answers;

    /**
     * 答卷网址
     */
    private String url;

    private int state;

    private String info;

    /**
     * 目标答者
     */
    private String target;

    private String tenantId;
}
