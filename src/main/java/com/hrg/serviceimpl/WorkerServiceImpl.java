package com.hrg.serviceimpl;

import com.hrg.enums.ErrorCode;
import com.hrg.exception.ValidatorException;
import com.hrg.javamapper.read.WorkdataReadMapper;
import com.hrg.javamapper.read.WorkerReadMapper;
import com.hrg.javamapper.read.WorkerRelMissionReadMapper;
import com.hrg.model.*;
import com.hrg.service.WorkerService;
import com.hrg.util.DateUtil;
import com.hrg.util.PageUtil;
import com.hrg.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by 82705 on 2017/6/1.
 */
@Service("workerService")
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    WorkerReadMapper workerReadMapper;
    @Autowired
    WorkerRelMissionReadMapper workerRelMissionReadMapper;
    @Autowired
    WorkdataReadMapper workdataReadMapper;

    /**
     * 方法说明：根据员工账号查询员工对象
     *
     * @param account 账号
     * @return
     * @throws Exception
     */
    @Override
    public Worker getWorkerInfo(String account) throws Exception {
        if (ValidUtil.isNullOrEmpty(account))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        WorkerCriteria example = new WorkerCriteria();
        example.setAccount(account);
        return workerReadMapper.selectByExampleForOne(example);
    }

    /**
     * 方法说明：根据账号获取密码
     *
     * @param account 账号
     * @return
     * @throws Exception
     */
    @Override
    public String getWorkerPassWord(String account) throws Exception {
        if (ValidUtil.isNullOrEmpty(account))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        WorkerCriteria example = new WorkerCriteria();
        example.setAccount(account);
        Worker worker = workerReadMapper.selectByExampleForOne(example);
        return worker.getPassword();
    }

    /**
     * 分页查询员工列表
     *
     * @param example  条件实体
     * @param pageUtil 分页条件
     * @return
     * @throws Exception
     */
    @Override
    public PageUtil<Worker> selectByExample(WorkerCriteria example, PageUtil pageUtil) throws Exception {
        if (pageUtil.getCurrentPage() !=null && pageUtil.getCurrentPage() <= 0)
            throw new ValidatorException(ErrorCode.ILLEGALARGUMENT_EXCEPTION.getCode());

        if (pageUtil.getPageSize() !=null && pageUtil.getPageSize() <= 0)
            throw new ValidatorException(ErrorCode.ILLEGALARGUMENT_EXCEPTION.getCode());

        pageUtil.setCurrentPage(pageUtil.getCurrentPage() == null ? 1 : pageUtil.getCurrentPage());

        if ("".equals(example.getState()))
            example.setState(null);

        //设置分页参数
        example.setPageSize((pageUtil.getPageSize() == null) ? 8 : pageUtil.getPageSize());
        example.setLimitStart((pageUtil.getCurrentPage()  == null) ? 0 : (pageUtil.getCurrentPage()-1) * 8);
        List<Worker> workerList = workerReadMapper.selectByExample(example);

        int count = workerReadMapper.countByExample(example);
        PageUtil<Worker> workerPageUtil = new PageUtil<Worker>(pageUtil.getPageSize(),count);
        workerPageUtil.generate(pageUtil.getCurrentPage());
        workerPageUtil.setPageResults(workerList);
        return workerPageUtil;
    }

    /**
     * 查询员工详情及未完成任务
     *
     * @param workerdataid
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> selectMissionDetail(String workerdataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(workerdataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        Worker worker = workerReadMapper.selectByPrimaryKey(workerdataid);
        WorkerRelMissionCriteria example = new WorkerRelMissionCriteria();
        example.setWorkerdataid(workerdataid);
        List<WorkerRelMission> missionList = workerRelMissionReadMapper.selectByExample(example);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("worker",worker);
        map.put("missionList",missionList);
        return map;
    }

    /**
     * 查询员工工作日志详情
     *
     * @param workdataid
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> selectWorkdataDetail(String workdataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(workdataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        Worker worker = workerReadMapper.selectByPrimaryKey(workdataid);
        WorkdataCriteria example = new WorkdataCriteria();
        example.setWorkerdataid(workdataid);
        example.setTimeMin(DateUtil.getWeekBegin());
        example.setTimeMax(DateUtil.getWeekEnd());
        List<Workdata> workdataList = workdataReadMapper.selectByExample(example);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("worker",worker);
        map.put("workdataList",workdataList);
        return map;
    }
}
