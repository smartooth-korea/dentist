package co.smartooth.app.auth;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 04. 28
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01
 */
@Component
public class MailAuthInfo extends Authenticator{
    
    PasswordAuthentication pa;
    
    private static String username;
    private static String password;
    private static String serverIp;
    private static String serverPort;
    private static String senderName;

    
    @Value("${mailusername}")
    public void setUsername(String value) {
    	username = value;
    }
    
    @Value("${mailpassword}")
    public void setPassword(String value) {
    	password = value;
    }

    @Value("${mailsendername}")
    public void setSenderName(String value) {
    	senderName = value;
    }
    
	@Value("${ip}")
    public void setServerIp(String value) {
		serverIp = value;
    }
    
	@Value("${port}")
    public void setServerPort(String value) {
		serverPort = value;
    }
	
    
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getServerIp() {
		return serverIp;
	}

	public String getServerPort() {
		return serverPort;
	}
	
	public String getServerInfo() {
		return serverIp + ":" + serverPort;
    }
    
    public String getSenderName() {
    	return senderName;
    }
    
    
    public MailAuthInfo() {
        pa = new PasswordAuthentication(username, password);
    }
    
    public PasswordAuthentication getPasswordAuthentication() {
        return pa;
    }
    
    
   
}
