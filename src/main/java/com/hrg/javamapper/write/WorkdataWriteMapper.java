package com.hrg.javamapper.write;

import com.hrg.model.Workdata;
import com.hrg.model.WorkdataCriteria;
import org.apache.ibatis.annotations.Param;

public interface WorkdataWriteMapper {
    int deleteByExample(@Param("example") WorkdataCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") Workdata record);

    int insertSelective(@Param("record") Workdata record);

    int updateByExampleSelective(@Param("record") Workdata record, @Param("example") WorkdataCriteria example);

    int updateByExample(@Param("record") Workdata record, @Param("example") WorkdataCriteria example);

    int updateByPrimaryKeySelective(@Param("record") Workdata record);

    int updateByPrimaryKey(@Param("record") Workdata record);
}