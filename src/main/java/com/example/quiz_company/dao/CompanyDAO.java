package com.example.quiz_company.dao;

import com.example.quiz_company.db.ConnectionDB;
import com.example.quiz_company.valObject.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAO {
    public List<Company> findAll() {
        List<Company> companies = new ArrayList<>();
        Connection conn = ConnectionDB.getInstance().getConnection();
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT companyid, name FROM company ORDER BY name");
            while (rs.next()) {
                int id = rs.getInt("companyid");
                String name = rs.getString("name");
                companies.add(new Company(id,name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    public void addCompany(Company company) {
        if (!company.getName().isEmpty()) {
            Connection conn = ConnectionDB.getInstance().getConnection();
            try {
                PreparedStatement insert = conn.prepareStatement("insert into company(name) values(?)");
                insert.setString(1, company.getName());
                insert.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Company findById(int companyId) {
        Connection conn = ConnectionDB.getInstance().getConnection();
        Company company = new Company(0,"");
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM company WHERE companyid=?");
            st.setInt(1, companyId);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("companyid");
                String name =resultSet.getString("name");
                company.setCompanyId(id);
                company.setName(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return company;

    }

    public void editCompany(Company company) {
        if (0 < company.getCompanyId()) {
            Connection conn = ConnectionDB.getInstance().getConnection();
            try {
                PreparedStatement st = conn.prepareStatement("UPDATE company SET name=? WHERE companyid=?");
                st.setString(1, company.getName());
                st.setInt(2, company.getCompanyId());
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void deleteById(int companyId) {
        if (0 < companyId) {
            Connection conn = ConnectionDB.getInstance().getConnection();
            try {
                PreparedStatement st = conn.prepareStatement("DELETE FROM company WHERE companyid=?");
                st.setInt(1, companyId);
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
