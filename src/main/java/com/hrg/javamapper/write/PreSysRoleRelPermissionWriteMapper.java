package com.hrg.javamapper.write;

import com.hrg.model.PreSysRoleRelPermission;
import com.hrg.model.PreSysRoleRelPermissionCriteria;
import org.apache.ibatis.annotations.Param;

public interface PreSysRoleRelPermissionWriteMapper {
    int deleteByExample(@Param("example") PreSysRoleRelPermissionCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") PreSysRoleRelPermission record);

    int insertSelective(@Param("record") PreSysRoleRelPermission record);

    int updateByExampleSelective(@Param("record") PreSysRoleRelPermission record, @Param("example") PreSysRoleRelPermissionCriteria example);

    int updateByExample(@Param("record") PreSysRoleRelPermission record, @Param("example") PreSysRoleRelPermissionCriteria example);

    int updateByPrimaryKeySelective(@Param("record") PreSysRoleRelPermission record);

    int updateByPrimaryKey(@Param("record") PreSysRoleRelPermission record);
}