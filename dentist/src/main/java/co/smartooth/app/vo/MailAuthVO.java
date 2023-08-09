package co.smartooth.app.vo;

public class MailAuthVO {
	
	// 메일 인증 할 아이디
	private String userId;
	// 메일 인증 번호
	private String authKey;
	// 메일 인증 여부 (Y,N)
	private String authStatus;
	// 메일 인증 번호 (암호화)
	private String encAuthKey;
	// 메일 인증 번호 (암호화)
	private String decAuthKey;

	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	public String getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}
	
	public String getEncAuthKey() {
		return encAuthKey;
	}
	public void setEncAuthKey(String encAuthKey) {
		this.encAuthKey = encAuthKey;
	}
	public String getDecAuthKey() {
		return decAuthKey;
	}
	public void setDecAuthKey(String decAuthKey) {
		this.decAuthKey = decAuthKey;
	}
	
	
}
