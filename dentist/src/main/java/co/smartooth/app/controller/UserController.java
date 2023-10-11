package co.smartooth.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import co.smartooth.app.service.LogService;
import co.smartooth.app.service.TeethService;
import co.smartooth.app.service.UserService;
import co.smartooth.app.utils.AES256Util;
import co.smartooth.app.vo.AuthVO;
import co.smartooth.app.vo.DentistInfoVO;
import co.smartooth.app.vo.TeethInfoVO;
import co.smartooth.app.vo.UserVO;


/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 04. 28
 * 수정일 : 2022. 08. 03
 * 서버분리 : 2023. 08 .01
 * @RestController를 쓰지 않는 이유는 몇 안되는 mapping에 jsp를 반환해줘야하는게 있으므로 @Controller를 사용한다.
 * @RestAPI로 반환해야할 경우 @ResponseBody를 사용하여 HashMap으로 return 해준다.
 */
@Controller
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	

	@Autowired(required = false)
	private UserService userService;
	
	@Autowired(required = false)
	private TeethService teethService;
	
	@Autowired(required = false)
	private LogService logService;
	
	// 인증 여부 확인 flag
	//private static boolean tokenValidation = false; 
	private static boolean tmpTokenValidation = true; 
	

	/**
	 * 기능   : 치과 서비스 앱 내의 피측정자(환자) 아이디 중복 확인
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 05. 10
	 * 수정일 : 2023. 08. 07
	 */
	@PostMapping(value = {"/dentist/user/duplicateChkId.do"})
	@ResponseBody
	public HashMap<String,Object> duplicateChkId(@RequestBody HashMap<String, String> paramMap){

		logger.debug("========== dentist.UserController ========== /dentist/user/duplicateChkId.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/duplicateChkId.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/duplicateChkId.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/duplicateChkId.do ==========");
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		int isExistRow = 0;

		String userId = (String)paramMap.get("userId");
		String dentalHospitalCd = (String)paramMap.get("schoolCode");
		
		userId = dentalHospitalCd + "-" + userId;
		// Parameter = userId 값 검증 (Null 체크 및 공백 체크)
		if(userId == null || userId.equals("")) {
			hm.put("code", "401");
			hm.put("msg", "등록번호(아이디)가 전달되지 않았습니다.");
			// hm.put("msg", "There is no userId parameter");
			return hm;
		}

		try {
			// 회원 아이디 중복 체크 :: 회원 테이블에 값이 존재하는지 여부 확인
			isExistRow =  userService.duplicateChkId(userId);
			
			if(isExistRow > 0) {
				// 등록되어 있는 아이디가 없을 경우 0
				// 등록되어 있는 아이디가 있을 경우 1
				hm.put("code", "402");
				hm.put("msg", "해당 아이디는 이미 등록되어 있습니다.");
				//hm.put("msg", "This ID is already registered.");
			}else {
				hm.put("code", "000");
				hm.put("msg", "해당 아이디는 사용 가능합니다.");
				//hm.put("msg", "This ID is an available.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("code", "500");
			hm.put("msg", "아이디 중복 체크에 실패하였습니다.\n관리자에게 문의해주시기 바랍니다.");
			//hm.put("msg", "Server error");
		}
		
		return hm;
	}

	
	
	
	
	/**
	 * 기능   : 치과 서비스 앱 치과 소속 환자(회원) 추가 - 일반 등록
	 * 작성자 : 정주현 
	 * 작성일 : 2023. 05. 19
	 * 수정일 : 2022. 08. 03
	 */
	@PostMapping(value = {"/dentist/user/register.do"})
	@ResponseBody
	public HashMap<String,Object> registerUser(@RequestBody HashMap<String, Object> paramMap) throws Exception{
		
		
		logger.debug("========== dentist.UserController ========== /dentist/user/register.do ==========");
		logger.debug("========== dentist.UserController  ========== /dentist/user/register.do ==========");
		logger.debug("========== dentist.UserController  ========== /dentist/user/register.do ==========");
		logger.debug("========== dentist.UserController  ========== /dentist/user/register.do ==========");

		
		String lang = (String)paramMap.get("lang");
	    // 하드코딩
	    //if(lang == null || lang.equals("")) {
	    //	lang = "ko";
	    //}
		
		/**회원 공통 정보**/
		// 기관 ID
		// String dentistUserId = (String)paramMap.get("dentistUserId");
		// 회원 ID
		String userId = (String)paramMap.get("userId");
		// 회원 번호
		String userNo = userId;
		// 회원 비밀번호
		String userPwd = (String)paramMap.get("userPwd");
		// 회원 유형 (PA : 환자)
		String userType = (String)paramMap.get("userType");
		// 회원 생일
		String userName = (String)paramMap.get("userName");
		// 회원 타입 (개인, 단체, 그룹)
		String userBirthday = (String)paramMap.get("userBirthday");
		// 회원 전화번호
		String userTelNo = (String)paramMap.get("userTelNo");
		// 회원 성별
		String userSex = (String)paramMap.get("userSex");
		// 회원 거주 - 국
		//String userCountry = (String)paramMap.get("userCountry");
		// 회원 거주 - 주
		//String userState = (String)paramMap.get("userStatus");
		// 회원 상세 주소
		//String userAddress = (String)paramMap.get("userStatus");
		
		// 회원 치아 상태
		String teethStatus = (String)paramMap.get("teethStatus");
		// 회원 치아 형태
		String teethType = (String)paramMap.get("teethType");
		
		// 치과 코드
		String dentalHospitalCd = (String)paramMap.get("schoolCode");
		// 부서 코드
		String departmentCd = (String)paramMap.get("classCode");
		
		/** 회원 - 14세미만 **/
		String paUserName = (String)paramMap.get("paUserName");
		String paUserTelNo = (String)paramMap.get("paUserTelNo");
		
		// 동의 여부
		String agreYn = null;
		
		// 회원 가입 시 부모 이름 및 부모의 번호가 있을 경우 동의 한 것으로 처리 : Y 
		if(paUserName!=null && !paUserName.equals("") || paUserTelNo!=null &&!paUserTelNo.equals("")) {
			agreYn = "Y";
		}

		
		
		// 파라미터에 치아 상태 정보가 없을 경우 정상으로 입력
		if(teethStatus == null || teethStatus.equals("")) {
			teethStatus = "100|100|100|100|100|100|100|100|100|100|100|100|"
					  + "100|100|100|100|100|100|100|100|100|100|100|100|"
					  + "100|100|100|100|100|100|100|100|100|100|100|100|"
					  + "100|100|100|100|100|100|100|100|100|100|100|100|"
					  + "100|100|100|100|100|100|100|100";
		}
		
		UserVO userVO = new UserVO();
		TeethInfoVO teethInfoVO = new TeethInfoVO();
		HashMap<String,Object> hm = new HashMap<String,Object>();
		List<UserVO> measuredUserList = new ArrayList<UserVO>();
		
		try {
			
			// 치과 전용이므로 PA로 고정
			if(userType == null || "".equals(userType)) {
				userType = "PA";
			}
			
			// 아이디 생성 시 치과 코드 + 입력 
			userId = dentalHospitalCd+"-"+userId;
			
			// 비밀번호 암호화
			AES256Util aes256Util = new AES256Util();
			userPwd = aes256Util.aesEncode(userPwd);
			// 이름 암호화
			//userName = aes256Util.aesEncode(userName);
			
			/** 회원 공통 정보**/
			// 회원 정보 VO
			userVO.setUserId(userId);
			userVO.setUserNo(userNo);
			userVO.setUserPwd(userPwd);
			userVO.setUserName(userName);
			userVO.setUserType(userType);
			userVO.setSchoolType(userType);
			userVO.setUserBirthday(userBirthday);
			userVO.setUserTelNo(userTelNo);
			userVO.setUserSex(userSex);
			
			/** 회원 치아 형태 정보 **/
			userVO.setTeethType(teethType);
			
			/** 법정대리인(보호자) 정보 **/
			userVO.setPaUserName(paUserName);
			userVO.setPaUserTelNo(paUserTelNo);
			
			/** 동의 여부 **/
			userVO.setAgreYn(agreYn);
			
			/**기관 및 부서 정보**/
			userVO.setClassCode(departmentCd);
			userVO.setSchoolCode(dentalHospitalCd);
			
			
			/** 회원 치아 정보 **/
			teethInfoVO.setUserId(userId);
			teethInfoVO.setTeethStatus(teethStatus); 
			
			// 프리미엄(치과) 회원 등록
			userService.insertUserInfo(userVO);
			// 프리미엄(치과) 회원 상세 정보 등록
			userService.insertUserDetail(userVO);
			// 프리미엄(치과) 회원 치아 상태 등록
			teethService.insertUserTeethInfo(teethInfoVO);
			 // 치과에 소속되어 있는 모든 환자 목록(T00~T99도 포함)
			measuredUserList= userService.selectMeasuredUserList(departmentCd, "ASC");
			
		} catch (Exception e) {
			
			hm.put("code", "500");
			//if(lang.equals("ko")) {
				hm.put("msg", "서버 에러가 발생했습니다.\n관리자에게 문의해주시기 바랍니다.");
			//}else if(lang.equals("en")) {
			//	hm.put("msg", "Server Error");
			//}
			e.printStackTrace();
			
		}
		
		// 피측정자 목록
		hm.put("measuredUserList", measuredUserList);
		hm.put("code", "000");
		//if(lang.equals("ko")) {
			hm.put("msg", "등록되었습니다.");
		//}else if(lang.equals("en")) {
		//	hm.put("msg", "Success");
		//}

		return hm;
	}
	
	
	
	
	/**
	 * 기능   : 일반 등록 회원 정보 수정
	 * 작성자 : 정주현 
	 * 작성일 : 2023. 06. 30
	 * 수정일 : 2023. 08. 07
	 */
	@PostMapping(value = {"/dentist/user/updateUserInfo.do"})
	@ResponseBody
	public HashMap<String, Object> updateUserInfo(@RequestBody HashMap<String, Object> paramMap) throws Exception {
		
		
		logger.debug("========== dentist.UserController ========== /dentist/user/updateUserInfo.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/updateUserInfo.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/updateUserInfo.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/updateUserInfo.do ==========");

		
		// 일반 등록 회원 아이디
		String userId = null;
		// 일반 등록 회원 이름
		String userName = null;
		// 일반 등록 회원 전화번호
		String userTelNo = null;
		// 일반 등록 회원 생년월일
		String userBirthday = null;
		// 일반 등록 회원 성별
		String userSex = null;
		// 법정대리인 동의 여부
		String agreYn = null;
		// 법정대리인 이름
		String paUserName = null;
		// 법정대리인 전화번호
		String paUserTelNo = null;
		// 회원 치아 형태
		String teethType = null;

		// 치과코드
		String dentalHospitalCd = null;
		// 치과부서 코드
		String departmentCd = null;

//		// 일반 등록 회원 거주 국
//		String userCountry = null;
//		// 일반 등록 회원 거주 주
//		String userState = null;
//		// 일반 등록 회원 주소 (시도, 시군구, 읍면동)
//		String sidoNm = null;
//		String sigunguNm = null;
//		String eupmyeondongNm = null;
		

		HashMap<String, Object> hm = new HashMap<String, Object>();
		UserVO userVO = new UserVO();
		List<UserVO> measuredUserList = new ArrayList<UserVO>();

		userId = (String) paramMap.get("userId");

		// Parameter = userId 값 검증 (Null 체크 및 공백 체크)
		userId = (String) paramMap.get("userId");
		if (userId == null || userId.equals("") || userId.equals(" ")) {
			hm.put("code", "401");
			hm.put("msg", "등록번호(아이디)가 전달되지 않았습니다.");
			// hm.put("msg", "There is no userId parameter");
			return hm;
		}
		
		userName = (String) paramMap.get("userName");
		userTelNo = (String) paramMap.get("userTelNo");
		userBirthday = (String) paramMap.get("userBirthday");
		userSex = (String) paramMap.get("userSex");

		// 14세 미만 회원일 경우
		agreYn = (String) paramMap.get("agreYn");
		paUserName = (String) paramMap.get("paUserName");
		paUserTelNo = (String) paramMap.get("paUserTelNo");
		
		// 회원 치아 형태
		teethType = (String) paramMap.get("teethType");
		// 치과코드
		dentalHospitalCd = (String) paramMap.get("dentalHospitalCd");
		// 치과부서 코드
		departmentCd = dentalHospitalCd+"01";
		
		userId = dentalHospitalCd +"-"+userId;
		
		//userCountry = (String) paramMap.get("userCountry");
		//userState = (String) paramMap.get("userState");

//		// TOKEN 검증
//		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
//		tokenValidation = jwtTokenUtil.validateToken(userAuthToken);

		if (tmpTokenValidation) {
			try {

				// 회원 기본 정보
				userVO.setUserId(userId);
				userVO.setUserName(userName);
				userVO.setUserTelNo(userTelNo);
				userVO.setUserBirthday(userBirthday);
				userVO.setUserSex(userSex);
				//userVO.setUserCountry(userCountry);
				//userVO.setUserState(userState);
				//userVO.setSidoNm(sidoNm);
				//userVO.setSigunguNm(sigunguNm);
				//userVO.setEupmyeondongNm(eupmyeondongNm);
				
				
				// 회원 상세 정보
				userVO.setPaUserName(paUserName);
				userVO.setPaUserTelNo(paUserTelNo);
				userVO.setAgreYn(agreYn);
				userVO.setTeethType(teethType);
				
				// 회원 기본 정보 업데이트
				userService.updateUserInfo(userVO);
				// 회원 상세 정보 업데이트
				userService.updateUserDetailInfo(userVO);
				
				// 치과에 소속되어 있는 모든 환자 목록(T00~T99도 포함)
				measuredUserList= userService.selectMeasuredUserList(departmentCd, "ASC");

			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "회원 정보 업데이트에 실패했습니다.\n관리자에게 문의해주시기 바랍니다.");
				// hm.put("msg", "Server Error.");
				e.printStackTrace();
			}
		} else {
			hm.put("code", "400");
			hm.put("msg", "토큰이 유효하지 않습니다.\n다시 로그인 해주시기 바랍니다.");
			// hm.put("msg", "The token is not valid.");
		}
		// 데이터 RETURN
		hm.put("measuredUserList", measuredUserList);
		hm.put("code", "000");
		hm.put("msg", "회원 정보 수정 완료");
		// hm.put("msg", "Success");
		return hm;
	}
	
	
	
	
	
	/**
	 * 기능   : 치과 소속 의사 등록
	 * 작성자 : 정주현 
	 * 작성일 : 2023. 06. 30
	 */
	@PostMapping(value = {"/dentist/user/registDentistInfo.do"})
	@ResponseBody
	public HashMap<String,Object> registDentistInfo(@RequestBody HashMap<String, Object> paramMap) throws Exception{
		
		
		logger.debug("========== dentist.UserController ========== /dentist/user/registDentistInfo.do ==========");
		logger.debug("========== dentist.UserController  ========== /dentist/user/registDentistInfo.do ==========");
		logger.debug("========== dentist.UserController  ========== /dentist/user/registDentistInfo.do ==========");
		logger.debug("========== dentist.UserController  ========== /dentist/user/registDentistInfo.do ==========");

		
		String lang = (String)paramMap.get("lang");
	    // 하드코딩
	    //if(lang == null || lang.equals("")) {
	    //	lang = "ko";
	    //}
		
	    // 치과 코드
	    String dentalHospitalCd = (String)paramMap.get("schoolCode");
		// 의사 아이디
		String dentistId = null;
		// 의사 시퀀스 번호
		int dentistSeqNo = 1;
		// 의사 이름
		String dentistNm = (String)paramMap.get("dentistNm");
		// 의사 이메일
		String dentistEmail = (String)paramMap.get("dentistEmail");
		// 의사 전화번호
		String dentistTelNo = (String)paramMap.get("dentistTelNo");
		// 전공 이름
		String medicalMajorNm = (String)paramMap.get("medicalMajorNm");
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		List<HashMap<String, Object>> dentistUserList = new ArrayList<HashMap<String, Object>>();
		DentistInfoVO dentalDentistInfoVO = new DentistInfoVO();
			
		 try {
			
			// 최근 등록한 의사 아이디 조회 후 seq_no (시퀀스 번호) 생성 후 의사 아이디 및 정보 등록
			dentistId = userService.selectDentistId(dentalHospitalCd);
			if(dentistId != null && !dentistId.equals("")) {
				 dentistSeqNo = Integer.parseInt(dentistId.substring(dentistId.length()-2, dentistId.length()));
				 dentistSeqNo++;
			}

			// 치과의사 아이디 생성 (가시적인 아이디 - 실제 사용하지 않음)
			dentistId = dentalHospitalCd+String.format("%02d", dentistSeqNo);
			// 치과의사 정보 VO
			dentalDentistInfoVO.setDentalHospitalCd(dentalHospitalCd);
			dentalDentistInfoVO.setDentistId(dentistId);
			dentalDentistInfoVO.setDentistNm(dentistNm);
			dentalDentistInfoVO.setDentistEmail(dentistEmail);
			dentalDentistInfoVO.setDentistTelNo(dentistTelNo);
			dentalDentistInfoVO.setMedicalMajorNm(medicalMajorNm);
			// 치과의사 정보 등록
			userService.insertDentistInfo(dentalDentistInfoVO);
			// 치과 소속 의사 목록
			dentistUserList = userService.selectDentistList(dentalHospitalCd);
			
		} catch (Exception e) {
			
			hm.put("code", "500");
			if(lang.equals("ko")) {
				hm.put("msg", "치과에 의사 등록에 실패하였습니다.\n관리자에게 문의해주시기 바랍니다.");
			}else if(lang.equals("en")) {
				hm.put("msg", "Failed to register the doctor registration.\nPlease contact the administrator.");
			}
			e.printStackTrace();
			
		}
		
		// 피측정자 목록
		hm.put("dentistUserList", dentistUserList);

		hm.put("code", "000");
		//if(lang.equals("ko")) {
			hm.put("msg", "검사자 추가가 완료되었습니다.");
		//}else if(lang.equals("en")) {
		//	hm.put("msg", "Success");
		//}
		return hm;
		
	}
	
	
	
	
	
	
	
	/**
	 * 기능   : 치과 소속 의사 정보 업데이트
	 * 작성자 : 정주현 
	 * 작성일 : 2023. 06. 30
	 * 수정일 : 2023. 08. 07
	 */
	@PostMapping(value = {"/dentist/user/updateDentistInfo.do"})
	@ResponseBody
	public HashMap<String,Object> updateDentistInfo(@RequestBody HashMap<String, Object> paramMap) throws Exception{
		
		
		logger.debug("========== dentist.UserController ========== updateDentistInfo.do ==========");
		logger.debug("========== dentist.UserController ========== updateDentistInfo.do ==========");
		logger.debug("========== dentist.UserController ========== updateDentistInfo.do ==========");
		logger.debug("========== dentist.UserController ========== updateDentistInfo.do ==========");

		
		String lang = (String)paramMap.get("lang");
	    // 하드코딩
	    //if(lang == null || lang.equals("")) {
	    //	lang = "ko";
	    //}
		
	    // 치과 코드
	    String dentalHospitalCd = (String)paramMap.get("schoolCode");
		// 의사 아이디
		String dentistId = (String)paramMap.get("dentistId");
		// 의사 이름
		String dentistNm = (String)paramMap.get("dentistNm");
		// 의사 이메일
		String dentistEmail = (String)paramMap.get("dentistEmail");
		// 의사 전화번호
		String dentistTelNo = (String)paramMap.get("dentistTelNo");
		// 전공 이름
		String medicalMajorNm = (String)paramMap.get("medicalMajorNm");
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		List<HashMap<String, Object>> dentistUserList = new ArrayList<HashMap<String, Object>>();
		DentistInfoVO dentalDentistInfoVO = new DentistInfoVO();
			
		 try {
			 
			 // 치과 의사 정보 VO
			 dentalDentistInfoVO.setDentalHospitalCd(dentalHospitalCd);
			 dentalDentistInfoVO.setDentistId(dentistId);
			 dentalDentistInfoVO.setDentistNm(dentistNm);
			 dentalDentistInfoVO.setDentistEmail(dentistEmail);
			 dentalDentistInfoVO.setDentistTelNo(dentistTelNo);
			 dentalDentistInfoVO.setMedicalMajorNm(medicalMajorNm);
			 
			 // 치과 소속 의사 정보 수정
			 userService.updateDentistInfo(dentalDentistInfoVO);
			 // 치과 소속 의사 목록
			 dentistUserList = userService.selectDentistList(dentalHospitalCd);
			
		} catch (Exception e) {
			
			hm.put("code", "500");
			//if(lang.equals("ko")) {
				hm.put("msg", "서버 에러가 발생했습니다.\n관리자에게 문의해주시기 바랍니다.");
			//}else if(lang.equals("en")) {
			//	hm.put("msg", "Server Error");
			//}
			e.printStackTrace();
			
		}
		
		// 피측정자 목록
		hm.put("dentistUserList", dentistUserList);

		hm.put("code", "000");
		//if(lang.equals("ko")) {
			hm.put("msg", "성공");
		//}else if(lang.equals("en")) {
		//	hm.put("msg", "Success");
		//}
		return hm;
		
	}
	
	
	
	
	/**
	 * 기능   : 빠른 등록
	 * 작성자 : 정주현 
	 * 작성일 : 2023. 07. 11
	 */
	@PostMapping(value = {"/dentist/user/quickResiter.do"})
	@ResponseBody
	public HashMap<String,Object> quickResiter(@RequestBody HashMap<String, Object> paramMap) throws Exception{
		
		
		logger.debug("========== dentist.UserController ========== /dentist/user/quickResiter.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/quickResiter.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/quickResiter.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/quickResiter.do ==========");

		
		String lang = (String)paramMap.get("lang");
	    // 하드코딩
	    //if(lang == null || lang.equals("")) {
	    //	lang = "ko";
	    //}
	    
	    
	    // 빠른 등록 - 환자 이름
	    String userName = (String)paramMap.get("userName");
	    // 빠른 등록 - 환자 생년월일
	    String userBirthday = (String)paramMap.get("userBirthday");
	    // 빠른 등록 - 환자 치아 형태 (유치잔존 : B, 영구치 - 구분해야함 : P)
	    String teethType = (String)paramMap.get("teethType");
	    
	    
	    String userId = null;
	    // 치과코드
	    String dentalHospitalCd = (String)paramMap.get("dentalHospitalCd"); 
	    // 치과소속 부서 (하드코딩)
	    String departmentCd = dentalHospitalCd+"01";
	    
		//DentalDentistInfoVO dentalDentistInfoVO = new DentalDentistInfoVO();

	    HashMap<String,Object> hm = new HashMap<String,Object>();
	    UserVO userVO = new UserVO();
	    List<UserVO> measuredUserList = new ArrayList<UserVO>();
	    // List<TeethInfoVO> teethInfoVO = new ArrayList<TeethInfoVO>();
			
		 try {
			
			 // 현재 측정된 자료는 없는 T00~T99 사이의 회원 할당
			 userVO = userService.selectNoMeasureValueUserId(dentalHospitalCd);
			 userId = userVO.getUserId();
			 
			 // 할당된 회원의 치아 형태 업데이트
			 userService.updateUserTeethType(userId, teethType);
			 
			 userVO.setUserName(userName);
			 userVO.setUserBirthday(userBirthday);

			 userService.updateUserInfo(userVO);
			 
			 
			 // 할당된 회원 userId로 IS_MEASURING = Y 처리
			 userService.updateIsMeasuring(userId);
			 
			 // 할당된 회원 치아 상태 값 조회
			 // teethInfoVO = teethService.selectUserTeethInfo(userVO);
			 
			 // 치과에 소속되어 있는 모든 환자 목록(T00~T99도 포함)
			 try {
				 measuredUserList= userService.selectMeasuredUserList(departmentCd, "ASC");
			} catch (Exception e) {
				hm.put("msg", "현재 빠른 등록으로 사용할 수 있는 인원 수를 초과하였습니다.\n빠른 등록으로 등록한 회원을 일반 등록으로 전환해주시기 바랍니다.");
				e.printStackTrace();
				return hm;
			}
			 
			
		} catch (Exception e) {
			
			hm.put("code", "500");
			//if(lang.equals("ko")) {
				hm.put("msg", "서버 에러가 발생했습니다.\n관리자에게 문의해주시기 바랍니다.");
			//}else if(lang.equals("en")) {
			//	hm.put("msg", "Server Error");
			//}
			e.printStackTrace();
			return hm;
		}

		// 데이터 RETURN
		hm.put("userId", userId);
		hm.put("measuredUserList", measuredUserList);
		
		 
		hm.put("code", "000");
		//if(lang.equals("ko")) {
			hm.put("msg", "빠른 등록이 완료 되었습니다.");
		//}else if(lang.equals("en")) {
		//	hm.put("msg", "Success");
		//}
		return hm;
		
	}
	
	
	
	
	/**
	 * 기능   : 빠른 등록 >> 일반 등록 전환
	 * 작성자 : 정주현 
	 * 작성일 : 2023. 07. 12
	 */
	@PostMapping(value = {"/dentist/user/switchUserRegister.do"})
	@ResponseBody
	public HashMap<String, Object> switchUserRegister(@RequestBody HashMap<String, Object> paramMap) throws Exception {

		
		logger.debug("========== dentist.UserController ========== /dentist/user/switchUserRegister.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/switchUserRegister.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/switchUserRegister.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/switchUserRegister.do ==========");

		
		String lang = (String) paramMap.get("lang");
		// 하드코딩
		//if (lang == null || lang.equals("")) {
		//	lang = "ko";
		//}

		// 회원 아이디 :: 기존의 T00~T99의 아이디를 받는다
		String testUserId = (String) paramMap.get("testUserId");
		// KRDE3405037007-T01
		// 회원 등록 번호
		String userId = (String) paramMap.get("userId");
		// 0000
		// 회원 이름
		String userName = (String) paramMap.get("userName");
		// 회원 전화번호
		String userTelNo = (String) paramMap.get("userTelNo");
		// 회원 생년월일
		String userBirthday = (String) paramMap.get("userBirthday");
		// 회원 성별
		String userSex = (String) paramMap.get("userSex");
		// 법정대리인 동의 여부
		String agreYn = (String) paramMap.get("agreYn");
		// 법정대리인 이름
		String paUserName = (String) paramMap.get("paUserName");
		// 법정 대리인 전화번호
		String paUserTelNo = (String) paramMap.get("paUserTelNo");
		// 회원 종류
		String userType = "PA";
		// 회원 등록일
		String userRgstDt = null;

		// 회원 치아 형태
		String teethType = (String) paramMap.get("teethType");
		// 회원 치아 상태
		String teethStatus = "100|100|100|100|100|100|100|100|100|100|100|100|"
									  + "100|100|100|100|100|100|100|100|100|100|100|100|"
									  + "100|100|100|100|100|100|100|100|100|100|100|100|"
									  + "100|100|100|100|100|100|100|100|100|100|100|100|"
									  + "100|100|100|100|100|100|100|100";

		// 치과코드
		String dentalHospitalCd = testUserId.substring(0, testUserId.lastIndexOf("-"));
		// 부서 코드
		String departmentCd = dentalHospitalCd + "01";

		// T00~T99 시퀀스 번호
		String testUserSeqNo = null;

		HashMap<String, Object> hm = new HashMap<String, Object>();
		UserVO userVO = new UserVO();
		//UserVO userDetailVO = new UserVO();
		UserVO originalUserVO = new UserVO();
		
		List<UserVO> measuredUserList = new ArrayList<UserVO>();

		TeethInfoVO teethInfoVO = new TeethInfoVO();

		try {

			// 치과 회원 아이디 = 치과코드 + userId 파라미터
			userId = dentalHospitalCd + "-" + userId;

			/** T00~T99 회원일 때의 ※※※※치아 형태※※※※ DB에서 직접 조회 **/
			// userDetailVO = userService.selectUserDetailInfo(userId);
			// teethType = userDetailVO.getTeethType();

			/** 회원 공통 정보 **/
			userVO.setUserId(userId);
			// userVO.setUserNo(userNo);
			// 초기 비밀번호 0000
			userVO.setUserPwd("ZrVBBrdMK1txRmfDR8ZOZA==");
			userVO.setUserName(userName);
			userVO.setUserType(userType);
			userVO.setSchoolType(userType);
			userVO.setUserBirthday(userBirthday);
			userVO.setUserTelNo(userTelNo);
			userVO.setUserSex(userSex);
			userVO.setTeethType(teethType);

			/** 법정대리인(보호자) 정보 **/
			userVO.setPaUserName(paUserName);
			userVO.setPaUserTelNo(paUserTelNo);

			/** 동의 여부 **/
			userVO.setAgreYn(agreYn);

			/** 기관 및 부서 정보 **/
			userVO.setClassCode(departmentCd);
			userVO.setSchoolCode(dentalHospitalCd);

			/** 회원 치아 정보 **/
			teethInfoVO.setUserId(userId);
			teethInfoVO.setTeethStatus(teethStatus);
			
			// 처음 측정 날짜를 가져온 후 아이디 생성일로 맞춰줌
			userRgstDt = teethService.selectUserTeethMeasureDt(testUserId);
			if(userRgstDt != null && !userRgstDt.equals("")) {
				userVO.setUserRgstDt(userRgstDt);
			}
			

			// 일반 등록 전환 전에 일반 사용자 중 동일한 개인정보가 있을 경우 데이터를 합산할수 있도록 하는 메시지
			HashMap<String, Object> duplicateChkUserInfo = userService.duplicateChkUserInfo(userVO);
			
			if(Integer.parseInt(duplicateChkUserInfo.get("COUNT").toString()) == 1) {
				
				// 치과에 소속되어 있는 모든 환자 목록(T00~T99도 포함)
				measuredUserList= userService.selectMeasuredUserList(departmentCd, "ASC");
				
				hm.put("userId", duplicateChkUserInfo.get("USER_ID"));
				hm.put("measuredUserList", measuredUserList);
				hm.put("code", "402");
				hm.put("msg", "해당 정보로 등록 되어있는 아이디가 있습니다.\nID : "+duplicateChkUserInfo.get("USER_ID"));
				return hm;
				
			}
			
			
			/** 빠른 등록 회원 일반 등록으로 전환 **/
			// 치과 회원 등록
			userService.insertUserInfo(userVO);
			// 치과 회원 상세 정보 등록
			userService.insertUserDetail(userVO);
			// 치과 회원 치아 상태 등록
			teethService.insertUserTeethInfo(teethInfoVO);
			// T00~T99의 측정 기록을 일반 회원의 측정 기록으로 전환
			teethService.updateMeasureValueUserId(userId, testUserId);

			/** 기존 T00~T99 계정의 정보 원복 **/
			testUserSeqNo = testUserId.substring(testUserId.length() - 2, testUserId.length());
			originalUserVO.setUserId(testUserId);
			originalUserVO.setUserName("환자고객" + testUserSeqNo);
			originalUserVO.setUserBirthday("2019-01-01");
			originalUserVO.setUserSex("");
			originalUserVO.setTeethType("");
			userService.updateUserInfo(originalUserVO);
			 // 치과에 소속되어 있는 모든 환자 목록(T00~T99도 포함)
			measuredUserList= userService.selectMeasuredUserList(departmentCd, "ASC");
			

		} catch (Exception e) {

			hm.put("code", "500");
			//if (lang.equals("ko")) {
				hm.put("msg", "서버 에러가 발생했습니다.\n관리자에게 문의해주시기 바랍니다.");
			//} else if (lang.equals("en")) {
			//	hm.put("msg", "Server Error");
			//}
			e.printStackTrace();

		}

		// 데이터 RETURN
		hm.put("measuredUserList", measuredUserList);
		
		hm.put("code", "000");
		//if (lang.equals("ko")) {
			hm.put("msg", "일반 회원으로 전환 되었습니다.");
		//} else if (lang.equals("en")) {
		//	hm.put("msg", "Success");
		//}
		return hm;

	}
	
	
	
	
	
	/**
	 * 기능   : 빠른 등록 회원 정보 삭제
	 * 작성자 : 정주현 
	 * 작성일 : 2023. 07. 12
	 */
	@PostMapping(value = {"/dentist/user/deleteQuickRegistUserInfo.do"})
	@ResponseBody
	public HashMap<String, Object> deleteQuickRegistUserInfo(@RequestBody HashMap<String, Object> paramMap) throws Exception {
		
		
		logger.debug("========== dentist.UserController ========== /dentist/user/deleteQuickRegistUserInfo.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/deleteQuickRegistUserInfo.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/deleteQuickRegistUserInfo.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/deleteQuickRegistUserInfo.do ==========");
		
		
		String lang = (String) paramMap.get("lang");
		// 하드코딩
		//if (lang == null || lang.equals("")) {
		//	lang = "ko";
		//}
		
		// 회원 아이디 리스트
		String userIdList = (String) paramMap.get("userId");
		// 회원 아이디를 배열로 분리
		String[] userId = userIdList.split("\\|"); 
		// 치과 부서 코드
		String departmentCd = null;
		
		// T00~T99 시퀀스 번호
		String testUserSeqNo = null;
		
		// 오늘 날짜 구하기 (SYSDATE)
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String measureDt = now.format(formatter);
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		UserVO originalUserVO = new UserVO();
		List<UserVO> measuredUserList = new ArrayList<UserVO>();
		
		
		try {
			
			for(int i=0; i<userId.length; i++) {
				
				if(i==0) {
					departmentCd = userId[i].substring(0, userId[i].lastIndexOf("-"))+"01";
				}
				
				// T00~T99의 측정 기록 삭제
				teethService.deleteMeasureValueUserId(userId[i], measureDt);
				
				/** 기존 T00~T99 계정의 정보 원복 **/
				testUserSeqNo = userId[i].substring(userId[i].length() - 2, userId[i].length());
				originalUserVO.setUserId(userId[i]);
				originalUserVO.setUserName("환자고객" + testUserSeqNo);
				originalUserVO.setUserBirthday("2019-01-01");
				userService.updateUserInfo(originalUserVO);
			}
			// 치과에 소속되어 있는 모든 환자 목록(T00~T99도 포함)
			measuredUserList= userService.selectMeasuredUserList(departmentCd, "ASC");
			
		} catch (Exception e) {
			
			hm.put("code", "500");
			//if (lang.equals("ko")) {
				hm.put("msg", "삭제하지 못했습니다.\n관리자에게 문의해주시기 바랍니다.");
			//} else if (lang.equals("en")) {
			//	hm.put("msg", "Server Error");
			//}
			e.printStackTrace();
			
		}
		
		// 데이터 RETURN
		hm.put("measuredUserList", measuredUserList);
		
		hm.put("code", "000");
		//if (lang.equals("ko")) {
			hm.put("msg", "삭제하였습니다.");
		//} else if (lang.equals("en")) {
		//	hm.put("msg", "Success");
		//}
		return hm;
		
	}
	
	
	
	/**
	 * 기능   : 빠른 등록 회원 정보 수정
	 * 작성자 : 정주현 
	 * 작성일 : 2023. 06. 30
	 */
	@PostMapping(value = {"/dentist/user/updateQuickRegistUserInfo.do"})
	@ResponseBody
	public HashMap<String, Object> updateQuickRegistUserInfo(@RequestBody HashMap<String, Object> paramMap) throws Exception {
		
		
		logger.debug("========== dentist.UserController ========== /dentist/user/updateQuickRegistUserInfo.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/updateQuickRegistUserInfo.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/updateQuickRegistUserInfo.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/updateQuickRegistUserInfo.do ==========");

		
		// 일반 등록 회원 아이디
		String userId = null;
		// 일반 등록 회원 이름
		String userName = null;
		// 일반 등록 회원 전화번호
		String userTelNo = null;
		// 일반 등록 회원 생년월일
		String userBirthday = null;
		// 일반 등록 회원 성별
		String userSex = null;
		// 법정대리인 동의 여부
		String agreYn = null;
		// 법정대리인 이름
		String paUserName = null;
		// 법정대리인 전화번호
		String paUserTelNo = null;
		// 회원 치아 형태
		String teethType = null;
		// 치과코드
		String dentalHospitalCd = null;
		// 치과부서 코드
		String departmentCd = null;
		
//		// 일반 등록 회원 거주 국
//		String userCountry = null;
//		// 일반 등록 회원 거주 주
//		String userState = null;
//		// 일반 등록 회원 주소 (시도, 시군구, 읍면동)
//		String sidoNm = null;
//		String sigunguNm = null;
//		String eupmyeondongNm = null;
		

		HashMap<String, Object> hm = new HashMap<String, Object>();
		UserVO userVO = new UserVO();
		List<UserVO> measuredUserList = new ArrayList<UserVO>();

		userId = (String) paramMap.get("userId");

		// Parameter = userId 값 검증 (Null 체크 및 공백 체크)
		userId = (String) paramMap.get("userId");
		if (userId == null || userId.equals("") || userId.equals(" ")) {
			hm.put("code", "401");
			hm.put("msg", "등록번호(아이디)가 전달되지 않았습니다.");
			// hm.put("msg", "There is no userId parameter");
			return hm;
		}

		
		userName = (String) paramMap.get("userName");
		userTelNo = (String) paramMap.get("userTelNo");
		userBirthday = (String) paramMap.get("userBirthday");
		userSex = (String) paramMap.get("userSex");

		// 14세 미만 회원일 경우
		agreYn = (String) paramMap.get("agreYn");
		paUserName = (String) paramMap.get("paUserName");
		paUserTelNo = (String) paramMap.get("paUserTelNo");
		
		// 회원 치아 형태
		teethType = (String) paramMap.get("teethType");
		// 치과 코드
		dentalHospitalCd = (String) paramMap.get("dentalHospitalCd");
		// 치과 부서 코드
		departmentCd = dentalHospitalCd+"01";
		
		//userCountry = (String) paramMap.get("userCountry");
		//userState = (String) paramMap.get("userState");

//		// TOKEN 검증
//		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
//		tokenValidation = jwtTokenUtil.validateToken(userAuthToken);

		if (tmpTokenValidation) {
			try {

				// 회원 기본 정보
				userVO.setUserId(userId);
				userVO.setUserName(userName);
				userVO.setUserTelNo(userTelNo);
				userVO.setUserBirthday(userBirthday);
				userVO.setUserSex(userSex);
				//userVO.setUserCountry(userCountry);
				//userVO.setUserState(userState);
				//userVO.setSidoNm(sidoNm);
				//userVO.setSigunguNm(sigunguNm);
				//userVO.setEupmyeondongNm(eupmyeondongNm);
				
				// 회원 상세 정보
				userVO.setPaUserName(paUserName);
				userVO.setPaUserTelNo(paUserTelNo);
				userVO.setAgreYn(agreYn);
				userVO.setTeethType(teethType);
				
				// 회원 기본 정보 업데이트
				userService.updateUserInfo(userVO);
				// 회원 상세 정보 업데이트
				userService.updateUserDetailInfo(userVO);
				
				// 치과에 소속되어 있는 모든 환자 목록(T00~T99도 포함)
				measuredUserList= userService.selectMeasuredUserList(departmentCd, "ASC");
				

			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "빠른 등록한 회원의 정보 수정에 실패했습니다.\n관리자에게 문의해주시기 바랍니다.");
				// hm.put("msg", "Server Error.");
				e.printStackTrace();
			}
		} else {
			hm.put("code", "400");
			hm.put("msg", "토큰이 유효하지 않습니다.\n다시 로그인 해주시기 바랍니다.");
			// hm.put("msg", "The token is not valid.");
		}
		
		// 데이터 RETURN
		hm.put("measuredUserList", measuredUserList);
		hm.put("code", "000");
		hm.put("msg", "빠른 등록 회원 정보를 수정하였습니다.");
		// hm.put("msg", "Success");
		return hm;
	}
	
	
	
	/**
	 * 기능   : 회원 비밀번호 UPDATE
	 * 작성자 : 정주현 
	 * 작성일 : 2023. 08. 08
	 * 
	 */
	@PostMapping(value = {"/dentist/user/updateUserPwd.do"})
	@ResponseBody
	public HashMap<String,Object> updateUserPwd(@RequestBody HashMap<String, String> paramMap) {
		
		
		logger.debug("========== dentist.UserController ========== /dentist/user/updateUserEmail.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/updateUserEmail.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/updateUserEmail.do ==========");
		logger.debug("========== dentist.UserController ========== /dentist/user/updateUserEmail.do ==========");
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		UserVO userVO = new UserVO();
		AuthVO authVO = new AuthVO();

		String userId = (String)paramMap.get("userId");
		String userPwd = (String)paramMap.get("userPwd");
		String userIp = (String)paramMap.get("userIp");
		
		// (Null 체크 및 공백 체크)
		if(userId == null || userId.equals("")) {
			hm.put("code", "401");
			hm.put("msg", "파라미터(아이디)가 전달되지 않았습니다.");
			return hm;
		}
		
		// 비밀번호 암호화
		AES256Util aes256Util = new AES256Util();
		userPwd = aes256Util.aesEncode(userPwd);
		
		// 회원 정보 VO
		userVO.setUserId(userId);
		userVO.setUserPwd(userPwd);
		// 회원 인증 VO
		authVO.setUserId(userId);
		authVO.setUserIp(userIp);
		
		try {
			// 회원 비밀번호 수정
			userService.updateUserPwd(userVO);
			// 회원 비밀번호 변경 이력 등록
			logService.updateUserPwdChangeHistory(authVO);
			
		} catch (Exception e) {
			hm.put("code", "500");
			hm.put("msg", "비밀번호 변경에 실패했습니다.\n관리자에게 문의해주시기 바랍니다.");
			e.printStackTrace();
		}
		
		hm.put("code", "000");
		hm.put("msg", "비밀번호가 변경되었습니다.");
		
		return hm;
	}	
	
	
	
	
	/**
	 * 기능   : 회원 이메일 업데이트
	 * 작성자 : 정주현 
	 * 작성일 : 2023. 08. 08
	 * 
	 */
	//@CrossOrigin(origins = "http://13.124.37.209:3000")
		//리액트와 연결 시 해야할 설정	
		@PostMapping(value = {"/dentist/user/updateUserEmail.do"})
		@ResponseBody
		public HashMap<String,Object> updateUserEmail(@RequestBody HashMap<String, String> paramMap) {
			
			
			logger.debug("========== dentist.UserController ========== /dentist/user/updateUserPwd.do ==========");
			logger.debug("========== dentist.UserController ========== /dentist/user/updateUserPwd.do ==========");
			logger.debug("========== dentist.UserController ========== /dentist/user/updateUserPwd.do ==========");
			logger.debug("========== dentist.UserController ========== /dentist/user/updateUserPwd.do ==========");
			
			HashMap<String,Object> hm = new HashMap<String,Object>();
			
			String userId = (String)paramMap.get("userId");
			String userEmail = (String)paramMap.get("userEmail");
			
			// (Null 체크 및 공백 체크)
			if(userId == null || userId.equals("")) {
				hm.put("code", "401");
				hm.put("msg", "파라미터(아이디)가 전달되지 않았습니다.");
				return hm;
			}
			
			try {
				// 회원 이메일 업데이트
				userService.updateUserEmail(userId, userEmail);
				
			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "이메일 수정에 실패했습니다.\n관리자에게 문의해주시기 바랍니다.");
				e.printStackTrace();
			}
			
			hm.put("code", "000");
			hm.put("msg", "이메일 주소가 변경되었습니다.");
			
			return hm;
		}
	
	
	
