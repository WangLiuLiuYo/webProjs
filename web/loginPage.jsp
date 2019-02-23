<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/2/20
  Time: 13:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录界面</title>
</head>
<body>
<form method="post" action="login.do">
    <p>
        手机号码：<input type="text" maxlength="12" minlength="4" name="phone">
    </p>
    <p>
        登录密码：<input type="text" maxlength="12" name="password">
    </p>
    <p>
        您的身份是：
        <input type="radio" name="type" value="manager" checked>工作人员
        <input type="radio" name="type" value="user">用户
    </p>
    <input type="submit" value="登录">
    <input type="reset" value="重置">
</form>
</body>
</html>
