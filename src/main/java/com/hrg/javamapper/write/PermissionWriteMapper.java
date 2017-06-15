package com.hrg.javamapper.write;

import com.hrg.model.Permission;
import com.hrg.model.PermissionCriteria;
import org.apache.ibatis.annotations.Param;

public interface PermissionWriteMapper {
    int deleteByExample(@Param("example") PermissionCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") Permission record);

    int insertSelective(@Param("record") Permission record);

    int updateByExampleSelective(@Param("record") Permission record, @Param("example") PermissionCriteria example);

    int updateByExample(@Param("record") Permission record, @Param("example") PermissionCriteria example);

    int updateByPrimaryKeySelective(@Param("record") Permission record);

    int updateByPrimaryKey(@Param("record") Permission record);
}