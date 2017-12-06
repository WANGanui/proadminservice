package com.hrg.javamapper.read;

import com.hrg.model.ProjectWeekReport;
import com.hrg.model.ProjectWeekReportCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface ProjectWeekReportReadMapper {
    int countByExample(@Param("example") ProjectWeekReportCriteria example);

    List<ProjectWeekReport> selectByExample(@Param("example") ProjectWeekReportCriteria example);

    ProjectWeekReport selectByPrimaryKey(@Param("dataid") String dataid);

    ProjectWeekReport selectByExampleForOne(@Param("example") ProjectWeekReportCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") ProjectWeekReportCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") ProjectWeekReportCriteria example);
}