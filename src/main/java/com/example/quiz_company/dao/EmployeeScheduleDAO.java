package com.example.quiz_company.dao;

import com.example.quiz_company.db.ConnectionDB;
import com.example.quiz_company.enumer.ScheduleType;
import com.example.quiz_company.valObject.Employee;
import com.example.quiz_company.valObject.EmployeeSchedule;

import java.sql.*;

public class EmployeeScheduleDAO {
    public EmployeeSchedule findEmployeeSchedule(int employeeId, int scheduleId){
        EmployeeSchedule employeeSchedule = new EmployeeSchedule(ScheduleType.getType(scheduleId));
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM employee_schedule WHERE employeeid = ?");
            statement.setInt(1, employeeId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int numberHours = rs.getInt("numberhours");
                employeeSchedule.setScheduleOffset(numberHours);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeSchedule;
    }

    public void change(Employee employee) {
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT count(*) as numrows FROM employee_schedule WHERE employeeid = ?");
            statement.setInt(1, employee.getEmployeeId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int numrows = rs.getInt("numrows");
                if (numrows > 0) {
                    updateEmployeeSchedule(employee);
                 } else {
                    addEmployeeSchedule(employee);
                }
            } else {
                addEmployeeSchedule(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addEmployeeSchedule(Employee employee) {
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO employee_schedule(employeeid, scheduleid, numberhours) VALUES (?, ?, ?)");
            statement.setInt(1, employee.getEmployeeId());
            statement.setInt(2, employee.getEmployeeSchedule().getTypeSchedule().getIndex());
            statement.setInt(3, employee.getEmployeeSchedule().getScheduleOffset());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployeeSchedule(Employee employee) {
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE employee_schedule SET scheduleid = ?, numberhours = ? WHERE employeeid = ?");
            statement.setInt(1, employee.getEmployeeSchedule().getTypeSchedule().getIndex());
            statement.setInt(2, employee.getEmployeeSchedule().getScheduleOffset());
            statement.setInt(3, employee.getEmployeeId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
