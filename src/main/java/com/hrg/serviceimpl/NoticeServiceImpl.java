package com.hrg.serviceimpl;

import com.hrg.enums.ErrorCode;
import com.hrg.exception.ValidatorException;
import com.hrg.javamapper.read.NoticeReadMapper;
import com.hrg.javamapper.write.NoticeRelWorkerWriteMapper;
import com.hrg.javamapper.write.NoticeWriteMapper;
import com.hrg.model.Notice;
import com.hrg.model.NoticeCriteria;
import com.hrg.model.NoticeRelWorker;
import com.hrg.service.NoticeService;
import com.hrg.util.PageUtil;
import com.hrg.util.UUIDGenerator;
import com.hrg.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 82705 on 2017/6/1.
 */
@Service("noticeServiceImpl")
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeReadMapper noticeReadMapper;
    @Autowired
    NoticeWriteMapper noticeWriteMapper;
    @Autowired
    NoticeRelWorkerWriteMapper noticeRelWorkerWriteMapper;

    /**
     * 添加公告
     *
     * @param notice 公告实体
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public boolean insert(Notice notice) throws Exception {
        if (ValidUtil.isNullOrEmpty(notice.getContext())||ValidUtil.isNullOrEmpty(notice.getWorker())||
                ValidUtil.isNullOrEmpty(notice.getWorkerdataid()) || ValidUtil.isNullOrEmpty(notice.getDepartment())||
                ValidUtil.isNullOrEmpty(notice.getDepartmentdataid())|| ValidUtil.isNullOrEmpty(notice.getTime()))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        notice.setDataid(UUIDGenerator.getUUID());
        notice.setCreatetime(new Date());
        int x = noticeWriteMapper.insert(notice);
        return x > 0 ? true : false;
    }

    /**
     * 修改公告
     *
     * @param notice 公告实体
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public boolean update(Notice notice) throws Exception {
        if (ValidUtil.isNullOrEmpty(notice.getContext())||ValidUtil.isNullOrEmpty(notice.getTime()) ||
                ValidUtil.isNullOrEmpty(notice.getDataid()))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        int x = noticeWriteMapper.updateByPrimaryKeySelective(notice);
        return x > 0 ? true : false;
    }

    /**
     * 删除公告
     *
     * @param dataid 主键
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public boolean delete(String dataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(dataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        int x = noticeWriteMapper.deleteByPrimaryKey(dataid);
        return x > 0 ? true : false;
    }

    /**
     * 分页条件查询
     *
     * @param example  条件实体
     * @param pageUtil 分页条件
     * @return
     * @throws Exception
     */
    @Override
    public PageUtil selectByExample(NoticeCriteria example, PageUtil pageUtil) throws Exception {
        if (pageUtil.getCurrentPage() !=null && pageUtil.getCurrentPage() <= 0)
            throw new ValidatorException(ErrorCode.ILLEGALARGUMENT_EXCEPTION.getCode());

        if (pageUtil.getPageSize() !=null && pageUtil.getPageSize() <= 0)
            throw new ValidatorException(ErrorCode.ILLEGALARGUMENT_EXCEPTION.getCode());

        pageUtil.setCurrentPage(pageUtil.getCurrentPage() == null ? 1 : pageUtil.getCurrentPage());

        //设置分页参数
        example.setOrderByClause(" createtime DESC");
        example.setPageSize((pageUtil.getPageSize() == null) ? 8 : pageUtil.getPageSize());
        example.setLimitStart((pageUtil.getCurrentPage()  == null) ? 0 : (pageUtil.getCurrentPage()-1) * 8);
        List<Notice> noticeList = noticeReadMapper.selectByExample(example);

        int count = noticeReadMapper.countByExample(example);
        PageUtil<Notice> noticePageUtil = new PageUtil<Notice>(pageUtil.getPageSize(),count);
        noticePageUtil.generate(pageUtil.getCurrentPage());
        noticePageUtil.setPageResults(noticeList);
        return noticePageUtil;
    }

    /**
     * 公告列表
     *
     * @param example
     * @return
     */
    @Override
    public List<Notice> selectList(NoticeCriteria example) throws Exception {
        return noticeReadMapper.selectByExample(example);
    }

    /**
     * 详情
     *
     * @param dataid
     * @return
     * @throws Exception
     */
    @Override
    public Notice selectDetail(String dataid) throws Exception {
        if(ValidUtil.isNullOrEmpty(dataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());

        return noticeReadMapper.selectByPrimaryKey(dataid);
    }

    /**
     * 查询公告列表
     * @param map
     * @return
     * @throws Exception
     */
    @Override
   public List<Map> selectNoticeWork(Map map)  throws Exception{
       return  noticeReadMapper.selectNoticeWork(map);
   };
    /**
     * 修改公告阅读表
     * @param map
     * @return
     * @throws Exception
     */
    @Override
   public int updateStatusNoticeRelWorker(NoticeRelWorker map)  throws Exception{
        return noticeRelWorkerWriteMapper.updateByPrimaryKeySelective(map);
    };
}
