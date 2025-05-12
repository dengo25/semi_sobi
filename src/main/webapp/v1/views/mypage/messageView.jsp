<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="sobi.dao.mypage.MessageDAO, sobi.vo.mypage.MessageVO" %>
<%@ page import="sobi.vo.member.MemberVO" %>
<%@ page import="java.sql.*, java.text.SimpleDateFormat" %>

<%
    String idParam = request.getParameter("messageId");
    MessageVO message = null;

    // 세션에서 로그인한 사용자 ID 가져오기
    
    MemberVO member = (MemberVO) session.getAttribute("member");
    String loginId = (member != null) ? member.getMemberId() : null;

    if (idParam != null) {
        try {
            int messageId = Integer.parseInt(idParam);
            MessageDAO dao = new MessageDAO();
            message = dao.getMessageById(messageId);
            if (message != null && loginId != null && loginId.equals(message.getReceiverId())) {
                dao.updateMessageRead(messageId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>쪽지 상세 보기</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th {
            width: 120px;
            text-align: left;
            background-color: #f0f0f0;
            padding: 8px;
        }
        td {
            padding: 8px;
            white-space: pre-wrap;
        }
    </style>
</head>
<body>

<h2>📨 쪽지 상세 보기</h2>

<%
    if (message != null) {
%>
    <table border="1">
        <tr><th>보낸 사람</th><td><%= message.getSenderId() %></td></tr>
        <tr><th>받는 사람</th><td><%= message.getReceiverId() %></td></tr>
        <tr><th>제목</th><td><%= message.getMessageTitle() %></td></tr>
        <tr><th>내용</th><td><%= message.getMessageContent() %></td></tr>
        <tr><th>보낸 날짜</th>
            <td><%= new SimpleDateFormat("yyyy-MM-dd HH:mm").format(message.getMessageSendDate()) %></td></tr>
    </table>

    <!-- 답장 버튼 (내가 받은 쪽지인 경우에만 출력) -->
    <%
        if (loginId != null && !loginId.equals(message.getSenderId())) {
    %>
        <div style="margin-top: 20px;">
            <button type="button" onclick="openReplyPopup('<%= message.getSenderId() %>')">✏ 답장하기</button>
        </div>
    <%
        }
    %>

<%
    } else {
%>
    <p>해당 쪽지를 찾을 수 없습니다.</p>
<%
    }
%>

<script>
    function openReplyPopup(senderId) {
        const width = 800;
        const height = 600;
        const left = (screen.width - width) / 2;
        const top = (screen.height - height) / 2;

        const url = 'composeForm.jsp?receiverId=' + encodeURIComponent(senderId);
        const popup = window.open(url, 'replyPopup',
            `width=${width},height=${height},left=${left},top=${top},scrollbars=yes,resizable=yes`);
        if (popup) popup.focus();
    }
    //창닫으면 즉시 새로고침
    window.onunload = function () {
        if (window.opener && !window.opener.closed) {
            window.opener.location.reload();
        }
    };
</script>

</body>
</html>
