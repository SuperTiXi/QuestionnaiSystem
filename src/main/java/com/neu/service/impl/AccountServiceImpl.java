package com.neu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.bean.HttpResponseEntity;
import com.neu.dao.AccountMapper;
import com.neu.dao.entity.Account;
import com.neu.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Override
    public HttpResponseEntity loginByUserName(String userName, String password) {
        log.error("登录成功");
        return null;
    }
}
