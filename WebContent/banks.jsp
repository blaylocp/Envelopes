<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search for bank</title>
<link rel="stylesheet" type="text/css" href="css/custom.css">
</head>

<body>
<div class="bodywrap">
<a href="Home"><img id="logo" src="images/Envelopes-01.png" alt="logo"  height="90" width="495"></a>
<h2>Banks</h2>
<div class="wrapper">

<p>${bankAddResponse}</p>
 
<h2>Bank Search</h2>

<form action="FindBanks" method="POST">
	<label for="bank">Banks: </label>
	<input type="text" name="bank" placeholder="Bank to search for..." />
	<button type="submit">Search</button>
</form>
<br>
<h2>Results</h2>
<div class="scroll">
<c:forEach var="bank" items="${banks}" varStatus="loop">
   <a href="#login${bank.siteID}">${bank.displayName}</a>
   <br />
   <div id="login${bank.siteID}" class="modalLogin">
	<div>
		<a href="#out" title="Close" class="out">X</a>
		<h2>Login</h2>
		<p>Login to ${bank.displayName}</p>
		<form action="ValidateBank" method="POST">
			Username <br /> <input type="text" name="username" /><br />
			Password <br /> <input type="password" name="password" /><br />
			<input type="hidden" name="index" value="${loop.index}" />
			<button type="submit" name="action" value="validate">Login</button>
		</form>
	</div>
</div>
  
</c:forEach>
</div>
</div>
</div>

</body>
</html>
