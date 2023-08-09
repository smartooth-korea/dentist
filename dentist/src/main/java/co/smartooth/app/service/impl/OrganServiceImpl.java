package co.smartooth.app.service.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.smartooth.app.mapper.OrganMapper;
import co.smartooth.app.service.OrganService;


/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 07. 15
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01
 */
@Service
public class OrganServiceImpl implements OrganService{
	
	
	@Autowired(required = false)
	OrganMapper organMapper; 

	
		// 부서 목록 조회
		@Override
		public List<HashMap<String, Object>> selectDepartmentList(@Param("organCode") String organCode) throws Exception {
			return organMapper.selectDepartmentList(organCode);
		}
		
		
		
		// 등록 되어 있는 측정 예정 혹은 측정 완료 기관 목록 조회 (SYSDATE 기준)
		@Override
		public List<HashMap<String, Object>> selectMeasureOrganList(@Param("userId")  String userId, @Param("userType")  String userType) throws Exception {
			return organMapper.selectMeasureOrganList(userId, userType);
		}
		
		
		
		// 치과 정보 조회
		@Override
		public HashMap<String, Object> selectDentalHospitalInfo(@Param("dentalHospitalCd") String dentalHospitalCd) throws Exception{
			return organMapper.selectDentalHospitalInfo(dentalHospitalCd);
		}
		
		
		
		
		
		
		
		
		
		/*
		 * // 부서 등록
		 * 
		 * @Override public void insertDepartmentInfo(@Param("schoolCode") String
		 * schoolCode, @Param("classCode") String classCode, @Param("className") String
		 * className) throws Exception {
		 * adminOrganMapper.insertDepartmentInfo(schoolCode, classCode, className); }
		 * 
		 * 
		 * // 부서 삭제
		 * 
		 * @Override public void deleteDepartInfo(@Param("classCode") String classCode)
		 * throws Exception { adminOrganMapper.deleteDepartInfo(classCode); }
		 * 
		 * 
		 * // 부서 존재 여부 확인
		 * 
		 * @Override public int isExistDepart(@Param("schoolCode") String schoolCode)
		 * throws Exception { return adminOrganMapper.isExistDepart(schoolCode); }
		 * 
		 * 
		 * // 부서 SEQ 번호 조회
		 * 
		 * @Override public String selectDepartSeqNo(@Param("schoolCode") String
		 * schoolCode) throws Exception { return
		 * adminOrganMapper.selectDepartSeqNo(schoolCode); }
		 * 
		 * 
		 * // 부서 이름 수정
		 * 
		 * @Override public void updateDepartInfo(@Param("classCode") String classCode
		 * , @Param("classCode") String className) throws Exception {
		 * adminOrganMapper.updateDepartInfo(classCode , className); }
		 * 
		 * 
		 * // 자문치과 등록
		 * 
		 * @Override public void insertAdviceInfo(@Param("schoolCode") String
		 * schoolCode, @Param("dentistName") String dentistName, @Param("dentistNum")
		 * String dentistNum) throws Exception{
		 * adminOrganMapper.insertAdviceInfo(schoolCode, dentistName, dentistNum); }
		 * 
		 * 
		 * // 자문치과 조회
		 * 
		 * @Override public AdminAdviceVO selectAdviceDentistInfo(@Param("schoolCode")
		 * String schoolCode) throws Exception { return
		 * adminOrganMapper.selectAdviceDentistInfo(schoolCode); }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * // 기관 실시간 검색 (ajax)
		 * 
		 * @Override public List<Map<String, Object>>
		 * ajaxSelectOrganList(@Param("searchType") String
		 * searchType, @Param("searchData") String searchData) throws Exception { return
		 * adminOrganMapper.ajaxSelectOrganList(searchType, searchData); }
		 * 
		 * 
		 * // 부서 실시간 검색 (ajax)
		 * 
		 * @Override public List<HashMap<String, Object>>
		 * ajaxSelectDepartmentList(@Param("searchType") String
		 * searchType, @Param("searchData") String searchData) throws Exception { return
		 * adminOrganMapper.ajaxSelectDepartmentList(searchType, searchData); }
		 * 
		 * 
		 * 
		 * // 그래프 계산 일괄 작업 여부 업데이트 (Y) public void
		 * updateGraphBatchedStatus(@Param("schoolCode") String schoolCode) throws
		 * Exception{ adminOrganMapper.updateGraphBatchedStatus(schoolCode); }
		 * 
		 * 
		 * 
		 * // 측정 예정 및 측정 완료 기관 목록 조회
		 * 
		 * @Override public List<AdminMeasureOrganVO>
		 * selectMeasureOrganList(@Param("userId") String userId, @Param("searchType")
		 * String searchType, @Param("searchData") String searchData) throws Exception {
		 * return adminOrganMapper.selectMeasureOrganList(userId, searchType,
		 * searchData); }
		 * 
		 * 
		 * 
		 * // 기관에 속한 피측정자 시퀀스 증가
		 * 
		 * @Override public void updateOrganUserSeqNo(AdminOrganVO adminOrganVO) throws
		 * Exception { adminOrganMapper.updateOrganUserSeqNo(adminOrganVO); }
		 * 
		 * 
		 * 
		 * // 측정 기관 등록 (ajax)
		 * 
		 * @Override public void inserMeasureOrganLog(@Param("userId") String
		 * userId, @Param("schoolCode") String schoolCode, @Param("measureDt") String
		 * measureDt) throws Exception { adminOrganMapper.inserMeasureOrganLog(userId,
		 * schoolCode, measureDt); }
		 */

	
}