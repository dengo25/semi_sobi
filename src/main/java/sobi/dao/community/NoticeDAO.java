package sobi.dao.community;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import sobi.db.ConnectionProvider;
import sobi.vo.community.NoticeImageVO;
import sobi.vo.community.NoticeVO;

public class NoticeDAO {
	public static int pageSize = 10; 		// 한페이지에 보여줄 글 수 
	public static int totalRecord = 1; 		// 총 레코드   
	public static int totalPage = 0;		// 총 페이지 
	public static int pageNum = 0;			// 현재 페이지 
	public static int start = 0;
	public static int end = 0;
	
	// 공지사항 전체 목록 조회 한다.
	public ArrayList<NoticeVO> getAll(HashMap<String,Object> map) {
		ArrayList<NoticeVO> list = new ArrayList<>();
		String keyword = (String) map.get("keyword");
		//String sort = (String) map.get("sort");
		int pageNum = (int) map.get("pageNum");
		
		totalRecord = getCount(keyword);
		totalPage = (int)Math.ceil(totalRecord / (double)pageSize);
		
		start = (pageNum - 1) * pageSize + 1;
		end = pageNum * pageSize;
		if(end > totalRecord) {
			end = totalRecord;
		}
		
		int offset = start - 1;			// mysql 용 변환 
		int count = end - start + 1;	// mysql 용 변환 
		
		String sql = "SELECT ROW_NUMBER() OVER (ORDER BY NOTICE_NO DESC) AS ROWNO,"
		           + " NOTICE_TITLE, NOTICE_CONTENT, COUNT, NOTICE_CREATE_DATE"
		           + " FROM NOTICE"
		           + " WHERE IS_DELETED = 'N'"
		           + " AND IS_VISIBLE = 'Y'"
		           + " AND NOTICE_TITLE LIKE CONCAT('%', ?, '%')"
		           + " ORDER BY NOTICE_NO DESC"
		           + " LIMIT ?, ?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1,keyword);
			pstm.setInt(2,offset);
			pstm.setInt(3,count);
			ResultSet rs = pstm.executeQuery();

			while(rs.next()) {
				NoticeVO vo = new NoticeVO();
				vo.setRowNo(rs.getInt(1));
				vo.setNoticeTitle(rs.getString(2));
				vo.setNoticeContent(rs.getString(3));
				vo.setCount(rs.getInt(4));
				vo.setNoticeCreateDate(rs.getDate(5));
				
				list.add(vo);
			}
			ConnectionProvider.close(conn, pstm, rs);
		} catch (Exception e) {
			System.out.println("getAll Exception : "+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
	
	// 공지사항 게시글을 카운트 한다. (키워드가 들어 갔을 경우도 포함)
	public int getCount(String keyword) {
		int re = -1;
		String sql = "SELECT COUNT(*) FROM NOTICE n"
				+ " WHERE n.NOTICE_TITLE LIKE CONCAT('%', ? ,'%')";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1,keyword);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()) {
				re = rs.getInt(1);
			}
			ConnectionProvider.close(conn, pstm, rs);
		} catch (Exception e) {
			System.out.println("getCount Exception : "+e.getMessage());
			e.printStackTrace();
		}
		return re;
	}
	
	
	// 선택된 게시글의 상세 내용을 반환한다.
	public int getDetail() {
		int re = -1;
		
		
		return re;
	}
	
	
	// 	새로운 게시글 등록시 작성 내용을 등록 한다.
	public int setDetail(NoticeVO nvo,String memberId) {
		int re = -1;
		String sql = "INSERT INTO NOTICE ("
				+ " MEMBER_ID, NOTICE_TITLE, NOTICE_CONTENT, NOTICE_IMAGE_NUMBER,"
				+ " NOTICE_CREATE_DATE, IS_DELETED, IS_VISIBLE )"
				+ " VALUES(?, ?, ?, ?, SYSDATE(),'N','Y')"; 
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			
			psmt.setString(1,memberId);
			psmt.setString(2,nvo.getNoticeTitle());
			psmt.setString(3,nvo.getNoticeContent());
			psmt.setInt(4,nvo.getNoticeImageNumber());
			// re = psmt.executeUpdate();
			int rows  = psmt.executeUpdate();
			if(rows > 0) {
				// pk 조회 
				ResultSet rs = psmt.getGeneratedKeys();
				if(rs.next()) {
					re = rs.getInt(1); // 생성된 noticeNo 
				}
				rs.close();
			}
			ConnectionProvider.close(conn, psmt);
		} catch (Exception e) {
			System.out.println("setDetail Exception : "+e.getMessage());
			e.printStackTrace();
		}
		return re;
	}
	
	
	// 선택한 게시글 수정할 내용을 반환 한다.
	public int editDetail(NoticeVO nvo) {
		int re = -1;
		
		
		return re;
	}
	
	
	// 선택한 게시글을 삭제한다.
	public int setDelete(int no) {
		int re = -1;
		
		
		return re;
	}
	
	
	// 공지사항중 최근 글 2건을 반환한다.
	public ArrayList<NoticeVO> getNewNotice() {
		ArrayList<NoticeVO> list = null;
		
		return list;
	}
	
	
	// NOTICE_IMAGE 테이블에 한 건의 이미지 메타정보를 삽입
    public int insertImage(NoticeImageVO vo) {
        int re = -1;
        String sql = "INSERT INTO NOTICE_IMAGE "
        		   + " (NOTICE_NO, FILE_URL, UPLOAD_TIME, ORIGINAL_FILE_NAME, FILE_TYPE)"
                   + " VALUES (?, ?, SYSDATE(), ?, ?)";
        try {
    		Connection conn = ConnectionProvider.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);
    			
            psmt.setInt(1, vo.getNoticeNo());
            psmt.setString(2, vo.getFileUrl());
            psmt.setString(3, vo.getOriginalFileName());
            psmt.setString(4, vo.getFileType());
            re = psmt.executeUpdate();
            
            ConnectionProvider.close(conn, psmt);
        } catch (Exception e) {
        	System.out.println("insertImage Exception : "+e.getMessage());
			e.printStackTrace();
        }
        return re;
    }
	
}
