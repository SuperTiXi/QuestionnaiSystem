package com.neu.controller;

import com.alibaba.fastjson.JSONObject;
import com.neu.bean.HttpResponseEntity;
import com.neu.common.Constants;
import com.neu.dao.entity.Answer;
import com.neu.dao.entity.ReleasedQuestionnaire;
import com.neu.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;

    @RequestMapping(value = "/insert",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity addAnswer(@RequestBody Answer answer){
        return answerService.addAnswer(answer);
    }

    @RequestMapping(value = "/addGroupToQuestionnaire",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity addGroupToQuestionnaire(@RequestBody Map<String, Object> map) {
        String groupId = (String) map.get("groupId");
        String questionnaireId = (String)map.get("questionnaireId");
        return answerService.addGroupToQuestionnaire(groupId, questionnaireId);
    }

    @RequestMapping(value = "/getAnswerersCount", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponseEntity getAnswerersCount(@RequestParam("questionnaireId") String questionnaireId) {
        return answerService.getAnswerersCount(questionnaireId);
    }

    @RequestMapping(value = "/getShortUrlForLink",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity getShortUrlForLink(@RequestBody HashMap<String, Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        System.err.println(map);

        String tinyurl = (String) map.get("link");
        tinyurl += map.get("id");
        tinyurl += "&l";
        map.put("tinyurl", tinyurl);
        JSONObject jsonObject = new JSONObject(map);
        httpResponseEntity.setCode(Constants.QUERY_SUCCESS_CODE);
        httpResponseEntity.setData(jsonObject.toJSONString());
        httpResponseEntity.setMessage(Constants.QUERY_SUCCESS_MESSAGE);

        return httpResponseEntity;
    }
}
