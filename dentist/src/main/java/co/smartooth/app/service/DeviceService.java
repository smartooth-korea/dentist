package co.smartooth.app.service;

import org.springframework.stereotype.Service;

import co.smartooth.app.vo.CalibrationVO;
import co.smartooth.app.vo.DeviceVO;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 04. 28
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01
 */
@Service
public interface DeviceService {
	
	
	// 회원이 사용하는 장비 정보 INSERT
	public void insertDeviceInfo(DeviceVO dentistDeviceVO) throws Exception;
	
	
	// 기준의 오늘날짜와 맥주소
	// 회원 장비의 캘리브레이션 측정 값을 저장하기 위해 현재 회원이 측정한 캘리브레이션 값이 오늘 데이터인지 확인 후 값 반환(0 : 오늘X, 1: 오늘)
	public Integer isExistCalibrationValueByMacAndDate(CalibrationVO dentistCalibrationVO) throws Exception;
	
	
	//	회원 장비의 캘리브레이션 값 INSERT
	public void insertCalibrationInfoValue(CalibrationVO dentistCalibrationVO) throws Exception;

	
	//	회원 장비의 캘리브레이션 값 UPDATE
	public void updateCalibrationInfoValue(CalibrationVO dentistCalibrationVO) throws Exception;
	

	

}