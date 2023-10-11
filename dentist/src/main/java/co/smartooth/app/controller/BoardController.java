package co.smartooth.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import co.smartooth.app.service.BoardService;
import co.smartooth.app.service.UserService;

/**
 * 작성자 : 정주현 
 * 작성일 : 2023. 09. 25
 * 수정일 : 2023. 09. 25
 * 
 */

@Slf4j
@PropertySource({ "classpath:application.yml" })
@Controller
public class BoardController {

	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	
	@Autowired(required = false)
	private  BoardService boardService;

	@Autowired(required = false)
	private UserService userService;
	
	// 인증 패스
	// private static boolean tokenValidation = false;
	private static boolean tmpTokenValidation = true;

	
	
	/**
	 * 기능   : 게시글 목록 조회
	 * 작성자 : 정주현 
	 * 작성일 : 2023. 09. 25
	 * 수정일 : 2023. 09. 25 
	 */
	@PostMapping(value = {"/dentist/board/selectNoticeAndEventList.do"})
	@ResponseBody
	public HashMap<String, Object> selectBugReportList(HttpServletRequest request, HttpSession session, Model model, @RequestBody HashMap<String, Object> paramMap) {
		
		logger.debug("========== BoardController ========== selectNoticeList.do ==========");
		logger.debug("========== BoardController ========== selectNoticeList.do ==========");
		logger.debug("========== BoardController ========== selectNoticeList.do ==========");
		logger.debug("========== BoardController ========== selectNoticeList.do ==========");
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		List<HashMap<String, Object>> boardList = new ArrayList<HashMap<String,Object>>();
		
		String searchType = null;
		String searchData = null;
		String startDt = null;
		String endDt = null;
		
		// TOKEN 검증
		// JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		// tokenValidation = jwtTokenUtil.validateToken(userAuthToken);
				
		if(tmpTokenValidation) {
			try {
				// 게시판 글 목록
				boardList = boardService.selectNoticeAndEventPostList(searchType, searchData, startDt, endDt);
			}catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "공지사항 및 이벤트 게시물 조회에 실패하였습니다.\n관리자에게 문의해주시기 바랍니다.");
				e.printStackTrace();
			}
		
		}else{
			hm.put("code", "400");
			hm.put("msg", "토큰이 유효하지 않습니다.");
		}
			hm.put("code", "000");
			hm.put("mgs", "성공");
			hm.put("boardList", boardList);
			return hm;

	}
	
	
}
