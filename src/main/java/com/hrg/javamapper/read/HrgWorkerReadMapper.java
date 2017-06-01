package com.hrg.javamapper.read;

import com.hrg.model.HrgWorker;
import com.hrg.model.HrgWorkerCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface HrgWorkerReadMapper {
    int countByExample(@Param("example") HrgWorkerCriteria example);

    List<HrgWorker> selectByExample(@Param("example") HrgWorkerCriteria example);

    HrgWorker selectByPrimaryKey(@Param("dataid") String dataid);

    HrgWorker selectByExampleForOne(@Param("example") HrgWorkerCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") HrgWorkerCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") HrgWorkerCriteria example);
}