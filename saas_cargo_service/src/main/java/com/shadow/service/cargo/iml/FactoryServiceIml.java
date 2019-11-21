package com.shadow.service.cargo.iml;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.cargo.Factory;
import com.shadow.bean.cargo.FactoryExample;
import com.shadow.dao.cargo.FactoryDao;
import com.shadow.service.cargo.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class FactoryServiceIml implements FactoryService {

    @Autowired
    FactoryDao factoryDao;

    @Override
    public PageInfo<Factory> findByPage(FactoryExample example, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(findAll(example));

    }

    @Override
    public List<Factory> findAll(FactoryExample example) {
        return factoryDao.selectByExample(example);
    }

    @Override
    public Factory findById(String id) {
        return factoryDao.selectByPrimaryKey(id);
    }

    @Override
    public int save(Factory factory) {
        return factoryDao.insert(factory);
    }

    @Override
    public int update(Factory factory) {
        return factoryDao.updateByPrimaryKey(factory);
    }

    @Override
    public int delete(String id) {
        return factoryDao.deleteByPrimaryKey(id);
    }

    @Override
    public String findFactoryNameById(String factoryName) {
        // 构造条件：工厂名称
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andFactoryNameEqualTo(factoryName);
        // 查询
        List<Factory> list = factoryDao.selectByExample(factoryExample);
        return (list != null && list.size() > 0) ? list.get(0).getId() : "";
    }
}
