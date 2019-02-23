<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/2/22
  Time: 8:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>RESULT</title>
</head>
<body>

<c:choose>
    <c:when test="${requestScope.flag eq 1}">
        <c:out value="感谢${username}的加入！您的卡号是${cardId}"/>
    </c:when>
    <c:when test="${requestScope.flag eq 0}">
        <c:out value="注册失败！点击下方链接重新注册"/>
        <a href="<c:url value="register.jsp"/>">点击重新注册</a>
    </c:when>
    <c:when test="${requestScope.flag eq 2}">
        <c:out value="登录失败，请重新登录。"/>
        <a href="<c:url value="loginPage.jsp"/> ">点击重新登录</a>
    </c:when>
    <c:when test="${requestScope.flag eq 4}">
        <h1><c:out value="余额不足！" /></h1>

    </c:when>
    <c:when test="${requestScope.flag eq 5}">
        <h1><c:out value="恭喜您，转账成功。"/></h1>
    </c:when>
    <c:when test="${requestScope.flag eq 7}">
        <c:out value="没有操作权限！"/>
    </c:when>
    <c:when test="${requestScope.flag eq 8}">
        <c:out value="您的当前余额为￥ ${balance} ！"/>
    </c:when>
    <c:when test="${requestScope.flag eq 9}">
        <h1><c:out value="结果如下："/></h1>
        <table>
            <tr>
                <th>金额</th>
                <th>日期</th>
                <th>转出账户</th>
                <th>流水线号码</th>
                <th>余额</th>
            </tr>
            <c:forEach items="${requestScope.records}" var="rec">
                <tr>
                    <td>${rec.change}</td>
                    <td>${rec.date}</td>
                    <td>${rec.toId}</td>
                    <td>${rec.waterId}</td>
                    <td>${rec.balance}</td>
                </tr>
            </c:forEach>

        </table>
    </c:when>
    <c:when test="${requestScope.flag eq 11}">
        <c:out value="注销成功！"/>
    </c:when>
    <c:when test="${requestScope.flag eq 12}">
        <c:out value="注销失败！"/>
    </c:when>
    <c:when test="${requestScope.flag eq 13}">
        <c:out value="修改成功！"/>
    </c:when>
    <c:when test="${requestScope.flag eq 14}">
        <c:out value="修改失败！"/>
    </c:when>
    <c:when test="${requestScope.flag eq 15}">
        <c:out value="充值/扣款成功！"/>
    </c:when>
    <c:when test="${requestScope.flag eq 16}">
        <c:out value="充值/扣款失败！"/>
    </c:when>
    <c:when test="${requestScope.flag eq 20}">
        <c:out value="管理员验证信息失败。"/>
    </c:when>

</c:choose>
</body>
</html>
