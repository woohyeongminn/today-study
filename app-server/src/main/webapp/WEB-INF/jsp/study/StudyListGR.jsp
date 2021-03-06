<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!-- @ page import="java.sql.DriverManager"%>
@ page import="java.sql.Connection"%>
@ page import="java.sql.PreparedStatement"%>
@ page import="java.sql.ResultSet"%>
@ page import="java.sql.SQLException"%> -->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<style>
.inner-page {
	height: 230vmin;
}

* {
	margin: 0;
	padding: 0;
	font-size: 14px;
	line-height: 1.5;
}

ul {
	list-style: none;
}

.tabmenu {
	max-width: 1000px;
	margin: 0 auto;
	position: relative;
	margin-top: 50px;
}

.tabmenu ul li {
	display: inline-block;
	width: 33.33%;
	float: left;
}

.tabmenu ul li a {
	display: block;
	line-height: 40px;
	text-decoration: none;
}

.tabCon {
	display: none;
	padding: 20px 0px;
	position: absolute;
	left: 0;
	top: 40px;
	box-sizing: border-box;
	width: 1000px;
}

.btnCon:target {
	background: rgb(247, 231, 215);
	xfont-weight: bold;
}

.btnCon:target .tabCon {
	display: block;
}

.card-body {
	flex: 1 1 auto;
	padding: 1rem 1rem;
	height: 185px;
}

#search {
	text-align: center;
}

#content {
	max-height: 1350px;
	overflow-y: scroll;
	overflow-x: hidden;
}

#empty-study {
	text-align: center;
}

.mb-3 select {
	height: 33.5px;
	width: 470px;
	xwidth: 100px;
}
</style>
</head>

<!-- ๊ธฐ์กด tab ver -->
<!-- <h3><a href="list?perno=${perno}">๐ ์คํฐ๋ ๋ชฉ๋ก</a></h3><br> -->
<!-- <div class="row" style="background-color: yellow">
  <div class="col-md-4"><a href='list?perno=${perno}'>์ ์ฒด</a></div>
  <div class="col-md-4"><a href='list/ing?perno=${perno}'>์งํ</a></div>
  <div class="col-md-4"><a href='list/end?perno=${perno}'>์๋ฃ</a></div>
  </div> -->
<!-- <button>
		<a href='list?perno=${perno}'>์ ์ฒด</a>
	</button>
	<button>
		<a href='list/ing?perno=${perno}'>์งํ</a>
	</button>
	<button>
		<a href='list/end?perno=${perno}'>์๋ฃ</a>
	</button>
	<br>
	
	<!-- [GR] Search Ver.2 -->
<!-- <div class="input-group mb-3">
		<select name="sk">
			<option value="area">์ง์ญ</option>
			<option value="subjectName">๋ถ์ผ</option>
			<option value="studyTitle">์คํฐ๋๋ช</option>
		</select> <input type="text" name="sv" class="form-control"
			placeholder="์ง์ญ / ๋ถ์ผ / ์คํฐ๋๋ช์ผ๋ก ๊ฒ์ํ  ์ ์์ต๋๋ค."
			aria-label="Recipient's username" aria-describedby="button-addon2">
		<input type="submit" value="๊ฒ์" class="btn btn-outline-secondary"
			id="button-addon2">
	</div>  -->

