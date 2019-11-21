package com.shadow.web.company;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.shadow.bean.company.SsCompany;
import com.shadow.service.company.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Reference
    CompanyService companyService;


    /**
     * http://localhost:8080/company/list.do
     * 400:bad request 封装请求数据
     * 404:找不到资源文件
     * 405:
     *
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5")Integer pageSize) {
        PageInfo<SsCompany> pageInfo = companyService.findByPage(pageNum, pageSize);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("company/company-list");
        mv.addObject("pageInfo", pageInfo);
        return mv;
    }

    @RequestMapping("/error")
    public String error() {
        int a = 1 / 0;
        return "error";
    }

    /**
     * http://localhost:8080/company/converter.do?date=2010-10-10
     *
     * @param date
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/converter")
    public void dateConverter(Date date, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(date);
        request.getRequestDispatcher("list.do").forward(request, response);
    }

    @RequestMapping("/login")
    public String login() {
        return "/login.jsp";
    }


    @RequestMapping("/home")
    public String home() {
        return "home/home";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "company/company-add";
    }

    @RequestMapping("/delete")
    public void delete(String id, HttpServletResponse response) throws IOException {
        companyService.deleteById(id);
        response.sendRedirect("/company/list.do");
    }


    @RequestMapping("/toUpdate")
    public String toUpdate(String id, Model model) {
        SsCompany company = companyService.findCompanyById(id);
        model.addAttribute("company", company);
        return "company/company-update";
    }


    @RequestMapping("/edit")
    public void edit(SsCompany company, HttpServletRequest request, HttpServletResponse response) throws IOException {
        companyService.edit(company);
        response.sendRedirect("/company/list.do");
    }

    @RequestMapping("/page")
    public void page() {
        int pageNum = 1;
        int pageSize = 3;
        PageInfo<SsCompany> pageInfo = companyService.findByPage(pageNum, pageSize);
        System.out.println(pageInfo);
    }

}
