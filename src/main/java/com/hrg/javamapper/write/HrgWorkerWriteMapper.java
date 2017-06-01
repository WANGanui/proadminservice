package com.hrg.javamapper.write;

import com.hrg.model.HrgWorker;
import com.hrg.model.HrgWorkerCriteria;
import org.apache.ibatis.annotations.Param;

public interface HrgWorkerWriteMapper {
    int deleteByExample(@Param("example") HrgWorkerCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") HrgWorker record);

    int insertSelective(@Param("record") HrgWorker record);

    int updateByExampleSelective(@Param("record") HrgWorker record, @Param("example") HrgWorkerCriteria example);

    int updateByExample(@Param("record") HrgWorker record, @Param("example") HrgWorkerCriteria example);

    int updateByPrimaryKeySelective(@Param("record") HrgWorker record);

    int updateByPrimaryKey(@Param("record") HrgWorker record);
}