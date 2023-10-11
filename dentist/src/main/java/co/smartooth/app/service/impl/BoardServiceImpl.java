package co.smartooth.app.service.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.smartooth.app.mapper.BoardMapper;
import co.smartooth.app.service.BoardService;


/**
 * 작성자 : 정주현 
 * 작성일 : 2023. 09. 08
 * 수정일 : 2023. 09. 21
 */
@Service
public class BoardServiceImpl implements BoardService{

	
	@Autowired(required = false)
	BoardMapper boardMapper;
	
	
	// 공지사항 게시글 목록 조회
	@Override
	public List<HashMap<String, Object>> selectNoticeAndEventPostList(@Param("searchType") String searchType, @Param("searchData") String searchData, @Param("startDt") String startDt, @Param("endDt") String endDt) throws Exception {
		return boardMapper.selectNoticeAndEventPostList(searchType, searchData, startDt, endDt);
	}
	
	
	
	
	
	
}
