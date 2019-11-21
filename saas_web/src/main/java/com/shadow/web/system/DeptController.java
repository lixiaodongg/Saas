package com.shadow.web.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.system.PeDept;
import com.shadow.service.system.DeptService;
;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("system/dept")
public class DeptController {

    @Reference
    DeptService deptService;

    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "1") String companyId) {
        PageInfo<PeDept> pageInfo = deptService.findByPage(companyId, pageNum, pageSize);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("system/dept/dept-list");
        mv.addObject("pageInfo", pageInfo);
        return mv;
    }


    /**
     * "redirect:/system/system/list.do"
     *
     * @param model
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(@RequestParam(defaultValue = "1") String companyId, String deptId, Model model) {
        PeDept dept = deptService.findDeptById(deptId);
        model.addAttribute("dept", dept);
        List<PeDept> deptList = deptService.findAllDept(companyId);
        for (Iterator<PeDept> iterator = deptList.iterator(); iterator.hasNext(); ) {
            PeDept next = iterator.next();
            PeDept parent = next.getParent();
            if (parent != null) {
                if (parent.getDeptId().equals(dept.getDeptId())) {
                    iterator.remove();
                }
            }
        }
        model.addAttribute("deptList", deptList);
        return "system/dept/dept-update";
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
        List<PeDept> dept = deptService.findAllDept(companyId);
        model.addAttribute("deptList", dept);
        return "system/dept/dept-add";
    }


    @RequestMapping("/edit")
    public String edit(PeDept dept) {
        String companyId = "1";
        String companyName = "传智播客教育股份有限公司";
        dept.setCompanyId(companyId);
        dept.setCompanyName(companyName);
        if (StringUtils.isEmpty(dept.getDeptId())) {
            deptService.insert(dept);
        } else {
            deptService.update(dept);
        }
        return "redirect:/system/dept/list.do";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public void delete(String deptId, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        boolean flag = deptService.deleteById(deptId);
        response.getWriter().print(flag);
    }

}
