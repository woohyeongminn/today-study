<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="https://static.msscdn.net/mfile_outsrc/js/vendor/jquery-1.11.1.min.js?20160201"></script>

<style>
  form {
  max-width: 500px;
  }
  .btn {
   font-size: 14px;
   line-height: 10px;
  }
  b {
  text-align: center;
  font-size:20px
  }  
 .all-content {
    width: 100%;
    max-width: 500px;
    margin: 0 auto;
    font-size:14px;
  } 
  #top {
  text-align: center;
  }
  a {
  color:black;
  } 
  #x-add-btn {
  background: white;
  } 
</style>
<body>
  <div class="all-content">
   <br>
    <div id="top">
      <b style="font-size: 20px">πκ°μΈ νμ κ°μ</b><br> 
    </div>
   <hr>
    <form id="member-form" action='add' name='perInfo' method='post' enctype="multipart/form-data" onsubmit="return checkValue()">
 
      <div id="mp">
        <label id='f-photo' for='f-photo' class="col-sm-2 col-form-label">μ¬μ§</label>
        <input id='f-photo' type='file' name='photoFile' /><br>
        <p style="color: blue; margin-left: 85px;">*λ―Έμ νμ κΈ°λ³Έμ΄λ―Έμ§λ‘ λ±λ‘λ©λλ€.</p>
      </div>

      <div id="mn">
        <label id='f-name' for='f-name' class="col-sm-2 col-form-label">μ΄λ¦</label>
        <input id='i-name' type='text' name='perName' value="${perName}" placeholder="*νμ"/><br>
      </div>
     
      
      <div id="mE">
	      <label for='f-email' class="col-sm-2 col-form-label">μ΄λ©μΌ</label>
	      <input id='f-email' type='text' name='id' pattern="^[a-zA-Z0-9]+$" placeholder="*νμ"/> @
	      <select name="site">
	        <option>naver.com</option>
	        <option>daum.net</option>
	        <option>gmail.com</option>
	        <option>kakao.com</option>
	      </select>
	      <input type="button" class="btn btn-outline-dark" value="μ€λ³΅νμΈ" onclick="idOverlap()"/><br>
      </div>   
         
      <div id="mNn">
	      <label id='f-nicknam'for='f-nickname' class="col-sm-2 col-form-label">λλ€μ</label>
	      <input id='i-nickname' type='text' name='nick' placeholder="*νμ" />
	      <input type="button" class="btn btn-outline-dark" value="μ€λ³΅νμΈ" onclick="nickOverlap()"/><br>
      </div>

      <div id="mt">
	      <label id='f-tel'for='f-tel' class="col-sm-2 col-form-label">μ νλ²νΈ</label>
	      <input id='i-tel' type='text' name='tel' pattern="[0-9]+" minlength='3' maxlength='3'  style="width:50px;"/> -
	      <input type='text' name='tel' pattern="[0-9]+" minlength='4' maxlength='4'  style="width:50px;"/> -
	      <input type='text' name='tel' pattern="[0-9]+" minlength='4' maxlength='4'  style="width:50px;"/>
        <input type="button" class="btn btn-outline-dark" value="μΈμ¦νκΈ°" /><br> 
      </div>
            
      <div id="mpw">
	      <label id='f-password' for='f-password' class="col-sm-2 col-form-label">λΉλ°λ²νΈ</label>
	      <input id='i-password' type='password' name='password'
	       pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{7,16}$"
	       title="μμ΄(λμλ¬Έμ), μ«μ, νΉμλ¬Έμλ₯Ό ν¬ν¨ν΄ 8μ μ΄μ 16μ μ΄νλ‘ μλ ₯ν΄μ£ΌμΈμ."
	       placeholder="*νμ"/><br>
      </div>
     
     <div id="mpwc">
      <label id='f-passwordcheck' for='f-passwordcheck' class="col-sm-2 col-form-label">μ¬μλ ₯</label>
      <input id='i-passwordcheck' type='password' name='perPassword' placeholder="λΉλ°λ²νΈ νμΈ"/><br>
     </div>
     <br>
     <label >κ°μΈμ λ³΄ μμ§ λ° μ΄μ©μ λν μλ΄</label>
     <input id="perAgree" type="checkbox"/><br>
     <label >μ΄μ© μ½κ΄</label>
     <input id="useAgree" type="checkbox"/><br>
     <label >μ μ²΄ λμ</label>
     <input id="allAgree" type="checkbox"/>
     <hr>
     
     <div class="d-grid gap-2 d-md-flex justify-content-md-center">
       <button id="x-add-btn" class="btn btn-outline-dark btn-primary" type="submit" onclick="joinPopup()" >βκ°μνκΈ°</button> 
       <a id="x-cancle-btn" type="button" class="btn btn-outline-dark" href="${contextPath}/app/index">βμ·¨μνκΈ°</a>
     </div> 
   </form>
  </div>
</body>

