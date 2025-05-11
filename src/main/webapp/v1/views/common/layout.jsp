<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
 /* 할일
 - 인클루드 루트는 절대경로로 관리한다.
 
 ⚠️ 주의할 점
 1. include vs jsp:include
 방식	설명	시점
 include file="...jsp"		정적 include	컴파일 시 포함됨
 jsp:include page="...jsp" 	동적 include	실행 시 포함됨
 → 지금처럼 정적 include 는 일반적으로 header/footer처럼 변하지 않는 파일에 적합.
 
 필터나 리다이렉트와 충돌 없음?
 필터나 리다이렉트 처리 시, include된 파일까지 모두 포함해서 실행되기 때문에 충돌은 없음.
 단, 로그인 상태에 따라 header를 다르게 보여줘야 한다면 → header.jsp에서 세션 체크하면 돼요.
 
 /WEB-INF/views/
 ├── common/
 │   ├── head_common.jsp
 │   ├── header.jsp
 │   └── footer.jsp
 ├── login/
 │   ├── login.jsp
 │   └── login_process.jsp
 ├── service/
 │   ├── service01.jsp
 │   └── service02.jsp
 
 
 //이렁식으로
 /src
├── /main
│   ├── /java
│   │   ├── /com
│   │   │   ├── /kosta
│   │   │   │   ├── /controller       // 서블릿 및 API 컨트롤러
│   │   │   │   ├── /service          // 서비스 로직
│   │   │   │   ├── /dao              // 데이터베이스 액세스
│   │   │   │   ├── /dto              // 데이터 전송 객체 (DTO)
│   │   │   │   └── /filter           // 필터
│   │   │   └── /utils               // 유틸리티 클래스
│   ├── /resources
│   │   ├── /application.properties  // 설정 파일
│   │   └── /logback.xml             // 로그 설정 파일
├── /webapp
│   ├── /WEB-INF
│   │   ├── /views                  // JSP 파일들
│   │   │   ├── /common
│   │   │   │   ├── head_common.jsp
│   │   │   │   ├── header.jsp
│   │   │   │   └── footer.jsp
│   │   │   ├── /login
│   │   │   │   ├── login.jsp
│   │   │   │   └── login_process.jsp
│   │   │   ├── /service
│   │   │   │   ├── service01.jsp
│   │   │   │   └── service02.jsp
│   │   ├── /web.xml                 // 서블릿 설정 및 필터 등
│   └── /static
│       ├── /css
│       ├── /js
│       └── /images


나의 말:
프로젝트명 / src/ main/ webapp/ resources
구조로 만들어서 resources 안에 jsp파일을 구성하는게 맞아?
// 🔍 이유를 정리해보면:

위치	용도	JSP 배치 권장 여부
src/main/webapp/resources/	정적 자원 (CSS, JS, 이미지 등)	❌ 아니요
src/main/webapp/WEB-INF/views/	동적 자원 (JSP 등 서버에서 렌더링)	✅ 예
resources는 클라이언트가 직접 접근 가능한 정적 자원(예: /resources/js/app.js, /resources/css/style.css)을 두는 곳이에요.

반면에 JSP는 서버에서 처리되는 동적 자원이기 때문에 WEB-INF 하위에 두어 직접 접근을 차단하고, 서블릿이나 컨트롤러를 통해 이동하는 방식으로 보안도 유지하는 게 일반적입니다.
 


그렇다면, 내가 사용자이고 login을 하려면 
webapp폴더에 있는 login.jsp로 이동해야되
이것도 WEB-INF에 넣어야되?


ChatGPT의 말:
아주 중요한 질문이에요! 👍
**로그인 페이지(login.jsp)를 어디에 둘 것인가?**는 보안과 사용자 흐름(UX) 측면에서 아주 전략적인 선택입니다.

일반적인 권장 구조

위치	설명	예시
webapp/login.jsp	공개되어도 괜찮은 페이지, 로그인 시작점	사용자 브라우저에서 직접 접근 가능
WEB-INF/views/login.jsp	직접 접근을 막고 싶을 때, 컨트롤러를 통해서만 보여주고 싶을 때	LoginServlet → forward()로 이동
💡 기준은?
🔓 직접 URL로 접근이 허용되어도 괜찮다면 → webapp/login.jsp에 둬도 됩니다.

🔐 보안을 강화하거나 직접 URL 접근을 막고 싶다면 → WEB-INF/views/login.jsp에 두고 서블릿 or 컨트롤러를 통해 포워딩합니다.	
*/
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="head_common.jsp" %>
<title>${title}</title>
</head>
<body>
<%@ include file="header.jsp" %>


<jsp:include page="${contentPage}" />
<%-- 
http://localhost:내 포트번호/sobi_test/v1/views/common/content.do
http://localhost:내 포트번호/sobi_test/v1/views/board/review_list.do
<c:import url="${contentPage} "/> 
컨텐츠영역<br>
- 컴포넌트 만들기<br><br>
- 테이블, 페이징<br>
- 폼관련<br>
- 버튼<br>
- 레이아웃 만들기<br><br>
- 메뉴
--%>
<%@ include file="footer.jsp" %>
</body>
</html>