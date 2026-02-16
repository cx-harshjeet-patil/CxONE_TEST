package com.pacejapan.components;

import java.io.*;
import javax.servlet.http.*;

public class SessionManager {
    
    // VULNERABILITY 10: Insecure Deserialization
    public Object deserializeUserSession(HttpServletRequest request) throws Exception {
        String sessionData = request.getParameter("session");
        byte[] data = java.util.Base64.getDecoder().decode(sessionData);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        return ois.readObject();
    }
    
    public void saveUserPreferences(Object preferences) throws IOException {
        FileOutputStream fos = new FileOutputStream("/tmp/user_prefs.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(preferences);
        oos.close();
    }
}
