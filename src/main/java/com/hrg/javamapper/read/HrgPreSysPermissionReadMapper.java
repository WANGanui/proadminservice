package com.hrg.javamapper.read;

import com.hrg.model.HrgPreSysPermission;
import com.hrg.model.HrgPreSysPermissionCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface HrgPreSysPermissionReadMapper {
    int countByExample(@Param("example") HrgPreSysPermissionCriteria example);

    List<HrgPreSysPermission> selectByExample(@Param("example") HrgPreSysPermissionCriteria example);

    HrgPreSysPermission selectByPrimaryKey(@Param("dataid") String dataid);

    HrgPreSysPermission selectByExampleForOne(@Param("example") HrgPreSysPermissionCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") HrgPreSysPermissionCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") HrgPreSysPermissionCriteria example);
}