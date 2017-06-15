package com.hrg.javamapper.read;

import com.hrg.model.ModuleRelPermission;
import com.hrg.model.ModuleRelPermissionCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface ModuleRelPermissionReadMapper {
    int countByExample(@Param("example") ModuleRelPermissionCriteria example);

    List<ModuleRelPermission> selectByExample(@Param("example") ModuleRelPermissionCriteria example);

    ModuleRelPermission selectByPrimaryKey(@Param("dataid") String dataid);

    ModuleRelPermission selectByExampleForOne(@Param("example") ModuleRelPermissionCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") ModuleRelPermissionCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") ModuleRelPermissionCriteria example);
}