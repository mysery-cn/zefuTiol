<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/jquery/jQuery.md5.js"></script>
	<script>
		var hostName = "${hostName}";
	</script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/evercookie/js/swfobject-2.2.min.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/evercookie/js/evercookie.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/fingerprintjs2/fingerprint2.js"></script>
    <script type="text/javascript" src="<%=SYSURL_static%>/js/ums/login.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/scca/js/topEsa.min.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/scca/js/clientConf.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/fingerprintjs2/fingerprint2.js"></script>
	<meta http-equiv="X-UA-Compatible" content="IE=8">
	<title>成都市一体化协同办公平台</title>
</head>

<style type="text/css">
	body,td,a,p,div,input,button { font-size:9pt; font-family:"微软雅黑","宋体", "Verdana", "Arial", "Helvetica", "sans-serif"; }
	body,ul,li,dl, dd, h3, h1, h2, h4, h5, h6, h7 { margin: 0px; padding: 0px; font-weight:normal; }
	body{ background: white url('<%=SYSURL_static%>/images/sso/login_bg.png') repeat-x 0px -40px; margin:0px; border:none; }
	table.login{ background: url('<%=SYSURL_static%>/images/sso/login_main1.jpg') no-repeat center -40px; }
	.login_input{width:275px;height:26px; border:none; font-size:16px; line-height:24px; background-color: white;}
	body.Assist { background: #F5F5F5 url(''); }
	.AssFrame {background-color: #EFEFEF; border:1px solid gray;}
	.AssFrame .login_input{width:175px; border: 1px solid #DDD; }
	.AssFrame .button{width:175px; }
	
	
	.app{ top:20;right:240;position:absolute; font-size:12px; text-align:center;display:inline;}
	.box{ border:1px solid #EEEEEE;text-align:center;width:135px; height:135px; display:block; position:absolute; left:0;; top:0;background-color: #FFFFFF}
	.box img {width:100%;}
</style>

<%
	 //计算UMS域名 【临时代码】
	 String URL_UMS = baseURL;
	 URL_UMS = URL_UMS.replaceAll("^http://(\\w*)oa\\.","http://$1ums.");
%>

<body scroll="auto" onload="character()">
<div class="app" onmouseover="this.className = 'app on';" onmouseout="this.className = 'app';">
	<div class="box" style="left:60;">
		<span style="font-size: 14px;text-align: center;padding-top: 20px">成都市协同办公APP</span>
		<img alt="移动办公APP" src="<%=SYSURL_static%>/images/sso/APP_QRCODE.png">
		<span style="font-size: 10px;text-align: center">支持安卓客户端</span><br>
	</div>
</div>
<table id="tabLogin" class="login" width="100%" style="height:100%;table-layout:fixed;" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td align="center" valign="top">
			<div style="width:484px;height:325px; margin-top:113px; position:relative;">
				<div id="showMsg" align="center" style="position:relative;top:28px;text-align:center;font:normal 'MicroSoft YaHei';color:red;text-align: center;width: 100%">
					<b style="font-size: 24px;">上网不涉密，涉密不上网</b>
				</div>
			  <!--  <img style="margin-top:115px; float:left;margin-left:320px" height=140px width=140px src=""></img>    -->
				  <FORM id="loginForm" onsubmit="return formSubmit(this);" class="Validate">
					<input style="position:absolute; width:218px; left:100px; top:125px;background:none;border:none" type="text" class="login_input" ErrEmptyCap="用户名不允许为空！"  id="userId"  name="userId">
					<input style="position:absolute; width:218px; left:100px; top:166px;background:none;border:none" type="password" class="login_input" id="password"  ErrEmptyCap="密码不允许为空！"  name="password" value1="8888">
					<input style="position:absolute; left:120px; top:212px; width:180px; height:38px;" type="image"  src="<%=SYSURL_static%>/images/sso/t.gif" >
				</FORM>

				<input style="position:absolute; left:350px; top:128px; width:90px; height:120px;cursor:hand;" type="image"  onclick="UKEYsubmit()"  src="<%=SYSURL_static%>/images/sso/t.gif" >
					<%-- <a id="GapDownFile" style="position:absolute; left:0px; top:312px; width:150px; height:38px;cursor:hand;display:none" onclick="downloadClient()" >下载中心</a>
					<div style="position:absolute; left:350px; top:128px; width:90px; height:120px;cursor:hand;"  onclick=doDataProcess()  src="<%=SYSURL_static%>/images/sso/t.gif" /></div> --%>


			</div>
			<div style="text-align: center">
				<a onclick="openRetrievePasswordPage()" href="#" style="font-size: 16px;margin-left: 380px"><u>找回密码</u></a>
				<a onclick="downloadClient()" href="#"  style="font-size: 16px;margin-left: 10px"><u>注意事项</u></a>
			</div>
		</td>
	</tr>
	<tr>
		<td align="center" valign="middle">
			<div align="center" style="text-align:center;padding:0 20px; font:normal 'MicroSoft YaHei';">
			<p style="font-size: 15px;margin-bottom: 16px">平台管理单位：国务院国有资产监督管理委员会</p>
			<p style="font-size: 15px;margin-bottom: 16px">技术支持：028-65048216</p>
			</div>
		</td>
	</tr>
</table>

<%
    String randStr = String.valueOf(System.currentTimeMillis());
    session.setAttribute("ToSign", randStr);
%>
    <!--
    <script src="<%=SYSURL_static%>/scca/js/topEsa.min.js" type="text/javascript"></script>
    <script src="<%=SYSURL_static%>/scca/js/clientConf.js"></script>
     -->
    <script type="text/javascript">

        function character(){
            $('#showMsg').css('color','#FF200D');  //默认值
            setTimeout(" $('#showMsg').css('display','none')",0); //第一次闪烁
            setTimeout( "$('#showMsg').css('display','')",300); //第二次闪烁
        };

        window.setInterval(character, 2000); //让index 多久循环一次

		function initCertList() {
            try {
                //var certs = CertStore.listAllCerts().forSign() ; //过滤签名证书
				var certs = CertStore.listAllCerts().byKeyUsage(128); //过滤签名证书
				var certs_Validity = CertStore.listAllCerts().byKeyUsage(128).byValidity(); //过滤有效期内的签名证书
                if (certs.size() > 0) {
                    for (var i = 0; i < certs.size(); i++) {
						var flag = 0;
                        var cert = certs.get(i);
                        var sn = cert.serialNumber();
                        var cn = getCNFromSubject(cert);
						for(j = 0; j < certs_Validity.size(); j++)
						{
							var cert_validity = certs_Validity.get(j);
							var sn_validity = cert_validity.serialNumber();
							if (sn == sn_validity)
							{
								addOption(cert.serialNumber(), cn);
								flag = 1;
							}
						}
						if (flag == 0)
						{
                        addOption(cert.serialNumber(), "(已过期)"+cn);
						}
                    }
                } else {
                	alert("未读取到UKEY！");
                    $("#CertList").append("<option value='0'>查询结果为空");
                    //return false;
                    window.location.reload();
                }
            } catch (e) {
                if (e instanceof TCACErr) {
                	alert("未安装控件,请下载安装后重启浏览器。");
        			downloadClient();
                } else {
                    alert("读取UKEY信息出错!");
                }
                return false;
            }
            return true;
        }

        // 从Certificate对象中获取CN
        function getCNFromSubject(cert) {
            try {
                var t = cert.subject().match(/(S(?!N)|L|O(?!U)|OU|SN|CN|E)=([^=]+)(?=, |$)/g);
                for (var i = 0; i < t.length; i++) {
                    if (t[i].indexOf("CN=") === 0)
                        return t[i].substr(3, t[i].length);
                }
                return null;
            } catch (e) {
                if (e instanceof TCACErr) {
                    alert(e.toStr());
                } else {
                    alert("Here is JS Error !!!");
                }
            }
        }

        function addOption(oValue, oName) {
            var sid = document.getElementById("CertList");
            var myOption = document.createElement("option");
            sid.appendChild(myOption);
            myOption.text = oName;
            myOption.value = oValue;
        }

        // 返回Certificate对象
        function getSelectedCert() {
            try {
                var certs = CertStore.listAllCerts();
                //var selectedCertSN = $("[id=CertList]").attr("value");
                var selectedCertSN =document.all.CertList.getAttribute("value");
                var r = certs.bySerialnumber(selectedCertSN);
                return r.get(0);
            } catch (e) {
                if (e instanceof TCACErr) {
                    alert(e.toStr());
                } else {
                    alert("没有找到证书");
                }
            }
        }
	//签名方法
	function sign() {
		try {
			var toSign = <%=randStr%>;
			var cert = getSelectedCert();
			if(cert == null || cert == ""){
				return false;
			}
			var P7 = cert.signLogondata(toSign);
			$("#Signature").val(P7);
			return true;
 		} catch (e) {
			if (e instanceof TCACErr) {
				showMsg(e.description);
				window.location.reload();
			} else {
				alert("Here is JS Error !!!");
			}
			return false;
		}
 		return true;
	}
	function UKEYsubmit(){
		var sURL='<%=urlStr.substring(0, urlStr.indexOf(contextPath))+contextPath%>/Scca/SccaLogin.action';
		if(TopESAConfig()){
			if(initCertList()){//如果取到UKEY证书信息
				if(!sign()){
					return false;
				}
				//document.all.ukeylogin.click();//执行提交
				var data = { "toSign":$("#ToSign").val(), "signedData":$("#Signature").val() };
				postJSON(sURL, data, function(json,xhr){
					if(typeof(json) != 'undefined'){
						if( json.code == "02"){//校验失败
							alert("登录失败，请联系管理员！"); 
							return;
						}
					}
					window.location.href = "http://oa.cd.sc.cegn.cn/jsp/portal/index.jsp";
					/* else{
						$("#userId").val(json.userId);
						$("#password").val(json.password);
						document.all.loginButton.click();//执行提交
					} */
				}, false);
			}
		}else{
			alert("未安装控件,请下载安装后重启浏览器。");
			downloadClient();
		}
	}
</script>
<form style=display:none name="LogonForm" id="LogonForm" method="post" action="<%=urlStr.substring(0, urlStr.indexOf(contextPath))+contextPath%>/Scca/SccaLogin.action" onsubmit="sign()">
   
    请选择证书：
    <select name="CertList" id="CertList"></select>
    <br>
    <input id="ukeylogin" name="Submit" type="submit" value="登陆">
    <input id="ToSign" name="ToSign" type="hidden" value=<%=randStr%>>
    <!-- 出于安全考虑，必须使用随机字符串 -->
    <input name="Signature" type="hidden" id="Signature">
</form>

</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
<script type="text/javascript">
    $(document).keydown(function (e){
        if(e.which == "13"){
            //鼠标焦点不在用户名或者密码上时，屏蔽回车键触发事件
            if(!$("#userId").is(":focus") && !$("#password").is(":focus")){
        		return false;
			}
        }
    });
    getDeviceId();
</script>

