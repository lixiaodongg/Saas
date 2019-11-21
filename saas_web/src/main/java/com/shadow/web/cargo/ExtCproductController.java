package com.shadow.web.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.cargo.*;
import com.shadow.service.cargo.ExportContractProductService;
import com.shadow.service.cargo.FactoryService;
import com.shadow.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cargo/extCproduct")
public class ExtCproductController extends BaseController {


    @Reference
    private ExportContractProductService exportContractProductService;

    @Reference
    private FactoryService factoryService;

    /**
     * 附件的列表与添加
     * 查询附件的厂家，查询货物的附件
     * 保存上述信息‘
     * contractId
     * contractProductId
     */
    @RequestMapping("/list")
    public String list(String contractId, String contractProductId, @RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "5") int pageSize) {

        //查询生产厂家
        FactoryExample example = new FactoryExample();
        FactoryExample.Criteria criteria = example.createCriteria();
        criteria.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(example);


        //根据购销合同id查询该购销合同下所有货物
        ExportContractProductExample cpExample = new ExportContractProductExample();
        ExportContractProductExample.Criteria cpCriteria = cpExample.createCriteria();
        cpCriteria.andContractProductIdEqualTo(contractProductId);
        PageInfo<ExportContractProduct> pageInfo = exportContractProductService.findByPage(cpExample, pageNum, pageSize);

        request.setAttribute("pageInfo", pageInfo);
        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId", contractProductId);
        request.setAttribute("factoryList", factoryList);
        return "cargo/extc/extc-list";
    }


    @RequestMapping("/edit")
    public String edit(ExportContractProduct exportContractProduct) {
        String company = getCompanyId();
        String companyName = getCompanyName();
        exportContractProduct.setCompanyId(company);
        exportContractProduct.setCompanyName(companyName);
        if (StringUtils.isEmpty(exportContractProduct.getId())) {
            exportContractProductService.save(exportContractProduct);
        } else {
            exportContractProductService.update(exportContractProduct);
        }
        return "redirect:list.do?contractId="
                + exportContractProduct.getContractId() + "&contractProductId=" + exportContractProduct.getContractProductId();
    }


    @RequestMapping("/toUpdate")
    public String toUpdate(String id, String contractId, String contractProductId) {
        //货物信息
        ExportContractProduct exportContractProduct = exportContractProductService.findById(id);
        request.setAttribute("extCproduct", exportContractProduct);

        //查询所有厂家
        FactoryExample example = new FactoryExample();
        FactoryExample.Criteria criteria = example.createCriteria();
        criteria.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(example);

        request.setAttribute("factoryList", factoryList);
        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId", contractProductId);
        return "cargo/extc/extc-update";

    }

    /**
     * 删除购销合同
     */
    @RequestMapping("/delete")
    public String delete(String id, String contractId, String contractProductId) {
        exportContractProductService.delete(id);
        return "redirect:/cargo/extCproduct/list.do?contractId=" + contractId + "&contractProductId=" + contractProductId;
    }


}