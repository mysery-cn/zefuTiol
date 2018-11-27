<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/addPlugin/ums_url.js"></script>
	<title>会议类型信息</title>
</head>
<script type="text/javascript">
	var typeId = jt.getParam("typeId");
	var status;
	if (typeId == null || typeId == ""){
		status = true;
	}else{
		status = false;
	}
	
	function init(){
		//加载领导班子类型
		var groupType = getCfgValueJson('global','cfg_group_type');
		var data= groupType.data;
		for(var i=0;i<data.length;i++){
			$("#groupType").append("<option value='"+data[i].text+"'>"+data[i].text+"</option>");
		}
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
		if (typeId != null && typeId != ""){
			var dataURL = "{contextPath}/meetingType/queryMeetingTypeDetail.action?typeId=" + typeId;
			var jPost= {};
			postJSON(dataURL,jPost,function(json,o){
				if (!json || json.errorCode != "0") {
					return jt.Msg.showMsg("获取数据失败！");
				} else {
					if(json.data.length > 0){
						jt._('#typeId').value = json.data[0].typeId;
						if (json.data[0].typeName != undefined) {
							jt._('#typeName').value = json.data[0].typeName;
						}
						if (json.data[0].typeCode != undefined) {
							jt._('#typeCode').value = json.data[0].typeCode;
						}
						if (json.data[0].groupType != undefined) {
							jt._('#groupType').value = json.data[0].groupType;
						}
						if (json.data[0].orderNumber != undefined) {
							jt._('#orderNumber').value = json.data[0].orderNumber;
						}
						if (json.data[0].alias != undefined) {
							jt._('#alias').value = json.data[0].alias;
						}
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
	  actPath="{contextPath}/meetingType/"
	  actSave="saveMeetingType.action"
	  actUpdate="updateMeetingType.action">
<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div icon="close.png" onclick="closeSelf();">关闭</div>
		<div icon="save.png" id="addButton" onclick="saveCatalog()">保存</div>
		<div></div>
	</div>
</div>

<div id="divFixCnt" style="padding:3px;">
	<form id="frmMain" name="frmMain" class="Validate">
		<DIV class=FormTableTitle>会议类型信息</DIV>
		<table width="100%" class="TableEdit" border="0" cellspacing="0" cellpadding="3">
			<tr>
				<td class="tit">编　　码：</td>
				<td class="cnt">
					<input id="catalogCode" type="text" class="input" name="typeCode" maxlength="64" />
					<input type="hidden" class="input" name="typeId" id="typeId" maxlength="64"   />
				</td>
			</tr>
			<tr>
				<td class="tit">会议类型名称：</td>
				<td class="cnt"><input type="text" class="input" name="typeName" ErrEmptyCap="会议类型名称不允许为空"/></td>
			</tr>
			<tr>
				<td class="tit">领导班子类型：</td>
				<td class="cnt">
					<select id="groupType" name="groupType">
						
					</select>
				</td>
			</tr>
			<tr>
				<td class="tit">排序：</td>
				<td class="cnt"><input type="text" class="input" name="orderNumber" /></td>
			</tr>
			<tr>
				<td class="tit">别名：</td>
				<td class="cnt"><input type="text" class="input" name="alias" /></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>

<%@ include file="/common/inc_bottom.jsp"%>