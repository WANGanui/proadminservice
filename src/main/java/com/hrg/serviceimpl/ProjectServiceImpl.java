package com.hrg.serviceimpl;

import com.hrg.enums.ErrorCode;
import com.hrg.exception.ValidatorException;
import com.hrg.javamapper.read.MissionReadMapper;
import com.hrg.javamapper.read.ProjectReadMapper;
import com.hrg.javamapper.read.WorkerRelProjectReadMapper;
import com.hrg.javamapper.write.ProjectWriteMapper;
import com.hrg.javamapper.write.WorkerRelProjectWriteMapper;
import com.hrg.model.*;
import com.hrg.service.ProjectService;
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
@Service("projectServiceImpl")
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectReadMapper projectReadMapper;
    @Autowired
    ProjectWriteMapper projectWriteMapper;
    @Autowired
    MissionReadMapper missionReadMapper;
    @Autowired
    WorkerRelProjectWriteMapper workerRelProjectWriteMapper;
    @Autowired
    WorkerRelProjectReadMapper workerRelProjectReadMapper;

    /**
     * 添加项目
     *
     * @param project 项目实体
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public boolean insert(Project project, List<WorkerRelProject> projectList) throws Exception {
        if (ValidUtil.isNullOrEmpty(project.getName()) || ValidUtil.isNullOrEmpty(project.getStarttime()) ||
                ValidUtil.isNullOrEmpty(project.getEndtime()) || ValidUtil.isNullOrEmpty(project.getLeader()) ||
                ValidUtil.isNullOrEmpty(project.getLeaderid()) || ValidUtil.isNullOrEmpty(project.getContect()) ||
                ValidUtil.isNullOrEmpty(project.getCreator()) || ValidUtil.isNullOrEmpty(project.getCreatordataid()))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        project.setDataid(UUIDGenerator.getUUID());
        project.setDays((int) DateUtil.getDays(DateUtil.toDateStr(project.getStarttime()),DateUtil.toDateStr(project.getEndtime())));
        project.setMonth((int) DateUtil.getMonth(DateUtil.toDateStr(project.getStarttime()),DateUtil.toDateStr(project.getEndtime())));
        project.setState("0");
        project.setCreatetime(new Date());
        int x = projectWriteMapper.insert(project);
        for (WorkerRelProject relProject : projectList){
            relProject.setDataid(UUIDGenerator.getUUID());
            relProject.setProjectdataid(project.getDataid());
            relProject.setProjectname(project.getName());
            int y = workerRelProjectWriteMapper.insert(relProject);
            if (y<=0)
                return false;
        }
        return x > 0 ? true : false;
    }

    /**
     * 修改项目
     *
     * @param project 项目实体
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public boolean update(Project project) throws Exception {
        if (ValidUtil.isNullOrEmpty(project.getName()) || ValidUtil.isNullOrEmpty(project.getStarttime()) ||
                ValidUtil.isNullOrEmpty(project.getEndtime()) || ValidUtil.isNullOrEmpty(project.getLeader()) ||
                ValidUtil.isNullOrEmpty(project.getLeaderid()) || ValidUtil.isNullOrEmpty(project.getContect()) ||
                ValidUtil.isNullOrEmpty(project.getCreator()) || ValidUtil.isNullOrEmpty(project.getCreatordataid())||
                ValidUtil.isNullOrEmpty(project.getDataid()) || ValidUtil.isNullOrEmpty(project.getModify()) ||
                ValidUtil.isNullOrEmpty(project.getModifydataid()))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        project.setModifytime(new Date());
        int x = projectWriteMapper.updateByPrimaryKeySelective(project);
        return x > 0 ? true : false;
    }

    /**
     * 分页条件查询
     *
     * @param example 条件实体
     * @return
     * @throws Exception
     */
    @Override
    public PageUtil<Project> selectByExample(ProjectCriteria example,PageUtil pageUtil) throws Exception {
        if (pageUtil.getCurrentPage() !=null && pageUtil.getCurrentPage() <= 0)
            throw new ValidatorException(ErrorCode.ILLEGALARGUMENT_EXCEPTION.getCode());

        if (pageUtil.getPageSize() !=null && pageUtil.getPageSize() <= 0)
            throw new ValidatorException(ErrorCode.ILLEGALARGUMENT_EXCEPTION.getCode());

        pageUtil.setCurrentPage(pageUtil.getCurrentPage() == null ? 1 : pageUtil.getCurrentPage());

        if ("".equals(example.getState()))
            example.setState(null);

        //设置分页参数
        example.setOrderByClause(" createtime DESC,modifytime DESC ");
        example.setPageSize((pageUtil.getPageSize() == null) ? 8 : pageUtil.getPageSize());
        example.setLimitStart((pageUtil.getCurrentPage()  == null) ? 0 : (pageUtil.getCurrentPage()-1) * 8);
        List<Project> projectList = projectReadMapper.selectByExample(example);

        int count = projectReadMapper.countByExample(example);
        PageUtil<Project> projectPageUtil = new PageUtil<Project>(pageUtil.getPageSize(),count);
        projectPageUtil.generate(pageUtil.getCurrentPage());
        projectPageUtil.setPageResults(projectList);
        return projectPageUtil;
    }

    /**
     * 条件查询列表
     *
     * @param example
     * @return
     * @throws Exception
     */
    @Override
    public List<Project> selectList(ProjectCriteria example) throws Exception {
        if (ValidUtil.isNullOrEmpty(example))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        return projectReadMapper.selectByExample(example);
    }

    /**
     * 查询项目详情
     *
     * @param datatid
     * @return
     * @throws Exception
     */
    @Override
    public Project selectDetail(String datatid) throws Exception {
        if (ValidUtil.isNullOrEmpty(datatid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());

        return projectReadMapper.selectByPrimaryKey(datatid);
    }

    /**
     * 项目详情及以下任务
     *
     * @param dataid
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> selectProjectDetail(String dataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(dataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        Project project = projectReadMapper.selectByPrimaryKey(dataid);
        MissionCriteria example = new MissionCriteria();
        example.setProdataid(project.getDataid());
        List<Mission> missionList = missionReadMapper.selectByExample(example);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("project",project);
        map.put("missionList",missionList);
        return map;
    }

    /**
     * 查询员工拥有的项目
     *
     * @param workerdataid
     * @return
     * @throws Exception
     */
    @Override
    public List<Project> selectByWorker(String workerdataid) throws Exception {
        if(ValidUtil.isNullOrEmpty(workerdataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        WorkerRelProjectCriteria criteria = new WorkerRelProjectCriteria();
        criteria.setWorkerdataid(workerdataid);
        List<WorkerRelProject> relProjects = workerRelProjectReadMapper.selectByExample(criteria);
        List<String> idList = new ArrayList<String>();
        for (WorkerRelProject relProject : relProjects){
            idList.add(relProject.getProjectdataid());
        }
        ProjectCriteria example = new ProjectCriteria();
        example.setDataidList(idList);
        return projectReadMapper.selectByExample(example);
    }
}
