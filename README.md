# 📝 SOBI - 내돈내산 리뷰 플랫폼
> 신뢰 기반 후기 문화 형성을 위한 리뷰 중심 블로그 플랫폼
> 
-----------------------------------------------------
## 👥 팀원 소개 - 4℃
| 이름     | 역할                          | GitHub ID       |
|----------|-------------------------------|------------------|
|오창진| 로그인|[dengo25](https://github.com/dengo-dev)|
|최완빈|관리자|[wanbinchoi](https://github.com/wanbinchoi)|
|김유진| 글쓰기 |[YooJin-Danny](https://github.com/YooJin-Danny)|
|왕시은|프론트, 공지 |[alo-wang](https://github.com/alo-wang)|
|최산하| 마이페이지 |[Sana2d2v](https://github.com/Sana2d2v)|

## 📆 프로젝트 기간
**2025.05.07 ~ 2025.05.14 ** 

## 🧾 프로젝트 개요

**SOBI**는 신뢰할 수 있는 "내돈내산" 리뷰를 중심으로  
블로그처럼 자유롭게 작성하고 공유할 수 있는 후기 플랫폼입니다.  
리뷰 기반 SNS 형태로 리뷰, 댓글, 채팅, 쪽지 등  
사용자 간의 교류를 중심으로 한 후기 문화를 형성합니다.

## 🔧 사용 기술 스택

### Backend
- Java 21, JSP/Servlet
- MySQL (AWS RDS)
- JDBC, DAO/VO 패턴
- Properties 기반 액션 매핑
- 파일 업로드 / S3 연동

- ### Frontend
- JSP, HTML/CSS, JavaScript
- jQuery, AJAX, JSON
- JSTL, EL

- ### 협업 & 배포
- Git & GitHub
- Figma, Notion (기획 및 설계)
- ERDCloud, Google Docs
- Eclipse IDE, Maven(썼나?)


## 🖥️ 주요 기능

### 👤 일반 사용자
- 회원가입 / 로그인 (소셜 연동 포함)
- 리뷰 게시판 (검색, 페이징, 좋아요, 신고)
- 댓글, 대댓글, 댓글 좋아요
- 쪽지함 (보내기, 받기, 읽음 처리, 페이징)
- 마이페이지
  - 내 정보 확인 및 수정
  - 포인트 내역 / 등급 조회
  - 쪽지 관리

## 📁 프로젝트 폴더 구조

```
semi_sobi/
├── src/
│   ├── sobi.action.*           ← 각 도메인별 Action 클래스
│   ├── sobi.dao.*              ← DAO 클래스
│   ├── sobi.vo.*               ← VO 클래스
│   ├── sobi.util               ← 공통 유틸리티 (DB 연결, 암호화 등)
│   └── sobi.db                 ← DB 연결(ConnectionProvider 등)
│
├── webapp/
│   ├── v1/
│   │   ├── views/
│   │   │   ├── common/         ← head.jsp, layout.jsp 등 공통 요소
│   │   │   ├── member/         ← 로그인, 회원가입, 내정보
│   │   │   ├── review/         ← 리뷰 목록, 작성, 상세
│   │   │   ├── message/        ← 쪽지함, 쪽지쓰기 팝업
│   │   │   ├── mypage/         ← 마이페이지, 등급, 포인트
│   │   │   ├── chat/           ← 채팅방 UI
│   │   │   └── admin/          ← 관리자 페이지
│   └── assets/                 ← 이미지, JS, CSS
│
├── sobi.properties             ← Action 매핑용 설정 파일
└── web.xml                     ← 서블릿/필터 매핑 설정
```


## 📷 서비스 화면
