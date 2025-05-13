<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String actionUrl = (request.getAttribute("noticeDetail") != null)
      ? "noticeEditOk.do"
      : "noticeWriteOk.do";
%>
<main>
	<h2>공지사항</h2>
	<form id="postForm" action="<%= actionUrl %>" method="post"
		enctype="multipart/form-data">
		<c:if test="${not empty noticeDetail}">
			<input type="hidden" name="noticeNo" value="${noticeDetail.noticeNo}">
		</c:if>

		<table class="table-basic">
			<caption>공지사항 작성 목록 - 제목, 작성자, 작성내용으로 구성</caption>
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
								class="form-control" placeholder="제목을 입력하세요."
								value="${noticeDetail.noticeTitle}">
						</div>
					</td>
				</tr>
				<c:if test="${empty noticeDetail}">
					<tr>
						<th scope="col">작성자</th>
						<td colspan="3">
							<div class="input-group w300">
								<input type="text" name="memberId" id="noticeWriter"
									class="form-control" value="${memberId}" readonly="readonly">
							</div>
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>

		<!-- Toast UI Editor 영역 -->
		<div id="editor"></div>
		<input type="hidden" name="noticeContent" id="noticeContent">
		<input type="hidden" name="noticeImageNumber" id="noticeImageNumber" value="0">

		<div class="btn-area">
			<button type="submit" class="btn btn-primary">저장</button>
			<button type="button" class="btn btn-line-deepgrey"
				onclick="handleDelete()">삭제</button>
			<button type="button" class="btn btn-deepgrey"
				onclick="history.back()">취소</button>
		</div>
	</form>

	<%-- 수정모드일때만 실행 --%>
	<c:if test="${not empty noticeDetail}">
		<script>
    const noticeContentFromServer = `<c:out value="${noticeDetail.noticeContent}" escapeXml="false" />`;
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
	      
	      imageCount++;  // 이미지 추가되면 수 증가
	      document.getElementById('noticeImageNumber').value = imageCount;
	    }
	  }
	});
	
	// 기존 내용 불러오기 
	if (typeof noticeContentFromServer !== "undefined") {
	  editor.setHTML(noticeContentFromServer);
	}
	
	// 에디터 내용 폼에 추가
	document.getElementById('postForm').addEventListener('submit', function () {
		console.log('📝 form submit 이벤트 발생');
		const content = editor.getHTML();
		document.getElementById('noticeContent').value = content
	});
	
	// 삭제  
	function handleDelete() {
	  const confirmed = confirm("정말 삭제하시겠습니까?");
	  if (confirmed) {
	    const noticeNo = document.querySelector('input[name="noticeNo"]').value;
	    location.href = "noticeDelete.do?noticeNo=" + noticeNo;
	  }
	}
</script>
</main>
