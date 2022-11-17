package com.neu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.dao.entity.Answer;
import com.neu.dao.entity.ReleasedQuestionnaire;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnswerMapper extends BaseMapper<Answer> {
    @Select("SELECT * FROM answer WHERE questionnaire_id = #{questionnaireId}")
    List<Answer> getAnswersByQuestionnaire(@Param("questionnaireId") String questionnaireId);
}
