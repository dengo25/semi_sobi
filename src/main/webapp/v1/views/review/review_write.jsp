<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="sobi.dao.review.CategoryDAO" %>
<%@ page import="sobi.vo.review.CategoryVO" %>
<%@ page import="java.util.List" %>

<%
    CategoryDAO categoryDao = new CategoryDAO();
    List<CategoryVO> categoryList = categoryDao.getAllCategories();
    request.setAttribute("categoryList", categoryList);

    String loginUserId = (String) session.getAttribute("loginId");
    if (loginUserId == null) loginUserId = "guest";  // 테스트용 fallback
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>리뷰 작성</title>
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
    <script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
    <style>
        form {
            max-width: 800px;
            margin: 40px auto;
        }

        input[type="text"], select {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
        }

        .stars {
            display: flex;
            gap: 8px;
            margin: 10px 0;
        }

        .star {
            font-size: 30px;
            cursor: pointer;
            color: #ccc;
            transition: transform 0.2s, color 0.2s;
        }

        .star.selected {
            color: gold;
            transform: scale(1.1);
        }

        #editor {
            width: 100%;
        }

        button {
            padding: 10px 20px;
            background-color: #333;
            color: white;
            border: none;
            cursor: pointer;
        }

        button:hover {
            background-color: #555;
        }
    </style>
</head>
<body>
    <form action="${pageContext.request.contextPath}/WriteReview.v1" method="post">
        <h2>✍ 후기 작성</h2>

        <input type="hidden" name="member_id" value="<%= loginUserId %>"/>

        <label>리뷰 제목:</label>
        <input type="text" name="review_title" required />

        <label>상품명:</label>
        <input type="text" name="product_name" required />

        <label>카테고리:</label>
        <select name="review_category_id" required>
            <option value="">-- 선택 --</option>
            <c:forEach var="cat" items="${categoryList}">
                <option value="${cat.reviewCategoryId}">${cat.reviewCategoryName}</option>
            </c:forEach>
        </select>

        <label>평점 (1~5점):</label>
        <div class="stars">
            <c:forEach var="i" begin="1" end="5">
                <span class="star" data-value="${i}">★</span>
            </c:forEach>
        </div>
        <input type="hidden" name="rating" id="rating" required />

        <label>리뷰 내용:</label>
        <div id="editor"></div>
        <input type="hidden" name="content" id="content" />

        <br />
        <button type="submit">리뷰 등록</button>
    </form>

    <script>
        // Toast UI Editor 초기화
        const editor = new toastui.Editor({
            el: document.querySelector('#editor'),
            height: '800px',  // ✅ 에디터 높이 확대
            initialEditType: 'wysiwyg',
            previewStyle: 'vertical',
            hooks: {
                addImageBlobHook: async (blob, callback) => {
                    const formData = new FormData();
                    formData.append('uploadFile', blob);
                    const response = await fetch('${pageContext.request.contextPath}/UploadImage', {
                        method: 'POST',
                        body: formData
                    });
                    const imageUrl = await response.text();
                    callback(imageUrl, '업로드 이미지');
                }
            }
        });

        // 별점 클릭 처리
        const stars = document.querySelectorAll('.star');
        const ratingInput = document.getElementById('rating');
        let selectedRating = 0;

        stars.forEach(star => {
            star.addEventListener('click', () => {
                selectedRating = parseInt(star.dataset.value);
                ratingInput.value = selectedRating;

                stars.forEach(s => s.classList.remove('selected'));
                for (let i = 0; i < selectedRating; i++) {
                    stars[i].classList.add('selected');
                }
            });
        });

        // 폼 제출 시 에디터 내용을 hidden input에 저장
        document.querySelector('form').addEventListener('submit', () => {
            document.getElementById('content').value = editor.getHTML();
        });
    </script>
</body>
</html>