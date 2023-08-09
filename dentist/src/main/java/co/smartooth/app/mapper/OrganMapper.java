package co.smartooth.app.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 10. 14
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01
 */
@Mapper
public interface OrganMapper {
	

	// 부서 목록 조회
	public List<HashMap<String, Object>> selectDepartmentList(@Param("organCode") String organCode) throws Exception;
	
	
	// 등록 되어 있는 측정 예정 혹은 측정 완료 기관 목록 조회 (SYSDATE 기준)
	public List<HashMap<String, Object>> selectMeasureOrganList(@Param("userId")  String userId, @Param("userType")  String userType) throws Exception;
	
	
	// 치과 정보 조회
	public HashMap<String, Object> selectDentalHospitalInfo(@Param("dentalHospitalCd") String dentalHospitalCd) throws Exception;
	
	
	
	
	
	
	
	
	
	
	/*
	 * // 부서 등록 public void insertDepartmentInfo(@Param("schoolCode") String
	 * schoolCode, @Param("classCode") String classCode, @Param("className") String
	 * className) throws Exception;
	 * 
	 * 
	 * // 부서 삭제 public void deleteDepartInfo(@Param("classCode") String classCode)
	 * throws Exception;
	 * 
	 * 
	 * // 부서 존재 여부 확인 public int isExistDepart(@Param("schoolCode") String
	 * schoolCode) throws Exception;
	 * 
	 * 
	 * // 부서 SEQ 번호 조회 public String selectDepartSeqNo(@Param("schoolCode") String
	 * schoolCode) throws Exception;
	 * 
	 * 
	 * // 부서 이름 수정 public void updateDepartInfo(@Param("classCode") String
	 * classCode, @Param("className") String className) throws Exception;
	 * 
	 * 
	 * // 자문치과 등록 public void insertAdviceInfo(@Param("schoolCode") String
	 * schoolCode, @Param("dentistName") String dentistName, @Param("dentistNum")
	 * String dentistNum) throws Exception;
	 * 
	 * 
	 * // 자문치과 조회 public AdminAdviceVO selectAdviceDentistInfo(@Param("schoolCode")
	 * String schoolCode) throws Exception;
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * // 기관 실시간 검색 (ajax) public List<Map<String, Object>>
	 * ajaxSelectOrganList(@Param("searchType") String
	 * searchType, @Param("searchData") String searchData) throws Exception;
	 * 
	 * 
	 * // 부서 실시간 검색 (ajax) public List<HashMap<String, Object>>
	 * ajaxSelectDepartmentList(@Param("searchType") String
	 * searchType, @Param("searchData") String searchData) throws Exception;
	 * 
	 * 
	 * // 측정 예정 및 측정 완료 기관 목록 조회 public List<AdminMeasureOrganVO>
	 * selectMeasureOrganList(@Param("userId") String userId, @Param("searchType")
	 * String searchType, @Param("searchData") String searchData) throws Exception;
	 * 
	 * 
	 * // 그래프 계산 일괄 작업 여부 업데이트 (Y) public void
	 * updateGraphBatchedStatus(@Param("schoolCode") String schoolCode) throws
	 * Exception;
	 * 
	 * 
	 * // 측정 기관 등록 (ajax) public void inserMeasureOrganLog(@Param("userId") String
	 * userId, @Param("schoolCode") String schoolCode, @Param("measureDt") String
	 * measureDt) throws Exception;
	 * 
	 * 
	 * // 기관에 속한 피측정자 시퀀스 증가 public void updateOrganUserSeqNo(AdminOrganVO
	 * adminOrganVO) throws Exception;
	 */
	
	
	
}