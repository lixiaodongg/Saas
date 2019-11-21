package com.shadow.web.cargo;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.cargo.Contract;
import com.shadow.bean.cargo.ContractExample;
import com.shadow.service.cargo.ContractService;
import com.shadow.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cargo/contract")
public class ContractController extends BaseController {

    @Reference
    ContractService contractService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {

        ContractExample example = new ContractExample();
        //排序
        example.setDistinct(true);
        example.setOrderByClause("create_time desc");
        //添加条件，企业ID
        ContractExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(getCompanyId());
        PageInfo<Contract> pageInfo = contractService.findByPage(example, pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);
        return "cargo/contract/contract-list";
    }


    /**
     * 2. 进入添加页面
     *
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "cargo/contract/contract-add";
    }

    /**
     * 3. 添加/修改
     */
    @RequestMapping("/edit")
    public String edit(Contract contract) {
        // 获取登录用户的所属企业信息（先写死）
        contract.setCompanyId(getCompanyId());
        contract.setCompanyName(getCompanyName());
        // 根据id判断
        if (StringUtils.isEmpty(contract.getId())) {
            // 添加创建者信息
            contract.setCreateBy(getUser().getUserId());
            contract.setCreateDept(getUser().getDeptId());
            // 添加
            contractService.save(contract);
        } else {
            // 修改
            contractService.update(contract);
        }

        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 4. 进入修改页面
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        // 根据部门id查询
        Contract contract = contractService.findById(id);
        request.setAttribute("contract", contract);

        return "cargo/contract/contract-update";
    }

    /**
     * 5. 删除
     */
    @RequestMapping("/delete")
    public String delete(String id) {
        contractService.delete(id);
        return "redirect:/cargo/contract/list.do";
    }


    /**
     * 查看
     *
     * @param id
     * @return
     */

    @RequestMapping("toView")
    String toView(String id) {
        Contract contract = contractService.findById(id);
        request.setAttribute("contract", contract);
        return "cargo/contract/contract-view";
    }

    /**
     * 提交。将状态由0改为1
     */
    @RequestMapping("/submit")
    public String submit(String id) {
        changeState(id, 1);
        return "redirect:/cargo/contract/list.do";
    }

    private void changeState(String id, int i) {
        Contract contract = new Contract();
        contract.setId(id);
        contract.setState(i);
        contractService.update(contract);
    }


    /**
     * 取消。将状态由1改为0
     */
    @RequestMapping("/cancel")
    public String cancel(String id) {
        changeState(id, 0);
        return "redirect:/cargo/contract/list.do";
    }



}
