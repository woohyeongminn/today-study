<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>


<style>

 .all-content {
    width: 100%;
    max-width: 600px;
    margin: 0 auto;
    text-align: center;
    margin-top: 50px;
  }
  
  form {
  max-width: 600px;
  font-size: 14px;
  text-align: left;
  }
  
  .col-form-label {
   width: 120px;
  margin-left: 105px;
  }
  
  .btn {
   font-size: 10px;
    line-height: 10px;
    padding: 6px 12px;
    margin-bottom: 2px;
  }
  
  b {
  text-align: center;
  }  

  
  input:invalid {
    color: gray;
  } 
  
  .s-btn {
    padding: 10px 20px;
    font-size: 13px;
    margin-top: 9px;
  }
   
</style>

	<div class="all-content">
	<br>
  <b style="font-size:14px">π’κΈ°μ νμκ°μ </b>
<body>
  <hr>
  
  <form id="member-form" action='add' name='ceoInfo' method='post' enctype="multipart/form-data" onsubmit="return checkValue()">
	  <label for='f-name' class="col-sm-2 col-form-label">μ΄λ¦</label>
	  <input id='f-name' type='text' name='ceoName' placeholder="*νμ"/><br>
	  
	  <label for='f-nickname' class="col-sm-2 col-form-label">λλ€μ</label>
	  <input id='f-nickname' type='text' name='ceoNickname' placeholder="*νμ" />
	  <input type="button" class="btn btn-outline-dark" value="μ€λ³΅νμΈ" onclick="nickOverlap()"/><br>

	  <label for='f-photo' class="col-sm-2 col-form-label">μ¬μ§</label>
	  <input id='f-photo' type='file' name='photoFile' /><br>
	  <p style="text-align: center; font-size: 10px; margin-left: 85px;">μ¬μ§ λ―Έμ νμ κΈ°λ³Έ νλ‘ν μ¬μ§μ΄ λ±λ‘λ©λλ€.</p>
	  
	  <label for='f-tel' class="col-sm-2 col-form-label">μ νλ²νΈ</label>
	  <input id='f-tel' type='text' name='tel1' pattern="[0-9]+" minlength='3' maxlength='3'  style="width:50px;"/> -
	  <input id='f-tel' type='text' name='tel2' pattern="[0-9]+" minlength='4' maxlength='4'  style="width:50px;"/> -
	  <input id='f-tel' type='text' name='tel3' pattern="[0-9]+" minlength='4' maxlength='4'  style="width:50px;"/> <br>
	  
	  <label for='f-bossname' class="col-sm-2 col-form-label">λνμλͺ</label>
    <input id='f-bossname' type='text' name='ceoBossName' placeholder="*νμ"/><br>
    
    <label for='f-licenseno' class="col-sm-2 col-form-label">μ¬μμ λ±λ‘λ²νΈ</label>
    <input id='f-licenseno' type='text' name='ceoLicenseNo'
     pattern="[0-9]{10}" title='10μλ¦¬ μ«μλ₯Ό μλ ₯ν΄μ£ΌμΈμ.' maxlength='10' placeholder="*νμ"/>
    <input type="button" class="btn btn-outline-dark" value="μ€λ³΅νμΈ" onclick="licenseOverlap()"/><br>
    
	  <label for='f-email' class="col-sm-2 col-form-label">μ΄λ©μΌ</label>
	  <input id='f-email' type='text' name='id' pattern="^[a-zA-Z0-9]+$" placeholder="*νμ"/> @ 
	  <select name="site" style="height: 24px;">
		  <option>naver.com</option>
		  <option>daum.net</option>
		  <option>gmail.com</option>
		  <option>kakao.com</option>
	  </select>
	  <input type="button" class="btn btn-outline-dark" value="μ€λ³΅νμΈ" onclick="idOverlap()"/><br>
	  <input type="hidden" name="idDuplication" value="idUncheck"/>
	  
	  <label for='f-password' class="col-sm-2 col-form-label">λΉλ°λ²νΈ</label>
	  <input id='f-password' type='password' name='password'
	   pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{7,16}$"
	   title="μμ΄(λμλ¬Έμ), μ«μ, νΉμλ¬Έμλ₯Ό ν¬ν¨ν΄ 8μ μ΄μ 16μ μ΄νλ‘ μλ ₯ν΄μ£ΌμΈμ."
	   placeholder="*νμ"/><br>
	 
	  <label for='f-passwordcheck' class="col-sm-2 col-form-label">λΉλ°λ²νΈ νμΈ</label>
	  <input id='f-passwordcheck' type='password' name='ceoPassword' placeholder="λΉλ°λ²νΈ νμΈ"/><br>
    <hr>
     <div class="d-grid gap-2 d-md-flex justify-content-md-center">
       <input type="submit" class="btn btn-outline-dark s-btn" value="βκ°μνκΈ°"/> 
       <a type="button" class="btn btn-outline-dark s-btn" href="${contextPath}/app/index">βμ·¨μνκΈ°</a>
     </div>
	 </form>
	 </div>
