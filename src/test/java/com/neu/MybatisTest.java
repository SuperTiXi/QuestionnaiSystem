package com.neu;

import com.neu.dao.AccountMapper;
import com.neu.dao.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class MybatisTest {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void testConnection(){
        Account account = new Account();
        account.setId("121");
        account.setName("11111111");
        account.setInfo("1111");
        accountMapper.insert(account);
    }
}
