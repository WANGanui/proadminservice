package com.hrg.javamapper.write;

import com.hrg.model.ProjectWeekReport;
import com.hrg.model.ProjectWeekReportCriteria;
import org.apache.ibatis.annotations.Param;

public interface ProjectWeekReportWriteMapper {
    int deleteByExample(@Param("example") ProjectWeekReportCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") ProjectWeekReport record);

    int insertSelective(@Param("record") ProjectWeekReport record);

    int updateByExampleSelective(@Param("record") ProjectWeekReport record, @Param("example") ProjectWeekReportCriteria example);

    int updateByExample(@Param("record") ProjectWeekReport record, @Param("example") ProjectWeekReportCriteria example);

    int updateByPrimaryKeySelective(@Param("record") ProjectWeekReport record);

    int updateByPrimaryKey(@Param("record") ProjectWeekReport record);
}