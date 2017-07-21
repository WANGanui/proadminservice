package com.hrg.javamapper.read;

import com.hrg.model.NoticeRelWorker;
import com.hrg.model.NoticeRelWorkerCriteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface NoticeRelWorkerReadMapper {
    int countByExample(@Param("example") NoticeRelWorkerCriteria example);

    List<NoticeRelWorker> selectByExample(@Param("example") NoticeRelWorkerCriteria example);

    NoticeRelWorker selectByPrimaryKey(@Param("dataid") String dataid);

    NoticeRelWorker selectByExampleForOne(@Param("example") NoticeRelWorkerCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") NoticeRelWorkerCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") NoticeRelWorkerCriteria example);
}