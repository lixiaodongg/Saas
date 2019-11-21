package com.shadow.service.cargo.iml;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.cargo.ExportProduct;
import com.shadow.bean.cargo.ExportProductExample;
import com.shadow.dao.cargo.ExportProductDao;
import com.shadow.service.cargo.ExportProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ExportProductServiceIml implements ExportProductService {


    @Autowired
    ExportProductDao exportProductDao;

    @Override
    public PageInfo<ExportProduct> findByPage(ExportProductExample exportProductExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(exportProductDao.selectByExample(exportProductExample));
    }

    @Override
    public List<ExportProduct> findAll(ExportProductExample example) {
        return exportProductDao.selectByExample(example);
    }


    @Override
    public ExportProduct findById(String id) {
        return exportProductDao.selectByPrimaryKey(id);
    }


    /*
     * 增删改的时候会对其他表有影响，需要做一些处理
     * */


    @Override
    public void save(ExportProduct export) {


        exportProductDao.insertSelective(export);
    }

    @Override
    public void update(ExportProduct export) {


        exportProductDao.updateByPrimaryKeySelective(export);
    }

    @Override
    public void delete(String id) {


        exportProductDao.deleteByPrimaryKey(id);
    }

}
