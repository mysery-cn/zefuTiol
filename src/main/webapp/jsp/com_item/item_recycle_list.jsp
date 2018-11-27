<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<title>事项目录</title>
	<link rel="stylesheet" href="<%=SYSURL_static%>/css/index.css" type="text/css"/>
	<link rel="stylesheet" href="<%=SYSURL_static%>/css/sidebar-menu.css" type="text/css"/>
    <script type="text/javascript" src="<%=SYSURL_static%>/js/echarts.js"></script>
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
		var sURL='{contextPath}/jsp/com_item/item_update.jsp?t={JSTime}&parId='+catalogId.curNavID+'&itemCode='+curNavCode.curNavCode;
		top_showDialog('新增',sURL,'WinEdit',750,550);
	}
	
	//修改事项 @author 李晓军
	function editItem(itemId){
		var sURL='{contextPath}/jsp/com_item/item_readonly.jsp?t={JSTime}&itemId='+itemId;
		top_showDialog('修改',sURL,'WinEdit',750,550);
	}
	
	//删除 @author 李晓军
	function delItem(itemId){
		var arr=delItem_GetIDs(itemId,'txtID','请选择要删除的数据!','确实要删除吗？');
        if (arr.length==0) return;

        var sURL='{contextPath}/com_item/removeItem.action';
        postJSON(sURL, {'ids':arr.toString()},function (json,o){
            if (json.errorCode!=0){ 
            	showMsg(json.errorInfo,'操作失败',2000); 
            	return; 
            }else{
            	showMsg('删除成功！');
            	_('#tabMain_List').loadData();
            }
        });
	}
	
	//还原 @author 李晓军
	function restoreItem(itemId){
		var arr=delItem_GetIDs(itemId,'txtID','请选择要还原的数据!','确实要还原吗？');
        if (arr.length==0) return;

        var sURL='{contextPath}/com_item/restoreItem.action';
        postJSON(sURL, {'ids':arr.toString()},function (json,o){
            if (json.errorCode!=0){ 
            	showMsg(json.errorInfo,'操作失败',2000); 
            	return; 
            }else{
            	showMsg('还原成功！');
            	_('#tabMain_List').loadData();
            }
        });
	}
	
	//上传事项 @author 李晓军
	function uploadItem(itemId){
		var arr=delItem_GetIDs(itemId,'txtID','请选择要上传的数据!','确实要上传吗？');
        if (arr.length==0) return;

        var sURL='{contextPath}/item/uploadItem.action';
        postJSON(sURL, {'ids':arr.toString()},function (json,o){
        	console.log(json);
            if (json.errorCode!=0){ 
            	showMsg(json.errorInfo,'操作失败',2000); return; 
            }else{
            	jt.Msg.alert(json.errorInfo);
            }
            _('#tabMain_List').loadData();
        });
	}
	
</script>
	
<body class="BodyTreeList">
<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div icon="export2.png" id="addButton" onclick="restoreItem('')">还原</div>
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
					   URLData="{contextPath}/com_item/queryItemListByPage.action?t={JSTime}&recycle=1&navLevel={navLevel}&catalogCode={curNavCode}&catalogId={curNavID}&currentPage={page}&pageSize={pageSize}&{searchParam}" EmptyInfo="没有记录" width="100%" AutoLoadData="false">
				<col boxName="txtID" width="30" boxValue="{itemId}" boxAttr="{boxAttr}">
				<col caption="事项名称" field="{itemName}" width="50" align="center">
				<col caption="事项编码" field="{itemCode}" width="50" align="center">
				<col caption="决策会议及顺序" field="[=formatStatus('{meetingDetail}')]" width="180" align="left">
				<col caption="上传状态" field="{uploadStatus?0|待上传|1|已上传|2|再次等待上传}" width="50" align="center">
				<col caption="操作类型" field="{status?0|删除|1|新增|2|修改}" width="50" align="center">
				</table>
			</div>
		</td>
	</tr> 
</table>
	
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
<script src="<%=SYSURL_static%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=SYSURL_static%>/js/select.js"></script>
<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/leader/leader_item_detail.js"></script>

