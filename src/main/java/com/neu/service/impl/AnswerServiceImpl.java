package com.neu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.dao.AnswerMapper;
import com.neu.dao.entity.Answer;
import com.neu.service.AnswerService;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerService {
}
