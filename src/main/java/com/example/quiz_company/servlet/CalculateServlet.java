package com.example.quiz_company.servlet;

import com.example.quiz_company.calculate.CompanyCalculate;
import com.example.quiz_company.calculate.DepartmentCalculate;
import com.example.quiz_company.calculate.EmployeeCalculate;
import com.example.quiz_company.dao.CompanyDAO;
import com.example.quiz_company.dao.DepartmentDAO;
import com.example.quiz_company.dao.EmployeeDAO;
import com.example.quiz_company.valObject.Company;
import com.example.quiz_company.valObject.Constant;
import com.example.quiz_company.valObject.Department;
import com.example.quiz_company.valObject.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CalculateServlet", value = "/calculate-servlet")
public class CalculateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Company> companies= new CompanyDAO().findAll();
        List<Department> departments = new DepartmentDAO().findAll();
        List<Employee> employees = new EmployeeDAO().findAll();

        request.setAttribute("companies", companies);
        request.setAttribute("departments", departments);
        request.setAttribute("employees", employees);

        request.getRequestDispatcher("./calculate/calculate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int var = Integer.parseInt(request.getParameter("option1"));
        String result = "";
        String ver = "";
        double efficiency = 0;
        List<EmployeeCalculate> employees = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        switch (var) {
            case 1:
                int companyId = Integer.parseInt(request.getParameter("company_id"));
                CompanyCalculate companyCalculate = new CompanyCalculate( new CompanyDAO().findById(companyId));
                companyCalculate.calculateEfficienty();
                result = mapper.writeValueAsString(companyCalculate);
                ver = "по компании: "+ companyCalculate.getCompany().getName();
                efficiency = companyCalculate.getEfficiency();

                companyCalculate.getDepartments().forEach(d -> d.getEmployees().forEach(e -> employees.add(e)));
                break;
            case 2:
                int departmentId = Integer.parseInt(request.getParameter("department_id"));
                DepartmentCalculate departmentCalculate = new DepartmentCalculate( new DepartmentDAO().findById(departmentId));
                departmentCalculate.calculateEfficienty();
                result = mapper.writeValueAsString(departmentCalculate);
                ver = "по подразделению: "+ departmentCalculate.getDepartment().getDepartmentName();
                efficiency = departmentCalculate.getEfficiency();
                departmentCalculate.getEmployees().forEach(e -> employees.add(e));
                break;
            default:
                int employeeId = Integer.parseInt(request.getParameter("employee_id"));
                EmployeeCalculate employeeCalculate = new EmployeeCalculate( new EmployeeDAO().findById(employeeId));
                employeeCalculate.calculateEfficienty();
                result = mapper.writeValueAsString(employeeCalculate);
                ver = "по работнику: "+ employeeCalculate.getEmployee().getLastName() + " " + employeeCalculate.getEmployee().getFirstName();
                efficiency = employeeCalculate.getEfficiency();
                employees.add(employeeCalculate);
                break;
        }

        request.setAttribute("efficiency",String.format("%.2f",efficiency));
        request.setAttribute("result",result);
        request.setAttribute("ver",ver);
        request.setAttribute("employees",employees);
        request.setAttribute("time_start", Constant.TIME_WORK_START);
        request.setAttribute("time_long", Constant.TIME_WORK_LONG + 1);

        request.getRequestDispatcher("./calculate/calculate_result.jsp").forward(request,response);
    }
}
