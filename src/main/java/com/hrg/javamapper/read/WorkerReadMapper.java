package com.hrg.javamapper.read;

import com.hrg.model.Worker;
import com.hrg.model.WorkerCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface WorkerReadMapper {
    int countByExample(@Param("example") WorkerCriteria example);

    List<Worker> selectByExample(@Param("example") WorkerCriteria example);

    Worker selectByPrimaryKey(@Param("dataid") String dataid);

    Worker selectByExampleForOne(@Param("example") WorkerCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") WorkerCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") WorkerCriteria example);

    Worker selectByAccount(@Param("account") String account);
}