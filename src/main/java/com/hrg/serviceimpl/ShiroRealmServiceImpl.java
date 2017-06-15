package com.hrg.serviceimpl;

import com.hrg.service.*;
import com.hrg.enums.ErrorCode;
import com.hrg.exception.ValidatorException;
import com.hrg.javamapper.read.PreSysPermissionReadMapper;
import com.hrg.javamapper.read.PreSysRoleRelPermissionReadMapper;
import com.hrg.model.*;
import com.hrg.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wanganui on 16-11-15.
 * ("shiroRealmService")
 */
@Service("shiroRealmServiceImpl")
public class ShiroRealmServiceImpl implements ShiroRealmService {

    @Autowired
    WorkerService workerService;
    @Autowired
    WorkerRoleService workerRoleService;
    @Autowired
    WorkerRolePermissionService workerRolePermissionService;
    @Autowired
    PreSysRoleRelPermissionReadMapper preSysRoleRelPermissionReadMapper;
    @Autowired
    PreSysPermissionReadMapper preSysPermissionReadMapper;
    /**
     * 获取完整的身份信息对象<br>
     * @param principal:用户输入的身份信息<br>
     * @return 用户对象<br>
     */
    @Override
    public Object getLoginUser(Object principal) throws Exception {
        if (ValidUtil.isNullOrEmpty(principal))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        return workerService.getWorkerInfo(principal.toString());
    }

    /**
     * 获取用户凭证信息<br>
     * @param principal:用户输入的身份信息<br>
     * @return 用户凭证对象<br>
     */
    @Override
    public Object getUserCredentials(Object principal) throws Exception {
        if (ValidUtil.isNullOrEmpty(principal))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        return workerService.getWorkerPassWord(principal.toString());
    }

    /**
     * 方法说明：获取用户拥有的角色<br>
     * @param principal:用户<br>
     * @return 角色集合<br>
     */
    @Override
    public List<String> getHaveRoleStringByUser(Object principal) throws Exception {
        Worker worker = (Worker) principal;
        List<String> list = new ArrayList<String>();
        List<WorkerRole> workerRoleList = workerRoleService.selectDetail(worker.getDataid());
        if (!ValidUtil.isNullOrEmpty(workerRoleList)){
            for (WorkerRole workerRole : workerRoleList)
                list.add(workerRole.getRolename());
        }
        return list;
    }

    /**
     * 方法说明：获取用户拥有的角色<br>
     * @param principal:用户<br>
     * @return 角色对象集合<br>
     */
    @Override
    public List<? extends Object> getHaveRoleByUser(Object principal) throws Exception {
        Worker worker = (Worker)principal;
        return workerRoleService.selectDetail(worker.getDataid());
    }

    /**
     * 方法说明：获取用户拥有的权限<br>
     * @param principal:用户<br>
     * @return 权限集合<br>
     */
    @Override
    public List<String> getPermissionStringByUser(Object principal) throws Exception {
        Worker worker = (Worker) principal;
        if (worker.getDataid()=="1"||worker.getDataid().equals("1"))
        {
            List<PreSysPermission> syspermissionList = preSysPermissionReadMapper.selectByExample(new PreSysPermissionCriteria());
            List<String> permissionlist = new ArrayList<String>();
            if (!ValidUtil.isNullOrEmpty(syspermissionList)){
                for (PreSysPermission preSysPermission : syspermissionList)
                    permissionlist.add(preSysPermission.getPermissioncode());
            }
            return permissionlist;
        }
        List<String> rolelist = new ArrayList<String>();
        //获取会员角色
        List<WorkerRole> workerRelRoleList = workerRoleService.selectDetail(worker.getDataid());
        if (!ValidUtil.isNullOrEmpty(workerRelRoleList)){
            for (WorkerRole workerRole : workerRelRoleList)
                rolelist.add(workerRole.getDataid());
        }
        //获取角色权限
        List<WorkerRolePermission> workerRolePermissionList = workerRolePermissionService.selectRolesPermission(rolelist);
        List<String> permissionlist = new ArrayList<String>();
        if (!ValidUtil.isNullOrEmpty(workerRolePermissionList)){
            for (WorkerRolePermission permission : workerRolePermissionList)
                permissionlist.add(permission.getPermissionurl());
        }
        return permissionlist;
    }

    /**
     * 方法说明：获取用户拥有的权限<br>
     * @param principal:用户<br>
     * @return 权限对象集合
     */
    @Override
    public List<? extends Object> getPermissionByUser(Object principal) throws Exception {
        Worker worker = (Worker) principal;
        if (!ValidUtil.isNullOrEmpty(worker.getDataid())&&worker.getDataid().equals("1")){
            //超级管理员所有权限
            List<PreSysPermission> permissions = preSysPermissionReadMapper.selectByExample(new PreSysPermissionCriteria());
            List<PreSysRoleRelPermission> permissionList = new ArrayList<PreSysRoleRelPermission>();
            for (PreSysPermission permission : permissions){
                PreSysRoleRelPermission relPermission = new PreSysRoleRelPermission();
                relPermission.setPermissiondataid(permission.getDataid());
                //relPermission.setPermissioncode(permission.getPermissioncode());
                permissionList.add(relPermission);
            }
            return permissionList;
        }
        List<String> rolelist = new ArrayList<String>();
        //获取会员角色
        List<WorkerRole> workerRelRoleList = workerRoleService.selectDetail(worker.getDataid());
        if (!ValidUtil.isNullOrEmpty(workerRelRoleList)){
            for (WorkerRole workerRelRole : workerRelRoleList)
                rolelist.add(workerRelRole.getDataid());
        }
        //获取角色权限

        List<WorkerRolePermission> workerRolePermissionList = workerRolePermissionService.selectRolesPermission(rolelist);
        return workerRolePermissionList;
    }

    /**
     * 方法说明：获取所有有效权限<br>
     * 用于在ShiroPermissionFilter过滤器中进行地址过滤<br>
     * 返回数据格式：Map集合中key存储url地址信息，value存储需要的权限信息<br>
     * @author 杨应铭 2015年11月5日 上午10:42:04<br>
     * @return
     */
    @Override
    public List<Map<String, String>> getAllPermission() throws Exception {
        /*List<Map<String,String>> perms = new LinkedList<Map<String,String>>();
        List<WarehouseRoleRelPermission> permissionlist = warehouseRoleRelPermissionService.selectAllPermission();
        if (!ValidUtil.isNullOrEmpty(permissionlist)) {
            for (WarehouseRoleRelPermission permission:permissionlist) {
                Map<String,String> map = new HashMap<String,String>();
                if(!ValidUtil.isNullOrEmpty(permission.getPermissionurl())){
                    map.put(permission.getPermissionurl(),permission.getPermissioncode());
                }
                perms.add(map);
            }
        }*/
        return null;
    }

    /**
     * 方法说明：用户是否被锁定<br>
     * @param principal:用户<br>
     * @return true:是　false:否<br>
     */
    @Override
    public boolean isLocked(Object principal){
        Worker worker = (Worker) principal;
        if (!worker.getState().equals("0"))
            return false;
        return true;
    }
}
