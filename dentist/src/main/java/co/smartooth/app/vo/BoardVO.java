package co.smartooth.app.vo;

import java.io.Serializable;


/**
 * 작성자 : 정주현 
 * 작성일 : 2023. 09. 08
 * 수정일 : 2023. 09. 08
 */
public class BoardVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String boardSeq;
	private String userId;
	private String userName;
	private String boardTitle;
	private String boardType;
	private String boardContent;
	private String boardRgstDt;
	private String boardStatus;
	private String boardRequestStatus;

	// 버그리포트
	private String boardStatusHtml;
	private String boardRequestStatusHtml;
	
	// 공지사항
	private String boardImgUrl;
	private String boardPreviewImgUrl;
	private String boardEventStartDt;
	private String boardEventEndDt;
	
	
	
	
	
	public String getBoardSeq() {
		return boardSeq;
	}
	public void setBoardSeq(String boardSeq) {
		this.boardSeq = boardSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getBoardRgstDt() {
		return boardRgstDt;
	}
	public void setBoardRgstDt(String boardRgstDt) {
		this.boardRgstDt = boardRgstDt;
	}
	public String getBoardStatus() {
		return boardStatus;
	}
	public void setBoardStatus(String boardStatus) {
		this.boardStatus = boardStatus;
	}
	public String getBoardRequestStatus() {
		return boardRequestStatus;
	}
	public void setBoardRequestStatus(String boardRequestStatus) {
		this.boardRequestStatus = boardRequestStatus;
	}
	public String getBoardStatusHtml() {
		return boardStatusHtml;
	}
	public void setBoardStatusHtml(String boardStatusHtml) {
		this.boardStatusHtml = boardStatusHtml;
	}
	public String getBoardRequestStatusHtml() {
		return boardRequestStatusHtml;
	}
	public void setBoardRequestStatusHtml(String boardRequestStatusHtml) {
		this.boardRequestStatusHtml = boardRequestStatusHtml;
	}
	public String getBoardPreviewImgUrl() {
		return boardPreviewImgUrl;
	}
	public void setBoardPreviewImgUrl(String boardPreviewImgUrl) {
		this.boardPreviewImgUrl = boardPreviewImgUrl;
	}
	public String getBoardImgUrl() {
		return boardImgUrl;
	}
	public void setBoardImgUrl(String boardImgUrl) {
		this.boardImgUrl = boardImgUrl;
	}
	public String getBoardEventStartDt() {
		return boardEventStartDt;
	}
	public void setBoardEventStartDt(String boardEventStartDt) {
		this.boardEventStartDt = boardEventStartDt;
	}
	public String getBoardEventEndDt() {
		return boardEventEndDt;
	}
	public void setBoardEventEndDt(String boardEventEndDt) {
		this.boardEventEndDt = boardEventEndDt;
	}
	
}
