package com.hrg.javamapper.read;

import com.hrg.model.HrgNotice;
import com.hrg.model.HrgNoticeCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface HrgNoticeReadMapper {
    int countByExample(@Param("example") HrgNoticeCriteria example);

    List<HrgNotice> selectByExample(@Param("example") HrgNoticeCriteria example);

    HrgNotice selectByPrimaryKey(@Param("dataid") String dataid);

    HrgNotice selectByExampleForOne(@Param("example") HrgNoticeCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") HrgNoticeCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") HrgNoticeCriteria example);
}