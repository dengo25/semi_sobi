package sobi.dao.community;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
		
		String sql = "SELECT NOTICE_NO, ROW_NUMBER() OVER (ORDER BY NOTICE_NO DESC) AS ROWNO,"
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
				vo.setNoticeNo(rs.getInt(1));
				vo.setRowNo(rs.getInt(2));
				vo.setNoticeTitle(rs.getString(3));
				vo.setNoticeContent(rs.getString(4));
				vo.setCount(rs.getInt(5));
				vo.setNoticeCreateDate(rs.getDate(6));
				
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
	public NoticeVO getDetail(int no) {
		NoticeVO nvo = new NoticeVO();
		String 	sql = "SELECT * FROM NOTICE "
				+ " WHERE NOTICE_NO = ? ";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1,no);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()) {
				nvo = new NoticeVO();
				nvo.setNoticeNo(rs.getInt("NOTICE_NO"));
				nvo.setNoticeTitle(rs.getString("NOTICE_TITLE"));
				nvo.setNoticeContent(rs.getString("NOTICE_CONTENT"));
				nvo.setNoticeCreateDate(rs.getDate("NOTICE_CREATE_DATE"));
				nvo.setNoticeImageNumber(rs.getInt("NOTICE_IMAGE_NUMBER"));
			}
			ConnectionProvider.close(conn, pstm, rs);
		} catch (Exception e) {
			System.out.println("getDetail Exception : "+e.getMessage());
			e.printStackTrace();
		}
		return nvo;
	}
	
	
	// 글을 선택시 조회수가 증가하게 한다
	public void increaseCount(int no) {
		String sql="UPDATE NOTICE SET COUNT = COUNT + 1 WHERE NOTICE_NO = ?";
		
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, no);
			pstm.executeUpdate();
		} catch (Exception e) {
			System.out.println("increaseCount Exception : "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	// 선택한 게시글의 이미지 목록만 따로 가져오기
	public ArrayList<NoticeImageVO> getImgByNoticeNo (int no){
		ArrayList<NoticeImageVO> list = new ArrayList<>();
		String sql = "SELECT * FROM NOTICE_IMAGE "
				+ "WHERE NOTICE_NO = ? ORDER BY IMAGE_NUMBER ASC";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, no);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				NoticeImageVO nivo = new NoticeImageVO();
				nivo.setNoticeNo(rs.getInt(1));
				nivo.setImageNumber(rs.getInt(2));
				nivo.setFileUrl(rs.getString(3));
				nivo.setUploadTime(rs.getDate(4));
				nivo.setOriginalFileName(rs.getString(5));
				nivo.setFileType(rs.getString(6));
				list.add(nivo);
			}
		} catch (Exception e) {
			System.out.println("getImgByNoticeNo Exception : "+e.getMessage());
			e.printStackTrace();
		}
		return list;
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
	
	
	// NOTICE_IMAGE 테이블에 한 건의 이미지 메타정보를 삽입
    public int insertImage(NoticeImageVO nivo) {
        int re = -1;
        String sql = "INSERT INTO NOTICE_IMAGE "
        		   + " (NOTICE_NO, FILE_URL, UPLOAD_TIME, ORIGINAL_FILE_NAME, FILE_TYPE)"
                   + " VALUES (?, ?, SYSDATE(), ?, ?)";
        try {
    		Connection conn = ConnectionProvider.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);
    			
            psmt.setInt(1, nivo.getNoticeNo());
            psmt.setString(2, nivo.getFileUrl());
            psmt.setString(3, nivo.getOriginalFileName());
            psmt.setString(4, nivo.getFileType());
            re = psmt.executeUpdate();
            
            ConnectionProvider.close(conn, psmt);
        } catch (Exception e) {
        	System.out.println("insertImage Exception : "+e.getMessage());
			e.printStackTrace();
        }
        return re;
    }
	
	
	// 선택한 게시글 수정할 내용을 반환 한다.
	public int editDetail(NoticeVO nvo) {
		int re = -1;
		String sql = "UPDATE NOTICE n SET "
				+ " NOTICE_TITLE = ?, NOTICE_CONTENT = ?, NOTICE_EDIT_DATE = SYSDATE(), NOTICE_IMAGE_NUMBER = ?"
				+ " WHERE NOTICE_NO = ?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			psmt.setString(1, nvo.getNoticeTitle());
			psmt.setString(2, nvo.getNoticeContent());
			psmt.setInt(3, nvo.getNoticeImageNumber());
			psmt.setInt(4,nvo.getNoticeNo());
			re = psmt.executeUpdate();

			ConnectionProvider.close(conn, psmt);
		} catch (Exception e) {
			System.out.println("editDetail Exception : "+e.getMessage());
			e.printStackTrace();
		}
		return re;
	}
	
	
	// 선택한 게시글을 수정시 이미지를 선택적으로 삭제 할경우 결과를 반환한다.
	public int deleteImg(ArrayList<String> fileUrls) {
	    if (fileUrls == null || fileUrls.isEmpty()) return 0;

	    int re = 0;
	    String sql = "DELETE FROM NOTICE_IMAGE WHERE FILE_URL IN (";
	    for (int i = 0; i < fileUrls.size(); i++) {
	        sql += "?";
	        if (i < fileUrls.size() - 1) {
	            sql += ", ";
	        }
	    }
	    sql += ")";

	    try {
	        Connection conn = ConnectionProvider.getConnection();
	        PreparedStatement psmt = conn.prepareStatement(sql);
	        for (int i = 0; i < fileUrls.size(); i++) {
	            psmt.setString(i + 1, fileUrls.get(i));
	        }
	        re = psmt.executeUpdate();
	        ConnectionProvider.close(conn, psmt);
	    } catch (Exception e) {
	        System.out.println("deleteImg Exception: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return re;
	}

	
	// 선택한 게시글을 삭제한다.
	public int setDelete(int no) {
		int re = -1;
		String sql = "UPDATE NOTICE SET IS_DELETED = 'Y', IS_VISIBLE = 'N', NOTICE_DELETE_DATE = SYSDATE() WHERE NOTICE_NO = ?";
		
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, no);
			re = pstm.executeUpdate();
		} catch (Exception e) {
			System.out.println("setDelete Exception: " + e.getMessage());
	        e.printStackTrace();
		}
		return re;
	}
	
	
	// 공지사항 중 최근 글 2건을 반환한다.(썸네일 포함)
	public ArrayList<NoticeVO> getNewNotice() {
		ArrayList<NoticeVO> list = new ArrayList<>();
		String sql = "SELECT n.NOTICE_TITLE, n.NOTICE_CONTENT, n.NOTICE_IMAGE_NUMBER, ni.FILE_URL "
				+ " FROM NOTICE n LEFT JOIN ( "
				+ "	SELECT NOTICE_NO, FILE_URL "
				+ "	FROM NOTICE_IMAGE ni "
				+ "	WHERE IMAGE_NUMBER IN ( "
				+ "		SELECT MIN(IMAGE_NUMBER) "
				+ "		FROM NOTICE_IMAGE "
				+ "		GROUP BY NOTICE_NO "
				+ "	) "
				+ " )ni ON n.NOTICE_NO = ni.NOTICE_NO "
				+ " WHERE n.IS_VISIBLE = 'Y' "
				+ " ORDER BY  n.NOTICE_CREATE_DATE DESC "
				+ " LIMIT 2 ";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				NoticeVO nvo = new NoticeVO();
				nvo.setNoticeTitle(rs.getString(1));
				nvo.setNoticeContent(rs.getString(2));
				nvo.setNoticeImageNumber(rs.getInt(3));
			
				NoticeImageVO nivo = new NoticeImageVO();
				nivo.setFileUrl(rs.getString(4));
				
				nvo.setNoticeImageVO(nivo);
				list.add(nvo);
			}
		} catch (Exception e) {
			System.out.println("getNewNotice Exception : "+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
}
