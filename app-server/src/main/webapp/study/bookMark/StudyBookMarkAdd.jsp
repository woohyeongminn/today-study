<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<title>북마크 추가 | 📖 스터디 찾기</title>
</head>
<body>
	<h1>북마크 추가</h1>
	<table border='1'>
		<tbody>
			<c:forEach items="${study.members}" var="joinMember">
				<c:if test="${member.perNo == joinMember.perNo}">이미 참여 중인 스터디입니다.</c:if>
			</c:forEach>

			<c:forEach items="${study.watingMember}" var="watingMember">
				<c:if test="${member.perNo == watingMember.perNo}">이미 승인 대기 중인 스터디입니다.</c:if>
			</c:forEach>

			<c:forEach items="${study.bookMarkMember}" var="bookMarkMember">
				<c:if test="${member.perNo == bookMarkMember.perNo}">이미 북마크 중인 스터디입니다.</c:if>
			</c:forEach>

			<p>북마크가 완료되었습니다.</p>
		</tbody>
	</table>
</body>
</html>
