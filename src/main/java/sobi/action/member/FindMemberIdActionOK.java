package sobi.action.member;

import com.google.gson.JsonObject;
import sobi.action.common.SobiAction;
import sobi.dao.member.MemberDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FindMemberIdActionOK implements SobiAction {
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("utf-8");
    response.setContentType("application/json; charset=UTF-8");
    
    String memberName = request.getParameter("memberName");
    String memberEmail = request.getParameter("memberEmail");

    
    MemberDAO dao = new MemberDAO();
    String memberId = dao.findMemberId(memberName, memberEmail);
    
    
    JsonObject json = new JsonObject();


    // memberId가 존재하고 공백이 아닌 경우 → 회원 아이디 찾기 성공
    if (memberId != null && !memberId.trim().isEmpty()) {
      // 응답 JSON에 status = "success" 추가
      json.addProperty("status", "success");
      // 응답 JSON에 찾은 memberId 값을 추가
      json.addProperty("memberId", memberId);
    } else {
      // 아이디를 찾지 못한 경우 → 실패 상태만 응답
      json.addProperty("status", "fail");
    }

    // 응답 출력 스트림 가져오기
    PrintWriter out = response.getWriter();

    // JSON 객체를 문자열로 변환하여 응답 본문에 출력
    // 예: {"status":"success","memberId":"dengo"}
    out.print(json.toString());
    
    return null;
  }
}
