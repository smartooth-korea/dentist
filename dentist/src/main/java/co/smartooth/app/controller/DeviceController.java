package co.smartooth.app.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.smartooth.app.service.DeviceService;
import co.smartooth.app.utils.JwtTokenUtil;
import co.smartooth.app.vo.CalibrationVO;
import co.smartooth.app.vo.DeviceVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 04. 28
 * 수정일 : 2023. 08. 09
 * 서버분리 : 2023. 08. 01
 * @RestController를 쓰지 않는 이유는 몇 안되는 mapping에 jsp를 반환해줘야하는게 있으므로 @Controller를 사용한다.
 * @RestAPI로 반환해야할 경우 @ResponseBody를 사용하여 HashMap으로 return 해준다.
 */
@Slf4j
@RestController
public class DeviceController {
    
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired(required = false)
	private DeviceService deviceService;
	
	// 인증 패스
	// private static boolean tokenValidation = false;
	private static boolean tmpTokenValidation = true;
	
	
	/**
	 * 기능   : 회원의 사용하는 장비 정보 INSERT
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 05. 19
	 * 수정일 : 2022. 08. 03
	 */
	@PostMapping(value = {"/premium/user/insertDeviceInfo.do"})
	@ResponseBody
	public HashMap<String,Object> insertDeviceInfo(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) throws Exception {
		
		logger.debug("========== DeviceController ========== insertDeviceInfo.do ==========");
		
		
		String userId = null;
		String userNo = null;
		String deviceName = null;
		String deviceCode = null;
		String serialNo = null;
		String macAddress = null;
		// String userAuthToken =  request.getHeader("Authorization");
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		DeviceVO deviceVO = new DeviceVO();
		
		userId = (String)paramMap.get("userId");
		userNo = (String)paramMap.get("userNo");
		deviceName = (String)paramMap.get("deviceName");
		deviceCode = (String)paramMap.get("deviceCode");
		serialNo = (String)paramMap.get("serialNo");
		macAddress = (String)paramMap.get("macAddress");

		deviceVO.setUserId(userId);
		deviceVO.setUserNo(userNo);
		deviceVO.setDeviceName(deviceName);
		deviceVO.setDeviceCode(deviceCode);
		deviceVO.setSerialNo(serialNo);
		deviceVO.setMacAddress(macAddress);
		
		// TOKEN 검증
		// JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		// tokenValidation = jwtTokenUtil.validateToken(userAuthToken);
		
		if(tmpTokenValidation) {
			try {
				// 회원이 사용하는 장비 정보 INSERT
				// 중복될 경우는 업데이트하는 로직 필요
				// 날짜로 판별
				deviceService.insertDeviceInfo(deviceVO);
				hm.put("code", "000");
				hm.put("msg", "장비 정보 등록 성공");
			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "장비 정보 등록에 실패했습니다.\n관리자에게 문의해주시기 바랍니다.");
				e.printStackTrace();
			}
		}else {
			hm.put("code", "400");
			hm.put("msg", "토큰이 유효하지 않습니다.");
		}
		return hm;
	}
	
	
	
	
	/**
	 * 기능   : 회원의 장비 - 캘리브레이션 값 등록 또는 업데이트
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 5. 19
	 */												
	@PostMapping(value = {"/premium/user/insertCalibrationInfoValue.do"})
	@ResponseBody
	public HashMap<String,Object> insertCalibrationInfoValue(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) throws Exception {
		
		logger.debug("========== DeviceController ========== insertCalibrationInfoValue.do ==========");
		
		String userId = null;
		String userNo = null;
		String calibrationAir = null;
		String calibrationKit = null;
		String calibrationRef = null;
		String macAddress = null;
		// String userAuthToken = request.getHeader("Authorization");
		
		int isExistRow = 0;
		
		// 오늘 날짜 구하기 (SYSDATE)
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String today = now.format(formatter);
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		CalibrationVO calibrationVO = new CalibrationVO();
		
		// Parameter = userId 값 검증 (Null 체크 및 공백 체크)
		userId= (String)paramMap.get("userId");
		if(userId == null || userId.equals("") || userId.equals(" ")) {
			hm.put("code", "401");
			hm.put("msg", "아이디가 존재하지 않습니다.");
			return hm;
		}
		
		userId= (String)paramMap.get("userId");
		userNo= (String)paramMap.get("userNo");
		calibrationAir= (String)paramMap.get("calibrationAir");
		calibrationKit= (String)paramMap.get("calibrationKit");
		calibrationRef= (String)paramMap.get("calibrationRef");
		macAddress = ((String)paramMap.get("macAddress"));
		
		// TOKEN 검증
		// JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		// tokenValidation = jwtTokenUtil.validateToken(userAuthToken);
		
		if (tmpTokenValidation) {
			calibrationVO.setUserId(userId);
			calibrationVO.setUserNo(userNo);
			calibrationVO.setCalibrationAir(calibrationAir);
			calibrationVO.setCalibrationKit(calibrationKit);
			calibrationVO.setCalibrationRef(calibrationRef);
			calibrationVO.setCalibrationDt(today);
			calibrationVO.setMacAddress(macAddress);
			
			try {
				// 맥주소와 날짜로 기존에 캘리브레이션 값이 존재하는 지 여부 확인
				isExistRow = deviceService.isExistCalibrationValueByMacAndDate(calibrationVO);
				if(isExistRow == 0) {
					deviceService.insertCalibrationInfoValue(calibrationVO);
				}else{
					deviceService.updateCalibrationInfoValue(calibrationVO);
				}
				
				hm.put("code", "000");
				hm.put("msg", "캘리브레이션 성공");
			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "캘리브레이션 정보 등록에 실패했습니다.\n관리자에게 문의해주시기 바랍니다.");
				e.printStackTrace();
			}
		}else {
			hm.put("code", "400");
			hm.put("msg", "토큰이 유효하지 않습니다.");
		}
		return hm;
	}
	
	
}
