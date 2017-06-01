package com.hrg.javamapper.write;

import com.hrg.model.PreSysPermission;
import com.hrg.model.PreSysPermissionCriteria;
import org.apache.ibatis.annotations.Param;

public interface PreSysPermissionWriteMapper {
    int deleteByExample(@Param("example") PreSysPermissionCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") PreSysPermission record);

    int insertSelective(@Param("record") PreSysPermission record);

    int updateByExampleSelective(@Param("record") PreSysPermission record, @Param("example") PreSysPermissionCriteria example);

    int updateByExample(@Param("record") PreSysPermission record, @Param("example") PreSysPermissionCriteria example);

    int updateByPrimaryKeySelective(@Param("record") PreSysPermission record);

    int updateByPrimaryKey(@Param("record") PreSysPermission record);
}