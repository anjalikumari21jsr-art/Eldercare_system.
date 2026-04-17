package com.eldercare;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

import com.eldercare.util.EmailUtil;

@WebServlet("/ScheduleServlet")
public class ScheduleServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String medicine = req.getParameter("medicine");
        String time = req.getParameter("time");
        String email = req.getParameter("email");

        // 👉 Call Email
        EmailUtil.sendEmail(email, medicine, time);

        req.setAttribute("msg", "Reminder saved & Email sent!");
        req.getRequestDispatcher("schedule.jsp").forward(req, res);
    }
}