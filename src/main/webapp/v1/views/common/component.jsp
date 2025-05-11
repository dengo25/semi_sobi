
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- /content.jsp --%>
<main>
    <h2>component</h2>
    <p>여기는 컴포넌트 컨텐츠입니다.</p>

    <div class="tab-classify type-round">
        <ul>
            <li class="current"><a href="#rond6-1"><span>Menu 1</span></a></li>
            <li><a href="#rond6-2"><span>Menu 2</span></a></li>
            <li><a href="#rond6-3"><span>Menu 3</span></a></li>
            <li><a href="#rond6-4"><span>Menu 4</span></a></li>
            <li><a href="#rond6-5"><span>Menu 5</span></a></li>
            <li><a href="#rond6-6"><span>Menu 6</span></a></li>
        </ul>
    </div><!--// tab-classify -->
    <br><hr><br>

    <div class="thum-item gallery-list reverse">
        <figure class="img">
            <a href="javascript:void(0);">
                <img class="" src="../../static/images/@img-test.png" alt="test img">
            </a>
        </figure>
        <div class="desc">
            <div class="category-badge-area">
                <a href="javascript:void(0);" class="txtbadge">카테고리명</a>
            </div>
            <p class="tit">
                <a href="javascript:void(0);">타이틀을 블라블라</a>
            </p>
            <p class="date">2025-05-08</p>
            <p class="cont">
                <a href="javascript:void(0);">내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!</a>
            </p>
            <p class="count">좋아요 <span>22</span>건 / 댓글 <span>10</span>건</p>
        </div>
    </div>
    <br><hr><br>

	<table class="table-basic">
	    <caption>테이블 목록- ~으로 구성</caption>
	    <colgroup>
	        <col style="width:8%">
	        <col style="width:37%">
	        <col style="width:20%">
	        <col style="width:15%">
	        <col style="width:20%">
	    </colgroup>
        <thead>
            <tr>
                <th scope="col">no</th>
                <th scope="col">제목</th>
                <th scope="col">작성일</th>
                <th scope="col">작성자</th>
                <th scope="col">기타</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td class="num">1</td>
                <td><a href="javascript();"><span>후기의 제목입니다.</span></a></td>
                <td>2025. 02. 13 15:25</td>
                <td>작성자의 아이디</td>
                <td>
                    <button class="btn" type="button">삭제</button>
                    <button class="btn" type="button">등록</button>
                </td>
            </tr>
            <tr>
                <td class="num">1</td>
                <td><a href="javascript();"><span>후기의 제목입니다.</span></a></td>
                <td>2025. 02. 13 15:25</td>
                <td>작성자의 아이디</td>
                <td>
                    <button class="btn" type="button">삭제</button>
                    <button class="btn" type="button">등록</button>
                </td>
            </tr>
            <tr>
                <td class="num">1</td>
                <td><a href="javascript();"><span>후기의 제목입니다.</span></a></td>
                <td>2025. 02. 13 15:25</td>
                <td>작성자의 아이디</td>
                <td>
                    <button class="btn" type="button">삭제</button>
                    <button class="btn" type="button">등록</button>
                </td>
            </tr>
        </tbody>
    </table>
	<br><hr><br>

	<div class="paging-area">
	    <button type="button" class="btn btn-paging">처음</button>
	    <button type="button" class="btn btn-paging">이전</button>
	    <ul class="pagination">
	        <li class="on"><a href="javascript:;" aria-current="page"><span>1</span></a></li>
	        <li><a href="javascript:;"><span>2</span></a></li>
	        <li><a href="javascript:;"><span>3</span></a></li>
	        <li><a href="javascript:;"><span>4</span></a></li>
	        <li><a href="javascript:;"><span>5</span></a></li>
	    </ul>
	    <button type="button" class="btn btn-paging">다음</i></button>
	    <button type="button" class="btn btn-paging">마지막</button>
	</div>
	<br><hr><br>


	<table class="table-basic-write">
	    <caption>쓰기 화면 - 컬럼~ 으로 구성</caption>
	    <colgroup>
	        <col style="width:160px">
	        <col style="width:auto">
	        <col style="width:160px">
	        <col style="width:auto">
	    </colgroup>
	    <tbody>
	        <tr>
	            <th>Radio</th>
	            <td colspan="3" class="text-left">
	                <div class="input-group">
	                    <label for="productAll" class="input-radio">
	                        <input type="radio" id="productAll" class="form-control" name="productType" checked="">
	                        <span class="radioMark"></span>
	                        <em>전체</em>
	                    </label>
	                
	                    <label for="productBall" class="input-radio">
	                        <input type="radio" id="productBall" class="form-control" name="productType">
	                        <span class="radioMark"></span>
	                        <em>볼</em>
	                    </label>
	                
	                    <label for="productClub" class="input-radio">
	                        <input type="radio" id="productClub" class="form-control" name="productType">
	                        <span class="radioMark"></span>
	                        <em>클럽</em>
	                    </label>
	
	                    <label for="productApparel" class="input-radio">
	                        <input type="radio" id="productApparel" class="form-control" name="productType">
	                        <span class="radioMark"></span>
	                        <em>어패럴</em>
	                    </label>
	
	                    <label for="productGolfgear" class="input-radio">
	                        <input type="radio" id="productGolfgear" class="form-control" name="productType">
	                        <span class="radioMark"></span>
	                        <em>골프기어</em>
	                    </label>
	                </div>
	            </td>
	        </tr>
	        <tr>
	            <th>input : text</th>
	            <td class="text-left">
	                <div class="input-group w300">
	                    <input type="text" id="localName" class="form-control" placeholder="시.도 또는 시군구 명을 입력하세요.">
	                </div>
	            </td>
	            <th>input : text</th>
	            <td class="text-left">
	                <div class="input-group w300">
	                    <input type="text" id="agencyName" class="form-control" placeholder="대리점 명을 입력해주세요.">
	                </div>
	            </td>
	        </tr>
	        <tr>
	            <th>Checkbox</th>
	            <td colspan="3" class="text-left">
	                <div class="input-group">
	                    <label for="functionServiceFitting" class="input-check">
	                        <input type="checkbox" id="functionServiceFitting" class="form-control" checked>
	                        <span class="checkMark"></span>
	                        <em>타이틀리스트 클럽 피팅 가능</em>
	                    </label>
	                    
	                    <label for="functionServiceTry" class="input-check">
	                        <input type="checkbox" id="functionServiceTry" class="form-control">
	                        <span class="checkMark"></span>
	                        <em>시타실 보유</em>
	                    </label>
	                    
	                    <label for="functionServiceStore" class="input-check">
	                        <input type="checkbox" id="functionServiceStore" class="form-control">
	                        <span class="checkMark"></span>
	                        <em>타이틀리스트 브랜드 스토어</em>
	                    </label>
	                </div>
	            </td>
	        </tr>
	
	        <tr>
	            <th>Radio</th>
	            <td colspan="3" class="text-left">
	                <div class="input-group mt8">
	                    <label for="timeSelect1" class="input-radio type-btn">
	                        <input type="radio" id="timeSelect1" class="form-control" name="radioTimeSelect">
	                        <span class="radioMark"></span>
	                        <em>14:00</em>
	                    </label>
	                
	                    <label for="timeSelect2" class="input-radio type-btn">
	                        <input type="radio" id="timeSelect2" class="form-control" name="radioTimeSelect" checked>
	                        <span class="radioMark"></span>
	                        <em>14:30</em>
	                    </label>
	                
	                    <label for="timeSelect3" class="input-radio type-btn">
	                        <input type="radio" id="timeSelect3" class="form-control" name="radioTimeSelect">
	                        <span class="radioMark"></span>
	                        <em>15:00</em>
	                    </label>
	                
	                    <label for="timeSelect4" class="input-radio type-btn">
	                        <input type="radio" id="timeSelect4" class="form-control" name="radioTimeSelect">
	                        <span class="radioMark"></span>
	                        <em>15:30</em>
	                    </label>
	
	                    <label for="timeSelect5" class="input-radio type-btn">
	                        <input type="radio" id="timeSelect5" class="form-control" name="radioTimeSelect">
	                        <span class="radioMark"></span>
	                        <em>16:00</em>
	                    </label>
	                </div>
	            </td>
	        </tr>
	        <tr>
	            <th>textarea</th>
	            <td colspan="3" class="text-left">
	                <div class="input-group">
	                    <textarea name="textarea" id="textarea2" class="form-control" rows="3" maxlength="" placeholder="특이사항을 입력해주세요."></textarea>
	                </div>
	            </td>
	        </tr>
	        <tr>
	            <th>select</th>
	            <td colspan="3" class="text-left">
	                <div class="input-group">
	                    <select class="form-select w160">
	                        <option value="">선택1</option>
	                        <option value="">선택2</option>
	                        <option value="">선택3</option>
	                    </select>
	                    <span class="deco-txt">년</span>
	                </div>
	            </td>
	        </tr>
	
	        <tr>
	            <th class="text-left">input : text</th>
	            <td colspan="3">
	                <div class="input-group w940">
	                    <input type="text" id="contentTitle" class="form-control" placeholder="제목 을 입력해주세요.">
	                </div>
	            </td>
	        </tr>
	        <tr>
	            <th class="text-left">select</th>
	            <td class="text-left">
	                <div class="input-group">
	                    <select class="form-select w120">
	                        <option value="">년</option>
	                        <option value="">선택2</option>
	                        <option value="">선택3</option>
	                    </select>
	                    <select class="form-select w120">
	                        <option value="">월</option>
	                        <option value="">선택2</option>
	                        <option value="">선택3</option>
	                    </select>
	                    <select class="form-select w120">
	                        <option value="">일</option>
	                        <option value="">선택2</option>
	                        <option value="">선택3</option>
	                    </select>
	                </div>
	            </td>
	            <th class="text-left">input : text</th>
	            <td class="text-left">
	                <div class="input-group w300">
	                    <input type="text" id="golfCourseName" class="form-control" placeholder="골프장 명을 입력해주세요.">
	                </div>
	            </td>
	        </tr>
	        <tr>
	            <th class="text-left">select</th>
	            <td colspan="3">
	                <div class="input-group">
	                    <select class="form-select w400">
	                        <option value="">선택1</option>
	                        <option value="">선택2</option>
	                        <option value="">선택3</option>
	                    </select>
	                </div>
	            </td>
	        </tr>
	
	    </tbody>
	</table>
	<br><hr><br>


	<p>test!</p>
    list : ${list}<br>
</main>