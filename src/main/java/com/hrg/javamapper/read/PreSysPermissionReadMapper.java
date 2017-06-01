package com.hrg.javamapper.read;

import com.hrg.model.PreSysPermission;
import com.hrg.model.PreSysPermissionCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface PreSysPermissionReadMapper {
    int countByExample(@Param("example") PreSysPermissionCriteria example);

    List<PreSysPermission> selectByExample(@Param("example") PreSysPermissionCriteria example);

    PreSysPermission selectByPrimaryKey(@Param("dataid") String dataid);

    PreSysPermission selectByExampleForOne(@Param("example") PreSysPermissionCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") PreSysPermissionCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") PreSysPermissionCriteria example);
}