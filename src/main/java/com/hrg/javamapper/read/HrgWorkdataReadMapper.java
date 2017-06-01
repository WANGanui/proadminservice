package com.hrg.javamapper.read;

import com.hrg.model.HrgWorkdata;
import com.hrg.model.HrgWorkdataCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface HrgWorkdataReadMapper {
    int countByExample(@Param("example") HrgWorkdataCriteria example);

    List<HrgWorkdata> selectByExample(@Param("example") HrgWorkdataCriteria example);

    HrgWorkdata selectByPrimaryKey(@Param("dataid") String dataid);

    HrgWorkdata selectByExampleForOne(@Param("example") HrgWorkdataCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") HrgWorkdataCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") HrgWorkdataCriteria example);
}