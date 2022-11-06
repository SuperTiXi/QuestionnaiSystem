package com.neu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.dao.entity.GroupToAnswerer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GroupToAnswererMapper extends BaseMapper<GroupToAnswerer> {

    @Select("SELECT answerer_id FROM group_answerer WHERE group_id = #{groupId}")
    List<String> getAnswererByGroup(@Param("groupId") String groupId);


    @Delete("DELETE FROM group_answerer WHERE group_id = #{groupId} AND answerer_id = #{answererId}")
    Integer removeAnswererFromGroup(@Param("answererId") String answererId, @Param("groupId") String groupId);
}
