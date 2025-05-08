<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>리뷰 작성 (Toast UI Editor)</title>
  <!-- Toast UI Editor CSS -->
  <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css">
  <style>
    body { font-family: sans-serif; padding: 2em; }
    form { width: 80%; margin: auto; }
    input, select { width: 100%; padding: 10px; margin-bottom: 15px; }
    button { padding: 10px 20px; font-size: 1em; }
    #editor { margin-top: 20px; }
  </style>
</head>
<body>

<h1>리뷰 작성</h1>

<form id="postForm" action="WriteReview.v1" method="post">
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

<!-- Toast UI Editor JS -->
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
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

</body>
</html>
