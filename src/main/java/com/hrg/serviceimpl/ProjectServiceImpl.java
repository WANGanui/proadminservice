package com.hrg.serviceimpl;

import com.hrg.enums.ErrorCode;
import com.hrg.exception.ValidatorException;
import com.hrg.javamapper.read.MissionReadMapper;
import com.hrg.javamapper.read.ProjectAuditReadMapper;
import com.hrg.javamapper.read.ProjectReadMapper;
import com.hrg.javamapper.read.WorkerRelProjectReadMapper;
import com.hrg.javamapper.write.*;
import com.hrg.javamapper.read.*;
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
    @Autowired
    ProjectAuditWriteMapper projectAuditWriteMapper;
    @Autowired
    ProjectRelDepartmentWriteMapper projectRelDepartmentWriteMapper;
    @Autowired
    ProjectAuditReadMapper projectAuditReadMapper;
    @Autowired
    WorkerReadMapper workerReadMapper;
    @Autowired
    ProjectRelDepartmentReadMapper projectRelDepartmentReadMapper;

    @Autowired
    ProjectAuditDelWriteMapper projectAuditDelWriteMapper;

    /**
     * 添加项目
     *
     * @param project 项目实体
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public boolean insert(Project project,List<ProjectAudit> projectAudits,List<ProjectRelDepartment> projectRelDepartments) throws Exception {
        if (ValidUtil.isNullOrEmpty(project.getName()) || ValidUtil.isNullOrEmpty(project.getStarttime()) ||
                ValidUtil.isNullOrEmpty(project.getEndtime()) || ValidUtil.isNullOrEmpty(project.getLeader()) ||
                ValidUtil.isNullOrEmpty(project.getLeaderid()) || ValidUtil.isNullOrEmpty(project.getContect()) ||
                ValidUtil.isNullOrEmpty(project.getCreator()) || ValidUtil.isNullOrEmpty(project.getCreatordataid()))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        String projectId=UUIDGenerator.getUUID();
        project.setDataid(projectId);
        project.setDays((int) DateUtil.getDays(DateUtil.toDateStr(project.getStarttime()),DateUtil.toDateStr(project.getEndtime())));
        project.setMonth((int) DateUtil.getMonth(DateUtil.toDateStr(project.getStarttime()),DateUtil.toDateStr(project.getEndtime())));
        project.setState("0");
        project.setCreatetime(new Date());
        int x = projectWriteMapper.insert(project);
        for (int i = 0; i < projectAudits.size(); i++) {
            ProjectAudit projectAudit=projectAudits.get(i);
            projectAudit.setProjectid(projectId);
            projectAuditWriteMapper.insert(projectAudit);
        }
        for (int i = 0; i < projectRelDepartments.size(); i++) {
            ProjectRelDepartment projectRelDepartment=projectRelDepartments.get(i);
            projectRelDepartment.setProjectid(projectId);
            projectRelDepartment.setDataid(UUIDGenerator.getUUID());
            projectRelDepartmentWriteMapper.insert(projectRelDepartment);
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
        if (ValidUtil.isNullOrEmpty(project.getDataid()) || ValidUtil.isNullOrEmpty(project.getModify()) ||
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
        if (workerdataid.equals("1")){
            ProjectCriteria projectCriteria=new ProjectCriteria();
            List<String> list=new ArrayList<String>();
            list.add("4");
            list.add("3");
            list.add("2");
            list.add("1"); list.add("0");
            projectCriteria.setStateList(list);
            List<Project> projectList= projectReadMapper.selectByExample(projectCriteria);
            return projectList;
        }
        Worker worker = workerReadMapper.selectByPrimaryKey(workerdataid);
        ProjectRelDepartmentCriteria criteria = new ProjectRelDepartmentCriteria();
        criteria.setDepartmentid(worker.getDepartmentdataid());
        List<ProjectRelDepartment> departments = projectRelDepartmentReadMapper.selectByExample(criteria);
        List ids = new ArrayList();
        for (ProjectRelDepartment relDepartment:departments){
            ids.add(relDepartment.getProjectid());
        }
        if (!ValidUtil.isNullOrEmpty(ids)){
            ProjectCriteria example = new ProjectCriteria();
            example.setDataidList(ids);
            List<Project> projectList = projectReadMapper.selectByExample(example);
            return projectList;
        }else {
            return null;
        }
    }

    /**
     * 查询当前登录人待审核任务
     * @param auditId
     * @return
     * @throws Exception
     */
    @Override
    public List<Map> selectProjectAudit(String auditId) throws Exception{

        Map map=new HashMap();
        map.put("auditId",auditId);
        return   projectReadMapper.selectProjectAudit(map);
    }

    /**
     * 审核详情
     * @param projectId
     * @return
     */
    @Override
    public List<Map> selectByExample(String projectId){
        Map<String,String> map=new HashMap<String, String>();
        map.put("projectid",projectId);
        return  projectAuditReadMapper.selectProjectAuditDetail(map);
    }

    /**
     * 根据项目ID 和状态查询总数
     * @param map
     * @return
     */
    @Override
    public  int selectAuditInt(Map map){
        return projectAuditReadMapper.selectAuditInt(map);
    }
    /**
     * 根据项目ID 和状态查询总数
     * @param map
     * @return
     */
    @Override
    public  int selectAuditDelInt(Map map){
        return projectAuditReadMapper.selectAuditDelInt(map);
    }

    /**
     * 修改项目审核状态。审核表的
     * @param projectAudit
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(ProjectAudit projectAudit){
        return projectAuditWriteMapper.updateByPrimaryKeySelective(projectAudit);
    }

    /**
     * 修改项目删除审核状态表的
     * @param projectAuditDel
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(ProjectAuditDel projectAuditDel){
        return projectAuditDelWriteMapper.updateByPrimaryKeySelective(projectAuditDel);
    }

    //删除时复制到删除审核表使用
    @Override
    public int copy(String prodataid){
        ProjectAuditDelCriteria projectAuditDelCriteria =new ProjectAuditDelCriteria();
        projectAuditDelCriteria.setProjectid(prodataid);
        projectAuditDelWriteMapper.deleteByExample(projectAuditDelCriteria);
        projectAuditDelWriteMapper.copy(prodataid);

        return 0;
    };
}
