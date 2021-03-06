package com.example.demo.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Это название 2-х параметров, которые мы передаем
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");


        try {
            if (check(user, pwd)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", "user");
                //setting session to expiry in 30 mins
                session.setMaxInactiveInterval(30 * 60);
                Cookie userName = new Cookie("user", user);
                userName.setMaxAge(30 * 60);
                response.addCookie(userName);
                PrintWriter out = response.getWriter();
                out.println("Welcome back to the team, " + user + "!");
            } else {
                PrintWriter out = response.getWriter();
                out.println("Either user name or password is wrong!");
                log("Something wrong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean check(String login, String password) throws SQLException {
        ResultSet rs = LoginRepository.getLoginInfo();
        HashMap<String, String> map = new HashMap<>();

        while (rs.next()){
            map.put(rs.getString("Login"), rs.getString("Password"));
        }
        if (map.containsKey(login) && map.get(login).equals(password)){
            return true;
        }else {
            return false;
        }
    }
}
