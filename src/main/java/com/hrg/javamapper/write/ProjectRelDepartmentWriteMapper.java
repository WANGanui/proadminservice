package com.hrg.javamapper.write;

import com.hrg.model.ProjectRelDepartment;
import com.hrg.model.ProjectRelDepartmentCriteria;
import org.apache.ibatis.annotations.Param;

public interface ProjectRelDepartmentWriteMapper {
    int deleteByExample(@Param("example") ProjectRelDepartmentCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") ProjectRelDepartment record);

    int insertSelective(@Param("record") ProjectRelDepartment record);

    int updateByExampleSelective(@Param("record") ProjectRelDepartment record, @Param("example") ProjectRelDepartmentCriteria example);

    int updateByExample(@Param("record") ProjectRelDepartment record, @Param("example") ProjectRelDepartmentCriteria example);

    int updateByPrimaryKeySelective(@Param("record") ProjectRelDepartment record);

    int updateByPrimaryKey(@Param("record") ProjectRelDepartment record);
}