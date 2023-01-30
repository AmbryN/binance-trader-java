<!DOCTYPE html>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h1>Hello, world!</h1>
<h1><c:if test="${ !empty user }">
        <c:out value="${ user }"/>
    </c:if>
</h1>
<br/>
</body>
</html>