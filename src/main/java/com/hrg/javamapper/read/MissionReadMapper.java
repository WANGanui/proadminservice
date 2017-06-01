package com.hrg.javamapper.read;

import com.hrg.model.Mission;
import com.hrg.model.MissionCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface MissionReadMapper {
    int countByExample(@Param("example") MissionCriteria example);

    List<Mission> selectByExample(@Param("example") MissionCriteria example);

    Mission selectByPrimaryKey(@Param("dataid") String dataid);

    Mission selectByExampleForOne(@Param("example") MissionCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") MissionCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") MissionCriteria example);
}