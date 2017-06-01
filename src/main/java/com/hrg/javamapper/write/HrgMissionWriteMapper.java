package com.hrg.javamapper.write;

import com.hrg.model.HrgMission;
import com.hrg.model.HrgMissionCriteria;
import org.apache.ibatis.annotations.Param;

public interface HrgMissionWriteMapper {
    int deleteByExample(@Param("example") HrgMissionCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") HrgMission record);

    int insertSelective(@Param("record") HrgMission record);

    int updateByExampleSelective(@Param("record") HrgMission record, @Param("example") HrgMissionCriteria example);

    int updateByExample(@Param("record") HrgMission record, @Param("example") HrgMissionCriteria example);

    int updateByPrimaryKeySelective(@Param("record") HrgMission record);

    int updateByPrimaryKey(@Param("record") HrgMission record);
}