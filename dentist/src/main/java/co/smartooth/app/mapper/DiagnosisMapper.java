package co.smartooth.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import co.smartooth.app.vo.DiagnosisVO;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 11. 16
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01
 */
@Mapper
public interface DiagnosisMapper {
	
	
	// 중위 진단 정보 조회 
	// public List<DentalDiagnosisVO> selectDiagDept2List() throws Exception;
	public List<DiagnosisVO> selectDiagDept2List(@Param("teethType") String teethType) throws Exception;
	
	
	
}