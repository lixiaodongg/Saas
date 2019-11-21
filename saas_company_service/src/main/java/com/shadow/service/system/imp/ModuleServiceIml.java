package com.shadow.service.system.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.system.PeUser;
import com.shadow.bean.system.SsModule;
import com.shadow.dao.system.ModuleDao;
import com.shadow.dao.system.UserDao;
import com.shadow.service.system.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceIml implements ModuleService {
    @Autowired
    ModuleDao moduleDao;
    @Autowired
    UserDao userDao;

    @Override
    public PageInfo<SsModule> findByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SsModule> ssModules = findAll();
        return new PageInfo<>(ssModules);
    }

    @Override
    public List<SsModule> findAll() {
        return moduleDao.findAll();
    }

    @Override
    public void save(SsModule module) {
        module.setModuleId(UUID.randomUUID().toString());
        moduleDao.save(module);
    }

    @Override
    public void update(SsModule module) {
        moduleDao.update(module);
    }

    @Override
    public boolean delete(String id) {
        return moduleDao.delete(id) > 0;
    }

    @Override
    public SsModule findById(String id) {
        return moduleDao.findById(id);
    }

    @Override
    public List<SsModule> findModuleByRoleId(String roleId) {
        return moduleDao.findModuleByRoleId(roleId);
    }

    @Override
    public void updateRoleModule(String roleId, String moduleIds) {
        //先通过id删除所有的权限,然后插入权限
        int row = moduleDao.deleteByRoleId(roleId);
        String[] module = moduleIds.split(",");
        for (String moduleId : module) {
            moduleDao.insert(roleId, moduleId);
        }
    }

    @Override
    public List<SsModule> findModuleByUserId(String userId) {

        PeUser user = userDao.findById(userId);

        int degree = user.getDegree();

        if (degree == 0 || degree == 1) {
            return moduleDao.findByBelong(degree + "");
        } else {
            return moduleDao.findModuleByUserId(userId);
        }
    }
}
