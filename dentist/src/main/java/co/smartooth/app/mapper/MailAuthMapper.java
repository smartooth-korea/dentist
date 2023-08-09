package co.smartooth.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import co.smartooth.app.vo.MailAuthVO;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 04. 28
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01
 */
@Mapper
public interface MailAuthMapper {

	// UserId로 메일 인증키 발행 후 Database에 입력
	public void insertAuthKeyById(MailAuthVO dentistMailAuthVO) throws Exception;

	// 메일 인증을 요청한 사용자 DB row에 인증키(AuthKey) 업데이트
	public void updateAuthKeyById(MailAuthVO dentistMailAuthVO) throws Exception;

	// 메일 인증 테이블에 USER_ID가 있는지 조회
	public int selectMailAuthInfo(MailAuthVO dentistMailAuthVO) throws Exception;
	
	// 회원가입 시 입력된 아이디(이메일)을 통해 DB에 있는지 조회
	public int selectIdWhetherOrNot(String userId) throws Exception;
	
	// 인증 메일 클릭 시 AuthStatus 'Y'로 변경
	public void updateAuthStatusY(String userId) throws Exception;

	// 인증 메일 재 요청 시 AuthStatus 'N'로 변경
	public void updateAuthStatusN(String userId) throws Exception;
	
	// 사용자 메일 인증 여부
	public String isEmailAuthEnabled(String userId) throws Exception;
}
