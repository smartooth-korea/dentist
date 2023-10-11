package co.smartooth.app.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import co.smartooth.app.service.DiagnosisService;
import co.smartooth.app.service.TeethService;
import co.smartooth.app.service.UserService;
import co.smartooth.app.vo.DiagnosisVO;
import co.smartooth.app.vo.TeethMeasureVO;
import co.smartooth.app.vo.ToothMeasureVO;
import co.smartooth.app.vo.UserVO;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 07. 14
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01
 * @RestController를 쓰지 않는 이유는 몇 안되는 mapping에 jsp를 반환해줘야하는게 있으므로 @Controller를 사용한다.
 * @RestAPI로 반환해야할 경우 @ResponseBody를 사용하여 HashMap으로 return 해준다.
 */
@Slf4j
@RestController
public class TeethController {
    
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired(required = false)
	private TeethService teethService;
	

	@Autowired(required =false)
	private UserService userService;

	
	@Autowired(required =false)
	private DiagnosisService diagnosisService;
	
	
	// 인증 패스
	// private static boolean tokenValidation = false; 
	private static boolean tmpTokenValidation = true;
	
	
	// 모든 치아의 값을 -99로 초기화하는 메소드
	public TeethMeasureVO setTeethInit(TeethMeasureVO teethMeasureVO) {
		teethMeasureVO.setT01(-99);
		teethMeasureVO.setT02(-99);
		teethMeasureVO.setT03(-99);
		teethMeasureVO.setT04(-99);
		teethMeasureVO.setT05(-99);
		teethMeasureVO.setT06(-99);
		teethMeasureVO.setT07(-99);
		teethMeasureVO.setT08(-99);
		teethMeasureVO.setT09(-99);
		teethMeasureVO.setT10(-99);
		teethMeasureVO.setT11(-99);
		teethMeasureVO.setT12(-99);
		teethMeasureVO.setT13(-99);
		teethMeasureVO.setT14(-99);
		teethMeasureVO.setT15(-99);
		teethMeasureVO.setT16(-99);
		teethMeasureVO.setT17(-99);
		teethMeasureVO.setT18(-99);
		teethMeasureVO.setT19(-99);
		teethMeasureVO.setT20(-99);
		teethMeasureVO.setT21(-99);
		teethMeasureVO.setT22(-99);
		teethMeasureVO.setT23(-99);
		teethMeasureVO.setT24(-99);
		teethMeasureVO.setT25(-99);
		teethMeasureVO.setT26(-99);
		teethMeasureVO.setT27(-99);
		teethMeasureVO.setT28(-99);
		teethMeasureVO.setT29(-99);
		teethMeasureVO.setT30(-99);
		teethMeasureVO.setT31(-99);
		teethMeasureVO.setT32(-99);
		teethMeasureVO.setT33(-99);
		teethMeasureVO.setT34(-99);
		teethMeasureVO.setT35(-99);
		teethMeasureVO.setT36(-99);
		teethMeasureVO.setT37(-99);
		teethMeasureVO.setT38(-99);
		teethMeasureVO.setT39(-99);
		teethMeasureVO.setT40(-99);
		teethMeasureVO.setT41(-99);
		teethMeasureVO.setT42(-99);
		teethMeasureVO.setT43(-99);
		teethMeasureVO.setT44(-99);
		teethMeasureVO.setT45(-99);
		teethMeasureVO.setT46(-99);
		teethMeasureVO.setT47(-99);
		teethMeasureVO.setT48(-99);
		teethMeasureVO.setT49(-99);
		teethMeasureVO.setT50(-99);
		teethMeasureVO.setT51(-99);
		teethMeasureVO.setT52(-99);
		teethMeasureVO.setT53(-99);
		teethMeasureVO.setT54(-99);
		teethMeasureVO.setT55(-99);
		teethMeasureVO.setT56(-99);
		return teethMeasureVO;
	}
	
	
	/**
	 * 기능   : 치아 개별 측정 값 조회 (기간)
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 05. 26
	 * 수정일 : 2023. 08. 07
 	 *	기간 조회 (startDt, endDt)
	 */
	  @PostMapping({"/dentist/user/selectUserToothMeasureValue.do"})
	  @ResponseBody
		public HashMap<String, Object> selectUserToothMeasureValue(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) throws Exception {

			logger.debug("========== dentist.TeethController ========== /dentist/user/selectUserToothMeasureValue.do ==========");
			logger.debug("========== dentist.TeethController ========== /dentist/user/selectUserToothMeasureValue.do ==========");
			logger.debug("========== dentist.TeethController ========== /dentist/user/selectUserToothMeasureValue.do ==========");
			logger.debug("========== dentist.TeethController ========== /dentist/user/selectUserToothMeasureValue.do ==========");

			// SYSDATE 기준 측정 여부 확인
			int isExistSysdateRow = 0;
			// 기존 측정 값 여부 확인
			int isExistOldRow = 0;

			// 인증 토큰
			// String userAuthToken = request.getHeader("Authorization");

			// 회원 아이디
			String userId = (String) paramMap.get("userId");
			// 측정 치아 번호0
			String toothNo = (String) paramMap.get("toothNo");
			// 치아 측정 값
			String toothValue = (String) paramMap.get("toothValue");
			// 측정일 : SYSDATE(yyyy-mm-dd)
			// 앱에서 보내주는 파라미터 : SYSDATE 기준으로 1년
			String startDt = (String) paramMap.get("startDt");
			String endDt = (String) paramMap.get("endDt");

			// 치과 코드
			String schoolCode = (String) paramMap.get("schoolCode");
			// String dentalHospitalCd= (String) paramMap.get("dentalHospitalCd");
			// 치과 의사 아이디
			String measurerId = (String) paramMap.get("dentistId");

			/** 치아 관련 정보 **/
			// 회원 치아 정보
			String teethStatus = null;
			// 회원 치아 형태
			String teethType = null;

			// 유치 측정 수치 배열
			int[] babyTeethArray = null;
			// 영구치 측정 수치 배열
			int[] permTeethArray = null;

			// 유치 정상 치아 개수
			int babyCvNormalCnt = 0;
			// 유치 주의 치아 개수
			int babyCvCautionCnt = 0;
			// 유치 충치 치아 개수
			int babyCvDangerCnt = 0;

			// 정상 영구치 개수
			int permCvNomalCnt = 0;
			// 주의 영구치 개수
			int permCvCautionCnt = 0;
			// 충치 영구치 개수
			int permCvDangerCnt = 0;

			// 충치 단계 - 주의
			Integer cautionLevel = 0;
			// 충치 단계 - 위험
			Integer dangerLevel = 0;

			UserVO userVO = new UserVO();
			ToothMeasureVO toothMeasureVO = new ToothMeasureVO();
			TeethMeasureVO teethMeasureVO = new TeethMeasureVO();

			HashMap<String, Object> hm = new HashMap<String, Object>();
			HashMap<String, Integer> cavityLevel = new HashMap<String, Integer>();

			List<ToothMeasureVO> userToothValues = new ArrayList<ToothMeasureVO>();
			List<TeethMeasureVO> userTeethValues = new ArrayList<TeethMeasureVO>();

			LocalDate now = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String sysDate = now.format(formatter);

			if (userId == null || userId.equals("") || userId.equals(" ")) {
				hm.put("code", "401");
				hm.put("msg", "파라미터(아이디)가 전달되지 않았습니다.");
				return hm;
			}

			// JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
			// tokenValidation = jwtTokenUtil.validateToken(userAuthToken);
			if (tmpTokenValidation) {

				try {

					// 피측정자의 법정대리인 동의 여부 확인
					userVO = userService.selectUserDetailInfo(userId);
					teethType = userVO.getTeethType();

					if (teethType == null || teethType.equals("")) {
						teethType = "B";
					}

					// 치아 한개에 대한 VO
					toothMeasureVO.setUserId(userId);
					toothMeasureVO.setStartDt(startDt);
					toothMeasureVO.setEndDt(endDt);
					toothMeasureVO.setToothNo(toothNo);
					toothMeasureVO.setMeasureDt(sysDate);
					toothMeasureVO.setSchoolCode(schoolCode);
					toothMeasureVO.setMeasurerId(measurerId);

					// 치아 전체에 대한 VO
					teethMeasureVO.setUserId(userId);
					teethMeasureVO.setStartDt(startDt);
					teethMeasureVO.setEndDt(endDt);
					teethMeasureVO.setMeasureDt(sysDate);
					teethMeasureVO.setSchoolCode(schoolCode);
					teethMeasureVO.setMeasurerId(measurerId);

					// 오늘의 데이터가 있는지 확인
					isExistSysdateRow = teethService.isExistSysDateRow(userId);
					// 기존에 데이터가 있는지 확인
					isExistOldRow = teethService.isExistOldRow(userId);

					// 치아가 선택 되었을 경우
					if (toothValue != null && !toothValue.equals("") && !toothValue.equals(" ")) {
						toothMeasureVO.setToothValue(Integer.parseInt(toothValue));

						String permToothNo = null;

						switch (toothNo) {
						case "t33":
							permToothNo = "t03";
							toothMeasureVO.setToothNo(permToothNo);
							teethService.updateUserToothMeasureValue(toothMeasureVO);
							break;
						case "t44":
							permToothNo = "t14";
							toothMeasureVO.setToothNo(permToothNo);
							teethService.updateUserToothMeasureValue(toothMeasureVO);
							break;
						case "t45":
							permToothNo = "t19";
							toothMeasureVO.setToothNo(permToothNo);
							teethService.updateUserToothMeasureValue(toothMeasureVO);
							break;
						case "t56":
							permToothNo = "t30";
							toothMeasureVO.setToothNo(permToothNo);
							teethService.updateUserToothMeasureValue(toothMeasureVO);
							break;
						}

						toothMeasureVO.setToothNo(toothNo);

						if (isExistSysdateRow == 0) {
							// 치아 값을 선택했을 경우 기존의 데이터에 추가적인 데이터를 합산하여 새로 데이터 생성
							List<TeethMeasureVO> userOldTeethMeasureValue = teethService.selectUserTeethMeasureValue(teethMeasureVO);
							teethService.insertUserTeethMeasureValue(userOldTeethMeasureValue.get(0));
							teethService.updateUserToothMeasureValue(toothMeasureVO);
							// 기존의 데이터를 반환
							userTeethValues = teethService.selectUserTeethMeasureValue(teethMeasureVO);
						} else {
							teethService.updateUserToothMeasureValue(toothMeasureVO);
							userTeethValues = teethService.selectUserTeethMeasureValue(teethMeasureVO);
						}
					} else { // 치아가 선택되지 않았을 경우
						// 기존 데이터는 있으나 오늘 데이터는 없을 경우
						if (isExistOldRow > 0 && isExistSysdateRow == 0) {
							teethMeasureVO.setUserId(userId);
							teethMeasureVO.setStartDt(startDt);
							teethMeasureVO.setEndDt(endDt);
							// 기존의 데이터를 반환
							userTeethValues = teethService.selectUserTeethMeasureValue(teethMeasureVO);
						} else if (isExistSysdateRow > 0) {
							userTeethValues = teethService.selectUserTeethMeasureValue(teethMeasureVO);
						} else {
							List<DiagnosisVO> diagList = diagnosisService.selectDiagDept2List(teethType);
							String diagCd = "";
							for (int i = 0; i < diagList.size(); i++) {
								if (i == diagList.size() - 1) {
									diagCd = diagCd + diagList.get(i).getDiagCd() + ":" + diagList.get(i).getDiagNo()
											+ ":0";
								} else {
									diagCd = diagCd + diagList.get(i).getDiagCd() + ":" + diagList.get(i).getDiagNo()
											+ ":0|";
								}
							}
							// VO 초기값 -99로 설정
							setTeethInit(teethMeasureVO);
							teethMeasureVO.setDiagCd(diagCd);
							teethService.insertUserTeethMeasureValue(teethMeasureVO);
							userTeethValues = teethService.selectUserTeethMeasureValue(teethMeasureVO);
						}
					}

					// 현재 측정한 치아의 값과 측정된 치아의 지난 측정 결과 값 RETURN
					userToothValues = teethService.selectUserToothMeasureValue(toothMeasureVO);
					// 현재 측정한 전체 치아의 값과 측정된 전체 치아의 값 RETURN
					// userTeethValues =
					// dentalTeethService.selectUserTeethMeasureValue(teethMeasureVO);

					cavityLevel = teethService.selectCavityLevel();
					// 수치 단계 : 데이터베이스 값 조회
					cautionLevel = Integer.parseInt(String.valueOf(cavityLevel.get("CAVITY_CAUTION")));
					dangerLevel = Integer.parseInt(String.valueOf(cavityLevel.get("CAVITY_DANGER")));

					if (teethType.equals("B")) {

						// 유치
						babyTeethArray = new int[20];
						permTeethArray = new int[12];

						babyTeethArray[0] = userTeethValues.get(0).getT33();
						babyTeethArray[1] = userTeethValues.get(0).getT34();
						babyTeethArray[2] = userTeethValues.get(0).getT35();
						babyTeethArray[3] = userTeethValues.get(0).getT36();
						babyTeethArray[4] = userTeethValues.get(0).getT37();
						babyTeethArray[5] = userTeethValues.get(0).getT38();
						babyTeethArray[6] = userTeethValues.get(0).getT39();
						babyTeethArray[7] = userTeethValues.get(0).getT40();
						babyTeethArray[8] = userTeethValues.get(0).getT41();
						babyTeethArray[9] = userTeethValues.get(0).getT42();
						babyTeethArray[10] = userTeethValues.get(0).getT43();
						babyTeethArray[11] = userTeethValues.get(0).getT44();
						babyTeethArray[12] = userTeethValues.get(0).getT45();
						babyTeethArray[13] = userTeethValues.get(0).getT46();
						babyTeethArray[14] = userTeethValues.get(0).getT47();
						babyTeethArray[15] = userTeethValues.get(0).getT48();
						babyTeethArray[16] = userTeethValues.get(0).getT49();
						babyTeethArray[17] = userTeethValues.get(0).getT50();
						babyTeethArray[18] = userTeethValues.get(0).getT51();
						babyTeethArray[19] = userTeethValues.get(0).getT52();

						// 영구치 어금니
						permTeethArray[0] = userTeethValues.get(0).getT33();
						permTeethArray[1] = userTeethValues.get(0).getT44();
						permTeethArray[2] = userTeethValues.get(0).getT45();
						permTeethArray[3] = userTeethValues.get(0).getT56();

						// 영구치 상악
						permTeethArray[4] = userTeethValues.get(0).getT07();
						permTeethArray[5] = userTeethValues.get(0).getT08();
						permTeethArray[6] = userTeethValues.get(0).getT09();
						permTeethArray[7] = userTeethValues.get(0).getT10();

						// 영구치 하악
						permTeethArray[8] = userTeethValues.get(0).getT23();
						permTeethArray[9] = userTeethValues.get(0).getT24();
						permTeethArray[10] = userTeethValues.get(0).getT25();
						permTeethArray[11] = userTeethValues.get(0).getT26();

						// 유치 정상, 주의, 충치 개수 저장
						if (babyTeethArray.length > 0 || babyTeethArray != null) {
							for (int i = 0; i < babyTeethArray.length; i++) { // 측정자가 입력한 주의나 충치 값의 -1000
								if (babyTeethArray[i] > 1000) {
									babyTeethArray[i] = (int) babyTeethArray[i] - 1000;
								}
								// 정상, 주의, 위험 개수 저장
								if (babyTeethArray[i] < cautionLevel) {
									babyCvNormalCnt++;
								} else if (babyTeethArray[i] >= cautionLevel && babyTeethArray[i] < dangerLevel) {
									babyCvCautionCnt++;
								} else if (babyTeethArray[i] >= dangerLevel) {
									babyCvDangerCnt++;
								}
							}
							// 유치 정상, 주의, 충치 개수 입력
							teethMeasureVO.setCavityNormal(babyCvNormalCnt);
							teethMeasureVO.setCavityCaution(babyCvCautionCnt);
							teethMeasureVO.setCavityDanger(babyCvDangerCnt);
						}

					} else if (teethType.equals("P") || teethType == null) {

						permTeethArray = new int[32];
						permTeethArray[0] = userTeethValues.get(0).getT01();
						permTeethArray[1] = userTeethValues.get(0).getT02();
						permTeethArray[2] = userTeethValues.get(0).getT03();
						permTeethArray[3] = userTeethValues.get(0).getT04();
						permTeethArray[4] = userTeethValues.get(0).getT05();
						permTeethArray[5] = userTeethValues.get(0).getT06();
						permTeethArray[6] = userTeethValues.get(0).getT07();
						permTeethArray[7] = userTeethValues.get(0).getT08();
						permTeethArray[8] = userTeethValues.get(0).getT09();
						permTeethArray[9] = userTeethValues.get(0).getT10();
						permTeethArray[10] = userTeethValues.get(0).getT11();
						permTeethArray[11] = userTeethValues.get(0).getT12();
						permTeethArray[12] = userTeethValues.get(0).getT13();
						permTeethArray[13] = userTeethValues.get(0).getT14();
						permTeethArray[14] = userTeethValues.get(0).getT15();
						permTeethArray[15] = userTeethValues.get(0).getT16();
						permTeethArray[16] = userTeethValues.get(0).getT17();
						permTeethArray[17] = userTeethValues.get(0).getT18();
						permTeethArray[18] = userTeethValues.get(0).getT19();
						permTeethArray[19] = userTeethValues.get(0).getT20();
						permTeethArray[20] = userTeethValues.get(0).getT21();
						permTeethArray[21] = userTeethValues.get(0).getT22();
						permTeethArray[22] = userTeethValues.get(0).getT23();
						permTeethArray[23] = userTeethValues.get(0).getT24();
						permTeethArray[24] = userTeethValues.get(0).getT25();
						permTeethArray[25] = userTeethValues.get(0).getT26();
						permTeethArray[26] = userTeethValues.get(0).getT27();
						permTeethArray[27] = userTeethValues.get(0).getT28();
						permTeethArray[28] = userTeethValues.get(0).getT29();
						permTeethArray[29] = userTeethValues.get(0).getT20();
						permTeethArray[30] = userTeethValues.get(0).getT31();
						permTeethArray[31] = userTeethValues.get(0).getT32();
					}

					// 영구치 정상, 주의, 충치 개수 저장
					for (int i = 0; i < permTeethArray.length; i++) { // 측정자가 입력한 주의나 충치 값의 -1000
						if (permTeethArray[i] > 1000) {
							permTeethArray[i] = (int) permTeethArray[i] - 1000;
						}
						// 정상, 주의, 위험 개수 저장
						if (permTeethArray[i] < cautionLevel) {
							permCvNomalCnt++;
						} else if (permTeethArray[i] >= cautionLevel && permTeethArray[i] < dangerLevel) {
							permCvCautionCnt++;
						} else if (permTeethArray[i] >= dangerLevel) {
							permCvDangerCnt++;
						}
					}

					// 영구치 정상, 주의, 충치 개수 입력
					teethMeasureVO.setPermCavityNormal(permCvNomalCnt);
					teethMeasureVO.setPermCavityCaution(permCvCautionCnt);
					teethMeasureVO.setPermCavityDanger(permCvDangerCnt);

					teethService.updateUserCavityCntByMeasureDt(teethMeasureVO);

					// 치아 상태 정보 조회
					teethStatus = teethService.selectTeethStatus(userId);

					// 소속 치과 의사 목록 조회
					List<HashMap<String, Object>> dentistList = new ArrayList<HashMap<String, Object>>();
					dentistList = userService.selectDentistList(schoolCode);

					hm.put("dentistList", dentistList);
					hm.put("userToothValues", userToothValues);
					hm.put("userTeethValues", userTeethValues);
					hm.put("teethStatus", teethStatus);
					hm.put("code", "000");
					hm.put("msg", "치아 측정 및 조회 성공");
				} catch (Exception e) {
					hm.put("code", "500");
					hm.put("msg", "치아 측정 및 조회에 실패하였습니다.\n관리자에게 문의해주시기 바랍니다.");
					e.printStackTrace();
				}
			} else {
				hm.put("code", "400");
				hm.put("msg", "토큰이 유효하지 않습니다.");
			}
			return hm;
		}
	  
	  
	  
