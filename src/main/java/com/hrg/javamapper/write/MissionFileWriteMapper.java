package com.hrg.javamapper.write;

import com.hrg.model.MissionFile;
import com.hrg.model.MissionFileCriteria;
import org.apache.ibatis.annotations.Param;

public interface MissionFileWriteMapper {
    int deleteByExample(@Param("example") MissionFileCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") MissionFile record);

    int insertSelective(@Param("record") MissionFile record);

    int updateByExampleSelective(@Param("record") MissionFile record, @Param("example") MissionFileCriteria example);

    int updateByExample(@Param("record") MissionFile record, @Param("example") MissionFileCriteria example);

    int updateByPrimaryKeySelective(@Param("record") MissionFile record);

    int updateByPrimaryKey(@Param("record") MissionFile record);
}