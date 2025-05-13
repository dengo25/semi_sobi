<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String actionUrl = (request.getAttribute("review") != null)
          ? "reviewEditOk.do"
          : "reviewWriteOk.do";
%>
<main>
	<h2>후기</h2>

	<form id="postForm" action="<%= actionUrl %>" method="post"
		enctype="multipart/form-data">
		<c:if test="${not empty review}">
			<input type="hidden" name="reviewId" value="${review.reviewId}" />
		</c:if>

		<table class="table-basic">
			<caption>리뷰 작성</caption>
			<colgroup>
				<col style="width: 15%">
				<col style="width: 35%">
				<col style="width: 15%">
				<col style="width: 35%">
			</colgroup>
			<tbody>
				<tr>
					<th>작성자</th>
					<td colspan="3">
						<div class="input-group w300">
							<input type="text" name="memberId" class="form-control"
								value="${member.memberId}" readonly="readonly">
						</div>
					</td>
				</tr>
				<tr>
					<th>제목</th>
					<td colspan="3">
						<div class="input-group w940">
							<input type="text" name="reviewTitle" class="form-control"
								placeholder="제목을 입력하세요." value="${review.reviewTitle}">
						</div>
					</td>
				</tr>
				<tr>
					<th>상품명</th>
					<td colspan="3">
						<div class="input-group w940">
							<input type="text" name="productName" class="form-control"
								placeholder="상품명을 입력하세요." value="${review.productName}">
						</div>
					</td>
				</tr>
				<tr>
					<th>카테고리</th>
					<td class="text-left">
					<select name="reviewCategoryId" class="form-select w300">
							<c:forEach var="cat" items="${categoryList}">
								<option value="${cat.reviewCategoryId}"
									<c:if test="${review.reviewCategoryId == cat.reviewCategoryId}">selected</c:if>>
									${cat.reviewCategoryName}</option>
							</c:forEach>
					</select></td>
					<th>평점</th>
					<td class="text-left"><select name="rating" class="form-select w300">
							<c:forEach begin="1" end="5" var="i">
								<option value="${i}"
									<c:if test="${review.rating == i}">selected</c:if>>${i}점</option>
							</c:forEach>
					</select></td>
				</tr>
			</tbody>
		</table>

		<!-- Toast UI Editor 영역 -->
		<div id="editor"></div>
		<input type="hidden" name="reviewContent" id="reviewContent">
		<input type="hidden" name="reviewImageNumber" id="reviewImageNumber">

		<div class="btn-area">
			<button type="submit" class="btn btn-primary">저장</button>
			<button type="button" class="btn btn-deepgrey"
				onclick="history.back()">취소</button>
		</div>
	</form>

	<c:if test="${not empty review}">
		<script>
      const reviewContentFromServer = `<c:out value="${review.content}" escapeXml="false" />`;
    </script>
	</c:if>

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

          imageCount++;
          document.getElementById('reviewImageNumber').value = imageCount;
        }
      }
    });

    // 기존 내용 불러오기 (수정 모드일 경우)
    if (typeof reviewContentFromServer !== 'undefined') {
      editor.setHTML(reviewContentFromServer);
    }

    // form 전송 전 editor 내용 반영
    document.getElementById('postForm').addEventListener('submit', function () {
      const content = editor.getHTML();
      document.getElementById('reviewContent').value = content;
    });
  </script>
</main>
