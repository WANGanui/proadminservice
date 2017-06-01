package com.hrg.javamapper.write;

import com.hrg.model.HrgNotice;
import com.hrg.model.HrgNoticeCriteria;
import org.apache.ibatis.annotations.Param;

public interface HrgNoticeWriteMapper {
    int deleteByExample(@Param("example") HrgNoticeCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") HrgNotice record);

    int insertSelective(@Param("record") HrgNotice record);

    int updateByExampleSelective(@Param("record") HrgNotice record, @Param("example") HrgNoticeCriteria example);

    int updateByExample(@Param("record") HrgNotice record, @Param("example") HrgNoticeCriteria example);

    int updateByPrimaryKeySelective(@Param("record") HrgNotice record);

    int updateByPrimaryKey(@Param("record") HrgNotice record);
}