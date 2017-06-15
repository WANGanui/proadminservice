package com.hrg.javamapper.write;

import com.hrg.model.PreRole;
import com.hrg.model.PreRoleCriteria;
import org.apache.ibatis.annotations.Param;

public interface PreRoleWriteMapper {
    int deleteByExample(@Param("example") PreRoleCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") PreRole record);

    int insertSelective(@Param("record") PreRole record);

    int updateByExampleSelective(@Param("record") PreRole record, @Param("example") PreRoleCriteria example);

    int updateByExample(@Param("record") PreRole record, @Param("example") PreRoleCriteria example);

    int updateByPrimaryKeySelective(@Param("record") PreRole record);

    int updateByPrimaryKey(@Param("record") PreRole record);
}