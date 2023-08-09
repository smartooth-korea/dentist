package co.smartooth.app.vo;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 11. 16
 * 수정일 : 2022. 11. 16
 */
public class DiagnosisVO {
	
	// 최상위 진단 코드 (ex: A)
	private String diagCd;
	// 최상위 진단 명
	private String diagNm;
	// 중위 진단 번호
	private String diagNo;
	// 진단 설명
	private String diagDescript;
	
	
	
	public String getDiagCd() {
		return diagCd;
	}
	public void setDiagCd(String diagCd) {
		this.diagCd = diagCd;
	}
	public String getDiagNm() {
		return diagNm;
	}
	public void setDiagNm(String diagNm) {
		this.diagNm = diagNm;
	}
	public String getDiagNo() {
		return diagNo;
	}
	public void setDiagNo(String diagNo) {
		this.diagNo = diagNo;
	}
	public String getDiagDescript() {
		return diagDescript;
	}
	public void setDiagDescript(String diagDescript) {
		this.diagDescript = diagDescript;
	}
	
}
