package com.hrg.javamapper.read;

import com.hrg.model.WorkerRole;
import com.hrg.model.WorkerRoleCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface WorkerRoleReadMapper {
    int countByExample(@Param("example") WorkerRoleCriteria example);

    List<WorkerRole> selectByExample(@Param("example") WorkerRoleCriteria example);

    WorkerRole selectByPrimaryKey(@Param("dataid") String dataid);

    WorkerRole selectByExampleForOne(@Param("example") WorkerRoleCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") WorkerRoleCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") WorkerRoleCriteria example);
}