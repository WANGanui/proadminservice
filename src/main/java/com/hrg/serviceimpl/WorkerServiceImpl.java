package com.hrg.serviceimpl;

import com.hrg.enums.ErrorCode;
import com.hrg.exception.ValidatorException;
import com.hrg.javamapper.read.*;
import com.hrg.javamapper.write.WorkerRoleWriteMapper;
import com.hrg.javamapper.write.WorkerWriteMapper;
import com.hrg.model.*;
import com.hrg.service.WorkerService;
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
@Service("workerServiceImpl")
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    WorkerReadMapper workerReadMapper;
    @Autowired
    WorkdataReadMapper workdataReadMapper;
    @Autowired
    WorkerWriteMapper workerWriteMapper;
    @Autowired
    WorkerRoleWriteMapper workerRoleWriteMapper;
    @Autowired
    WorkerRoleReadMapper workerRoleReadMapper;
    @Autowired
    ModuleReadMapper moduleReadMapper;
    @Autowired
    ModuleRelPermissionReadMapper moduleRelPermissionReadMapper;
    @Autowired
    PreRolePermissionReadMapper preRolePermissionReadMapper;
    @Autowired
    DepartmentReadMapper departmentReadMapper;
    @Autowired
    PreRoleReadMapper preRoleReadMapper;
    @Autowired
    WorkerRelMissionReadMapper workerRelMissionReadMapper;
    @Autowired
    MissionReadMapper missionReadMapper;
    @Autowired
    NoticeReadMapper noticeReadMapper;
    @Autowired
    ProjectReadMapper projectReadMapper;
    @Autowired
    WorkdataChatReadMapper workdataChatReadMapper;
    @Autowired
    MissionAuditReadMapper missionAuditReadMapper;

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

        return workerReadMapper.selectByAccount(account);
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

    /**
     * 添加员工
     *
     * @param worker
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public boolean insert(Worker worker, String roledataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(worker.getName())||ValidUtil.isNullOrEmpty(worker.getAccount())||
                ValidUtil.isNullOrEmpty(worker.getDepartment())||ValidUtil.isNullOrEmpty(worker.getDepartmentdataid())||
                ValidUtil.isNullOrEmpty(worker.getPassword())||ValidUtil.isNullOrEmpty(worker.getPhone())||
                ValidUtil.isNullOrEmpty(worker.getState())||ValidUtil.isNullOrEmpty(roledataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        worker.setDataid(UUIDGenerator.getUUID());
        worker.setCreatetime(new Date());
        int x = workerWriteMapper.insert(worker);
        WorkerRole role = new WorkerRole();
        role.setDataid(UUIDGenerator.getUUID());
        role.setWorkerdataid(worker.getDataid());
        role.setRoleid(roledataid);
        int y = workerRoleWriteMapper.insert(role);
        if (y <= 0)
            return false;
        return x > 0 ? true : false;
    }

    /**
     * 根据id查询对象
     *
     * @param dataid
     * @return
     * @throws Exception
     */
    @Override
    public Worker selectDetail(String dataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(dataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        return workerReadMapper.selectByPrimaryKey(dataid);
    }

    /**
     * 修改员工信息
     *
     * @param worker
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public boolean update(Worker worker) throws Exception {
        if (ValidUtil.isNullOrEmpty(worker.getDataid()))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        int x = workerWriteMapper.updateByPrimaryKeySelective(worker);
        return x > 0 ? true : false;
    }

    /**
     * 查询员工模块和权限
     *
     * @param workerdataid
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> selectModuleAndPermission(String workerdataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(workerdataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        Map<String, Object> map = new HashMap<String, Object>();
        WorkerRoleCriteria workerRoleCriteria = new WorkerRoleCriteria();
        workerRoleCriteria.setWorkerdataid(workerdataid);
        WorkerRole workerRole = workerRoleReadMapper.selectByExampleForOne(workerRoleCriteria);

        PreRolePermissionCriteria preRolePermissionCriteria = new PreRolePermissionCriteria();
        preRolePermissionCriteria.setRoleid(workerRole.getRoleid());
        List<PreRolePermission> preRolePermissions = preRolePermissionReadMapper.selectByExample(preRolePermissionCriteria);
        List<String> permissionids = new ArrayList<String>();

        for (PreRolePermission preRolePermission: preRolePermissions){
            permissionids.add(preRolePermission.getPermissionid());
        }

        ModuleRelPermissionCriteria moduleRelPermissionCriteria = new ModuleRelPermissionCriteria();
        moduleRelPermissionCriteria.setPermissionidList(permissionids);

        List<ModuleRelPermission> permissionList = moduleRelPermissionReadMapper.selectByExample(moduleRelPermissionCriteria);
        List<String> moduleid = new ArrayList<String>();
        for (ModuleRelPermission permission : permissionList){
            moduleid.add(permission.getModuleid());
        }

        ModuleCriteria moduleCriteria = new ModuleCriteria();
        moduleCriteria.setDatatidList(moduleid);
        List<Module> moduleList = moduleReadMapper.selectByExample(moduleCriteria);

        WorkerRelMissionCriteria relMissionCriteria = new WorkerRelMissionCriteria();
        relMissionCriteria.setWorkerdataid(workerdataid);
        List<WorkerRelMission> relMissionList = workerRelMissionReadMapper.selectByExample(relMissionCriteria);
        int y = workerRelMissionReadMapper.countByExample(relMissionCriteria);
        map.put("workermission",y);

        MissionCriteria missionCriteria = new MissionCriteria();
        missionCriteria.setAuditorid(workerdataid);
        missionCriteria.setMissionstate("2");
        int count = missionReadMapper.countByExample(missionCriteria);
        MissionAuditCriteria missionAuditCriteria = new MissionAuditCriteria();
        missionAuditCriteria.setAuditorid(workerdataid);
        missionAuditCriteria.setAuditstate("0");
        int cc = missionAuditReadMapper.countByExample(missionAuditCriteria);
        map.put("auditmission",count+cc);

        int x = projectReadMapper.countAudit(workerdataid);

        map.put("auditproject",x);

        Worker worker = workerReadMapper.selectByPrimaryKey(workerdataid);
        WorkerCriteria workerCriteria = new WorkerCriteria();
        workerCriteria.setDepartmentdataid(worker.getDepartmentdataid());
        List<Worker> workerList = workerReadMapper.selectByExample(workerCriteria);
        List<String> ids = new ArrayList<String>();
        for (Worker worker1 : workerList){
            ids.add(worker1.getDataid());
        }
        WorkdataCriteria workdataCriteria = new WorkdataCriteria();
        workdataCriteria.setIsread("0");
        workdataCriteria.setWorkerdataidList(ids);
        int cou = workdataReadMapper.countByExample(workdataCriteria);
        map.put("workdata",cou);

        WorkdataCriteria example = new WorkdataCriteria();
        example.setWorkerdataid(workerdataid);
        List<Workdata> workdataList = workdataReadMapper.selectByExample(example);
        int cht = 0;
        for (Workdata workdata:workdataList){
            WorkdataChatCriteria workdataChatCriteria = new WorkdataChatCriteria();
            workdataChatCriteria.setWorkdataid(workdata.getDataid());
            workdataChatCriteria.setIsread("0");
           cht += workdataChatReadMapper.countByExample(workdataChatCriteria);
        }
        map.put("dataworker",cht);
        map.put("menus",moduleList);
        map.put("permissions",permissionList);
        map.put("roleid",workerRole.getRoleid());

        return map;
    }

    /**
     * 查询列表
     *
     * @param example
     * @return
     * @throws Exception
     */
    @Override
    public List<Worker> selectList(WorkerCriteria example) throws Exception {

        return workerReadMapper.selectByExample(example);
    }

    /**
     * 查询所有角色和部门
     *
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> selectRoleAndPartment() throws Exception {
        List<Department> departmentList = departmentReadMapper.selectByExample(new DepartmentCriteria());
        List<PreRole> preRoles = preRoleReadMapper.selectByExample(new PreRoleCriteria());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("roles",preRoles);
        map.put("daparts",departmentList);
        return map;
    }

    /**
     * @param dataid
     * @return
     * @throws Exception
     */
    @Override
    public Map selectWorkerRole(String dataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(dataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        WorkerRoleCriteria example = new WorkerRoleCriteria();
        example.setWorkerdataid(dataid);
        WorkerRole role = workerRoleReadMapper.selectByExampleForOne(example);
        PreRole preRole = preRoleReadMapper.selectByPrimaryKey(role.getRoleid());
        Map map = new HashMap();
        map.put("workerroleid",preRole.getDataid());
        map.put("workerrolename",preRole.getName());
        return map;
    }

    /**
     * 修改员工角色
     *
     * @param worker
     * @param roleid
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public boolean updateWorkerandRole(Worker worker, String roleid) throws Exception {
        if (ValidUtil.isNullOrEmpty(worker.getDataid())|| ValidUtil.isNullOrEmpty(roleid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        int x = workerWriteMapper.updateByPrimaryKeySelective(worker);
        WorkerRoleCriteria example = new WorkerRoleCriteria();
        example.setWorkerdataid(worker.getDataid());
        int y = workerRoleWriteMapper.deleteByExample(example);
        if (y<=0)
            return false;
        WorkerRole role = new WorkerRole();
        role.setDataid(UUIDGenerator.getUUID());
        role.setWorkerdataid(worker.getDataid());
        role.setRoleid(roleid);
        int z = workerRoleWriteMapper.insert(role);
        if (z <= 0)
            return false;
        return x > 0 ? true : false;
    }

    /**
     * 删除
     *
     * @param dataid
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public boolean delete(String dataid) throws Exception {
        if(ValidUtil.isNullOrEmpty(dataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        int x = workerWriteMapper.deleteByPrimaryKey(dataid);
        return x > 0 ? true : false;
    }

    /**
     * 查询首页数据
     *
     * @param dataid
     * @return
     * @throws Exception
     */
    @Override
    public Map selectIndex(String dataid,Worker worker) throws Exception {
        Map map = new HashMap();

        WorkerRoleCriteria roleCriteria = new WorkerRoleCriteria();
        roleCriteria.setWorkerdataid(dataid);
        WorkerRole workerRole = workerRoleReadMapper.selectByExampleForOne(roleCriteria);
        map.put("roleid",workerRole.getRoleid());
        //按角色区分
       /* WorkerRelMissionCriteria relmissionCriteria = new WorkerRelMissionCriteria();
        relmissionCriteria.setWorkerdataid(dataid);
        List<WorkerRelMission> relMissionList = workerRelMissionReadMapper.selectByExample(relmissionCriteria);
        List<String> missionidList = new ArrayList<String>();
        for (WorkerRelMission relMission : relMissionList){
            missionidList.add(relMission.getMissiondataid());
        }*/

        MissionCriteria mCriteria = new MissionCriteria();
        mCriteria.setAuditorid(dataid);
        mCriteria.setMissionstate("2");
        int count = missionReadMapper.countByExample(mCriteria);
        MissionAuditCriteria missionAuditCriteria = new MissionAuditCriteria();
        missionAuditCriteria.setAuditorid(dataid);
        missionAuditCriteria.setAuditstate("0");
        int cc = missionAuditReadMapper.countByExample(missionAuditCriteria);
        map.put("auditmission",count+cc);
        int cou = projectReadMapper.countAudit(dataid);
        map.put("auditproject",cou);

        ProjectCriteria projectCriteria = new ProjectCriteria();
        projectCriteria.setEndtimeMin(new Date());
        projectCriteria.setEndtimeMax(DateUtil.addDays(new Date(),5));
        projectCriteria.setOrderByClause("endtime asc");
        projectCriteria.setLimitStart(0);
        projectCriteria.setPageSize(7);
        List<Project> projectList = projectReadMapper.selectByExample(projectCriteria);
        int x = projectReadMapper.countByExample(new ProjectCriteria());
        map.put("project",projectList);
        map.put("projectNum",x);

        MissionCriteria missionCriteria = new MissionCriteria();
        missionCriteria.setEndtimeMin(new Date());
        missionCriteria.setEndtimeMax(DateUtil.addDays(new Date(),5));
        missionCriteria.setOrderByClause("endtime asc");
        projectCriteria.setLimitStart(0);
        projectCriteria.setPageSize(7);
        List<Mission> missionList = missionReadMapper.selectByExample(missionCriteria);
        int y = missionReadMapper.countByExample(new MissionCriteria());
        map.put("missionNum",y);
        map.put("mission",missionList);

        //查询公告
        NoticeCriteria noticeCriteria = new NoticeCriteria();
        noticeCriteria.setLimitStart(0);
        noticeCriteria.setPageSize(10);
        noticeCriteria.setTimeMax(new Date());
        noticeCriteria.setOrderByClause("time desc");
        List<Notice> noticeList = noticeReadMapper.selectByExample(noticeCriteria);

        map.put("notice",noticeList);

        //查询当前月缺失日志
        //获取当前月第一天：
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());//当前月第一天
        String currentTime=format.format(new Date());//当前日期
        Map param=new HashMap();
        param.put("first",first);
        param.put("currentTime",currentTime);
        param.put("departmentdataid",worker.getDepartmentdataid());
        map.put("workDataMissingLog",workdataReadMapper.selectMissingLog(param));
        return map;
    }

    /**
     * 柱状图
     *
     * @param worker
     * @return
     * @throws Exception
     */
    @Override
    public List slectyeji(Worker worker) throws Exception {
        List mapList = new ArrayList();
        WorkerCriteria workerCriteria = new WorkerCriteria();
        List<String> ids = new ArrayList<String>();
        ids.add("1");
        ids.add("3");
        ids.add("4");
        ids.add("6");
        workerCriteria.setDepartmentdataidList(ids);
        workerCriteria.setOrderByClause("departmentdataid asc");
        List<Worker> workerList = workerReadMapper.selectByExample(workerCriteria);
        for (Worker worker1:workerList){
            Map mapp = new HashMap();
            WorkerRelMissionCriteria workerRelMissionCriteria = new WorkerRelMissionCriteria();
            workerRelMissionCriteria.setWorkerdataid(worker1.getDataid());
            List<WorkerRelMission> workerRelMissions = workerRelMissionReadMapper.selectByExample(workerRelMissionCriteria);
            int count = 0;
            if (!ValidUtil.isNullOrEmpty(workerRelMissions)){
                for (WorkerRelMission relMission:workerRelMissions){
                    MissionCriteria missionCriteria = new MissionCriteria();
                    missionCriteria.setDataid(relMission.getMissiondataid());
                    missionCriteria.setMissionstate("0");
                    missionCriteria.setState("2");
                    count += missionReadMapper.countByExample(missionCriteria);
                }
            }
            mapp.put("name",worker1.getName());
            mapp.put("count",count);
            mapList.add(mapp);
        }
        return mapList;
    }
}
