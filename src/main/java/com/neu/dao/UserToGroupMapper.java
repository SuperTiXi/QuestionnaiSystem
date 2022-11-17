package com.neu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.dao.entity.UserToGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import javax.management.Query;
import java.util.List;

@Mapper
public interface UserToGroupMapper extends BaseMapper<UserToGroup> {

    @Select("SELECT group_id FROM user_group WHERE user_id = #{userId}")
    List<String> queryGroupByUser(@Param("userId") String userId);
    @Select("SELECT user_id FROM user_group WHERE group__id = #{groupId}")
    List<String> queryUserByGroup(@Param("groupId") String groupId);
}
