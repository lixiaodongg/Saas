package com.shadow.service.cargo.iml;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.cargo.*;
import com.shadow.dao.cargo.*;
import com.shadow.service.cargo.ExportService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class ExportServiceIml implements ExportService {

    @Autowired
    ExportDao exportDao;

    //附件商品
    @Autowired
    ExtEproductDao extEproductDao;

    //报运商品
    @Autowired
    ExportProductDao exportProductDao;

    //购销合同
    @Autowired
    ContractDao contractDao;

    //购销合同附件表
    @Autowired
    ExportContractProductDao exportContractProductDao;

    //购销合同商品表
    @Autowired
    ContractProductDao contractProductDao;

    @Override
    public PageInfo<Export> findByPage(ExportExample exportExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(exportDao.selectByExample(exportExample));
    }


    @Override
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }


    /*
     * 增删改的时候会对其他表有影响，需要做一些处理
     * */


    /**
     * 输入：3张购销合同id
     * 输出：
     * 1.插入报运表
     * 2.插入报运商品表
     * 3.插入报运附件表
     */
    @Override
    public void save(Export export) {

        export.setId(UUID.randomUUID().toString());
        export.setCreateTime(new Date()); //创建时间
        export.setState(0); //状态
        String contractIds = export.getContractIds();
        String[] contractIdArr = contractIds.split(",");
        StringJoiner joiner = new StringJoiner("/");
        for (String contractId : contractIdArr) {
            Contract contract = contractDao.selectByPrimaryKey(contractId);
            joiner.add(contract.getContractNo());//添加
        }
        export.setCustomerContract(joiner.toString()); //签合同客户名称

        List<String> condition = Arrays.asList(contractIdArr); //查询条件
        //通过条件查询到货物表和附件表，再将货物和附件封装到报运表中

        //购销合同附件条件对象
        ExportContractProductExample exportContractProductExample = new ExportContractProductExample();
        ExportContractProductExample.Criteria ecpCriteria = exportContractProductExample.createCriteria();
        ecpCriteria.andContractIdIn(condition);
        List<ExportContractProduct> exportContractProducts = exportContractProductDao.selectByExample(exportContractProductExample);

        //购销合同商品条件对象
        ContractProductExample contractProductExample = new ContractProductExample();
        ContractProductExample.Criteria cpCriteria = contractProductExample.createCriteria();
        cpCriteria.andContractIdIn(condition);
        List<ContractProduct> contractProducts = contractProductDao.selectByExample(contractProductExample);

        export.setProNum(contractProducts.size()); //设置货物数量
        export.setExtNum(exportContractProducts.size()); //设置附件数量

        //附件表和货物表的插入

        Map<String, String> map = new HashMap<>();

        for (ContractProduct cp : contractProducts) {
            ExportProduct ep = new ExportProduct();
            BeanUtils.copyProperties(cp, ep);
            ep.setId(UUID.randomUUID().toString()); //设置ID
            ep.setExportId(export.getId());
            map.put(cp.getId(), ep.getId()); //给附件表用
            exportProductDao.insertSelective(ep);
        }

        for (ExportContractProduct ecp : exportContractProducts) { //购销合同附件
            ExtEproduct ep = new ExtEproduct(); //报运附件
            BeanUtils.copyProperties(ecp, ep); //赋值
            ep.setExportId(export.getId()); //设置报运单id
            ep.setId(UUID.randomUUID().toString()); //设置id
            ep.setExportProductId(map.get(ecp.getContractProductId())); //设置产品id
            extEproductDao.insertSelective(ep);
        }

        exportDao.insertSelective(export);
    }

    @Override
    public void update(Export export) {
        //修改报运信息
        exportDao.updateByPrimaryKeySelective(export);
        //修改报运单商品
        List<ExportProduct> exportProducts = export.getExportProducts();
        for (ExportProduct exportProduct : exportProducts) {
            exportProductDao.updateByPrimaryKeySelective(exportProduct);
        }
    }

    @Override
    public void delete(String id) {


        exportDao.deleteByPrimaryKey(id);
    }
}
