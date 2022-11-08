package com.neu.controller;

import com.neu.bean.HttpResponseEntity;
import com.neu.dao.entity.Question;
import com.neu.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.Map;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @RequestMapping(value = "/saveOrUpdateQuestion",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity saveOrUpdateQuestion(@RequestBody Question question) {
        return questionService.saveOrUpdateQuestion(question);
    }

    @RequestMapping(value = "/deleteQuestion",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteQuestion(@RequestParam("id") String id) {
        return questionService.deleteQuestion(id);
    }

    /**
     *
     * @param map 多功能查询
     * @return
     */
    @RequestMapping(value = "/queryQuestions",method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponseEntity queryQuestions(@RequestBody Map<String, String> map) {
        return questionService.queryQuestions(map);
    }
}
