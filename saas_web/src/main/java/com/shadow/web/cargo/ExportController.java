package com.shadow.web.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.cargo.*;
import com.shadow.service.cargo.ContractService;
import com.shadow.service.cargo.ExportProductService;
import com.shadow.service.cargo.ExportService;
import com.shadow.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Controller
@RequestMapping("/cargo/export")
public class ExportController extends BaseController {

    @Reference
    private ExportService exportService;
    @Reference
    private
    ContractService contractService;

    @Reference
    private
    ExportProductService exportProductService;

    /**
     * 报运列表
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {
        //条件对象
        ExportExample exportExample = new ExportExample();
        exportExample.setOrderByClause("create_time desc");//倒序

        ExportExample.Criteria criteria = exportExample.createCriteria();
        criteria.andCompanyIdEqualTo(getCompanyId());

        //分页查询
        PageInfo<Export> pageInfo = exportService.findByPage(exportExample, pageNum, pageSize);

        request.setAttribute("pageInfo", pageInfo);

        return "cargo/export/export-list";
    }

    @RequestMapping("/contractList")
    public String contractList(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "5") Integer pageSize) {

        ContractExample example = new ContractExample();
        ContractExample.Criteria criteria = example.createCriteria();

        //查询条件是：1，状态为1； 2，企业id
        criteria.andStateEqualTo(1);
        criteria.andCompanyIdEqualTo(getCompanyId());

        PageInfo<Contract> pageInfo = contractService.findByPage(example, pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);
        return "cargo/export/export-contractList";
    }


    @RequestMapping("/toExport")
    public String toExport(String id) {
        request.setAttribute("id", id);
        return "cargo/export/export-toExport";
    }

    @RequestMapping("/edit")
    String edit(Export export) {
        export.setCompanyId(getCompanyId());
        export.setCompanyName(getCompanyName());
        if (StringUtils.isEmpty(export.getId())) {
            try {
                exportService.save(export);
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            exportService.update(export);
        }
        return "redirect:/cargo/export/list.do";
    }

    @RequestMapping("/submit")
    public String submit(String id) {
        Export export = new Export();
        export.setId(id);
        export.setState(2);
        exportService.update(export);
        return "cargo/export/export-list";
    }


    /**
     * 报运
     */
    public String exportE() {
        //封装请求数据
        //远程调用电子报运
        //获取报运结果
        //根据结果修改数据库中的信息

        return null;

    }

    /**
     * 更新，id用来查询，跳转
     *
     * @param id
     * @return
     */
    @RequestMapping("toUpdate")
    public String toUpdate(String id) {
        Export export = exportService.findById(id);
        request.setAttribute("export", export);
        //根据报运单id查询报运商品

        ExportProductExample example = new ExportProductExample();
        example.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> eps = exportProductService.findAll(example);
        request.setAttribute("eps", eps);
        return "cargo/export/export-update";
    }


}
