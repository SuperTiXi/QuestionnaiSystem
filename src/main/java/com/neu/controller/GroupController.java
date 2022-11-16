package com.neu.controller;

import com.neu.bean.HttpResponseEntity;
import com.neu.dao.entity.Account;
import com.neu.service.AccountService;
import com.neu.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 群组内对答者进行管理
 */
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    AccountService accountService;

    @Autowired
    GroupService groupService;

    /**
     * 查询该群组下的答者
     * @param groupId 群组id
     * @return 答者信息
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET,headers = "Accept=application/json")
    public HttpResponseEntity queryAllAnswerer(@RequestParam("groupId") String groupId){

        return accountService.queryAllAnswerer(groupId);
    }

    /**
     * 创建答者
     * @param answerer 答者，需要username,password,name,phone
     * @return 答者信息
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity addAnswerer(@RequestBody Account answerer){

        return accountService.addAnswerer(answerer);
    }

    /**
     * 将答者添加到群组中
     * @param answererId 答者id
     * @param groupId 群组id
     * @return 添加状态
     */
    @RequestMapping(value = "/addAnswerer",method = RequestMethod.GET,headers = "Accept=application/json")
    public HttpResponseEntity addAnswererToGroup(@RequestParam("answererId") String answererId,@RequestParam("groupId") String groupId){

        return groupService.addAnswererToGroup(answererId,groupId);
    }

    /**
     * 将答者从群组中移除
     * @param answererId 答者id
     * @param groupId 群组id
     * @return 删除状态
     */
    @RequestMapping(value = "/removeAnswerer",method = RequestMethod.GET,headers = "Accept=application/json")
    public HttpResponseEntity removeAnswererFromGroup(@RequestParam("answererId") String answererId,@RequestParam("groupId") String groupId){

        return groupService.removeAnswererFromGroup(answererId,groupId);
    }
}
