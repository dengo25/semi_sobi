<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main>
	<h2>공지사항</h2>

<form id="postForm" action="WriteReview.do" method="post">
  <input type="text" name="member_id" placeholder="작성자 ID" required><br>
  <input type="text" name="product_name" placeholder="제품명" required><br>
  <input type="text" name="review_title" placeholder="리뷰 제목" required><br>
  <input type="number" name="rating" min="1" max="5" placeholder="평점 (1~5)" required><br>
  <input type="number" name="review_category_id" placeholder="카테고리 ID (숫자)" required><br>

  <!-- Toast UI Editor 영역 -->
  <div id="editor"></div>
  <input type="hidden" name="content" id="content">

  <br>
  <button type="submit">작성 완료</button>
</form>
	
<script>
  const editor = new toastui.Editor({
    el: document.querySelector('#editor'),
    height: '500px',
    initialEditType: 'wysiwyg',
    previewStyle: 'vertical',
    hooks: {
      addImageBlobHook: async (blob, callback) => {
        const formData = new FormData();
        formData.append('uploadFile', blob);

        const response = await fetch('UploadImage', {
          method: 'POST',
          body: formData
        });

        const imageUrl = await response.text();
        callback(imageUrl, '업로드 이미지');
      }
    }
  });

  // 에디터 내용 폼에 추가
  document.getElementById('postForm').addEventListener('submit', function () {
    document.getElementById('content').value = editor.getHTML();
  });
</script>
</main>
