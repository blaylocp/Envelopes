<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/custom.css">
<title>Assign</title>
</head>
<body>
<div class="bodywrap">
<a href="Home"><img id="logo" src="images/Envelopes-01.png" alt="logo"  height="90" width="495"></a>
<h2>Assign</h2>
<div class="wrapper">

<h3>Total Earnings</h3>
            <h4><strong>${lastMonthTotal}</strong></h4>
  
<h2>Budget Envelopes</h2>
<div class="scroll">

     			<c:forEach var="enve" items="${enves}">
     				<br>
     				<form action="AssignAmt" method="POST">
               		${enve.category_name} : ${enve.envelope_amount}
               		<input type="text" name="envelope_update" placeholder="Input Budget Amount" value="${enve.envelope_amount}">
               		<input type="hidden" name="envelope_id" value="${enve.category_id}" >
                	<button type="submit" name="action" value="CreateAmount + ${enve.category_id}">Assign</button>
					</form>
              </c:forEach>

    </div>
</div>
</div>
</body>
</html>