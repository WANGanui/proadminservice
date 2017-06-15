package com.hrg.javamapper.read;

import com.hrg.model.Permission;
import com.hrg.model.PermissionCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface PermissionReadMapper {
    int countByExample(@Param("example") PermissionCriteria example);

    List<Permission> selectByExample(@Param("example") PermissionCriteria example);

    Permission selectByPrimaryKey(@Param("dataid") String dataid);

    Permission selectByExampleForOne(@Param("example") PermissionCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") PermissionCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") PermissionCriteria example);
}