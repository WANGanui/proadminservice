package com.hrg.javamapper.read;

import com.hrg.model.HrgDepartment;
import com.hrg.model.HrgDepartmentCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface HrgDepartmentReadMapper {
    int countByExample(@Param("example") HrgDepartmentCriteria example);

    List<HrgDepartment> selectByExample(@Param("example") HrgDepartmentCriteria example);

    HrgDepartment selectByPrimaryKey(@Param("dataid") String dataid);

    HrgDepartment selectByExampleForOne(@Param("example") HrgDepartmentCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") HrgDepartmentCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") HrgDepartmentCriteria example);
}