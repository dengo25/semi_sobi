package s3.util;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import sobi.review.dao.ReviewDAO;
import sobi.review.dao.ReviewImageDAO;
import sobi.review.vo.ReviewImageVO;
import sobi.review.vo.ReviewVO;

import java.io.*;
import java.util.*;
import java.util.regex.*;

@WebServlet("/WriteReview.v1") // /WriteReview.v1 경로로 들어오는 요청을 이 서블릿에서 처리
@MultipartConfig // 파일 업로드를 처리하기 위해 multipart/form-data를 처리할 수 있게 해줌
public class WriteReviewServlet extends HttpServlet {
  
  private static final String TEMP_DIR = "/uploads/tmp";  // 파일 업로드 디렉토리 경로
  private final ReviewDAO reviewDao = new ReviewDAO();  // ReviewDAO 객체 생성 (리뷰 데이터베이스 작업 처리)
  private final ReviewImageDAO imageDao = new ReviewImageDAO();  // ReviewImageDAO 객체 생성 (리뷰 이미지 데이터베이스 작업 처리)
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    
    req.setCharacterEncoding("utf-8");  // 요청 파라미터의 문자 인코딩을 UTF-8로 설정
    
    // 클라이언트에서 전송한 폼 데이터 가져오기
    String memberId = req.getParameter("member_id");
    String productName = req.getParameter("product_name");
    String reviewTitle = req.getParameter("review_title");
    int rating = Integer.parseInt(req.getParameter("rating"));  // 평점
    int reviewCategoryId = Integer.parseInt(req.getParameter("review_category_id"));  // 카테고리 ID
    String content = req.getParameter("content");  // 리뷰 내용
    
    try {
      // 실제 저장된 webapp 기준 경로로 설정
      String realPath = req.getServletContext().getRealPath("/uploads/tmp");
      System.out.println("== 리뷰 작성 시 탐색 경로 ==");
      System.out.println("realPath: " + realPath);  // 디버깅을 위해 실제 경로 출력
      
      // /uploads/tmp/ 경로에서 파일명을 찾는 정규식 패턴
      Pattern pattern = Pattern.compile("/uploads/tmp/([^\"\\s]+)");
      Matcher matcher = pattern.matcher(content);  // 리뷰 내용에서 이미지 경로를 찾음
      
      StringBuffer newContent = new StringBuffer();  // 새로운 리뷰 내용
      List<ReviewImageVO> imageList = new ArrayList<>();  // 리뷰 이미지 정보를 담을 리스트
      
      while (matcher.find()) {
        // 찾은 이미지의 파일명을 추출
        String fileName = matcher.group(1);
        File tempFile = new File(realPath, fileName);  // 해당 파일이 서버에 존재하는지 확인
        
        // 디버깅용 로그: 찾은 이미지 경로 출력
        System.out.println("== 찾은 이미지 src 경로 ==");
        System.out.println("원본 매칭: " + matcher.group(0));
        System.out.println("파일명: " + fileName);
        System.out.println("실제 파일 경로: " + tempFile.getAbsolutePath());
        System.out.println("파일 존재 여부: " + tempFile.exists());
        
        if (tempFile.exists()) {
          // 이미지가 서버에 존재하면 S3에 업로드하고 URL을 얻음
          String s3Url = S3Uploader.upload(tempFile);  // S3에 업로드 후 URL 반환
          matcher.appendReplacement(newContent, Matcher.quoteReplacement(s3Url));  // 이미지 URL로 내용 교체
          
          // 파일의 MIME 타입을 얻어온다 (이미지의 형식)
          String mimeType = req.getServletContext().getMimeType(tempFile.getAbsolutePath());
          
          // 이미지 정보를 ReviewImageVO 객체에 저장
          ReviewImageVO image = new ReviewImageVO();
          image.setFileUrl(s3Url);  // S3 URL
          image.setOriginalFileName(fileName);  // 원본 파일명
          image.setFileType(mimeType != null ? mimeType : "application/octet-stream");  // 파일 타입 (없으면 기본값 설정)
          
          imageList.add(image);  // 이미지 리스트에 추가
          tempFile.delete();  // 서버에 있는 임시 파일 삭제
        }
      }
      matcher.appendTail(newContent);  // 나머지 내용 추가
      String finalContent = newContent.toString();  // 최종 리뷰 내용
      
      // 첫 번째 이미지를 썸네일로 설정 (없으면 null)
      String thumbnail = imageList.isEmpty() ? null : imageList.get(0).getFileUrl();
      
      // ReviewVO 객체 생성 후 클라이언트에서 받은 데이터를 설정
      ReviewVO review = new ReviewVO();
      review.setMemberId(memberId);
      review.setProductName(productName);
      review.setTitle(reviewTitle);
      review.setRating(rating);
      review.setCategoryId(reviewCategoryId);
      review.setContent(finalContent);  // 수정된 내용
      review.setImageUrl(thumbnail);  // 썸네일 이미지 URL
      
      // 리뷰를 데이터베이스에 삽입하고 리뷰 ID 반환
      int reviewId = reviewDao.insertReview(review);   
      // 이미지 정보들을 ReviewImageVO 객체에 저장하고 데이터베이스에 삽입
      for (ReviewImageVO image : imageList) {
        image.setReviewId(reviewId);  // 리뷰 ID 설정
        imageDao.insertImage(image);  // 이미지 데이터베이스에 삽입
      }
      
      // 클라이언트에 성공 메시지와 함께 main.jsp로 리디렉션
      resp.setContentType("text/html;charset=UTF-8");
      PrintWriter out = resp.getWriter();
      out.println("<script>alert('리뷰 작성 완료!'); location.href='main.jsp';</script>");
      
    } catch (Exception e) {
      e.printStackTrace();  // 예외 발생 시 콘솔에 출력
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 서버 오류 응답
    }
  }
}
