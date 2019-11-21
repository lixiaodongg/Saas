package com.shadow.web.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;


public class CustomerCredentialsMatcher extends SimpleCredentialsMatcher {


    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        String userName = (String) token.getPrincipal();

        String password = new String((char[]) token.getCredentials());

        String MD5Pwd = new Md5Hash(password,userName).toString();

        String dbPwd = (String) info.getCredentials();
        return MD5Pwd.equals(dbPwd);
    }
}
