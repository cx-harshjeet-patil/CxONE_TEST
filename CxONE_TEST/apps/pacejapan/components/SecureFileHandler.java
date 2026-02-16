package com.pacejapan.components;

import javax.servlet.http.*;
import java.io.*;
import java.nio.file.*;
import org.owasp.encoder.Encode;

public class SecureFileHandler {
    
    private static final String BASE_DIR = "/app/data/";
    
    // SECURE: Path traversal prevention
    public String readFile(HttpServletRequest request) throws IOException {
        String filename = request.getParameter("file");
        
        // Validate filename
        if (filename == null || filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
            throw new SecurityException("Invalid filename");
        }
        
        Path basePath = Paths.get(BASE_DIR).toRealPath();
        Path filePath = basePath.resolve(filename).normalize();
        
        // Ensure the resolved path is still within base directory
        if (!filePath.startsWith(basePath)) {
            throw new SecurityException("Access denied");
        }
        
        return Files.readString(filePath);
    }
    
    // SECURE: XSS prevention with output encoding
    public void displayUserComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String comment = request.getParameter("comment");
        
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Your comment:</h2>");
        out.println("<p>" + Encode.forHtml(comment) + "</p>");
        out.println("</body></html>");
    }
}
