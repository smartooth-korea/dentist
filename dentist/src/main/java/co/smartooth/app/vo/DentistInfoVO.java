package co.smartooth.app.vo;

import java.io.Serializable;

public class DentistInfoVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 치과 코드
	private String dentalHospitalCd;
	// 의사 아이디
    private String dentistId;
	// 의사 이름
	private String dentistNm;
	// 의사 이메일
	private String dentistEmail;
	// 의사 전화번호
	private String dentistTelNo;
	// 전공 이름
	private String medicalMajorNm;
	
	
	
	
	public String getDentalHospitalCd() {
		return dentalHospitalCd;
	}
	public void setDentalHospitalCd(String dentalHospitalCd) {
		this.dentalHospitalCd = dentalHospitalCd;
	}
	public String getDentistId() {
		return dentistId;
	}
	public void setDentistId(String dentistId) {
		this.dentistId = dentistId;
	}
	public String getDentistNm() {
		return dentistNm;
	}
	public void setDentistNm(String dentistNm) {
		this.dentistNm = dentistNm;
	}
	public String getDentistEmail() {
		return dentistEmail;
	}
	public void setDentistEmail(String dentistEmail) {
		this.dentistEmail = dentistEmail;
	}
	public String getDentistTelNo() {
		return dentistTelNo;
	}
	public void setDentistTelNo(String dentistTelNo) {
		this.dentistTelNo = dentistTelNo;
	}
	public String getMedicalMajorNm() {
		return medicalMajorNm;
	}
	public void setMedicalMajorNm(String medicalMajorNm) {
		this.medicalMajorNm = medicalMajorNm;
	}

}