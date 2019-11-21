package com.shadow.service.system;

import com.github.pagehelper.PageInfo;
import com.shadow.bean.system.PeDept;

import java.util.List;

public interface DeptService {

    /**
     * 分页查询
     *
     * @param companyId 企业id
     * @param pageNum   当前页
     * @param pageSize  页大小
     * @return 封装的分页数据
     */
    PageInfo<PeDept> findByPage(String companyId, int pageNum, int pageSize);


    PeDept findDeptById(String id);

    List<PeDept> findAllDept(String companyId);

    void update(PeDept dept);

    void insert(PeDept dept);

    boolean deleteById(String deptId);
}
