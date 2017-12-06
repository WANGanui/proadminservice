package com.hrg.javamapper.write;

import com.hrg.model.MissionAudit;
import com.hrg.model.MissionAuditCriteria;
import org.apache.ibatis.annotations.Param;

public interface MissionAuditWriteMapper {
    int deleteByExample(@Param("example") MissionAuditCriteria example);

    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(@Param("record") MissionAudit record);

    int insertSelective(@Param("record") MissionAudit record);

    int updateByExampleSelective(@Param("record") MissionAudit record, @Param("example") MissionAuditCriteria example);

    int updateByExample(@Param("record") MissionAudit record, @Param("example") MissionAuditCriteria example);

    int updateByPrimaryKeySelective(@Param("record") MissionAudit record);

    int updateByPrimaryKey(@Param("record") MissionAudit record);
}