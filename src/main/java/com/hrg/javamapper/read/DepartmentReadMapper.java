package com.hrg.javamapper.read;

import com.hrg.model.Department;
import com.hrg.model.DepartmentCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface DepartmentReadMapper {
    int countByExample(@Param("example") DepartmentCriteria example);

    List<Department> selectByExample(@Param("example") DepartmentCriteria example);

    Department selectByPrimaryKey(@Param("dataid") String dataid);

    Department selectByExampleForOne(@Param("example") DepartmentCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") DepartmentCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") DepartmentCriteria example);
}