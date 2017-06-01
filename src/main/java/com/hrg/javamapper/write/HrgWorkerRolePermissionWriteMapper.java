package com.hrg.javamapper.write;

import com.hrg.model.HrgWorkerRolePermission;
import com.hrg.model.HrgWorkerRolePermissionCriteria;
import org.apache.ibatis.annotations.Param;

public interface HrgWorkerRolePermissionWriteMapper {
    int deleteByExample(@Param("example") HrgWorkerRolePermissionCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") HrgWorkerRolePermission record);

    int insertSelective(@Param("record") HrgWorkerRolePermission record);

    int updateByExampleSelective(@Param("record") HrgWorkerRolePermission record, @Param("example") HrgWorkerRolePermissionCriteria example);

    int updateByExample(@Param("record") HrgWorkerRolePermission record, @Param("example") HrgWorkerRolePermissionCriteria example);

    int updateByPrimaryKeySelective(@Param("record") HrgWorkerRolePermission record);

    int updateByPrimaryKey(@Param("record") HrgWorkerRolePermission record);
}