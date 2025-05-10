<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<main>
  <h2>
    📤 보낸 쪽지함
    <button onclick="openComposePopup()" style="float: right;">✏ 쪽지 쓰기</button>
  </h2>

  <c:if test="${empty messageList}">
    <p>보낸 쪽지가 없습니다.</p>
  </c:if>

  <c:if test="${not empty messageList}">
    <table border="1" width="100%" cellpadding="8" style="border-collapse: collapse; margin-top: 20px;">
      <thead>
        <tr>
          <th>받는 사람</th>
          <th>제목</th>
          <th>보낸 날짜</th>
          <th>읽음 여부</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="msg" items="${messageList}">
          <tr>
            <td>${msg.receiverId}</td>
            <td>
              <a href="javascript:void(0);" onclick="openMessagePopup(${msg.messageId})">
                ${msg.messageTitle}
              </a>
            </td>
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

  <script>
    function openComposePopup() {
      const width = 800;
      const height = 600;
      const left = (window.screen.width - width) / 2;
      const top = (window.screen.height - height) / 2;

      const popup = window.open(
        'composeForm.jsp',
        'composeWindow',
        `width=${width},height=${height},top=${top},left=${left},scrollbars=yes,resizable=yes`
      );
      if (popup) popup.focus();
    }

    function openMessagePopup(messageId) {
      const width = 800;
      const height = 600;
      const left = (window.screen.width - width) / 2;
      const top = (window.screen.height - height) / 2;

      window.open(
        'messageView.jsp?messageId=' + messageId,
        'messagePopup',
        `width=${width},height=${height},left=${left},top=${top},scrollbars=yes,resizable=yes`
      );
    }
  </script>
</main>
