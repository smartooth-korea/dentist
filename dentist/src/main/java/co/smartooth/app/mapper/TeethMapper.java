package co.smartooth.app.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import co.smartooth.app.vo.TeethInfoVO;
import co.smartooth.app.vo.TeethMeasureVO;
import co.smartooth.app.vo.ToothMeasureVO;

/**
 * 작성자 : 정주현
 * 작성일 : 2022. 04. 28
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01
 */
@Mapper
public interface TeethMapper {
	
	
	// 피측정자 치아 상태 값 등록
	public void insertUserTeethInfo(TeethInfoVO teethInfoVO) throws Exception;
	
	
	// 피측정자 치아 측정 값 등록
	public void insertUserTeethMeasureValue(TeethMeasureVO teethMeasureVO) throws Exception;

	
	// 피측정자 개별 치아 측정 값 업데이트
	public void updateUserToothMeasureValue(ToothMeasureVO ToothMeasureVO) throws Exception;
	
	
	// 피측정자 충치 개수 업데이트 (측정일별)
	public void updateUserCavityCntByMeasureDt(TeethMeasureVO teethMeasureVO) throws Exception;
	
	
	// 피측정자의 SYSDATE(오늘)의 측정 값이 있는지 여부 확인 (0 : 오늘X, 1: 오늘)
	public Integer isExistSysDateRow(@Param("userId") String userId) throws Exception;

	
	// 피측정자의 기존 치아 측정 값 있는지 여부 반환(0 : 없음 / 1이상: 있음)
	public Integer isExistOldRow(@Param("userId") String userId) throws Exception;

	
	/** 피측정자 치아 측정 값 조회 (기간) **/
	public List<TeethMeasureVO> selectUserTeethMeasureValue(TeethMeasureVO teethMeasureVO) throws Exception;
	
	
	// 피측정자 치아 개별 측정 값 조회 (기간)
	public List<ToothMeasureVO> selectUserToothMeasureValue(ToothMeasureVO ToothMeasureVO) throws Exception;


    // 충치 단계별 수치 조회
	public HashMap<String, Integer> selectCavityLevel() throws Exception;
	
	
	// 피측정자 진단 정보 조회 (측정일)
	public TeethMeasureVO selectDiagCd(@Param("userId") String userId, @Param("measureDt") String measureDt) throws Exception;
	
	
	// 피측정자 진단 정보 업데이트
	public void updateDiagCd(@Param("userId") String userId, @Param("diagCd") String diagCd , @Param("measureDt") String measureDt) throws Exception;
	
	
	// 피측정자 비고(메모) 정보 조회 (측정일)
	public TeethMeasureVO selectMemo(@Param("userId") String userId, @Param("measureDt") String measureDt) throws Exception;
	
	
	// 피측정자 비고(메모) 정보 업데이트
	public void updateMemo(@Param("userId") String userId, @Param("memo") String memo, @Param("measureDt") String measureDt) throws Exception;
	
	
	// 피측정자 치아 정보 조회
	public String selectTeethStatus(@Param("userId") String userId) throws Exception;
	  
	
	// T00~T99의 측정 기록을 일반 회원의 측정 기록으로 전환
	public void updateMeasureValueUserId(@Param("userId") String userId, @Param("testUserId") String testUserId) throws Exception;
	
	
	// T00~T99의 측정 기록 삭제
	public void deleteMeasureValueUserId(@Param("userId") String userId, @Param("measureDt") String measureDt) throws Exception;
	
	
	// 빠른 전환 고객 첫 측정 날짜 조회
	public String selectUserTeethMeasureDt(@Param("userId") String userId) throws Exception;
	
	
	// 측정 기록에 측정자 아이디 업데이트
	public void updateMeasurerId(@Param("userId") String userId, @Param("measurerId") String measurerId, @Param("measureDt") String measureDt) throws Exception;
	
	
	
	
	// 피측정자 치아 상태 값 조회
	// public List<TeethInfoVO> selectUserTeethInfo(UserVO userVO) throws Exception; 
	
	
	// 피측정자 치아 측정 값 INSERT
	// public void insertUserTeethMeasureValue(TeethMeasureVO teethMeasureVO) throws Exception;
	
	
	// 피측정자 치아 측정 값 UPDATE
	// public void updateUserTeethMeasureValue(TeethMeasureVO teethMeasureVO) throws Exception;
	
	
	// 피측정자 개별 치아 측정 값 INSERT
	// public void insertUserToothMeasureValue(ToothMeasureVO ToothMeasureVO) throws Exception;
	
	
	// 피측정자 충치 개수 UPDATE (최근) - ST_STUDENT_USER_DETAIL
	// public void updateUserCavityCnt(TeethMeasureVO teethMeasureVO) throws Exception;
	
	
	// 피측정자 치아 측정 값 조회 (기간)
	// public DentalTeethMeasureVO selectUserTeethMeasureValue(DentalTeethMeasureVO teethMeasureVO) throws Exception;
	// public List<TeethMeasureVO> selectUserTeethMeasureValue(TeethMeasureVO teethMeasureVO) throws Exception;
	
	
	// 피측정자 치아 측정 상태 목록 조회 (IsMeasuring) 
    // public List<UserVO> selectStUserIsMeasuring(UserVO userVO) throws Exception;
    
    
    // 피측정자 치아 측정 상태 업데이트
    // public void updateStUserIsMeasuring(UserVO userVO) throws Exception;
    
    
	// 피측정자 치아 측정 값 목록 조회 (최근 3개)
    // public List<TeethMeasureVO> selectUserMeasureValueList(@Param("userId") String userId, @Param("startDt") String startDt, @Param("endDt") String endDt) throws Exception;
	
	
	// 피측정자 치아 정보 업데이트
	// public void updateTeethStatus(@Param("userId") String userId, @Param("teethStatus") String teethStatus, @Param("recordDt") String recordDt) throws Exception;
	
	
	// 피측정자 치아 정보 등록
	// public void insertTeethStatus(@Param("userId") String userId, @Param("teethStatus") String teethStatus) throws Exception;
	
	
	// 피측정자 치아 정보 갯수 조회
	// public int selectCountTeethInfo(@Param("userId") String userId, @Param("recordDt") String recordDt) throws Exception;
	
	
	
}