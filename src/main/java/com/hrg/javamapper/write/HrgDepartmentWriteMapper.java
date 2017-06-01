package com.hrg.javamapper.write;

import com.hrg.model.HrgDepartment;
import com.hrg.model.HrgDepartmentCriteria;
import org.apache.ibatis.annotations.Param;

public interface HrgDepartmentWriteMapper {
    int deleteByExample(@Param("example") HrgDepartmentCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") HrgDepartment record);

    int insertSelective(@Param("record") HrgDepartment record);

    int updateByExampleSelective(@Param("record") HrgDepartment record, @Param("example") HrgDepartmentCriteria example);

    int updateByExample(@Param("record") HrgDepartment record, @Param("example") HrgDepartmentCriteria example);

    int updateByPrimaryKeySelective(@Param("record") HrgDepartment record);

    int updateByPrimaryKey(@Param("record") HrgDepartment record);
}