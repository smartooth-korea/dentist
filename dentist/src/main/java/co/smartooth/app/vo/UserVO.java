package co.smartooth.app.vo;

import java.io.Serializable;


/**
 * 작성자 : 정주현 
 * 작성일 : 2023. 07. 01
 * 수정일 : 2023. 08. 07
 */
public class UserVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int seqNo;
	// ST_USER
	private String userNo;
	private String userId;
	private String userPwd;
	private String userType;
	private String userName;
	private String userEmail;
	private String userBirthday;
	private String userCountry;
	private String userState;
	private String userAddress;
	private String userTelNo;
	private String userSex;
	private String userRgstDt;
	private String pushToken;
	private String loginDt;
	private String userDeleteYn;
	private String userDeleteDt;
	private int loginCk;
	private String countryNm;
	private String stateNm;
	private String sidoNm;
	private String sigunguNm;
	private String eupmyeondongNm;
	private String addrDetail;
	private String organCd;

	// 이메일 인증 관련
	private String authEmail;
	private String authStatus;
	private String authKey;
	
	// ST_STUDENT_USER_DETAIL :: STUDENT
	private String teacherId;
	private String isMeasuring;
	private String paUserName;
	private String paUserTelNo;
	private String teethType;
	private String agreYn;
	
	// ST_TEACHER_USER_DETAIL :: TEACHER
	private String schoolCode;
	private String schoolType;
	private String classCode;
	private String classDepth;
	
	// ST_PARENT_USER_DETAIL :: PARENT
	private String childId;
	
	// 기타
	private String userEmailYn;
	private String userAuthToken;

	
	
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
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
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}
	public String getUserCountry() {
		return userCountry;
	}
	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}
	public String getUserState() {
		return userState;
	}
	public void setUserState(String userState) {
		this.userState = userState;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getUserTelNo() {
		return userTelNo;
	}
	public void setUserTelNo(String userTelNo) {
		this.userTelNo = userTelNo;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserRgstDt() {
		return userRgstDt;
	}
	public void setUserRgstDt(String userRgstDt) {
		this.userRgstDt = userRgstDt;
	}
	public String getPushToken() {
		return pushToken;
	}
	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}
	public String getLoginDt() {
		return loginDt;
	}
	public void setLoginDt(String loginDt) {
		this.loginDt = loginDt;
	}
	public String getUserDeleteYn() {
		return userDeleteYn;
	}
	public void setUserDeleteYn(String userDeleteYn) {
		this.userDeleteYn = userDeleteYn;
	}
	public String getUserDeleteDt() {
		return userDeleteDt;
	}
	public void setUserDeleteDt(String userDeleteDt) {
		this.userDeleteDt = userDeleteDt;
	}
	public int getLoginCk() {
		return loginCk;
	}
	public void setLoginCk(int loginCk) {
		this.loginCk = loginCk;
	}
	public String getCountryNm() {
		return countryNm;
	}
	public void setCountryNm(String countryNm) {
		this.countryNm = countryNm;
	}
	public String getStateNm() {
		return stateNm;
	}
	public void setStateNm(String stateNm) {
		this.stateNm = stateNm;
	}
	public String getSidoNm() {
		return sidoNm;
	}
	public void setSidoNm(String sidoNm) {
		this.sidoNm = sidoNm;
	}
	public String getSigunguNm() {
		return sigunguNm;
	}
	public void setSigunguNm(String sigunguNm) {
		this.sigunguNm = sigunguNm;
	}
	public String getEupmyeondongNm() {
		return eupmyeondongNm;
	}
	public void setEupmyeondongNm(String eupmyeondongNm) {
		this.eupmyeondongNm = eupmyeondongNm;
	}
	public String getAddrDetail() {
		return addrDetail;
	}
	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}
	public String getOrganCd() {
		return organCd;
	}
	public void setOrganCd(String organCd) {
		this.organCd = organCd;
	}
	public String getAuthEmail() {
		return authEmail;
	}
	public void setAuthEmail(String authEmail) {
		this.authEmail = authEmail;
	}
	public String getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getIsMeasuring() {
		return isMeasuring;
	}
	public void setIsMeasuring(String isMeasuring) {
		this.isMeasuring = isMeasuring;
	}
	public String getPaUserName() {
		return paUserName;
	}
	public void setPaUserName(String paUserName) {
		this.paUserName = paUserName;
	}
	public String getPaUserTelNo() {
		return paUserTelNo;
	}
	public void setPaUserTelNo(String paUserTelNo) {
		this.paUserTelNo = paUserTelNo;
	}
	public String getTeethType() {
		return teethType;
	}
	public void setTeethType(String teethType) {
		this.teethType = teethType;
	}
	public String getAgreYn() {
		return agreYn;
	}
	public void setAgreYn(String agreYn) {
		this.agreYn = agreYn;
	}
	public String getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}
	public String getSchoolType() {
		return schoolType;
	}
	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getClassDepth() {
		return classDepth;
	}
	public void setClassDepth(String classDepth) {
		this.classDepth = classDepth;
	}
	public String getChildId() {
		return childId;
	}
	public void setChildId(String childId) {
		this.childId = childId;
	}
	public String getUserEmailYn() {
		return userEmailYn;
	}
	public void setUserEmailYn(String userEmailYn) {
		this.userEmailYn = userEmailYn;
	}
	public String getUserAuthToken() {
		return userAuthToken;
	}
	public void setUserAuthToken(String userAuthToken) {
		this.userAuthToken = userAuthToken;
	}
	
}