package com.shadow.service.cargo;

import com.github.pagehelper.PageInfo;
import com.shadow.bean.cargo.Factory;
import com.shadow.bean.cargo.FactoryExample;

import java.util.List;

public interface FactoryService {

    /**
     * @param example  条件
     * @param pageNum  当前页
     * @param pageSize 每页条数
     * @return 分页对象
     */
    PageInfo<Factory> findByPage(FactoryExample example, int pageNum, int pageSize);


    List<Factory> findAll(FactoryExample example);


    Factory findById(String id);

    int save(Factory factory);

    int update(Factory factory);

    int delete(String id);


    String findFactoryNameById(String factoryName);
}
