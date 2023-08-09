package co.smartooth.app.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import co.smartooth.app.service.MailAuthService;
import co.smartooth.app.service.UserService;
import co.smartooth.app.utils.AES256Util;
import co.smartooth.app.utils.JwtTokenUtil;

/**
 * 기능 : 유틸리티 컨트롤러
 * 작성자 : 정주현 
 * 작성일 : 2023. 08. 08
 * @RestController를 쓰지 않는 이유는 몇 안되는 mapping에 jsp를 반환해줘야하는게 있으므로 @Controller를 사용한다.
 * @RestAPI로 반환해야할 경우 @ResponseBody를 사용하여 HashMap으로 return 해준다.
 */
@Controller
public class UtilsController {

	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired(required = false)
	private UserService userService;
	
	
	@Autowired(required = false)
	private MailAuthService mailAuthService;
	
	
	// @Autowired(required = false)
	// private RegistService registService;
	
	
	
	/**
	 * 기능   : 비밀번호 재설정(이메일 발송)
	 * 작성자 : 정주현 
	 * 작성일 : 2023. 08. 08
	 * 
	 */
	@PostMapping(value = {"/dentist/utils/sendResetPwdMail.do"})
	@ResponseBody
	public HashMap<String, Object> sendResetPwdMail(@RequestBody HashMap<String, String> paramMap) {

		
		logger.debug("========== Dentist.UtilsController ========== /dentist/utils/sendResetPwdMail.do ==========");
		logger.debug("========== Dentist.UtilsController ========== /dentist/utils/sendResetPwdMail.do ==========");
		logger.debug("========== Dentist.UtilsController ========== /dentist/utils/sendResetPwdMail.do ==========");
		logger.debug("========== Dentist.UtilsController ========== /dentist/utils/sendResetPwdMail.do ==========");

		
		int isExistId = 0;
		
		String userId = (String) paramMap.get("userId");
		String userEmail = (String) paramMap.get("userEmail");
		String emailAuthKey = null;
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

		
		// (Null 체크 및 공백 체크)
		if (userId == null || userId.equals("")) {
			hm.put("code", "401");
			hm.put("msg", "파라미터(아이디)가 전달되지 않았습니다.");
			return hm;
		}

		// 메일 인증키 생성
		emailAuthKey = jwtTokenUtil.createToken(userId);

		try {
			// 아이디 중복 체크 :: ID가 없을 경우 0, ID가 있을 경우 1
			isExistId = userService.duplicateChkId(userId);
		} catch (Exception e) {
			hm.put("code", "500");
			hm.put("msg", "아이디 중복 체크 중 문제가 발생하였습니다.\n관리자에게 문의해주시기 바랍니다.");
			e.printStackTrace();
		}

		if (isExistId == 1) { // 아이디가 있는 경우 메일 발송
			try {
				// 이메일 안에 비밀번호 변경 url을 전송하도록 함
				mailAuthService.sendResetPasswordMail(userId, userEmail, emailAuthKey);

			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "인증 메일 발송에 실패하였습니다.\n관리자에게 문의해주시기 바랍니다.");
				e.printStackTrace();
				return hm;
			}

		} else { // 아이디가 없을 경우 JSON code 및 msg RETURN
			hm.put("code", "405");
			hm.put("msg", "해당 아이디가 존재하지 않습니다.");
		}

		hm.put("code", "000");
		hm.put("msg", "인증 메일을 발송하였습니다.");

