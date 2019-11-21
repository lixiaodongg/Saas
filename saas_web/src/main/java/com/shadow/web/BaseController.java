package com.shadow.web;

import com.shadow.bean.system.PeUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    @Autowired
    protected HttpSession session;

    public String getCompanyId() {
        return getUser().getCompanyId();
    }

    public PeUser getUser() {
        return (PeUser) session.getAttribute("user");
    }

    public String getCompanyName() {
        return getUser().getCompanyName();
    }

}
