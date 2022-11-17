package com.neu.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.bean.HttpResponseEntity;
import com.neu.common.Constants;
import com.neu.common.utils.UUIDUtil;
import com.neu.dao.QuestionMapper;
import com.neu.dao.QuestionnaireMapper;
import com.neu.dao.TenantToUserMapper;
import com.neu.dao.entity.Group;
import com.neu.dao.entity.Question;
import com.neu.dao.entity.Questionnaire;
import com.neu.service.QuestionService;
import com.neu.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.neu.common.Constants.*;

@Service
public class QuestionnaireServiceImpl extends ServiceImpl<QuestionnaireMapper, Questionnaire> implements QuestionnaireService {

    @Autowired
    QuestionnaireMapper questionnaireMapper;
    @Autowired
    QuestionService questionService;

    @Autowired
    TenantToUserMapper tenantToUserMapper;
    String[] ways = {"id", "name","type", "creator_id", "tenant_id","high_quality","state"};
    @Override
    public HttpResponseEntity createQuestionnaire(Questionnaire questionnaire) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        String tenantId = tenantToUserMapper.queryTenantByUser(questionnaire.getCreatorId());
        questionnaire.setTenantId(tenantId);
        questionnaire.setState(0);
        questionnaire.setId(UUIDUtil.getOneUUID());
        questionnaire.setCreateTime(DateUtil.now());

        try {
            for (Field field : questionnaire.getClass().getFields()) {
                field.setAccessible(true);
                Object obj = field.get(questionnaire);
                if (obj==null) {
                    httpResponseEntity.setCode(INSERT_FAIL_CODE);
                    httpResponseEntity.setMessage(EMPTY_ERROR);
                    return httpResponseEntity;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }


        try {
            save(questionnaire);
            httpResponseEntity.setCode(INSERT_SUCCESS_CODE);
            httpResponseEntity.setMessage(INSERT_SUCCESS_MESSAGE);
        } catch (Exception e) {
            httpResponseEntity.setCode(INSERT_FAIL_CODE);
            httpResponseEntity.setMessage(INSERT_FAIL_MESSAGE);
        }
        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity findQuestionnaire(Map<String, String> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        QueryWrapper<Questionnaire> wrapper = new QueryWrapper<>();

        for (String way : ways) {
            if (map.containsKey(way)) {
                wrapper.eq(way, map.get(way));
            }
        }
        List<Questionnaire> list = questionnaireMapper.selectList(wrapper);

        if (list.size()==0) {
            httpResponseEntity.setCode(QUERY_FAIL_CODE);
            httpResponseEntity.setMessage(QUERY_FAIL_MESSAGE);
        }else {
            httpResponseEntity.setCode(QUERY_SUCCESS_CODE);
            httpResponseEntity.setMessage(QUERY_SUCCESS_MESSAGE);
            httpResponseEntity.setData(list);
        }

        return httpResponseEntity;
    }
    @Override
    public HttpResponseEntity containQuestions(String info) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        String[] questionIds = info.split(",");
        if (questionIds.length==0) {
            httpResponseEntity.setCode(QUERY_FAIL_CODE);
            httpResponseEntity.setMessage(EMPTY_ERROR);
            return httpResponseEntity;
        }
        List<Question> questionList = new ArrayList<>();
        for (String id : questionIds) {
            Question question = questionService.query().eq("id", id).one();
            if (question==null) {
                continue;
            }
            questionList.add(question);
        }

        httpResponseEntity.setCode(QUERY_SUCCESS_CODE);
        httpResponseEntity.setMessage(QUERY_SUCCESS_MESSAGE);
        httpResponseEntity.setData(questionList);
        return httpResponseEntity;
    }


    @Override
    public HttpResponseEntity modifyQuestionnaire(Questionnaire questionnaire) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        UpdateWrapper<Questionnaire> updateWrapper = new UpdateWrapper<>();

        updateWrapper.eq("id",questionnaire.getId());

        boolean update = update(questionnaire, updateWrapper);
        if(!update){
            httpResponseEntity.setCode(MODIFY_FAIL_CODE);
            httpResponseEntity.setMessage(MODIFY_FAIL_MESSAGE);
            return  httpResponseEntity;
        }
        httpResponseEntity.setCode(MODIFY_SUCCESS_CODE);
        httpResponseEntity.setMessage(MODIFY_SUCCESS_MESSAGE);
        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity deleteQuestionnaires(List<String> ids) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        boolean flag = removeByIds(ids);
        if (!flag) {
            httpResponseEntity.setCode(DELETE_FAIL_CODE);
            httpResponseEntity.setMessage(DELETE_FAIL_MESSAGE);
            return  httpResponseEntity;
        }
        httpResponseEntity.setCode(DELETE_SUCCESS_CODE);
        httpResponseEntity.setMessage(DELETE_SUCCESS_MESSAGE);
        return httpResponseEntity;
    }
}