//	/**
//	 * 기능   : 비밀번호 찾기 (이메일 발송)
//	 * 작성자 : 정주현 
//	 * 작성일 : 2022. 06. 09
//	 */
//	@PostMapping(value = {"/dentist/user/findUserPwd.do"})
////	@PostMapping(value = {"/general/user/findUserPwd.do"})
//	@ResponseBody
//		public HashMap<String,Object> findUserPwd(@RequestBody HashMap<String, String> paramMap) {
//		
//		logger.debug("========== UserController ========== findUserPwd.do ==========");
//
//		int isExistId = 0;
//		String userId = null;
//		String emailAuthKey = null;
//		HashMap<String,Object> hm = new HashMap<String,Object>();
//		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
//		
//		
//		// Parameter :: userId 값 검증
//		userId = (String)paramMap.get("userId");
//		// (Null 체크 및 공백 체크)
//		if(userId == null || userId.equals("")) {
//			hm.put("code", "401");
//			hm.put("msg", "There is no ID.");
//			return hm;
//		}
//		
//		emailAuthKey = jwtTokenUtil.createToken(userId);
//		
//		try {
//			// 아이디 중복 체크 :: ID가 없을 경우 0, ID가 있을 경우 1
//			isExistId = userService.duplicateChkId(userId);
//		} catch (Exception e) {
//			hm.put("code", "500");
//			hm.put("msg", "Server Error");
//			e.printStackTrace();
//		}
//
//		if (isExistId == 1) { // 아이디가 있는 경우 메일 발송
//			try {
//				
//				// 이메일 안에 비밀번호 변경 url을 전송하도록 함
//				mailAuthService.sendResetPasswordMail(userId, emailAuthKey);
//				
//			} catch (Exception e) {
//				hm.put("code", "500");
//				hm.put("msg", "Server Error");
//				e.printStackTrace();
//				return hm;
//			}
//			hm.put("code", "000");
//			hm.put("msg", "Mail Sent Completed.");
//		} else { // 아이디가 없을 경우 JSON code 및 msg RETURN
//			hm.put("code", "405");
//			hm.put("msg", "This ID does not exist.");
//		}
//		return hm;
//	}
	
	
}
