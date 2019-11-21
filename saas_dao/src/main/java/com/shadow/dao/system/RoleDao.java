package com.shadow.dao.system;


import com.shadow.bean.system.PeRole;
import com.shadow.bean.system.PeUser;

import java.util.List;

public interface RoleDao {
    //根据企业id查询全部
    List<PeRole> findAll(String companyId);

    //根据id查询
    PeRole findById(String roleId);

    //根据id删除
    int delete(String roleId);

    //保存
    int save(PeRole role);

    //更新
    int update(PeRole role);
}
