package com.neu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.dao.entity.Account;
import com.neu.dao.entity.TenantToUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.util.List;

@Mapper
public interface TenantToUserMapper extends BaseMapper<TenantToUser> {

    @Select("SELECT tenant_user.user_id FROM account LEFT JOIN tenant_user ON account.id = #{tenantId}")
    List<String> queryUserByTenant(@Param("tenantId") String tenantId);


}
