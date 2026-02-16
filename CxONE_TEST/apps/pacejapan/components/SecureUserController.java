package com.pacejapan.components;

import java.sql.*;
import javax.servlet.http.*;

public class SecureUserController {
    
    // SECURE: Using Prepared Statements to prevent SQL Injection
    public User getUserById(HttpServletRequest request, Connection conn) throws SQLException {
        String userId = request.getParameter("id");
        
        // Input validation
        if (userId == null || !userId.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        String query = "SELECT * FROM users WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, userId);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            return new User(rs.getString("username"), rs.getString("email"));
        }
        return null;
    }
    
    // SECURE: Input validation and sanitization
    public String getLogData(String logType) throws Exception {
        // Whitelist approach
        if (!logType.matches("^[a-zA-Z0-9_-]+$")) {
            throw new IllegalArgumentException("Invalid log type");
        }
        
        // Use safe file operations instead of command execution
        Path logPath = Paths.get("/var/log/app/" + logType + ".log");
        return Files.readString(logPath);
    }
}
