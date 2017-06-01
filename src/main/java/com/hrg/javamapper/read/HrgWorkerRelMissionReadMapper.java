package com.hrg.javamapper.read;

import com.hrg.model.HrgWorkerRelMission;
import com.hrg.model.HrgWorkerRelMissionCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface HrgWorkerRelMissionReadMapper {
    int countByExample(@Param("example") HrgWorkerRelMissionCriteria example);

    List<HrgWorkerRelMission> selectByExample(@Param("example") HrgWorkerRelMissionCriteria example);

    HrgWorkerRelMission selectByPrimaryKey(@Param("dataid") String dataid);

    HrgWorkerRelMission selectByExampleForOne(@Param("example") HrgWorkerRelMissionCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") HrgWorkerRelMissionCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") HrgWorkerRelMissionCriteria example);
}