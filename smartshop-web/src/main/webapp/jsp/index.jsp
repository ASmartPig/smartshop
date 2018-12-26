<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/15 0015
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Hello</h1>
<a href="index.html">获取表格数据</a><br>
<table>
    <tread>
        <tr style="color: red">
            <td>用户名</td>
            <td>密码</td>
        </tr>
    </tread>
    <tbody>
    <c:forEach items="${userList}" var="user">
        <tr class="table-body">
            <td>${user.userName}</td>
            <td>${user.password}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
