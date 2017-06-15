package com.hrg.javamapper.write;

import com.hrg.model.Module;
import com.hrg.model.ModuleCriteria;
import org.apache.ibatis.annotations.Param;

public interface ModuleWriteMapper {
    int deleteByExample(@Param("example") ModuleCriteria example);

    int deleteByPrimaryKey(@Param("datatid") String datatid);

    int insert(@Param("record") Module record);

    int insertSelective(@Param("record") Module record);

    int updateByExampleSelective(@Param("record") Module record, @Param("example") ModuleCriteria example);

    int updateByExample(@Param("record") Module record, @Param("example") ModuleCriteria example);

    int updateByPrimaryKeySelective(@Param("record") Module record);

    int updateByPrimaryKey(@Param("record") Module record);
}