package com.neu.controller;

import com.neu.bean.HttpResponseEntity;
import com.neu.dao.entity.Questionnaire;
import com.neu.dao.entity.ReleasedQuestionnaire;
import com.neu.service.QuestionService;
import com.neu.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    @Autowired
    QuestionnaireService questionnaireService;

    /**
     *
     * @param questionnaire 需要填写问卷名==name，问卷类型==type，
     *                      问卷所包含的问题id==info，制作问卷者的id==creatorId，
     *                      所属租户的id==tenantId
     * @return
     */
    @RequestMapping(value = "/createQuestionnaire",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity createQuestionnaire(@RequestBody Questionnaire questionnaire) {
        return questionnaireService.createQuestionnaire(questionnaire);
    }

    @RequestMapping(value = "/findQuestionnaire",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity findQuestionnaire(@RequestBody Map<String, String> map) {
        return questionnaireService.findQuestionnaire(map);
    }

    @RequestMapping(value = "/list",method =RequestMethod.POST,headers = "Accept=application/json" )
    public HttpResponseEntity queryAllQuestionnaire(@RequestParam("creatorId") String creatorId){

        return questionnaireService.queryAllQuestionnaire(creatorId);
    }
    /**
     *
     * @param info 问卷中存储的问题id
     * @return
     */
    @RequestMapping(value = "/containQuestions",method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponseEntity containQuestions(@RequestParam("info") String info) {
        return questionnaireService.containQuestions(info);
    }

    @RequestMapping(value = "/modifyQuestionnaire",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity switchQuestionnaires(@RequestBody Questionnaire questionnaire) {
        return questionnaireService.modifyQuestionnaire(questionnaire);
    }

    @RequestMapping(value = "/deleteQuestionnaires",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteQuestionnaires(@RequestParam List<String> ids) {
        return questionnaireService.deleteQuestionnaires(ids);
    }

    @RequestMapping(value = "/release" , method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity release(@RequestBody ReleasedQuestionnaire releasedQuestionnaire) {
        return questionnaireService.release(releasedQuestionnaire);
    }
}
