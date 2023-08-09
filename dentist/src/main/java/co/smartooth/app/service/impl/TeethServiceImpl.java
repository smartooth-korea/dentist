package co.smartooth.app.service.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.smartooth.app.mapper.TeethMapper;
import co.smartooth.app.service.TeethService;
import co.smartooth.app.vo.TeethInfoVO;
import co.smartooth.app.vo.TeethMeasureVO;
import co.smartooth.app.vo.ToothMeasureVO;

/**
 * 작성자 : 정주현 
 * 작성일 : 2023. 05. 24
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01
 */
@Service
public class TeethServiceImpl implements TeethService{
	
	
	@Autowired(required = false)
	TeethMapper teethMapper;

	
	// 피측정자 치아 상태 값 INSERT
	@Override
	public void insertUserTeethInfo(TeethInfoVO dentistTeethInfoVO) throws Exception {
		teethMapper.insertUserTeethInfo(dentistTeethInfoVO);
	}
	
	
	
	// 피측정자 치아 측정 값 INSERT 
	@Override
	public void insertUserTeethMeasureValue(TeethMeasureVO dentistTeethMeasureVO) throws Exception {
		teethMapper.insertUserTeethMeasureValue(dentistTeethMeasureVO);
	}

	
	
	// 피측정자 개별 치아 측정 값 UPDATE
	@Override
	public void updateUserToothMeasureValue(ToothMeasureVO dentistToothMeasureVO) throws Exception {
		teethMapper.updateUserToothMeasureValue(dentistToothMeasureVO);
		
	}
	
	
	
	// 피측정자의 SYSDATE(오늘)의 측정 값이 있는지 여부 확인 (0 : 오늘X, 1: 오늘)
	@Override
	public Integer isExistSysDateRow(@Param("userId") String userId) throws Exception {
		return teethMapper.isExistSysDateRow(userId);
	}
	
	
	
	// 피측정자의 기존 치아 측정 값 있는지 여부 반환(0 : 없음 / 1이상: 있음)
	@Override
	public Integer isExistOldRow(@Param("userId") String userId) throws Exception {
		return teethMapper.isExistOldRow(userId);
	}
	
	
	
	// 회원 충치 개수 UPDATE (측정일별)
	@Override
	public void updateUserCavityCntByMeasureDt(TeethMeasureVO dentistTeethMeasureVO) throws Exception {
		teethMapper.updateUserCavityCntByMeasureDt(dentistTeethMeasureVO);
	}
	
	
	
	/** 피측정자 치아 측정 값 조회 (기간) **/
	@Override
	public List<TeethMeasureVO> selectUserTeethMeasureValue(TeethMeasureVO dentistTeethMeasureVO) throws Exception {
		return teethMapper.selectUserTeethMeasureValue(dentistTeethMeasureVO);
	}
	
	
	
	// 피측정자 치아 개별 측정 값 조회 (기간)
	@Override
	public List<ToothMeasureVO> selectUserToothMeasureValue(ToothMeasureVO toothMeasureVO) throws Exception {
		return teethMapper.selectUserToothMeasureValue(toothMeasureVO);
	}
	
	
	
    // 충치 단계별 수치 조회
	@Override
	public HashMap<String, Integer> selectCavityLevel() throws Exception {
		return teethMapper.selectCavityLevel();
	}
	
	
	// 피측정자 진단 정보 조회 (측정일)
	@Override
	public TeethMeasureVO selectDiagCd(@Param("userId") String userId, @Param("measureDt") String measureDt) throws Exception{
		return teethMapper.selectDiagCd(userId, measureDt);
	}


	// 피측정자 진단 정보 업데이트 
	@Override
	public void updateDiagCd(@Param("userId") String userId, @Param("diagCd") String diagCd , @Param("measureDt") String measureDt) throws Exception {
		teethMapper.updateDiagCd(userId, diagCd, measureDt);
	}


	// 피측정자 비고(메모) 정보 조회 (측정일)
	@Override
	public TeethMeasureVO selectMemo(@Param("userId") String userId, @Param("measureDt") String measureDt) throws Exception{
		return teethMapper.selectMemo(userId, measureDt);
	}
	
	
	// 피측정자 비고(메모) 정보 업데이트
	@Override
	public void updateMemo(@Param("userId") String userId, @Param("memo") String memo, @Param("measureDt") String measureDt) throws Exception {
		teethMapper.updateMemo(userId, memo, measureDt);
	}


	
	// 피측정자 치아 정보 조회
	@Override
	public String selectTeethStatus(@Param("userId") String userId) throws Exception {
		return teethMapper.selectTeethStatus(userId);
	}


	
	// T00~T99의 측정 기록을 일반 회원의 측정 기록으로 전환
	@Override
	public void updateMeasureValueUserId (@Param("userId") String userId, @Param("testUserId") String testUserId) throws Exception{
		teethMapper.updateMeasureValueUserId(userId, testUserId);
	}

	
	
