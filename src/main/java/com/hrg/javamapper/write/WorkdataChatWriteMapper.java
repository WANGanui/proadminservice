package com.hrg.javamapper.write;

import com.hrg.model.WorkdataChat;
import com.hrg.model.WorkdataChatCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface WorkdataChatWriteMapper {
    int deleteByExample(@Param("example") WorkdataChatCriteria example);

    int deleteByPrimaryKey(@Param("dataid") Integer dataid);

    int insert(@Param("record") WorkdataChat record);

    int insertSelective(@Param("record") WorkdataChat record);

    int updateByExampleSelective(@Param("record") WorkdataChat record, @Param("example") WorkdataChatCriteria example);

    int updateByExample(@Param("record") WorkdataChat record, @Param("example") WorkdataChatCriteria example);

    int updateByPrimaryKeySelective(@Param("record") WorkdataChat record);

    int updateByPrimaryKey(@Param("record") WorkdataChat record);

    int updateIsread(Map map);
}