<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<style>
* {
  font-size:14px;
}
label {
	margin-right: 5px;
	text-align: right;
	display: inline-block;
	width: 60px;
}
</style>
</head>
<body>
	<input type="hidden" name="loginUser" value="${loginUser.perNo}">
	<label for='f-studyNo'>번호</label>
	<input id='f-studyNo' type='text' name='studyno'
		value='${study.studyNo}' readonly>
	<br>
	<label for='f-countBookMember'>북마크</label>
	<input id='f-countBookMember' type='text' name='countbookmember'
		value='${study.countBookMember}' readonly>
	<br>
	<label for='f-studyTitle'>제목</label>
	<input id='f-studyTitle' type='text' name='studytitle'
		value='${study.studyTitle}' readonly>
	<br>
	<label for='f-owner'>조장</label>
	<input id='f-owner' type='text' name='owner'
		value='${owner.perNickname}' readonly>
	<br>
	<label for='f-subjectName'>분야</label>
	<input id='f-subjectName' type='text' name='subjectname'
		value='${study.subjectName}' readonly>
	<br>
	<label for='f-area'>지역</label>
	<input id='f-area' type='text' name='area' value='${study.area}'
		readonly>
	<br>
	<label for='f-countMember'>인원수</label>
	<input id='f-countMember' type='text' name='countmember'
		value='${study.countBookMember}' readonly>
	<br>
	<label for='f-numberOfPeple'>최대 인원수</label>
	<input id='f-numberOfPeple' type='text' name='numberofpeple'
		value='${study.numberOfPeple}' readonly>
	<br>
	<label for='f-faceName'>대면/비대면</label>
	<input id='f-faceName' type='text' name='facename'
		value='${study.faceName}' readonly>
	<br>
	<label for='f-introduction'>소개글</label>
	<input id='f-introduction' type='text' name='introduction'
		value='${study.introduction}' readonly>
	<br>
	<button>
		<a href='delete?studyno=${study.studyNo}'>삭제</a>
	</button>
	<button>
		<a href='list'>목록</a>
	</button>
	<br>
</body>
</html>