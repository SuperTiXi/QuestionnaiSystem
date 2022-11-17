package com.neu.controller;

import com.alibaba.fastjson.JSONObject;
import com.neu.bean.HttpResponseEntity;
import com.neu.dao.entity.Answer;
import com.neu.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;

    @RequestMapping(value = "/insert",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity addAnswer(@RequestBody Answer answer){
        return answerService.addAnswer(answer);
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
        httpResponseEntity.setCode("666");
        httpResponseEntity.setData(jsonObject.toJSONString());


        return httpResponseEntity;
    }
}
