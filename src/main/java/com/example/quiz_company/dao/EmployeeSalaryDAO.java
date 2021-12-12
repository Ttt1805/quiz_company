package com.example.quiz_company.dao;

import com.example.quiz_company.db.ConnectionDB;
import com.example.quiz_company.enumer.SalaryType;
import com.example.quiz_company.valObject.Employee;
import com.example.quiz_company.valObject.EmployeeSalary;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeSalaryDAO {
    public EmployeeSalary findEmployeeSalary(int employeeId){
        EmployeeSalary employeeSalary = new EmployeeSalary();
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM employee_salary WHERE employeeid = ? ORDER BY sdate desc LIMIT 1");
            statement.setInt(1, employeeId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int salaryId = rs.getInt("salaryid");
                int salary = rs.getInt("salary");
                int percent = rs.getInt("percent");
                String data_text = rs.getString("sdate");
                LocalDate sDate = LocalDate.parse(data_text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                employeeSalary = new EmployeeSalary(SalaryType.getType(salaryId), salary, percent,sDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeSalary;
    }

    public void change(Employee employee) {
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT count(*) as numrows FROM employee_salary WHERE employeeid = ? and sdate = ?");
            statement.setInt(1, employee.getEmployeeId());
            statement.setDate(2, Date.valueOf(employee.getEmployeeSalary().getSalary_day()));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int numrows = rs.getInt("numrows");
                if (numrows == 0) {
                    addEmployeeSalary(employee);
                } else {
                    updateEmployeeSalary(employee);
                }
            } else {
                addEmployeeSalary(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addEmployeeSalary(Employee employee) {
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO employee_salary(employeeid, salaryid, salary, percent, sdate) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, employee.getEmployeeId());
            statement.setInt(2, employee.getEmployeeSalary().getSalaryType().getIndex());
            statement.setInt(3, employee.getEmployeeSalary().getSalary());
            statement.setInt(4, employee.getEmployeeSalary().getPercent());
            statement.setDate(5, Date.valueOf(employee.getEmployeeSalary().getSalary_day()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployeeSalary(Employee employee) {
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE employee_salary SET salaryid = ?, salary = ?, percent = ?, sdate = ? WHERE employeeid = ?");
            statement.setInt(1, employee.getEmployeeSalary().getSalaryType().getIndex());
            statement.setInt(2, employee.getEmployeeSalary().getSalary());
            statement.setInt(3, employee.getEmployeeSalary().getPercent());
            statement.setDate(4, Date.valueOf(employee.getEmployeeSalary().getSalary_day()));
            statement.setInt(5, employee.getEmployeeId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
