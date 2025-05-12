<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String actionUrl = "reviewWriteOk.do";
%>

<main>
  <h2>리뷰 작성</h2>

  <form id="postForm" action="<%= actionUrl %>" method="post" enctype="multipart/form-data">
    <table class="table-basic">
      <caption>리뷰 작성 목록 - 제목, 상품명, 카테고리, 평점, 내용 구성</caption>
      <colgroup>
        <col style="width: 15%">
        <col style="width: 85%">
      </colgroup>
      <tbody>
      <tr>
        <th>작성자</th>
        <td>
          <div class="input-group w300">
            <input type="text" name="memberId" class="form-control"
                   value="${member.memberId}" readonly="readonly">
          </div>
        </td>
      </tr>
      <tr>
        <th>제목</th>
        <td>
          <div class="input-group w940">
            <input type="text" name="reviewTitle" class="form-control" placeholder="제목을 입력하세요.">
          </div>
        </td>
      </tr>
      <tr>
        <th>상품명</th>
        <td>
          <div class="input-group w940">
            <input type="text" name="productName" class="form-control" placeholder="상품명을 입력하세요.">
          </div>
        </td>
      </tr>
      <tr>
        <th>카테고리</th>
        <td>
          <select name="reviewCategoryId" class="form-control w300">
            <c:forEach var="cat" items="${categoryList}">
              <option value="${cat.reviewCategoryId}">${cat.reviewCategoryName}</option>
            </c:forEach>
          </select>
        </td>
      </tr>
      <tr>
        <th>평점</th>
        <td>
          <select name="rating" class="form-control w100">
            <c:forEach begin="1" end="5" var="i">
              <option value="${i}">${i}점</option>
            </c:forEach>
          </select>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- Toast UI Editor -->
    <div id="editor"></div>
    <input type="hidden" name="reviewContent" id="reviewContent">
    <input type="hidden" name="reviewImageNumber" id="reviewImageNumber">

    <div class="btn-area">
      <button type="submit" class="btn btn-primary">저장</button>
      <button type="button" class="btn btn-deepgrey" onclick="history.back()">취소</button>
    </div>
  </form>

  <!-- Toast UI Editor 스크립트 -->
  <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css">
  <script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>

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

    document.getElementById('postForm').addEventListener('submit', function () {
      const content = editor.getHTML();
      document.getElementById('reviewContent').value = content;
    });
  </script>
</main>
