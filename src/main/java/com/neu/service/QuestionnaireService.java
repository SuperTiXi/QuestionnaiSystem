package com.neu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.bean.HttpResponseEntity;
import com.neu.dao.entity.Questionnaire;
import com.neu.dao.entity.ReleasedQuestionnaire;

import java.util.List;
import java.util.Map;

public interface QuestionnaireService extends IService<Questionnaire> {
    HttpResponseEntity createQuestionnaire(Questionnaire questionnaire);

    HttpResponseEntity findQuestionnaire(Map<String, String> map);

    HttpResponseEntity containQuestions(String info);

    HttpResponseEntity modifyQuestionnaire(Questionnaire questionnaire);
    HttpResponseEntity deleteQuestionnaires(List<String> ids);

    HttpResponseEntity release(ReleasedQuestionnaire releasedQuestionnaire);
}
