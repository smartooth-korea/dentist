package co.smartooth.app.mapper;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 04. 28
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01
 */
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import co.smartooth.app.vo.AuthVO;

@Mapper
public interface AuthMapper {

	// 회원 아이디 존재 여부 :: true = 1, false = 0
	public int loginChkByIdPwd(AuthVO dentistAuthVO) throws Exception;
	
	// 회원 아이디가 존재하는지 여부 확인 :: true = 1, false = 0
	public int isIdExist(@Param("userId") String userId) throws Exception;

}
