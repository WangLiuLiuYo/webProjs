<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/2/22
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注销用户（管理员权限）</title>
</head>
<body>
<form method="get" action="managerservice.do">
    <h2>
        <p>填入想要注销的用户手机号：<input type="text" maxlength="11" minlength="11" name="phone"></p>
        <p><input type="submit" value="确定"></p>
    </h2>
</form>
</body>
</html>
