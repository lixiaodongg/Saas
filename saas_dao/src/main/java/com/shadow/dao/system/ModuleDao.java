package com.shadow.dao.system;

import com.shadow.bean.system.SsModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleDao {
    //根据企业id查询全部
    List<SsModule> findAll();

    //根据id查询
    SsModule findById(String moduleId);

    //根据id删除
    int delete(String moduleId);

    //保存
    int save(SsModule module);

    //更新
    int update(SsModule module);

    List<SsModule> findModuleByRoleId(String roleId);

    int deleteByRoleId(String roleId);

    int  insert(@Param("roleId") String roleId,@Param("moduleId") String moduleId);

    List<SsModule> findModuleByUserId(String userId);

    List<SsModule> findByBelong(String belong);
}
