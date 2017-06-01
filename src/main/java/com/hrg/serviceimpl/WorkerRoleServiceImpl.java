package com.hrg.serviceimpl;

import com.hrg.enums.ErrorCode;
import com.hrg.exception.ValidatorException;
import com.hrg.javamapper.read.WorkerRoleReadMapper;
import com.hrg.model.WorkerRole;
import com.hrg.model.WorkerRoleCriteria;
import com.hrg.service.WorkerRoleService;
import com.hrg.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 82705 on 2017/6/1.
 */
@Service("workerRoleService")
public class WorkerRoleServiceImpl implements WorkerRoleService {

    @Autowired
    WorkerRoleReadMapper workerRoleReadMapper;

    /**
     * 方法说明：查询员工角色
     *
     * @param workerdataid 员工主键
     * @return
     * @throws Exception
     */
    @Override
    public List<WorkerRole> selectDetail(String workerdataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(workerdataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        WorkerRoleCriteria example = new WorkerRoleCriteria();
        example.setWorkerdataid(workerdataid);
        return workerRoleReadMapper.selectByExample(example);
    }
}
