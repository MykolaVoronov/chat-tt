<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Login</title>
</head>
<h1 class="table_dark">Login page</h1>
<body>
<form method="post" id="login" action="${pageContext.request.contextPath}/login"></form>
<table class="table_dark">
    <tr>
        <td>
            <label>Login: <input type="text" name="login" required form="login"></label> <br>
        </td>
    </tr>
    <tr>
        <td>
            <input type="submit" form="login">
        </td>
    </tr>
</table>
</body>
</html>
