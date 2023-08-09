package co.smartooth.app.service;

import co.smartooth.app.vo.MailAuthVO;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 04. 28
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01
 * -- 메일 사용해야하며, UserVO안의 auth 관련 컬럼에 저장 후 조회 업데이트 해야함
 */
public interface MailAuthService {
	
	
	// 메일 발송 : MAPPER - XML 이없음
	public void sendMail(String userId) throws Exception;

	
	// 비밀번호 초기화 메일 발송
	public void sendResetPasswordMail(String userId, String userEmail, String emailAuthKey) throws Exception;
	
	
	// UserId로 메일 인증 번호 발행 후 Database 에 입력
	void insertAuthKeyById(MailAuthVO dentistMailAuthVO) throws Exception;

	
	// 메일 인증 번호 업데이트
	public void updateAuthKeyById(MailAuthVO dentistMailAuthVO) throws Exception;
	
	
	// 인증 메일 클릭 시 인증 상태를 'Y' 로 업데이트
	public void updateAuthStatusY(String userId) throws Exception;

	
	// 인증 메일 요청 시 인증 상태를 'N' 로 업데이트
	public void updateAuthStatusN(String userId) throws Exception;
	
	
	// 사용자 메일 인증 여부 확인
	public String isEmailAuthEnabled(String userId) throws Exception;


	
}
