package com.neu.controller;

import com.neu.bean.HttpResponseEntity;
import com.neu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户对所创建的群组进行管理
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    AccountService accountService;

    /**
     * 查询该用户下所有组
     * @param userId 该用户id
     * @return list
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET,headers = "Accept=Application/json")
    public HttpResponseEntity queryAllGroup(@RequestParam("userId") String userId){

        return accountService.queryAllGroup(userId);
    }
}
