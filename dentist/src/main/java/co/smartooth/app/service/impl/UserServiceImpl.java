package co.smartooth.app.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.smartooth.app.mapper.UserMapper;
import co.smartooth.app.service.UserService;
import co.smartooth.app.vo.DentistInfoVO;
import co.smartooth.app.vo.UserVO;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 04. 28
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01
 */
@Service
public class UserServiceImpl implements UserService{
	
	
	@Autowired(required = false)
	UserMapper userMapper;


	
	// 일반 등록 회원 아이디 중복 체크
	@Override
	public int duplicateChkId(String userId) throws Exception {
		return userMapper.duplicateChkId(userId);
	}

	
	
	//	일반 등록 회원 등록 (회원가입)
	@Override
	public void insertUserInfo(UserVO dentistUserVO) throws Exception {
		userMapper.insertUserInfo(dentistUserVO);
	}
	
	
	
	// 측정 자료가 없는 테스트 계정 아이디 조회
	@Override
	public UserVO selectNoMeasureValueUserId(@Param("dentalHospitalCd") String dentalHospitalCd) throws Exception{
		return userMapper.selectNoMeasureValueUserId(dentalHospitalCd);
	}
	
	
	
	// 할당된 회원의 차이 형태 업데이트
	@Override
	public void updateUserTeethType(@Param("userId") String userId, @Param("teethType") String teethType) throws Exception{
		userMapper.updateUserTeethType(userId, teethType);
	}
	
	
	
	// 할당된 회원 userId로 IS_MEASURING = Y 처리
	@Override
	public void updateIsMeasuring(@Param("userId") String userId) throws Exception{
		userMapper.updateIsMeasuring(userId);
	}
	
	
	
	// 일반 등록 회원 정보 업데이트
	@Override
	public void updateUserInfo(UserVO userVo) throws Exception {
		userMapper.updateUserInfo(userVo);
	}
	
	
	
	// 일반 등록 회원 상세정보 업데이트
	@Override
	public void updateUserDetailInfo(UserVO userVO) throws Exception{
		userMapper.updateUserDetailInfo(userVO);
	}

	
	
	// 일반 등록 회원 시퀀스 조회 ( 생성 전 SEQ_STR)
	@Override
	public Integer selectUserSeqNo(@Param("userType") String userType) throws Exception {
		return userMapper.selectUserSeqNo(userType);
	}
	
	
	
	// 일반 등록 회원 시퀀스 조회 ( 생성 전 SEQ_STR)
	@Override
	public int selectUserSeqStr() throws Exception {
		return userMapper.selectUserSeqStr();
	}
	
	
	
	// 일반 등록 회원 시퀀스 생성 후 SEQ_NO
	@Override
	public void updateUserSeqNo(@Param("userType") String userType, @Param("seqNo") int seqNo) throws Exception {
		userMapper.updateUserSeqNo(userType, seqNo);
	}

	
	
	// 일반 등록 회원 시퀀스 업데이트  
	@Override
	public void updateUserSeqStr(@Param("seqStr") int seqStr) throws Exception {
		userMapper.updateUserSeqStr(seqStr);
	}

	
	
	// 일반 등록 회원 정보 조회
	@Override
	public UserVO selectUserInfo(@Param("userId") String userId) throws Exception {
		return userMapper.selectUserInfo(userId);
	}
	
	
	
	// 일반 등록 회원 비밀번호 변경(찾기)
	@Override
	public void updateUserPwd(UserVO dentistUserVO) throws Exception {
		userMapper.updateUserPwd(dentistUserVO);
	}
	
	
	
