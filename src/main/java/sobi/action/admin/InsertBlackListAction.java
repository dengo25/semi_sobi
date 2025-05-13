package sobi.action.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;
import sobi.dao.admin.BlackListDAO;
import sobi.dao.admin.BlackListHistoryDAO;
import sobi.vo.admin.BlackListVO;

public class InsertBlackListAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("memberId");
		String reportType = request.getParameter("reportType");
		String description = request.getParameter("description");
	
		BlackListDAO blackListdao = new BlackListDAO();
		BlackListHistoryDAO historydao = new BlackListHistoryDAO();
		
		boolean isSuccess = false;
		int isBlackList = blackListdao.isBlackList(memberId);
		request.setAttribute("title", "블랙리스트");
		if(isBlackList != 1) {
			isSuccess = blackListdao.insertBlackList(memberId, reportType, "blocked") == 1;
		} else {
			isSuccess = blackListdao.updateBlackList(memberId, reportType, "blocked") == 1;
		}
		
		//실패 시 
		if (!isSuccess) {
			request.setAttribute("memberId", memberId); // 다시 넘겨줘야 함
			return "/v1/views/admin/blackListDetail.jsp";
		}
		
		// 성공 시 히스토리 저장
		BlackListVO blackList = blackListdao.getBlackListByMemberId(memberId);
		System.out.println("블랙리스트 번호: " + blackList.getBlackListNo());

		historydao.insertBlackListHistory(memberId, blackList.getBlackListNo(), description);

		return "/v1/views/admin/getBlackList.jsp";
	}

}
