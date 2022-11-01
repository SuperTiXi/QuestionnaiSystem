package com.neu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.dao.entity.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
