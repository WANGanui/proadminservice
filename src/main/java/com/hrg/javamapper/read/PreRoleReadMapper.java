package com.hrg.javamapper.read;

import com.hrg.model.PreRole;
import com.hrg.model.PreRoleCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface PreRoleReadMapper {
    int countByExample(@Param("example") PreRoleCriteria example);

    List<PreRole> selectByExample(@Param("example") PreRoleCriteria example);

    PreRole selectByPrimaryKey(@Param("dataid") String dataid);

    PreRole selectByExampleForOne(@Param("example") PreRoleCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") PreRoleCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") PreRoleCriteria example);
}