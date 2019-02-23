<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/2/22
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>充值/扣款界面</title>
</head>
<body>
<form method="post" action="managerservice.do">
    <input type="hidden" name="symbol" value="money">
    <h2>
        用户手机号（必填）：<input type="text" minlength="11" maxlength="11" name="phone" required><br>
        金额：<input type="text" name="changednum"><br>
        选项：<input type="radio" name="type" value="add">充值
        <input type="radio" name="type" value="sub">扣款
        <input type="submit" value="提交">
    </h2>
</form>
</body>
</html>
