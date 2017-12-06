package com.hrg.javamapper.read;

import com.hrg.model.ProjectAuditDel;
import com.hrg.model.ProjectAuditDelCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface ProjectAuditDelReadMapper {
    int countByExample(@Param("example") ProjectAuditDelCriteria example);

    List<ProjectAuditDel> selectByExample(@Param("example") ProjectAuditDelCriteria example);

    ProjectAuditDel selectByPrimaryKey(@Param("id") Integer id);

    ProjectAuditDel selectByExampleForOne(@Param("example") ProjectAuditDelCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") ProjectAuditDelCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("id") Integer id);

    Map<String, Object> selectMapByExampleForOne(@Param("example") ProjectAuditDelCriteria example);
}