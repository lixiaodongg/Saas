package com.shadow.service.cargo.iml;

import com.alibaba.dubbo.config.annotation.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.cargo.Contract;
import com.shadow.bean.cargo.ContractExample;
import com.shadow.dao.cargo.ContractDao;
import com.shadow.service.cargo.ContractService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class ContractServiceIml implements ContractService {

    @Autowired
    ContractDao contractDao;

    @Override
    public PageInfo<Contract> findByPage(ContractExample contractExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Contract> list = findAll(contractExample);
        return new PageInfo<>(list);
    }

    @Override
    public List<Contract> findAll(ContractExample contractExample) {
        return contractDao.selectByExample(contractExample);
    }

    @Override
    public Contract findById(String id) {
        return contractDao.selectByPrimaryKey(id);
    }

    @Override
    public int save(Contract contract) {

        //设置状态，总金额，货物数量，附件数量
        contract.setId(UUID.randomUUID().toString());
        contract.setState(0);
        contract.setTotalAmount(0d);
        contract.setProNum(0);
        contract.setExtNum(0);

        contract.setCreateTime(new Date());

        return contractDao.insert(contract);
    }

    @Override
    public int update(Contract contract) {
        return contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public int delete(String id) {
        return contractDao.deleteByPrimaryKey(id);
    }
}
