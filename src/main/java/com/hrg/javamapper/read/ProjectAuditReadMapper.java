package com.hrg.javamapper.read;

import com.hrg.model.ProjectAudit;
import com.hrg.model.ProjectAuditCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface ProjectAuditReadMapper {
    int countByExample(@Param("example") ProjectAuditCriteria example);

    List<ProjectAudit> selectByExample(@Param("example") ProjectAuditCriteria example);

    ProjectAudit selectByPrimaryKey(@Param("id") Integer id);

    ProjectAudit selectByExampleForOne(@Param("example") ProjectAuditCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") ProjectAuditCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("id") Integer id);

    Map<String, Object> selectMapByExampleForOne(@Param("example") ProjectAuditCriteria example);
}