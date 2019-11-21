package com.shadow.service.cargo.iml;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.cargo.Contract;
import com.shadow.bean.cargo.ExportContractProduct;
import com.shadow.bean.cargo.ExportContractProductExample;


import com.shadow.dao.cargo.ContractDao;
import com.shadow.dao.cargo.ContractProductDao;
import com.shadow.dao.cargo.ExportContractProductDao;
import com.shadow.service.cargo.ExportContractProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class ExportContractProductServiceIml implements ExportContractProductService {

    @Autowired
    ExportContractProductDao exportContractProductDao;
    @Autowired
    ContractDao contractDao;
    @Autowired
    ContractProductDao contractProductDao;

    @Override
    public PageInfo<ExportContractProduct> findByPage(ExportContractProductExample example, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(findAll(example));
    }

    @Override
    public List<ExportContractProduct> findAll(ExportContractProductExample example) {
        return exportContractProductDao.selectByExample(example);
    }

    @Override
    public ExportContractProduct findById(String id) {
        return exportContractProductDao.selectByPrimaryKey(id);
    }

    @Override
    public int save(ExportContractProduct exportContractProduct) {

        exportContractProduct.setId(UUID.randomUUID().toString());

        Double amount = 0d;
        if (exportContractProduct.getPrice() != null && exportContractProduct.getCnumber() != null) {
            amount = exportContractProduct.getPrice() * exportContractProduct.getCnumber();
        }
        exportContractProduct.setAmount(amount);

        //修改购销合同
        Contract contract = contractDao.selectByPrimaryKey(exportContractProduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount() + amount);
        contract.setExtNum(contract.getExtNum() + 1);
        contractDao.updateByPrimaryKeySelective(contract);
        return exportContractProductDao.insertSelective(exportContractProduct);
    }

    @Override
    public int update(ExportContractProduct exportContractProduct) {
        //更新 重新计算附件的货物数量和价格
        //购销合同的价格加上现在的价格-之前的价格
        String id = exportContractProduct.getId();
        ExportContractProduct data = exportContractProductDao.selectByPrimaryKey(id);//数据库中数据

        Double price = exportContractProduct.getPrice();
        Long cnumber = exportContractProduct.getCnumber();
        Double amount = 0d;
        if (price != null && cnumber != null) {//如果单价和数量不为空
            amount = price * cnumber;
        }
        exportContractProduct.setAmount(amount); //设置值

        String contractId = exportContractProduct.getContractId(); //购销合同
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        contract.setTotalAmount(contract.getTotalAmount() - data.getAmount() + amount);

        return exportContractProductDao.updateByPrimaryKeySelective(exportContractProduct); //更新旧的数据
    }

    @Override
    public int delete(String id) {
        //通过id查询到附件表
        ExportContractProduct exportContractProduct = exportContractProductDao.selectByPrimaryKey(id);
        Double amount = exportContractProduct.getAmount();
        String contractId = exportContractProduct.getContractId();
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        contract.setTotalAmount(contract.getTotalAmount() - amount);
        contract.setExtNum(contract.getExtNum() - 1);
        contractDao.updateByPrimaryKeySelective(contract);
        return exportContractProductDao.deleteByPrimaryKey(id);
    }
}
