package com.neu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.bean.HttpResponseEntity;
import com.neu.dao.entity.Account;

import java.util.Map;

public interface AccountService extends IService<Account> {

    HttpResponseEntity loginByUserName(String userName,String password);

    HttpResponseEntity loginByPhone(String phone, String code);

    HttpResponseEntity sendCode(String phone);
    HttpResponseEntity register(Account account);

    HttpResponseEntity queryAllTenant();

    HttpResponseEntity addTenant(Account account);

    HttpResponseEntity delete(String userName, String phone);

    HttpResponseEntity recover(String userName, String phone);

    HttpResponseEntity securityQuestions(String userName);

//    HttpResponseEntity answerSecurityQuestions(Map<String, String> answers);
}
