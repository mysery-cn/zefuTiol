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
			var dataURL = "{contextPath}/com_item/queryItem.action?itemId=" + itemId;
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
						jt._('#oitemCode').value = json.data[0].itemCode;
						jt._('#oitemName').value = json.data[0].itemName;
						jt._('#legalFlag').value = json.data[0].legalFlag;
						
						
						if(jt._('#legalFlag').value==1){
							$("input[name=legalFlag]:eq(0)").attr("checked",'checked'); 
						}else{
							$("input[name=legalFlag]:eq(1)").attr("checked",'checked'); 
						}
						$("input[name=legalFlag]:eq(0)").attr("value",'1');
						$("input[name=legalFlag]:eq(1)").attr("value",'0');

						if(json.data[0].remark) {
                            jt._('#remark').value = json.data[0].remark;
                        }

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
		if(!isItemCodeExistes()){
			$("#itemCode").focus();
			return;
		} 
		if(!isItemNameExistes()){
			$("#itemName").focus();
			return;
		}
		
		if(selectNum <= 0){
			showMsg("请添加决策会议");
			return;
		}
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
					reloadFrameMainGrid(aForm);
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
		flDiv.className = "fl";
		var selectDiv = document.createElement("div");
		selectDiv.className = "select_diy";
		var select; 
		if(selectBox){
			select = selectBox;
		}else{
			select = initMeetintSelect(meetingTypesJson);
		}
		var groupDiv = document.createElement("div");
        /*
		var delBtn = document.createElement("button");
       	delBtn.innerHTML = "删除";
       	delBtn.onclick = function(){ div.removeChild(flDiv) };
        */
        var delDiv = document.createElement("div");
        delDiv.onclick = function(){ 
        	//div.removeChild(flDiv);
        	//div.removeChild(delDiv);
        	div.removeChild(groupDiv);
        	selectNum = selectNum - 1;
        	
        };
        delDiv.className = "del_meetting";
        
        selectDiv.appendChild(select);
       	flDiv.appendChild(selectDiv);
       	//flDiv.appendChild(delDiv);
       	
       	groupDiv.appendChild(flDiv);
       	groupDiv.appendChild(delDiv);
       	
       	//div.appendChild(flDiv);
       	//div.appendChild(delDiv);
       	div.appendChild(groupDiv);
	}
	
	//获取会议类型
	function listMeetingType(){
		var dataURL = "{contextPath}/com_item/queryMeetingType.action";
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
		//select.options[0] = new Option("请选择", "");
		var i = 0;
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
		//select.style = "width:100%;margin:0px 3.5px 0px 0px;";
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
	
	//判断事项编码是否重复
	function isItemCodeExistes(){
		var flag = true;
		var nitemCode = $('input[name="itemCode"]').val();
		var oitemCode = $('#oitemCode').val();
		var baseItemCode = $('#baseItemCode').val();
        if (nitemCode) {
        	
        	//转成大写
        	$('input[name="itemCode"]').val(nitemCode.toUpperCase());
        	if(status == 1){
        		var count = nitemCode.split(itemCode).length-1;
        		var sCount = nitemCode.split("-").length-1;
            	if(nitemCode.indexOf(itemCode) != 0 || count != 1 || sCount != 1){
            		 jt.Msg.showMsg('事项编码格式不符合标准,格式是:"' + itemCode + '-NNN"' );
    				 flag = false;
    				 return flag;
            	}
        	}else{
        		var cIndex = oitemCode.indexOf("-");
        		itemCode = oitemCode.substring(0,cIndex);
        		var count = nitemCode.split(itemCode).length-1;
        		var sCount = nitemCode.split("-").length-1;
            	if(nitemCode.indexOf(itemCode) != 0 || count != 1 || sCount != 1){
            		 jt.Msg.showMsg('事项编码格式不符合标准,格式是:"' + itemCode + '-NNN"' );
    				 flag = false;
    				 return flag;
            	}
        	}
        	var dataURL = "{contextPath}/com_item/isExistsItem.action";
    		var jPost= {"itemCode":nitemCode,"oitemCode":oitemCode};
    		postJSON(dataURL,jPost,function(json,o){
    			if (json.isExists == "1") {
    				 jt.Msg.showMsg('事项编码已存在！')
    				 flag = false;
    			}else{
    				flag = true;
    			}
    		},false);
        }
        return flag;
	}
	
	//判断事项名称是否重复
	function isItemNameExistes(){
		var flag = true;
		var itemName = $('input[name="itemName"]').val();
		var oitemName = $('#oitemName').val();
        if (itemName) {
        	var dataURL = "{contextPath}/com_item/isExistsItem.action";
    		var jPost= {"itemName":itemName,"oitemName":oitemName};
    		postJSON(dataURL,jPost,function(json,o){
    			if (json.isExists == "1") {
    				 jt.Msg.showMsg('事项名称已存在！')
    				 flag = false;
    			}else{
    				flag = true;
    			}
    		},false);
        }
        return flag;
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
				}
				if ( (obj.type=='radio') && (!obj.checked) ){ 
					continue;
				}
			}
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
			}
		}
		return jPost;
	};
	
	function reloadFrameMainGrid1(aEditForm) {
		var aWin = getFrameMain();
		if (typeof(aWin.funReloadMainGrid) == 'function') { aWin.funReloadMainGrid(aEditForm); return; }
		try {
			aWin._('#tabMain_List').loadData();
		} catch (e) {
	        try {
	            aWin.initDesktop();
	        }catch (e) {
	            try{
	                aWin.location.reload();
	            }catch (e) { }
	        }
		}
	}
</script>

<body class="BodyEdit" pageType="EditPage"
	  actPath="{contextPath}/com_item/"
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
				<td class="cnt"><input type="text" class="input" id="itemName" name="itemName" onblur="isItemNameExistes()" ErrEmptyCap="事项名称不允许为空" ErrLength="170"/></td>
			</tr>
			<tr>
				<td class="tit">事项清单编码：</td>
				<td class="cnt">
					<input id="itemCode" type="text" class="input" name="itemCode" maxlength="64" ErrEmptyCap="事项清单编码不允许为空" ErrLength="64"
					onblur="isItemCodeExistes()" ErrCheckCap="输入不符合要求！" />
					<input type="hidden" class="input" name="catalogId" id="catalogId" maxlength="64" />
					<input type="hidden" class="input" name="itemId" id="itemId" maxlength="64"   />
					<input type="hidden" class="input" name="fid" id="fid" maxlength="64"   />
					<input type="hidden" class="input" id="oitemName" />
	   	 			<input type="hidden" class="input" id="oitemCode" />
	   	 			<input type="hidden" class="input" id="baseItemCode" />
				</td>
			</tr>
			<tr>
				<td class="tit">是否需经法律审核：</td>
				<td class="cnt" nowrap="nowrap">
				 	<input type="radio" value="1" name="legalFlag">是
				 	<input type="radio" value="0" name="legalFlag" checked="checked">否
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
					<textarea rows="5" cols="7" name="remark" ErrLength="85">${remark}</textarea>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>