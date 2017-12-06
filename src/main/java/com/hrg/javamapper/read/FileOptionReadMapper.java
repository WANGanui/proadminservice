package com.hrg.javamapper.read;

import com.hrg.model.FileOption;
import com.hrg.model.FileOptionCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface FileOptionReadMapper {
    int countByExample(@Param("example") FileOptionCriteria example);

    List<FileOption> selectByExample(@Param("example") FileOptionCriteria example);

    FileOption selectByPrimaryKey(@Param("dataid") String dataid);

    FileOption selectByExampleForOne(@Param("example") FileOptionCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") FileOptionCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") FileOptionCriteria example);
}