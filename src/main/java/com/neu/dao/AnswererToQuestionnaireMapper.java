package com.neu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.dao.entity.AnswererToQuestionnaire;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface AnswererToQuestionnaireMapper extends BaseMapper<AnswererToQuestionnaire> {
    @Select("SELECT answerer_id FROM answerer_questionnaire WHERE questionnaire_id = #{questionnaireId}")
    List<String> getAnswererByQuestionnaireId (@Param("questionnaireId") String questionnaireId);

    @Select("SELECT questionnaire_id FROM answerer_questionnaire WHERE answerer_id = #{answererId}")
    List<String> getQuestionnaireByAnswererId (@Param("answererId") String answererId);

    @Delete("DELETE FROM answerer_questionnaire WHERE questionnaire_id = #{questionnaireId} AND answerer_id = #{answererId}")
    Integer remove(@Param("questionnaireId") String questionnaireId, @Param("answererId") String answererId);

}
