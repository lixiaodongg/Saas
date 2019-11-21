package com.shadow.service.system;

import com.github.pagehelper.PageInfo;
import com.shadow.bean.system.SsModule;

import java.util.List;

public interface ModuleService {
    // 分页查询
    PageInfo<SsModule> findByPage(int pageNum, int PageSize);

    //查询所有部门
    List<SsModule> findAll();

    //保存
    void save(SsModule module);

    //更新
    void update(SsModule module);

    //删除
    boolean delete(String id);

    //根据id查询
    SsModule findById(String id);

    List<SsModule> findModuleByRoleId(String roleId);

    void updateRoleModule(String roleId, String moduleIds);

    List<SsModule> findModuleByUserId(String userId);
}
