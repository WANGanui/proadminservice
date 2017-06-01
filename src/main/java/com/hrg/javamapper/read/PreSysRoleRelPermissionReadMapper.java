package com.hrg.javamapper.read;

import com.hrg.model.PreSysRoleRelPermission;
import com.hrg.model.PreSysRoleRelPermissionCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface PreSysRoleRelPermissionReadMapper {
    int countByExample(@Param("example") PreSysRoleRelPermissionCriteria example);

    List<PreSysRoleRelPermission> selectByExample(@Param("example") PreSysRoleRelPermissionCriteria example);

    PreSysRoleRelPermission selectByPrimaryKey(@Param("dataid") String dataid);

    PreSysRoleRelPermission selectByExampleForOne(@Param("example") PreSysRoleRelPermissionCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") PreSysRoleRelPermissionCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") PreSysRoleRelPermissionCriteria example);
}