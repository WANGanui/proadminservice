package com.hrg.serviceimpl;

import com.hrg.enums.ErrorCode;
import com.hrg.exception.ValidatorException;
import com.hrg.javamapper.read.ModuleRelPermissionReadMapper;
import com.hrg.javamapper.read.PreRolePermissionReadMapper;
import com.hrg.model.ModuleRelPermission;
import com.hrg.model.ModuleRelPermissionCriteria;
import com.hrg.model.PreRolePermission;
import com.hrg.model.PreRolePermissionCriteria;
import com.hrg.service.PermissionService;
import com.hrg.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 82705 on 2017/6/26.
 */
@Service("permissionServiceImpl")
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PreRolePermissionReadMapper preRolePermissionReadMapper;
    @Autowired
    ModuleRelPermissionReadMapper moduleRelPermissionReadMapper;

    /**
     * 查询操作权限
     *
     * @param moduleid
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public List<String> selectList(String moduleid, String roleid) throws Exception {
        if (ValidUtil.isNullOrEmpty(moduleid) || ValidUtil.isNullOrEmpty(roleid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());

        PreRolePermissionCriteria criteria = new PreRolePermissionCriteria();
        criteria.setRoleid(roleid);

        PreRolePermission permiss = preRolePermissionReadMapper.selectByExampleForOne(criteria);

        ModuleRelPermissionCriteria example = new ModuleRelPermissionCriteria();
        example.setModuleid(moduleid);
        example.setPermissionid(permiss.getPermissionid());
        ModuleRelPermission permission = moduleRelPermissionReadMapper.selectByExampleForOne(example);
        List<String> idList = new ArrayList<String>();
        if (!ValidUtil.isNullOrEmpty(permission)){
            String[] ids = permission.getOptions().split(",");
            for (int i = 0;i<ids.length;i++){
                idList.add(ids[i]);
            }
        }
        return idList;
    }
}
