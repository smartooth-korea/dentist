package co.smartooth.app.vo;

import java.io.Serializable;

public class TeethInfoVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 회원 번호
	private String userNo;
	// 회원 아이디
	private String userId;
	// 회원 치아 상태
	private String teethStatus;
	// 회원 치아 측정일
	private String recordDt;
	
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTeethStatus() {
		return teethStatus;
	}
	public void setTeethStatus(String teethStatus) {
		this.teethStatus = teethStatus;
	}
	public String getRecordDt() {
		return recordDt;
	}
	public void setRecordDt(String recordDt) {
		this.recordDt = recordDt;
	}
	
	
}
