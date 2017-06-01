package com.hrg.javamapper.read;

import com.hrg.model.HrgMission;
import com.hrg.model.HrgMissionCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface HrgMissionReadMapper {
    int countByExample(@Param("example") HrgMissionCriteria example);

    List<HrgMission> selectByExample(@Param("example") HrgMissionCriteria example);

    HrgMission selectByPrimaryKey(@Param("dataid") String dataid);

    HrgMission selectByExampleForOne(@Param("example") HrgMissionCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") HrgMissionCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") HrgMissionCriteria example);
}