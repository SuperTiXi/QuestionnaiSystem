package com.neu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.bean.HttpResponseEntity;
import com.neu.dao.entity.ReleasedQuestionnaire;

public interface ReleasedQuestionnaireService extends IService<ReleasedQuestionnaire> {
    HttpResponseEntity getReleasedList(String userId);

    HttpResponseEntity queryQuestionnaireById(String id);

    HttpResponseEntity getAnswersByQuestionnaire(String id);

}
