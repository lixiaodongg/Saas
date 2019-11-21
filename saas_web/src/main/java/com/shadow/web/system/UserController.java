package com.shadow.web.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.system.PeDept;
import com.shadow.bean.system.PeRole;
import com.shadow.bean.system.PeUser;
import com.shadow.service.system.DeptService;
import com.shadow.service.system.RoleService;
import com.shadow.service.system.UserService;
import com.shadow.utils.Utils;
import com.shadow.web.BaseController;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequestMapping("system/user")
@Controller
public class UserController extends BaseController {

    @Reference
    UserService userService;
    @Reference
    DeptService deptService;
    @Reference
    RoleService roleService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    //用户列表分页
    @RequestMapping("/list")
    public String list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "5") int PageSize) {
        //1.调用service查询用户列表
        PageInfo<PeUser> pageInfo =
                userService.findByPage(getCompanyId(), pageNum, PageSize);
        //2.将用户列表保存到request域中
        request.setAttribute("pageInfo", pageInfo);
        //3.跳转到对象的页面
        return "system/user/user-list";
    }


    @RequestMapping("/toUpdate")
    public String toUpdate(@RequestParam(defaultValue = "1") String companyId, String userId, Model model) {
        PeUser user = userService.findById(userId);
        model.addAttribute("user", user);
        List<PeDept> deptList = deptService.findAllDept(companyId);
        model.addAttribute("deptList", deptList);
        return "system/user/user-update";
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
        List<PeDept> users = deptService.findAllDept(companyId);
        model.addAttribute("deptList", users);
        return "system/user/user-add";
    }

    @RequestMapping("/edit")
    public String edit(PeUser user) {
        user.setCompanyId(getCompanyId());
        user.setCompanyName(getCompanyName());
        if (StringUtils.isEmpty(user.getUserId())) {
            userService.save(user);
            if (!StringUtils.isEmpty(user.getEmail())) {
                String to = user.getEmail();
                String subject = "入职通知";
                String content = "欢迎来到SaaSExport大家庭,你的账号信息如下："+user;
                Email email = new Email(to, subject, content);
                Map<String, Object> map = Utils.getMapByBean(email);
                System.out.println(email);
                rabbitTemplate.convertAndSend("msg.email", map);
            }
        } else {
            userService.update(user);
        }
        return "redirect:/system/user/list.do";
    }

    private static class Email {
        private String to;
        private String subject;
        private String content;

        Email(String to, String subject, String content) {
            this.to = to;
            this.subject = subject;
            this.content = content;
        }

        @Override
        public String toString() {
            return "Email{" +
                    "to='" + to + '\'' +
                    ", subject='" + subject + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public void delete(String userId, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        boolean flag;
        try {
            flag = userService.delete(userId);
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        response.getWriter().print(flag);
    }

    @RequestMapping("roleList")
    public String roleList(String userId, Model model) {
        PeUser user = userService.findById(userId);
        List<PeRole> roleList = roleService.findAll(getCompanyId());
        List<PeRole> roles = userService.findRoleByUserId(userId);
        StringBuilder sb = new StringBuilder();
        for (PeRole role : roles) {
            sb.append(role.getRoleId()).append(",");
        }
        model.addAttribute("user", user);
        model.addAttribute("userRoleStr", sb.toString());
        model.addAttribute("roleList", roleList);
        return "system/user/user-role";
    }

    @RequestMapping("changeRole")
    public String changeRole(String userId, String[] roleIds) {
        userService.updateUserRole(userId, roleIds);
        return "redirect:list.do";
    }

}
