package com.hrg.javamapper.write;

import com.hrg.model.NoticeRelWorker;
import com.hrg.model.NoticeRelWorkerCriteria;
import org.apache.ibatis.annotations.Param;

public interface NoticeRelWorkerWriteMapper {
    int deleteByExample(@Param("example") NoticeRelWorkerCriteria example);

    int deleteByPrimaryKey(@Param("dataid") String dataid);

    int insert(@Param("record") NoticeRelWorker record);

    int insertSelective(@Param("record") NoticeRelWorker record);

    int updateByExampleSelective(@Param("record") NoticeRelWorker record, @Param("example") NoticeRelWorkerCriteria example);

    int updateByExample(@Param("record") NoticeRelWorker record, @Param("example") NoticeRelWorkerCriteria example);

    int updateByPrimaryKeySelective(@Param("record") NoticeRelWorker record);

    int updateByPrimaryKey(@Param("record") NoticeRelWorker record);
}