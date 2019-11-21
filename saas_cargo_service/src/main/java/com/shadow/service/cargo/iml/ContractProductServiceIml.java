package com.shadow.service.cargo.iml;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.cargo.*;
import com.shadow.dao.cargo.ContractDao;
import com.shadow.dao.cargo.ContractProductDao;
import com.shadow.dao.cargo.ExportContractProductDao;
import com.shadow.service.cargo.ContractProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class ContractProductServiceIml implements ContractProductService {

    @Autowired
    ContractProductDao contractProductDao;

    @Autowired
    ContractDao contractDao;
    @Autowired
    ExportContractProductDao exportContractProductDao;

    @Override
    public PageInfo<ContractProduct> findByPage(ContractProductExample example, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(findAll(example));
    }

    @Override
    public List<ContractProduct> findAll(ContractProductExample example) {
        return contractProductDao.selectByExample(example);
    }

    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    @Override
    public int save(ContractProduct contractProduct) {

        contractProduct.setId(UUID.randomUUID().toString());

        Double amount = 0d;
        if (contractProduct.getPrice() != null && contractProduct.getCnumber() != null) {
            amount = contractProduct.getPrice() * contractProduct.getCnumber();
        }
        contractProduct.setAmount(amount);

        //修改购销合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount() + amount);
        contract.setProNum(contract.getProNum() + 1);
        contractDao.updateByPrimaryKeySelective(contract);

        return contractProductDao.insert(contractProduct);
    }

    @Override
    public int update(ContractProduct contractProduct) {
        Double amount = 0d;
        if (contractProduct.getPrice() != null && contractProduct.getCnumber() != null) {
            amount = contractProduct.getPrice() * contractProduct.getCnumber();
        }
        contractProduct.setAmount(amount);


        ContractProduct cp =
                contractProductDao.selectByPrimaryKey(contractProduct.getId());
        Double oldAmount = cp.getAmount();

        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());

        contract.setTotalAmount(contract.getTotalAmount() + amount - oldAmount);
        contractDao.updateByPrimaryKeySelective(contract);

        return contractProductDao.updateByPrimaryKey(contractProduct);
    }

    @Override
    public int delete(String id) {
        //根据货物id查询货物，获取货物的金额
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);


        //根据货物id查询所有附件
        ExportContractProductExample example = new ExportContractProductExample();
        ExportContractProductExample.Criteria criteria = example.createCriteria();
        criteria.andContractProductIdEqualTo(id);
        List<ExportContractProduct> exportContractProductList = exportContractProductDao.selectByExample(example);
        //遍历附件，累加附件金额,删除附件
        Double exAmount = 0d; //附件的金额
        for (ExportContractProduct exportContractProduct : exportContractProductList) {
            exAmount += exportContractProduct.getAmount();
            exportContractProductDao.deleteByPrimaryKey(exportContractProduct.getId());
        }

        //修改购销合同
        //1.金额
        //2.货物数量
        //3.删除货物
        Double productAmount = contractProduct.getAmount();
        String contractId = contractProduct.getContractId();
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        contract.setTotalAmount(contract.getTotalAmount() - productAmount - exAmount);
        contract.setProNum(contract.getProNum() - 1);
        contract.setExtNum(contract.getProNum() - exportContractProductList.size());
        contractDao.updateByPrimaryKeySelective(contract);
        return contractProductDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<ContractProductVo> findByShipTime(String companyId, String inputDate) {
        return contractProductDao.findByShipTime(companyId,inputDate);
    }


}
