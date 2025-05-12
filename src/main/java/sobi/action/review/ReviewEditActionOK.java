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
import sobi.dao.review.ReviewImageDAO;
import sobi.util.s3.S3Uploader;
import sobi.vo.member.MemberVO;
import sobi.vo.review.ReviewImageVO;
import sobi.vo.review.ReviewVO;

@MultipartConfig
public class ReviewEditActionOK implements SobiAction {
  private static final int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;
  private static final String TEMP_DIR = "/uploads/tmp";
  
  private final ReviewDAO reviewDao = new ReviewDAO();
  private final ReviewImageDAO imageDao = new ReviewImageDAO();
  
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    HttpSession session = request.getSession();
    MemberVO member = (MemberVO) session.getAttribute("member");
    
    if (member == null) {
      request.setAttribute("errorMsg", "로그인이 필요합니다.");
      return "/v1/views/member/login.jsp";
    }
    
    String memberId = member.getMemberId();
    String saveDir = request.getServletContext().getRealPath(TEMP_DIR);
    
    MultipartRequest multi = new MultipartRequest(
        request, saveDir, MAX_UPLOAD_SIZE, "utf-8", new DefaultFileRenamePolicy());
    
    int reviewId = Integer.parseInt(multi.getParameter("reviewId"));
    String title = multi.getParameter("reviewTitle");
    String productName = multi.getParameter("productName");
    String content = multi.getParameter("reviewContent");
    int rating = Integer.parseInt(multi.getParameter("rating"));
    int categoryId = Integer.parseInt(multi.getParameter("reviewCategoryId"));
    
    // 🔍 이미지 파싱
    ArrayList<ReviewImageVO> newImages = new ArrayList<>();
    Pattern pattern = Pattern.compile("/uploads/tmp/([^\"]+)");
    Matcher matcher = pattern.matcher(content);
    
    String thumbnailUrl = null;
    Matcher s3Matcher = Pattern.compile("https://[^\"]+").matcher(content);
    if (s3Matcher.find()) {
      thumbnailUrl = s3Matcher.group();  // 기존 이미지 중 첫 번째 이미지 → 대표 이미지로 사용
    }
    
    
    while (matcher.find()) {
      String filename = matcher.group(1);
      File tmpFile = new File(saveDir, filename);
      
      if (tmpFile.exists()) {
        String s3Url = S3Uploader.upload(tmpFile, "review");
        content = content.replace("/uploads/tmp/" + filename, s3Url);

        
        ReviewImageVO imageVO = new ReviewImageVO();
        imageVO.setReviewId(reviewId);
        imageVO.setOriginalFileName(filename);
        imageVO.setFileUrl(s3Url);
        imageVO.setFileType(Files.probeContentType(tmpFile.toPath()));
        newImages.add(imageVO);
        
        tmpFile.delete();
      }
    }
    
    // 기존 이미지 가져오기
    ArrayList<ReviewImageVO> oldImages = imageDao.getImagesByReviewId(reviewId);
    
    // 본문에 남아있는 S3 이미지 URL 확인
    ArrayList<String> remainUrls = new ArrayList<>();
    Matcher remainMatcher = Pattern.compile("https://[^\"]+").matcher(content);
    while (remainMatcher.find()) {
      remainUrls.add(remainMatcher.group());
    }
    
    // 삭제할 이미지 URL 찾기
    ArrayList<String> toDeleteUrls = new ArrayList<>();
    for (ReviewImageVO img : oldImages) {
      if (!remainUrls.contains(img.getFileUrl())) {
        toDeleteUrls.add(img.getFileUrl());
      }
    }
    
    // DB 및 S3 이미지 삭제
    if (!toDeleteUrls.isEmpty()) {
      imageDao.deleteImages(toDeleteUrls);

    }
    
    // DB 업데이트
    ReviewVO review = new ReviewVO();
    review.setReviewId(reviewId);
    review.setMemberId(memberId);
    review.setReviewTitle(title);
    review.setProductName(productName);
    review.setContent(content);
    review.setRating(rating);
    review.setReviewCategoryId(categoryId);
    review.setImageURL(thumbnailUrl);
    
    reviewDao.updateReview(review);
    
    for (ReviewImageVO img : newImages) {
      imageDao.insertReviewImage(img);
    }
    
    return "redirect:reviewDetail.do?reviewId=" + reviewId;
  }
}
