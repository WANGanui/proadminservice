package com.hrg.serviceimpl;

import com.hrg.javamapper.read.MissionAuditReadMapper;
import com.hrg.model.MissionAudit;
import com.hrg.model.MissionAuditCriteria;
import com.hrg.service.MissionAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 82705 on 2017/7/21.
 */
@Service("missionAuditServiceImpl")
public class MissionAuditServiceImpl implements MissionAuditService{

    @Autowired
    MissionAuditReadMapper missionAuditReadMapper;

    /**
     * 查询集合
     *
     * @param example
     * @return
     * @throws Exception
     */
    @Override
    public List<MissionAudit> selectList(MissionAuditCriteria example) throws Exception {
        return missionAuditReadMapper.selectByExample(example);
    }
}
