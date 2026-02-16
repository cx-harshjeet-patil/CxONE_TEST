package com.pacejapan.components;

import javax.naming.*;
import javax.naming.directory.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class AuthenticationService {
    
    // VULNERABILITY 8: LDAP Injection
    public boolean authenticateUser(String username, String password) throws NamingException {
        String ldapUrl = "ldap://localhost:389";
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapUrl);
        
        DirContext ctx = new InitialDirContext(env);
        String filter = "(&(uid=" + username + ")(userPassword=" + password + "))";
        SearchControls searchControls = new SearchControls();
        NamingEnumeration<SearchResult> results = ctx.search("ou=users,dc=example,dc=com", filter, searchControls);
        
        return results.hasMore();
    }
    
    // VULNERABILITY 9: XML External Entity (XXE) Injection
    public Document parseXmlConfig(String xmlContent) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new java.io.ByteArrayInputStream(xmlContent.getBytes()));
    }
}
