package com.shadow.service.system.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.system.PeRole;
import com.shadow.bean.system.PeUser;
import com.shadow.dao.system.UserDao;
import com.shadow.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceIml implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public PageInfo<PeUser> findByPage(String companyId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PeUser> peUsers = findAll(companyId);
        return new PageInfo<>(peUsers);
    }

    @Override
    public List<PeUser> findAll(String companyId) {
        return userDao.findAll(companyId);
    }

    @Override
    public void save(PeUser user) {
        user.setUserId(UUID.randomUUID().toString());
        userDao.save(user);
    }

    @Override
    public void update(PeUser user) {
        userDao.update(user);
    }

    @Override
    public boolean delete(String id) {
        return userDao.delete(id) > 0;
    }

    @Override
    public PeUser findById(String id) {
        return userDao.findById(id);
    }

    @Override
    public List<PeRole> findRoleByUserId(String userId) {
        return userDao.findRoleByUserId(userId);
    }

    @Override
    public void updateUserRole(String userId, String[] roleIds) {
        userDao.deleteUserRoleByUserId(userId);
        for (String roleId : roleIds) {
            userDao.insertUserRole(userId, roleId);
        }
    }

    @Override
    public List<PeUser> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
