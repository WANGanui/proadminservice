package com.hrg.javamapper.write;

import com.hrg.model.Mission;
import com.hrg.model.MissionCriteria;
import org.apache.ibatis.annotations.Param;

public interface MissionWriteMapper {
    int deleteByExample(@Param("example") MissionCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") Mission record);

    int insertSelective(@Param("record") Mission record);

    int updateByExampleSelective(@Param("record") Mission record, @Param("example") MissionCriteria example);

    int updateByExample(@Param("record") Mission record, @Param("example") MissionCriteria example);

    int updateByPrimaryKeySelective(@Param("record") Mission record);

    int updateByPrimaryKey(@Param("record") Mission record);
}