package com.neu.controller;

import com.neu.bean.HttpResponseEntity;
import com.neu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/loginByUserName",method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponseEntity loginByUserName(String userName,String password){
        //TODO 实现用户名登录
        return accountService.loginByUserName(userName,password);
    }
}
