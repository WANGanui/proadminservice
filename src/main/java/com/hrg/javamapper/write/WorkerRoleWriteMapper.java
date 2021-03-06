package com.hrg.javamapper.write;

import com.hrg.model.WorkerRole;
import com.hrg.model.WorkerRoleCriteria;
import org.apache.ibatis.annotations.Param;

public interface WorkerRoleWriteMapper {
    int deleteByExample(@Param("example") WorkerRoleCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") WorkerRole record);

    int insertSelective(@Param("record") WorkerRole record);

    int updateByExampleSelective(@Param("record") WorkerRole record, @Param("example") WorkerRoleCriteria example);

    int updateByExample(@Param("record") WorkerRole record, @Param("example") WorkerRoleCriteria example);

    int updateByPrimaryKeySelective(@Param("record") WorkerRole record);

    int updateByPrimaryKey(@Param("record") WorkerRole record);
}