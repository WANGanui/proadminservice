package com.hrg.javamapper.write;

import com.hrg.model.ProjectAuditDel;
import com.hrg.model.ProjectAuditDelCriteria;
import org.apache.ibatis.annotations.Param;

public interface ProjectAuditDelWriteMapper {
    int deleteByExample(@Param("example") ProjectAuditDelCriteria example);

    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(@Param("record") ProjectAuditDel record);

    int insertSelective(@Param("record") ProjectAuditDel record);

    int updateByExampleSelective(@Param("record") ProjectAuditDel record, @Param("example") ProjectAuditDelCriteria example);

    int updateByExample(@Param("record") ProjectAuditDel record, @Param("example") ProjectAuditDelCriteria example);

    int updateByPrimaryKeySelective(@Param("record") ProjectAuditDel record);

    int updateByPrimaryKey(@Param("record") ProjectAuditDel record);

    void copy(@Param("prodataid") String prodataid);
}