	// 이메일 주소 업데이트
	@Override
	public void updateUserEmail(@Param("userId") String userId, @Param("userEmail") String userEmail) throws Exception {
		userMapper.updateUserEmail(userId, userEmail);
	}
	
	
	
//   // 회원의 삭제
//     @Override
//     public void deleteUser(String userId) throws Exception {
//    		dentalUserMapper.deleteUser(userId);
//     }
    

	
	// 기관 목록 조회
//	@Override
//	public List<DentalSchoolClassInfoVO> selectSchoolList() throws Exception {
//		return dentalUserMapper.selectSchoolList();
//	}
	
	
    
    // 부서(반) 회원 리스트 조회
//	@Override
//	public List<DentalUserVO> selectDepartmentList(@Param("schoolCode") String schoolCode) throws Exception {
//		return dentalUserMapper.selectDepartmentList(schoolCode);
//	}

	
	
	// 부서(반) ID로 해당 피측정자 목록 조회
	@Override
	public List<UserVO> selectMeasuredUserList(@Param("userId") String userId, @Param("orderBy") String orderBy) throws Exception {
		return userMapper.selectMeasuredUserList(userId, orderBy);
	}


	
	// 피측정자 상세 정보 등록
	@Override
	public void insertUserDetail(UserVO dentistUserVO) throws Exception {
		userMapper.insertUserDetail(dentistUserVO);
	}


	
	// 부서(반) ID로 피측정자 목록 조회
//	@Override
//	public List<DentalUserVO> selectTestUserListByTc(@Param("userId") String userId, @Param("userName") String userName) throws Exception {
//		return dentalUserMapper.selectTestUserListByTc(userId, userName);
//	}


	// 피측정자 이름 변경
//	@Override
//	public void updateTestUserName(@Param("userId")  String userId, @Param("userName")  String userName) throws Exception {
//		dentalUserMapper.updateTestUserName(userId, userName);
//	}




	
	
	
	
	
	
	
	
	
	
	
	// 피측정자 상세 정보 조회
	@Override
	public UserVO selectUserDetailInfo(@Param("userId")  String userId) throws Exception {
		return userMapper.selectUserDetailInfo(userId);
	}



	// 피측정자 아이디 중복 체크 (ID와 기관코드)
//	@Override
//	public int duplicateChkPaUserId(@Param("userId") String userId, @Param("schoolCode") String schoolCode) throws Exception {
//		return dentalUserMapper.duplicateChkPaUserId(userId, schoolCode);
//	}
	

	
	// 치과 소속 의사 정보 등록
	@Override
	public void insertDentistInfo(DentistInfoVO dentalDentistInfoVO) throws Exception{
		userMapper.insertDentistInfo(dentalDentistInfoVO);
	}
	
	
	
	// 치과 소속 의사 정보 등록
	@Override
	public void updateDentistInfo(DentistInfoVO dentalDentistInfoVO) throws Exception{
		userMapper.updateDentistInfo(dentalDentistInfoVO);
	}
	
	
	
	// 치과 소속 의사 목록 조회
	@Override
	public List<HashMap<String, Object>>  selectDentistList(@Param("dentalHospitalCd") String dentalHospitalCd) throws Exception {
		return userMapper.selectDentistList(dentalHospitalCd);
				
	}
	
	
	// 최근 등록한 의사 아이디 조회
	public String selectDentistId(@Param("dentalHospitalCd") String dentalHospitalCd) throws Exception{
		return userMapper.selectDentistId(dentalHospitalCd);
	}


	
	// 개인정보 제공을 동의한 환자 목록(SYSDATE)
	@Override
	public List<HashMap<String, Object>> selectInfomationAgreeUserList(@Param("dentalHospitalCd") String dentalHospitalCd) throws Exception {
		return userMapper.selectInfomationAgreeUserList(dentalHospitalCd);
	}



	// 일반 등록 전환 전 일반 사용자 중 동일한 개인정보를 가지고 있는지 여부 확인
	@Override
	public HashMap<String, Object> duplicateChkUserInfo(UserVO userVO) throws Exception{
		return userMapper.duplicateChkUserInfo(userVO);
	}


	
	
	

	
}