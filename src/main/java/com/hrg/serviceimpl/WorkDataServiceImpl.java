package com.hrg.serviceimpl;

import com.hrg.enums.ErrorCode;
import com.hrg.exception.ValidatorException;
import com.hrg.javamapper.read.*;
import com.hrg.javamapper.write.WorkdataChatWriteMapper;
import com.hrg.javamapper.write.WorkdataWriteMapper;
import com.hrg.model.*;
import com.hrg.service.WorkDataService;
import com.hrg.util.DateUtil;
import com.hrg.util.PageUtil;
import com.hrg.util.UUIDGenerator;
import com.hrg.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 82705 on 2017/6/2.
 */
@Service("workDataServiceImpl")
public class WorkDataServiceImpl implements WorkDataService {

    @Autowired
    WorkdataReadMapper workdataReadMapper;
    @Autowired
    WorkdataWriteMapper workdataWriteMapper;
    @Autowired
    WorkerReadMapper workerReadMapper;
    @Autowired
    WorkerRelMissionReadMapper workerRelMissionReadMapper;
    @Autowired
    MissionReadMapper missionReadMapper;
    @Autowired
    WorkdataChatReadMapper workdataChatReadMapper;
    @Autowired
    WorkdataChatWriteMapper workdataChatWriteMapper;
    /**
     * 添加工作日志
     *
     * @param workdata
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public boolean insert(Workdata workdata) throws Exception {
        if (
                ValidUtil.isNullOrEmpty(workdata.getWorkerdataid())||ValidUtil.isNullOrEmpty(workdata.getWorkcontext())||
                ValidUtil.isNullOrEmpty(workdata.getWorkername()))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        Mission mission = missionReadMapper.selectByPrimaryKey(workdata.getMissiondataid());
        if(!ValidUtil.isNullOrEmpty(mission)){
            if (mission.getType()=="0" || mission.getType().equals("0")){
                workdata.setProjectdataid(mission.getProdataid());
                workdata.setProjectname(mission.getProname());
            }
        }
        workdata.setTime(new Date());
        workdata.setDataid(UUIDGenerator.getUUID());
        workdata.setIsread("0");
        int x = workdataWriteMapper.insert(workdata);
        return x > 0 ? true : false;
    }

    /**
     * 分页查询
     *
     * @param example  条件实体
     * @param pageUtil 分页实体
     * @return
     * @throws Exception
     */
    @Override
    public PageUtil<Workdata> selectByExample(WorkdataCriteria example, PageUtil pageUtil) throws Exception {
        if (pageUtil.getCurrentPage() !=null && pageUtil.getCurrentPage() <= 0)
            throw new ValidatorException(ErrorCode.ILLEGALARGUMENT_EXCEPTION.getCode());

        if (pageUtil.getPageSize() !=null && pageUtil.getPageSize() <= 0)
            throw new ValidatorException(ErrorCode.ILLEGALARGUMENT_EXCEPTION.getCode());

        pageUtil.setCurrentPage(pageUtil.getCurrentPage() == null ? 1 : pageUtil.getCurrentPage());

        //设置分页参数
        example.setOrderByClause("time DESC");
        example.setPageSize((pageUtil.getPageSize() == null) ? 8 : pageUtil.getPageSize());
        example.setLimitStart((pageUtil.getCurrentPage()  == null) ? 0 : (pageUtil.getCurrentPage()-1) * 8);
        List<Workdata> workdatas = workdataReadMapper.selectByExample(example);

        int count = workdataReadMapper.countByExample(example);
        PageUtil<Workdata> workdataPageUtil = new PageUtil<Workdata>(pageUtil.getPageSize(),count);
        workdataPageUtil.generate(pageUtil.getCurrentPage());
        workdataPageUtil.setPageResults(workdatas);
        return workdataPageUtil;
    }

    /**
     * 员工工作详情(单周)
     *
     * @param example
     * @return
     * @throws Exception
     */
    @Override
    public List<Workdata> selectList(WorkdataCriteria example,String dapartmentdataid) throws Exception {
        if (!ValidUtil.isNullOrEmpty(dapartmentdataid)){
            WorkerCriteria workerCriteria = new WorkerCriteria();
            workerCriteria.setDepartmentdataid(dapartmentdataid);
            List<Worker> workerList = workerReadMapper.selectByExample(workerCriteria);
            List<String> idList = new ArrayList<String>();
            for (Worker worker : workerList){
                idList.add(worker.getDataid());
            }
            example.setWorkerdataidList(idList);
        }if (ValidUtil.isNullOrEmpty(example.getTimeMin())){
            example.setTimeMin(DateUtil.getWeekBegin());
            example.setOrderByClause("time desc");
        }if (ValidUtil.isNullOrEmpty(example.getTimeMax())){
            example.setTimeMax(DateUtil.getWeekEnd());
            example.setOrderByClause("time desc");
        }

        List<Workdata> workdataList = workdataReadMapper.selectByExample(example);
        for(Workdata workdata:workdataList){
            WorkdataChatCriteria workdataChatCriteria = new WorkdataChatCriteria();
            workdataChatCriteria.setWorkdataid(workdata.getDataid());
            List<WorkdataChat> workdataChatList = workdataChatReadMapper.selectByExample(workdataChatCriteria);
            workdataChatCriteria.setIsread("0");
            workdata.setCount(workdataChatReadMapper.countByExample(workdataChatCriteria));
            String chate = "";
            for (WorkdataChat workdataChat : workdataChatList){
                chate += workdataChat.getChatname()+" : "+workdataChat.getContext()+"---";
            }
            workdata.setChat(chate);
        }
        return workdataList;
    }

    /**
     * 工作日志列表
     *
     * @param example
     * @return
     * @throws Exception
     */
    @Override
    public List<Workdata> queryList(WorkdataCriteria example) throws Exception {

        return workdataReadMapper.selectByExample(example);
    }

    /**
     * @param dataid
     * @return
     * @throws Exception
     */
    @Override
    public Workdata selectDetail(String dataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(dataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());

        return workdataReadMapper.selectByPrimaryKey(dataid);
    }

    /**
     * 修改
     *
     * @param workdata
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public boolean update(Workdata workdata) throws Exception {
        if (ValidUtil.isNullOrEmpty(workdata.getDataid()))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        int x = workdataWriteMapper.updateByPrimaryKeySelective(workdata);
        return x > 0 ? true : false;
    }

    /**
     * 查询日志评论
     * @param workdataChatCriteria
     * @return
     */
    @Override
    public   List<WorkdataChat> selectByExample(WorkdataChatCriteria workdataChatCriteria){
        return  workdataChatReadMapper.selectByExample(workdataChatCriteria);
    };
    /**
     * 添加评论
     * @param workdataChat
     * @return
     */
    @Override
    public int insert(WorkdataChat workdataChat){
        return workdataChatWriteMapper.insert(workdataChat);
    };

    /**
     * 修改评论
     * @param record
     * @return
     */
    @Override
    public int updateByExampleSelective(Map record){
        return  workdataChatWriteMapper.updateIsread(record);
    };
}
