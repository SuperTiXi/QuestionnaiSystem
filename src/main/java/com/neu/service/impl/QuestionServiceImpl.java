package com.neu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.dao.QuestionMapper;
import com.neu.dao.entity.Question;
import com.neu.service.QuestionService;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper,Question> implements QuestionService {
}
