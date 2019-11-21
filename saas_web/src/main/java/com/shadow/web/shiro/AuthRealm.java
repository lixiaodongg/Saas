package com.shadow.web.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shadow.bean.system.PeUser;
import com.shadow.bean.system.SsModule;
import com.shadow.service.system.ModuleService;
import com.shadow.service.system.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class AuthRealm extends AuthorizingRealm {

    @Reference
    UserService userService;
    @Reference
    ModuleService moduleService;

    /**
     * 登录验证
     *
     * @param principal
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {

        PeUser user = (PeUser) principal.getPrimaryPrincipal();

        List<SsModule> moduleList = moduleService.findModuleByUserId(user.getUserId());


        SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();

        if (moduleList.size() > 0) {
            for (SsModule module : moduleList) {
                sai.addStringPermission(module.getName());
            }
        }
        return sai;

    }

    /**
     * 授权
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String email = (String) token.getPrincipal();

        List<PeUser> userList = userService.findByEmail(email);
        if (userList.size() == 0) {
            return null;
        }

        PeUser user = userList.get(0);
        /*
         * 参数1: 保存用户的身份信息
         * 参数2:告诉shiro数据库的密码,shiro会自动做校验
         * 参数3:获取realm的名称
         * */
        SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        return sai;
    }
}
