package com.hrg.javamapper.write;

import com.hrg.model.HrgPreSysRole;
import com.hrg.model.HrgPreSysRoleCriteria;
import org.apache.ibatis.annotations.Param;

public interface HrgPreSysRoleWriteMapper {
    int deleteByExample(@Param("example") HrgPreSysRoleCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") HrgPreSysRole record);

    int insertSelective(@Param("record") HrgPreSysRole record);

    int updateByExampleSelective(@Param("record") HrgPreSysRole record, @Param("example") HrgPreSysRoleCriteria example);

    int updateByExample(@Param("record") HrgPreSysRole record, @Param("example") HrgPreSysRoleCriteria example);

    int updateByPrimaryKeySelective(@Param("record") HrgPreSysRole record);

    int updateByPrimaryKey(@Param("record") HrgPreSysRole record);
}