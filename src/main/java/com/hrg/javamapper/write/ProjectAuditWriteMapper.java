package com.hrg.javamapper.write;

import com.hrg.model.ProjectAudit;
import com.hrg.model.ProjectAuditCriteria;
import org.apache.ibatis.annotations.Param;

public interface ProjectAuditWriteMapper {
    int deleteByExample(@Param("example") ProjectAuditCriteria example);

    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(@Param("record") ProjectAudit record);

    int insertSelective(@Param("record") ProjectAudit record);

    int updateByExampleSelective(@Param("record") ProjectAudit record, @Param("example") ProjectAuditCriteria example);

    int updateByExample(@Param("record") ProjectAudit record, @Param("example") ProjectAuditCriteria example);

    int updateByPrimaryKeySelective(@Param("record") ProjectAudit record);

    int updateByPrimaryKey(@Param("record") ProjectAudit record);
}