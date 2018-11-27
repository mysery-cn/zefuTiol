<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<title>事项目录</title>
</head>
<script type="text/javascript">
	var curNavID = "";
	var curNavCode = "";
	var navLevel = "";
	function jtParseURL_Page(sURL){
		if(curNavCode == "undefind"){
			curNavCode = "";
		}
		if(curNavID == "undefind"){
			curNavID = "";
		}
		sURL=sURL.replace(/\{curNavID\}/ig, curNavID);
		sURL=sURL.replace(/\{curNavCode\}/ig, curNavCode);
		sURL=sURL.replace(/\{navLevel\}/ig, navLevel);
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
		navLevel = oNode.jsonItem.navLevel;
		jt._('#tabMain_List').loadData();
	}
	
	//添加事项 @author 李晓军
	function newItem(catalogId,curNavCode){
		var sURL='{contextPath}/jsp/item/qyitem_update.jsp?t={JSTime}&parId='+catalogId.curNavID+'&itemCode='+curNavCode.curNavCode;
		top_showDialog('新增',sURL,'WinEdit',750,550);
	}
	
	//修改事项 @author 李晓军
	function editItem(itemId){
		var sURL='{contextPath}/jsp/item/qyitem_update.jsp?t={JSTime}&itemId='+itemId;
		top_showDialog('修改',sURL,'WinEdit',750,550);
	}
	
	//删除 @author 李晓军
	function delItem(itemId){
		var arr=delItem_GetIDs(itemId,'txtID','请选择要删除的数据!','确实要删除吗？');
        if (arr.length==0) return;

        var sURL='{contextPath}/item/deleteGzwItem.action';
        postJSON(sURL, {'ids':arr.toString()},function (json,o){
            if (json.errorCode!=0){ showMsg(json.errorInfo,'操作失败',2000); return; }
            _('#tabMain_List').loadData();
        });
	}
	
	//导入事项 @author 李晓军
	function importItem(){
		var sURL = '{contextPath}/item/toFileImportPage.action?fileType=SUMMARY';
		top_showDialog('数据导入',sURL,'WinEdit',550,350);
	}
</script>
	
<body class="BodyTreeList">
<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div icon="import.png" id="importButton" onclick="importItem()">导入</div>
		<div icon="add.png" id="addButton" onclick="newItem({curNavID},{curNavCode})">新建</div>
		<div icon="del.png" id="delButton" onclick="delItem('')">删除</div>
	</div>
	<div id="divSitebar">
		<!-- 
		<div id="divCurPosition">当前位置 : 系统管理 &gt;&gt; 系统配置 &gt;&gt; 导航链接池</div>
		<div id="divGridSearch" grid="tabMain_List" InputTip="模块查询" ></div>
		 -->
	</div>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
	<tr>
		<td width="180">
			<div id="divFixLeft">
				<div id="divTree" class="TreeViewLite" ExpandLevel="1"
					 URLNodeData="{contextPath}/catalog/queryUmsCatalogList.action?t={JSTime}&parentCode={navId}" AutoFocusFirst="true" TextField="{navName}" IconPath="{SYSURL.static}/images/icon16/" TreeStyle="Icon_Folder">
					<xmp class="data">{data:[{ navCode:'ROOT' ,navId:'ROOT', navLevel:'0' ,navName:'事项目录', childUrl:'{URLNodeData}'}]};</xmp>
				</div>
			</div>
		</td>
		<td class="SplitBar" width="3"><span></span></td>
		<td>
			<div id="divFixMain" class="GridOnly">
				<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0" Action="editItem('{itemId}')"
					   URLData="{contextPath}/item/queryItemListByPage.action?t={JSTime}&navLevel={navLevel}&catalogCode={curNavCode}&catalogId={curNavID}&currentPage={page}&pageSize={pageSize}&{searchParam}" EmptyInfo="没有记录" width="100%" AutoLoadData="false">
				<col boxName="txtID" width="30" boxValue="{itemId}" boxAttr="{boxAttr}">
				<col caption="序号" field="{RN}" width="20" align="center">
				<col caption="事项名称" field="{itemName}" width="50" align="center">
				<col caption="事项编码" field="{itemCode}" width="50" align="center">
				<col caption="决策会议及顺序" field="{meetingDetail}" width="180" align="left">
				</table>
			</div>
		</td>
	</tr>
</table>
	
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
