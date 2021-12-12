package com.example.quiz_company.servlet;

import com.example.quiz_company.dao.CompanyDAO;
import com.example.quiz_company.db.ConnectionDB;
import com.example.quiz_company.valObject.Company;

import java.io.*;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "mServlet", value = "/")
public class mServlet extends HttpServlet {
    private String message;
    private Connection connection;

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
             request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void destroy() {
    }
}