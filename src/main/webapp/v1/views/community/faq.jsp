<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /content.jsp --%>
<style>
/* Accodian ---------------------------------------- */
.ui-acc dt { display: flex; justify-content: space-between; align-items: center; min-height: 52px; padding:23px 16px 16px; border-bottom: 1px solid #e5e5e5; cursor: pointer; } 
.ui-acc dt .tit { display: inline-block; width: calc(100% - 48px); color: #4c4c4c; font-size: 14px; line-height: 20px; } 
.ui-acc dd { min-height: 106px; overflow: hidden; padding:16px; border-bottom: 1px solid #e5e5e5; background-color: #f8f8f8; } /* max-height: 106px;padding:20px 30px; */
.ui-acc dl .plusminus{ display: block; position: relative; width: 36px; height: 28px; cursor: pointer; } /*  width: 28px; height: 28px; width: 10px; height: 10px;margin-right: 12px; */
.ui-acc dl .plusminus:before,
.ui-acc dl .plusminus:after { content: ""; display: block; background-color: #4c4c4c; position: absolute; top: 50%; left:11px; transition: .35s ease-in-out; width: 14px; height: 2px; } /* width: 50%; height: 1.5px;left: 8px;<< left: 0; */
.ui-acc dl .plusminus:before { transform: translateY(-50%); } 
.ui-acc dl .plusminus:after { transform: translateY(-50%) rotate(90deg); } 
.ui-acc dl.on .plusminus:before { transform: translateY(-50%) rotate(-90deg); opacity: 0; } 
.ui-acc dl.on .plusminus:after { transform: translateY(-50%) rotate(0); } 
/* dt > input */
.ui-acc dt .input-group{width:calc(100% - 36px);}/* width:calc(100% - 36px); */
/* 유형추가 */


/* Accodian : ui-acc(type-faq) ---------------------------------------- */
.ui-acc.type-faq{margin-bottom:24px;background-color:transparent;}/* border-top:1px solid #4c4c4c;background-color:#fff; */
.ui-acc.type-faq dl{overflow:hidden;border-radius:4px;border-bottom:16px solid #f5f6f7;}
.ui-acc.type-faq dt{position:relative;min-height:56px;padding:16px;border-bottom:none;border-radius:4px;background-color:#fff;}/* min-height:80px;padding:24px; */
.ui-acc.type-faq dl.on dt{}/* border-bottom: 2px dashed #e5e5e5; */
.ui-acc.type-faq dl.on dt::after{content:'';overflow:hidden;display:block;position:absolute;right:16px;left:16px;bottom:0; clear:both;width:calc(100% - 16px);border-bottom:2px dashed #e5e5e5; border-spacing:2px;}

.ui-acc.type-faq dt .tit{display: flex;gap:8px;align-items: center;font-size:14px;line-height:20px;}/*  gap :16px;font-size:16px; line-height: 24px; */
.ui-acc.type-faq dt .tit .ico-faq-q{flex-basis:24px;}/* flex-basis: 33px; */
.ui-acc.type-faq dt .tit span{flex-basis: calc(100% - 24px);}/* flex-basis: calc(100% - 32px); */
.ui-acc.type-faq dd{position:relative;display:flex;flex-direction:column;justify-content:flex-start;gap:16px;min-height: auto; overflow: hidden;padding:16px 16px 16px 48px;background-color:#fff;border-radius:0 0 4px 4px;border-bottom:none;}/* align-items: center;padding:32px 40px;background-color: #f5f6f7; */
.ui-acc.type-faq dd.over-line{align-items: start;}
.ui-acc.type-faq dd .ico-faq-a{position:absolute;top:16px;left:16px;}/*  margin-left: 32px; flex-basis: 33px; */
.ui-acc.type-faq dd .cont-txt{ flex-basis: calc(100% - 72px); font-size: 14px; line-height: 20px; color: #4c4c4c;}/* font-size: 16px;line-height: 24px; */
.ui-acc.type-faq dl dt .dropdown {display: inline-block;width:16px;height:16px; background:url(/v1/static/images/ico-faq-dropdown.svg);transition: .35s ease-in-out;}/* margin-right: 8px; */
.ui-acc.type-faq dl.on dt .dropdown{transform: rotate(-180deg);}

/* FAQ */
[class*="ico-faq-"]{width:24px;height:24px;}
.ico-faq-q{background:url('/v1/static/images/ico-faq-q.svg') no-repeat center / contain;}/* width:32px;height:32px; */
.ico-faq-a{background:url('/v1/static/images/ico-faq-a.svg') no-repeat center / contain;}

</style>

<main>
	<h2>FAQ</h2>
	<div class="ui-acc type-faq">
	   
	   <dl class="">
		    <dt>
		        <p class="tit">
		            <i class="ico-faq-q"></i>
		            <span>SOBI는 어떤 커뮤니티인가요?</span>
		        </p>
		        <i class="dropdown"></i>
		    </dt>
		    <dd style="display: none;">
		        <i class="ico-faq-a"></i>
		        <p class="cont-txt">
		            SOBI는 ‘Save, Or Buy Immediately’를 모토로 한 <strong>내돈내산 리뷰 커뮤니티</strong>입니다.<br>
		            실사용자의 경험을 바탕으로 신뢰할 수 있는 후기와 정보를 나누는 공간이에요.
		        </p>
		    </dd>
		</dl>

		<dl class="">
		    <dt>
		        <p class="tit">
		            <i class="ico-faq-q"></i>
		            <span>어떤 후기가 금지되나요?</span>
		        </p>
		        <i class="dropdown"></i>
		    </dt>
		    <dd style="display: none;">
		        <i class="ico-faq-a"></i>
		        <p class="cont-txt">
		            다음과 같은 후기는 절대 등록하시면 안 됩니다:<br>
		            - 금전적 보상을 받은 광고성 후기<br>
		            - 다른 사람의 글을 무단 복사/도용한 후기<br>
		            - 제품을 실제로 사용하지 않고 작성한 후기
		        </p>
		    </dd>
		</dl>
		
		<dl class="">
		    <dt>
		        <p class="tit">
		            <i class="ico-faq-q"></i>
		            <span>욕설이나 혐오 표현도 제재 대상인가요?</span>
		        </p>
		        <i class="dropdown"></i>
		    </dt>
		    <dd style="display: none;">
		        <i class="ico-faq-a"></i>
		        <p class="cont-txt">
		            네, SOBI는 모두가 존중받는 커뮤니티를 지향합니다.<br>
		            욕설, 비하, 차별적 발언, 성적 표현 등은 <strong>즉시 제재</strong>됩니다.
		        </p>
		    </dd>
		</dl>
		
		<dl class="">
		    <dt>
		        <p class="tit">
		            <i class="ico-faq-q"></i>
		            <span>사진도 아무거나 올리면 안 되나요?</span>
		        </p>
		        <i class="dropdown"></i>
		    </dt>
		    <dd style="display: none;">
		        <i class="ico-faq-a"></i>
		        <p class="cont-txt">
		            맞아요. 이미지 업로드 시에는 다음을 꼭 지켜주세요:<br>
		            - 직접 사용한 제품 사진만 등록<br>
		            - 타인의 이미지 도용 금지<br>
		            - 과도한 편집/합성 금지<br>
		            - 음란하거나 폭력적인 이미지 업로드 시 계정 제재
		        </p>
		    </dd>
		</dl>
		
		<dl class="">
		    <dt>
		        <p class="tit">
		            <i class="ico-faq-q"></i>
		            <span>부적절한 콘텐츠는 어떻게 신고하나요?</span>
		        </p>
		        <i class="dropdown"></i>
		    </dt>
		    <dd style="display: none;">
		        <i class="ico-faq-a"></i>
		        <p class="cont-txt">
		            각 리뷰나 댓글 하단의 <strong>‘신고’ 버튼</strong>을 이용해주세요.<br>
		            운영팀은 다음과 같은 조치를 취할 수 있습니다:<br>
		            - 콘텐츠 블라인드 또는 삭제<br>
		            - 사용자 경고 또는 활동 제한<br>
		            - 심각한 경우 계정 정지 또는 탈퇴 처리
		        </p>
		    </dd>
		</dl>
	    
	</div>
</main>
