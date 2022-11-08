package com.neu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.bean.HttpResponseEntity;
import com.neu.common.utils.CommonUtils;
import com.neu.common.utils.UUIDUtil;
import com.neu.dao.QuestionMapper;
import com.neu.dao.entity.Question;
import com.neu.dao.entity.Questionnaire;
import com.neu.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.neu.common.Constants.*;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper,Question> implements QuestionService {
    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionMapper questionMapper;
    final String[] cols = {"type", "user_id"};
    @Override
    public HttpResponseEntity saveOrUpdateQuestion(Question question) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        String id = question.getId(),user_id = question.getUserId(), info = question.getInfo(),type = question.getType();
        if (CommonUtils.stringIsEmpty(user_id)||CommonUtils.stringIsEmpty(info)||CommonUtils.stringIsEmpty(type)) {
            httpResponseEntity.setCode(INSERT_FAIL_CODE);
            httpResponseEntity.setMessage(EMPTY_ERROR);
            return httpResponseEntity;
        }
        if (CommonUtils.stringIsEmpty(id)) {
            id = UUIDUtil.getOneUUID();
        }
        try {
            saveOrUpdate(question);
            httpResponseEntity.setCode(INSERT_SUCCESS_CODE);
            httpResponseEntity.setMessage(INSERT_SUCCESS_MESSAGE);
        } catch (Exception e) {
            httpResponseEntity.setCode(INSERT_FAIL_CODE);
            httpResponseEntity.setMessage(INSERT_FAIL_MESSAGE);
            throw new RuntimeException(e);
        }

        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity deleteQuestion(String id) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if (CommonUtils.stringIsEmpty(id)) {
            httpResponseEntity.setCode(DELETE_FAIL_CODE);
            httpResponseEntity.setMessage(EMPTY_ERROR);
            return httpResponseEntity;
        }

        try {
            removeById(id);
            httpResponseEntity.setCode(DELETE_SUCCESS_CODE);
            httpResponseEntity.setMessage(DELETE_SUCCESS_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            httpResponseEntity.setCode(DELETE_FAIL_CODE);
            httpResponseEntity.setMessage(DELETE_FAIL_MESSAGE);
        }
        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity queryQuestions(Map<String, String> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        for (String way : cols) {
            if (map.containsKey(way)) {
                wrapper.eq(way, map.get(way));
            }
        }

        List<Question> list = questionMapper.selectList(wrapper);
        if (list==null) {
            httpResponseEntity.setCode(QUERY_FAIL_CODE);
            httpResponseEntity.setMessage(QUERY_FAIL_MESSAGE);
            return httpResponseEntity;
        }

        httpResponseEntity.setCode(QUERY_SUCCESS_CODE);
        httpResponseEntity.setMessage(QUERY_SUCCESS_MESSAGE);
        httpResponseEntity.setData(list);
        return httpResponseEntity;
    }

}
