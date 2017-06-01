package com.hrg.javamapper.write;

import com.hrg.model.HrgWorkerRelProject;
import com.hrg.model.HrgWorkerRelProjectCriteria;
import org.apache.ibatis.annotations.Param;

public interface HrgWorkerRelProjectWriteMapper {
    int deleteByExample(@Param("example") HrgWorkerRelProjectCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") HrgWorkerRelProject record);

    int insertSelective(@Param("record") HrgWorkerRelProject record);

    int updateByExampleSelective(@Param("record") HrgWorkerRelProject record, @Param("example") HrgWorkerRelProjectCriteria example);

    int updateByExample(@Param("record") HrgWorkerRelProject record, @Param("example") HrgWorkerRelProjectCriteria example);

    int updateByPrimaryKeySelective(@Param("record") HrgWorkerRelProject record);

    int updateByPrimaryKey(@Param("record") HrgWorkerRelProject record);
}