<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/custom.css">
<title>Home</title>
</head>
<body>
<div class="bodywrap">
<img id="logo" src="images/Envelopes-01.png" alt="logo"  height="90" width="495">
<div class="wrapper">
<p>
${errorMsg}
</p>
	<h2>Budget</h2>
    <br />
	<a href="ShowEnvelopes">
		<img class="button" src="images/Envelopes-02.png" alt="Envelopes"></a>
    <br/>
    <br/>
	<!--<a href="assign.jsp">-->
	<a href="<c:url value="/AssignAmt" />">
		<img class="button" src="images/Envelopes-03.png" alt="Assign"></a>
         <br/>
    <br/>
	<a href="Banks">
		<img class="button" src="images/Envelopes-04.png" alt="Banks"></a>
</div>
</div>
</body>
</html>
