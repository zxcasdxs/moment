<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2021-09-10
  Time: 오전 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <h1>doAll</h1>

    <sec:authorize access="isAnonymous()">
        <a href="/customLogin">Login plz..........</a>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
          <a href="/logout">Logout</a>
    </sec:authorize>


</body>
</html>
