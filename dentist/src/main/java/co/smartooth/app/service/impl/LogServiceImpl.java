package co.smartooth.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.smartooth.app.mapper.LogMapper;
import co.smartooth.app.mapper.LogMapper;
import co.smartooth.app.service.LogService;
import co.smartooth.app.service.LogService;
import co.smartooth.app.vo.AuthVO;
import co.smartooth.app.vo.AuthVO;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 04. 28
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01
 */
@Service
public class LogServiceImpl implements LogService{

	
	@Autowired(required = false)
	LogMapper logMapper;
	
	
	// 회원 로그인 기록 INSERT
	@Override
	public void insertUserLoginHistory(AuthVO dentistAuthVO) throws Exception {
		logMapper.insertUserLoginHistory(dentistAuthVO);
	}
	
	
	
	// 회원 접속일 UPDATE
	@Override
	public void updateLoginDt(AuthVO dentistAuthVO) throws Exception {
		logMapper.updateLoginDt(dentistAuthVO);
	}

	
	
	// 회원 비밀번호 변경 이력 등록
	@Override
	public void updateUserPwdChangeHistory(AuthVO authVO) throws Exception {
		logMapper.updateUserPwdChangeHistory(authVO);
	}
	
}
