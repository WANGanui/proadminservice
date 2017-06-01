package com.hrg.javamapper.write;

import com.hrg.model.PreSysRole;
import com.hrg.model.PreSysRoleCriteria;
import org.apache.ibatis.annotations.Param;

public interface PreSysRoleWriteMapper {
    int deleteByExample(@Param("example") PreSysRoleCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") PreSysRole record);

    int insertSelective(@Param("record") PreSysRole record);

    int updateByExampleSelective(@Param("record") PreSysRole record, @Param("example") PreSysRoleCriteria example);

    int updateByExample(@Param("record") PreSysRole record, @Param("example") PreSysRoleCriteria example);

    int updateByPrimaryKeySelective(@Param("record") PreSysRole record);

    int updateByPrimaryKey(@Param("record") PreSysRole record);
}