package com.hrg.serviceimpl;

import com.hrg.enums.ErrorCode;
import com.hrg.exception.ValidatorException;
import com.hrg.javamapper.read.*;
import com.hrg.javamapper.write.MissionWriteMapper;
import com.hrg.javamapper.write.WorkerRelMissionWriteMapper;
import com.hrg.model.*;
import com.hrg.service.MissionService;
import com.hrg.util.DateUtil;
import com.hrg.util.PageUtil;
import com.hrg.util.UUIDGenerator;
import com.hrg.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    WorkdataReadMapper workdataReadMapper;
    @Autowired
    WorkerReadMapper workerReadMapper;
    @Autowired
    WorkerRoleReadMapper workerRoleReadMapper;
    @Autowired
    WorkdataChatReadMapper workdataChatReadMapper;

    /**
     * 条件查询任务列表
     *
     * @param example 条件实体
     * @return
     * @throws Exception
     */
    @Override
    public List<Mission> selectList(MissionCriteria example,Worker worker) throws Exception {
        WorkerRoleCriteria workerRoleCriteria = new WorkerRoleCriteria();
        workerRoleCriteria.setWorkerdataid(worker.getDataid());
        WorkerRole workerRole = workerRoleReadMapper.selectByExampleForOne(workerRoleCriteria);
        if (workerRole.getRoleid()=="1" || workerRole.getRoleid()=="5" ){
            example.setOrderByClause("createtime desc,proportion desc");
            return missionReadMapper.selectByExample(example);
        }else {
            WorkerCriteria workerCriteria = new WorkerCriteria();
            workerCriteria.setDepartmentdataid(worker.getDepartmentdataid());
            List<Worker> workerList = workerReadMapper.selectByExample(workerCriteria);
            List<String> wids = new ArrayList<String>();
            for (Worker worker1:workerList){
                wids.add(worker1.getDataid());
            }
            WorkerRelMissionCriteria relMissionCriteria = new WorkerRelMissionCriteria();
            relMissionCriteria.setWorkerdataidList(wids);
            List<WorkerRelMission> relMissionList = workerRelMissionReadMapper.selectByExample(relMissionCriteria);
            List<String> mids = new ArrayList<String>();
            for (WorkerRelMission relMission:relMissionList){
                mids.add(relMission.getMissiondataid());
            }
            MissionCriteria missionCriteria = new MissionCriteria();
            missionCriteria.setDataidList(mids);
            missionCriteria.isDistinct();
            missionCriteria.setOrderByClause("proportion desc,createtime desc");
            List<Mission> missionList = missionReadMapper.selectByExample(missionCriteria);
            return missionList;
        }
    }

    /**
     * 根据主键查询任务详情
     *
     * @param dataid
     * @return
     * @throws Exception
     */
    @Override
    public Map<String,Object> selectDetailById(String dataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(dataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        Mission mission = missionReadMapper.selectByPrimaryKey(dataid);
        WorkerRelMissionCriteria example = new WorkerRelMissionCriteria();
        example.setMissiondataid(mission.getDataid());
        List<WorkerRelMission> missionList = workerRelMissionReadMapper.selectByExample(example);
        Map map = new HashMap();
        map.put("mission",mission);
        map.put("relworker",missionList);
        return map;
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
                ValidUtil.isNullOrEmpty(mission.getName()) ||
                ValidUtil.isNullOrEmpty(mission.getProportion()) || ValidUtil.isNullOrEmpty(mission.getStarttime()))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        mission.setDataid(UUIDGenerator.getUUID());
        mission.setCreatetime(new Date());
        int x = missionWriteMapper.insert(mission);
        if (!ValidUtil.isNullOrEmpty(workerList)){
            for (Worker worker : workerList){
                WorkerRelMission relMission = new WorkerRelMission();
                relMission.setDataid(UUIDGenerator.getUUID());
                relMission.setWorkerdataid(worker.getDataid());
                relMission.setWorkername(worker.getName());
                relMission.setMissiondataid(mission.getDataid());
                relMission.setMissionname(mission.getName());
                int y = workerRelMissionWriteMapper.insert(relMission);
                if (y <= 0)
                    return false;
            }
        }else {
            WorkerRelMission workerRelMission = new WorkerRelMission();
            workerRelMission.setDataid(UUIDGenerator.getUUID());
            workerRelMission.setWorkerdataid(mission.getHeaderid());
            workerRelMission.setWorkername(mission.getHeadername());
            workerRelMission.setMissiondataid(mission.getDataid());
            workerRelMission.setMissionname(mission.getName());
            int y = workerRelMissionWriteMapper.insert(workerRelMission);
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
        if (ValidUtil.isNullOrEmpty(mission.getContext()) || ValidUtil.isNullOrEmpty(mission.getEndtime()) ||
                ValidUtil.isNullOrEmpty(mission.getProportion()) || ValidUtil.isNullOrEmpty(mission.getStarttime()) ||
                ValidUtil.isNullOrEmpty(mission.getModify()) || ValidUtil.isNullOrEmpty(mission.getModifydataid()))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        mission.setModifytime(new Date());
        WorkerRelMissionCriteria example = new WorkerRelMissionCriteria();
        example.setMissiondataid(mission.getDataid());
        int x = workerRelMissionWriteMapper.deleteByExample(example);
        WorkerRelMission relMission = new WorkerRelMission();
        if (!ValidUtil.isNullOrEmpty(workerList)){
            for (Worker worker : workerList){
                relMission.setMissiondataid(mission.getDataid());
                relMission.setWorkername(worker.getName());
                relMission.setDataid(UUIDGenerator.getUUID());
                relMission.setWorkerdataid(worker.getDataid());
                relMission.setMissionname(mission.getName());
                int y = workerRelMissionWriteMapper.insert(relMission);
                if (y<=0)
                    return false;
            }
        }
        if (mission.getState().equals("2")){
            mission.setFinishtime(new Date());
        }
        int y = missionWriteMapper.updateByPrimaryKeySelective(mission);
        if (y<=0)
            return false;
        return x > 0 ? true : false;
    }


    @Override
    public List<Mission> selectList(MissionCriteria example) throws Exception {
        example.setOrderByClause("createtime desc,proportion desc");
        return missionReadMapper.selectByExample(example);
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

    /**
     * 查询员工任务列表
     *
     * @param example
     * @return
     * @throws Exception
     */
    @Override
    public Map<String,Object> slectWorkerMission(MissionCriteria example,String wokerdataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(wokerdataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        WorkerRelMissionCriteria relMissionCriteria = new WorkerRelMissionCriteria();
        relMissionCriteria.setWorkerdataid(wokerdataid);
        List<WorkerRelMission> relMissionList = workerRelMissionReadMapper.selectByExample(relMissionCriteria);
        List<String> idList = new ArrayList<String>();
        Map<String,Object> map = new HashMap<String,Object>();
        if (!ValidUtil.isNullOrEmpty(relMissionList)){
            for (WorkerRelMission relMission : relMissionList){
                idList.add(relMission.getMissiondataid());
            }
            example.setDataidList(idList);
            example.setType("1");
            //个人任务
            List<Mission> missionList1 = missionReadMapper.selectByExample(example);
            example.setType("0");
            //项目任务
            List<Mission> missionList2 = missionReadMapper.selectByExample(example);

            map.put("list1",missionList1);
            map.put("list2",missionList2);
        }
        return map;
    }

    /**
     * @param example
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> slectMissionBylevel(MissionCriteria example) throws Exception {
        example.setLevel("1");
        example.setOrderByClause("createtime desc,proportion desc");
        List<Mission> missions1 = missionReadMapper.selectByExample(example);
        example.setLevel("2");
        List<Mission> missions2 = missionReadMapper.selectByExample(example);
        example.setLevel("3");
        List<Mission> missions3 = missionReadMapper.selectByExample(example);
        example.setLevel("4");
        List<Mission> missions4 = missionReadMapper.selectByExample(example);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("list1",missions1);
        map.put("list2",missions2);
        map.put("list3",missions3);
        map.put("list4",missions4);
        return map;
    }

    /**
     * 根据主键删除任务
     *
     * @param dataid
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public boolean deleteMission(String dataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(dataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        int x = missionWriteMapper.deleteByPrimaryKey(dataid);
        WorkerRelMissionCriteria example = new WorkerRelMissionCriteria();
        example.setMissiondataid(dataid);
        int count = workerRelMissionReadMapper.countByExample(example);
        if (count>0){
            int y = workerRelMissionWriteMapper.deleteByExample(example);
            if (y<=0)
                return false;
        }

        return x > 0 ? true : false;
    }

    /**
     * 修改状态
     *
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public boolean updateState(Mission mission) throws Exception {
        if (ValidUtil.isNullOrEmpty(mission.getDataid()) || ValidUtil.isNullOrEmpty(mission.getMissionstate()))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        mission.setModifytime(new Date());
        int x = missionWriteMapper.updateByPrimaryKeySelective(mission);
        return x > 0 ? true : false;
    }

    /**
     * 任务进度详情
     *
     * @param dataid
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> selectMissionJindu(String dataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(dataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        Map map = new HashMap();
        Mission mission = missionReadMapper.selectByPrimaryKey(dataid);
        WorkdataCriteria workdataCriteria = new WorkdataCriteria();
        workdataCriteria.setMissiondataid(dataid);
        workdataCriteria.setTimeMin(mission.getStarttime());
        workdataCriteria.setTimeMax(mission.getEndtime());
        workdataCriteria.setOrderByClause("time asc");
        List<Workdata> workdataList = workdataReadMapper.selectByExample(workdataCriteria);
        for(Workdata workdata:workdataList){
            WorkdataChatCriteria workdataChatCriteria = new WorkdataChatCriteria();
            workdataChatCriteria.setWorkdataid(workdata.getDataid());
            List<WorkdataChat> workdataChatList = workdataChatReadMapper.selectByExample(workdataChatCriteria);
            String chat = "";
            for (WorkdataChat workdataChat : workdataChatList){
                chat += workdataChat.getChatname()+" : "+workdataChat.getContext()+"---";
            }
            workdata.setChat(chat);
        }

        map.put("mission",mission);
        map.put("workdatas",workdataList);
        return map;
    }
}
