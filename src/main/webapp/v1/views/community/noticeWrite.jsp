<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<main>
	<h2>공지사항 - 작성하기</h2>
	<%-- 서블릿으로 리퀘스트 하나 생성해서, 수정시 타이틀 바꾸기 --%>


	<form id="postForm" action="noticeWriteOk.do" method="post"
		enctype="multipart/form-data">
		<table class="table-basic">
			<caption>공지사항 작성 목록 - 제목, 작성일, 작성자, 작성내용으로 구성</caption>
			<colgroup>
				<col style="width: 15%">
				<col style="width: 35%">
				<col style="width: 15%">
				<col style="width: 35%">
			</colgroup>
			<tbody>
				<tr>
					<th scope="col">제목</th>
					<td colspan="3">
						<div class="input-group w940">
							<input type="text" name="noticeTitle" id="noticeTitle"
								class="form-control" placeholder="제목을 입력하세요.">
						</div>
					</td>
				</tr>
				<tr>
					<th scope="col">내용없</th>
					<td>
						<div class="input-group w300">
							<input type="text" name="" id="" class="form-control" value=""
								readonly="readonly">
						</div>
					</td>
					<th scope="col">작성자</th>
					<td>
						<div class="input-group w300">
							<input type="text" name="memberId" id="noticeWriter"
								class="form-control" value="${memberId}" readonly="readonly">
						</div>
					</td>
				</tr>
			</tbody>
		</table>

		<!--  
  <input type="text" name="member_id" placeholder="작성자 ID" required><br>
  <input type="text" name="product_name" placeholder="제품명" required><br>
  <input type="text" name="review_title" placeholder="리뷰 제목" required><br>
  <input type="number" name="rating" min="1" max="5" placeholder="평점 (1~5)" required><br>
  <input type="number" name="review_category_id" placeholder="카테고리 ID (숫자)" required><br>
-->
		<!-- Toast UI Editor 영역 -->
		<div id="editor"></div>
		<input type="hidden" name="noticeContent" id="noticeContent">
		<input type="hidden" name="noticeImageNumber" id="noticeImageNumber">

		<br>
		<button type="submit" class="btn">저장</button>
		<button type="submit" class="btn">삭제</button>
		<button type="submit" class="btn">취소</button>
</form>

<script>
let imageCount = 0;
const editor = new toastui.Editor({
  el: document.querySelector('#editor'),
  height: '500px',
  initialEditType: 'wysiwyg',
  previewStyle: 'vertical',
  hooks: {
    addImageBlobHook: async (blob, callback) => {
      const formData = new FormData();
      formData.append('uploadFile', blob);

      const response = await fetch('/UploadImage', {
        method: 'POST',
        body: formData
      });

      const imageUrl = await response.text();
      callback(imageUrl, '업로드 이미지');
      
      imageCount++;  // 이미지 추가되면 수 증가
      document.getElementById('noticeImageNumber').value = imageCount;
    }
  }
});

// 에디터 내용 폼에 추가
document.getElementById('postForm').addEventListener('submit', function () {
	document.getElementById('noticeContent').value = editor.getHTML();
});
</script>
</main>
