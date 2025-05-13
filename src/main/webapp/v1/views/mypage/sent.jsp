<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<main>
	<div class="mypage-wrapper">

		<!-- 좌측 서브 메뉴 -->
		<jsp:include page="mypage_submenu.jsp" />
		<section class="mypage-content">
			<h2>📤 보낸 쪽지함</h2>

			<c:if test="${empty messageList}">
				<p>보낸 쪽지가 없습니다.</p>
			</c:if>
			<div class="scroll-area">
				<div class="scroll-wrap">
					<form method="post" action="deleteSent.do"
						onsubmit="return confirmDelete();">
						<c:if test="${not empty messageList}">
							<table class="table-basic">
								<caption>보낸쪽지 테이블 목록 - 체크박스, 받는 사람, 제목, 보낸 날짜, 읽음 여부로
									구성</caption>
								<colgroup>
									<col style="width: 15%">
									<col style="width: 20%">
									<col style="width: 27%">
									<col style="width: 18%">
									<col style="width: 20%">
								</colgroup>
								<thead>
									<tr>
										<th><label for="checkAll" class="input-check type-only">
												<input type="checkbox" id="checkAll" class="form-control"
												onclick="toggleAll(this)"> <span class="checkMark"></span>
										</label></th>
										<th>받는 사람</th>
										<th>제목</th>
										<th>보낸 날짜</th>
										<th>읽음 여부</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="msg" items="${messageList}">
										<tr>
											<td><label for="messageId" class="input-check type-only">
													<input type="checkbox" id="messageId" name="messageId"
													class="form-control" value="${msg.messageId}"> <span
													class="checkMark"></span>
											</label></td>
											<td>${msg.receiverId}</td>
											<td><a href="javascript:void(0);"
												onclick="openMessagePopup(${msg.messageId})">${msg.messageTitle}</a></td>
											<td><fmt:formatDate value="${msg.messageSendDate}"
													pattern="yyyy-MM-dd HH:mm" /></td>
											<td><c:choose>
													<c:when test="${msg.messageIsRead eq 'Y'}">
														<span style="color: gray;">읽음</span>
													</c:when>
													<c:otherwise>
														<span style="color: blue; font-weight: bold;">안읽음</span>
													</c:otherwise>
												</c:choose></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
						<c:if test="${not empty paging}">
							<div class="paging-area">
								<ul class="pagination">
									<c:if test="${paging.hasPrev()}">
										<a href="sent.do?page=${paging.startPage - 1}">&lt;</a>
									</c:if>

									<c:forEach begin="${paging.startPage}" end="${paging.endPage}"
										var="i">
										<li class="${i == paging.nowPage ? 'on' : ''}"><a
											href="sent.do?page=${i}">${i}</a></li>
									</c:forEach>

									<c:if test="${paging.hasNext()}">
										<a href="sent.do?page=${paging.endPage + 1}">&gt;</a>
									</c:if>
								</ul>
							</div>
						</c:if>
						<div class="btn-area-right">
							<button type="submit" class="btn btn-grey">선택 삭제</button>
							<button type="button" onclick="openComposePopup()"
								class="btn btn-primaryß">쪽지 쓰기</button>
						</div>
					</form>
				</div>
			</div>
		</section>

	</div>
	<script>
function openComposePopup() {
  const popup = window.open('', 'composeWindow',
    'width=800,height=600,left=100,top=100,resizable=yes,scrollbars=yes');
  if (popup) {
    popup.location.href = 'composeForm.jsp';
    popup.focus();
  } else {
    alert("팝업이 차단되었거나 브라우저에서 막힘");
  }
}
function openMessagePopup(messageId) {
  const popup = window.open('', 'messagePopup',
    'width=800,height=600,left=100,top=100,resizable=yes,scrollbars=yes');
  if (popup) {
    popup.location.href = 'messageView.jsp?messageId=' + messageId;
    popup.focus();
  } else {
    alert("팝업이 차단되었거나 브라우저에서 막힘");
  }
}
/*
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
*/
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
