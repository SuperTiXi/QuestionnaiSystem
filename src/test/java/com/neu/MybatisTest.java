package com.neu;

import com.neu.dao.AccountMapper;
import com.neu.dao.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MybatisTest {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void testConnection(){
        Account account = new Account();
        account.setId("111");
        account.setName("11111111");
        account.setInfo("1111");
        accountMapper.insert(account);
    }
}
