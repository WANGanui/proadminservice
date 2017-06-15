package com.hrg.javamapper.read;

import com.hrg.model.Module;
import com.hrg.model.ModuleCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface ModuleReadMapper {
    int countByExample(@Param("example") ModuleCriteria example);

    List<Module> selectByExample(@Param("example") ModuleCriteria example);

    Module selectByPrimaryKey(@Param("datatid") String datatid);

    Module selectByExampleForOne(@Param("example") ModuleCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") ModuleCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("datatid") String datatid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") ModuleCriteria example);
}