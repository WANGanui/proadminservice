package com.hrg.javamapper.read;

import com.hrg.model.Project;
import com.hrg.model.ProjectCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProjectReadMapper {
    int countByExample(@Param("example") ProjectCriteria example);

    List<Project> selectByExample(@Param("example") ProjectCriteria example);

    Project selectByPrimaryKey(@Param("dataid") String dataid);

    Project selectByExampleForOne(@Param("example") ProjectCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") ProjectCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") ProjectCriteria example);

    List<Map> selectProjectAudit(Map map);

    int countAudit(@Param("auditorid") String auditorid);
}