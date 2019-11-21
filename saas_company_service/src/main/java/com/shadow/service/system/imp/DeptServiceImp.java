package com.shadow.service.system.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.system.PeDept;
import com.shadow.dao.system.DeptDao;
import com.shadow.service.system.DeptService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class DeptServiceImp implements DeptService {

    @Autowired
    DeptDao deptDao;

    /**
     * 分页查询
     *
     * @param companyId 企业id
     * @param pageNum   当前页
     * @param pageSize  页大小
     * @return 封装的分页数据
     */
    @Override
    public PageInfo<PeDept> findByPage(String companyId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PeDept> deptList = deptDao.findAll(companyId);
        return new PageInfo<>(deptList);
    }

    @Override
    public PeDept findDeptById(String id) {
        return deptDao.findById(id);
    }

    @Override
    public List<PeDept> findAllDept(String companyId) {
        return deptDao.findAll(companyId);
    }

    @Override
    public void update(PeDept dept) {
        deptDao.update(dept);
    }

    @Override
    public void insert(PeDept dept) {
        dept.setDeptId(UUID.randomUUID().toString());
        deptDao.insert(dept);
    }

    @Override
    public boolean deleteById(String deptId) {
        List<PeDept> deptList = deptDao.findParentDeptById(deptId);
        if (deptList != null && deptList.size() > 0) {
            return false;
        }
        return deptDao.deleteById(deptId) > 0;
    }

}
