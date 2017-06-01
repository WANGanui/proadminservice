package com.hrg.javamapper.write;

import com.hrg.model.WorkerRelMission;
import com.hrg.model.WorkerRelMissionCriteria;
import org.apache.ibatis.annotations.Param;

public interface WorkerRelMissionWriteMapper {
    int deleteByExample(@Param("example") WorkerRelMissionCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") WorkerRelMission record);

    int insertSelective(@Param("record") WorkerRelMission record);

    int updateByExampleSelective(@Param("record") WorkerRelMission record, @Param("example") WorkerRelMissionCriteria example);

    int updateByExample(@Param("record") WorkerRelMission record, @Param("example") WorkerRelMissionCriteria example);

    int updateByPrimaryKeySelective(@Param("record") WorkerRelMission record);

    int updateByPrimaryKey(@Param("record") WorkerRelMission record);
}