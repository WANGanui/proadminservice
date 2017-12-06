package com.hrg.javamapper.read;

import com.hrg.model.ProjectRelDepartment;
import com.hrg.model.ProjectRelDepartmentCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface ProjectRelDepartmentReadMapper {
    int countByExample(@Param("example") ProjectRelDepartmentCriteria example);

    List<ProjectRelDepartment> selectByExample(@Param("example") ProjectRelDepartmentCriteria example);

    ProjectRelDepartment selectByPrimaryKey(@Param("dataid") String dataid);

    ProjectRelDepartment selectByExampleForOne(@Param("example") ProjectRelDepartmentCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") ProjectRelDepartmentCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") ProjectRelDepartmentCriteria example);
}