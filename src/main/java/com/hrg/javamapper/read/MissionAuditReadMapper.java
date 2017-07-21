package com.hrg.javamapper.read;

import com.hrg.model.MissionAudit;
import com.hrg.model.MissionAuditCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface MissionAuditReadMapper {
    int countByExample(@Param("example") MissionAuditCriteria example);

    List<MissionAudit> selectByExample(@Param("example") MissionAuditCriteria example);

    MissionAudit selectByPrimaryKey(@Param("id") Integer id);

    MissionAudit selectByExampleForOne(@Param("example") MissionAuditCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") MissionAuditCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("id") Integer id);

    Map<String, Object> selectMapByExampleForOne(@Param("example") MissionAuditCriteria example);
}