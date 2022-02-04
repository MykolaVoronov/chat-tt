<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chat</title>
</head>
<body>
<table>
    <c:forEach var="message" items="${messages}">
        <tr>
            <td>
                <c:out value="${message.sendTime} "/>
            </td>
            <td>
                <c:out value="${message.sender.login}: "/>
            </td>
            <td>
                <c:out value="${message.text}"/>
            </td>
        </tr>
    </c:forEach>
</table>
<form method="post" id="message_field" action="${pageContext.request.contextPath}/chat"></form>
<table>
    <tr>
        <td>
            <label><input type="text" name="text" required form="message_field"></label> <br>
        </td>
    </tr>
    <tr>
        <td>
            <input type="submit" form="message_field">
        </td>
    </tr>
</table>
    <a href="${pageContext.request.contextPath}/chat"><button type="reset">Refresh</button></a>
</body>
</html>
