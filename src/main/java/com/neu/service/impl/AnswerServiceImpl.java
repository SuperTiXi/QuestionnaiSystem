package com.neu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.bean.HttpResponseEntity;
import com.neu.common.Constants;
import com.neu.common.utils.CommonUtils;
import com.neu.common.utils.UUIDUtil;
import com.neu.dao.AnswerMapper;
import com.neu.dao.entity.Answer;
import com.neu.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.neu.common.Constants.*;

@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerService {
    @Autowired
    AnswerService answerService;
    @Autowired
    AnswerMapper answerMapper;
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
}
