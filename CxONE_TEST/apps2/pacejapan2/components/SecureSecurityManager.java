package com.pacejapan.components;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.Properties;
import java.io.FileInputStream;

public class SecureSecurityManager {
    
    private Properties config;
    
    public SecureSecurityManager() throws Exception {
        // SECURE: Load credentials from external configuration
        config = new Properties();
        config.load(new FileInputStream("/etc/app/config.properties"));
    }
    
    public Connection getDatabaseConnection() throws Exception {
        String url = config.getProperty("db.url");
        String username = config.getProperty("db.username");
        String password = config.getProperty("db.password");
        return DriverManager.getConnection(url, username, password);
    }
    
    // SECURE: Strong cryptographic algorithm (SHA-256 with salt)
    public String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hash = md.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
    
    // SECURE: Cryptographically secure random number generation
    public String generateToken() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return java.util.Base64.getEncoder().encodeToString(randomBytes);
    }
}
