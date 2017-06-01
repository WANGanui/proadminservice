package com.hrg.javamapper.read;

import com.hrg.model.HrgPreSysRoleRelPermission;
import com.hrg.model.HrgPreSysRoleRelPermissionCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface HrgPreSysRoleRelPermissionReadMapper {
    int countByExample(@Param("example") HrgPreSysRoleRelPermissionCriteria example);

    List<HrgPreSysRoleRelPermission> selectByExample(@Param("example") HrgPreSysRoleRelPermissionCriteria example);

    HrgPreSysRoleRelPermission selectByPrimaryKey(@Param("dataid") String dataid);

    HrgPreSysRoleRelPermission selectByExampleForOne(@Param("example") HrgPreSysRoleRelPermissionCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") HrgPreSysRoleRelPermissionCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") HrgPreSysRoleRelPermissionCriteria example);
}