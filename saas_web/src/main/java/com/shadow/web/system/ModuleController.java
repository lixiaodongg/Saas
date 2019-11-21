package com.shadow.web.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import com.shadow.bean.system.SsModule;
import com.shadow.service.system.DeptService;
import com.shadow.service.system.ModuleService;
import com.shadow.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequestMapping("system/module")
@Controller
public class ModuleController extends BaseController {

    @Reference
    ModuleService moduleService;
    @Reference
    DeptService deptService;

    //用户列表分页
    @RequestMapping("/list")
    public String list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "5") int PageSize) {
        //1.调用service查询用户列表
        PageInfo<SsModule> pageInfo =
                moduleService.findByPage(pageNum, PageSize);
        //2.将用户列表保存到request域中
        request.setAttribute("pageInfo", pageInfo);
        //3.跳转到对象的页面
        return "system/module/module-list";
    }


    @RequestMapping("/toUpdate")
    public String toUpdate(String moduleId, Model model) {
        SsModule module = moduleService.findById(moduleId);
        model.addAttribute("module", module);
        List<SsModule> menus = moduleService.findAll();
        model.addAttribute("menus", menus);
        return "system/module/module-update";
    }

    /**
     * "redirect:/system/system/list.do"
     *
     * @param model
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(Model model) {
        List<SsModule> list = moduleService.findAll();
        model.addAttribute("menus", list);
        return "system/module/module-add";
    }

    @RequestMapping("/edit")
    public String edit(SsModule module) {
        //1.判断是否具有id属性
        if (StringUtils.isEmpty(module.getModuleId())) {
            //2.没有id，保存
            moduleService.save(module);
        } else {
            //3.有id，更新
            moduleService.update(module);
        }
        return "redirect:/system/module/list.do";
    }


    @RequestMapping("/delete")
    @ResponseBody
    public void delete(String moduleId, HttpServletResponse response) throws IOException {
        boolean flag;
        try {
            flag = moduleService.delete(moduleId);
        } catch (Exception e) {
            flag = false;
        }
        response.getWriter().print(flag);
    }

}
