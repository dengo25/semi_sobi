package sobi.action.review;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import sobi.action.common.SobiAction;
import sobi.dao.review.ReviewDAO;
import sobi.dao.review.ReviewImageDAO; // ✅ 추가
import sobi.util.s3.S3Uploader;
import sobi.vo.member.MemberVO;
import sobi.vo.review.ReviewImageVO;
import sobi.vo.review.ReviewVO;

@MultipartConfig
public class ReviewWriteActionOK implements SobiAction {
  
  private static final int MAX_UPLOAD_SIZE = 5 * 1024 * 1024; // 5MB
  private static final String TEMP_DIR = "/uploads/tmp";
  
  private final ReviewDAO reviewDao = new ReviewDAO();
  private final ReviewImageDAO reviewImageDao = new ReviewImageDAO(); // ✅ 추가
  
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    
    HttpSession session = request.getSession();
    MemberVO member = (MemberVO) session.getAttribute("member");
    
    // 로그인 체크 -> 이미 글 쓸 때 체크를해서 필요없을 듯
//    if (member == null) {
//      request.setAttribute("errorMsg", "로그인이 필요합니다.");
//      return "/v1/views/member/login.jsp";
//    }
    
    String memberId = member.getMemberId();
    
    // Multipart 처리
    String saveDir = request.getServletContext().getRealPath(TEMP_DIR);
    MultipartRequest multi = new MultipartRequest(
        request, saveDir, MAX_UPLOAD_SIZE, "utf-8", new DefaultFileRenamePolicy());
    
    // 폼 데이터 수집
    String title = multi.getParameter("reviewTitle");
    String productName = multi.getParameter("productName");
    String content = multi.getParameter("reviewContent");
    int rating = Integer.parseInt(multi.getParameter("rating"));
    int categoryId = Integer.parseInt(multi.getParameter("reviewCategoryId"));
    
    // 이미지 경로 추출 및 S3 업로드
    ArrayList<ReviewImageVO> imageList = new ArrayList<>();
    Pattern pattern = Pattern.compile("/uploads/tmp/([^\"]+)");
    Matcher matcher = pattern.matcher(content);
    
    while (matcher.find()) {
      String filename = matcher.group(1);
      File tmpFile = new File(saveDir, filename);
      
      if (tmpFile.exists()) {
        // S3 업로드
        String s3Url = S3Uploader.upload(tmpFile, "review");
        
        // 본문 내 이미지 경로 치환
        content = content.replace("/uploads/tmp/" + filename, s3Url);
        
        // 이미지 메타 정보 생성
        ReviewImageVO imageVO = new ReviewImageVO();
        imageVO.setOriginalFileName(filename);
        imageVO.setFileUrl(s3Url);
        imageVO.setFileType(Files.probeContentType(tmpFile.toPath()));
        imageList.add(imageVO);
        
        tmpFile.delete(); // 임시 파일 삭제
      }
    }
    
    // 리뷰 정보 저장
    ReviewVO review = new ReviewVO();
    review.setMemberId(memberId);
    review.setReviewTitle(title);
    review.setProductName(productName);
    review.setContent(content);
    review.setRating(rating);
    review.setReviewCategoryId(categoryId);
    
    int reviewId = reviewDao.insertReview(review);
    
    // 이미지 정보 저장
    for (ReviewImageVO img : imageList) {
      img.setReviewId(reviewId);
      reviewImageDao.insertReviewImage(img); // ✅ 여기가 핵심!
    }
    
    // 완료 페이지로 이동
    request.setAttribute("writeInfo", reviewId);
    return "redirect:review.do";
  }
}
