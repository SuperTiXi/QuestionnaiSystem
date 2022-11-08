package com.neu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.bean.HttpResponseEntity;
import com.neu.dao.entity.Question;

import java.util.Map;

public interface QuestionService extends IService<Question> {
    HttpResponseEntity addQuestion(Question question);

    HttpResponseEntity deleteQuestion(String id);
    HttpResponseEntity queryQuestions(Map<String, String> map);

    HttpResponseEntity modifyQuestion(Question question);
}
