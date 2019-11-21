package com.shadow.service.system;

import com.github.pagehelper.PageInfo;
import com.shadow.bean.system.PeRole;
import com.shadow.bean.system.PeUser;

import java.util.List;

public interface UserService {
    // 分页查询
    PageInfo<PeUser> findByPage(String companyId, int pageNum, int PageSize);

    //查询所有部门
    List<PeUser> findAll(String companyId);

    //保存
    void save(PeUser user);

    //更新
    void update(PeUser user);

    //删除
    boolean delete(String id);

    //根据id查询
    PeUser findById(String id);

    List<PeRole> findRoleByUserId(String userId);

    void updateUserRole(String userId, String[] roleIds);

    List<PeUser> findByEmail(String email);
}
