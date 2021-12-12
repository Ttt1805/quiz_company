package com.example.quiz_company.servlet;

import com.example.quiz_company.dao.CompanyDAO;
import com.example.quiz_company.dao.DepartmentDAO;
import com.example.quiz_company.dao.EmployeeDAO;
import com.example.quiz_company.dao.TitleDAO;
import com.example.quiz_company.enumer.SalaryType;
import com.example.quiz_company.enumer.ScheduleType;
import com.example.quiz_company.valObject.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "EmployeeServlet", value = {"/employee-servlet", "/employee-edit", "/employee-remove"})
public class EmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/employee-servlet")) {
            List<Employee> employees = new EmployeeDAO().findAll();
            request.setAttribute("employees", employees);
            request.setAttribute("time_start", Constant.TIME_WORK_START);
            request.getRequestDispatcher("./employee/employee.jsp").forward(request, response);
        } else if (requestURI.contains("/employee-edit")) {
            String id = request.getParameter("id");
            Employee employee = new Employee();
            if (id != null) {
                employee = new EmployeeDAO().findById(Integer.parseInt(id));
            } else {
                employee.setEmployeeSalary(new EmployeeSalary());
                employee.setEmployeeSchedule(new EmployeeSchedule());
                employee.setDepartment(new Department());
            }
            request.setAttribute("employee", employee);

            List<Department> departments = new DepartmentDAO().findAll();
            request.setAttribute("departments", departments);

            List<Title> titles = new TitleDAO().findAll();
            request.setAttribute("titles", titles);

            ScheduleType[] schedules = ScheduleType.values();
            request.setAttribute("schedules", schedules);

            SalaryType[] salaries = SalaryType.values();
            request.setAttribute("salaries", salaries);

            int time1 = employee.getEmployeeSchedule() == null ? Constant.TIME_WORK_START : Constant.TIME_WORK_START + employee.getEmployeeSchedule().getScheduleOffset();
            request.setAttribute("time1", time1);
            request.setAttribute("time2", Constant.TIME_WORK_LONG);
            request.getRequestDispatcher("./employee/empl_edit.jsp").forward(request, response);
       } else if (requestURI.contains("/employee-remove")) {
            String id = request.getParameter("id");
            if (id != null) {
                new EmployeeDAO().deleteById(Integer.parseInt(id));
            }
            response.sendRedirect( "./employee-servlet");
        } else {
            response.sendRedirect("index.jsp");
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lastName = request.getParameter("last_name");
        String firstName = request.getParameter("first_name");
        int employeeID = Integer.parseInt(request.getParameter("employee_id"));
        int departmentId = Integer.parseInt(request.getParameter("department_id"));
        Department department = new DepartmentDAO().findById(departmentId);
        int titleId = Integer.parseInt(request.getParameter("title_id"));
        Title title = new TitleDAO().findById(titleId);
        if (employeeID != 0) {
            String sc = request.getParameter("type_schedule");
            int type_schedule = Integer.parseInt(request.getParameter("type_schedule"));
            ScheduleType scheduleType = ScheduleType.getType(type_schedule);
            int time_start = Integer.parseInt(request.getParameter("time_start"));
            if (scheduleType == ScheduleType.STANDART || scheduleType == ScheduleType.HOME) {
                time_start = Constant.TIME_WORK_START;
            } else {
                scheduleType = time_start > Constant.TIME_WORK_START ? ScheduleType.AFTER : time_start < Constant.TIME_WORK_START ? ScheduleType.BEFORE : ScheduleType.STANDART;
            }
            EmployeeSchedule employeeSchedule = new EmployeeSchedule(scheduleType, time_start - Constant.TIME_WORK_START);
            int type_salary = Integer.parseInt(request.getParameter("type_salary"));
            SalaryType salaryType = SalaryType.getType(type_salary);
            int salary = Integer.parseInt(request.getParameter("salary"));
            int percent = Integer.parseInt(request.getParameter("percent"));
            if (salaryType != SalaryType.SALARY_AND_PERCENT) {
                percent = 0;
            }
            String text_data = request.getParameter("sdate");
            LocalDate sdata;
            if (text_data.isEmpty()) {
                sdata = LocalDate.now();
            } else {
                sdata = LocalDate.parse(text_data);
            }
            EmployeeSalary employeeSalary = new EmployeeSalary(salaryType, salary, percent, sdata);
            Employee employee = new Employee(employeeID, firstName, lastName, department, title, employeeSchedule, employeeSalary);
            new EmployeeDAO().editEmployee(employee);
        } else {
            Employee employee = new Employee(employeeID, firstName, lastName, department, title, new EmployeeSchedule(), new EmployeeSalary());
            new EmployeeDAO().addEmployee(employee);
        }
        response.sendRedirect( "./employee-servlet");
    }
}
