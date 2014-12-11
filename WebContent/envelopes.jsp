<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/custom.css">
<title>Envelopes</title>
</head>

<body>
<div class="bodywrap">
<a href="Home"><img id="logo" src="images/Envelopes-01.png" alt="logo"  height="90" width="495"></a>
<h2>Manage Envelopes</h2>
<div class="wrapper">
<div class="col1">
	
<form action="DeleteEnvelope" method="POST">

 			Current Envelopes
            <br />


              <div class="scroll">
              <c:forEach var="enve" items="${enves}">
               <input type="checkbox" name="category_list" value="${enve.category_id}">${enve.category_name}:<br />
              </c:forEach>

<br>
<button type="submit" name="action" value="DeleteEnvelope">Delete</button>
</form>
</div> 
</div>
<div class="col2">
<form action="CreateEnvelope" method="POST">
     	New Envelope:
        <br /> 
        <input type="text" name="enve" />
        <br />
     	<br />
     <button type="submit" name="action" value="CreateName">Create</button>
	</form>
</div>
</div>
<br />
<a href="Home">Back</a>
</div>
</body>
</html>