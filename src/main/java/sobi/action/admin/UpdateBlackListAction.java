package sobi.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;
import sobi.dao.admin.BlackListDAO;
import sobi.dao.admin.BlackListHistoryDAO;
import sobi.vo.admin.BlackListVO;

public class UpdateBlackListAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BlackListDAO blackListdao = new BlackListDAO();
		BlackListHistoryDAO blackListHistorydao = new BlackListHistoryDAO();
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("memberId");
		int re = blackListdao.updateBlackList(memberId, "unblocked");
		
		if(re == 1) {
			int blackListNo = blackListdao.getBlackListByMemberId(memberId).getBlackListNo();
			blackListHistorydao.insertBlackListHistory(memberId, blackListNo,"해제");
			List<BlackListVO> list = blackListdao.getAllBlackList(); // 모든 블랙리스트 가져오기
			request.setAttribute("list", list);
			request.setAttribute("title", "블랙리스트");
			return "/v1/views/admin/getBlackList.jsp";

		}
		return null;
	}

}
