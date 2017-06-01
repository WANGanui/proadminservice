package com.hrg.serviceimpl;

import com.hrg.enums.ErrorCode;
import com.hrg.exception.ValidatorException;
import com.hrg.javamapper.read.WorkerRolePermissionReadMapper;
import com.hrg.model.WorkerRolePermission;
import com.hrg.model.WorkerRolePermissionCriteria;
import com.hrg.service.WorkerRolePermissionService;
import com.hrg.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 82705 on 2017/6/1.
 */
@Service("workerRolePermissionService")
public class WorkerRolePermissionServiceImpl implements WorkerRolePermissionService {

    @Autowired
    WorkerRolePermissionReadMapper workerRolePermissionReadMapper;

    /**
     * 方法说明：根据角色ID集合查询角色权限
     *
     * @param roledataidlist
     * @return
     * @Author 王向涛 2016-11-15 09:37:54
     */
    @Override
    public List<WorkerRolePermission> selectRolesPermission(List<String> roledataidlist) throws Exception {
        if (ValidUtil.isNullOrEmpty(roledataidlist))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        WorkerRolePermissionCriteria example = new WorkerRolePermissionCriteria();
        example.setRoleidList(roledataidlist);
        return workerRolePermissionReadMapper.selectByExample(example);
    }

    /**
     * 方法说明：根据角色ID查询角色权限
     *
     * @param workerRoledataid 员工角色dataid
     * @return
     * @throws Exception
     * @Author 王向涛 2016-12-07 11:05:28
     */
    @Override
    public List<WorkerRolePermission> selectRoleByRoledataid(String workerRoledataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(workerRoledataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        WorkerRolePermissionCriteria example = new WorkerRolePermissionCriteria();
        example.setRoleid(workerRoledataid);
        return workerRolePermissionReadMapper.selectByExample(example);
    }
}
