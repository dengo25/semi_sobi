<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="sobi.vo.review.ReviewVO" %>

<%
    ReviewVO review = new ReviewVO();
    review.setTitle("샘플 리뷰 제목입니다!");
    review.setMemberId("sampleUser123");
    review.setRating(5);
    review.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    review.setImageUrl("https://via.placeholder.com/600x400.png?text=샘플+이미지");
    review.setContent("이 제품 정말 최고예요! 👍<br>추천합니다.<br><strong>디자인도 예쁨!</strong>");

    request.setAttribute("review", review);
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>리뷰 상세보기</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 800px;
            margin: 40px auto;
            padding: 20px;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }

        h1 {
            font-size: 24px;
            margin-bottom: 10px;
        }

        .meta {
            font-size: 14px;
            color: #666;
            margin-bottom: 20px;
        }

        .image-preview {
            max-width: 100%;
            height: auto;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        .content {
            font-size: 16px;
            line-height: 1.7;
        }

        .back-link {
            margin-top: 30px;
            display: inline-block;
            color: #333;
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>${review.title}</h1>
        <div class="meta">
            작성자: ${review.memberId} |
            평점: ${review.rating}점 |
            작성일: <fmt:formatDate value="${review.createdAt}" pattern="yyyy-MM-dd" />
        </div>

        <c:if test="${not empty review.imageUrl}">
            <img src="${review.imageUrl}" alt="리뷰 이미지" class="image-preview">
        </c:if>

        <div class="content">
            <c:out value="${review.content}" escapeXml="false" />
        </div>

        <a href="#" onclick="history.back()" class="back-link">← 목록으로 돌아가기</a>
    </div>
</body>
</html>
