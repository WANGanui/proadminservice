package com.hrg.serviceimpl;

import com.hrg.enums.ErrorCode;
import com.hrg.exception.ValidatorException;
import com.hrg.javamapper.read.WorkerReadMapper;
import com.hrg.model.Worker;
import com.hrg.model.WorkerCriteria;
import com.hrg.service.WorkerService;
import com.hrg.util.PageUtil;
import com.hrg.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
