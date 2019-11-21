package com.shadow.service.company;

import com.github.pagehelper.PageInfo;
import com.shadow.bean.company.SsCompany;

import java.util.List;

public interface CompanyService {
    List<SsCompany> findAll();

    void edit(SsCompany company);

    SsCompany findCompanyById(String id);

    void deleteById(String id);


    /**
     * 分页查询
     * @param pageNum 当前页
     * @param pageSize 页大小
     * @return 封装的pageInfo对象
     */
    PageInfo<SsCompany> findByPage(int pageNum, int pageSize);

}
