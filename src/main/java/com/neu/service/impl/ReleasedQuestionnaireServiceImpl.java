package com.neu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.bean.HttpResponseEntity;
import com.neu.dao.ReleasedQuestionnaireMapper;
import com.neu.dao.UserToQuestionnaireMapper;
import com.neu.dao.entity.ReleasedQuestionnaire;
import com.neu.dao.entity.UserToReleasedQuestionnaire;
import com.neu.service.ReleasedQuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.neu.common.Constants.QUERY_SUCCESS_CODE;
import static com.neu.common.Constants.QUERY_SUCCESS_MESSAGE;


@Service
public class ReleasedQuestionnaireServiceImpl extends ServiceImpl<ReleasedQuestionnaireMapper, ReleasedQuestionnaire> implements ReleasedQuestionnaireService {

    @Autowired
    UserToQuestionnaireMapper userToQuestionnaireMapper;

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
}
