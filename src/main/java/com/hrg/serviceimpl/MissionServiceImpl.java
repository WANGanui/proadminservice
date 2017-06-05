package com.hrg.serviceimpl;

import com.hrg.enums.ErrorCode;
import com.hrg.exception.ValidatorException;
import com.hrg.javamapper.read.MissionReadMapper;
import com.hrg.javamapper.read.WorkerRelMissionReadMapper;
import com.hrg.javamapper.write.MissionWriteMapper;
import com.hrg.javamapper.write.WorkerRelMissionWriteMapper;
import com.hrg.model.*;
import com.hrg.service.MissionService;
import com.hrg.util.PageUtil;
import com.hrg.util.UUIDGenerator;
import com.hrg.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by 82705 on 2017/6/1.
 */
@Service("missionServiceImpl")
public class MissionServiceImpl implements MissionService {

    @Autowired
    MissionReadMapper missionReadMapper;
    @Autowired
    MissionWriteMapper missionWriteMapper;
    @Autowired
    WorkerRelMissionReadMapper workerRelMissionReadMapper;
    @Autowired
    WorkerRelMissionWriteMapper workerRelMissionWriteMapper;

    /**
     * 条件查询任务列表
     *
     * @param example 条件实体
     * @return
     * @throws Exception
     */
    @Override
    public List<Mission> selectList(MissionCriteria example) throws Exception {
        if (ValidUtil.isNullOrEmpty(example))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());

        return missionReadMapper.selectByExample(example);
    }

    /**
     * 根据主键查询任务详情
     *
     * @param dataid
     * @return
     * @throws Exception
     */
    @Override
    public Mission selectDetailById(String dataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(dataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());

        return missionReadMapper.selectByPrimaryKey(dataid);
    }

    /**
     * 创建任务
     *
     * @param mission 任务实体
     * @param workerList  任务人员集合
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public boolean insert(Mission mission, List<Worker> workerList) throws Exception {
        if (ValidUtil.isNullOrEmpty(mission.getContext()) || ValidUtil.isNullOrEmpty(mission.getCreator()) ||
                ValidUtil.isNullOrEmpty(mission.getEndtime()) || ValidUtil.isNullOrEmpty(mission.getCreatordataid()) ||
                ValidUtil.isNullOrEmpty(mission.getName()) || ValidUtil.isNullOrEmpty(mission.getProdataid()) ||
                ValidUtil.isNullOrEmpty(mission.getProportion()) || ValidUtil.isNullOrEmpty(mission.getStarttime()) ||
                ValidUtil.isNullOrEmpty(workerList))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        mission.setDataid(UUIDGenerator.getUUID());
        mission.setCreatetime(new Date());
        mission.setState("0");
        int x = missionWriteMapper.insert(mission);
        for (Worker worker : workerList){
            WorkerRelMission relMission = new WorkerRelMission();
            relMission.setDataid(UUIDGenerator.getUUID());
            relMission.setWorkerdataid(worker.getDataid());
            relMission.setWorkername(worker.getName());
            relMission.setMissiondataid(mission.getDataid());
            relMission.setMissionname(mission.getName());
            relMission.setSchedule("0%");
            int y = workerRelMissionWriteMapper.insert(relMission);
            if (y <= 0)
                return false;
        }
        return x > 0 ? true : false;
    }

    /**
     * 修改任务
     *
     * @param mission 任务实体
     * @param workerList  任务人员
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public boolean update(Mission mission, List<Worker> workerList) throws Exception {
        if (ValidUtil.isNullOrEmpty(mission.getContext()) || ValidUtil.isNullOrEmpty(mission.getCreator()) ||
                ValidUtil.isNullOrEmpty(mission.getEndtime()) || ValidUtil.isNullOrEmpty(mission.getCreatordataid()) ||
                ValidUtil.isNullOrEmpty(mission.getName()) || ValidUtil.isNullOrEmpty(mission.getProdataid()) ||
                ValidUtil.isNullOrEmpty(mission.getProportion()) || ValidUtil.isNullOrEmpty(mission.getStarttime()) ||
                ValidUtil.isNullOrEmpty(workerList)|| ValidUtil.isNullOrEmpty(mission.getModify()) ||
                ValidUtil.isNullOrEmpty(mission.getModifydataid()))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        mission.setModifytime(new Date());
        WorkerRelMissionCriteria example = new WorkerRelMissionCriteria();
        example.setMissiondataid(mission.getDataid());
        int x = workerRelMissionWriteMapper.deleteByExample(example);

        return x > 0 ? true : false;
    }

    /**
     * 分页查询任务列表（）
     *
     * @param example  条件实体
     * @param pageUtil 分页实体
     * @return
     * @throws Exception
     */
    @Override
    public PageUtil<Mission> selectByExample(MissionCriteria example, PageUtil pageUtil) throws Exception {
        if (pageUtil.getCurrentPage() !=null && pageUtil.getCurrentPage() <= 0)
            throw new ValidatorException(ErrorCode.ILLEGALARGUMENT_EXCEPTION.getCode());

        if (pageUtil.getPageSize() !=null && pageUtil.getPageSize() <= 0)
            throw new ValidatorException(ErrorCode.ILLEGALARGUMENT_EXCEPTION.getCode());

        pageUtil.setCurrentPage(pageUtil.getCurrentPage() == null ? 1 : pageUtil.getCurrentPage());

        if ("".equals(example.getState()))
            example.setState(null);

        //设置分页参数
        example.setOrderByClause(" createtime DESC，proportion DESC ");
        example.setPageSize((pageUtil.getPageSize() == null) ? 8 : pageUtil.getPageSize());
        example.setLimitStart((pageUtil.getCurrentPage()  == null) ? 0 : (pageUtil.getCurrentPage()-1) * 8);
        List<Mission> missionList = missionReadMapper.selectByExample(example);

        int count = missionReadMapper.countByExample(example);
        PageUtil<Mission> missionPageUtil = new PageUtil<Mission>(pageUtil.getPageSize(),count);
        missionPageUtil.generate(pageUtil.getCurrentPage());
        missionPageUtil.setPageResults(missionList);
        return missionPageUtil;
    }
}
