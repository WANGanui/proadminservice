package com.hrg.serviceimpl;

import com.hrg.enums.ErrorCode;
import com.hrg.exception.ValidatorException;
import com.hrg.javamapper.read.WorkerReadMapper;
import com.hrg.model.Worker;
import com.hrg.model.WorkerCriteria;
import com.hrg.service.WorkerService;
import com.hrg.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 82705 on 2017/6/1.
 */
@Service("workerService")
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    WorkerReadMapper workerReadMapper;

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
}
