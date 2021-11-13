<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>

* {
font-size: 14px;
}

body {
  height: auto;
}
a {
text-decoration: none;
}

legend {
  text-align: center;
}

.all-content {
  width: 100%;
  margin: 0 auto;
  height: 800px;
}

.profile {
  max-width: 400px;
  margin: 100px auto 0;
  background-color: white;
  border-radius: 15px;
  border: 2px solid rgb(110, 110, 110);
  text-align: center;
  padding-bottom: 30px;
}

.profile > label {
  margin-right: 5px;
  text-align: center;
  display: inline;
  width: 60px;
}

.profile > label, span {
  display: inline-block;
  padding: 5px 0;
  width: 100px;
}

.profile input {
  border:0;
}

.profile input:focus {
  outline: none;
}


.profile .profile-header {
  padding: 0;
  height: 60px;
  display: flex;
  align-items: center; 
}

.profile .profile-header>a {
  display:inline-block;
  text-decoration:none;
  width: 100px;
  height: 100px;
  border-radius: 1000px;
  position: absolute;
  left: 50%;
  margin-top: 30px;
  transform: translate(-50%, -50%);
  border: 2px solid rgb(110, 110, 110);
  background-color: white;
  vertical-align: middle;
}

 .profile .profile-header .profile-img {
    margin-top: 8px;
    margin-left: 3px;
}


 .btn_wrap {
  max-width: 420px;
  margin: 20px auto 0;
  text-align: center;
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-bottom: 100px;
 }
 
 .btn_wrap .btn {
  margin: 0 7px;
  padding: 5px 10px;
  height: auto;
  line-height: inherit;
 }
 
 button:hover {
  color: white;
}
 
</style>
</head>

<body>
  <br><br><br>
    <div class="all-content"> 
      <div class="profile">
        <div class="profile-header">
           <a href="../upload/ceoMember/${ceoMember.ceoPhoto}" >
               <img id="f-photo-image" src="../upload/ceoMember/${ceoMember.ceoPhoto}_80x80.jpg">
           </a>
        </div>
         <label for='f-name' class='form-label'>이름</label>
         <input id='f-name' type='text' name='name' readonly value='${ceoMember.ceoName}'><br>
         
         <label class="profile-label" for='f-nickname' class='form-label'>닉네임</label>
         <input id='f-nickname' type='text' name='nickname' readonly value='${ceoMember.ceoNickname}'><br>
         
         <label for='f-tel' class='form-label'>전화번호</label>
         <input id='f-tel' type='tel' name='tel' readonly value='${ceoMember.ceoTel}'><br>
         
         <label for='f-bossName' class='form-label'>대표자명</label>
         <input id='f-bossName' type='bossName' name='bossName' readonly value='${ceoMember.ceoBossName}'><br>
         
         <label for='f-licenseNo' class='form-label'>사업자 번호</label>
         <input id='f-licenseNo' type='licenseNo' name='licenseNo' readonly value='${ceoMember.ceoLicenseNo}'><br>
         
         <label for='f-email' class='form-label'>이메일</label>
         <input id='f-email' type='email' name='email' readonly value='${ceoMember.ceoEmail}'><br>
         
         <label for='f-registeredDate' class='form-label'>가입일</label>
         <input id='f-registeredDate' type='registeredDate'
          name='registeredDate' readonly value='${ceoMember.ceoRegisteredDate}'><br>
        </div>
        
        <div class="btn_wrap">
          <a href='list' class = "btn btn-outline-dark">목록</a>
          <a href='delete?no=${ceoMember.ceoNo}' class = "btn btn-outline-dark">탈퇴시키기</a>
        </div>
      </div>
</body>
</html>