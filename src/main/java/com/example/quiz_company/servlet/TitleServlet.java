package com.example.quiz_company.servlet;

import com.example.quiz_company.dao.TitleDAO;
import com.example.quiz_company.valObject.Title;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "TitleServlet", value = {"/title-servlet", "/title-edit", "/title-remove"})
public class TitleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/title-servlet")) {
            List<Title> titles = new TitleDAO().findAll();
            request.setAttribute("titles", titles);
            request.getRequestDispatcher("./title/title.jsp").forward(request, response);
        } else if (requestURI.contains("/title-edit")) {
            String id = request.getParameter("id");
            Title title = new Title();
            if (id != null) {
                title = new TitleDAO().findById(Integer.parseInt(id));
            }
            request.setAttribute("title", title);
            request.getRequestDispatcher("./title/title_edit.jsp").forward(request, response);
        } else if (requestURI.contains("/title-remove")) {
            String id = request.getParameter("id");
            if (id != null) {
                new TitleDAO().deleteById(Integer.parseInt(id));
            }
            response.sendRedirect( "./title-servlet");
        } else {
            response.sendRedirect(".");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titleName = request.getParameter("title_name");
        int titleId = Integer.parseInt(request.getParameter("title_id"));
        String[] checkbox = request.getParameterValues("free_schedule");
        boolean free_schedule = checkbox.length > 0;
        if (titleName != null && !titleName.isEmpty()) {
            Title title = new Title(titleId, titleName, free_schedule);
            if (titleId == 0) {
                new TitleDAO().addTitle(title);
            } else {
                new TitleDAO().editTitle(title);
            }
        }
        response.sendRedirect( "./title-servlet");

    }
}
