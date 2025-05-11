package sobi.action.community;

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
import sobi.dao.community.NoticeDAO;
import sobi.util.s3.S3Uploader;
import sobi.vo.community.NoticeImageVO;
import sobi.vo.community.NoticeVO;
import sobi.vo.member.MemberVO;

@MultipartConfig  // multipart/form-data 파싱을 위해 필요
public class NoticeEditActionOK implements SobiAction {
    private static final int MAX_UPLOAD_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String TEMP_DIR = "/uploads/tmp";
    
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("member");

		if (member == null || !"A".equals(member.getRole())) {
		    request.setAttribute("errorMsg", "관리자만 글을 수정할 수 있습니다.");
		    return "/v1/views/community/notice.jsp";
		}
		
		// MultipartRequest 파싱
		String saveDir = request.getServletContext().getRealPath(TEMP_DIR);
		MultipartRequest multi = new MultipartRequest(
			request,
			saveDir,
			MAX_UPLOAD_SIZE,
			"utf-8",
			new DefaultFileRenamePolicy()
		);
		
		// 파라미터 가져오기 
		String noticeNoStr = multi.getParameter("noticeNo");
		String noticeTitle = multi.getParameter("noticeTitle");
		String noticeContent = multi.getParameter("noticeContent");
		/*
		System.out.println("noticeNo = " + noticeNoStr);
		System.out.println("noticeTitle = " + noticeTitle);
		System.out.println("noticeContent = " + noticeContent);
		*/
		
		int noticeNo    = Integer.parseInt(noticeNoStr);
		
		// 이미지 처리 (Toast UI Editor에서 업로드된 tmp 경로 S3로 이동)
		ArrayList<NoticeImageVO> images = new ArrayList<>();
        Pattern pattern = Pattern.compile("/uploads/tmp/([^\"]+)");
        Matcher matcher = pattern.matcher(noticeContent);

        while (matcher.find()) {
            String filename = matcher.group(1);
            File tmpFile = new File(saveDir, filename);

            if (tmpFile.exists()) {
                String s3Url = S3Uploader.upload(tmpFile, "notice");
                noticeContent = noticeContent.replace("/uploads/tmp/" + filename, s3Url);

                NoticeImageVO imgVo = new NoticeImageVO();
                imgVo.setNoticeNo(noticeNo);
                imgVo.setOriginalFileName(filename);
                imgVo.setFileUrl(s3Url);
                imgVo.setFileType(Files.probeContentType(tmpFile.toPath()));
                images.add(imgVo);

                tmpFile.delete();
            }
        }
        
        int imageCount = 0;
        Pattern countPattern = Pattern.compile("https://[^\"]+");
        Matcher countMatcher = countPattern.matcher(noticeContent);
        while (countMatcher.find()) {
        	imageCount++;
        }
    
		NoticeVO nvo = new NoticeVO();
		nvo.setNoticeNo(noticeNo);
		nvo.setNoticeTitle(noticeTitle);
		nvo.setNoticeContent(noticeContent);
		nvo.setNoticeImageNumber(imageCount);

		NoticeDAO dao = new NoticeDAO();
		
		// 기존 이미지 조회
        ArrayList<NoticeImageVO> beforeImages = dao.getImgByNoticeNo(noticeNo);

        // 현재 content에 남아있는 S3 이미지 URL
        ArrayList<String> remainUrls = new ArrayList<>();
        Pattern s3Pattern = Pattern.compile("https://[^\"]+");
        Matcher remainMatcher = s3Pattern.matcher(noticeContent);
        while (remainMatcher.find()) {
            remainUrls.add(remainMatcher.group());
        }

        // 삭제 대상 이미지 구분
        ArrayList<String> filesToDelete = new ArrayList<>();
        for (NoticeImageVO img : beforeImages) {
            if (!remainUrls.contains(img.getFileUrl())) {
                filesToDelete.add(img.getFileUrl());
            }
        }

        // DB 및 S3에서 삭제
        if (!filesToDelete.isEmpty()) {
            int deleted = dao.deleteImg(filesToDelete);
            System.out.println("️삭제된 이미지 수: " + deleted);
            for (String url : filesToDelete) {
                S3Uploader.delete(url);  // 실제 S3 삭제 로직 필요
            }
        }
		
		int result = dao.editDetail(nvo);
		if (result > 0) { // 수정 성공 시 
			for (NoticeImageVO img : images) {
                dao.insertImage(img);
            }
			response.sendRedirect("noticeDetail.do?noticeNo=" + noticeNo);
			return null;
		} else { // 수정 실패 시
			request.setAttribute("errorMsg", "게시글 수정에 실패했습니다.");
			return "/v1/views/community/noticeWriteCheck.jsp";
		}
	}
}
