package com.shadow.service.system.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.system.PeRole;
import com.shadow.dao.system.RoleDao;
import com.shadow.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
@Service
public class RoleServiceIml implements RoleService {
    @Autowired
    RoleDao roleDao;

    @Override
    public PageInfo<PeRole> findByPage(String companyId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PeRole> peRoles = findAll(companyId);
        return new PageInfo<>(peRoles);
    }

    @Override
    public List<PeRole> findAll(String companyId) {
        return roleDao.findAll(companyId);
    }

    @Override
    public void save(PeRole role) {
        role.setRoleId(UUID.randomUUID().toString());
        roleDao.save(role);
    }

    @Override
    public void update(PeRole role) {
        roleDao.update(role);
    }

    @Override
    public boolean delete(String id) {
        return roleDao.delete(id) > 0;
    }

    @Override
    public PeRole findById(String id) {
        return roleDao.findById(id);
    }
}
