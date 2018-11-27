<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/addPlugin/ums_url.js"></script>
	<title>事项目录</title>
</head>
<script type="text/javascript">
debugger;
	//父节点ID
	var catalogPid = jt.getParam("parId");
	var itemId = jt.getParam("itemId");
	
	var status;
	if (itemId == null || itemId == ""){
		status = true;
	}else{
		status = false;
	}
	
	function initEditPageBefore(){
		if (itemId != null && itemId != ""){
			var dataURL = "{contextPath}/item/queryGzwItem.action?itemId=" + itemId;
			var jPost= {};
			postJSON(dataURL,jPost,function(json,o){
				if (!json || json.errorCode != "0") {
					return jt.Msg.showMsg("获取数据失败！");
				} else {
					if(json.data.length > 0){
						jt._('#catalogId').value = json.data[0].catalogId;
						jt._('#itemId').value =   json.data[0].itemId;
						jt._('#itemCode').value = json.data[0].itemCode;
						jt._('#itemName').value = json.data[0].itemName;
					}
				}
			},false);
		}
	}
	
	function saveCatalog(aForm) {
		if (typeof(aForm) == 'undefined') aForm = jt._('#' + jt.getAttr(document.body, 'mainForm', 'frmMain'));
		if (!aForm.checkForm()) return; //表单验证

		//处理CodeEditor
		var objs = jt._('textarea.CodeEditor', aForm);
		for (var i=0;i<objs.length;i++) { try{ objs[i].getValue(); }catch(e){} }

		//disableEmptyField(aForm,'UI_ID');//将部分为空的输入框禁用
		
		var sURL = status ? actSave : actUpdate;

		top.showLoading('正在保存...');
		postJSON(sURL, jt.Form2Json(aForm), function (json, o) {
			top.showLoading(false);
			if (json.errorCode == 0) { //保存成功
				//[Event] EditPage保存成功后调用 EditPage_SaveSuccess(json) @Tag editPage
				if (typeof(EditPage_SaveSuccess) == 'function') {
					EditPage_SaveSuccess(json);
				} else {
					reloadFrameMainGrid(false,aForm);
					closeSelf();
				}
				if (typeof(submitResAuth) == 'function') submitResAuth(); //保存权限
			} else { //保存失败
				//[Event] EditPage保存失败后调用 EditPage_SaveError(json) @Tag editPage
				if (typeof(EditPage_SaveError) == 'function') {
					EditPage_SaveError(json);
				} else {
					showMsg(json.errorInfo); return;
				}
			}
		});
	}
	
	function initFormValueAfter(json){
		if (catalogPid != null && catalogPid != ""){
			jt._('#catalogId').value = catalogPid;
		}
	}
</script>

<body class="BodyEdit" pageType="EditPage"
	  actPath="{contextPath}/item/"
	  actSave="saveGzwItem.action"
	  actUpdate="updateGzwItem.action">
<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div icon="close.png" onclick="closeSelf();">关闭</div>
		<div icon="save.png" id="addButton" onclick="saveCatalog()">保存</div>
		<div></div>
	</div>
</div>

<div id="divFixCnt" style="padding:3px;">
	<form id="frmMain" name="frmMain" class="Validate">
		<DIV class=FormTableTitle>事项清单信息</DIV>
		<table width="100%" class="TableEdit" border="0" cellspacing="0" cellpadding="3">
			<tr>
				<td class="tit">事项清单编码：</td>
				<td class="cnt">
					<input id="catalogCode" type="text" class="input" name="itemCode" maxlength="64" />
					<input type="hidden" class="input" name="catalogId" id="catalogId" maxlength="64" />
					<input type="hidden" class="input" name="itemId" id="itemId" maxlength="64"   />
				</td>
			</tr>
			<tr>
				<td class="tit">事项清单名称：</td>
				<td class="cnt"><input type="text" class="input" name="itemName" ErrEmptyCap="事项名称不允许为空"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>

<%@ include file="/common/inc_bottom.jsp"%>