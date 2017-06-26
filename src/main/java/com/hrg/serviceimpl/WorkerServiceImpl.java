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

import java.util.*;

/**
 * Created by 82705 on 2017/6/1.
 */
@Service("workerServiceImpl")
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    WorkerReadMapper workerReadMapper;
    @Autowired
    WorkerRelMissionReadMapper workerRelMissionReadMapper;
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

        Map<String, Object> map = new HashMap<String, Object>();
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
}
