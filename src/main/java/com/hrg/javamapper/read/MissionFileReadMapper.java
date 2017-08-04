package com.hrg.javamapper.read;

import com.hrg.model.MissionFile;
import com.hrg.model.MissionFileCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface MissionFileReadMapper {
    int countByExample(@Param("example") MissionFileCriteria example);

    List<MissionFile> selectByExample(@Param("example") MissionFileCriteria example);

    MissionFile selectByPrimaryKey(@Param("dataid") String dataid);

    MissionFile selectByExampleForOne(@Param("example") MissionFileCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") MissionFileCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") MissionFileCriteria example);
}