package com.hrg.javamapper.read;

import com.hrg.model.HrgProject;
import com.hrg.model.HrgProjectCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface HrgProjectReadMapper {
    int countByExample(@Param("example") HrgProjectCriteria example);

    List<HrgProject> selectByExample(@Param("example") HrgProjectCriteria example);

    HrgProject selectByPrimaryKey(@Param("dataid") String dataid);

    HrgProject selectByExampleForOne(@Param("example") HrgProjectCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") HrgProjectCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") HrgProjectCriteria example);
}