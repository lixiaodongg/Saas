package com.shadow.service.system;

import com.github.pagehelper.PageInfo;
import com.shadow.bean.system.PeRole;

import java.util.List;

public interface RoleService {
    // 分页查询
    PageInfo<PeRole> findByPage(String companyId, int pageNum, int PageSize);

    //查询所有部门
    List<PeRole> findAll(String companyId);

    //保存
    void save(PeRole role);

    //更新
    void update(PeRole role);

    //删除
    boolean delete(String id);

    //根据id查询
    PeRole findById(String id);

}
