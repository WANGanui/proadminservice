package com.hrg.javamapper.write;

import com.hrg.model.WorkerRolePermission;
import com.hrg.model.WorkerRolePermissionCriteria;
import org.apache.ibatis.annotations.Param;

public interface WorkerRolePermissionWriteMapper {
    int deleteByExample(@Param("example") WorkerRolePermissionCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") WorkerRolePermission record);

    int insertSelective(@Param("record") WorkerRolePermission record);

    int updateByExampleSelective(@Param("record") WorkerRolePermission record, @Param("example") WorkerRolePermissionCriteria example);

    int updateByExample(@Param("record") WorkerRolePermission record, @Param("example") WorkerRolePermissionCriteria example);

    int updateByPrimaryKeySelective(@Param("record") WorkerRolePermission record);

    int updateByPrimaryKey(@Param("record") WorkerRolePermission record);
}