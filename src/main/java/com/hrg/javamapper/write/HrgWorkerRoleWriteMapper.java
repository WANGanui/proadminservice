package com.hrg.javamapper.write;

import com.hrg.model.HrgWorkerRole;
import com.hrg.model.HrgWorkerRoleCriteria;
import org.apache.ibatis.annotations.Param;

public interface HrgWorkerRoleWriteMapper {
    int deleteByExample(@Param("example") HrgWorkerRoleCriteria example);

    int insert(@Param("record") HrgWorkerRole record);

    int insertSelective(@Param("record") HrgWorkerRole record);

    int updateByExampleSelective(@Param("record") HrgWorkerRole record, @Param("example") HrgWorkerRoleCriteria example);

    int updateByExample(@Param("record") HrgWorkerRole record, @Param("example") HrgWorkerRoleCriteria example);
}