package com.hrg.javamapper.write;

import com.hrg.model.HrgPreSysRoleRelPermission;
import com.hrg.model.HrgPreSysRoleRelPermissionCriteria;
import org.apache.ibatis.annotations.Param;

public interface HrgPreSysRoleRelPermissionWriteMapper {
    int deleteByExample(@Param("example") HrgPreSysRoleRelPermissionCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") HrgPreSysRoleRelPermission record);

    int insertSelective(@Param("record") HrgPreSysRoleRelPermission record);

    int updateByExampleSelective(@Param("record") HrgPreSysRoleRelPermission record, @Param("example") HrgPreSysRoleRelPermissionCriteria example);

    int updateByExample(@Param("record") HrgPreSysRoleRelPermission record, @Param("example") HrgPreSysRoleRelPermissionCriteria example);

    int updateByPrimaryKeySelective(@Param("record") HrgPreSysRoleRelPermission record);

    int updateByPrimaryKey(@Param("record") HrgPreSysRoleRelPermission record);
}