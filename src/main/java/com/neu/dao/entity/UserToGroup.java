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
@TableName("user_group")
public class UserToGroup {

    private String userId;
    private String groupId;
    /**
     * 1.可用
     * 0.不可用
     */
    private int valid;
}
