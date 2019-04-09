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

<form method="post" action="login.do" id="theForm">
    <p>
        手机号码：<input type="text" maxlength="12" minlength="4" name="phone">
    </p>
    <p>
        登录密码：<input type="password" maxlength="20" minlength="6" id="pwd">
    </p>
    <p>
        您的身份是：
        <input type="radio" name="type" value="manager" checked>工作人员
        <input type="radio" name="type" value="user">用户
    </p>
    <input type="hidden" id="relPwd" name="password">
    <input type="submit" value="登录" id="log">
    <input type="reset" value="重置">
</form>
<script src="js-rsa-security.js" type="text/javascript"></script>
<script type="text/javascript">

    RSAUtils.setMaxDigits(200);
    var key=RSAUtils.getKeyPair("${pkExp}","","${pkMod}");
    var sub=document.getElementById("log");
    sub.onclick=function (ev) {
        var pwd=document.getElementById('pwd').value;
        var rel=document.getElementById("relPwd");
        rel.value=RSAUtils.encryptedString(key,pwd);
    }
</script>
</body>
</html>
