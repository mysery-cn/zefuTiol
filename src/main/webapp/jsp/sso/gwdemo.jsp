<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// 是否开启二维码认证
String QRCodeAuth = request.getAttribute("QRCodeAuth")==null?
		null:request.getAttribute("QRCodeAuth").toString();
%>
<head>
	<title>模拟应用登录页面</title>
	<script type="text/javascript">
		// 是否开启二维码认证
		var qrCodeAuth = '<%=QRCodeAuth%>';
	</script>
</head>
<body>
<br>

<table width="100%" align="center">
	<tr align="center" valign="middle">
		<td>
			用户名：<input type="text" />
		</td>
	</tr>
	<tr align="center" valign="middle">
		<td>
			密&nbsp;&nbsp;&nbsp;&nbsp;码：<input type="text" />
		</td>
	</tr>
	<tr align="center" valign="middle">
		<td>
			<input type="submit" value="登录" />&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://bgtoa.sc.cegn:8080/gap/jitGWRandom">认证</a>
		</td>
	</tr>
</table>


<%if(Boolean.valueOf(QRCodeAuth)==true){ %>
	<jsp:include page="qrCodeLogin.jsp"></jsp:include>
<%}else{ %>
	<jsp:include page="login.jsp"></jsp:include>
<%} %>
</body>