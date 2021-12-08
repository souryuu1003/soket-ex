<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<form method="post" action="play.do">
	<input type="text" name="memberId">
	<input type="text" name="memberName">
	<input type="text" name="mmemberPhone">
	<input type="submit" value="play!">
</form>

</body>
</html>
