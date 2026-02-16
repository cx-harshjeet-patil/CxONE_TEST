package com.pacejapan.components;

import java.sql.*;
import javax.servlet.http.*;

public class UserController {
    
    // VULNERABILITY 1: SQL Injection
    public User getUserById(HttpServletRequest request, Connection conn) throws SQLException {
        String userId = request.getParameter("id");
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM users WHERE id = '" + userId + "'";
        ResultSet rs = stmt.executeQuery(query);
        
        if (rs.next()) {
            return new User(rs.getString("username"), rs.getString("email"));
        }
        return null;
    }
    
    // VULNERABILITY 2: Command Injection
    public String executeSystemCommand(String filename) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("cat /var/log/" + filename);
        return new String(process.getInputStream().readAllBytes());
    }
}
