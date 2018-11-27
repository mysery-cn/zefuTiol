<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<title>系统参数配置</title>
</head>
<script type="text/javascript">
	var appId = jt.getParam('appId');

	function jtParseURL_Page(sURL) {
		sURL = jtReplaceVAR(sURL, 'sID,appId');
		return sURL;
	}

	//表单检测
	function EditPage_CheckForm(aForm) {
		return true;
	}

	//页面初始化后
	function initFormValueAfter(json) {
		jt._('#appId').value = jt._('#appId').value == "" ? appId : "";
	}

	//切换页签
	function showPage(tabId) {
		var tabIds = "tabbasic".split(";");
		for (var i = 0; i < tabIds.length; i++) {
			document.all(tabIds[i]).style.display = "none";
		}
		document.all(tabId).style.display = "";
	}

</script>

<body class="BodyEdit" pageType="EditPage"
	  actPath="{SYSURL.bam}/bam/bizSystemConfig/"
	  actSave="saveOrUpdate.action" actUpdate="saveOrUpdate.action"
	  actGetData="listByAppAndRange.action?appId={appId}">

<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div id="btnSave" icon="save.png" onclick="EditPage_Save()">保存</div>
	</div>
</div>

<div id="divFixCnt" style="padding:3px;">
	<form id="frmMain" name="frmMain" class="Validate">
		<div style="font-size:16px;font-weight:bold;text-align:center;padding:15px;">全局配置</div>
		<input type="hidden" name="appId" value="" />
		<div class="TabBase" cssTab="tabItem" cssTabSel="tabItemSel" cssTabOver="tabItemOver" style="padding-left:0px;padding-top:0px;">
			<div class="tabItem" AfterClick="showPage('tabbasic')" default="true">基本配置</div>
		</div>
		<table width="100%" id="tabbasic" class="TableEdit" border="0"
			   cellspacing="0" cellpadding="3">
			<tr>
				<td class="tit">系统名称：</td>
				<td class="cnt">
					<input type="text" class="input" name="cfg_global_system_name" maxlength="120" value="" />
				</td>
			</tr>
			<tr>
				<td class="tit">技术支持信息：</td>
				<td class="cnt">
					<input type="text" class="input" name="cfg_global_support_info" maxlength1="120" value="" />
				</td>
			</tr>
			<tr>
				<td class="tit">登录入口地址配置：</td>
				<td class="cnt">
					<input type="text" class="input" name="cfg_portal_indexurl" maxlength1="120" value="" />
				</td>
			</tr>
			<tr>
				<td class="tit">首页内嵌页面配置：</td>
				<td class="cnt">
					<input type="text" class="input" name="cfg_tiol_indexurl" maxlength1="120" value="" />
				</td>
			</tr>
			<tr>
				<td class="tit">首页左侧导航隐藏：</td>
				<td class="cnt">
					<input type="text" class="input" name="cfg_tiol_portal_hide_nav" maxlength1="120" value="" />
				</td>
			</tr>
			<tr>
				<td class="tit">单点登录地址：</td>
				<td class="cnt">
					<input type="text" class="input" name="cfg_tiol_sso_url" maxlength1="120" value="" />
				</td>
			</tr>
			<tr>
				<td class="tit">单点登录Cookie名称：</td>
				<td class="cnt">
					<input type="text" class="input" name="cfg_tiol_sso_cookie_name" maxlength1="120" value="" />
				</td>
			</tr>
			<tr>
				<td class="tit">单点登录UserKey：</td>
				<td class="cnt">
					<input type="text" class="input" name="cfg_tiol_sso_cookie_user" maxlength1="120" value="" />
				</td>
			</tr>
			<tr>
				<td class="tit">校验失败跳转地址：</td>
				<td class="cnt">
					<input type="text" class="input" name="cfg_tiol_sso_cookie_loginUrl" maxlength1="120" value="" />
				</td>
			</tr>
		</table>
	</form>
</div>

</div>
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
