package com.hrg.javamapper.read;

import com.hrg.model.PreSysRole;
import com.hrg.model.PreSysRoleCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface PreSysRoleReadMapper {
    int countByExample(@Param("example") PreSysRoleCriteria example);

    List<PreSysRole> selectByExample(@Param("example") PreSysRoleCriteria example);

    PreSysRole selectByPrimaryKey(@Param("dataid") String dataid);

    PreSysRole selectByExampleForOne(@Param("example") PreSysRoleCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") PreSysRoleCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") PreSysRoleCriteria example);
}