<body>
	<div class="tabmenu">
		<ul>
			<!--===== ์ ์ฒด ์คํฐ๋ ๋ชฉ๋ก =====-->
			<li id="tab1" class="btnCon"><a class="btn first" href="#tab1">์ ์ฒด</a>
				<div class="tabCon">
					<br>

					<!-- ๊ฒ์ -->
					<div id="search">
						<form action="search" method='get'>
							<select name="where">
								<option value="1">๋ถ์ผ</option>
								<option value="2">์ ๋ชฉ</option>
								<option value="3">์ง์ญ</option>
							</select> <input type="text" name="keyword">
							<button class="btn btn-outline-dark btn-sm">๊ฒ์</button>
						</form>
					</div>

					<!-- ํ์ -->
					<c:if test="${loginUser ne null}">
						<!-- ๊ธฐ์กด ์คํฐ๋ ๋ฑ๋ก -->
						<!-- <div id="button"
							class="d-grid gap-2 d-md-flex justify-content-md-end">
							<a href='form' class="btn btn-light">๊ธ์ฐ๊ธฐ</a>
						</div> -->

						<!-- ์คํฐ๋ ๋ฑ๋ก -->
						<button type="button" class="btn btn-light" data-bs-toggle="modal"
							data-bs-target="#exampleModal" data-bs-whatever="@mdo">๊ธ์ฐ๊ธฐ</button>

						<div class="modal fade" id="exampleModal" tabindex="-1"
							aria-labelledby="exampleModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">

									<!-- ์๋จ ํค๋ -->
									<div class="modal-header">
										<h5 class="modal-title" id="exampleModalLabel">์คํฐ๋ ๋ฑ๋ก</h5>
										<button type="button" class="btn-close"
											data-bs-dismiss="modal" aria-label="Close"></button>
									</div>

									<div class="modal-body">
										<form action='add' method='post'>
											<!-- ์ ๋ชฉ -->
											<div class="mb-3">
												<label for='f-studyTitle'>์ ๋ชฉ</label> <input
													id='f-studyTitle' type='text' name='studyTitle'
													class="form-control" required
													oninvalid="this.setCustomValidity('์ ๋ชฉ์ ์๋ ฅํ์ธ์.')"
													oninput="this.setCustomValidity('')">
											</div>

											<!-- ๋ถ์ผ -->
											<div class="mb-3">
												<label for='f-subjectNo'>๋ถ์ผ</label> <select name="subjectNo">
													<option value="1" name="subjectNo" selected>์ดํ</option>
													<option value="2" selected>์๊ฒฉ์ฆ</option>
													<option value="3" selected>์ทจ์</option>
													<option value="4" selected>IT</option>
													<option value="5" selected>์์ฒด๋ฅ</option>
													<option value="6" selected>๊ธฐํ</option>
												</select>
											</div>



											<!-- <input type="text" id="sample6_postcode" placeholder="์ฐํธ๋ฒํธ">
											<input type="button" onclick="sample6_execDaumPostcode()"
												value="์ฐํธ๋ฒํธ ์ฐพ๊ธฐ"><br> <input type="text"
												id="sample6_address" placeholder="์ฃผ์"><br> <input
												type="text" id="sample6_detailAddress" placeholder="์์ธ์ฃผ์">
											<input type="text" id="sample6_extraAddress"
												placeholder="์ฐธ๊ณ ํญ๋ชฉ">

											<script
												src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
											<script>
												function sample6_execDaumPostcode() {
													new daum.Postcode(
															{
																oncomplete : function(
																		data) {
																	// ํ์์์ ๊ฒ์๊ฒฐ๊ณผ ํญ๋ชฉ์ ํด๋ฆญํ์๋ ์คํํ  ์ฝ๋๋ฅผ ์์ฑํ๋ ๋ถ๋ถ.

																	// ๊ฐ ์ฃผ์์ ๋ธ์ถ ๊ท์น์ ๋ฐ๋ผ ์ฃผ์๋ฅผ ์กฐํฉํ๋ค.
																	// ๋ด๋ ค์ค๋ ๋ณ์๊ฐ ๊ฐ์ด ์๋ ๊ฒฝ์ฐ์ ๊ณต๋ฐฑ('')๊ฐ์ ๊ฐ์ง๋ฏ๋ก, ์ด๋ฅผ ์ฐธ๊ณ ํ์ฌ ๋ถ๊ธฐ ํ๋ค.
																	var addr = ''; // ์ฃผ์ ๋ณ์
																	var extraAddr = ''; // ์ฐธ๊ณ ํญ๋ชฉ ๋ณ์

																	//์ฌ์ฉ์๊ฐ ์ ํํ ์ฃผ์ ํ์์ ๋ฐ๋ผ ํด๋น ์ฃผ์ ๊ฐ์ ๊ฐ์ ธ์จ๋ค.
																	if (data.userSelectedType === 'R') { // ์ฌ์ฉ์๊ฐ ๋๋ก๋ช ์ฃผ์๋ฅผ ์ ํํ์ ๊ฒฝ์ฐ
																		addr = data.roadAddress;
																	} else { // ์ฌ์ฉ์๊ฐ ์ง๋ฒ ์ฃผ์๋ฅผ ์ ํํ์ ๊ฒฝ์ฐ(J)
																		addr = data.jibunAddress;
																	}

																	// ์ฌ์ฉ์๊ฐ ์ ํํ ์ฃผ์๊ฐ ๋๋ก๋ช ํ์์ผ๋ ์ฐธ๊ณ ํญ๋ชฉ์ ์กฐํฉํ๋ค.
																	if (data.userSelectedType === 'R') {
																		// ๋ฒ์ ๋๋ช์ด ์์ ๊ฒฝ์ฐ ์ถ๊ฐํ๋ค. (๋ฒ์ ๋ฆฌ๋ ์ ์ธ)
																		// ๋ฒ์ ๋์ ๊ฒฝ์ฐ ๋ง์ง๋ง ๋ฌธ์๊ฐ "๋/๋ก/๊ฐ"๋ก ๋๋๋ค.
																		if (data.bname !== ''
																				&& /[๋|๋ก|๊ฐ]$/g
																						.test(data.bname)) {
																			extraAddr += data.bname;
																		}
																		// ๊ฑด๋ฌผ๋ช์ด ์๊ณ , ๊ณต๋์ฃผํ์ผ ๊ฒฝ์ฐ ์ถ๊ฐํ๋ค.
																		if (data.buildingName !== ''
																				&& data.apartment === 'Y') {
																			extraAddr += (extraAddr !== '' ? ', '
																					+ data.buildingName
																					: data.buildingName);
																		}
																		// ํ์ํ  ์ฐธ๊ณ ํญ๋ชฉ์ด ์์ ๊ฒฝ์ฐ, ๊ดํธ๊น์ง ์ถ๊ฐํ ์ต์ข ๋ฌธ์์ด์ ๋ง๋ ๋ค.
																		if (extraAddr !== '') {
																			extraAddr = ' ('
																					+ extraAddr
																					+ ')';
																		}
																		// ์กฐํฉ๋ ์ฐธ๊ณ ํญ๋ชฉ์ ํด๋น ํ๋์ ๋ฃ๋๋ค.
																		document
																				.getElementById("sample6_extraAddress").value = extraAddr;

																	} else {
																		document
																				.getElementById("sample6_extraAddress").value = '';
																	}

																	// ์ฐํธ๋ฒํธ์ ์ฃผ์ ์ ๋ณด๋ฅผ ํด๋น ํ๋์ ๋ฃ๋๋ค.
																	document
																			.getElementById('sample6_postcode').value = data.zonecode;
																	document
																			.getElementById("sample6_address").value = addr;
																	// ์ปค์๋ฅผ ์์ธ์ฃผ์ ํ๋๋ก ์ด๋ํ๋ค.
																	document
																			.getElementById(
																					"sample6_detailAddress")
																			.focus();
																}
															}).open();
												}
											</script> -->



											<!-- ์ง์ญ -->
											<div class="mb-3">
												<label for='f-area'>์ง์ญ</label> <input id='f-area'
													type='text' name='area' class="form-control" required
													oninvalid="this.setCustomValidity('์ / ๋ / ๊ตฌ๋ฅผ ์๋ ฅํ์ธ์.')"
													oninput="this.setCustomValidity('')">
											</div>

											<!-- ์ต๋ ์ธ์์ -->
											<div class="mb-3">
												<label for='f-numberOfPeple'>์ต๋ ์ธ์์</label> <select
													name="numberOfPeple">
													<option value="2" name="numberOfPeple" selected>2</option>
													<option value="3" selected>3</option>
													<option value="4" selected>4</option>
													<option value="5" selected>5</option>
													<option value="6" selected>6</option>
													<option value="7" selected>7</option>
													<option value="8" selected>8</option>
													<option value="9" selected>9</option>
													<option value="10" selected>10</option>
													<option value="11" selected>11</option>
													<option value="12" selected>12</option>
													<option value="13" selected>13</option>
													<option value="14" selected>14</option>
													<option value="15" selected>15</option>
													<option value="16" selected>16</option>
													<option value="17" selected>17</option>
													<option value="18" selected>18</option>
													<option value="19" selected>19</option>
													<option value="20" selected>20</option>
													<option value="21" selected>21</option>
													<option value="22" selected>22</option>
													<option value="23" selected>23</option>
													<option value="24" selected>24</option>
													<option value="25" selected>25</option>
													<option value="26" selected>26</option>
													<option value="27" selected>27</option>
													<option value="28" selected>28</option>
													<option value="29" selected>29</option>
													<option value="30" selected>30</option>
												</select>
											</div>

											<!-- ๋๋ฉด ์ํ -->
											<div class="mb-3">
												<label for='f-faceNo'>๋๋ฉด ์ํ</label> <select name="faceNo">
													<option value="1" name="faceNo" selected>๋๋ฉด</option>
													<option value="2" selected>๋น๋๋ฉด</option>
													<option value="3" selected>๋๋ฉด/๋น๋๋ฉด</option>
												</select>
											</div>

											<!-- ์๊ฐ๊ธ -->
											<div class="mb-3">
												<label for='f-introduction'>์๊ฐ๊ธ</label>
												<textarea id='f-introduction' type='text'
													name='introduction' class="form-control" rows="3" required
													oninvalid="this.setCustomValidity('์๊ฐ๊ธ์ ์๋ ฅํ์ธ์.')"
													oninput="this.setCustomValidity('')"></textarea>
											</div>

											<!-- ์งํ ์ํ -->
											<div class="mb-3">
												<label for='f-studyStatus'>์งํ ์ํ</label> <select
													name="studyStatus">
													<option value="1" name="studyStatus" selected>์งํ</option>
													<option value="2" disabled>์ข๋ฃ</option>
												</select>
											</div>

											<!-- ํ๋จ ๋ฒํผ -->
											<div class="modal-footer">
												<button type="button" class="btn btn-light"
													data-bs-dismiss="modal">์ทจ์</button>
												<button class="btn btn-dark">๋ฑ๋ก</button>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<br>

					<c:if test='${not empty studyList}'>
						<div id="content">
							<div class="row row-cols-1 row-cols-md-2 g-5">
								<c:forEach items="${studyList}" var="study">
									<div class="col">
										<div class="card">

											<!-- ์คํฐ๋ ๋ฉ์ธ -->
											<div class="card-header">

												<!-- ์คํฐ๋ ๋ชจ์ง ์ํ -->
												<c:choose>
													<c:when
														test="${study.countMember ne study.numberOfPeple && study.studyStatus ne '2'}">
														<button type="button" class="btn btn-primary btn-sm">๋ชจ์ง์ค</button>
													</c:when>
													<c:when
														test="${study.countMember eq study.numberOfPeple && study.studyStatus ne '2'}">
														<button type="button" class="btn btn-primary btn-sm">๋ชจ์ง์ค</button>
													</c:when>
													<c:when
														test="${study.countMember ne study.numberOfPeple && study.studyStatus eq '2'}">
														<button type="button" class="btn btn-secondary btn-sm">๋ชจ์ง์๋ฃ</button>
													</c:when>
													<c:when
														test="${study.countMember eq study.numberOfPeple && study.studyStatus eq '2'}">
														<button type="button" class="btn btn-secondary btn-sm">๋ชจ์ง์๋ฃ</button>
													</c:when>
												</c:choose>

												${study.subjectName}
											</div>

											<!-- ์คํฐ๋ ๋ถ๊ฐ ์ ๋ณด -->
											<div class="card-body">
												<h5 class="card-title" style="font-weight: bold">
													<a href='detail?studyno=${study.studyNo}'>${study.studyTitle}</a>
												</h5>
												<p class="card-text">${study.introduction}</p>
												${study.faceName}<br> ์ธ์
												${study.countMember}/${study.numberOfPeple}<br>
												${study.owner.perNickname} โญ${study.countBookMember}
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:if>

					<!-- ๊ฒ์ ๊ฒฐ๊ณผ -->
					<div id="empty-study">
						<c:if test='${empty studyList}'>
   ๊ฒ์ ๊ฒฐ๊ณผ๊ฐ ์กด์ฌํ์ง ์์ต๋๋ค.<br>
							<br>
						</c:if>
					</div>
				</div></li>

			<!-- ๊ธฐ์กด ์คํฐ๋ ๋ชฉ๋ก ver -->
			<!-- <div id="content">
    <div class="row row-cols-1 row-cols-md-2 g-4">
    <c:forEach items="${studyList}" var="study">
      <div class="col">
        <div class="card">
          <div class="card-body">
            <span style="color:royalblue">${study.subjectName}</span><br>
            <span style="font-weight: bold"><a href='detail?studyno=${study.studyNo}'>${study.studyTitle}</a></span><br>
            ${study.faceName}<br>
            <c:if test="${study.faceName ne '๋น๋๋ฉด'}">
            ${study.area}<br>
            </c:if>
            ์ธ์ ${study.countMember}/${study.numberOfPeple}<br>
            ${study.owner.perNickname}
            ๐${study.countBookMember}
          </div>
        </div>
      </div>
    </c:forEach>
    </div>
  </div> -->

			<!--===== ์งํ ์คํฐ๋ ๋ชฉ๋ก =====-->
			<li id="tab2" class="btnCon"><a class="btn" href="#tab2">์งํ</a>
				<div class="tabCon">
					<br>

					<!-- ๊ฒ์ -->
					<div id="search">
						<form action="search" method='get'>
							<select name="where">
								<option value="1">๋ถ์ผ</option>
								<option value="2">์ ๋ชฉ</option>
								<option value="3">์ง์ญ</option>
							</select> <input type="text" name="keyword">
							<button class="btn btn-outline-dark btn-sm">๊ฒ์</button>
						</form>
					</div>

					<!-- ํ์ -->
					<c:if test="${loginUser ne null}">

						<!-- ์คํฐ๋ ๋ฑ๋ก -->
						<button type="button" class="btn btn-light" data-bs-toggle="modal"
							data-bs-target="#exampleModal" data-bs-whatever="@mdo">๊ธ์ฐ๊ธฐ</button>

						<div class="modal fade" id="exampleModal" tabindex="-1"
							aria-labelledby="exampleModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">

									<!-- ์๋จ ํค๋ -->
									<div class="modal-header">
										<h5 class="modal-title" id="exampleModalLabel">์คํฐ๋ ๋ฑ๋ก</h5>
										<button type="button" class="btn-close"
											data-bs-dismiss="modal" aria-label="Close"></button>
									</div>

									<div class="modal-body">
										<form action='add' method='post'>
											<!-- ์ ๋ชฉ -->
											<div class="mb-3">
												<label for='f-studyTitle'>์ ๋ชฉ</label> <input
													id='f-studyTitle' type='text' name='studyTitle'
													class="form-control" required
													oninvalid="this.setCustomValidity('์ ๋ชฉ์ ์๋ ฅํ์ธ์.')"
													oninput="this.setCustomValidity('')">
											</div>

											<!-- ๋ถ์ผ -->
											<div class="mb-3">
												<label for='f-subjectNo'>๋ถ์ผ</label> <select name="subjectNo">
													<option value="1" name="subjectNo" selected>์ดํ</option>
													<option value="2" selected>์๊ฒฉ์ฆ</option>
													<option value="3" selected>์ทจ์</option>
													<option value="4" selected>IT</option>
													<option value="5" selected>์์ฒด๋ฅ</option>
													<option value="6" selected>๊ธฐํ</option>
												</select>
											</div>

											<!-- ์ง์ญ -->
											<div class="mb-3">
												<label for='f-area'>์ง์ญ</label> <input id='f-area'
													type='text' name='area' class="form-control" required
													oninvalid="this.setCustomValidity('์ง์ญ์ ์๋ ฅํ์ธ์.')"
													oninput="this.setCustomValidity('')">
											</div>

											<!-- ์ต๋ ์ธ์์ -->
											<div class="mb-3">
												<label for='f-numberOfPeple'>์ต๋ ์ธ์์</label> <select
													name="numberOfPeple">
													<option value="2" name="numberOfPeple" selected>2</option>
													<option value="3" selected>3</option>
													<option value="4" selected>4</option>
													<option value="5" selected>5</option>
													<option value="6" selected>6</option>
													<option value="7" selected>7</option>
													<option value="8" selected>8</option>
													<option value="9" selected>9</option>
													<option value="10" selected>10</option>
													<option value="11" selected>11</option>
													<option value="12" selected>12</option>
													<option value="13" selected>13</option>
													<option value="14" selected>14</option>
													<option value="15" selected>15</option>
													<option value="16" selected>16</option>
													<option value="17" selected>17</option>
													<option value="18" selected>18</option>
													<option value="19" selected>19</option>
													<option value="20" selected>20</option>
													<option value="21" selected>21</option>
													<option value="22" selected>22</option>
													<option value="23" selected>23</option>
													<option value="24" selected>24</option>
													<option value="25" selected>25</option>
													<option value="26" selected>26</option>
													<option value="27" selected>27</option>
													<option value="28" selected>28</option>
													<option value="29" selected>29</option>
													<option value="30" selected>30</option>
												</select>
											</div>

											<!-- ๋๋ฉด ์ํ -->
											<div class="mb-3">
												<label for='f-faceNo'>๋๋ฉด ์ํ</label> <select name="faceNo">
													<option value="1" name="faceNo" selected>๋๋ฉด</option>
													<option value="2" selected>๋น๋๋ฉด</option>
													<option value="3" selected>๋๋ฉด/๋น๋๋ฉด</option>
												</select>
											</div>

											<!-- ์๊ฐ๊ธ -->
											<div class="mb-3">
												<label for='f-introduction'>์๊ฐ๊ธ</label>
												<textarea id='f-introduction' type='text'
													name='introduction' class="form-control" rows="3" required
													oninvalid="this.setCustomValidity('์๊ฐ๊ธ์ ์๋ ฅํ์ธ์.')"
													oninput="this.setCustomValidity('')"></textarea>
											</div>

											<!-- ์งํ ์ํ -->
											<div class="mb-3">
												<label for='f-studyStatus'>์งํ ์ํ</label> <select
													name="studyStatus">
													<option value="1" name="studyStatus" selected>์งํ</option>
													<option value="2" disabled>์ข๋ฃ</option>
												</select>
											</div>

											<!-- ํ๋จ ๋ฒํผ -->
											<div class="modal-footer">
												<button type="button" class="btn btn-light"
													data-bs-dismiss="modal">์ทจ์</button>
												<button class="btn btn-dark">๋ฑ๋ก</button>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<br>

					<c:if test='${not empty studyIngList}'>
						<div id="content">
							<div class="row row-cols-1 row-cols-md-2 g-5">
								<c:forEach items="${studyIngList}" var="study">
									<div class="col">
										<div class="card">

											<!-- ์คํฐ๋ ๋ฉ์ธ -->
											<div class="card-header">

												<!-- ์คํฐ๋ ๋ชจ์ง ์ํ -->
												<c:choose>
													<c:when
														test="${study.countMember ne study.numberOfPeple && study.studyStatus ne '2'}">
														<button type="button" class="btn btn-primary btn-sm">๋ชจ์ง์ค</button>
													</c:when>
													<c:when
														test="${study.countMember eq study.numberOfPeple && study.studyStatus ne '2'}">
														<button type="button" class="btn btn-primary btn-sm">๋ชจ์ง์ค</button>
													</c:when>
													<c:when
														test="${study.countMember ne study.numberOfPeple && study.studyStatus eq '2'}">
														<button type="button" class="btn btn-secondary btn-sm">๋ชจ์ง์๋ฃ</button>
													</c:when>
													<c:when
														test="${study.countMember eq study.numberOfPeple && study.studyStatus eq '2'}">
														<button type="button" class="btn btn-secondary btn-sm">๋ชจ์ง์๋ฃ</button>
													</c:when>
												</c:choose>

												${study.subjectName}
											</div>

											<!-- ์คํฐ๋ ๋ถ๊ฐ ์ ๋ณด -->
											<div class="card-body">
												<h5 class="card-title" style="font-weight: bold">
													<a href='detail?studyno=${study.studyNo}'>${study.studyTitle}</a>
												</h5>
												<p class="card-text">${study.introduction}</p>
												${study.faceName}<br> ์ธ์
												${study.countMember}/${study.numberOfPeple}<br>
												${study.owner.perNickname} โญ${study.countBookMember}
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:if>

					<!-- ๊ฒ์ ๊ฒฐ๊ณผ -->
					<div id="empty-study">
						<c:if test='${empty studyIngList}'>
   ๊ฒ์ ๊ฒฐ๊ณผ๊ฐ ์กด์ฌํ์ง ์์ต๋๋ค.<br>
							<br>
						</c:if>
					</div>
				</div></li>

			<!--===== ์ข๋ฃ ์คํฐ๋ ๋ชฉ๋ก =====-->
			<li id="tab3" class="btnCon"><a class="btn" href="#tab3">์ข๋ฃ</a>
				<div class="tabCon">
					<br>

					<!-- ๊ฒ์ -->
					<div id="search">
						<form action="search" method='get'>
							<select name="where">
								<option value="1">๋ถ์ผ</option>
								<option value="2">์ ๋ชฉ</option>
								<option value="3">์ง์ญ</option>
							</select> <input type="text" name="keyword">
							<button class="btn btn-outline-dark btn-sm">๊ฒ์</button>
						</form>
					</div>

					<!-- ํ์ -->
					<c:if test="${loginUser ne null}">

						<!-- ์คํฐ๋ ๋ฑ๋ก -->
						<button type="button" class="btn btn-light" data-bs-toggle="modal"
							data-bs-target="#exampleModal" data-bs-whatever="@mdo">๊ธ์ฐ๊ธฐ</button>

						<div class="modal fade" id="exampleModal" tabindex="-1"
							aria-labelledby="exampleModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">

									<!-- ์๋จ ํค๋ -->
									<div class="modal-header">
										<h5 class="modal-title" id="exampleModalLabel">์คํฐ๋ ๋ฑ๋ก</h5>
										<button type="button" class="btn-close"
											data-bs-dismiss="modal" aria-label="Close"></button>
									</div>

									<div class="modal-body">
										<form action='add' method='post'>
											<!-- ์ ๋ชฉ -->
											<div class="mb-3">
												<label for='f-studyTitle'>์ ๋ชฉ</label> <input
													id='f-studyTitle' type='text' name='studyTitle'
													class="form-control" required
													oninvalid="this.setCustomValidity('์ ๋ชฉ์ ์๋ ฅํ์ธ์.')"
													oninput="this.setCustomValidity('')">
											</div>

											<!-- ๋ถ์ผ -->
											<div class="mb-3">
												<label for='f-subjectNo'>๋ถ์ผ</label> <select name="subjectNo">
													<option value="1" name="subjectNo" selected>์ดํ</option>
													<option value="2" selected>์๊ฒฉ์ฆ</option>
													<option value="3" selected>์ทจ์</option>
													<option value="4" selected>IT</option>
													<option value="5" selected>์์ฒด๋ฅ</option>
													<option value="6" selected>๊ธฐํ</option>
												</select>
											</div>

											<!-- ์ง์ญ -->
											<div class="mb-3">
												<label for='f-area'>์ง์ญ</label> <input id='f-area'
													type='text' name='area' class="form-control" required
													oninvalid="this.setCustomValidity('์ง์ญ์ ์๋ ฅํ์ธ์.')"
													oninput="this.setCustomValidity('')">
											</div>

											<!-- ์ต๋ ์ธ์์ -->
											<div class="mb-3">
												<label for='f-numberOfPeple'>์ต๋ ์ธ์์</label> <select
													name="numberOfPeple">
													<option value="2" name="numberOfPeple" selected>2</option>
													<option value="3" selected>3</option>
													<option value="4" selected>4</option>
													<option value="5" selected>5</option>
													<option value="6" selected>6</option>
													<option value="7" selected>7</option>
													<option value="8" selected>8</option>
													<option value="9" selected>9</option>
													<option value="10" selected>10</option>
													<option value="11" selected>11</option>
													<option value="12" selected>12</option>
													<option value="13" selected>13</option>
													<option value="14" selected>14</option>
													<option value="15" selected>15</option>
													<option value="16" selected>16</option>
													<option value="17" selected>17</option>
													<option value="18" selected>18</option>
													<option value="19" selected>19</option>
													<option value="20" selected>20</option>
													<option value="21" selected>21</option>
													<option value="22" selected>22</option>
													<option value="23" selected>23</option>
													<option value="24" selected>24</option>
													<option value="25" selected>25</option>
													<option value="26" selected>26</option>
													<option value="27" selected>27</option>
													<option value="28" selected>28</option>
													<option value="29" selected>29</option>
													<option value="30" selected>30</option>
												</select>
											</div>

											<!-- ๋๋ฉด ์ํ -->
											<div class="mb-3">
												<label for='f-faceNo'>๋๋ฉด ์ํ</label> <select name="faceNo">
													<option value="1" name="faceNo" selected>๋๋ฉด</option>
													<option value="2" selected>๋น๋๋ฉด</option>
													<option value="3" selected>๋๋ฉด/๋น๋๋ฉด</option>
												</select>
											</div>

											<!-- ์๊ฐ๊ธ -->
											<div class="mb-3">
												<label for='f-introduction'>์๊ฐ๊ธ</label>
												<textarea id='f-introduction' type='text'
													name='introduction' class="form-control" rows="3" required
													oninvalid="this.setCustomValidity('์๊ฐ๊ธ์ ์๋ ฅํ์ธ์.')"
													oninput="this.setCustomValidity('')"></textarea>
											</div>

											<!-- ์งํ ์ํ -->
											<div class="mb-3">
												<label for='f-studyStatus'>์งํ ์ํ</label> <select
													name="studyStatus">
													<option value="1" name="studyStatus" selected>์งํ</option>
													<option value="2" disabled>์ข๋ฃ</option>
												</select>
											</div>

											<!-- ํ๋จ ๋ฒํผ -->
											<div class="modal-footer">
												<button type="button" class="btn btn-light"
													data-bs-dismiss="modal">์ทจ์</button>
												<button class="btn btn-dark">๋ฑ๋ก</button>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<br>

					<c:if test='${not empty studyEndList}'>
						<div id="content">
							<div class="row row-cols-1 row-cols-md-2 g-5">
								<c:forEach items="${studyEndList}" var="study">
									<div class="col">
										<div class="card">

											<!-- ์คํฐ๋ ๋ฉ์ธ -->
											<div class="card-header">

												<!-- ์คํฐ๋ ๋ชจ์ง ์ํ -->
												<c:choose>
													<c:when
														test="${study.countMember ne study.numberOfPeple && study.studyStatus ne '2'}">
														<button type="button" class="btn btn-primary btn-sm">๋ชจ์ง์ค</button>
													</c:when>
													<c:when
														test="${study.countMember eq study.numberOfPeple && study.studyStatus ne '2'}">
														<button type="button" class="btn btn-primary btn-sm">๋ชจ์ง์ค</button>
													</c:when>
													<c:when
														test="${study.countMember ne study.numberOfPeple && study.studyStatus eq '2'}">
														<button type="button" class="btn btn-secondary btn-sm">๋ชจ์ง์๋ฃ</button>
													</c:when>
													<c:when
														test="${study.countMember eq study.numberOfPeple && study.studyStatus eq '2'}">
														<button type="button" class="btn btn-secondary btn-sm">๋ชจ์ง์๋ฃ</button>
													</c:when>
												</c:choose>

												${study.subjectName}
											</div>

											<!-- ์คํฐ๋ ๋ถ๊ฐ ์ ๋ณด -->
											<div class="card-body">
												<h5 class="card-title" style="font-weight: bold">
													<a href='detail?studyno=${study.studyNo}'>${study.studyTitle}</a>
												</h5>
												<p class="card-text">${study.introduction}</p>
												${study.faceName}<br> ์ธ์
												${study.countMember}/${study.numberOfPeple}<br>
												${study.owner.perNickname} โญ${study.countBookMember}
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:if>

					<!-- ๊ฒ์ ๊ฒฐ๊ณผ -->
					<div id="empty-study">
						<c:if test='${empty studyEndList}'>
   ๊ฒ์ ๊ฒฐ๊ณผ๊ฐ ์กด์ฌํ์ง ์์ต๋๋ค.<br>
							<br>
						</c:if>
					</div>
				</div></li>
		</ul>
	</div>

	<script>
		location.href = "#tab1";
	</script>

