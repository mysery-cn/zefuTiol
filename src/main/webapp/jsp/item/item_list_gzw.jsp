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
		sURL=sURL.replace(/\{curNavID\}/ig, curNavID);
		return sURL;
	}
	
	function jtAfterTreeViewLiteNodeFocus (oComp, oNode){
		curNavID = oNode.jsonItem.navId;
		curNavCode = oNode.jsonItem.navCode;
		jt._('#tabMain_List').loadData();
	}
	
	function editItem(itemId){
		var sURL='{contextPath}/jsp/item/item_update_gzw.jsp?t={JSTime}&itemId='+itemId;
		if (itemId=='') sURL += '&parId='+curNavID ;
		top_showDialog('新增/修改',sURL,'WinEdit',750,550);
	}

	function delItem(sID){
		var arr=delItem_GetIDs(sID,'txtID','请选择要删除的数据!','确实要删除吗？');
		if (arr.length==0) return;
		var sURL='{contextPath}/item/deleteGzwItem.action';
		postJSON(sURL, {'ids':arr.toString()},function (json,o){
			if (json.errorCode!=0){ showMsg(json.errorInfo,'操作失败'); return; }
			_('#tabMain_List').loadData();
		});
	}
	
</script>
	
<body class="BodyTreeList">
	
<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div icon="add.png" id="addButton" onclick="editItem('')">新建</div>
		<div icon="del.png" id="delButton" onclick="delItem('')">删除</div>
	</div>
</div>
	
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
	<tr>
		<td width="180">
			<div id="divFixLeft">
					<div id="divTree" class="TreeViewLite" ExpandLevel="1"
						 URLNodeData="{contextPath}/catalog/queryUmsCatalogList.action?t={JSTime}&parentCode={navId}" AutoFocusFirst="true" TextField="{navName}" IconPath="{SYSURL.static}/images/icon16/" TreeStyle="Icon_Folder">
						<xmp class="data">{data:[{ navCode:'ROOT' ,navId:'ROOT', navName:'事项目录', childUrl:'{URLNodeData}'}]};</xmp>
					</div>
			</div>
		</td>
		<td class="SplitBar" width="3"><span></span></td>
		<td>
			<div id="divFixMain" class="GridOnly">
				<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"  Action="editItem('{itemId}')"
					   URLData="{contextPath}/item/queryGzwItemList.action?t={JSTime}&catalogId={curNavID}&currentPage={page}&pageSize={pageSize}&{searchParam}" EmptyInfo="没有记录" width="100%" AutoLoadData="false">
					<col boxName="txtID" width="30" boxValue="{itemId}" boxAttr="{boxAttr}">
					<col caption="事项清单编码" field="{itemCode}" width="40px" align="center">
					<col caption="事项清单名称" field="{itemName}" width="100px" align="center">
				</table>
			</div>
		</td>
	</tr>
</table>
	
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
