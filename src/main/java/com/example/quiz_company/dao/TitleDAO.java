package com.example.quiz_company.dao;

import com.example.quiz_company.db.ConnectionDB;
import com.example.quiz_company.valObject.Title;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TitleDAO {
    public List<Title> findAll() {
        List<Title> titles = new ArrayList<>();
        Connection conn = ConnectionDB.getInstance().getConnection();
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM titles ORDER BY name");
            while (rs.next()) {
                int id = rs.getInt("titleid");
                String name = rs.getString("name");
                Boolean freeSchedule = rs.getBoolean("free_schedule");
                titles.add(new Title(id, name, freeSchedule));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return titles;
    }

    public void addTitle(Title title) {
        if (!title.getTitleName().isEmpty()) {
            Connection conn = ConnectionDB.getInstance().getConnection();
            try {
                PreparedStatement insert = conn.prepareStatement("insert into titles(name, free_schedule) values(?, ?)");
                insert.setString(1, title.getTitleName());
                insert.setBoolean(2, title.isFreeSchedule());
                insert.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Title findById(int titleId) {
        Connection conn = ConnectionDB.getInstance().getConnection();
        Title title = new Title();
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM titles WHERE titleid=?");
            st.setInt(1, titleId);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("titleid");
                String name = resultSet.getString("name");
                Boolean freeSchedule = resultSet.getBoolean("free_schedule");
                title = new Title(id, name, freeSchedule);
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return title;

    }

    public void editTitle(Title title) {
        if (0 < title.getTitleId()) {
            Connection conn = ConnectionDB.getInstance().getConnection();
            try {
                PreparedStatement st = conn.prepareStatement("UPDATE titles SET name=?, free_schedule = ? WHERE titleid=?");
                st.setString(1, title.getTitleName());
                st.setBoolean(2, title.isFreeSchedule());
                st.setInt(3, title.getTitleId());
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void deleteById(int titleId) {
        if (0 < titleId) {
            Connection conn = ConnectionDB.getInstance().getConnection();
            try {
                PreparedStatement st = conn.prepareStatement("DELETE FROM titles WHERE titleid=?");
                st.setInt(1, titleId);
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
