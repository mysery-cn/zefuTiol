<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<title>服务接口</title>
</head>
<script type="text/javascript">
  
// function formSubmit(aForm,mustCheck){
// 	if ((mustCheck) && (!aForm.onsubmit())) return false;
// 	var sURL='{contextPath}/schedule/manager.action?m=addOrUpdateMethod';
// 	showLoading('正在保存...');
// 	postJSON(sURL, jt.Form2Json(aForm),function (json,o){
// 		showLoading(false);
// 		if (json.errorCode!=0){ showMsg(json.errorInfo,'操作失败'); return; }
// 		reloadFrameMainGrid();
// 		closeSelf();
// 	});
// 	return false;
// }
</script>

<body class="BodyEdit" pageType="EditPage"
	  actPath="{contextPath}/serviceInterface/"
	  actSave="saveInterface.action"
	  actUpdate="updateInterface.action"
	  actGetData="getInterfaceByID.action?id={sID}"
>

<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div id="btnClose" icon="close.png" onclick="closeSelf();" style="display:none;">关闭</div>
		<div icon="save.png" onclick="EditPage_Save()">保存</div>
	</div>
</div>

<div id="divFixCnt" style="padding:3px;">
	<form id="frmMain" name="frmMain" class="Validate">
		<input type="image" class="imgSubmit">
		<input type="hidden" name="INTERFACE_ID"/>
		<table width="100%" class="TableEdit" border="0" cellspacing="0" cellpadding="3">
			<tr>
				<td class="tit must">接口名称：</td>
				<td class="cnt"><input type="text" class="input" name="INTERFACE_NAME" maxlength="64" ErrEmptyCap="接口名称不允许为空"/></td>
			</tr>
			<tr>
				<td class="tit must">接口类型：</td>
				<td class="cnt">
					<select class="input" name="INTERFACE_TYPE">
						<option value="REPORT">上报</option>
						<option value="DOWNLOAD">下载</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="tit must">接口地址：</td>
				<td class="cnt">
					<textarea rows="5" cols="7" name="INTERFACE_URL"  ErrEmptyCap="接口地址不允许为空"></textarea>
				</td>
			</tr>
			<tr>
				<td class="tit must">账号：</td>
				<td class="cnt"><input type="text" class="input" name="INTERFACE_USER" maxlength="64" ErrEmptyCap="账户不允许为空"/></td>
			</tr>
			<tr>
				<td class="tit must">密码：</td>
				<td class="cnt"><input type="text" class="input" name="INTERFACE_PASSWORD" maxlength="64" ErrEmptyCap="密码不允许为空"/></td>
			</tr>
		</table>
	</form>
</div>

</div>

</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