</body>

<script>
function idOverlap(){
	
	var form = document.ceoInfo;
	
      console.log("idOverlap νΈμΆ")
      console.log("μμ΄λ μλ ₯ κ° : "+form.id.value)
    $.ajax({
      type :"post",/* μ μ‘ λ°©μ */
      url :"idOverlap", /* μ»¨νΈλ‘€λ¬ μ¬μ©ν  λ. λ΄κ° λ³΄λΌ λ°μ΄ν°μ μ£Όμ. */
      data : {"id" : form.id.value+"@"+form.site.value},
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
          alert("μ΄ μμ΄λλ μ¬μ© κ°λ₯ν©λλ€.");
          form.idDuplication.value = "idCheck";
        }else{  //ajaxκ° μ λλ‘ μλμ λ .
          alert("μ΄ μμ΄λλ μ¬μ©  λΆκ°λ₯ν©λλ€.");
        }
      },
      error : function(){
        alert("μμ΄λ μ€λ³΅ νμΈ ajax μ€ν μ€ν¨");
      }
    });
    
  };

function nickOverlap(){
 
 var form = document.ceoInfo;
 
     console.log("nickOverlap νΈμΆ")
     console.log("λλ€μ μλ ₯ κ° : "+form.ceoNickname.value)
   $.ajax({
     type :"post",/* μ μ‘ λ°©μ */
     url :"nickOverlap", /* μ»¨νΈλ‘€λ¬ μ¬μ©ν  λ. λ΄κ° λ³΄λΌ λ°μ΄ν°μ μ£Όμ. */
     data : {"nick" : form.ceoNickname.value},
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
       }else{  //ajaxκ° μ λλ‘ μλμ λ .
         alert("μ΄λ―Έ μ¬μ©μ€μΈ λλ€μ μλλ€.");
       }
     },
     error : function(){
       alert("λλ€μ μ€λ³΅ νμΈ ajax μ€ν μ€λ₯");
     }
   });
   
 };
  
 function licenseOverlap(){
	 
	 var form = document.ceoInfo;
	 
	     console.log("licenseOverlap νΈμΆ")
	     console.log("μ¬μμλ²νΈ μλ ₯ κ° : "+form.ceoLicenseNo.value)
	   $.ajax({
	     type :"post",/* μ μ‘ λ°©μ */
	     url :"licenseOverlap", /* μ»¨νΈλ‘€λ¬ μ¬μ©ν  λ. λ΄κ° λ³΄λΌ λ°μ΄ν°μ μ£Όμ. */
	     data : {"license" : form.ceoLicenseNo.value},
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
	         alert("μ¬μ© κ°λ₯ν μ¬μμλ²νΈ μλλ€.");
	       }else{  //ajaxκ° μ λλ‘ μλμ λ .
	         alert("μ΄λ―Έ μ¬μ©μ€μΈ μ¬μμλ²νΈ μλλ€.");
	       }
	     },
	     error : function(){
	       alert("μ¬μμλ²νΈ μ€λ³΅ νμΈ ajax μ€ν μ€λ₯");
	     }
	   });
	   
	 };
  
</script>



<script type="text/javascript">
function checkValue() {
  
  var form = document.ceoInfo;
  
  if(!form.ceoName.value){
        alert("μ΄λ¦μ μλ ₯νμΈμ.");
        return false;
   }
  
  if(!form.ceoNickname.value){
        alert("λλ€μμ μλ ₯νμΈμ.");
        return false;
    }
  
    if(!form.tel1.value && !form.tel2.value && !form.tel3.value){
        alert("μ νλ²νΈλ₯Ό μλ ₯νμΈμ.");
        return false;
    }
  
  if(!form.ceoBossName.value){
        alert("λνμλͺμ μλ ₯νμΈμ.");
        return false;
    }
  
  if(!form.ceoLicenseNo.value){
        alert("μ¬μμλ²νΈλ₯Ό μλ ₯νμΈμ.");
        return false;
    }

    if(!form.id.value){
          alert("μ΄λ©μΌμ μλ ₯νμΈμ.");
          return false;
     }
    
    /*if(form.id.value != "idCheck"){
           alert("μ΄λ©μΌ μ€λ³΅μ²΄ν¬λ₯Ό ν΄μ£ΌμΈμ.");
           return false;
     }*/
     
    if(!form.password.value){
          alert("λΉλ°λ²νΈλ₯Ό μλ ₯νμΈμ.");
          return false;
     }
    
    if(form.ceoPassword.value ==""){
          alert("λΉλ°λ²νΈ νμΈλμ μλ ₯ν΄μ£ΌμΈμ.");
          form.passwordcheck.focus();
          return false;
      }
    
    if(form.ceoPassword.value != form.password.value){
          alert("λΉλ°λ²νΈλ₯Ό λμΌνκ² μλ ₯νμΈμ.");
          form.passwordcheck.focus();
          return false;
     }
}
</script>