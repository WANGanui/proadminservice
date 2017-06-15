package com.hrg.javamapper.write;

import com.hrg.model.PreRolePermission;
import com.hrg.model.PreRolePermissionCriteria;
import org.apache.ibatis.annotations.Param;

public interface PreRolePermissionWriteMapper {
    int deleteByExample(@Param("example") PreRolePermissionCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") PreRolePermission record);

    int insertSelective(@Param("record") PreRolePermission record);

    int updateByExampleSelective(@Param("record") PreRolePermission record, @Param("example") PreRolePermissionCriteria example);

    int updateByExample(@Param("record") PreRolePermission record, @Param("example") PreRolePermissionCriteria example);

    int updateByPrimaryKeySelective(@Param("record") PreRolePermission record);

    int updateByPrimaryKey(@Param("record") PreRolePermission record);
}