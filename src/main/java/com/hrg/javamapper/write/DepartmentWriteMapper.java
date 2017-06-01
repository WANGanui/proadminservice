package com.hrg.javamapper.write;

import com.hrg.model.Department;
import com.hrg.model.DepartmentCriteria;
import org.apache.ibatis.annotations.Param;

public interface DepartmentWriteMapper {
    int deleteByExample(@Param("example") DepartmentCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") Department record);

    int insertSelective(@Param("record") Department record);

    int updateByExampleSelective(@Param("record") Department record, @Param("example") DepartmentCriteria example);

    int updateByExample(@Param("record") Department record, @Param("example") DepartmentCriteria example);

    int updateByPrimaryKeySelective(@Param("record") Department record);

    int updateByPrimaryKey(@Param("record") Department record);
}