	// T00~T99의 측정 기록 삭제
	@Override
	public void deleteMeasureValueUserId (@Param("userId") String userId, @Param("measureDt") String measureDt) throws Exception{
		teethMapper.deleteMeasureValueUserId(userId, measureDt);
	}



	// 빠른 전환 고객 첫 측정 날짜 조회
	@Override
	public String selectUserTeethMeasureDt(@Param("userId") String userId) throws Exception {
		return teethMapper.selectUserTeethMeasureDt(userId);
	}



	// 측정 기록에 측정자 아이디 업데이트
	@Override
	public void updateMeasurerId(String userId, String measurerId, String measureDt) throws Exception {
		teethMapper.updateMeasurerId(userId, measurerId, measureDt);
	}



	
	
	
//	// 피측정자 치아 상태 값 조회
//	@Override
//	public List<TeethInfoVO> selectUserTeethInfo(UserVO dentistUserVO) throws Exception {
//		return teethMapper.selectUserTeethInfo(dentistUserVO);
//	}

	
	
//	// 피측정자 치아 측정 값 INSERT
//	@Override
//	public void insertUserTeethMeasureValue(TeethMeasureVO dentistTeethMeasureVO) throws Exception {
//		teethMapper.insertUserTeethMeasureValue(dentistTeethMeasureVO);
//	}

	
	
//	// 피측정자 치아 측정 값 입력 UPDATE
//	@Override
//	public void updateUserTeethMeasureValue(TeethMeasureVO dentistTeethMeasureVO) throws Exception {
//		teethMapper.updateUserTeethMeasureValue(dentistTeethMeasureVO);
//	}

	
	
//	// 피측정자 개별 치아 측정 값 INSERT
//	@Override
//	public void insertUserToothMeasureValue(ToothMeasureVO dentistToothMeasureVO) throws Exception {
//		teethMapper.insertUserToothMeasureValue(dentistToothMeasureVO);
//	}
	
	
	
//	// 피측정자 치아 측정 값 UPDATE
//	@Override
//	public void updateUserTeethMeasureValue(TeethMeasureVO dentistTeethMeasureVO) throws Exception {
//		teethMapper.updateUserTeethMeasureValue(dentistTeethMeasureVO);
//	}
	
	
	
//	// 회원 충치 개수 UPDATE (최근) - ST_STUDENT_USER_DETAIL
//	@Override
//	public void updateUserCavityCnt(TeethMeasureVO dentistTeethMeasureVO) throws Exception {
//		teethMapper.updateUserCavityCnt(dentistTeethMeasureVO);
//	}
	
	
	
//	// 피측정자 치아 측정 상태 목록 조회 (IsMeasuring) 
//	@Override
//	public List<UserVO> selectStUserIsMeasuring(UserVO dentistUserVO) throws Exception {
//		return teethMapper.selectStUserIsMeasuring(dentistUserVO);
//	}

	
	
//    // 피측정자 치아 측정 상태 업데이트
//	@Override
//	public void updateStUserIsMeasuring(UserVO dentistUserVO) throws Exception {
//		teethMapper.updateStUserIsMeasuring(dentistUserVO);
//	}

	
	
//	// 피측정자 치아 측정 값 목록 조회 (최근 3개)
//	@Override
//    public List<TeethMeasureVO> selectUserMeasureValueList(@Param("userId") String userId, @Param("startDt") String startDt, @Param("endDt") String endDt) throws Exception {
//		return teethMapper.selectUserMeasureValueList(userId, startDt, endDt);
//	}
	
	

//	// 피측정자 치아 정보 업데이트
//	@Override
//	public void updateTeethStatus(@Param("userId") String userId, @Param("teethStatus") String teethStatus, @Param("recordDt") String recordDt) throws Exception {
//		teethMapper.updateTeethStatus(userId, teethStatus, recordDt);
//	}



//	// 피측정자 치아 정보 등록
//	@Override
//	public void insertTeethStatus(@Param("userId") String userId, @Param("teethStatus") String teethStatus) throws Exception {
//		teethMapper.insertTeethStatus(userId, teethStatus);
//	}


	
//	// 피측정자 치아 정보 갯수 조회
//	@Override
//	public int selectCountTeethInfo(@Param("userId") String userId, @Param("recordDt") String recordDt) throws Exception {
//		return teethMapper.selectCountTeethInfo(userId, recordDt);
//	}


}