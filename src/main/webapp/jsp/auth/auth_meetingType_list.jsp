<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<title>事项目录</title>
</head>
<script type="text/javascript">
	var curNavID = "";
	var curNavCode = "";
	function jtParseURL_Page(sURL){
		if(curNavCode == "undefind"){
			curNavCode = "";
		}
		sURL=sURL.replace(/\{curNavID\}/ig, curNavID);
		return sURL;
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
					$("#delButton").css('display','none'); 
				}
			}
		},false);
	}
	
	function jtAfterTreeViewLiteNodeFocus (oComp, oNode){
		curNavID = oNode.jsonItem.navId;
		curNavCode = oNode.jsonItem.navCode;
		jt._('#tabMain_List').loadData();
	}
	
	function editItem(typeId){
		var sURL='{contextPath}/jsp/auth/auth_meetingType_update.jsp?t={JSTime}&typeId='+typeId;
		if (typeId=='') sURL += '&parId='+curNavID ;
		top_showDialog('新增/修改',sURL,'WinEdit',750,550);
	}

	function delItem(sID){
		var arr=delItem_GetIDs(sID,'txtID','请选择要删除的数据!','确实要删除吗？');
		if (arr.length==0) return;
		var sURL='{contextPath}/catalog/deleteCatalog.action';
		postJSON(sURL, {'ids':arr.toString()},function (json,o){
			if (json.errorCode!=0){ showMsg(json.errorInfo,'操作失败'); return; }
			_('#tabMain_List').loadData();
		});
	}
	
	function jtInitJtDataItem (oComp, jsonItem, idx){
		jsonItem.showIcon = jt.getDefVal(jsonItem.navIcon)==''?'':'<img src="'+SYSURL.static+'/images/icon_biz/'+jsonItem.navIcon+'" align="absmiddle" onload="imgMax(this)"> ';
	}
	
	function imgMax(img){ 
		if (img.offsetWidth>24){ img.width=24; } 
	}
</script>
	
<body class="BodyTreeList">
	
<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div icon="add.png" id="addButton" onclick="editItem('')">新建</div>
	</div>
	<div id="divSitebar">
		<div id="divCurPosition">当前位置 : 系统管理 &gt;&gt; 系统配置 &gt;&gt; 导航链接池</div>
		<div id="divGridSearch" grid="tabMain_List" InputTip="模块查询" ></div>
	</div>
</div>
	
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
	<tr>
		<td width="180">
			<div id="divFixLeft">
					<div id="divTree" class="TreeViewLite" ExpandLevel="1"
						 URLNodeData="{contextPath}/authMeetingType/queryUmsMeetingTypeList.action?t={JSTime}" AutoFocusFirst="true" TextField="{navName}" IconPath="{SYSURL.static}/images/icon16/" TreeStyle="Icon_Folder">
						<xmp class="data">{data:[{ navCode:'ROOT' ,navId:'ROOT', navName:'会议类型', childUrl:'{URLNodeData}'}]};</xmp>
					</div>
			</div>
		</td>
		<td class="SplitBar" width="3"><span></span></td>
		<td>
			<div id="divFixMain" class="GridOnly">
				<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"  Action="editItem('{navId}')"
					   URLData="{contextPath}/authMeetingType/queryAuthMeetingTypeList.action?t={JSTime}&typeId={curNavID}&currentPage={page}&pageSize={pageSize}&{searchParam}" EmptyInfo="没有记录" width="100%" AutoLoadData="false">
					<col boxName="txtID" width="30" boxValue="{navId}" boxAttr="{boxAttr}">
					<col caption="关联名称" field="{relationName}" width="50px" align="center">
					<col caption="关联类型" field="[=formatStatus('{relationType}')]" width="50px" align="center" UseAction="1">
				</table>
			</div>
		</td>
	</tr>
</table>
</body>
	<script type="text/javascript">
		function formatStatus(status){
	        if(status=="1")return '用户';
	        if(status=="2")return '角色';
	        if(status=="3")return '群组';
	    }
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
