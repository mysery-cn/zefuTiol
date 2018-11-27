<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/addPlugin/ums_url.js"></script>
	<title>事项目录</title>
</head>
<script type="text/javascript">
	//父节点ID
	var catalogPid = jt.getParam("parId");
	var catalogId = jt.getParam("catalogId");
	var navLevel = jt.getParam("navLevel");
	
	
	var status;
	if (catalogId == null || catalogId == ""){
		status = true;
	}else{
		status = false;
	}
	
	function init(){ 
		var dataURL = "{contextPath}/catalog/queryCatalogRole.action";
		var jPost= {};
		postJSON(dataURL,jPost,function(json,o){
			if (!json || json.errorCode != "0") {
				return jt.Msg.showMsg("获取数据失败！");
			} else {
				if(json.data[0] == 0){
					$("#addButton").css('display','none'); 
				}
			}
		},false);
	}
	
	function initEditPageBefore(){
        jt._('#catalogLevel').value = parseInt(navLevel) + 1;
		if (catalogId != null && catalogId != ""){
			var dataURL = "{contextPath}/catalog/queryCatalog.action?catalogId=" + catalogId;
			var jPost= {};
			postJSON(dataURL,jPost,function(json,o){
				if (!json || json.errorCode != "0") {
					return jt.Msg.showMsg("获取数据失败！");
				} else {
					if(json.data.length > 0){
					    if(json.data[0].catalogCode != "undefined"){
                            jt._('#catalogCode').value = json.data[0].catalogCode;
                        }
						jt._('#catalogPid').value = json.data[0].catalogPid;
						jt._('#catalogId').value = json.data[0].catalogId;
						jt._('#catalogName').value = json.data[0].catalogName;
						jt._('#orderNumber').value = json.data[0].orderNumber;
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

		showLoading('正在保存...');
		postJSON(sURL, jt.Form2Json(aForm), function (json, o) {
			showLoading(false);
			if (json.errorCode == 0) { //保存成功
				//[Event] EditPage保存成功后调用 EditPage_SaveSuccess(json) @Tag editPage
				if (typeof(EditPage_SaveSuccess) == 'function') {
					EditPage_SaveSuccess(json);
				} else {
					reloadFrameMainGrid(false,aForm);
					//closeSelf();
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
		if (catalogId == null || catalogId == ""){
			jt._('#catalogPid').value=jt.getParam('parId');
		}
	}
</script>

<body class="BodyEdit" pageType="EditPage"
	  actPath="{contextPath}/catalog/"
	  actSave="saveCatalog.action"
	  actUpdate="updateCatalog.action"
	  actGetData="queryCatalog.action?catalogId={catalogId}"
		>
<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div icon="close.png" onclick="closeSelf();">关闭</div>
		<div icon="save.png" id="addButton" onclick="saveCatalog()">保存</div>
		<div></div>
	</div>
</div>

<div id="divFixCnt" style="padding:3px;">
	<form id="frmMain" name="frmMain" class="Validate">
		<DIV class=FormTableTitle>事项信息</DIV>
		<table width="100%" class="TableEdit" border="0" cellspacing="0" cellpadding="3">
			<tr>
				<td class="tit">编　　码：</td>
				<td class="cnt">
					<input id="catalogCode" type="text" class="input" name="catalogCode" maxlength="32" />
					<input type="hidden" class="input" name="catalogPid" id="catalogPid" />
					<input type="hidden" class="input" name="catalogId" id="catalogId" />
                    <input type="hidden" class="input" name="catalogLevel" id="catalogLevel" />
				</td>
			</tr>
			<tr>
				<td class="tit">事项名称：</td>
				<td class="cnt"><input type="text" class="input" name="catalogName" ErrEmptyCap="事项名称不允许为空" maxlength="30"/></td>
			</tr>
			<tr>
				<td class="tit">排　　序：</td>
				<td class="cnt"><input type="text" class="input" name="orderNumber" ErrCheck="{CST_Integer}" ErrEmptyCap="排序不允许为空" ErrCheckCap="请输入整数！" /></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>

<%@ include file="/common/inc_bottom.jsp"%>