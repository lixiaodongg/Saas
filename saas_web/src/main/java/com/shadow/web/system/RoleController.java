package com.shadow.web.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.system.PeDept;
import com.shadow.bean.system.PeRole;
import com.shadow.bean.system.SsModule;
import com.shadow.service.system.DeptService;
import com.shadow.service.system.ModuleService;
import com.shadow.service.system.RoleService;
import com.shadow.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("system/role")
@Controller
public class RoleController extends BaseController {

    @Reference
    RoleService roleService;
    @Reference
    DeptService deptService;

    @Reference
    private ModuleService moduleService;

    //用户列表分页
    @RequestMapping("/list")
    public String list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "5") int PageSize) {
        //1.调用service查询用户列表
        PageInfo<PeRole> pageInfo =
                roleService.findByPage(getCompanyId(), pageNum, PageSize);
        //2.将用户列表保存到request域中
        request.setAttribute("pageInfo", pageInfo);
        //3.跳转到对象的页面
        return "system/role/role-list";
    }


    @RequestMapping("/toUpdate")
    public String toUpdate(@RequestParam(defaultValue = "1") String companyId, String roleId, Model model) {
        PeRole role = roleService.findById(roleId);
        model.addAttribute("role", role);
        List<PeDept> deptList = deptService.findAllDept(companyId);
        model.addAttribute("deptList", deptList);
        return "system/role/role-update";
    }

    /**
     * "redirect:/system/system/list.do"
     *
     * @param model
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(@RequestParam(defaultValue = "1") String companyId, Model model) {
        model.addAttribute("companyId", companyId);
        List<PeDept> deptList = deptService.findAllDept(companyId);
        model.addAttribute("deptList", deptList);
        return "system/role/role-add";
    }

    @RequestMapping("/edit")
    public String edit(PeRole role) {
        role.setCompanyId(getCompanyId());
        role.setCompanyName(getCompanyName());
        if (StringUtils.isEmpty(role.getRoleId())) {
            roleService.save(role);
        } else {
            roleService.update(role);
        }
        return "redirect:/system/role/list.do";
    }


    @RequestMapping("/delete")
    @ResponseBody
    public void delete(String roleId, HttpServletResponse response) throws IOException {
        boolean flag;
        try {
            flag = roleService.delete(roleId);
        } catch (Exception e) {
            flag = false;
        }
        response.getWriter().print(flag);
    }

    @RequestMapping("/roleModule")
    public String RoleModule(String roleId, Model model) {
        PeRole role = roleService.findById(roleId);
        model.addAttribute("role", role);
        return "system/role/role-module";
    }


    @PostMapping("/getZtreeNodes")
    @ResponseBody
    public List<Map<String, Object>> getZtreeNodes(String roleId) {
        //自定义返回的结果
        List<Map<String, Object>> result = new ArrayList<>();

        //查询所有的权限

        List<SsModule> moduleList = moduleService.findAll();

        List<SsModule> roleModuleList = moduleService.findModuleByRoleId(roleId);

        for (SsModule module : moduleList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", module.getModuleId());
            map.put("pId", module.getParentId());
            map.put("name", module.getName());
            map.put("open", true);
            if (roleModuleList.contains(module)) {
                map.put("checked", true);
            }
            result.add(map);
        }
        return result;
    }

    @RequestMapping("updateRoleModule")
    public String updateRoleModule(String roleId, String moduleIds) {
        moduleService.updateRoleModule(roleId, moduleIds);
        return "redirect:list.do";
    }


}
