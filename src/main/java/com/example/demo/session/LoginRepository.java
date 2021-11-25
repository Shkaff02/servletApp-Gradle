package com.example.demo.session;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;

public class LoginRepository {
    public static void main(String[] args) throws SQLException {

        getConnection();
        System.out.println(check("Kostyan", "2002"));
    }

    public static Connection getConnection(){
        Connection connection = null;
        String url = "jdbc:mysql://localhost:8889/employee";
        String user = "root";
        String password = "root";

        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connected to the MySQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return connection;
    }

    public static ResultSet getLoginInfo(){
        ResultSet rs = null;
        try{
            Connection connection = LoginRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT login.Login, login.Password FROM login");
            rs = ps.executeQuery();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return rs;
    }

    public static boolean check(String login, String password) throws SQLException {
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
