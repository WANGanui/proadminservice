package com.hrg.javamapper.read;

import com.hrg.model.HrgWorkerRole;
import com.hrg.model.HrgWorkerRoleCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface HrgWorkerRoleReadMapper {
    int countByExample(@Param("example") HrgWorkerRoleCriteria example);

    List<HrgWorkerRole> selectByExample(@Param("example") HrgWorkerRoleCriteria example);

    HrgWorkerRole selectByExampleForOne(@Param("example") HrgWorkerRoleCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") HrgWorkerRoleCriteria example);

    Map<String, Object> selectMapByExampleForOne(@Param("example") HrgWorkerRoleCriteria example);
}