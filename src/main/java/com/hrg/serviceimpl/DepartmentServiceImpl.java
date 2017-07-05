package com.hrg.serviceimpl;

import com.hrg.enums.ErrorCode;
import com.hrg.exception.ValidatorException;
import com.hrg.javamapper.read.DepartmentReadMapper;
import com.hrg.javamapper.read.ProjectRelDepartmentReadMapper;
import com.hrg.javamapper.write.DepartmentWriteMapper;
import com.hrg.model.Department;
import com.hrg.model.DepartmentCriteria;
import com.hrg.model.ProjectRelDepartment;
import com.hrg.model.ProjectRelDepartmentCriteria;
import com.hrg.service.DepartmentService;
import com.hrg.util.PageUtil;
import com.hrg.util.UUIDGenerator;
import com.hrg.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 82705 on 2017/6/1.
 */
@Service("departmentServiceImpl")
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentReadMapper departmentReadMapper;
    @Autowired
    DepartmentWriteMapper departmentWriteMapper;
    @Autowired
    ProjectRelDepartmentReadMapper projectRelDepartmentReadMapper;

    /**
     * 添加部门
     *
     * @param department
     * @return
     * @throws Exception
     */
    @Override
    public boolean insert(Department department) throws Exception {
        if (ValidUtil.isNullOrEmpty(department.getName())||ValidUtil.isNullOrEmpty(department.getLeader())||
                ValidUtil.isNullOrEmpty(department.getPhone()))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        department.setDataid(UUIDGenerator.getUUID());
        int x = departmentWriteMapper.insert(department);
        return x > 0 ? true : false;
    }

    /**
     * 修改部门信息
     *
     * @param department
     * @return
     * @throws Exception
     */
    @Override
    public boolean update(Department department) throws Exception {
        if (ValidUtil.isNullOrEmpty(department.getDataid())|| ValidUtil.isNullOrEmpty(department.getName())||
                ValidUtil.isNullOrEmpty(department.getLeader())||ValidUtil.isNullOrEmpty(department.getPhone()))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        int x = departmentWriteMapper.updateByPrimaryKeySelective(department);
        return x > 0 ? true : false;
    }

    /**
     * 分页查询
     *
     * @param example  条件实体
     * @param pageUtil 分页条件
     * @return
     * @throws Exception
     */
    @Override
    public PageUtil<Department> selectByExample(DepartmentCriteria example, PageUtil pageUtil) throws Exception {
        if (pageUtil.getCurrentPage() !=null && pageUtil.getCurrentPage() <= 0)
            throw new ValidatorException(ErrorCode.ILLEGALARGUMENT_EXCEPTION.getCode());

        if (pageUtil.getPageSize() !=null && pageUtil.getPageSize() <= 0)
            throw new ValidatorException(ErrorCode.ILLEGALARGUMENT_EXCEPTION.getCode());

        pageUtil.setCurrentPage(pageUtil.getCurrentPage() == null ? 1 : pageUtil.getCurrentPage());

        //设置分页参数
        example.setOrderByClause(" createtime DESC ");
        example.setPageSize((pageUtil.getPageSize() == null) ? 8 : pageUtil.getPageSize());
        example.setLimitStart((pageUtil.getCurrentPage()  == null) ? 0 : (pageUtil.getCurrentPage()-1) * 8);
        List<Department> departmentList = departmentReadMapper.selectByExample(example);

        int count = departmentReadMapper.countByExample(example);
        PageUtil<Department> departmentPageUtil = new PageUtil<Department>(pageUtil.getPageSize(),count);
        departmentPageUtil.generate(pageUtil.getCurrentPage());
        departmentPageUtil.setPageResults(departmentList);
        return departmentPageUtil;
    }

    /**
     * 查询集合
     *
     * @param example 条件实体
     * @return
     * @throws Exception
     */
    @Override
    public List<Department> selectList(DepartmentCriteria example) throws Exception {
        if (ValidUtil.isNullOrEmpty(example))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());

        return departmentReadMapper.selectByExample(example);
    }

    /**
     * 查询部门详情
     *
     * @param dataid
     * @return
     * @throws Exception
     */
    @Override
    public Department selectDetail(String dataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(dataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());

        return departmentReadMapper.selectByPrimaryKey(dataid);
    }

    /**
     * 查询项目部门
     * @param prodataid
     * @return
     * @throws Exception
     */
    @Override
    public List<Department> selectPartmentBypro(String prodataid) throws Exception {
        if (ValidUtil.isNullOrEmpty(prodataid))
            throw new ValidatorException(ErrorCode.INCOMPLETE_REQ_PARAM.getCode());
        ProjectRelDepartmentCriteria example = new ProjectRelDepartmentCriteria();
        example.setProjectid(prodataid);
        List<ProjectRelDepartment> relDepartments = projectRelDepartmentReadMapper.selectByExample(example);
        List<String> ids = new ArrayList<String>();
        for (ProjectRelDepartment relDepartment: relDepartments){
            ids.add(relDepartment.getDepartmentid());
        }
        if (ids.size()>0){
            DepartmentCriteria departmentCriteria = new DepartmentCriteria();
            departmentCriteria.setDataidList(ids);
            List<Department> departmentList = departmentReadMapper.selectByExample(departmentCriteria);
            if (!ValidUtil.isNullOrEmpty(departmentList)){
                return departmentList;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
}
