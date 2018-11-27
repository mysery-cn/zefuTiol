<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/addPlugin/ums_url.js"></script>
	<title>权限配置管理</title>
</head>
<script type="text/javascript">
	var purviewId = jt.getParam("purviewId");
	var status;
	if (purviewId == null || purviewId == ""){
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
		if (purviewId != null && purviewId != ""){
			var dataURL = "{contextPath}/purview/queryPurviewDetail.action?purviewId=" + purviewId;
			var jPost= {};
			postJSON(dataURL,jPost,function(json,o){
				if (!json || json.errorCode != "0") {
					return jt.Msg.showMsg("获取数据失败！");
				} else {
					if(json.data.length > 0){
						jt._('#purviewId').value = json.data[0].PURVIEW_ID;
						jt._('#purviewType').value = json.data[0].PURVIEW_TYPE;
							if(json.data[0].PURVIEW_TYPE==1){
								$("#objectName").attr("disabled",false);
								$("#objType").html("用户：");
								/*$("#objectName").attr("onclick","userTree('purviewObject', 'objectName', true)"); */
								document.getElementById("objectName").onclick = function(){
									userTree('purviewObject', 'objectName', true);
								}
							}else if(json.data[0].PURVIEW_TYPE==2){
								$("#objectName").attr("disabled",false);
								$("#objType").html("角色：");
								/*$("#objectName").attr("onclick","roleTree('purviewObject', 'objectName', true)");  */
								document.getElementById("objectName").onclick = function(){
									roleTree('purviewObject', 'objectName', true);
								}
							}else{
								$("#objectName").attr("disabled",false);
								$("#objType").html("部门：");
								/*$("#objectName").attr("onclick","funSelectTree_Org('purviewObject', 'objectName', true)");  */
								document.getElementById("objectName").onclick = function(){
									funSelectTree_Org('purviewObject', 'objectName', true);
								}
							}
						jt._('#objectName').value = json.data[0].OBJECT_NAME;
						jt._('#purviewObject').value = json.data[0].PURVIEW_OBJECT;
						jt._('#companyNames').value = json.data[0].companyNames;
						jt._('#companyIds').value = json.data[0].companyIds
						jt._('#startTime').value = json.data[0].START_TIME;
						jt._('#endTime').value = json.data[0].END_TIME;
					}
				}
			},false);
		}
	}
	
	function savePurview(aForm) {
		getCheckBoxIds();
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
</script>

<body class="BodyEdit" pageType="EditPage"
	  actPath="{contextPath}/purview/"
	  actSave="savePurview.action"
	  actUpdate="updatePurview.action">
<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div icon="close.png" onclick="closeSelf();">关闭</div>
		<div icon="save.png" id="addButton" onclick="savePurview()">保存</div>
		<div></div>
	</div>
</div>

<div id="divFixCnt" style="padding:3px;">
	<form id="frmMain" name="frmMain" class="Validate">
		<DIV class=FormTableTitle>权限信息</DIV>
		<table width="100%" class="TableEdit" border="0" cellspacing="0" cellpadding="3">
			<input type="hidden" class="input" name="purviewId" id="purviewId"  />
			<tr>
				<td class="tit">权限类型：</td>
				<td class="cnt">
					<select name="purviewType" id="purviewType">
		       			<option value="">--请选择--</option>
		        		<option value="1">用户</option>
		        		<option value="2">角色</option>
		        		<option value="0">部门</option>
		    		</select>
				</td>
				<td class="tit" id="objType">选择：</td>
				<td class="cnt">
					<input id="objectName" type="text" class="input" name="objectName" placeholder="请选择" disabled="disabled"/>
					<input type="hidden" class="input" name="purviewObject" id="purviewObject"  />
				</td>
			</tr>
			<tr>
				<td class="tit">企业：</td>
				<td class="cnt">
					<input id="companyNames" type="text" class="input" name="companyNames" placeholder="请选择"  onclick="companyTree('companyIds', 'companyNames', true)"/>
					<input type="hidden" class="input" name="companyIds" id="companyIds"  />
				</td>
			</tr>
			<tr>
				<td class="tit">事项目录：</td>
				<td class="cnt" style="height: 170px"><div id="catalogTree" class="TreeViewLite" ExpandLevel="1" style="height: 170px;overflow-y:scroll;" CheckBoxName="chkT1" CheckChildren="true" CheckBoxValue="{navId}"
						 URLNodeData="{contextPath}/catalog/queryUmsCatalogList.action?t={JSTime}&parentCode={navId}" AutoFocusFirst="true" TextField="{navName}" IconPath="{SYSURL.static}/images/icon16/" TreeStyle="Icon_Folder">
						<xmp class="data">{data:[{ navCode:'ROOT' ,navId:'ROOT', navName:'事项目录', childUrl:'{URLNodeData}'}]};</xmp>
					</div>
					<input type="hidden" class="input" name="catalogIds" id="catalogIds"  />
				</td>
				<td class="tit">会议类型：</td>
				<td class="cnt" style="height: 170px"><div id="meetingTypeTree" class="TreeViewLite" ExpandLevel="1" style="height: 170px;overflow-y:scroll;" CheckBoxName="chkT2" CheckChildren="true" CheckBoxValue="{navId}"
						 URLNodeData="{contextPath}/authMeetingType/queryUmsMeetingTypeList.action?t={JSTime}" AutoFocusFirst="true" TextField="{navName}" IconPath="{SYSURL.static}/images/icon16/" TreeStyle="Icon_Folder">
						<xmp class="data">{data:[{ navCode:'ROOT' ,navId:'ROOT', navName:'会议类型', childUrl:'{URLNodeData}'}]};</xmp>
					</div>
					<input type="hidden" class="input" name="meetingTypeIds" id="meetingTypeIds"  />
				</td>
			</tr>
			<tr>
				<td class="tit">有效开始时间：</td>
				<td class="cnt">
                    <input type="text" class="Input_DateTime" containerid="divFixCnt" showtimetype="0"
                           shownow="" showtime="true" enterindex="1" id="startTime"
                           name="startTime" readonly="" onblur="sourceReceiveTimeBlur(this)"
                           errcheckcap="请输入正确的生效日期！" placeholder="请选择" ErrEmptyCap="会议时间不允许为空">
				</td>
				<td class="tit">有效结束时间：</td>
				<td class="cnt">
                    <input type="text" class="Input_DateTime" containerid="divFixCnt" showtimetype="0"
                           shownow="" showtime="true" enterindex="1" id="endTime"
                           name="endTime" readonly="" onblur="sourceReceiveTimeBlur(this)"
                           errcheckcap="请输入正确的过期日期！" placeholder="请选择" ErrEmptyCap="会议时间不允许为空">
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
<script type="text/javascript">
	var catalogIds = [];
	var meetingTypeIds = [];
	$("select#purviewType").change(function(){
		if($(this).val()==1){
			$("#objectName").attr("disabled",false);
			$("#objectName").val("");$("#purviewObject").val("");
			$("#objType").html("用户：");
			/*$("#objectName").attr("onclick","userTree('purviewObject', 'objectName', true)"); */
			document.getElementById("objectName").onclick = function(){
				userTree('purviewObject', 'objectName', true);
			}
		}else if($(this).val()==2){
			$("#objectName").attr("disabled",false);
			$("#objectName").val("");$("#purviewObject").val("");
			$("#objType").html("角色：");
			/*$("#objectName").attr("onclick","roleTree('purviewObject', 'objectName', true)");  */
			document.getElementById("objectName").onclick = function(){
				roleTree('purviewObject', 'objectName', true);
			}
		}else{
			$("#objectName").attr("disabled",false);
			$("#objectName").val("");$("#purviewObject").val("");
			$("#objType").html("部门：");
			/*$("#objectName").attr("onclick","funSelectTree_Org('purviewObject', 'objectName', true)");  */
			document.getElementById("objectName").onclick = function(){
				funSelectTree_Org('purviewObject', 'objectName', true);
			}
		}
	});
	
	function getCheckBoxIds(){
		
		$.each($('#catalogTree input[type=checkbox]:checked'),function(){
			catalogIds.push($(this).val());
		});
		$("#catalogIds").val(catalogIds);
		$.each($('#meetingTypeTree input[type=checkbox]:checked'),function(){
			meetingTypeIds.push($(this).val());
		});
		$("#meetingTypeIds").val(meetingTypeIds);
	}
	
	/**
	 * 加载会议类型
	 */
	function loadingMeetingType(){
	
		var dataURL = "{contextPath}/meetingType/queryList.action";
		var jPost= {};
		postJSON(dataURL,jPost,function(json,o){
			if (!json || json.errorCode != "0") {
				return jt.Msg.showMsg("获取数据失败！");
			} else {
				for(var i=0;i<json.data.length;i++){
					$("#meetingType").append("<option value='"+json.data[i].TYPE_ID+"'>"+json.data[i].TYPE_NAME+"</option>");	        	
		        }
			}
		},false);
	}
</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>