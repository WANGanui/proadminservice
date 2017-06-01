package com.hrg.javamapper.write;

import com.hrg.model.WorkerRelProject;
import com.hrg.model.WorkerRelProjectCriteria;
import org.apache.ibatis.annotations.Param;

public interface WorkerRelProjectWriteMapper {
    int deleteByExample(@Param("example") WorkerRelProjectCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") WorkerRelProject record);

    int insertSelective(@Param("record") WorkerRelProject record);

    int updateByExampleSelective(@Param("record") WorkerRelProject record, @Param("example") WorkerRelProjectCriteria example);

    int updateByExample(@Param("record") WorkerRelProject record, @Param("example") WorkerRelProjectCriteria example);

    int updateByPrimaryKeySelective(@Param("record") WorkerRelProject record);

    int updateByPrimaryKey(@Param("record") WorkerRelProject record);
}