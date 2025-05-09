<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // JSP에서 전달된 파라미터 받기
    String receiverId = request.getParameter("receiverId");
%>

<main>
    <h2> 쪽지 쓰기</h2>

    <form action="sendMessage.do" method="post">
        <table cellpadding="8" style="width: 100%; border-collapse: collapse;">
            <tr>
                <th align="left" style="width: 120px;">받는 사람 ID</th>
                 <button type="button" onclick="toggleRecentList()">+</button>
                <td>
                    <input type="text" name="receiverId" value="<%= receiverId != null ? receiverId : "" %>" required style="width: 300px;">
                </td>
            </tr>
            <tr>
                <th align="left">제목</th>
                <td><input type="text" name="messageTitle" required style="width: 500px;"></td>
            </tr>
            <tr>
                <th align="left">내용</th>
                <td><textarea name="messageContent" rows="8" required style="width: 500px;"></textarea></td>
            </tr>
        </table>

        <div style="margin-top: 20px;">
            <button type="submit">보내기</button>
            <button type="reset">다시 쓰기</button>
        </div>
    </form>
</main>
