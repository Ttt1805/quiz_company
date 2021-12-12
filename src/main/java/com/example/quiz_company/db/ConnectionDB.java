package com.example.quiz_company.db;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionDB {
    private volatile static ConnectionDB instance;
    private Connection connection;

    private ConnectionDB(){
        try {
            InitialContext cxt = new InitialContext();
            DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/quizcompany");
            if (ds == null) {
                throw new IllegalArgumentException("Data source not found");
            } else {
                connection = ds.getConnection();
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionDB getInstance() {
        ConnectionDB localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionDB.class) {
                if (localInstance == null) {
                    instance = localInstance = new ConnectionDB();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
