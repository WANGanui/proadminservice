package com.hrg.javamapper.write;

import com.hrg.model.HrgProject;
import com.hrg.model.HrgProjectCriteria;
import org.apache.ibatis.annotations.Param;

public interface HrgProjectWriteMapper {
    int deleteByExample(@Param("example") HrgProjectCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") HrgProject record);

    int insertSelective(@Param("record") HrgProject record);

    int updateByExampleSelective(@Param("record") HrgProject record, @Param("example") HrgProjectCriteria example);

    int updateByExample(@Param("record") HrgProject record, @Param("example") HrgProjectCriteria example);

    int updateByPrimaryKeySelective(@Param("record") HrgProject record);

    int updateByPrimaryKey(@Param("record") HrgProject record);
}