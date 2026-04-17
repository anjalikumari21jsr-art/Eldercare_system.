package com.eldercare;

import com.eldercare.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

@WebServlet("/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {

            HttpSession session = req.getSession(false);

            if (session == null || session.getAttribute("user_id") == null) {
                res.sendRedirect("login.jsp");
                return;
            }

            int userId = (Integer) session.getAttribute("user_id");

            String item = req.getParameter("item");
            String image = req.getParameter("image");

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO purchase(user_id,item,image) VALUES(?,?,?)"
            );

            ps.setInt(1, userId);
            ps.setString(2, item);
            ps.setString(3, image);

            ps.executeUpdate();

            ps.close();
            con.close();

            res.sendRedirect("medicine.jsp");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}