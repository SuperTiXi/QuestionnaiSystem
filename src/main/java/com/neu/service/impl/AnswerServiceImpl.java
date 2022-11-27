package com.neu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.bean.HttpResponseEntity;
import com.neu.common.Constants;
import com.neu.common.utils.CommonUtils;
import com.neu.common.utils.UUIDUtil;
import com.neu.dao.AnswerMapper;
import com.neu.dao.AnswererToQuestionnaireMapper;
import com.neu.dao.ReleasedQuestionnaireMapper;
import com.neu.dao.UserToGroupMapper;
import com.neu.dao.entity.Answer;
import com.neu.dao.entity.AnswererToQuestionnaire;
import com.neu.dao.entity.ReleasedQuestionnaire;
import com.neu.service.AnswerService;
import com.neu.service.ReleasedQuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.neu.common.Constants.*;

@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerService {
    @Autowired
    AnswerMapper answerMapper;
    @Autowired
    UserToGroupMapper userToGroupMapper;
    @Autowired
    AnswererToQuestionnaireMapper answererToQuestionnaireMapper;
    @Autowired
    ReleasedQuestionnaireService releasedQuestionnaireService;

    @Autowired
    ReleasedQuestionnaireMapper releasedQuestionnaireMapper;

    public HttpResponseEntity addAnswer(Answer answer) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        String info = answer.getInfo();
        String accountId = answer.getAccountId();
        String createTime = answer.getCreateTime();
        String questionnaireId = answer.getQuestionnaireId();

        if (CommonUtils.stringIsEmpty(info)||CommonUtils.stringIsEmpty(accountId)
                ||CommonUtils.stringIsEmpty(createTime)||CommonUtils.stringIsEmpty(questionnaireId)) {
            httpResponseEntity.setCode(INSERT_FAIL_CODE);
            httpResponseEntity.setMessage(EMPTY_ERROR);
            return httpResponseEntity;
        }

        answer.setId(UUIDUtil.getOneUUID());

        ReleasedQuestionnaire releasedQuestionnaire = releasedQuestionnaireService.query().eq("id", answer.getQuestionnaireId()).one();

        int answers = releasedQuestionnaire.getAnswers();
        answers++;
        releasedQuestionnaire.setAnswers(answers);
        releasedQuestionnaireService.updateById(releasedQuestionnaire);
        try {
            save(answer);
            httpResponseEntity.setCode(INSERT_SUCCESS_CODE);
            httpResponseEntity.setMessage(INSERT_SUCCESS_MESSAGE);
        } catch (Exception e) {
            httpResponseEntity.setCode(INSERT_FAIL_CODE);
            httpResponseEntity.setMessage(INSERT_FAIL_MESSAGE);
        }

        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity addGroupToQuestionnaire(String groupId, String questionnaireId) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if (CommonUtils.stringIsEmpty(groupId)||CommonUtils.stringIsEmpty(questionnaireId)) {
            httpResponseEntity.setCode(INSERT_FAIL_CODE);
            httpResponseEntity.setMessage(EMPTY_ERROR);
            return httpResponseEntity;
        }

        List<String> list = userToGroupMapper.queryUserByGroup(groupId);

        try {
            for (String userId : list) {
                answererToQuestionnaireMapper.insert(new AnswererToQuestionnaire(userId, questionnaireId));
            }

            httpResponseEntity.setCode(INSERT_SUCCESS_CODE);
            httpResponseEntity.setMessage(INSERT_SUCCESS_MESSAGE);
        } catch (Exception e) {
            httpResponseEntity.setCode(INSERT_FAIL_CODE);
            httpResponseEntity.setMessage(INSERT_FAIL_MESSAGE);
            e.printStackTrace();
        }
        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity getAnswerersCount(String questionnaireId) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        ReleasedQuestionnaire releasedQuestionnaire = releasedQuestionnaireMapper.selectById(questionnaireId);
        Integer answererCount = releasedQuestionnaire.getAnswers();

        if (answererCount==null) {
            httpResponseEntity.setCode(QUERY_FAIL_CODE);
            httpResponseEntity.setMessage(QUERY_FAIL_MESSAGE);
        }else {
            httpResponseEntity.setCode(QUERY_SUCCESS_CODE);
            httpResponseEntity.setData(answererCount);
            httpResponseEntity.setMessage(QUERY_SUCCESS_MESSAGE);
        }

        return httpResponseEntity;
    }
}
