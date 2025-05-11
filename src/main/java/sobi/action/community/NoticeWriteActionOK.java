package sobi.action.community;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import sobi.action.common.SobiAction;
import sobi.dao.community.NoticeDAO;
import sobi.util.s3.S3Uploader;
import sobi.vo.community.NoticeImageVO;
import sobi.vo.community.NoticeVO;
import sobi.vo.member.MemberVO;

@MultipartConfig  // multipart/form-data 파싱을 위해 필요
public class NoticeWriteActionOK implements SobiAction {
    private static final int MAX_UPLOAD_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String TEMP_DIR = "/uploads/tmp";

    private final NoticeDAO noticeDao = new NoticeDAO();

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        MemberVO member = (MemberVO) session.getAttribute("member");
        String memberId = member.getMemberId();

        // 1) MultipartRequest 로 임시 폴더에 업로드
        String saveDir = request.getServletContext().getRealPath(TEMP_DIR);
        MultipartRequest multi = new MultipartRequest(
            request, 
            saveDir, 
            MAX_UPLOAD_SIZE, 
            "utf-8", 
            new DefaultFileRenamePolicy()
        );

        // 2) 폼값 꺼내기
        String title   = multi.getParameter("noticeTitle");
        String content = multi.getParameter("noticeContent");
        int fileNumber = Integer.parseInt(multi.getParameter("noticeImageNumber")); 

        // System.out.println("📄 제목: " + title);
        // System.out.println("📝 원본 content (치환 전): " + content);
        // System.out.println("📎 fileNumber: " + fileNumber);
        
        
        // 3) 업로드된 파일들 S3에 올리고, HTML 내 src 교체
        List<NoticeImageVO> images = new ArrayList<>();
        Pattern pattern = Pattern.compile("/uploads/tmp/([^\"]+)");
        Matcher matcher = pattern.matcher(content);
        
        // Enumeration<String> files = multi.getFileNames();
        // while (files.hasMoreElements()) {
        while(matcher.find()) {
            // String fieldName = files.nextElement();
            // File   tmpFile   = multi.getFile(fieldName);
        	String filename = matcher.group(1);
            File tmpFile = new File(saveDir, filename);
            
            // System.out.println("🖼️ 추출된 파일명: " + filename);
            // System.out.println("📂 임시 파일 경로 존재?: " + tmpFile.exists());
            
            // if (tmpFile != null) {
            if (tmpFile.exists()) {
                // S3에 업로드
                String s3Url = S3Uploader.upload(tmpFile,"notice");
                // System.out.println("☁️ S3 업로드 완료: " + s3Url);
                
                // content 내 임시 경로 → S3 URL 치환
                // content = content.replace(TEMP_DIR + "/" + tmpFile.getName(),s3Url);
                content = content.replace("/uploads/tmp/" + filename, s3Url);
                // System.out.println("✅ 최종 content (치환 후): " + content);
                
                // 이미지 메타 VO 생성
                NoticeImageVO imgVo = new NoticeImageVO();
                // imgVo.setOriginalFileName(tmpFile.getName());
                imgVo.setOriginalFileName(filename);
                imgVo.setFileUrl(s3Url);
                // imgVo.setFileType(multi.getContentType(fieldName));
                imgVo.setFileType(Files.probeContentType(tmpFile.toPath()));
                images.add(imgVo);

                // 임시 파일 지우기
                tmpFile.delete();
            }
        }

        // 4) 공지글 저장 (setDetail 에서 방금 생성된 PK를 반환하도록 수정하세요)
        NoticeVO notice = new NoticeVO();
        // notice.setMemberId(memberId);
        notice.setNoticeTitle(title);
        notice.setNoticeContent(content);
        notice.setNoticeImageNumber(fileNumber);
        int noticeId = noticeDao.setDetail(notice, memberId);
        // System.out.println("🆔 저장된 Notice ID: " + noticeId);
        
        // 5) 이미지 정보 DB에 저장
        for (NoticeImageVO img : images) {
        	// System.out.println("📤 이미지 메타 저장 - 파일명: " + img.getOriginalFileName());
            // System.out.println("📤 이미지 URL: " + img.getFileUrl());
            // System.out.println("📤 이미지 타입: " + img.getFileType());
            
            img.setNoticeNo(noticeId);
            noticeDao.insertImage(img);
        }

        // 6) forward
        request.setAttribute("writeInfo", noticeId);
        // System.out.println("fileNumber : "+fileNumber);
        return "/v1/views/community/noticeWriteCheck.jsp";
    }
}
