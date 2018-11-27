<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/addPlugin/ums_url.js"></script>
	<link rel="stylesheet" href="<%=SYSURL_static%>/css/item_form.css" type="text/css"/>
	<title>事项目录</title>
</head>
<script type="text/javascript">
	//debugger;
	//父节点ID
	var catalogPid = jt.getParam("parId");
	var itemId = jt.getParam("itemId");
	var itemCode = jt.getParam("itemCode");
	//会议类型json对象数组
	var meetingTypesJson;
	//select控件ID数
	var selectNum = 0;
	
	var status;
	if (itemId == null || itemId == ""){
		status = 1;
	}else{
		status = 2;
	}
	
	//页面加载完成初始调用
	$(document).ready(function(){
		listMeetingType();
	});
	
	function initEditPageBefore(){
		if (itemId != null && itemId != ""){
			var dataURL = "{contextPath}/item/queryItem.action?itemId=" + itemId;
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
						jt._('#legalFlag').value = json.data[0].legalFlag;
						if(jt._('#legalFlag').value==1){
							$("input[name=legalFlag]:eq(0)").attr("checked",'checked'); 
						}else{
							$("input[name=legalFlag]:eq(1)").attr("checked",'checked'); 
						}
						jt._('#remark').value = json.data[0].remark;
						listMeetingType();
						listMeetingTypeSelect(json.data[0].meetingTypeList,meetingTypesJson);
						
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
		for (var i=0;i<objs.length;i++) { 
			try{ 
				objs[i].getValue(); 
			}catch(e){} 
			
		}
		var sURL;
		if(status == 1){
			sURL = actSave;
		}else{
			sURL = actUpdate;
		}
		top.showLoading('正在保存...');

		var from_data = Form2Json(aForm);
		postJSON(sURL, from_data, function (json, o) {
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
	
	//初始化表单数据
	function initFormValueAfter(json){
		if (catalogPid != null && catalogPid != ""){
			jt._('#catalogId').value = catalogPid;
		}
		if (itemCode != null && itemCode != ""){
			jt._('#itemCode').value = itemCode + "-";
		}
	}
	
	//动态增加会议类型选择框
	function add_selectbox(selectBox){
		var div = document.getElementById("meeting");
		var flDiv = document.createElement("div");
		flDiv.setAttribute("class", "fl");
		var selectDiv = document.createElement("div");
		selectDiv.setAttribute("class", "select_diy");
		var select; 
		if(selectBox){
			select = selectBox;
		}else{
			select = initMeetintSelect(meetingTypesJson);
		}
        /*
		var delBtn = document.createElement("button");
       	delBtn.innerHTML = "删除";
       	delBtn.onclick = function(){ div.removeChild(flDiv) };
        */
        var delDiv = document.createElement("div");
        delDiv.onclick = function(){ 
        	div.removeChild(flDiv);
        	div.removeChild(delDiv);
        	
        };
        delDiv.className = "del_meetting";
        
        selectDiv.appendChild(select);
       	flDiv.appendChild(selectDiv);
        div.appendChild(flDiv);
        div.appendChild(delDiv);
	}
	
	//获取会议类型
	function listMeetingType(){
		var dataURL = "{contextPath}/item/queryMeetingType.action";
		var jPost= {};
		postJSON(dataURL,jPost,function(json,o){
			if (!json || json.errorCode != "0") {
				return jt.Msg.showMsg("获取数据失败！");
			} else {
				if(json.data.length > 0){
					meetingTypesJson = json.data;
				}
			}
		},false);
	}
	
	//初始会议类型选中框
	function initMeetintSelect(datas,selectedId){
		var select = document.createElement("select");
		select.name = "meetingTypeIds";
		selectNum = selectNum + 1;
		select.id = "selectNum" + selectNum;
		select.options[0] = new Option("请选择", "");
		var i = 1;
		for (key in datas){
			if(!isNaN(key)){
				select.options[i] = new Option(datas[key].typeName, datas[key].typeId);
				if(selectedId){
					if(datas[key].typeId == selectedId){
						select.options[i] = new Option(datas[key].typeName, datas[key].typeId,false,true);
					}
				}
				i++;
			}
		}
		select.style = "width:100%;margin:0px 3.5px 0px 0px;";
		return select;
	}
	
	//加载已保存的会议类型
	function listMeetingTypeSelect(savedDatas,datas){		
		for (key in savedDatas){
			if(!isNaN(key)){
				var selectBox = initMeetintSelect(datas,savedDatas[key].typeId);				
				add_selectbox(selectBox);
			}
		}
	}
	
	//[Func] Form中的元素转JSON，方便Ajax提交 
	//重写该方法支持控件名称一样时传值，以";"分割 @author 李晓军
	Form2Json = function (aForm){
		var jPost={};
		for(var i=0;i<aForm.elements.length;i++){
			var obj=aForm.elements[i];
			if ((obj.name=='') || (obj.disabled) ) continue;
			if (jt.getAttr(obj,'NotSubmit',false)) continue;
			var bCheckBox=false;
			if (/input/i.test(obj.nodeName)) {
				if (obj.type=='checkbox') {
					if (!obj.checked) continue;
					bCheckBox=true;
				};
				if ( (obj.type=='radio') && (!obj.checked) ) continue;
			};
			if (bCheckBox) {
				if (typeof(jPost[obj.name])!='undefined') continue;
				jPost[obj.name] = jt.getCheckBoxValue(obj.name,true,false,true);
			}else{
				//处理同名控件传值
				if(jPost[obj.name] == null){
					jPost[obj.name] = obj.value;
				}else{
					jPost[obj.name] = jPost[obj.name] + ';' + obj.value;
				}
			};
		};
		return jPost;
	};
</script>

<body class="BodyEdit" pageType="EditPage"
	  actPath="{contextPath}/item/"
	  actSave="saveItem.action"
	  actUpdate="updateItem.action">
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
				<td class="tit">事项清单名称：</td>
				<td class="cnt"><input type="text" class="input" name="itemName" ErrEmptyCap="事项名称不允许为空"/></td>
			</tr>
			<tr>
				<td class="tit">事项清单编码：</td>
				<td class="cnt">
					<input id="itemCode" type="text" class="input" name="itemCode" maxlength="64" />
					<input type="hidden" class="input" name="catalogId" id="catalogId" maxlength="64" />
					<input type="hidden" class="input" name="itemId" id="itemId" maxlength="64"   />
					<input type="hidden" class="input" name="fid" id="fid" maxlength="64"   />
				</td>
			</tr>
			<tr>
				<td class="tit">是否需经法律审核：</td>
				<td class="cnt" nowrap="nowrap">
				 	<input type="radio" value="1" name="legalFlag">是
				 	<input type="radio" value="0" name="legalFlag">否
				</td>
			</tr>
			<tr>
				<td class="tit">决策会议：</td>
				<td class="cnt">
					<div id="meeting">
					</div>
					<div class="fl">
					<div onclick="add_selectbox()" class="add_meetting"></div>
					</div>
				</td>
			</tr>
			<tr>
				<td class="tit">备注：</td>
				<td class="cnt">
					<textarea rows="5" cols="7" name="remark">${remark}</textarea>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>