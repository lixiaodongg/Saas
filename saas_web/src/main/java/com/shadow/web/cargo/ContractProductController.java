package com.shadow.web.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.cargo.*;
import com.shadow.service.cargo.ContractProductService;
import com.shadow.service.cargo.FactoryService;
import com.shadow.web.BaseController;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/cargo/contractProduct")
public class ContractProductController extends BaseController {

    @Reference
    private FactoryService factoryService;


    @Reference
    private ContractProductService contractProductService;

    @RequestMapping("/list")
    public String list(String contractId, @RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "5") int pageSize) {

        //查询生产厂家
        FactoryExample example = new FactoryExample();
        FactoryExample.Criteria criteria = example.createCriteria();
        criteria.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(example);

        request.setAttribute("factoryList", factoryList);
        //根据购销合同id查询该购销合同下所有货物
        ContractProductExample cpExample = new ContractProductExample();
        ContractProductExample.Criteria cpCriteria = cpExample.createCriteria();
        cpCriteria.andContractIdEqualTo(contractId);

        PageInfo<ContractProduct> pageInfo = contractProductService.findByPage(cpExample, pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);
        request.setAttribute("contractId", contractId);

        return "cargo/product/product-list";
    }

    @RequestMapping("/edit")
    public String edit(ContractProduct contractProduct) {
        String company = getCompanyId();
        String companyName = getCompanyName();
        contractProduct.setCompanyId(company);
        contractProduct.setCompanyName(companyName);
        if (StringUtils.isEmpty(contractProduct.getId())) {
            contractProductService.save(contractProduct);
        } else {
            contractProductService.update(contractProduct);
        }
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractProduct.getContractId();
    }


    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        //货物信息
        ContractProduct contractProduct = contractProductService.findById(id);
        request.setAttribute("contractProduct", contractProduct);

        //查询所有厂家
        FactoryExample example = new FactoryExample();
        FactoryExample.Criteria criteria = example.createCriteria();
        criteria.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(example);

        request.setAttribute("factoryList", factoryList);

        return "cargo/product/product-update";

    }

    /**
     * 删除购销合同
     */
    @RequestMapping("/delete")
    public String delete(String id, String contractId) {
        contractProductService.delete(id);
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractId;
    }

    @RequestMapping("/toImport")
    public String toImport(String contractId) {
        request.setAttribute("contractId", contractId);
        return "cargo/product/product-import";
    }

    @RequestMapping("import")
    String importExcel(String contractId, MultipartFile file) throws IOException {
        //将上传的文件写到数据库中
        //1. 根据上传的文件创建工作簿
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        //2. 获取工作表
        Sheet sheet = workbook.getSheetAt(0);

        //3. 获取总行数
        int totalRows = sheet.getPhysicalNumberOfRows();
        // 获取企业
        String loginCompanyId = getCompanyId();
        String loginCompanyName = getCompanyName();
        //4. 第一行是标题，所以从第二行开始遍历
        for (int i=1; i<totalRows; i++) {
            //5. 获取每一行
            Row row = sheet.getRow(i);
            //6. 获取每一行的每一列, 设置到对象属性中
            ContractProduct contractProduct = new ContractProduct();
            //空列	生产厂家	货号	数量	包装单位(PCS/SETS)	装率	箱数	单价	货物描述	要求
            contractProduct.setFactoryName(row.getCell(1).getStringCellValue());
            contractProduct.setProductNo(row.getCell(2).getStringCellValue());
            contractProduct.setCnumber((int) row.getCell(3).getNumericCellValue());
            contractProduct.setPackingUnit(row.getCell(4).getStringCellValue());
            contractProduct.setLoadingRate(row.getCell(5).getNumericCellValue() + "");
            contractProduct.setBoxNum((int) row.getCell(6).getNumericCellValue());
            contractProduct.setPrice(row.getCell(7).getNumericCellValue());
            contractProduct.setProductDesc(row.getCell(8).getStringCellValue());
            contractProduct.setProductRequest(row.getCell(9).getStringCellValue());
            // 设置购销合同id
            contractProduct.setContractId(contractId);
            // 设置企业
            contractProduct.setCompanyId(loginCompanyId);
            contractProduct.setCompanyName(loginCompanyName);
            // 设置工厂id
            String factoryId =
                    factoryService.findFactoryNameById(contractProduct.getFactoryName());
            contractProduct.setFactoryId(factoryId);

            //7. 调用service，保存货物
            contractProductService.save(contractProduct);
        }

        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractId;
    }

}
