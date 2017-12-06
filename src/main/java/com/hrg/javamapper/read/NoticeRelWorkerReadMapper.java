package com.hrg.javamapper.read;

import com.hrg.model.NoticeRelWorker;
import com.hrg.model.NoticeRelWorkerCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NoticeRelWorkerReadMapper {
    int countByExample(@Param("example") NoticeRelWorkerCriteria example);

    List<NoticeRelWorker> selectByExample(@Param("example") NoticeRelWorkerCriteria example);

    NoticeRelWorker selectByPrimaryKey(@Param("dataid") String dataid);

    NoticeRelWorker selectByExampleForOne(@Param("example") NoticeRelWorkerCriteria example);

    List<Map<String, Object>> selectMapByExample(@Param("example") NoticeRelWorkerCriteria example);

    Map<String, Object> selectMapByPrimaryKey(@Param("dataid") String dataid);

    Map<String, Object> selectMapByExampleForOne(@Param("example") NoticeRelWorkerCriteria example);

    Map selectOneWorker(Map map);
}