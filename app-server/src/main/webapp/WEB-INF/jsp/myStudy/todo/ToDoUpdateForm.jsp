<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
  input {
  border : white;
  outline-color : lightgray;
  }
  button {
  margin-right: 10px;
  }
  a {
  color : black;
  text-decoration : auto;
  }
  a:hover {
  color : white;
  }
</style>

<br><br><br>

<div class="allbox">

    <form action='update' method='post'>

  <input type='hidden' name='studyno' value='${study.studyNo}'>
  <input type='hidden' name='todoNo' value='${todo.todoNo}'>
  <%-- <input type='hidden' name='perno' value='${member.perNo}'> --%>

    <table class="table table-hover text-center">
    <thead>
    
    <tr id="first">
      <th scope="col"></th>
      <th scope="col" id="title">π | To-Do List λ³κ²½</th>
      <th scope="col" id="date">${todo.todoDate}</th>
    </tr>
	
     <tr>
      <th scope="row" id="middlebox"><label for='progress_no'>μν</label></th>
      <td>
      <select name="progress_no">
        <c:choose>
	        <c:when test="${todo.todoStatus == 1}">
	          <option value="${todo.todoStatus}">μ§ν μ€</option>
	          <option value="2" name="progress_no" >μλ£</option>
	         </c:when>
	         <c:otherwise>
	          <option value="${todo.todoStatus}">μλ£</option>
	          <option value="1" name="progress_no" >μ§ν μ€</option>
	         </c:otherwise>
        </c:choose>
      </select>
      </td>
      <td></td>
    </tr>
	
    <tr>
      <th scope="row" id="middlebox"><label for='todono'>λ²νΈ</label></th>
      <td><span>${todo.todoNo}</span></td>
      <td></td>
    </tr>
    
    <tr>
      <th scope="row" id="middlebox"><label for='f-content'>λ΄μ©</label></th>
      <td><textarea id='f-content' type='text' name='todoContent' rows='5' wrap="virtual" placeholder="${todo.todoContent}"></textarea></td>
      <td></td>
    </tr>
    
    <tr>
      <th scope="row" id="middlebox"><label for='f-note'>λΉκ³ </label></th>
      <td><input id='f-note' type='text' name='todoRemark' placeholder="${todo.todoRemark}"></td>
      <td></td>
    </tr>
    
    <tr>
      <th scope="row" id="middlebox"><label for='f-writer'>μμ±μ</label></th>
      <td><input id='f-writer' type='text' value="${member.perNickname}" readonly></td>
      <td></td>
    </tr>
  
  </thead>
  </table>
  
  <div class="d-grid gap-2 d-md-flex justify-content-md-end">
      <button class="btn btn-outline-dark">λ³κ²½</button>
  </form>
      <button class="btn btn-outline-dark"><a href="list?perno=${member.perNo}&studyno=${study.studyNo}">λͺ©λ‘</a></button>
  </div>
  

</div>
