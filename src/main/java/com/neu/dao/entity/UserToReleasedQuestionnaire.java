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
@TableName("user_releasedQuestionnaire")
public class UserToReleasedQuestionnaire {
    String userId;
    String questionnaireId;
}
