<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<main>
<div class="mypage-wrapper" style="display: flex; padding: 20px;">
    
    <!-- 좌측 서브 메뉴 -->
    <jsp:include page="mypage_submenu.jsp" />
     <section class="mypage-content" style="flex-grow: 1; margin-left: 40px;">
	<h2>📤 보낸 쪽지함</h2>

	<c:if test="${empty messageList}">
		<p>보낸 쪽지가 없습니다.</p>
	</c:if>

	<form method="post" action="deleteSent.do" onsubmit="return confirmDelete();">
		

		
		<div style="margin: 20px 0 10px 0; display: flex; gap: 10px;">
			<input type="submit" value="🗑 선택 삭제"
			       style="all: unset; padding: 8px 16px; font-size: 14px; height: 38px; line-height: 1.5; border: 1px solid #ccc; background-color: white; cursor: pointer;">
			<button type="button" onclick="openComposePopup()"
			        style="padding: 8px 16px; font-size: 14px; height: 38px; line-height: 1.5; border: 1px solid #ccc; background-color: white; cursor: pointer;">
				✏ 쪽지 쓰기
			</button>
		</div>

		<c:if test="${not empty messageList}">
			<table border="1" width="100%" cellpadding="8" style="border-collapse: collapse; margin-top: 0;">
				<thead>
					<tr>
						<th><input type="checkbox" onclick="toggleAll(this)"></th>
						<th>받는 사람</th>
						<th>제목</th>
						<th>보낸 날짜</th>
						<th>읽음 여부</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="msg" items="${messageList}">
						<tr>
							<td><input type="checkbox" name="messageId" value="${msg.messageId}"></td>
							<td>${msg.receiverId}</td>
							<td><a href="javascript:void(0);" onclick="openMessagePopup(${msg.messageId})">${msg.messageTitle}</a></td>
							<td><fmt:formatDate value="${msg.messageSendDate}" pattern="yyyy-MM-dd HH:mm" /></td>
							<td>
								<c:choose>
									<c:when test="${msg.messageIsRead eq 'Y'}">
										<span style="color: gray;">읽음</span>
									</c:when>
									<c:otherwise>
										<span style="color: blue; font-weight: bold;">안읽음</span>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<c:if test="${not empty paging}">
  <div class="pagination" style="text-align:center; margin-top:20px;">
    <c:if test="${paging.hasPrev()}">
      <a href="sent.do?page=${paging.startPage - 1}">&lt;</a>
    </c:if>

    <c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="i">
      <a href="sent.do?page=${i}" style="${i == paging.nowPage ? 'font-weight:bold; color:red;' : ''}">
        ${i}
      </a>
    </c:forEach>

    <c:if test="${paging.hasNext()}">
      <a href="sent.do?page=${paging.endPage + 1}">&gt;</a>
    </c:if>
  </div>
</c:if>
		
	</form>
  </section>
    
  </div>
<script>
function openComposePopup() {
	const width = 800;
	const height = 600;
	const left = (window.screen.width - width) / 2;
	const top = (window.screen.height - height) / 2;
	const popup = window.open('composeForm.jsp', 'composeWindow',
		`width=${width},height=${height},top=${top},left=${left},scrollbars=yes,resizable=yes`);
	if (popup) popup.focus();
}

function openMessagePopup(messageId) {
	const width = 800;
	const height = 600;
	const left = (window.screen.width - width) / 2;
	const top = (window.screen.height - height) / 2;
	window.open('messageView.jsp?messageId=' + messageId, 'messagePopup',
		`width=${width},height=${height},left=${left},top=${top},scrollbars=yes,resizable=yes`);
}

function toggleAll(source) {
	const checkboxes = document.getElementsByName("messageId");
	for (let i = 0; i < checkboxes.length; i++) {
		checkboxes[i].checked = source.checked;
	}
}

function confirmDelete() {
	const checked = document.querySelectorAll("input[name='messageId']:checked");
	if (checked.length === 0) {
		alert("삭제할 쪽지를 선택하세요.");
		return false;
	}
	return confirm("선택한 쪽지를 삭제하시겠습니까?");
}
</script>
</main>
