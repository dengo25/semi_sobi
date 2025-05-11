<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="sobi.vo.review.ReviewVO" %>

<%
    ReviewVO review = (ReviewVO) request.getAttribute("review");
    if (review == null) {
        out.println("<h2>수정할 리뷰가 없습니다.</h2>");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>리뷰 수정</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f4f4;
        }
        .container {
            width: 600px;
            margin: 40px auto;
            padding: 20px;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h2 {
            text-align: center;
        }
        form label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
        }
        form input[type="text"],
        form textarea,
        form select {
            width: 100%;
            padding: 8px;
            margin-top: 4px;
            box-sizing: border-box;
        }
        form button {
            margin-top: 20px;
            width: 100%;
            padding: 10px;
            font-size: 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
        form button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>리뷰 수정</h2>
    <form action="${pageContext.request.contextPath}/updateReview.do" method="post">
        <input type="hidden" name="reviewId" value="${review.reviewId}" />

        <label for="title">제목</label>
        <input type="text" name="title" id="title" value="${review.title}" required />

        <label for="productName">제품명</label>
        <input type="text" name="productName" id="productName" value="${review.productName}" required />

        <label for="rating">평점</label>
        <select name="rating" id="rating">
            <c:forEach var="i" begin="1" end="5">
                <option value="${i}" ${i == review.rating ? 'selected' : ''}>${i}</option>
            </c:forEach>
        </select>

        <label for="content">내용</label>
        <textarea name="content" id="content" rows="10" required>${review.content}</textarea>

        <label for="imageUrl">이미지 URL</label>
        <input type="text" name="imageUrl" id="imageUrl" value="${review.imageUrl}" />

        <button type="submit">수정 완료</button>
    </form>
</div>
</body>
</html>
