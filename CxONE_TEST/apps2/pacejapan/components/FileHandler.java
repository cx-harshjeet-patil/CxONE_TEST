package com.pacejapan.components;

import javax.servlet.http.*;
import java.io.*;

public class FileHandler {
    
    // VULNERABILITY 3: Path Traversal
    public String readFile(HttpServletRequest request) throws IOException {
        String filename = request.getParameter("file");
        File file = new File("/app/data/" + filename);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        reader.close();
        return content.toString();
    }
    
    // VULNERABILITY 4: Cross-Site Scripting (XSS)
    public void displayUserComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String comment = request.getParameter("comment");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Your comment:</h2>");
        out.println("<p>" + comment + "</p>");
        out.println("</body></html>");
    }
}
