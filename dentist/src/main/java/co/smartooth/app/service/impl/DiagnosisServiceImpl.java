package co.smartooth.app.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.smartooth.app.mapper.DiagnosisMapper;
import co.smartooth.app.service.DiagnosisService;
import co.smartooth.app.vo.DiagnosisVO;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 04. 28
 * 수정일 : 2022. 08. 03
 */
@Service
public class DiagnosisServiceImpl implements DiagnosisService{

	
	@Autowired(required = false)
	DiagnosisMapper diagnosisMapper;
	
	
//	@Override
//	public List<DentalDiagnosisVO> selectDiagDept2List() throws Exception {
//		return dentalDiagnosisMapper.selectDiagDept2List();
//	}
	@Override
	public List<DiagnosisVO> selectDiagDept2List(@Param("teethType") String teethType) throws Exception {
		return diagnosisMapper.selectDiagDept2List(teethType);
	}

	
}