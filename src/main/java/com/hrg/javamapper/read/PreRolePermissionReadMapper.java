package com.hrg.javamapper.read;

import com.hrg.model.PreRolePermission;
import com.hrg.model.PreRolePermissionCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface PreRolePermissionReadMapper {
    int countByExample(@Param("example") PreRolePermissionCriteria example);

    List<PreRolePermission> selectByExample(@Param("example") PreRolePermissionCriteria example);

    PreRolePermission selectByPrimaryKey(@Param("dataid") String dataid);

    PreRolePermission selectByExampleForOne(@Param("example") PreRolePermissionCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") PreRolePermissionCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") PreRolePermissionCriteria example);
}