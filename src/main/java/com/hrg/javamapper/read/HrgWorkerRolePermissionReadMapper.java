package com.hrg.javamapper.read;

import com.hrg.model.HrgWorkerRolePermission;
import com.hrg.model.HrgWorkerRolePermissionCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface HrgWorkerRolePermissionReadMapper {
    int countByExample(@Param("example") HrgWorkerRolePermissionCriteria example);

    List<HrgWorkerRolePermission> selectByExample(@Param("example") HrgWorkerRolePermissionCriteria example);

    HrgWorkerRolePermission selectByPrimaryKey(@Param("dataid") String dataid);

    HrgWorkerRolePermission selectByExampleForOne(@Param("example") HrgWorkerRolePermissionCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") HrgWorkerRolePermissionCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") HrgWorkerRolePermissionCriteria example);
}