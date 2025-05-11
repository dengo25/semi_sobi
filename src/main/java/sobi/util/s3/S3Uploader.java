package sobi.util.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

public class S3Uploader {
  
  // S3 버킷 이름 (kosta-blog)
  private static final String BUCKET_NAME = "kosta-blog";
  
  // S3 버킷이 속한 AWS 리전
  private static final String REGION = "ap-northeast-2"; // 서울 리전
  
  // S3 클라이언트 객체 생성
  private static final AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
      .withRegion(REGION)
      .build();
  
  /**
   * 로컬 파일을 S3에 업로드하고, 퍼블릭 URL을 반환하는 메서드
   *
   * @param file 업로드할 파일 객체
   * @return 업로드된 S3 파일의 URL
   * @throws Exception 예외 발생 시 상위에서 처리
   */
  public static String upload(File file, String folder) throws Exception {
    // 파일명을 UUID로 고유하게 생성하여 중복 방지
    String fileName = folder + "/" + UUID.randomUUID() + "_" + file.getName();  // "폴더명 /" 폴더에 저장
    
    // try-with-resources로 파일 스트림 자동 종료 처리
    try (InputStream inputStream = new FileInputStream(file)) {
      
      // MIME 타입 추출 (예: image/png, image/jpeg 등)
      String mimeType = Files.probeContentType(file.toPath());
      
      // S3에 업로드할 파일의 메타데이터 설정 (크기 명시, MIME 타입 설정)
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(file.length());
      metadata.setContentType(mimeType != null ? mimeType : "application/octet-stream");
      
      // S3 업로드 요청 생성
      PutObjectRequest request = new PutObjectRequest(
          BUCKET_NAME,          // 버킷 이름
          fileName,             // 객체 키 (review 폴더 안에 파일명 저장)
          inputStream,          // 파일 스트림
          metadata              // 메타데이터
      ).withCannedAcl(CannedAccessControlList.PublicRead); // 퍼블릭 읽기 권한 설정
      
      // 실제 업로드 실행
      s3Client.putObject(request);
      
      // 업로드된 객체의 퍼블릭 URL 반환
      return "https://" + BUCKET_NAME + ".s3." + REGION + ".amazonaws.com/" + fileName;
    }
  }
  
  
  /**
   * S3에서 특정 파일 URL에 해당하는 객체 삭제
   */
  public static void delete(String fileUrl) {
      try {
          String key = extractKeyFromUrl(fileUrl);
          s3Client.deleteObject(BUCKET_NAME, key);
          System.out.println("S3 이미지 삭제 완료: " + key);
      } catch (Exception e) {
          System.out.println("S3Uploader.delete 실패: " + e.getMessage());
          e.printStackTrace();
      }
  }
  
  
  /**
   * S3 퍼블릭 URL에서 key 경로만 추출
   * 예: https://kosta-blog.s3.ap-northeast-2.amazonaws.com/notice/abc.png → notice/abc.png
   */
  private static String extractKeyFromUrl(String url) {
      String baseUrl = "https://" + BUCKET_NAME + ".s3." + REGION + ".amazonaws.com/";
      if (!url.startsWith(baseUrl)) {
          throw new IllegalArgumentException("S3 URL 형식이 올바르지 않습니다: " + url);
      }
      return url.substring(baseUrl.length());
  }
}