		return hm;
	}	
	
	
	
	/**
	 * 기능   : 비밀번호 초기화 전 메일 인증
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 06. 02
	 * 수정일 : 2022. 08. 01
	 */
	@GetMapping(value = {"/dentist/utils/resetUserPwd.do"})
	  public String userList(HttpServletRequest request, Model model) throws Exception {
		
		logger.debug("========== Dentist.UserController ========== /dentist/utils/resetUserPwd.do ==========");
		logger.debug("========== Dentist.UserController ========== /dentist/utils/resetUserPwd.do ==========");
		logger.debug("========== Dentist.UserController ========== /dentist/utils/resetUserPwd.do ==========");
		logger.debug("========== Dentist.UserController ========== /dentist/utils/resetUserPwd.do ==========");

		
		boolean validation = false;
		String userId = null;
		String emailAuthKey = null;
		
		userId = request.getParameter("userId");
		emailAuthKey = request.getParameter("emailAuthKey");
		
		// 복호화
		AES256Util aes256Util = new AES256Util();
		userId = aes256Util.aesDecode(userId);
		
		// 토큰 검증
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		validation = jwtTokenUtil.validateToken(emailAuthKey);
		model.addAttribute("userId", userId);
		
		if(validation) {
		    return "/utils/resetUserPwd";
		    // React 사용 시 필수
		    // return "redirect:http://13.124.37.209:3000/app/user/resetUserPwd.jsp.do?userId="+userId;
		}else {
			return "/error/500/mailAuth";
		}
	  }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	/**
//	 * 기능   : 비밀번호 암호화 및 복호화
//	 * 작성자 : 정주현 
//	 * 작성일 : 2022. 08. 24
//	 * 			 APP에서 JSON형식으로 전달 받은 회원의 ID와 비밀번호를 확인 후 JSON으로 반환
//	 */
//	@PostMapping(value = {"/premium/utils/passwordCrypto.do"})
//	@ResponseBody
//	public HashMap<String,Object> passwordCrypto(@RequestBody HashMap<String, Object> paramMap) {
//       
//		logger.debug("========== UtilController ========== passwordCrypto.do ==========");
//	    
//	    HashMap<String, Object> hm = new HashMap<String, Object>();
//	    
//		String originalPwd = null;
//		String decodePwd = null;
//		String encodePwd = null;
//		
//		// 비밀번호 암호화 
//		AES256Util aes256Util = new AES256Util();
//		originalPwd = (String)paramMap.get("userPwd");
//		decodePwd = aes256Util.aesDecode(originalPwd);
//		encodePwd = aes256Util.aesEncode(originalPwd);
//		
//		System.out.println("USER_PWD Original >>>>>>>>>>>>>>>> "+ originalPwd);
//		System.out.println("USER_PWD Encoding >>>>>>>>>>>>>>>> "+ encodePwd);
//		System.out.println("USER_PWD Decoding >>>>>>>>>>>>>>>> "+ decodePwd);
//		
//		hm.put("originalPwd", originalPwd);
//		hm.put("encodePwd", encodePwd);
//		hm.put("decodePwd", decodePwd);
//		
//		return hm;
//	}
	
	
	
