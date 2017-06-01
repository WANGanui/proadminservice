package com.hrg.javamapper.read;

import com.hrg.model.WorkerRolePermission;
import com.hrg.model.WorkerRolePermissionCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface WorkerRolePermissionReadMapper {
    int countByExample(@Param("example") WorkerRolePermissionCriteria example);

    List<WorkerRolePermission> selectByExample(@Param("example") WorkerRolePermissionCriteria example);

    WorkerRolePermission selectByPrimaryKey(@Param("dataid") String dataid);

    WorkerRolePermission selectByExampleForOne(@Param("example") WorkerRolePermissionCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") WorkerRolePermissionCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") WorkerRolePermissionCriteria example);
}