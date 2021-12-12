package com.example.quiz_company.servlet;

import com.example.quiz_company.dao.CompanyDAO;
import com.example.quiz_company.valObject.Company;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CompanyServlet", value = {"/company-servlet", "/company-edit", "/company-remove"})
public class CompanyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/company-servlet")) {
            List<Company> companies = new CompanyDAO().findAll();
            request.setAttribute("companies", companies);
            request.getRequestDispatcher("./company/company.jsp").forward(request, response);
        } else if (requestURI.contains("/company-edit")) {
            String id = request.getParameter("id");
            Company company = new Company(0,"");
            if (id != null) {
                company = new CompanyDAO().findById(Integer.parseInt(id));
            }
            request.setAttribute("company", company);
            request.getRequestDispatcher("./company/company_edit.jsp").forward(request, response);
        } else if (requestURI.contains("/company-remove")) {
            String id = request.getParameter("id");
            if (id != null) {
                new CompanyDAO().deleteById(Integer.parseInt(id));
            }
            response.sendRedirect( "./company-servlet");
        } else {
            response.sendRedirect(".");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String companyName = request.getParameter("company_name");
        int companyId = Integer.parseInt(request.getParameter("company_id"));
        if (companyName != null && !companyName.isEmpty()) {
            Company company = new Company(companyId, companyName);
            if (companyId == 0) {
                 new CompanyDAO().addCompany(company);
            } else {
                new CompanyDAO().editCompany(company);
            }
        }
        response.sendRedirect( "./company-servlet");
    }
}
