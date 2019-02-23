<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/2/22
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改用户信息</title>
</head>
<body>
<form method="post" action="managerservice.do">
    <input type="hidden" name="symbol" value="update">
    <h2>
        (由于手机号和卡号绑定，所以无法修改这两项信息。）<br>
        用户手机号（必填）：<input type="text" minlength="11" maxlength="11" name="phone" required><br>
        新用户名：<input type="text" name="username" minlength="6" maxlength="12"><br>
        新邮箱：<input type="text" name="email"><br>
        新密码：<input type="password" name="password" minlength="6" maxlength="10"><br>
        <input type="submit" value="提交">
    </h2>
</form>
</body>
</html>