		/**
	     * 기능   : 진단 코드 및 정보 업데이트
	     * 작성자 : 정주현 
	     * 작성일 : 2022. 11. 25
	     * 수정일 : 2023. 08. 07
	     */
	  	@PostMapping(value = {"/dentist/user/updateDiagCd.do"})
		@ResponseBody
		public HashMap<String, Object> updateDiagCdForDentist(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) throws Exception {

	  		
	  		logger.debug("========== dentist.TeethController ========== /dentist/user/updateDiagCd.do ==========");
	  		logger.debug("========== dentist.TeethController ========== /dentist/user/updateDiagCd.do ==========");
	  		logger.debug("========== dentist.TeethController ========== /dentist/user/updateDiagCd.do ==========");
	  		logger.debug("========== dentist.TeethController ========== /dentist/user/updateDiagCd.do ==========");
	  		
			
			// SYSDATE 기준 측정 여부 확인
			int isExistSysdateRow = 0;
			// 기존 측정 값 여부 확인
			int isExistOldRow = 0;
			
			// 회원 아이디
			String userId = (String) paramMap.get("userId");
			// 측정일
			String measureDt = (String) paramMap.get("measureDt");
			// 시작일
			String startDt = null;
			// 종료일
			String endDt = null;
			// 진단 코드
			String diagCd = (String) paramMap.get("diagCd");
			// 피측정자 진단 코드
			String userDiagCd = null;
			// 진단 코드 배열
			String[] diagArray = null;
			// 측정된 진단 키워드
			String measureDiagCd = null;

			HashMap<String, Object> hm = new HashMap<String, Object>();
			TeethMeasureVO teethMeasureVO = new TeethMeasureVO();

			
			if (measureDt == null || "".equals(measureDt)) {
				// 측정일 파라미터의 값이 존재 하지 않을 경우 SYSDATE로 설정
				LocalDate now = LocalDate.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				startDt = now.minusYears(1).toString();
				endDt = now.format(formatter);
				measureDt = now.format(formatter);
			}

			try {
				// 오늘의 데이터가 있는지 확인
				isExistSysdateRow = teethService.isExistSysDateRow(userId);
				// 기존에 데이터가 있는지 확인
				isExistOldRow = teethService.isExistOldRow(userId); 
				
				if(isExistOldRow > 0 && isExistSysdateRow == 0) {
					// 기존의 데이터가 있으나 오늘의 데이터가 없을 경우, 측정 row를 새로 생성해준 후 그 이후에 업데이트를 진행	
					teethMeasureVO.setUserId(userId);
					teethMeasureVO.setStartDt(startDt);
					teethMeasureVO.setEndDt(endDt);
					// 치아 값을 선택했을 경우 기존의 데이터에 추가적인 데이터를 합산하여 새로 데이터 생성 
					List<TeethMeasureVO> userOldTeethMeasureValue = teethService.selectUserTeethMeasureValue(teethMeasureVO);
					teethService.insertUserTeethMeasureValue(userOldTeethMeasureValue.get(0));
				}
					
				// 회원 진단 정보 조회
				teethMeasureVO = teethService.selectDiagCd(userId, measureDt);
				diagArray = diagCd.split(":");
				// 진단 목록 선택 시 전달되는 PARAMETER
				measureDiagCd = diagArray[0] + ":" + diagArray[1] + ":";
				// 회원 진단 코드
				userDiagCd = teethMeasureVO.getDiagCd();
				
				StringBuffer sb = new StringBuffer();
				sb.append(userDiagCd);
				// 회원의 전체 진단 코드에 선택된 진단 코드의 값을 1로 변경
				// 예) A:01:0 의 6자리가 A:001:0 7자리로 변경되어 밑의 값을 7로 변경 
				userDiagCd = sb.replace(userDiagCd.indexOf(measureDiagCd), userDiagCd.indexOf(measureDiagCd) + 7, diagCd).toString();
				// 회원의 전체 진단 코드 업데이트
				teethService.updateDiagCd(userId, userDiagCd, measureDt);
				
			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "진단 정보 업데이트에 실패했습니다.\n관리자에게 문의해주시기 바랍니다.");
				e.printStackTrace();
			}

