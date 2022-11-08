package com.neu.dao.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.neu.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import static com.neu.common.Constants.*;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Charging {
    @TableId
    private String tenantId;
    private int groupCount;
    private int questionnaireCount;
    private int answerCount;
    private double balance;
    private double requiredMoney;

    public void generateRequiredMoney(){
        this.requiredMoney =
                groupCount * EXPENSE_PER_GROUP
                + questionnaireCount * EXPENSE_PER_QUESTIONNAIRE
                + answerCount * EXPENSE_PER_ANSWER;

    }
}