</body>
</html>

<!-- ํ์ด๋ธ ver ์คํฐ๋ ๋ชฉ๋ก -->
<!-- <table class="table table-hover">
		<thead>
			<tr>
				<th>๋ฒํธ</th>
				<th>๋ถ๋งํฌ</th>
				<th>์ ๋ชฉ</th>
				<th>๋๋ฉด/๋น๋๋ฉด</th>
				<th>์กฐ์ฅ</th>
				<th>๋ถ์ผ</th>
				<th>์ง์ญ</th>
				<th>์ธ์์</th>
				<th>์ต๋ ์ธ์์</th>
			</tr>
			</thead> -->

<!-- [GR] Search Ver.2 -->
<!-- request.setCharacterEncoding("utf-8");
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			Class.forName("com.mysql.jdbc.Driver");
			String sk = request.getParameter("sk");
			String sv = request.getParameter("sv");

			try {
			  String jdbcDriver =
			  "jdbc:mysql://localhost:3306/doublesdb?" + "useUnicode=true&characterEncoding=euckr";
			  String dbUser = "doublesid";
			  String dbpass = "doublespw";
			  conn = DriverManager.getConnection(jdbcDriver, dbUser, dbpass);
			  if (sk == null & sv == null) {
			    pstmt = conn.prepareStatement("select * from study");
			  } else if (sk != null & sv.equals("")) {
			    pstmt = conn.prepareStatement("select * from study");
			  } else if (sk != null & sv != null) {
			    pstmt = conn.prepareStatement("select * from study where " + sk + "=?");
			    pstmt.setString(1, sv);
			  }
			  rs = pstmt.executeQuery();
			  while (rs.next()) {
			
			<tr>
				<td>=rs.getString("area")%></td>
				<td>=rs.getString("subjectName")</td>
				<td>=rs.getString("studyTitle")</td>
			</tr>
			
			}
			} catch (SQLException ex) {
			ex.printStackTrace();

			} finally {
			if (rs != null)
			try {
			  rs.close();
			} catch (SQLException ex) {
			}
			if (pstmt != null)
			try {
			  pstmt.close();
			} catch (SQLException ex) {
			}

			if (conn != null)
			try {
			  conn.close();
			} catch (SQLException ex) {
			}
			} -->

