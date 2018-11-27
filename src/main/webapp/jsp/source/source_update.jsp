<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/addPlugin/ums_url.js"></script>
	<title>制度分类信息</title>
</head>
<script type="text/javascript">
	var sourceId = jt.getParam("sourceId");
	
	var status;
	if (sourceId == null || sourceId == ""){
		status = true;
	}else{
		status = false;
	}
	
	
	function initEditPageBefore(){
	    var role = curUser.adminRoleId;
        var roleList= curUser.roleListStr;
        //配置管理员及超级管理员可以修改
        if(role != "configAdminRole" && roleList.indexOf("SYS_9999") ==-1){
            $('#addButton').hide();
            $('#addButton').removeAttr('onclick');//去掉标签中的onclick事件
            $('#frmMain input').attr('readonly','readonly');
            $('#frmMain select').attr('disabled','true');
        }
		if (sourceId != null && sourceId != ""){
			var dataURL = "{contextPath}/source/querySourceDetail.action?sourceId=" + sourceId;
			var jPost= {};
			postJSON(dataURL,jPost,function(json,o){
				if (!json || json.errorCode != "0") {
					return jt.Msg.showMsg("获取数据失败！");
				} else {
					if(json.data.length > 0){
						jt._('#sourceId').value = json.data[0].sourceId;
						jt._('#sourceName').value = json.data[0].sourceName;
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
	
</script>

<body class="BodyEdit" pageType="EditPage"
	  actPath="{contextPath}/source/"  actSave="saveSource.action"  actUpdate="updateSource.action">
<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div icon="close.png" onclick="closeSelf();">关闭</div>
		<div icon="save.png" id="addButton" onclick="saveCatalog()">保存</div>
		<div></div>
	</div>
</div>

<div id="divFixCnt" style="padding:3px;">
	<form id="frmMain" name="frmMain" class="Validate">
		<DIV class=FormTableTitle>任务来源信息</DIV>
		<table width="100%" class="TableEdit" border="0" cellspacing="0" cellpadding="3">
			<tr>
				<td class="tit">任务来源名称：</td>
				<td class="cnt"><input type="text" class="input" name="sourceName" ErrEmptyCap="制度名称不允许为空"/></td>
				<input type="hidden" class="input" name="sourceId" id="sourceId" />
			</tr>
		</table>
	</form>
</div>
</body>
</html>

<%@ include file="/common/inc_bottom.jsp"%>