//	 /**
//     * 기능   : 학생 회원 충치 개수 업데이트 (수동)
//     * 작성자 : 정주현 
//     * 작성일 : 2022. 08. 26
//     * 수정일 : 
//     */
//	@PostMapping(value = {"/premium/utils/updateCavityCntKids.do"})
//	@ResponseBody
//	public void updateCavityCntKids(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) throws Exception {
//		
//		logger.debug("========== UtilController ========== updateCavityCnt.do ==========");
//		
//		String userId = null;
//		String startDt = null;
//		String endDt = null;
//
//		// 20개 치아 측정 값 
//		int[] teethValue = new int[20];
//		
//		Integer cavityCaution = 0;
//		Integer cavityDanger = 0;
//			    
//		// 정상 수치 0~12 이하 갯수
//		int cavityNormalCnt = 0;
//		// 주의 수치 13~24 이하 갯수
//		int cavityCautionCnt = 0;
//	    // 위험 수치 25이상 갯수
//	    int cavityDangerCnt = 0;
//		
//		
//		List<WebTeethMeasureVO> userTeethValues = new ArrayList<WebTeethMeasureVO>();
//		
//		WebTeethMeasureVO WebTeethMeasureVO = new WebTeethMeasureVO();
//
//		HashMap<String,Integer> cavityLevel = new HashMap<String,Integer>();
//		
//		// Parameter = userId 값 검증 (Null 체크 및 공백 체크)
//		userId= (String)paramMap.get("userId");
//		startDt= (String)paramMap.get("startDt");
//		endDt= (String)paramMap.get("endDt");
//		
//		WebTeethMeasureVO.setUserId(userId);
//		WebTeethMeasureVO.setStartDt(startDt);
//		WebTeethMeasureVO.setEndDt(endDt);
//		
//			
//		try {
//			
//			// 모든 치아에 대한 조회 값 (오늘의 값이 없을 경우 최근 값으로 불러와야함)
//			userTeethValues = teethService.selectUserTeethMeasureValue(WebTeethMeasureVO);
//			
//			// CAVITY_LEVEL 분류 부분 - 충치 단계별 수치 조회
//			cavityLevel = teethService.selectCavityLevel();
//			
//			teethValue[0] = userTeethValues.get(0).getT04();
//			teethValue[1] = userTeethValues.get(0).getT05();
//			teethValue[2] = userTeethValues.get(0).getT06();
//			teethValue[3] = userTeethValues.get(0).getT07();
//			teethValue[4] = userTeethValues.get(0).getT08();
//			teethValue[5] = userTeethValues.get(0).getT09();
//			teethValue[6] = userTeethValues.get(0).getT10();
//			teethValue[7] = userTeethValues.get(0).getT11();
//			teethValue[8] = userTeethValues.get(0).getT12();
//			teethValue[9] = userTeethValues.get(0).getT13();
//			teethValue[10] = userTeethValues.get(0).getT20();
//			teethValue[11] = userTeethValues.get(0).getT21();
//			teethValue[12] = userTeethValues.get(0).getT22();
//			teethValue[13] = userTeethValues.get(0).getT23();
//			teethValue[14] = userTeethValues.get(0).getT24();
//			teethValue[15] = userTeethValues.get(0).getT25();
//			teethValue[16] = userTeethValues.get(0).getT26();
//			teethValue[17] = userTeethValues.get(0).getT27();
//			teethValue[18] = userTeethValues.get(0).getT28();
//			teethValue[19] = userTeethValues.get(0).getT29();
//			
//			
//			cavityCaution = Integer.parseInt(String.valueOf(cavityLevel.get("CAVITY_CAUTION")));
//			cavityDanger = Integer.parseInt(String.valueOf(cavityLevel.get("CAVITY_DANGER")));
//
//			
//			for (int i = 0; i < teethValue.length; i++) {
//				
//				System.out.println("teethValue[i] >> " + teethValue[i]);
//				if (teethValue[i] < cavityCaution) { 														// 0~12 정상 치아
//					cavityNormalCnt++;
//				} else if (teethValue[i] >= cavityCaution && teethValue[i] < cavityDanger) {	// 13~24 주의
//					cavityCautionCnt++;
//				} else if (teethValue[i] >= cavityDanger) {	// 25~ 충치
//					cavityDangerCnt++;
//				}
//			}
//			
//			WebTeethMeasureVO.setCavityNormal(cavityNormalCnt);
//			WebTeethMeasureVO.setCavityCaution(cavityCautionCnt);
//			WebTeethMeasureVO.setCavityDanger(cavityDangerCnt);
//			
//			userTeethValues.get(0).setCavityDanger(cavityDangerCnt);
//			
//			// ST_STUDENT_USER_DETAIL 테이블에 CavityCnt 업데이트
//			WebTeethMeasureVO.setMeasureDt(startDt);
//			teethService.updateUserCavityCntByMeasureDt(WebTeethMeasureVO);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
	
	
	
