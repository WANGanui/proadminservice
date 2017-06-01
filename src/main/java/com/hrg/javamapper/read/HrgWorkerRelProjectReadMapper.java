package com.hrg.javamapper.read;

import com.hrg.model.HrgWorkerRelProject;
import com.hrg.model.HrgWorkerRelProjectCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface HrgWorkerRelProjectReadMapper {
    int countByExample(@Param("example") HrgWorkerRelProjectCriteria example);

    List<HrgWorkerRelProject> selectByExample(@Param("example") HrgWorkerRelProjectCriteria example);

    HrgWorkerRelProject selectByPrimaryKey(@Param("dataid") String dataid);

    HrgWorkerRelProject selectByExampleForOne(@Param("example") HrgWorkerRelProjectCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") HrgWorkerRelProjectCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") HrgWorkerRelProjectCriteria example);
}