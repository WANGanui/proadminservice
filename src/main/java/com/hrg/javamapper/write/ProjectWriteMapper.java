package com.hrg.javamapper.write;

import com.hrg.model.Project;
import com.hrg.model.ProjectCriteria;
import org.apache.ibatis.annotations.Param;

public interface ProjectWriteMapper {
    int deleteByExample(@Param("example") ProjectCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") Project record);

    int insertSelective(@Param("record") Project record);

    int updateByExampleSelective(@Param("record") Project record, @Param("example") ProjectCriteria example);

    int updateByExample(@Param("record") Project record, @Param("example") ProjectCriteria example);

    int updateByPrimaryKeySelective(@Param("record") Project record);

    int updateByPrimaryKey(@Param("record") Project record);
}