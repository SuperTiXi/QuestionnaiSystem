package com.neu.controller;

import cn.hutool.core.bean.BeanUtil;
import com.neu.bean.HttpResponseEntity;
import com.neu.dao.entity.Account;
import com.neu.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/loginByUserName",method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponseEntity loginByUserName(@RequestParam("userName") String userName, @RequestParam("password") String password){

        return accountService.loginByUserName(userName,password);
    }


    @RequestMapping(value = "/sendCode",method = RequestMethod.GET,headers = "Accept=application/json")
    public HttpResponseEntity sendCode(@RequestParam("phone") String phone){

        return accountService.sendCode(phone);
    }
    @RequestMapping(value = "/loginByPhone",method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponseEntity loginByPhone(@RequestParam("phone") String phone,@RequestParam("code") String code){

        return accountService.loginByPhone(phone,code);
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity register(@RequestBody Map<String, Object> body) {
        return accountService.register(BeanUtil.fillBeanWithMap(body, new Account(),true));
    }
}
