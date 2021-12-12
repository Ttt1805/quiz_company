package com.example.quiz_company.dao;

import com.example.quiz_company.calculate.DepartmentCalculate;
import com.example.quiz_company.db.ConnectionDB;
import com.example.quiz_company.valObject.Company;
import com.example.quiz_company.valObject.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT  d.departmentid as departmentid, " +
                    "d.name as departmentname, " +
                    "d.free_schedule as free_schedule, " +
                    "d.sync_schedule as sync_schedule, " +
                    "c.companyid as companyid, " +
                    "COALESCE(s.numberhours,0) as counthours, " +
                    "c.name as companyname " +
                    "FROM departments as d " +
                    "LEFT JOIN company c ON " +
                    "d.companyid = c.companyid " +
                    "LEFT JOIN departments_schedule s ON " +
                    "d.departmentid = s.departmentid ORDER BY d.name");
            while (rs.next()) {
                int departmentId = rs.getInt("departmentid");
                String departmentName = rs.getString("departmentname");
                int companyId = rs.getInt("companyid");
                String companyName = rs.getString("companyname");
                boolean free_schedule = rs.getBoolean("free_schedule");
                boolean sync_schedule = rs.getBoolean("sync_schedule");
                int countHours = rs.getInt("counthours");
                departments.add(new Department(departmentId,
                                                departmentName,
                                                new Company(companyId, companyName),
                                                free_schedule,
                                                sync_schedule, countHours));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    public Department findById(int departId) {
        Connection conn = ConnectionDB.getInstance().getConnection();
        Department department = new Department();
        try {
            PreparedStatement st = conn.prepareStatement("SELECT  d.departmentid as departmentid, " +
                    "d.name as departmentname, " +
                    "d.free_schedule as free_schedule, " +
                    "d.sync_schedule as sync_schedule, " +
                    "c.companyid as companyid, " +
                    "COALESCE(s.numberhours,0) as counthours, " +
                    "c.name as companyname " +
                    "FROM departments as d " +
                    "LEFT JOIN company c ON " +
                    "d.companyid = c.companyid " +
                    "LEFT JOIN departments_schedule s ON " +
                    "d.departmentid = s.departmentid " +
                    "WHERE d.departmentid = ?");
            st.setInt(1, departId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int departmentId = rs.getInt("departmentid");
                String departmentName = rs.getString("departmentname");
                int companyId = rs.getInt("companyid");
                String companyName = rs.getString("companyname");
                boolean free_schedule = rs.getBoolean("free_schedule");
                boolean sync_schedule = rs.getBoolean("sync_schedule");
                int countHours = rs.getInt("counthours");
                department = new Department(departmentId,
                        departmentName,
                        new Company(companyId, companyName),
                        free_schedule,
                        sync_schedule,countHours);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return department;
    }

    public void addDepartment(Department department) {
        if (!department.getDepartmentName().isEmpty()) {
            Connection conn = ConnectionDB.getInstance().getConnection();
            try {
                PreparedStatement insert = conn.prepareStatement("INSERT INTO departments(name, free_schedule,sync_schedule, companyid) VALUES (?,?,?,?)");
                insert.setString(1, department.getDepartmentName());
                insert.setBoolean(2, department.isFreeSchedule());
                insert.setBoolean(3, department.isSyncSchedule());
                insert.setInt(4, department.getCompany().getCompanyId());
                int rowsAdd = insert.executeUpdate();
                if (department.isSyncSchedule()) {
                    if  (rowsAdd > 0) {
                        insert = conn.prepareStatement("INSERT INTO departments_schedule(departmentid, scheduleid, numberhours) VALUES (?, ?, ?)");
                        insert.setInt(1,department.getDepartmentId());
                        int schedule = Integer.compare(department.getScheduleOffset(), 0);
                        insert.setInt(2, schedule);
                        insert.setInt(3,department.getScheduleOffset());
                        insert.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void editDepartment(Department department) {
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE departments SET name = ?, free_schedule = ?, sync_schedule = ?, companyid = ? " +
                                                                 " WHERE departmentid = ?");
            st.setString(1, department.getDepartmentName());
            st.setBoolean(2, department.isFreeSchedule());
            st.setBoolean(3, department.isSyncSchedule());
            st.setInt(4, department.getCompany().getCompanyId());
            st.setInt(5, department.getDepartmentId());
            int rowsUpd = st.executeUpdate();
            if (department.isSyncSchedule()) {
                if  (rowsUpd > 0) {
                    st = conn.prepareStatement("SELECT count(*) FROM departments_schedule WHERE departmentid = ?");
                    st.setInt(1,department.getDepartmentId());
                    ResultSet rs = st.executeQuery();
                    int rows = 0;
                    if (rs.next()) {
                        rows = rs.getInt(1);
                    }
                    if (rows == 0) {
                        st = conn.prepareStatement("INSERT INTO departments_schedule(departmentid, scheduleid, numberhours) VALUES (?, ?, ?)");
                    } else {
                        st = conn.prepareStatement("UPDATE departments_schedule SET departmentid = ?, scheduleid = ?, numberhours = ?  WHERE departmentid = ?");
                        st.setInt(4, department.getDepartmentId());
                    }
                    st.setInt(1,department.getDepartmentId());
                    int schedule = Integer.compare(department.getScheduleOffset(), 0);
                    st.setInt(2, schedule);
                    st.setInt(3,department.getScheduleOffset());
                    st.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int departmentId) {
        if (0 < departmentId) {
            Connection conn = ConnectionDB.getInstance().getConnection();
            try {
                PreparedStatement st = conn.prepareStatement("DELETE FROM departments WHERE departmentId = ?");
                st.setInt(1, departmentId);
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public List<DepartmentCalculate> findCompanyDepartments(Company company) {
        List<DepartmentCalculate> departments = new ArrayList<>();
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {

            PreparedStatement st = conn.prepareStatement("SELECT  d.departmentid as departmentid, " +
                    "d.name as departmentname, " +
                    "d.free_schedule as free_schedule, " +
                    "d.sync_schedule as sync_schedule, " +
                    "c.companyid as companyid, " +
                    "COALESCE(s.numberhours,0) as counthours, " +
                    "c.name as companyname " +
                    "FROM departments as d " +
                    "LEFT JOIN company c ON " +
                    "d.companyid = c.companyid " +
                    "LEFT JOIN departments_schedule s ON " +
                    "d.departmentid = s.departmentid " +
                    "WHERE d.companyid = ? ORDER BY d.name");
            st.setInt(1,  company.getCompanyId());
            ResultSet rs =st.executeQuery();
            while (rs.next()) {
                int departmentId = rs.getInt("departmentid");
                String departmentName = rs.getString("departmentname");
                boolean free_schedule = rs.getBoolean("free_schedule");
                boolean sync_schedule = rs.getBoolean("sync_schedule");
                int countHours = rs.getInt("counthours");
                departments.add(new DepartmentCalculate(new Department(departmentId,
                        departmentName,
                        company,
                        free_schedule,
                        sync_schedule, countHours)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

}
