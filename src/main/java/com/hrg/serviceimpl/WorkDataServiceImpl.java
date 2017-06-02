package com.hrg.serviceimpl;

import com.hrg.enums.ErrorCode;
import com.hrg.exception.ValidatorException;
import com.hrg.javamapper.read.WorkdataReadMapper;
import com.hrg.javamapper.write.WorkdataWriteMapper;
import com.hrg.model.Workdata;
import com.hrg.model.WorkdataCriteria;
import com.hrg.service.WorkDataService;
import com.hrg.util.PageUtil;
import com.hrg.util.UUIDGenerator;
import com.hrg.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by 82705 on 2017/6/2.
 */
@Service("workDataService")
public class WorkDataServiceImpl implements WorkDataService {

    @Autowired
    WorkdataReadMapper workdataReadMapper;
    @Autowired
    WorkdataWriteMapper workdataWriteMapper;

    /**
     * 添加工作日志
     *
     * @param workdata
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public boolean insert(Workdata workdata) throws Exception {
        if (ValidUtil.isNullOrEmpty(workdata.getProjectdataid()) || ValidUtil.isNullOrEmpty(workdata.getProjectname())||
                ValidUtil.isNullOrEmpty(workdata.getMissiondataid())||ValidUtil.isNullOrEmpty(workdata.getMissionname())||
                ValidUtil.isNullOrEmpty(workdata.getWorkerdataid())||ValidUtil.isNullOrEmpty(workdata.getWorkcontext())||
                ValidUtil.isNullOrEmpty(workdata.getWorkername())||ValidUtil.isNullOrEmpty(workdata.getProjectleader()))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        workdata.setDataid(UUIDGenerator.getUUID());
        workdata.setTime(new Date());
        int x = workdataWriteMapper.insert(workdata);
        return x > 0 ? true : false;
    }

    /**
     * 分页查询
     *
     * @param example  条件实体
     * @param pageUtil 分页实体
     * @return
     * @throws Exception
     */
    @Override
    public PageUtil<Workdata> selectByExample(WorkdataCriteria example, PageUtil pageUtil) throws Exception {
        if (pageUtil.getCurrentPage() !=null && pageUtil.getCurrentPage() <= 0)
            throw new ValidatorException(ErrorCode.ILLEGALARGUMENT_EXCEPTION.getCode());

        if (pageUtil.getPageSize() !=null && pageUtil.getPageSize() <= 0)
            throw new ValidatorException(ErrorCode.ILLEGALARGUMENT_EXCEPTION.getCode());

        pageUtil.setCurrentPage(pageUtil.getCurrentPage() == null ? 1 : pageUtil.getCurrentPage());

        //设置分页参数
        example.setOrderByClause("time DESC");
        example.setPageSize((pageUtil.getPageSize() == null) ? 8 : pageUtil.getPageSize());
        example.setLimitStart((pageUtil.getCurrentPage()  == null) ? 0 : (pageUtil.getCurrentPage()-1) * 8);
        List<Workdata> workdatas = workdataReadMapper.selectByExample(example);

        int count = workdataReadMapper.countByExample(example);
        PageUtil<Workdata> workdataPageUtil = new PageUtil<Workdata>(pageUtil.getPageSize(),count);
        workdataPageUtil.generate(pageUtil.getCurrentPage());
        workdataPageUtil.setPageResults(workdatas);
        return workdataPageUtil;
    }
}
