package com.mark.javaweb.controller;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT role FROM users WHERE username=? AND password=?"
            );
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");

                if ("admin".equalsIgnoreCase(role)) {
                    response.sendRedirect("/Admin/Admin.html");
                } else if ("doctor".equalsIgnoreCase(role)) {
                    response.sendRedirect("/Doctor/DoctorHome.html");
                } else if ("patient".equalsIgnoreCase(role)) {
                    response.sendRedirect("/Patient/PatientHome.html");
                } else {
                    response.getWriter().println("Unknown role: " + role);
                }
            } else {
                response.getWriter().println("Invalid login!");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
