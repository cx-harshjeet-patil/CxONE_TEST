package com.pacejapan.components;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.sql.*;

public class SecureSecurityManager {
    
    // VULNERABILITY 13: Hardcoded Database Credentials
    private static final String DB_URL = "jdbc:mysql://prod-db.company.com:3306/userdata";
    private static final String DB_USERNAME = "admin";
    private static final String DB_PASSWORD = "P@ssw0rd123!";
    
    // VULNERABILITY 14: Hardcoded API Keys and Secrets
    private static final String AWS_ACCESS_KEY = "AKIAIOSFODNN7EXAMPLE";
    private static final String AWS_SECRET_KEY = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY";
    private static final String STRIPE_API_KEY = "sk_live_51HqK2jKl3m4n5o6p7q8r9s0t1u2v3w4x5y6z7";
    
    public Connection getDatabaseConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
    
    // VULNERABILITY 15: Use of Broken/Weak Cryptographic Algorithm (DES)
    public String encryptData(String data) throws Exception {
        String key = "mykey123"; // 8 bytes for DES
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return java.util.Base64.getEncoder().encodeToString(encrypted);
    }
    
    // VULNERABILITY 16: Weak Password Hashing (MD5 without salt)
    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
    
    // VULNERABILITY 17: Insecure Random Number Generation
    public String generateToken() {
        java.util.Random random = new java.util.Random(System.currentTimeMillis());
        return String.valueOf(random.nextLong());
    }
    
    // VULNERABILITY 18: Hardcoded Encryption Key
    public String encryptSensitiveData(String plaintext) throws Exception {
        String hardcodedKey = "ThisIsMySecretEncryptionKey12345";
        byte[] keyBytes = hardcodedKey.getBytes();
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, 0, 16, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(plaintext.getBytes());
        return java.util.Base64.getEncoder().encodeToString(encrypted);
    }
}
