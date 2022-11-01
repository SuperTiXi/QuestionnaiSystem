package com.neu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.dao.QuestionnaireMapper;
import com.neu.dao.entity.Questionnaire;
import com.neu.service.QuestionnaireService;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireServiceImpl extends ServiceImpl<QuestionnaireMapper, Questionnaire> implements QuestionnaireService {
}
