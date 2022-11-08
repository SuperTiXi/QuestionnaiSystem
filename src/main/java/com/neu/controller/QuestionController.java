package com.neu.controller;

import com.neu.bean.HttpResponseEntity;
import com.neu.dao.entity.Question;
import com.neu.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @RequestMapping(value = "/addQuestion",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @RequestMapping(value = "/modifyQuestion",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyQuestion(@RequestBody Question question) {
        return questionService.modifyQuestion(question);
    }

    @RequestMapping(value = "/deleteQuestion",method = RequestMethod.DELETE, headers = "Accept=application/json")
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
