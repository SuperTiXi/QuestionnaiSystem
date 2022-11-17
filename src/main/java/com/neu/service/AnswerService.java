package com.neu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.bean.HttpResponseEntity;
import com.neu.dao.entity.Answer;
import org.springframework.stereotype.Service;


public interface AnswerService extends IService<Answer> {
    HttpResponseEntity addAnswer(Answer answer);

    HttpResponseEntity addGroupToQuestionnaire(String groupId, String questionnaireId);

    HttpResponseEntity getAnswerersCount(String questionnaireId);
}
