package com.eldercare;

import com.eldercare.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

@WebServlet("/MedicineServlet")
public class MedicineServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {

            HttpSession session = req.getSession(false);

            if (session == null || session.getAttribute("user_id") == null) {
                System.out.println("SESSION LOST → REDIRECT LOGIN");
                res.sendRedirect("login.jsp");
                return;
            }

            int userId = (Integer) session.getAttribute("user_id");

            String name = req.getParameter("name");
            String dosage = req.getParameter("dosage");

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO medicine(user_id, name, dosage) VALUES(?,?,?)"
            );

            ps.setInt(1, userId);
            ps.setString(2, name);
            ps.setString(3, dosage);

            int result = ps.executeUpdate();

            System.out.println("INSERT RESULT = " + result);

            ps.close();
            con.close();

            res.sendRedirect("medicine.jsp");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}