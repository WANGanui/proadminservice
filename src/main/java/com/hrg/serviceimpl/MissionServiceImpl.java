package com.hrg.serviceimpl;

import com.hrg.enums.ErrorCode;
import com.hrg.exception.ValidatorException;
import com.hrg.javamapper.read.*;
import com.hrg.javamapper.write.*;
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
    @Autowired
    MissionAuditWriteMapper missionAuditWriteMapper;
    @Autowired
    MissionAuditReadMapper missionAuditReadMapper;
    @Autowired
    MissionFileReadMapper missionFileReadMapper;
    @Autowired
    MissionFileWriteMapper missionFileWriteMapper;
    @Autowired
    FileOptionReadMapper fileOptionReadMapper;
    @Autowired
    FileOptionWriteMapper fileOptionWriteMapper;

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
            example.setOrderByClause("modifytime desc,proportion desc");
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
            missionCriteria.setHeaderid(example.getHeaderid());
            missionCriteria.setCreatetime(example.getCreatetime());
            List list = new ArrayList();
            list.add("0");
            list.add("1");
            missionCriteria.setStateList(list);
            missionCriteria.setOrderByClause("modifytime desc,proportion desc");
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
        MissionAuditCriteria missionAuditCriteria = new MissionAuditCriteria();
        missionAuditCriteria.setMissionid(dataid);
        List<MissionAudit> auditList = missionAuditReadMapper.selectByExample(missionAuditCriteria);
        Map map = new HashMap();
        map.put("mission",mission);
        map.put("relworker",missionList);
        map.put("audit",auditList);
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
    public boolean insert(Mission mission, List<Worker> workerList,List<MissionAudit> auditList) throws Exception {
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
        for (MissionAudit missionAudit : auditList){
            missionAudit.setMissionid(mission.getDataid());
            missionAudit.setAuditstate("0");
            int y = missionAuditWriteMapper.insert(missionAudit);
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
        MissionAuditCriteria missionAuditCriteria = new MissionAuditCriteria();
        missionAuditCriteria.setMissionid(mission.getDataid());
        MissionAudit missionAudit = new MissionAudit();
        missionAudit.setAuditstate("0");
        int s = missionAuditWriteMapper.updateByExampleSelective(missionAudit,missionAuditCriteria);
        return x > 0 ? true : false;
    }


    @Override
    public List<Mission> selectList1(MissionCriteria example,Worker worker) throws Exception {
        WorkerCriteria workerCriteria = new WorkerCriteria();
        workerCriteria.setDepartmentdataid(worker.getDepartmentdataid());
        List<Worker> workers = workerReadMapper.selectByExample(workerCriteria);
        List workerids = new ArrayList();
        if (!ValidUtil.isNullOrEmpty(workers)){
            for (Worker worker1 : workers){
                workerids.add(worker1.getDataid());
            }
        }
        example.setHeaderidList(workerids);
        example.setOrderByClause("modifytime desc,proportion desc");
        return missionReadMapper.selectByExample(example);
    }

    @Override
    public List<Mission> selectList(MissionCriteria example) throws Exception {
        example.setOrderByClause("modifytime desc,proportion desc");
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
        example.setOrderByClause(" modifytime DESC，proportion DESC ");
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
        example.setOrderByClause("modifytime desc,proportion desc");
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
            workdataChatCriteria.setIsread("0");
            workdata.setCount(workdataChatReadMapper.countByExample(workdataChatCriteria));
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

    /**
     * 查询任务文件集合
     *
     * @param example
     * @return
     * @throws Exception
     */
    @Override
    public List<MissionFile> selectMissionFileList(MissionFileCriteria example) throws Exception {
        example.setOrderByClause("orderid asc");
        return missionFileReadMapper.selectByExample(example);
    }

    /**
     * 添加文件
     *
     * @param missionFile
     * @return
     * @throws Exception
     */
    @Override
    public boolean insert(MissionFile missionFile) throws Exception {
        if (ValidUtil.isNullOrEmpty(missionFile.getPath()))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        missionFile.setDataid(UUIDGenerator.getUUID());
        int x = missionFileWriteMapper.insert(missionFile);
        List<Worker> workerList = workerReadMapper.selectByExample(new WorkerCriteria());
        for (Worker worker : workerList){
            FileOption option = new FileOption();
            option.setDataid(UUIDGenerator.getUUID());
            option.setFileid(missionFile.getDataid());
            option.setIsread("0");
            option.setWorkerid(worker.getDataid());
            option.setWorkername(worker.getName());
            int y = fileOptionWriteMapper.insert(option);
            if (y < 0)
                return false;
        }
        return x > 0 ? true : false;
    }

    /**
     * 查询文件
     *
     * @param missionid
     * @return
     * @throws Exception
     */
    @Override
    public List<MissionFile> selectMissionFile(String missionid) throws Exception {
        if (ValidUtil.isNullOrEmpty(missionid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        MissionFileCriteria example = new MissionFileCriteria();
        example.setMissionid(missionid);
        return missionFileReadMapper.selectByExample(example);
    }

    /**
     * 查询项目文件
     *
     * @param projectid
     * @return
     * @throws Exception
     */
    @Override
    public List<MissionFile> selectProjectFile(String projectid) throws Exception {
        if (ValidUtil.isNullOrEmpty(projectid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        MissionFileCriteria example = new MissionFileCriteria();
        example.setProjectid(projectid);
        return missionFileReadMapper.selectByExample(example);
    }

    /**
     * 查询文件实体
     *
     * @param example
     * @return
     * @throws Exception
     */
    @Override
    public MissionFile selectFIle(MissionFileCriteria example) throws Exception {
        return missionFileReadMapper.selectByExampleForOne(example);
    }

    /**
     * 任务实体
     *
     * @param dataid
     * @return
     * @throws Exception
     */
    @Override
    public Mission selectDetail(String dataid) throws Exception {
        return missionReadMapper.selectByPrimaryKey(dataid);
    }

    /**
     * 删除文件
     *
     * @param dataid
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteFile(String dataid) throws Exception {
        int x = missionFileWriteMapper.deleteByPrimaryKey(dataid);
        return x > 0 ? true : false;
    }

    /**
     * 查询列表文件
     * @param example
     * @return
     * @throws Exception
     */
    @Override
    public List<MissionFile> selectFileList(MissionFileCriteria example,Worker worker) throws Exception {
        example.setOrderByClause("orderid asc");
        if (!worker.getDataid().equals("1")){
            example.setShow(1);
        }
        return missionFileReadMapper.selectByExample(example);
    }

    /**
     * 是否已浏览
     *
     * @param worker
     * @param fileid
     * @return
     * @throws Exception
     */
    @Override
    public boolean isread(Worker worker, String fileid) throws Exception {
        if (ValidUtil.isNullOrEmpty(fileid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        FileOptionCriteria criteria = new FileOptionCriteria();
        criteria.setWorkerid(worker.getDataid());
        criteria.setFileid(fileid);
        FileOption option = fileOptionReadMapper.selectByExampleForOne(criteria);
        if (!ValidUtil.isNullOrEmpty(option)){
            if (option.getIsread().equals("0")){
                option.setIsread("1");
                int x = fileOptionWriteMapper.updateByPrimaryKeySelective(option);
                return x > 0 ? true : false;
            }
        }
        return true;
    }

    /**
     * 查询已读人员
     *
     * @param example
     * @return
     * @throws Exception
     */
    @Override
    public List<FileOption> selectFileoption(FileOptionCriteria example) throws Exception {
        example.setOrderByClause("isread desc");
        return fileOptionReadMapper.selectByExample(example);
    }
}
