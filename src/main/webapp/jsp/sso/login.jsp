<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv=“X-UA-Compatible” content=“IE=8″>
	<%@ include file="/common/inc_head.jsp"%>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/jquery/jQuery.md5.js"></script>
	<script>
		var hostName = "${hostName}";
		var redirectToStr = "${redirectTo}";
	</script>
    <!--<script type="text/javascript" src="<%=SYSURL_static%>/js/evercookie/js/swfobject-2.2.min.js"></script>
    <script type="text/javascript" src="<%=SYSURL_static%>/js/evercookie/js/evercookie.js"></script>
    <script type="text/javascript" src="<%=SYSURL_static%>/js/fingerprintjs2/fingerprint2.js"></script>-->
	<script type="text/javascript" src="<%=SYSURL_static%>/js/ums/login.js"></script>
	<link type="text/css" href="<%=SYSURL_static%>/css/login.css" rel="stylesheet" />
<title>登录</title>
</head>
<style type="text/css">
	.login{ position:absolute;  width:508px; height:368px; background:url('<%=SYSURL_static%>/images/sso/login_win.png'); z-index:2;top:50%;left: 50%;margin-left: -254px;margin-top: -184px;}
</style>
<body scroll="auto">
        <div class="head_bg"><img src="<%=SYSURL_static%>/images/sso/com_login_logo.png"></div>
		<div class="mid_bg"><img src="<%=SYSURL_static%>/images/sso/login_bg_b.png"></div>
		<div class="login">
		    <FORM onsubmit="return formSubmit(this);" class="Validate">
			<input class="login-input-admin" type="text" placeholder="请输入用户名" ErrEmptyCap="用户名不允许为空！"  id="userId"  name="userId">
			<input class="login-input-password" type="password" placeholder="请输入密码" id="password"  ErrEmptyCap="密码不允许为空！"  name="password">
			<input class="login-btn" type="image"  src="<%=SYSURL_static%>/images/sso/btn_login.png" >
			</FORM>
		</div>
       <div class="footer" style="display:none;"><div class="foot_a"><a class="footer_a">版权所有：国资委</a><a class="footer_a">技术支持：信息中心</a> </div> </div>
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
<script type="text/javascript">
	//getDeviceId();
	deviceKey  = "127.0.0.1";
</script>
