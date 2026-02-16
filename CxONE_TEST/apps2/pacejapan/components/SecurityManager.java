package com.pacejapan.components;

import java.security.*;
import javax.crypto.*;

public class SecurityManager {
    
    // VULNERABILITY 5: Hardcoded Credentials
    private static final String DB_PASSWORD = "admin123";
    private static final String API_KEY = "sk_live_51HqK2jKl3m4n5o6p7q8r9s0";
    
    public Connection getDatabaseConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/pacejapan";
        String username = "root";
        return DriverManager.getConnection(url, username, DB_PASSWORD);
    }
    
    // VULNERABILITY 6: Weak Cryptographic Algorithm (MD5)
    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
    
    // VULNERABILITY 7: Insecure Random Number Generation
    public String generateToken() {
        java.util.Random random = new java.util.Random();
        return String.valueOf(random.nextLong());
    }
}
