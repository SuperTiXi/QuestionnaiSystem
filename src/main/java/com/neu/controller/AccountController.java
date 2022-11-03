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

    /**
     * 以用户名登录
     * @param userName 用户名
     * @param password 密码
     * @return 失败返回401，成功返回用户信息
     */
    @RequestMapping(value = "/loginByUserName",method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponseEntity loginByUserName(@RequestParam("userName") String userName, @RequestParam("password") String password){

        return accountService.loginByUserName(userName,password);
    }

    /**
     * 发送验证码，验证码2min过期
     * @param phone 手机号
     * @return 返回状态码和信息
     */
    @RequestMapping(value = "/sendCode",method = RequestMethod.GET,headers = "Accept=application/json")
    public HttpResponseEntity sendCode(@RequestParam("phone") String phone){

        return accountService.sendCode(phone);
    }

    /**
     * 使用手机号登录
     * @param phone 手机号
     * @param code 验证码
     * @return 登录状态
     */
    @RequestMapping(value = "/loginByPhone",method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponseEntity loginByPhone(@RequestParam("phone") String phone,@RequestParam("code") String code){

        return accountService.loginByPhone(phone,code);
    }

    /**
     * 注册
     * @param body 注册填写的信息，包括username，name，password，phone，identity（identity需要用户自己选择：1.租户 2.用户 3.答者）
     * @return 注册状态
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity register(@RequestBody Map<String, Object> body) {
        return accountService.register(BeanUtil.fillBeanWithMap(body, new Account(),true));
    }
}
