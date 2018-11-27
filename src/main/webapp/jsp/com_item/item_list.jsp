<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<title>事项目录</title>
	<link rel="stylesheet" href="<%=SYSURL_static%>/css/sidebar-menu.css" type="text/css"/>
    <script type="text/javascript" src="<%=SYSURL_static%>/js/echarts.js"></script>
    <style type="text/css">
	    .bg_meeting{width:auto;float: left;margin: 4px 0px;}
		.bg_meet_left{display: block;background: url(<%=SYSURL_static%>/images/tiol/btn_met_left.png);width: 6px;height: 32px;background-repeat: no-repeat;float: left;}
		.bg_meet_right{display: block;background: url(<%=SYSURL_static%>/images/tiol/btn_met_right.png);width: 6px;height: 32px;background-repeat: no-repeat;float: left;}
		.meeting_name_bg{background: #e5f4ff;border-top: #60a3e8 1px solid;border-bottom: #60a3e8 3px solid;float: left;height: 28px;line-height: 28px;padding:0px 4px;}
		.meeting_name_bg a{width: 100%;}
		.meetting_line{float: left;background:url(<%=SYSURL_static%>/images/tiol/meeting_line.png);width: 18px;display: block;height: 32px;margin-top: 6px;}
	</style>    
    
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
	function newItem(){
		if(curNavCode == null || curNavCode == '' || curNavCode == 'ROOT'){
			showMsg('请选择有目录编码的子目录。');
			return;
		}
		var sURL='{contextPath}/jsp/com_item/item_update.jsp?t={JSTime}&parId='+curNavID+'&itemCode='+curNavCode;
		top_showDialog('新增',sURL,'WinEdit',750,550);
	}
	
	//修改事项 @author 李晓军
	function editItem(itemId){
		var sURL='{contextPath}/jsp/com_item/item_update.jsp?t={JSTime}&itemId='+itemId;
		top_showDialog('修改',sURL,'WinEdit',750,550);
	}
	
	//删除 @author 李晓军
	function delItem(itemId){
		var arr=delItem_GetIDs(itemId,'txtID','请选择要删除的数据!','未上传数据将彻底删除,已上传数据删除后请上传文件,确定要删除吗？');
        if (arr.length==0) return;

        var sURL='{contextPath}/com_item/deleteGzwItem.action';
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
	
	//导入事项 @author 李晓军
	function importItem(){
		var sURL = '{contextPath}/com_item/toFileImportPage.action?fileType=SUMMARY';
		top_showDialog('数据导入',sURL,'WinEdit',550,350);
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
	
<body class="BodyTreeList" id="item_list">
<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div icon="import.png" id="importButton" onclick="importItem()">导入</div>
		<div icon="add.png" id="addButton" onclick="newItem('')">新建</div>
		<div icon="del.png" id="delButton" onclick="delItem('')">删除</div>
		<div icon="export.png" id="uploadButton" onclick="uploadItem('')">上传</div>
	</div>
	<div id="divSitebar">
		<div id="divCurPosition">当前位置 : </div>
	</div>
</div>
<div id="divFixCnt" class="GridOnly" >
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
					   URLData="{contextPath}/com_item/queryItemListByPage.action?t={JSTime}&recycle=0&navLevel={navLevel}&catalogCode={curNavCode}&catalogId={curNavID}&currentPage={page}&pageSize={pageSize}&{searchParam}" EmptyInfo="没有记录" width="100%" AutoLoadData="false">
				<col boxName="txtID" width="30" boxValue="{itemId}" boxAttr="{boxAttr}" align="center">
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
</div>
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
<script src="<%=SYSURL_static%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=SYSURL_static%>/js/select.js"></script>
<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/leader/leader_item_detail.js"></script>

