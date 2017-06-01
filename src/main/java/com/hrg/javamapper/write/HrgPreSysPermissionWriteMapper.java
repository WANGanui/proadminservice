package com.hrg.javamapper.write;

import com.hrg.model.HrgPreSysPermission;
import com.hrg.model.HrgPreSysPermissionCriteria;
import org.apache.ibatis.annotations.Param;

public interface HrgPreSysPermissionWriteMapper {
    int deleteByExample(@Param("example") HrgPreSysPermissionCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") HrgPreSysPermission record);

    int insertSelective(@Param("record") HrgPreSysPermission record);

    int updateByExampleSelective(@Param("record") HrgPreSysPermission record, @Param("example") HrgPreSysPermissionCriteria example);

    int updateByExample(@Param("record") HrgPreSysPermission record, @Param("example") HrgPreSysPermissionCriteria example);

    int updateByPrimaryKeySelective(@Param("record") HrgPreSysPermission record);

    int updateByPrimaryKey(@Param("record") HrgPreSysPermission record);
}