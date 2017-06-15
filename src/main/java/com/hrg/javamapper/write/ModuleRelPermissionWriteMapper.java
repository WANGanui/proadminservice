package com.hrg.javamapper.write;

import com.hrg.model.ModuleRelPermission;
import com.hrg.model.ModuleRelPermissionCriteria;
import org.apache.ibatis.annotations.Param;

public interface ModuleRelPermissionWriteMapper {
    int deleteByExample(@Param("example") ModuleRelPermissionCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") ModuleRelPermission record);

    int insertSelective(@Param("record") ModuleRelPermission record);

    int updateByExampleSelective(@Param("record") ModuleRelPermission record, @Param("example") ModuleRelPermissionCriteria example);

    int updateByExample(@Param("record") ModuleRelPermission record, @Param("example") ModuleRelPermissionCriteria example);

    int updateByPrimaryKeySelective(@Param("record") ModuleRelPermission record);

    int updateByPrimaryKey(@Param("record") ModuleRelPermission record);
}