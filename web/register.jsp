<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/2/20
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册页面</title>
</head>
<body>
<form method="post" action="register.do">
    <p>
        用户名（昵称）：<input type="text" name="username" maxlength="12" minlength="6" placeholder="6-12个数字或字母">
    </p>
    <p>
        密码：<input type="password" minlength="6" maxlength="10" name="password" placeholder="6-10位数字或字母">
    </p>
    <p>
        Email:<input type="text" name="email">
    </p>
    <p>
        手机号：<input type="text" name="phone" maxlength="11" minlength="11">
    </p>
    <input type="submit" value="注册">
    <input type="reset" value="重置">
</form>
</body>
</html>
