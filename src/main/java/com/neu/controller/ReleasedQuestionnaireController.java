package com.neu.controller;

import com.neu.bean.HttpResponseEntity;
import com.neu.service.ReleasedQuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;

@RestController
@RequestMapping("/release")
public class ReleasedQuestionnaireController {

    @Autowired
    ReleasedQuestionnaireService releasedQuestionnaireService;
    @RequestMapping(value = "/list",method = RequestMethod.GET,headers ="Accept=application/json")
    public HttpResponseEntity getReleasedList(@RequestParam("userId") String userId){

        return releasedQuestionnaireService.getReleasedList(userId);
    }
}
