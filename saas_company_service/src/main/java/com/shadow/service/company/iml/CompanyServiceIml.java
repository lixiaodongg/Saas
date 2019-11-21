package com.shadow.service.company.iml;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.company.SsCompany;
import com.shadow.dao.company.CompanyDao;
import com.shadow.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceIml implements CompanyService {

    @Autowired
    CompanyDao companyDao;

    @Override
    public List<SsCompany> findAll() {
        return companyDao.findAll();
    }

    @Override
    public void edit(SsCompany company) {
        if (StringUtils.isEmpty(company.getId())) {
            company.setId(UUID.randomUUID().toString());
            companyDao.insert(company);
        } else {
            companyDao.update(company);
        }
    }

    @Override
    public SsCompany findCompanyById(String id) {
        return companyDao.findCompanyById(id);
    }

    @Override
    public void deleteById(String id) {
        companyDao.deleteById(id);
    }

    @Override
    public PageInfo<SsCompany> findByPage(int pageNum, int pageSize) {
        /*
         * 其后第一条查询语句自动分页
         * */
        PageHelper.startPage(pageNum, pageSize);
        List<SsCompany> list = companyDao.findAll();
        return new PageInfo<>(list);
    }
}
