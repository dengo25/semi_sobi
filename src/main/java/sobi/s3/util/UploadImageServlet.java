package sobi.s3.util;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

// 이 서블릿은 /UploadImage 경로로 들어오는 요청을 처리합니다.
// multipart/form-data를 처리하기 위한 설정 (파일 업로드를 위한 설정)
@WebServlet("/UploadImage")
@MultipartConfig
public class UploadImageServlet extends HttpServlet {
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    
    // 응답 타입을 텍스트 형식으로 설정하고, 문자 인코딩을 UTF-8로 지정
    resp.setContentType("text/plain;charset=UTF-8");
    
    try {
      // 클라이언트에서 업로드된 파일을 Part 객체로 가져옴
      // name="uploadFile"에 해당하는 파일을 가져옵니다.
      Part filePart = req.getPart("uploadFile");
      
      // 파일이 없거나 비어 있으면 오류 응답을 반환
      if (filePart == null || filePart.getSize() == 0) {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 Bad Request 상태 코드
        resp.getWriter().write("No file uploaded"); // 업로드된 파일이 없다는 메시지 반환
        return;
      }
      
      // 톰캣 서버의 실제 경로로 업로드 폴더 경로를 설정
      String uploadPath = req.getServletContext().getRealPath("/uploads/tmp");
      File uploadFolder = new File(uploadPath);
      
      // 만약 해당 폴더가 없다면 폴더를 생성
      if (!uploadFolder.exists()) {
        uploadFolder.mkdirs();
      }
      
      // 파일 이름을 UUID로 변환하여 중복을 방지한 뒤, 원래의 파일 이름을 붙임
      String fileName = UUID.randomUUID() + "_" + filePart.getSubmittedFileName();
      
      // filePart.write()를 사용하여 톰캣 서버의 uploads/tmp 폴더에 실제 파일을 저장
      filePart.write(uploadPath + File.separator + fileName);
      
      // 업로드된 파일의 URL을 클라이언트에 반환
      // 예: /uploads/tmp/파일이름
      String imageUrl = req.getContextPath() + "/uploads/tmp/" + fileName;
      resp.getWriter().write(imageUrl);  // 클라이언트에 이미지 URL을 반환
      
    } catch (Exception e) {
      // 예외가 발생하면 콘솔에 출력하고, 서버 오류 응답을 반환
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error 상태 코드
      resp.getWriter().write("Upload failed");  // 업로드 실패 메시지 반환
    }
  }
}