<!-- [GR] Search Ver.3 -->
<!-- @ include file="/study/StudySearch.jsp"%>
			
			request.setCharacterEncoding("utf-8");
			String sk = request.getParameter("sk");
			String sv = request.getParameter("sv");

			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			Class.forName("org.mariadb.jdbc.Driver");

			String jdbcDriver = "jdbc:mariadb://localhost:8080/jdbc.url";
			String dbUser = "ogong";
			String dbPass = "1111";
			conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);

			if (sk == null & sv == null) {
			  pstmt = conn.prepareStatement("select * from study");
			} else if (sk != null & sv.equals("")) {
			  pstmt = conn.prepareStatement("select * from study");
			} else if (sk != null & sv != null) {
			  if (sk.equals("area")) {
			    pstmt = conn.prepareStatement("select * from study where area=?");
			    pstmt.setString(1, sv);
			  } else if (sk.equals("name")) {
			    pstmt = conn.prepareStatement("select * from study_subject where name=?");
			    pstmt.setString(1, sv);
			  } else if (sk.equals("name")) {
			    pstmt = conn.prepareStatement("select * from study where name=?");
			    pstmt.setString(1, sv);
			  }
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
			%> -->
<!-- <tbody>
			<c:forEach items="${studyList}" var="study">
				<tr>
					<td>${study.studyNo}</td>
					<td>${study.countBookMember}</td>
					<td><a href='detail?studyno=${study.studyNo}&perno=${perno}'>${study.studyTitle}</a></td>
					<td>${study.faceName}</td>
					<td>${study.owner.perNickname}</td>
					<td>${study.subjectName}</td>
					<td>${study.area}</td>
					<td>${study.countMember}</td>
					<td>${study.numberOfPeple}</td>
				</tr>
			</c:forEach>
		</tbody> -->
<!-- }
		rs.close();
		pstmt.close();
		conn.close();
		%>  -->
<!-- </table> -->
