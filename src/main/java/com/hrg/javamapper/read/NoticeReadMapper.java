package com.hrg.javamapper.read;

import com.hrg.model.Notice;
import com.hrg.model.NoticeCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface NoticeReadMapper {
    int countByExample(@Param("example") NoticeCriteria example);

    List<Notice> selectByExample(@Param("example") NoticeCriteria example);

    Notice selectByPrimaryKey(@Param("dataid") String dataid);

    Notice selectByExampleForOne(@Param("example") NoticeCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") NoticeCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") NoticeCriteria example);
}