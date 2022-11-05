package com.neu.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_group")
/**
 * 租户下的用户分组
 */
public class Group {

    private String id;

    private String name;

    private String description;

    /**
     * 格式为 yyyy:MM:dd HH:mm:ss
     */
    private String createTime;

    /**
     * 所属的租户
     */
    private String tenantId;

    /**
     * 0:停用
     * 1:使用中
     */
    private int state;
}
