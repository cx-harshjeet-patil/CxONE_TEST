# CxONE_TEST Repository

This repository contains a test structure with intentional security vulnerabilities for testing purposes.

## Structure

```
CxONE_TEST/
├── apps/
│   └── pacejapan/
│       └── components/
│           ├── SecureUserController.java (SECURE)
│           └── SecureFileHandler.java (SECURE)
├── apps2/
│   ├── pacejapan/
│   │   └── components/
│   │       ├── UserController.java (VULNERABLE)
│   │       ├── FileHandler.java (VULNERABLE)
│   │       ├── SecurityManager.java (VULNERABLE)
│   │       ├── AuthenticationService.java (VULNERABLE)
│   │       └── SessionManager.java (VULNERABLE)
│   └── pacejapan2/
│       └── components/
│           ├── SecureSecurityManager.java (SECURE)
│           └── SecureAuthenticationService.java (SECURE)
```

## Vulnerabilities in CxONE_TEST\apps2\pacejapan

### 1. SQL Injection (UserController.java)
- Direct string concatenation in SQL query
- Location: `getUserById()` method

### 2. Command Injection (UserController.java)
- Unsanitized user input passed to `Runtime.exec()`
- Location: `executeSystemCommand()` method

### 3. Path Traversal (FileHandler.java)
- No validation on file path input
- Location: `readFile()` method

### 4. Cross-Site Scripting - XSS (FileHandler.java)
- Unencoded user input displayed in HTML
- Location: `displayUserComment()` method

### 5. Hardcoded Credentials (SecurityManager.java)
- Database password and API key in source code
- Location: Class constants `DB_PASSWORD` and `API_KEY`

### 6. Weak Cryptographic Algorithm (SecurityManager.java)
- Using MD5 for password hashing
- Location: `hashPassword()` method

### 7. Insecure Random Number Generation (SecurityManager.java)
- Using `java.util.Random` instead of `SecureRandom`
- Location: `generateToken()` method

### 8. LDAP Injection (AuthenticationService.java)
- Unsanitized input in LDAP filter
- Location: `authenticateUser()` method

### 9. XML External Entity (XXE) Injection (AuthenticationService.java)
- XML parser not configured to prevent XXE attacks
- Location: `parseXmlConfig()` method

### 10. Insecure Deserialization (SessionManager.java)
- Deserializing untrusted user input
- Location: `deserializeUserSession()` method

## Secure Code Examples

The other directories (`apps/pacejapan` and `apps2/pacejapan2`) contain secure implementations that demonstrate:
- Prepared statements for SQL injection prevention
- Input validation and whitelisting
- Output encoding for XSS prevention
- Secure configuration management
- Strong cryptographic algorithms
- Secure XML parsing
- LDAP input sanitization

## ⚠️ Warning

This code contains intentional security vulnerabilities for testing purposes only. 
**DO NOT use this code in production environments.**