			hm.put("code", "000");
			hm.put("msg", "성공");
			hm.put("diagInfo", teethMeasureVO);
			return hm;

		}
	  	
	  	
	  	
	  	/**
		 * 기능 : 회원 치아 상태 정보 업데이트(수정)
		 * 작성자 : 정주현
		 * 작성일 : 2023. 07. 19
		 * 수정일 : 2023. 08. 17
		 */
//	  	@PostMapping({ "/dentist/user/updateTeethStatus.do" })
	  	@PostMapping({ "/premium/user/updateTeethStatus.do" })
		@ResponseBody
		public HashMap<String, Object> updateTeethStatus(@RequestBody HashMap<String, Object> paramMap,
				HttpServletRequest request) throws Exception {

			String userId = (String) paramMap.get("userId");
			String teethStatus = (String) paramMap.get("teethStatus");
			int isExist = 0;
			LocalDate now = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String measureDt = now.format(formatter);

			HashMap<String, Object> hm = new HashMap<String, Object>();
			TeethMeasureVO teethMeasureVO = new TeethMeasureVO();

			try {
				isExist = teethService.selectCountTeethInfo(userId, measureDt);
				if (isExist == 1) {
					teethService.updateTeethStatus(userId, teethStatus, measureDt);
				} else {
					teethService.insertTeethStatus(userId, teethStatus);
				}

			} catch (Exception e) {
				hm.put("code", "500");
				// hm.put("msg", "Server error");
				hm.put("msg", "치아 상태 정보 업데이트에 실패했습니다.\n관리자에게 문의해주시기 바랍니다.");
				e.printStackTrace();
			}

			hm.put("code", "000");
			hm.put("msg", "Success");
			hm.put("memoInfo", teethMeasureVO);
			return hm;
		}
	  	
	  	
	  	
	  	/**
		 * 기능 : 회원의 메모 업데이트(수정)
		 * 작성자 : 정주현
		 * 작성일 : 2023. 07. 19
		 * 수정일 : 2023. 08. 07
		 */
		@PostMapping(value = { "/premium/user/updateMemo.do" })
		@ResponseBody
		public HashMap<String, Object> updateMemo(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) throws Exception {

			String userId = (String) paramMap.get("userId");
			String memo = (String) paramMap.get("memo");

			// 오늘 날짜 구하기 (SYSDATE)
			LocalDate now = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate minusYears = now.minusYears(1);

			String startDt = minusYears.format(formatter);
			String endDt = now.format(formatter);
			String measureDt = endDt;

			HashMap<String, Object> hm = new HashMap<String, Object>();
			TeethMeasureVO teethMeasureVO = new TeethMeasureVO();
			
			try {
				if (!"ÿ".equals(memo)) {
					
					int isExistSysDateRow = teethService.isExistSysDateRow(userId);
					if(isExistSysDateRow == 0) {
						// 측정 값이 없을 경우 측정 ROW 생성
						teethMeasureVO.setUserId(userId);
						teethMeasureVO.setStartDt(startDt);
						teethMeasureVO.setEndDt(endDt);
						teethMeasureVO.setMeasureDt(measureDt);
						List<TeethMeasureVO> userOldTeethMeasureValue = teethService.selectUserTeethMeasureValue(teethMeasureVO);
						teethService.insertUserTeethMeasureValue(userOldTeethMeasureValue.get(0));
						//teethService.updateUserToothMeasureValue(toothMeasureVO);
					}
					teethService.updateMemo(userId, memo, measureDt);
				}
				teethMeasureVO = teethService.selectMemo(userId, measureDt);
			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "메모 등록에 실패했습니다.\n관리자에게 문의해주시기 바랍니다.");
				e.printStackTrace();
			}
			hm.put("code", "000");
			hm.put("msg", "메모 등록 성공");
			hm.put("memoInfo", teethMeasureVO);
			return hm;

		}
		
		
		
		/**
	     * 기능   : 측정자 아이디 업데이트
	     * 작성자 : 정주현 
	     * 작성일 : 2023. 08. 09
	     */
	  	@PostMapping(value = {"/dentist/user/updateMeasurerId.do"})
		@ResponseBody
		public HashMap<String, Object> updateMeasurerId(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) throws Exception {

	  		
	  		logger.debug("========== dentist.TeethController ========== /dentist/user/updateMeasurerId.do ==========");
	  		logger.debug("========== dentist.TeethController ========== /dentist/user/updateMeasurerId.do ==========");
	  		logger.debug("========== dentist.TeethController ========== /dentist/user/updateMeasurerId.do ==========");
	  		logger.debug("========== dentist.TeethController ========== /dentist/user/updateMeasurerId.do ==========");
	  		
			
			// 회원 아이디
			String userId = (String) paramMap.get("userId");
			// 측정자 아이디
			String measureId = (String) paramMap.get("dentistId");
			// 측정일
			String measureDt = (String) paramMap.get("measureDt");
			
			HashMap<String, Object> hm = new HashMap<String, Object>();
			
			try {
				// 측정 기록에 측정자 아이디 업데이트 
				teethService.updateMeasurerId(userId, measureId, measureDt);
				
			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "측정자 아이디 업데이트에 실패했습니다.\n관리자에게 문의해주시기 바랍니다.");
				e.printStackTrace();
			}

			hm.put("code", "000");
			hm.put("msg", "성공");
			return hm;

		}
	  	
}


