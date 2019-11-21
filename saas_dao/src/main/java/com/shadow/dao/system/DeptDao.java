package com.shadow.dao.system;

import com.shadow.bean.system.PeDept;


import java.util.List;

public interface DeptDao {


    /**
     * 查询全部门
     *
     * @param companyId 公司id
     * @return 公司所有部门的集合
     */
    List<PeDept> findAll(String companyId);


    /**
     * 根据id查询部门
     *
     * @param deptId 部门id
     * @return 部门对象
     */
    PeDept findById(String deptId);

    int insert(PeDept dept);

    int update(PeDept dept);

    List<PeDept> findParentDeptById(String deptId);

    int deleteById(String deptId);
}