//	/**
//     * 기능   : 학생 회원 충치 개수 업데이트 (수동)
//     * 작성자 : 정주현 
//     * 작성일 : 2022. 08. 26
//     * 수정일 : 
//     */
//	@PostMapping(value = {"/premium/utils/updateCavityCntNormal.do"})
//	@ResponseBody
//	public void updateCavityCntNormal(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) throws Exception {
//		
//		logger.debug("========== UtilController ========== updateCavityCnt.do ==========");
//		
//		String userId = null;
//		String startDt = null;
//		String endDt = null;
//
//		// 32개 치아 측정 값 
//		int[] teethValue = new int[32];
//		
//		Integer cavityCaution = 0;
//		Integer cavityDanger = 0;
//			    
//		// 정상 수치 0~12 이하 갯수
//		int cavityNormalCnt = 0;
//		// 주의 수치 13~24 이하 갯수
//		int cavityCautionCnt = 0;
//	    // 위험 수치 25이상 갯수
//	    int cavityDangerCnt = 0;
//		
//		
//		List<WebTeethMeasureVO> userTeethValues = new ArrayList<WebTeethMeasureVO>();
//		
//		WebTeethMeasureVO WebTeethMeasureVO = new WebTeethMeasureVO();
//
//		HashMap<String,Integer> cavityLevel = new HashMap<String,Integer>();
//		
//		// Parameter = userId 값 검증 (Null 체크 및 공백 체크)
//		userId= (String)paramMap.get("userId");
//		startDt= (String)paramMap.get("startDt");
//		endDt= (String)paramMap.get("endDt");
//		
//		WebTeethMeasureVO.setUserId(userId);
//		WebTeethMeasureVO.setStartDt(startDt);
//		WebTeethMeasureVO.setEndDt(endDt);
//		WebTeethMeasureVO.setMeasureDt(endDt);
//		
//			
//		try {
//			
//			// 모든 치아에 대한 조회 값 (오늘의 값이 없을 경우 최근 값으로 불러와야함)
//			userTeethValues = teethService.selectUserTeethMeasureValue(WebTeethMeasureVO);
//			
//			// CAVITY_LEVEL 분류 부분 - 충치 단계별 수치 조회
//			cavityLevel = teethService.selectCavityLevel();
//			
//			teethValue[0] = userTeethValues.get(0).getT01();
//			teethValue[1] = userTeethValues.get(0).getT02();
//			teethValue[2] = userTeethValues.get(0).getT03();
//			teethValue[3] = userTeethValues.get(0).getT04();
//			teethValue[4] = userTeethValues.get(0).getT05();
//			teethValue[5] = userTeethValues.get(0).getT06();
//			teethValue[6] = userTeethValues.get(0).getT07();
//			teethValue[7] = userTeethValues.get(0).getT08();
//			teethValue[8] = userTeethValues.get(0).getT09();
//			teethValue[9] = userTeethValues.get(0).getT10();
//			teethValue[10] = userTeethValues.get(0).getT11();
//			teethValue[11] = userTeethValues.get(0).getT12();
//			teethValue[12] = userTeethValues.get(0).getT13();
//			teethValue[13] = userTeethValues.get(0).getT14();
//			teethValue[14] = userTeethValues.get(0).getT15();
//			teethValue[15] = userTeethValues.get(0).getT16();
//			teethValue[16] = userTeethValues.get(0).getT17();
//			teethValue[17] = userTeethValues.get(0).getT18();
//			teethValue[18] = userTeethValues.get(0).getT19();
//			teethValue[19] = userTeethValues.get(0).getT20();
//			teethValue[20] = userTeethValues.get(0).getT21();
//			teethValue[21] = userTeethValues.get(0).getT22();
//			teethValue[22] = userTeethValues.get(0).getT23();
//			teethValue[23] = userTeethValues.get(0).getT24();
//			teethValue[24] = userTeethValues.get(0).getT25();
//			teethValue[25] = userTeethValues.get(0).getT26();
//			teethValue[26] = userTeethValues.get(0).getT27();
//			teethValue[27] = userTeethValues.get(0).getT28();
//			teethValue[28] = userTeethValues.get(0).getT29();
//			teethValue[29] = userTeethValues.get(0).getT30();
//			teethValue[30] = userTeethValues.get(0).getT31();
//			teethValue[31] = userTeethValues.get(0).getT32();
//			
//			
//			cavityCaution = Integer.parseInt(String.valueOf(cavityLevel.get("CAVITY_CAUTION")));
//			cavityDanger = Integer.parseInt(String.valueOf(cavityLevel.get("CAVITY_DANGER")));
//
//			
//			for (int i = 0; i < teethValue.length; i++) {
//				
//				System.out.println("teethValue[i] >> " + teethValue[i]);
//				if (teethValue[i] < cavityCaution) { 														// 0~12 정상 치아
//					cavityNormalCnt++;
//				} else if (teethValue[i] >= cavityCaution && teethValue[i] < cavityDanger) {	// 13~24 주의
//					cavityCautionCnt++;
//				} else if (teethValue[i] > cavityDanger) {	// 25~ 충치
//					cavityDangerCnt++;
//				}
//			}
//
//			WebTeethMeasureVO.setCavityNormal(cavityNormalCnt);
//			WebTeethMeasureVO.setCavityCaution(cavityCautionCnt);
//			WebTeethMeasureVO.setCavityDanger(cavityDangerCnt);
//			
//			userTeethValues.get(0).setCavityDanger(cavityDangerCnt);
//			
//			// ST_STUDENT_USER_DETAIL 테이블에 CavityCnt 업데이트
//			teethService.updateUserCavityCntByMeasureDt(WebTeethMeasureVO);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
//	/**
//	 * 기능   : 비밀번호 암호화 및 복호화
//	 * 작성자 : 정주현 
//	 * 작성일 : 2022. 08. 24
//	 * 			 APP에서 JSON형식으로 전달 받은 회원의 ID와 비밀번호를 확인 후 JSON으로 반환
//	 */
//	@PostMapping(value = {"/premium/utils/selectTeethStatusInfo.do"})
//	@ResponseBody
//	public HashMap<String,Object> selectTeethStatusInfo(@RequestBody HashMap<String, Object> paramMap) {
//		
//		logger.debug("========== UtilController ========== passwordCrypto.do ==========");
//		
//		HashMap<String, Object> hm = new HashMap<String, Object>();
//		
//		String teethStatus = null;
//		String teethStatusStr = null;
//		String loosingToothNoStr = "";
//		String treatedToothNoStr = "";
//		
//		ArrayList<Integer> losingToothArray = new ArrayList<Integer>();
//		ArrayList<Integer> treatedToothArray = new ArrayList<Integer>();
//		
//		int treatedCnt = 0;
//		int cameOutCnt = 0;
//		
//		String[] teethStatusArray = new String[32]; 
//		
//		// 치아 상태 정보 파라미터
//		teethStatus = (String)paramMap.get("teethStatus");
//		
//		teethStatusArray = teethStatus.split("\\|");
//		
//		for(int i=0 ; i<teethStatusArray.length; i++) {
//		
//			// 빠진 치아
//			if(teethStatusArray[i].equals("200")) {
//				cameOutCnt++;
//				losingToothArray.add(i+1);
//				
//			}
//			// 치료받은 치아
//			if(teethStatusArray[i].equals("300")) {
//				treatedCnt++;
//				treatedToothArray.add(i+1);
//			}
//
//		}
//		
//		for(Integer losingTooth : losingToothArray) {
//			
//			switch (losingTooth) {
//	            case 1:  loosingToothNoStr += "영구치1,";
//	                     break;
//	            case 2:  loosingToothNoStr += "영구치2,";
//	                     break;
//	            case 3:  loosingToothNoStr += "16,";
//	                     break;
//	            case 4:  loosingToothNoStr += "55,";
//	                     break;
//	            case 5:  loosingToothNoStr += "54,";
//	                     break;
//	            case 6:  loosingToothNoStr += "53,";
//	                     break;
//	            case 7:  loosingToothNoStr += "52,";
//	                     break;
//	            case 8:  loosingToothNoStr += "51,";
//	                     break;
//	            case 9:  loosingToothNoStr += "61,";
//	                     break;
//	            case 10: loosingToothNoStr += "62,";
//	                     break;
//	            case 11: loosingToothNoStr += "63,";
//	                     break;
//	            case 12: loosingToothNoStr += "64,";
//	                     break;
//	            case 13: loosingToothNoStr += "65,";
//	            		break;
//	            case 14: loosingToothNoStr += "26,";
//	            		break;
//	            case 15: loosingToothNoStr += "영구치15,";
//	            		break;
//	            case 16: loosingToothNoStr += "영구치16,";
//	            		break;
//	            case 17: loosingToothNoStr += "영구치17,";
//	            		break;
//	            case 18: loosingToothNoStr += "영구치18,";
//	            		break;
//	            case 19: loosingToothNoStr += "36,";
//	            		break;
//	            case 20: loosingToothNoStr += "75,";
//	            		break;
//	            case 21: loosingToothNoStr += "74,";
//	            		break;
//	            case 22: loosingToothNoStr += "73,";
//	            		break;
//	            case 23: loosingToothNoStr += "72,";
//	            		break;
//	            case 24: loosingToothNoStr += "71,";
//	            		break;
//	            case 25: loosingToothNoStr += "81,";
//	            		break;
//	            case 26: loosingToothNoStr += "82,";
//	            		break;
//	            case 27: loosingToothNoStr += "83,";
//	            		break;
//	            case 28: loosingToothNoStr += "84,";
//	            		break;
//	            case 29: loosingToothNoStr += "85,";
//	            		break;
//	            case 30: loosingToothNoStr += "46,";
//	            		break;
//	            case 31: loosingToothNoStr += "영구치31,";
//	            		break;
//	            case 32: loosingToothNoStr += "영구치32,";
//	            		break;
//			}
//		
//		
//		
//		for(Integer treatedTooth : treatedToothArray) {
//			
//			switch (treatedTooth) {
//	            case 1:  treatedToothNoStr += "영구치1,";
//	                     break;
//	            case 2:  treatedToothNoStr += "영구치2,";
//	                     break;
//	            case 3:  treatedToothNoStr += "16,";
//	                     break;
//	            case 4:  treatedToothNoStr += "55,";
//	                     break;
//	            case 5:  treatedToothNoStr += "54,";
//	                     break;
//	            case 6:  treatedToothNoStr += "53,";
//	                     break;
//	            case 7:  treatedToothNoStr += "52,";
//	                     break;
//	            case 8:  treatedToothNoStr += "51,";
//	                     break;
//	            case 9:  treatedToothNoStr += "61,";
//	                     break;
//	            case 10: treatedToothNoStr += "62,";
//	                     break;
//	            case 11: treatedToothNoStr += "63,";
//	                     break;
//	            case 12: treatedToothNoStr += "64,";
//	                     break;
//	            case 13: treatedToothNoStr += "65,";
//	            		break;
//	            case 14: treatedToothNoStr += "26,";
//	            		break;
//	            case 15: treatedToothNoStr += "영구치15,";
//	            		break;
//	            case 16: treatedToothNoStr += "영구치16,";
//	            		break;
//	            case 17: treatedToothNoStr += "영구치17,";
//	            		break;
//	            case 18: treatedToothNoStr += "영구치18,";
//	            		break;
//	            case 19: treatedToothNoStr += "36,";
//	            		break;
//	            case 20: treatedToothNoStr += "75,";
//	            		break;
//	            case 21: treatedToothNoStr += "74,";
//	            		break;
//	            case 22: treatedToothNoStr += "73,";
//	            		break;
//	            case 23: treatedToothNoStr += "72,";
//	            		break;
//	            case 24: treatedToothNoStr += "71,";
//	            		break;
//	            case 25: treatedToothNoStr += "81,";
//	            		break;
//	            case 26: treatedToothNoStr += "82,";
//	            		break;
//	            case 27: treatedToothNoStr += "83,";
//	            		break;
//	            case 28: treatedToothNoStr += "84,";
//	            		break;
//	            case 29: treatedToothNoStr += "85,";
//	            		break;
//	            case 30: treatedToothNoStr += "46,";
//	            		break;
//	            case 31: treatedToothNoStr += "영구치31,";
//	            		break;
//	            case 32: treatedToothNoStr += "영구치32,";
//	            		break;
//				}
//			
//			}
//		}
//		
//		loosingToothNoStr = loosingToothNoStr.substring(0, loosingToothNoStr.length()-1);
//		treatedToothNoStr = treatedToothNoStr.substring(0, treatedToothNoStr.length()-1);
//		
//		hm.put("loosingToothNoStr", loosingToothNoStr);
//		hm.put("treatedToothNoStr", treatedToothNoStr);
//		return hm;
//	}
	
	
	
