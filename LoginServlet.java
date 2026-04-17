package com.eldercare;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/eldercare", "root", ""
            );

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM user WHERE username=? AND password=?"
            );

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                // ✅ SESSION SET
                HttpSession session = request.getSession();
                session.setAttribute("username", rs.getString("username"));
                session.setAttribute("email", rs.getString("email"));

                response.sendRedirect("dashboard.jsp");

            } else {
                response.sendRedirect("login.jsp?error=Invalid Credentials");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}