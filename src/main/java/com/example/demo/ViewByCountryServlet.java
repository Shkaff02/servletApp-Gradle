package com.example.demo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/viewByCountryServlet")
public class ViewByCountryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String country = request.getParameter("country");
        try {
            out.print(EmployeeRepository.getByCountry(country));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.close();
    }

}
