package com.hrg.javamapper.read;

import com.hrg.model.HrgPreSysRole;
import com.hrg.model.HrgPreSysRoleCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface HrgPreSysRoleReadMapper {
    int countByExample(@Param("example") HrgPreSysRoleCriteria example);

    List<HrgPreSysRole> selectByExample(@Param("example") HrgPreSysRoleCriteria example);

    HrgPreSysRole selectByPrimaryKey(@Param("dataid") String dataid);

    HrgPreSysRole selectByExampleForOne(@Param("example") HrgPreSysRoleCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") HrgPreSysRoleCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") HrgPreSysRoleCriteria example);
}