<script type="text/javascript">
function checkValue() {
  
  var form = document.perInfo;
  
  if(!form.perName.value){
        alert("μ΄λ¦μ μλ ₯νμΈμ.");
        return false;
   }
  
  if(!form.perNickname.value){
        alert("λλ€μμ μλ ₯νμΈμ.");
        return false;
    }
  
  if(!form.tel1.value && !form.tel2.value && !form.tel3.value){
        alert("μ νλ²νΈλ₯Ό μλ ₯νμΈμ.");
        return false;
    }

    if(!form.perEmail.value){
          alert("μ΄λ©μΌμ μλ ₯νμΈμ.");
          return false;
     }
    
/*     if(!form.email.value != "checkEmail"){
           alert("μ΄λ©μΌ μ€λ³΅μ²΄ν¬λ₯Ό ν΄μ£ΌμΈμ.");
           return false;
     }
      */
    if(!form.password.value){
          alert("λΉλ°λ²νΈλ₯Ό μλ ₯νμΈμ.");
          return false;
     }
    
    if(form.perPassword.value ==""){
          alert("λΉλ°λ²νΈ νμΈλμ μλ ₯ν΄μ£ΌμΈμ.");
          form.passwordcheck.focus();
          return false;
      }
    
    if(form.perPassword.value != form.password.value){
          alert("λΉλ°λ²νΈλ₯Ό λμΌνκ² μλ ₯νμΈμ.");
          form.passwordcheck.focus();
          return false;
     }
}
</script>

<script>
document.querySelector("#member-form").onsubmit = () => {
  if (document.querySelector("#i-name").value == "" ||
      document.querySelector("#i-email").value == "" ||
      document.querySelector("#i-nickname").value == "" ||
      document.querySelector("#i-password").value == "") {
    window.alert("νμ μλ ₯ ν­λͺ©μ΄ λΉμ΄ μμ΅λλ€.")
    //Swal.fire("νμ μλ ₯ ν­λͺ©μ΄ λΉμ΄ μμ΅λλ€.")
    return false;
  }
};
</script>

<script>
var addBtn = document.querySelector("#x-add-btn");
function idOverlap(){
  
  var form = document.perInfo;
  
      console.log("idOverlap νΈμΆ")
      console.log("μμ΄λ μλ ₯ κ° : "+form.id.value)
    $.ajax({
      type :"post",/* μ μ‘ λ°©μ */
      url :"idOverlap", /* μ»¨νΈλ‘€λ¬ μ¬μ©ν  λ. λ΄κ° λ³΄λΌ λ°μ΄ν°μ μ£Όμ. */
      data : {"id" : form.id.value+"@"+form.site.value},
      dataType : "text",  /* text, xml, html, script, json, jsonp κ°λ₯ */
            
      success : function(data){ 
        
        console.log(data);
        
        if(data=="1"){
          alert("μ¬μ© κ°λ₯ν μ΄λ©μΌ μλλ€.");
          addBtn.removeAttribute("disabled");
        }else{  //ajaxκ° μ λλ‘ μλμ λ .
          alert("μ΄λ―Έ μ¬μ©μ€μΈ μ΄λ©μΌ μλλ€.");
          addBtn.setAttribute("disabled", "disabled");
        }
      },
      error : function(){
        alert("μμ΄λ μ€λ³΅ νμΈ ajax μ€ν μ€ν¨");
      }
    });
    
  }

function nickOverlap(){
  
  var form = document.perInfo;
      console.log("nickOverlap νΈμΆ")
      console.log("λλ€μ μλ ₯ κ° : "+form.nick.value)
    $.ajax({
      type :"post",/* μ μ‘ λ°©μ */
      url :"nickOverlap", /* μ»¨νΈλ‘€λ¬ μ¬μ©ν  λ. λ΄κ° λ³΄λΌ λ°μ΄ν°μ μ£Όμ. */
      data : {"nick" : form.nick.value},
      /* JSONνμ μμ JSON νμμΌλ‘ ννν λ°μ΄ν°. 
            "νλΌλ―Έν° μ΄λ¦" : νΌνκ·Έμ μ μ NAME κ°.IDμλ ₯μ°½μ NAMEκ°.value μ¬λ¬ κ°λ κ°λ₯
      data :{ "id" : joinForm.id.value, 
      "id1" : joinForm.password.value}, μ΄λ κ²λ μ¬μ© κ°λ₯.         
      */
      dataType : "text",  /* text, xml, html, script, json, jsonp κ°λ₯ */
            //μ μμ μΈ ν΅μ μ νλ€λ©΄ functionμ λ°±μλ λ¨μμ λ°μ΄ν°λ₯Ό μ²λ¦¬.
            
      success : function(data){ 
        
        console.log(data);
        
        if(data=="1"){
          alert("μ¬μ© κ°λ₯ν λλ€μ μλλ€.");
          addBtn.removeAttribute("disabled");
        }else{  //ajaxκ° μ λλ‘ μλμ λ .
          alert("μ΄λ―Έ μ¬μ©μ€μΈ λλ€μ μλλ€.");
          addBtn.setAttribute("disabled", "disabled");
        }
      },
      error : function(){
        alert("λλ€μ μ€λ³΅ νμΈ ajax μ€ν μ€λ₯");
      }
    });
  }
 // var form = document.perInfo;
 // function joinPopup() {
 //	  alert("'" + form.nick.value + "'λ νμν©λλ€.β")
 // }
 
 function checkCapsLock(event)  {
  if (event.getModifierState("CapsLock")) {
    document.getElementById("message").innerText 
      = "Caps Lockμ΄ μΌμ Έ μμ΅λλ€."
  }else {
    document.getElementById("message").innerText 
      = ""
  }
}
</script>
 



