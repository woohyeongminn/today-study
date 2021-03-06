<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="https://static.msscdn.net/mfile_outsrc/js/vendor/jquery-1.11.1.min.js?20160201"></script>
 <style>
  form {
  max-width: 500px;
  }
  .btn {
   font-size: 14px;
   line-height: 12px;
  }
  #all-content {
    width: 100%;
    max-width: 500px;
    margin: 0 auto;
    font-size:14px;
  } 
  #top {
  text-align: center;
  }
  a {
  color: black;
  }
  #f-stauts {
  padding-right: 30px;
  }
</style>

<body>
  <div id="all-content">
   <br>
    <div id="top">
      <b style="font-size: 18px">π«νμ νν΄</b><br> 
    </div>
    <br>
    <b>
    1. νν΄ μ μ²­ν μμ΄λλ‘λ μ¦μ μ¬κ°μμ΄ λΆκ°λ₯ ν©λλ€.
    </b>
    <br>
    <b>
    2. νν΄ ν ν΄λΉκ³μ μ λ³΅κ΅¬ν  μ μμ΅λλ€.
    </b>
    <br>
    <b>    
    3. λ±λ‘λ κ²μκΈ λ° μ΄μ©λ΄μ­μ μ­μ λμ§ μμ΅λλ€.
       (μ­μ  ν νν΄ν΄μ£ΌμΈμ.)
    </b>
    <hr>
    <form id="member-form" action='delete' name='perInfo' method='post' enctype="multipart/form-data" onsubmit="return checkValue()">
      <div id="me">
        <label id='f-email' for='f-email' class="col-sm-2 col-form-label">μ΄λ©μΌ</label>
        <input id='i-email' type='text' name='perEmail' value="${perMember.perEmail}"
        style="border:0 solid black" readonly/><br>
      </div>

      <div id="mp">
        <label id='f-password' for='f-password' class="col-sm-2 col-form-label">λΉλ°λ²νΈ</label>
        <input id='i-password' type='password' name='perPassword' placeholder="*λΉλ°λ²νΈ"/><br>
      </div>
      
      <input type="hidden" name="perNo" value="${perMember.perNo}">
      
	    <label for='f-status'>νν΄μ¬μ </label>
	    <select id="f-status" name='reason' >
	      <option value='1' >μμ΄λ λ³κ²½</option>
	      <option value='2' >μ¬μ΄νΈ μ΄μ© λΆλ§</option>
	      <option value='3' >μ¬μ΄νΈ μ΄μ© μν¨</option>
	      <option value='4' >κΈ°ν</option>
	    </select><br> 

      <div id="etc">
        <label id='f-etc' for='f-etc' class="col-sm-2 col-form-label">κΈ°ν</label>
        <input id='i-etc' type='text' name='etc' placeholder="*μ¬μ λ₯Ό μλ ₯ν΄μ£ΌμΈμ"/><br>
      </div>         
      
       <div class="d-grid gap-2 d-md-flex justify-content-md-end">
         <button class="btn btn-outline-dark" type="submit" onclick="deleteMember()">βνν΄νκΈ°</button> 
       </div><hr> 
       <div class="d-grid gap-2 d-md-flex justify-content-md-end">
         <a href="detail">μ·¨μνκΈ°</a>
       </div> 
   </form>
  </div>
</body>

<script>  
  document.querySelector("#all-content").onsubmit = () => {
   if (document.querySelector("#i-password").value == "" ) {
    alert("**λΉλ°λ²νΈλ₯Ό μλ ₯ν΄μ£ΌμΈμ.")
    return false;
  }
};

function deletePop() {
	alert("μ λ§ νν΄νμκ² μ΅λκΉ?")
}

var fStatus = document.querySelector("#f-status");
var etcRow = document.querySelector("#etc");

etcRow.style["display"] = "none";

fStatus.addEventListener("input", function() {
  if (fStatus.value == "4") {
	  etcRow.style["display"] = "";
  } else {
	  etc.style["display"] = "none";
  }
});

function deleteMember(){
	alert("νν΄ λμμ΅λλ€. μ΄μ©ν΄μ£Όμμ κ°μ¬ν©λλ€.")
}

</script>
 



