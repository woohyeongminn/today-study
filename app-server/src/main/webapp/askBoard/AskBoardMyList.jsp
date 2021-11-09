<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
   <title>문의 게시글(개인회원)</title>
  <link rel="stylesheet" href="../node_modules/bootstrap/dist/css/bootstrap.css">
  
  <script src="../node_modules/@popperjs/core/dist/umd/popper.js"></script>
  <script src="../node_modules/bootstrap/dist/js/bootstrap.js"></script>
  
  <style>
  label {
    margin-right: 5px;
    text-align: center;
    display: inline;
    width: 60px;
    size:100px;
  }
  legend {
    font-family: '굴림체';
    text-align: center;
     background-color: blanchedalmond;
     text-align: center;
     color: black;
     margin-top: 10px;
     font-size: 50px;
  }

  div {
  font-family: '굴림체';
  margin-right: 10px;
  }
  
  a {
  color : black;
  text-decoration : auto;
  }
  
  a:hover {
  color : lightgray;
  }
  
  #head {
  font-family: '굴림체';
  background-color: beige;
  color: black;
  font-size: 25px;
  }
  
  div {
    font-family: '굴림체';
  }
  
  td {
    font-size: 15px;
  }
  
  .btn {
   border-radius: 4px;
   background-color: blanchedalmond;
   color: black;
   font-size: 18px;
  }
  
  .btn:hover {
   background-color: beige;
   color: black;
  }
  
  #empty {
  text-align: center;
  }
  </style>
</head>
<body>
<jsp:include page="../header.jsp"/>

<fieldset>
<br>
<legend ><b> 💬 문의게시글 목록 </b></legend><br>
<hr>
<table class="table table-responsive">
<thead>
<tr id="head">
  <th>No.</th>
  <th>제목</th>
  <th>작성자</th>
  <th>조회수</th>
  <th>등록일</th>
<th></th>
</tr>
</thead>
<tbody>

<c:forEach items="${myAskBoardList}" var="askBoard">
<tr>
    <c:if test="${askBoard.askMemberWriter.perNo == loginUser.perNo}">
      <div>
        <td>${askBoard.askNo}.</td>
      </div>
        <td><a href='permydetail?askNo=${askBoard.askNo}'>${askBoard.askTitle}</a></td>
        <td>${askBoard.askMemberWriter.perNickname}</td>
        <td>${askBoard.askVeiwCount}</td>
        <td>${askBoard.askRegisteredDate}</td>     
           <c:choose>
             <c:when test="${empty askBoard.reply}">
               <td> 📕 </td>
             </c:when>
            <c:otherwise>
               <td> 📖 </td>
            </c:otherwise>
           </c:choose> 
    </c:if>
    <c:if test="${askBoard.askCeoWriter.ceoNo == loginCeoUser.ceoNo}">
      <div>
        <td>${askBoard.askNo}.</td>
      </div>
        <td><a href='ceomydetail?askNo=${askBoard.askNo}'>${askBoard.askTitle}</a></td>
        <td>${askBoard.askMemberWriter.perNickname}</td>
        <td>${askBoard.askVeiwCount}</td>
        <td>${askBoard.askRegisteredDate}</td>     
           <c:choose>
             <c:when test="${empty askBoard.reply}">
               <td> 📕 </td>
             </c:when>
            <c:otherwise>
               <td> 📖 </td>
            </c:otherwise>
           </c:choose> 
    </c:if>    
</tr>
</c:forEach>
</table>
</fieldset>
</tbody> 
<c:if test="${empty myAskBoardList}">
   <form id="empty">등록한 문의글이 없습니다.</form><br>
</c:if>
<c:if test="${not empty loginUser}">
<div class="d-grid gap-2 d-md-flex justify-content-md-end">
   <button class="btn btn-primary me-md-2" type="submit" value="등록" formaction="add">
   <a href='peraddform'>등록하기</a>
   </button>
</div>
</c:if>
<c:if test="${not empty loginCeoUser}">
<div class="d-grid gap-2 d-md-flex justify-content-md-end">
   <button class="btn btn-primary me-md-2" type="submit" value="등록" formaction="add">
   <a href='ceoaddform'>등록하기</a>
   </button>
</div>
</c:if>
</body>
</html> 









