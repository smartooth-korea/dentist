package co.smartooth.app.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import co.smartooth.app.service.AuthService;
import co.smartooth.app.service.LogService;
import co.smartooth.app.service.OrganService;
import co.smartooth.app.service.UserService;
import co.smartooth.app.utils.AES256Util;
import co.smartooth.app.utils.CryptUtil;
import co.smartooth.app.utils.JwtTokenUtil;
import co.smartooth.app.vo.AuthVO;
import co.smartooth.app.vo.UserVO;

/**
 * 작성자 : 정주현 
 * 작성일 : 2023. 06. 28
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01
 * 
 * SCHOOL_CODE, SCHOOL_NAME에 관련하여 전체적으로 변경해야함
 */

@Controller
public class LoginController {

	@Autowired(required = false)
	private LogService logService;

	@Autowired(required = false)
	private AuthService authService;

	@Autowired(required = false)
	private UserService userService;

	@Autowired(required = false)
	private OrganService organService;
	

	
	/**
	 * 기능   : 로그인 API
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 05. 16
	 * 수정일 : 2023. 08. 09
	 */
	@PostMapping(value = {"/dentist/login.do"})
	@ResponseBody
	public HashMap<String,Object> login(@RequestBody HashMap<String, Object> paramMap) {
       
		
	    Logger logger = LoggerFactory.getLogger(getClass());
	    
	    logger.debug("========== dentist.LoginController ========== /dentist/login.do ==========");
	    logger.debug("========== dentist.LoginController ========== /dentist/login.do ==========");
	    logger.debug("========== dentist.LoginController ========== /dentist/login.do ==========");
	    
	    CryptUtil cryptUtil = new CryptUtil();
	    
	    // String lang = (String)paramMap.get("lang");
	    // 하드코딩
	    //if(lang == null || lang.equals("")) {
	    //	lang = "ko";
	    //}
	    
	    String userId = null;
	    String userName = null;
		String userPwd = null;
		String userEmail = null;
		String userType = null;
		String loginIp = null;
		// 치과 코드
		String dentalHospitalCd = null;
		// 부서 코드
		String departmentCd = null;
		// 치과 이름
		String dentalHospitalNm = null;
		// 정렬
		String orderBy = null;
		
		int loginChkByIdPwd = 0;
		int isIdExist = 0;
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		List<HashMap<String, Object>> measureOrganList = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> departmentList = new  ArrayList<HashMap<String, Object>>();
		List<UserVO> measuredUserList = new ArrayList<UserVO>();
		List<HashMap<String, Object>> dentistList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> dentalHospitalInfo = new HashMap<String, Object>();
		List<HashMap<String, Object>> infomationAgreeUserList = new ArrayList<HashMap<String, Object>>();
		
		// 로그인 인증 VO
		AuthVO authVO = new AuthVO();
		UserVO userVO = new UserVO();
		
		// 오늘 일자 계산
		Date tmpDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		String sysDate = sdf.format(tmpDate);
		
		userId= (String)paramMap.get("userId");
		loginIp = (String)paramMap.get("loginIp"); 
		userPwd = (String)paramMap.get("userPwd");
		
		//AES256Util aes256Util = new AES256Util();
		//userPwd = aes256Util.aesEncode(userPwd);
		
		if(userPwd.equals("false")) { // 암호화에 실패할 경우
			hm.put("code", "500");
			//if(lang.equals("ko")) {
				hm.put("msg", "암호화 중 실패하였습니다.\n관리자에게 문의해주시기 바랍니다.");
			//}else if(lang.equals("en")) {
			//	hm.put("msg", "Server Error");
			//}
			return hm;
		}
		
		// 회원 정보 VO
		userVO.setUserId(userId);
		// 회원 인증 VO
		authVO.setUserId(userId);
		authVO.setUserPwd(userPwd);
		authVO.setLoginDt(sysDate);
		authVO.setUserIp(loginIp);
		
		
		try {
			// 로그인 확인
			loginChkByIdPwd = authService.loginChkByIdPwd(authVO);
			if(loginChkByIdPwd == 0){ // 0일 경우는 Database에 ID와 비밀번호가 틀린 것
				isIdExist = authService.isIdExist(authVO.getUserId());
				if(isIdExist == 0) { // ID가 존재하지 않을 경우
					//if(lang.equals("ko")) {
						hm.put("msg", "해당 아이디가 존재하지 않습니다");
					//}else if(lang.equals("en")) {
					//	hm.put("msg", "This ID does not exist");
					//}
				}else { // PWD가 틀렸을 경우
					hm.put("code", "406");
					//if(lang.equals("ko")) {
						hm.put("msg", "비밀번호가 틀렸습니다");
					//}else if(lang.equals("en")) {
					//	hm.put("msg", "The password is wrong");
					//}
				}
			}else {
				
				orderBy = (String)paramMap.get("order");
				if(orderBy == null) {
					orderBy = "ASC";
				}
				
				// ID와 PWD가 검증된 이후 JWT 토큰과 회원의 정보를 제공하고 LOG를 INSERT
				// JWT token 발행
				//JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
				//userAuthToken = jwtTokenUtil.createToken(authVO);
				
				// 로그인 일자 업데이트
				logService.updateLoginDt(authVO);
				
				userVO = userService.selectUserInfo(userId);
				userType = userVO.getUserType();
				userName = userVO.getUserName();
				userEmail = userVO.getUserEmail();
				
				// 로그인 시 등록 되어 있는 측정 예정 혹은 측정 완료 기관 목록 조회 (SYSDATE 기준)
				measureOrganList = organService.selectMeasureOrganList(userId, userType);
				
				
				// 치과 이름
				dentalHospitalNm = (String)measureOrganList.get(0).get("SCHOOL_NAME");
				// 치과 코드
				dentalHospitalCd = (String) measureOrganList.get(0).get("SCHOOL_CODE");
				
				// 치과 부서 목록 조회 (1개밖에없음)
				departmentList = organService.selectDepartmentList(dentalHospitalCd);		
				// 치과 부서 코드
				departmentCd = (String) departmentList.get(0).get("CLASS_CODE");
				// 치과 소속 환자 목록
				measuredUserList= userService.selectMeasuredUserList(departmentCd, orderBy);
				
				// 개인정보 제공을 동의한 환자 목록(SYSDATE)
				infomationAgreeUserList = userService.selectInfomationAgreeUserList(dentalHospitalNm);
				
				// 치과 소속 의사 목록
				dentistList = userService.selectDentistList(dentalHospitalCd);
				// 치과 정보
				dentalHospitalInfo = organService.selectDentalHospitalInfo(dentalHospitalCd);
				
				// 데이터 RETURN
				// hm.put("userAuthToken", userAuthToken);
				hm.put("userName", userName);
				hm.put("userEmail", userEmail);
				hm.put("schoolName", dentalHospitalNm);
				hm.put("schoolCode", dentalHospitalCd);
				hm.put("classCode", departmentCd);
				hm.put("measuredUserList", measuredUserList);
				hm.put("dentistList", dentistList);
				hm.put("dentalHospitalInfo", dentalHospitalInfo);
				hm.put("infomationAgreeUserList", infomationAgreeUserList);
				
				// 메시지 RETURN
				hm.put("code", "000");
				//if(lang.equals("ko")) {
					hm.put("msg", "로그인 성공");
				//}else if(lang.equals("en")) {
				//	hm.put("msg", "Login Success");
				//}
				
				// 로그인 Log 등록
				logService.insertUserLoginHistory(authVO);
			}
		} catch (Exception e) {
			hm.put("code", "500");
			//if(lang.equals("ko")) {
				hm.put("msg", "로그인에 실패하였습니다.\n관리자에게 문의해주시기 바랍니다.");
			//}else if(lang.equals("en")) {
			//	hm.put("msg", "Server Error");
			//}
			e.printStackTrace();
		}
		return hm;
	}
	
	
	
}
