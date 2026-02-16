package com.pacejapan.components;

import javax.naming.*;
import javax.naming.directory.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class SecureAuthenticationService {
    
    // SECURE: LDAP injection prevention with proper escaping
    public boolean authenticateUser(String username, String password) throws NamingException {
        String ldapUrl = "ldap://localhost:389";
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapUrl);
        
        DirContext ctx = new InitialDirContext(env);
        
        // Escape special LDAP characters
        String escapedUsername = escapeLdap(username);
        String escapedPassword = escapeLdap(password);
        
        String filter = "(&(uid=" + escapedUsername + ")(userPassword=" + escapedPassword + "))";
        SearchControls searchControls = new SearchControls();
        NamingEnumeration<SearchResult> results = ctx.search("ou=users,dc=example,dc=com", filter, searchControls);
        
        return results.hasMore();
    }
    
    private String escapeLdap(String input) {
        return input.replace("\\", "\\5c")
                    .replace("*", "\\2a")
                    .replace("(", "\\28")
                    .replace(")", "\\29")
                    .replace("\0", "\\00");
    }
    
    // SECURE: XXE prevention with secure parser configuration
    public Document parseXmlConfig(String xmlContent) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        // Disable external entities
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);
        
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new java.io.ByteArrayInputStream(xmlContent.getBytes()));
    }
}
