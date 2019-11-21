package com.shadow.web.login;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shadow.bean.system.PeUser;
import com.shadow.bean.system.SsModule;
import com.shadow.service.system.ModuleService;
import com.shadow.service.system.UserService;
import com.shadow.web.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends BaseController {

    @Reference
    UserService userService;
    @Reference
    ModuleService moduleService;

    /**
     * 登录校验成功 后,将用户的信息放在session中
     *
     * @param email
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public String login(String email, String password) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            return "forward:/login.jsp";
        }
        Subject subject = SecurityUtils.getSubject();
        try {
            AuthenticationToken token = new UsernamePasswordToken(email, password);
            subject.login(token);
        } catch (Exception e) {
            // 登录失败
            request.setAttribute("error", "用户名或者密码错误！");
            return "forward:/login.jsp";
        }

        PeUser user = (PeUser) subject.getPrincipal();
        session.setAttribute("user", user);
        List<SsModule> moduleList = moduleService.findModuleByUserId(user.getUserId());
        session.setAttribute("moduleList", moduleList);
        return "home/main";
    }


    /*
    取消退出的办法,在配置文件中指定哪个资源使用shiro提供的退出过滤器实现自动退出
     * */
/*    @RequestMapping("/logout")
    public String logout() {
        // shiro也提供了退出方法(清除shiro的认证信息)
        SecurityUtils.getSubject().logout();
        // 删除session中用户
        session.removeAttribute("user");
        // 销毁session
        session.invalidate();
        return "company/login";
    }*/


}
