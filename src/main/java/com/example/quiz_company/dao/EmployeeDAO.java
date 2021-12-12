package com.example.quiz_company.dao;

import com.example.quiz_company.calculate.EmployeeCalculate;
import com.example.quiz_company.db.ConnectionDB;
import com.example.quiz_company.valObject.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT e.*, es.scheduleid FROM employee as e LEFT JOIN employee_schedule as es on e.employeeid = es.employeeid ORDER BY lastname");
            while (rs.next()) {
                int departmentId = rs.getInt("departmentid");
                int scheduleId = rs.getInt("scheduleid");
                int employeeId = rs.getInt("employeeid");
                int titleId = rs.getInt("titleid");
                String lastName = rs.getString("lastname");
                String firstName = rs.getString("firstname");
                employees.add(new Employee(employeeId,
                        firstName, lastName,
                        new DepartmentDAO().findById(departmentId),
                        new TitleDAO().findById(titleId),
                        new EmployeeScheduleDAO().findEmployeeSchedule(employeeId, scheduleId),
                        new EmployeeSalaryDAO().findEmployeeSalary(employeeId)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public Employee findById(int id) {
        Employee employee = new Employee();
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
             PreparedStatement st = conn.prepareStatement("SELECT e.*, es.scheduleid FROM employee as e LEFT JOIN employee_schedule as es on e.employeeid = es.employeeid WHERE e.employeeid = ?");
             st.setInt(1, id);
             ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int departmentId = rs.getInt("departmentid");
                int scheduleId = rs.getInt("scheduleid");
                int employeeId = rs.getInt("employeeid");
                int titleId = rs.getInt("titleid");
                String lastName = rs.getString("lastname");
                String firstName = rs.getString("firstname");
                employee = new Employee(employeeId,
                        firstName, lastName,
                        new DepartmentDAO().findById(departmentId),
                        new TitleDAO().findById(titleId),
                        new EmployeeScheduleDAO().findEmployeeSchedule(employeeId, scheduleId),
                        new EmployeeSalaryDAO().findEmployeeSalary(employeeId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public void deleteById(int employeeId) {
        if (0 < employeeId) {
            Connection conn = ConnectionDB.getInstance().getConnection();
            try {
                PreparedStatement st = conn.prepareStatement("DELETE FROM employee WHERE employeeId = ?");
                st.setInt(1, employeeId);
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addEmployee(Employee employee) {
        if (!employee.getLastName().isEmpty() || !employee.getFirstName().isEmpty()) {
            Connection conn = ConnectionDB.getInstance().getConnection();
            try {
                PreparedStatement insert = conn.prepareStatement("INSERT INTO employee(lastname, firstname, departmentid, titleid) VALUES (?,?,?,?)");
                insert.setString(1, employee.getLastName());
                insert.setString(2, employee.getFirstName());
                insert.setInt(3, employee.getDepartment().getDepartmentId());
                insert.setInt(4, employee.getTitle().getTitleId());
                int rowsAdd = insert.executeUpdate();
                if (employee.getEmployeeId() != 0) {
                    if (rowsAdd > 0) {
                        new EmployeeScheduleDAO().change(employee);
                        new EmployeeSalaryDAO().change(employee);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void editEmployee(Employee employee) {
        if (!employee.getLastName().isEmpty() || !employee.getFirstName().isEmpty()) {
            Connection conn = ConnectionDB.getInstance().getConnection();
            try {
                PreparedStatement insert = conn.prepareStatement("UPDATE employee SET lastname = ?, firstname = ?, departmentid = ?, titleid = ? WHERE employeeid = ?");
                insert.setString(1, employee.getLastName());
                insert.setString(2, employee.getFirstName());
                insert.setInt(3, employee.getDepartment().getDepartmentId());
                insert.setInt(4, employee.getTitle().getTitleId());
                insert.setInt(5, employee.getEmployeeId());
                int rowsAdd = insert.executeUpdate();
                if  (rowsAdd > 0) {
                    new EmployeeScheduleDAO().change(employee);
                    new EmployeeSalaryDAO().change(employee);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<EmployeeCalculate> getDepEmployees(Department department) {
        List<EmployeeCalculate> employees = new ArrayList<>();
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT e.*, es.scheduleid FROM employee as e LEFT JOIN employee_schedule as es on e.employeeid = es.employeeid WHERE e.departmentid = ? ORDER BY lastname");
            statement.setInt(1, department.getDepartmentId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int scheduleId = rs.getInt("scheduleid");
                int employeeId = rs.getInt("employeeid");
                int titleId = rs.getInt("titleid");
                String lastName = rs.getString("lastname");
                String firstName = rs.getString("firstname");
                employees.add(new EmployeeCalculate(new Employee(employeeId,
                        firstName, lastName,
                        department,
                        new TitleDAO().findById(titleId),
                        new EmployeeScheduleDAO().findEmployeeSchedule(employeeId, scheduleId),
                        new EmployeeSalaryDAO().findEmployeeSalary(employeeId))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

}
