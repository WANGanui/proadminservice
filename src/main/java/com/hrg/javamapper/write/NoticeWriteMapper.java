package com.hrg.javamapper.write;

import com.hrg.model.Notice;
import com.hrg.model.NoticeCriteria;
import org.apache.ibatis.annotations.Param;

public interface NoticeWriteMapper {
    int deleteByExample(@Param("example") NoticeCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") Notice record);

    int insertSelective(@Param("record") Notice record);

    int updateByExampleSelective(@Param("record") Notice record, @Param("example") NoticeCriteria example);

    int updateByExample(@Param("record") Notice record, @Param("example") NoticeCriteria example);

    int updateByPrimaryKeySelective(@Param("record") Notice record);

    int updateByPrimaryKey(@Param("record") Notice record);
}