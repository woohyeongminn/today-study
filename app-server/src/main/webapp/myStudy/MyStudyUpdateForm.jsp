<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>상세 | 내 스터디</title>
<style>
*{
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
  <h1>🖊 내 스터디 수정</h1>
  <form action='update' method='post'>
    <label for='f-studyTitle'>제목</label> <input id='f-studyTitle' type='text' name='studytitle'><br>
        <p>
				[ 분야 ]<br>
				1. 어학<br>
				2. 자격증<br>
				3. 취업<br>
				4. IT<br>
				5. 예체능<br>
				6. 기타<br>
		</p>
        <label for='f-subjectNo'>분야</label> <input id='f-subjectNo' type='text' name='subjectno'><br>
        <label for='f-area'>지역</label> <input id='f-area' type='text' name='area'><br>
        <label for='f-numberOfPeple'>최대 인원수</label> <input id='f-numberOfPeple' type='text' name='numberofpeple'><br>
        <p>
				[ 대면 상태 ]<br>
				1. 대면<br>
				2. 비대면<br>
				3. 대면/비대면<br>
		</p>
        <label for='f-faceNo'>대면/비대면</label> <input id='f-faceNo' type='text' name='faceno'><br>
        <label for='f-introduction'>소개글</label> <input id='f-introduction' type='text' name='introduction'><br>
    <button type="submit" value="수정">수정</button><br>
	</form>
</body>
</html>
