package com.shadow.dao.company;

import com.shadow.bean.company.SsCompany;

import java.util.List;
public interface CompanyDao {
    List<SsCompany> findAll();

    void insert(SsCompany company);

    void update(SsCompany company);

    SsCompany findCompanyById(String id);

    void deleteById(String id);
}
