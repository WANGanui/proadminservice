package com.hrg.javamapper.read;

import com.hrg.model.WorkdataChat;
import com.hrg.model.WorkdataChatCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface WorkdataChatReadMapper {
    int countByExample(@Param("example") WorkdataChatCriteria example);

    List<WorkdataChat> selectByExample(@Param("example") WorkdataChatCriteria example);

    WorkdataChat selectByPrimaryKey(@Param("dataid") Integer dataid);

    WorkdataChat selectByExampleForOne(@Param("example") WorkdataChatCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") WorkdataChatCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") Integer dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") WorkdataChatCriteria example);
}