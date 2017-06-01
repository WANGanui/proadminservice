package com.hrg.javamapper.read;

import com.hrg.model.WorkerRelProject;
import com.hrg.model.WorkerRelProjectCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface WorkerRelProjectReadMapper {
    int countByExample(@Param("example") WorkerRelProjectCriteria example);

    List<WorkerRelProject> selectByExample(@Param("example") WorkerRelProjectCriteria example);

    WorkerRelProject selectByPrimaryKey(@Param("dataid") String dataid);

    WorkerRelProject selectByExampleForOne(@Param("example") WorkerRelProjectCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") WorkerRelProjectCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") WorkerRelProjectCriteria example);
}