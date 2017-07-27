package com.hrg.serviceimpl;

import com.hrg.javamapper.read.MissionAuditReadMapper;
import com.hrg.javamapper.write.MissionAuditWriteMapper;
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
    @Autowired
    MissionAuditWriteMapper missionAuditWriteMapper;

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

    /**
     * 修改
     *
     * @param missionAudit
     * @param example
     * @return
     * @throws Exception
     */
    @Override
    public boolean update(MissionAudit missionAudit, MissionAuditCriteria example) throws Exception {
        int x = missionAuditWriteMapper.updateByExampleSelective(missionAudit,example);
        return x > 0 ? true : false;
    }

    /**
     * 查条数
     *
     * @param example
     * @return
     * @throws Exception
     */
    @Override
    public int count(MissionAuditCriteria example) throws Exception {
        return missionAuditReadMapper.countByExample(example);
    }
}
