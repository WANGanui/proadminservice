package com.hrg.javamapper.read;

import com.hrg.model.Workdata;
import com.hrg.model.WorkdataCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface WorkdataReadMapper {
    int countByExample(@Param("example") WorkdataCriteria example);

    List<Workdata> selectByExample(@Param("example") WorkdataCriteria example);

    Workdata selectByPrimaryKey(@Param("dataid") String dataid);

    Workdata selectByExampleForOne(@Param("example") WorkdataCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") WorkdataCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") WorkdataCriteria example);
}