//	/**
//	 * 기능   : 회원 일괄 등록
//	 * 작성자 : 정주현 
//	 * 작성일 : 2022. 10. 05
//	 * 비고 : 추 후 미국이나 해외에 관련 되어서 STATE에 대한 정보도 같이 넣을 수 있도록 변경해야함
//	 * 
//	 * 수정일 : 
//	 */
//	@PostMapping(value = {"/premium/utils/csvRegistUsers.do"})
//	@ResponseBody
//	public void registUsers(@RequestParam("file") MultipartFile file) throws Exception {
//		
//		
//		logger.debug("========== UtilController ========== registUsers.do ==========");
//		
//		
//		int classSeqNo = 0;
//		int tcSeqNo = 0;
//		int stSeqNo = 0;
//		int paSeqNo = 0;
//		
//		String countryCd = null;
//		String sidoCd = null;
//		String ggemdCd = null;
//		
//		
//		
//		
//		// 확장자이름
//		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
//		
//		// xlsx, xls 엑셀 확장자만 업로드 할 수 있도록 설정
//		if (!extension.equals("xlsx") && !extension.equals("xls") && !extension.equals("cvs")) {
//		      throw new IOException("엑셀파일만 업로드 해주세요.");
//	    } 
//		
//		Workbook workbook = null;
//		if (extension.equals("xlsx")) {
//	      workbook = new XSSFWorkbook(file.getInputStream());
//	    } else if (extension.equals("xls")) {
//	      workbook = new HSSFWorkbook(file.getInputStream());
//	    }
//		
//		
//		/**
//		 ** 첫번째 시트 (기관정보)
//		 **/ 
//		
//		Sheet schInfoworksheet = workbook.getSheetAt(0);
//		
//		// 기관 정보 입력
//		// i = 1 부터 인것은 맨 윗줄을 읽지 않는다.
//		for (int i = 1; i < schInfoworksheet.getPhysicalNumberOfRows(); i++) {
//
//			// 엑셀에서 ROW가 여러 줄(반으로 인해서) 일 경우 첫번째만 DB에 입력되도록 if문 사용
//			if(i == 1) {
//				String schNm = null;
//				String schType = null;
//				String classNm = null;
//				String countryNm = null;
//				String siooNm = null;
//				String sigunguNm = null;
//				String eupMyeonDongNm = null;
//				String schCd = null;
//				int schSeqNo = 0;
//				
//				
//				WebLocationVO webLocationVO = new WebLocationVO();
//				WebSeqNoVO webSeqNoVO = new WebSeqNoVO();
//	
//				Row row = schInfoworksheet.getRow(i);
//				
//				schNm = row.getCell(0).getStringCellValue();
//				schType = row.getCell(1).getStringCellValue();
//				classNm = row.getCell(2).getStringCellValue();
//				countryNm = row.getCell(3).getStringCellValue();
//				siooNm = row.getCell(4).getStringCellValue();
//				sigunguNm = row.getCell(5).getStringCellValue();
//	
//				
//				if(schType.contains("어린") || schType.contains("유아") || schType.contains("유치")) {
//					schType = "KG";
//				}else if(schType.contains("초등")) {
//					schType = "EL";
//				}else if(schType.contains("중")) {
//					schType = "MD";
//				}else if(schType.contains("고등")) {
//					schType = "HI";
//				}
//				
//				// 읍면동에서 예를들어 신정동 하나로 입력하는 경우가 발생, 그럴 경우 동을 제외하고 신정만 읽어서 조회해야함
//				eupMyeonDongNm = row.getCell(6).getStringCellValue();
//				
//				// 기관 코드를 생성하기 위해 나라, 시도, 시군구, 읍면동 코드 조회
//				countryCd = webRegistService.selectCountryCd(countryNm);
//				sidoCd = webRegistService.selectSiDoCd(siooNm);
//				
//				
//				webLocationVO.setSidoCd(sidoCd);
//				webLocationVO.setSigunguNm(sigunguNm);
//				if(eupMyeonDongNm.matches(".*\\d.*")) {
//					webLocationVO.setEupmyeondongNm(eupMyeonDongNm);
//				}else {
//					webLocationVO.setEupmyeondongNm(eupMyeonDongNm.replaceAll("동", ""));
//				}
//				
//				
//				// 지역 정보
//				webLocationVO = webRegistService.selectLocationInfo(webLocationVO);
//				
//				// 시군구읍면동 코드
//				ggemdCd = webLocationVO.getSggemdCd();
//				
//				// 시퀀스 조회
//				webSeqNoVO = webRegistService.selectSeqNoAll();
//				
//				// 기관 코드 시퀀스
//				schSeqNo = webSeqNoVO.getSchSeqNo();
//				// 기관 코드 생성 :: 나라코드+기관타입+시도코드+시군구읍동면코드+0001
//				schCd = countryCd+schType+sidoCd+ggemdCd+String.format("%02d", schSeqNo);
//				// 반 코드 시퀀스
//				classSeqNo = webSeqNoVO.getClassSeqNo();
//				
//				// 기관 코드와 기관 이름 INSERT
////				webRegistService.insertSchInfo(schCd, schNm);
//				// 기관 코드 시퀀스 증가
////				webRegistService.updateSeqNo("SCH_SEQ_NO" , Integer.toString(schSeqNo+1));
//				
//				// 기관 이름 등록
////				webRegistService.insertClassInfo(String.format("%07d", classSeqNo), classNm);
//				// 반 코드 시퀀스 증가
////				webRegistService.updateSeqNo("CLASS_SEQ_NO" , Integer.toString(classSeqNo++));
//				
//				
//				
//				// 조회해온 시퀀스를 변수에 담아 저장
//				stSeqNo = webSeqNoVO.getStSeqNo();
//				tcSeqNo = webSeqNoVO.getTcSeqNo();
//				paSeqNo = webSeqNoVO.getPaSeqNo();
//				
//				
//				
//			}else {
//				
//				String classNm = null;
//				// 기관 이름이 여러개인 경우가 많으므로 반이름만 넣는 작업을 진행
//				Row row = schInfoworksheet.getRow(i);
//				// 기관 이름
//				classNm = row.getCell(2).getStringCellValue();
//				// 조회를 해서 동일한 값이 있을 경우 그걸로 만든다 (나중에 고려할 문제)
////				webRegistService.insertClassInfo(String.format("%08d", classSeqNo), classNm);
//				// 반 코드 시퀀스 증가
//				classSeqNo++;
//			}
//			
////			webRegistService.updateSeqNo("CLASS_SEQ_NO" , Integer.toString(classSeqNo));
//			
//		}
	
}
