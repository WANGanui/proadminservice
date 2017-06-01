package com.hrg.javamapper.write;

import com.hrg.model.HrgWorkerRelMission;
import com.hrg.model.HrgWorkerRelMissionCriteria;
import org.apache.ibatis.annotations.Param;

public interface HrgWorkerRelMissionWriteMapper {
    int deleteByExample(@Param("example") HrgWorkerRelMissionCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") HrgWorkerRelMission record);

    int insertSelective(@Param("record") HrgWorkerRelMission record);

    int updateByExampleSelective(@Param("record") HrgWorkerRelMission record, @Param("example") HrgWorkerRelMissionCriteria example);

    int updateByExample(@Param("record") HrgWorkerRelMission record, @Param("example") HrgWorkerRelMissionCriteria example);

    int updateByPrimaryKeySelective(@Param("record") HrgWorkerRelMission record);

    int updateByPrimaryKey(@Param("record") HrgWorkerRelMission record);
}