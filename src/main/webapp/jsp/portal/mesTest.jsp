<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/portal.js"></script>
	<title>短信测试</title>
</head>
<script type="text/javascript">
	var UI_ID = jt.getParam('UI_ID');
	var jConfig={data:[]};
	var par; //容器
	var arrCell=[]; 
	function jtParseURL_Page(sURL){
		//sURL=sURL.replace(/\{appId\}/ig, appId);
		return sURL;
	}
	function init(){
		par = _('#divMain');
		loadConfig();
		loadShortcut();
		addResizeEvent(bodyResize);
	}
	/* function msg(){
		var sURL='{contextPath}/msgcnt/test/enginetip.action?t={JSTime}&uid=baiyunhao';
		getJSON(sURL,function (json,o){
  		});
	}
	function msg2(){
		var sURL='{contextPath}/msgcnt/test/smss.action?t={JSTime}&uid=baiyunhao';
		getJSON(sURL,function (json,o){
  		});
	}
	function msg3(){
		//var sURL='{contextPath}/sms/msg/single.action?t={JSTime}';
		var sURL='{contextPath}/Sms/sendSmsCode.action?t={JSTime}&MOBILE=18084830050&userName=yinc&companyID=123';
		getJSON(sURL,function (json,o){
  		});
	} */
	function msg4(){
		var sURL='{contextPath}/sms/msg/single.action?msg=这是一条测试短信！&userId=baiyunhao';
		getJSON(sURL,function (json,o){
  		});
	}
	function msg5(){
		var sURL='{contextPath}/sms/msg/code.action?t={JSTime}&mobile=18084830050&userName=yinc&companyID=C001ZDL';
		getJSON(sURL,function (json,o){
			console.log(json);
			console.log(o)
  		});
	}
	function msg6(){
		var sURL='{contextPath}/sms/msg/list.action';
		getJSON(sURL,function (json,o){
			console.log(json);
			console.log(o);
  		});
	}
	
	function msg7(){
		var code = $("#code").val();
		var sURL='{contextPath}/sms/msg/verification.action?mobile=18084830050&code='+code;
		getJSON(sURL,function (json,o){
			console.log(json);
			console.log(o);
  		});
	}
</script>
	
<body class="BodyDesktop" scroll="no">
<div id="divBox" class="ScrollBox" style="padding-right:5px;">
	<!-- <button onclick="msg()">按钮1</button>
	<button onclick="msg2()">按钮2</button>
	<button onclick="msg3()">按钮3</button> -->
	<button onclick="msg4()">发送短信</button>
	<button onclick="msg5()">发送验证码</button>
	<button onclick="msg6()">短信集合</button><br/>
	请输入验证码：<input type="text" name="code" id="code"/><button onclick="msg7()">验证码验证</button>
	
</div>
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>