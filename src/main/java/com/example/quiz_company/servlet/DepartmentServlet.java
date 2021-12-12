package com.example.quiz_company.servlet;

import com.example.quiz_company.dao.CompanyDAO;
import com.example.quiz_company.dao.DepartmentDAO;
import com.example.quiz_company.valObject.Company;
import com.example.quiz_company.valObject.Constant;
import com.example.quiz_company.valObject.Department;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DepartmentServlet", value = {"/department-servlet", "/department-edit", "/department-remove"})
public class DepartmentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/department-servlet")) {
            List<Department> departments = new DepartmentDAO().findAll();
            request.setAttribute("departments", departments);
            request.setAttribute("time_start", Constant.TIME_WORK_START);
            request.getRequestDispatcher("./department/department.jsp").forward(request, response);
        } else if (requestURI.contains("/department-edit")) {
            String id = request.getParameter("id");
            Department department = new Department();
            if (id != null) {
                department = new DepartmentDAO().findById(Integer.parseInt(id));
            }
            request.setAttribute("department", department);
            List<Company> companies = new CompanyDAO().findAll();
            request.setAttribute("companies", companies);
            int type_schedule;
            int time1;
            if (department.isFreeSchedule()){
                type_schedule = 1;
                time1 = Constant.TIME_WORK_START;
            } else if (department.isSyncSchedule()) {
                type_schedule = 3;
                time1 = Constant.TIME_WORK_START + department.getScheduleOffset();
            } else {
                type_schedule = 2;
                time1 = Constant.TIME_WORK_START;
            }
            request.setAttribute("time1", time1);
            request.setAttribute("time2", Constant.TIME_WORK_LONG);

            request.setAttribute("schedule", type_schedule);


            request.getRequestDispatcher("./department/depart_edit.jsp").forward(request, response);
        } else if (requestURI.contains("/department-remove")) {
           String id = request.getParameter("id");
            if (id != null) {
                new DepartmentDAO().deleteById(Integer.parseInt(id));
            }
            response.sendRedirect( "./department-servlet");
        } else {
            response.sendRedirect("index.jsp");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String departmentName = request.getParameter("department_name");
        int departmentID = Integer.parseInt(request.getParameter("department_id"));
        int companyId = Integer.parseInt(request.getParameter("company_id"));
        Company company = new CompanyDAO().findById(companyId);
        int typeSchedule = Integer.parseInt(request.getParameter("type_schedule"));
        int timeBegin = Integer.parseInt(request.getParameter("time_start")) - Constant.TIME_WORK_START;
        boolean free_schedule = false;
        boolean sync_schedule = false;

        switch (typeSchedule) {
            case 1 : free_schedule = true; break;
            case 3 : sync_schedule =true; break;
        }

        Department department= new Department(departmentID, departmentName, company, free_schedule, sync_schedule, timeBegin);

        if (departmentID == 0) {
            new DepartmentDAO().addDepartment(department);
        } else {
            new DepartmentDAO().editDepartment(department);
        }
        response.sendRedirect( "./department-servlet");

    }
}
