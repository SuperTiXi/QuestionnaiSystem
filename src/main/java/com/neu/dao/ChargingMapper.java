package com.neu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.dao.entity.Charging;
import com.neu.dao.entity.ReleasedQuestionnaire;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChargingMapper extends BaseMapper<Charging> {

    @Select("SELECT COUNT(*) FROM tb_group WHERE tenant_id = #{tenantId}")
    Integer groupUnderTenant(@Param("tenantId") String tenantId);

    @Select("SELECT questionnaire_id FROM released_questionnaire WHERE tenant_id = #{tenantId}")
    List<ReleasedQuestionnaire> questionnaireUnderTenant(@Param("tenantId") String tenantId);

    @Select("SELECT COUNT(*) FROM answer WHERE questionnaire_id = #{questionnaireId}")
    Integer answerUnderQuestionnaire(@Param("questionnaireId") String questionnaireId);
}
