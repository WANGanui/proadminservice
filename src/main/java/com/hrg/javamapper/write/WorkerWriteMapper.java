package com.hrg.javamapper.write;

import com.hrg.model.Worker;
import com.hrg.model.WorkerCriteria;
import org.apache.ibatis.annotations.Param;

public interface WorkerWriteMapper {
    int deleteByExample(@Param("example") WorkerCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") Worker record);

    int insertSelective(@Param("record") Worker record);

    int updateByExampleSelective(@Param("record") Worker record, @Param("example") WorkerCriteria example);

    int updateByExample(@Param("record") Worker record, @Param("example") WorkerCriteria example);

    int updateByPrimaryKeySelective(@Param("record") Worker record);

    int updateByPrimaryKey(@Param("record") Worker record);
}