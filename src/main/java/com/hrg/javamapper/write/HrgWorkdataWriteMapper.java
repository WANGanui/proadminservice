package com.hrg.javamapper.write;

import com.hrg.model.HrgWorkdata;
import com.hrg.model.HrgWorkdataCriteria;
import org.apache.ibatis.annotations.Param;

public interface HrgWorkdataWriteMapper {
    int deleteByExample(@Param("example") HrgWorkdataCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") HrgWorkdata record);

    int insertSelective(@Param("record") HrgWorkdata record);

    int updateByExampleSelective(@Param("record") HrgWorkdata record, @Param("example") HrgWorkdataCriteria example);

    int updateByExample(@Param("record") HrgWorkdata record, @Param("example") HrgWorkdataCriteria example);

    int updateByPrimaryKeySelective(@Param("record") HrgWorkdata record);

    int updateByPrimaryKey(@Param("record") HrgWorkdata record);
}