package co.smartooth.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.smartooth.app.mapper.DeviceMapper;
import co.smartooth.app.service.DeviceService;
import co.smartooth.app.vo.CalibrationVO;
import co.smartooth.app.vo.DeviceVO;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 04. 28
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01
 */
@Service
public class DeviceServiceImpl implements DeviceService{

	
	@Autowired(required = false)
	DeviceMapper deviceMapper;
	
	
	// 장비 정보 INSERT
	@Override
	public void insertDeviceInfo(DeviceVO dentistDeviceVO) throws Exception {
		deviceMapper.insertDeviceInfo(dentistDeviceVO);
	}
	
	// 회원의 장비 - 캘리브레이션 측정 값을 저장하기 위해 현재 회원이 측정한 캘리브레이션 값이 오늘 데이터인지 확인 후 값 반환(0 : 오늘X, 1: 오늘)
	@Override
	public Integer isExistCalibrationValueByMacAndDate(CalibrationVO dentistCalibrationVO) throws Exception {
		return deviceMapper.isExistCalibrationValueByMacAndDate(dentistCalibrationVO);
	}
	
	// 회원 장비의 캘리브레이션 값 INSERT
	@Override
	public void insertCalibrationInfoValue(CalibrationVO dentistCalibrationVO) throws Exception {
		deviceMapper.insertCalibrationInfoValue(dentistCalibrationVO);
	}
	
	// 회원의 장비 - 캘리브레이션 측정 값을 저장하기 위해 현재 회원이 측정한 캘리브레이션 값이 오늘 데이터인지 확인 후 값 반환(0 : 오늘X, 1: 오늘)
	//	@Override
	//	public Integer selectUserCalibrationValueByDate(CalibrationVO calibrationVO) throws Exception {
	//		return userMapper.selectUserCalibrationValueByDate(calibrationVO);
	//	}
	
	// 회원 장비의 캘리브레이션 값 UPDATE
	@Override
	public void updateCalibrationInfoValue(CalibrationVO dentistCalibrationVO) throws Exception {
		deviceMapper.updateCalibrationInfoValue(dentistCalibrationVO);
	}
	
}
