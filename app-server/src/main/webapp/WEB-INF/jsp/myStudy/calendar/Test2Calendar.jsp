<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 <meta name="viewport" content="width=device-width, initial-scale=1.0">

 <link href="${contextPath}/css/calendar/calstyle.css" rel="stylesheet" type="text/css">

<div class="all-content"> 
  <div class="c-top">
    </div>
    <div class="calendarwrap">
      <div class="calendar">
          <div class="header">
          <div><p style="font-size:18px;">π${myStudy.studyTitle}</p></div>
              <div class="year-month" style="font-weight: 700;"></div>
                <div class="nav">
                  <button class="nav-btn go-prev" onclick="prevMonth()">β</button>
                  <button class="nav-btn go-today" onclick="goToday()">now</button>
                  <button class="nav-btn go-next" onclick="nextMonth()">βΆ</button>
                </div>
          </div><hr>
          
          <!-- Button trigger modal -->
          <div class="d-grid gap-2 d-md-flex justify-content-md-end">
            <a  type="button" data-bs-toggle="modal"  data-bs-target="#addCalendarModal">β</a> |                
            <a  type="button" data-bs-toggle="modal"  data-bs-target="#searchCalendarModal">π</a> |
            <a href="../detail?studyNo=${myStudy.studyNo}">β</a>                
          </div> 
          <!-- Modal -->
          <form action="add" method="post">
            <div class="modal fade" id="addCalendarModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                 
                  <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">μΌμ  λ±λ‘</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  
                  <div class="modal-body">
                    <div class="addcalendar">
                      <label>π</label>
                      <input type="date" name="calendarDate"/>
                      <label for='f-status'>β­</label>
                      <select id="f-status" name='importanceNo' >
                        <option value='1' name='importanceNo'>β­β­β­β­β­</option>
                        <option value='2' name='importanceNo'>β­β­β­β­β</option>
                        <option value='2' name='importanceNo'>β­β­β­ββ</option>
                        <option value='2' name='importanceNo'>β­β­βββ</option>
                        <option value='2' name='importanceNo'>β­ββββ</option>
                      </select><br><hr>                 
                      
                      <label>π</label>
                      <input type="text" name="calendarContent" placeholder="*λ΄μ©μ μλ ₯νμΈμ." /><br>
                      <label >π‘</label>
                      <input id="alarm" type="checkbox"/><br>
                      <input type="hidden" name=studyNo value="${myStudy.studyNo}"/>
                    </div>
                  </div>
                  
                  <div class="modal-footer">
                    <button type="submit" class="btn btn-outline-dark" onclick="addCalendar(this)" data-bs-dismiss="modal">λ±λ‘</button>
                    <button type="button" class="btn btn-outline-dark" data-bs-dismiss="modal">μ·¨μ</button>
                  </div>
                  
                </div>
              </div>
            </div> <!-- d-grid gap-2 d-md-flex justify-content-md-end  -->    
          </form>
          
          <!-- Modal -->
          <div class="modal fade" id="searchCalendarModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="staticBackdropLabel">μΌμ  κ²μ</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                
                  <div id="searching">
                    <label>π</label>
                    <input type="date" name='date'></input> |
                    <label>π</label>
                    <input type="text" name="search" placeholder="*κ²μμ΄λ₯Ό μλ ₯νμΈμ." /><br>
                  </div>           
               
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-outline-dark" onclick="searchCalendar(this)" data-bs-dismiss="modal">κ²μ</button>
                  <button type="button" class="btn btn-outline-dark" data-bs-dismiss="modal">μ·¨μ</button>
                </div>
              </div>
            </div>
          </div> <!-- d-grid gap-2 d-md-flex justify-content-md-end  -->    
          <!-- Button trigger modal -->                     

          <div class="main">
              <div class="days">
                  <div class="day" style="font-size:18px;">Sun</div>
                  <div class="day" style="font-size:18px;">Mon</div>
                  <div class="day" style="font-size:18px;">Thu</div>
                  <div class="day" style="font-size:18px;">Wed</div>
                  <div class="day" style="font-size:18px;">Thr</div>
                  <div class="day" style="font-size:18px;">Fri</div>
                  <div class="day" style="font-size:18px;">Sat</div>
              </div>
              <div class="dates"></div>
          </div>
       
      </div>
   </div>  
</div> 
<script type="text/javascript" src="${contextPath}/js/calendar.js"></script>

<script>

function addCalendar(obj) { 
    alert("μΌμ μ΄ λ±λ‘ λμμ΅λλ€.")
    }
function searchCalendar(obj) { 
    alert("κ²μ κ²°κ³Ό!!")
    } 
</script>
