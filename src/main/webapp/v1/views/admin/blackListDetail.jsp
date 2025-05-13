<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
<style>
  table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
    font-family: 'Noto Sans KR', sans-serif;
    font-size: 14px;
    background-color: #fff;
    box-shadow: 0 4px 8px rgba(0,0,0,0.05);
  }

  th, td {
    border: 1px solid #ddd;
    padding: 12px 10px;
    text-align: center;
  }

  th {
    background-color: #f0f4f8;
    color: #333;
    font-weight: bold;
  }

  tr:nth-child(even) {
    background-color: #fafafa;
  }

  tr:hover {
    background-color: #eef6ff;
  }

  input[type="text"],
  select,
  textarea {
    width: 100%;
    padding: 8px;
    font-size: 14px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
  }

  input[type="submit"] {
    margin-top: 15px;
    padding: 10px 20px;
    background-color: #007acc;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }

  input[type="submit"]:hover {
    background-color: #005c99;
  }
</style>--%>
<main class="admin">
<div class="admin-header">
	<h3 class="main-header">회원 상세</h3>
	<div><%@ include file="/v1/views/admin/adminSidebar.jsp" %></div>
</div>
	<div>
		<form action="insertBlackList.do?memberId=${memberId }" method="post">
			<label for="id">ID</label> 
			<input type="text" name="id" value="${memberId }" readonly> 
			<label for="reportType">콘텐츠 유형</label>
				<select name="reportType">
					<option disabled selected>--선택해주세요--</option>
					<option value="가짜/조작된 리뷰">가짜/조작된 리뷰</option>
					<option value="부적절한 표현 및 혐오 콘텐츠">부적절한 표현 및 혐오 콘텐츠</option>
					<option value="스팸 및 상업적 광고">스팸 및 상업적 광고</option>
					<option value="민감한 주제의 표현">민감한 주제의 표현</option>
				</select>
			<label for="description">상세 설명</label>
			<textarea name="description" rows="8" required></textarea>
			<input type="submit" value="저장">
		</form>
	</div>
</main>
