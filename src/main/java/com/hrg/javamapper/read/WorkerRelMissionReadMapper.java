package com.hrg.javamapper.read;

import com.hrg.model.WorkerRelMission;
import com.hrg.model.WorkerRelMissionCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface WorkerRelMissionReadMapper {
    int countByExample(@Param("example") WorkerRelMissionCriteria example);

    List<WorkerRelMission> selectByExample(@Param("example") WorkerRelMissionCriteria example);

    WorkerRelMission selectByPrimaryKey(@Param("dataid") String dataid);

    WorkerRelMission selectByExampleForOne(@Param("example") WorkerRelMissionCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") WorkerRelMissionCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") WorkerRelMissionCriteria example);
}