package co.smartooth.app.mapper;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;



/**
 * 작성자 : 정주현 
 * 작성일 : 2023. 09. 08
 * 수정일 : 2023. 09. 22
 */
@Mapper
public interface BoardMapper {
	
	
	/** 공지사항 **/
	// 공지사항 게시글 리스트 조회
	public List<HashMap<String, Object>> selectNoticeAndEventPostList(@Param("searchType") String searchType, @Param("searchData") String searchData, @Param("startDt") String startDt, @Param("endDt") String endDt) throws Exception;
	
	
}
