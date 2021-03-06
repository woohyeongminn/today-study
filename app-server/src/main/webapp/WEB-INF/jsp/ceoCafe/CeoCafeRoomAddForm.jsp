<%@page import="com.ogong.pms.web.cafe.CafeHandlerHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
<style>
* {
  font-size: 14px;
}


 a {
   text-decoration:none;
}

label {
  display: inline-block;
  margin-right: 5px;
  width: 130px;
}

.template-content {
    height: 900px;
}

 .all-content {
  width: 1000px;
  margin: 0 auto;
  height: 800px;
}

ul {
  list-style:none;
}

.addForm {
  display: flex;
  flex-direction: row;
    justify-content: center;
}


.cafe-top {
  width: 25%;
  margin: 8px 8px 0 0;
  padding-top: 30px;
}
 
.cafe-bottom {
    width: 40%;
    text-align: left;
    padding: 5px 0;
    margin-top: 30px;
    margin-left: 15px;
}

.cafe-bottom label {
   width: 75px;
   font-weight: bold;
   padding: 5px 0;
}

.cafe-bottom span {
  width: 80%;
  padding: 5px 0;
}

.label-wrap {
  width: 100%;
  height: fit-content;
  display: flex;
    flex-direction: row;
}

.label-wrap > label {
   width: 135px;
   font-weight: bold;
   padding: 5px 0;
}

.label-wrap > span {
  width:80%;
  height:80px;
  padding: 5px 0;
  overflow: scroll;
}

.cafe-bottom-review {
  width: 100%;
  padding: 0 10px 30px 0px;
  text-align: left;
}
 
.line {
   width: 100%;
   height: 4px;
   background: gray;
}

.review-wrap {
  width: 830px;
  height: 180px;
  overflow: scroll;
}

#c-review-content {
  margin: 0;
}
  
#c-review {
  background-color: whitesmoke;
  height: fit-content;
  margin-bottom: 10px;
  padding: 10px;
}
  
button {
  border: 0;
  background: transparent;
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

#input-file-button {
  display: inline-table;
  width: 120px;
  padding: 2px;
  background-color: gray;
  border-radius: 5px;
  color: white;
  font-size: smaller;
  cursor: pointer;
  text-align: center;
  margin-top: 5px;
}

#c-image {
    width: 238px;
    height: 190px;
    background-color: darkgray;
    display: table-cell;
    vertical-align: middle;
    text-align: center;
}

#c-grade {
  margin-left: 41px;
  vertical-align: 4px;
}

#c-review {
  width: 427px;
  background-color: whitesmoke;
  height: 80px;
  margin-bottom: 10px;
}

#button {
    width: 382px;
    margin-left: 440px;
}

#button:hover {
color: white;
}

.btn {
    width: 100%;
    padding: 6px;
    margin-right: 10px;
    border-radius: 8px;
    color: black;
    font-size: 15px;
    cursor: pointer;
    text-align: center;
}
.btn:horver {
  color: white;
}

</style>
</head>

<div class="all-content">
  
    <form action="add" method="post" enctype="multipart/form-data" style="text-align: center;">
    <div class="addForm">
	     <div class="cafe-top">
	         <span id='c-image'>??????</span>
	         <br>
	         <input id="input-roomFile" type="file" name='photoFile'/>
	     </div>
    
	     <div class="cafe-bottom">
	        <!-- ??? ?????? ??? ?????? -->      
	        <input type ='hidden' name='cafeno' value='${cafeNo}'>
	        
		      <label for='f-roomInfo'>??? ??????</label>
		      <input id='f-roomName' type='text' name='roomName' class="form-control" style="width: 300px; display: inline-block;"><br><br>
		      
	        <label for='f-roomInfo'>?????????</label>
	        <input id='f-roomInfo' type='text' name='roomInfo' class="form-control" style="width: 300px; display: inline-block; height: 100px"><br><br>
		      
		      <label for='f-people'>????????????</label>
		      <input id='f-roomPeople' type="number" pattern="\d*" name='people'
		      min="1" max="50" class="form-control" placeholder="?????? ?????? 1??? ~ ?????? 50????????? ????????????"
		      style="width: 300px; display: inline-block;"><br><br>
		      
		      <label for='f-roomPrice'>???????????????</label>
		      <input id='f-roomPrice' type='number' pattern="\d*"
		      min="1000" max="500000" name='roomPrice' class="form-control" 
		      placeholder="?????? ?????? 1000??? ~ ?????? 50???????????? ????????????"
		      style="width: 300px; display: inline-block;"><br><br>
		      
	    </div>
    </div>
  
	  <!-- ?????? -->
	  <div id='button_wrap'>
	    <!-- 
	      if (people <= 0) {
	          System.out.println(" >> ????????? 0?????? ?????? ????????? ??? ????????????. ?????? ??????????????????.");
	        } else if (people > 50) {
	          System.out.println(" >> ?????? 50????????? ????????? ??? ????????????. ?????? ??????????????????.");
	        } else {
	        
	        
	        if (timePrice <= 0) {
	          System.out.println(" >> ????????? 0?????? ?????? ????????? ??? ????????????. ?????? ??????????????????.");
	        } else if (timePrice > 500000) {
	          System.out.println(" >> 50?????? ?????? ????????? ??? ????????????. ?????? ??????????????????.");
	        } 
	         -->
		    <div id='button'>
		      <button id='b-but' type="submit" value="??????" class="btn btn-outline-dark">??????</button>
		    </div>
	    </div>
	    
    </form>
 </div>
 
