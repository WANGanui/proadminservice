package com.hrg.javamapper.write;

import com.hrg.model.FileOption;
import com.hrg.model.FileOptionCriteria;
import org.apache.ibatis.annotations.Param;

public interface FileOptionWriteMapper {
    int deleteByExample(@Param("example") FileOptionCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") FileOption record);

    int insertSelective(@Param("record") FileOption record);

    int updateByExampleSelective(@Param("record") FileOption record, @Param("example") FileOptionCriteria example);

    int updateByExample(@Param("record") FileOption record, @Param("example") FileOptionCriteria example);

    int updateByPrimaryKeySelective(@Param("record") FileOption record);

    int updateByPrimaryKey(@Param("record") FileOption record);
}