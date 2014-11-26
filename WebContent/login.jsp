<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/custom.css">
<title>Login</title>
</head>
<body>
<div class="bodywrap">
<img id="logo" src="images/Envelopes-01.png" alt="logo"  height="90" width="495">
<div class="wrapper">
<p>
${errorMsg}
</p>
<h2>Login</h2>
<form action="ValidateUser" method="POST">
	Username <br /> <input type="text" name="username" /><br />
	Password <br /> <input type="password" name="password" /><br />
	<button type="submit" name="action" value="validate">Login</button>
</form>
<h2>Sign up</h2>
<form action="ValidateUser" method="POST">
	First Name <br /><input type="text" name="firstName" /><br />
	Last Name <br /><input type="text" name="lastName" /><br />
	Email Address <br /><input type="text" name="email" /><br />
	Username <br /><input type="text" name="username" /><br />
	Password <br /><input type="password" name="password" /><br />
	<button type="submit" name="action" value="addUser">Register</button>
</form>
</div>
</div>
</body>
</html>
