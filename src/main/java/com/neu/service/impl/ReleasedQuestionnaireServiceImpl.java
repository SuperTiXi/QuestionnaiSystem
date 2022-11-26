package com.neu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.bean.HttpResponseEntity;
import com.neu.common.utils.CommonUtils;
import com.neu.dao.AnswerMapper;
import com.neu.dao.AnswererToQuestionnaireMapper;
import com.neu.dao.ReleasedQuestionnaireMapper;
import com.neu.dao.UserToQuestionnaireMapper;
import com.neu.dao.entity.Answer;
import com.neu.dao.entity.ReleasedQuestionnaire;
import com.neu.dao.entity.UserToReleasedQuestionnaire;
import com.neu.service.ReleasedQuestionnaireService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.neu.common.Constants.*;


@Service
public class ReleasedQuestionnaireServiceImpl extends ServiceImpl<ReleasedQuestionnaireMapper, ReleasedQuestionnaire> implements ReleasedQuestionnaireService {

    @Autowired
    UserToQuestionnaireMapper userToQuestionnaireMapper;
    @Autowired
    AnswererToQuestionnaireMapper answererToQuestionnaireMapper;
    @Autowired
    AnswerMapper answerMapper;

    /**
     * 获取用户下所有已发布的问卷
     * @param userId
     * @return
     */
    @Override
    public HttpResponseEntity getReleasedList(String userId) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        QueryWrapper<UserToReleasedQuestionnaire> userToQuestionnaireQueryWrapper = new QueryWrapper<>();
        userToQuestionnaireQueryWrapper.eq("userId",userId);
        List<UserToReleasedQuestionnaire> userToReleasedQuestionnaires = userToQuestionnaireMapper.selectList(userToQuestionnaireQueryWrapper);

        List<ReleasedQuestionnaire> releasedQuestionnaires = new ArrayList<>();
        for (UserToReleasedQuestionnaire userToReleasedQuestionnaire : userToReleasedQuestionnaires) {
            ReleasedQuestionnaire releasedQuestionnaire = query().eq("questionnaire_id", userToReleasedQuestionnaire.getQuestionnaireId()).one();
            releasedQuestionnaires.add(releasedQuestionnaire);
        }

        httpResponseEntity.setCode(QUERY_SUCCESS_CODE);
        httpResponseEntity.setMessage(QUERY_SUCCESS_MESSAGE);
        httpResponseEntity.setData(releasedQuestionnaires);

        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity queryQuestionnaireById(String id) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if (CommonUtils.stringIsEmpty(id)) {
            httpResponseEntity.setCode(QUERY_FAIL_CODE);
            httpResponseEntity.setMessage(EMPTY_ERROR);
            return httpResponseEntity;
        }

        ReleasedQuestionnaire releasedQuestionnaire = query().eq("questionnaire_id", id).one();
        if (releasedQuestionnaire==null) {
            httpResponseEntity.setCode(QUERY_FAIL_CODE);
            httpResponseEntity.setMessage(QUERY_FAIL_MESSAGE);
        }else {
            httpResponseEntity.setCode(QUERY_SUCCESS_CODE);
            httpResponseEntity.setMessage(QUERY_SUCCESS_MESSAGE);
            httpResponseEntity.setData(releasedQuestionnaire);
        }

        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity getAnswersByQuestionnaire(String id) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if (CommonUtils.stringIsEmpty(id)) {
            httpResponseEntity.setCode(QUERY_FAIL_CODE);
            httpResponseEntity.setMessage(EMPTY_ERROR);
            return httpResponseEntity;
        }

        List<Answer> answerList = answerMapper.getAnswersByQuestionnaire(id);

        if (answerList==null) {
            httpResponseEntity.setCode(QUERY_FAIL_CODE);
            httpResponseEntity.setMessage(QUERY_FAIL_MESSAGE);
        }else {
            httpResponseEntity.setCode(QUERY_SUCCESS_CODE);
            httpResponseEntity.setMessage(QUERY_SUCCESS_MESSAGE);
            httpResponseEntity.setData(answerList);
        }

        return httpResponseEntity;
    }
}
