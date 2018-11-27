<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<title>制度信息</title>
</head>
<script type="text/javascript">
	function init(){ 
		var dataURL = "{contextPath}/catalog/queryCatalogRole.action";
		var jPost= {};
		postJSON(dataURL,jPost,function(json,o){
			if (!json || json.errorCode != "0") {
				return jt.Msg.showMsg("获取数据失败！");
			} else {
				if(json.data[0] == 0){
					$("#addButton").css('display','none'); 
				}
			}
		},false);
	}
	
	function editItem(purviewId){
		var sURL='{contextPath}/jsp/purview/purview_update.jsp?t={JSTime}&purviewId='+purviewId;
		top_showDialog('新增/修改',sURL,'WinEdit',850,450);
	}
	
	function delItem(sID){
		var arr=delItem_GetIDs(sID,'txtID','请选择要删除的数据!','确实要删除吗？');
		if (arr.length==0) return;
		var sURL='{contextPath}/purview/deletePurview.action';
		postJSON(sURL, {'ids':arr.toString()},function (json,o){
			if (json.errorCode!=0){ showMsg(json.errorInfo,'操作失败'); return; }
			_('#tabMain_List').loadData();
		});
	}

</script>
<body class="BodyList">
	<div id="divFixTop">
		<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
			<div icon="add.png" id="addButton" onclick="editItem('')">新建</div>
			<div icon="del.png" id="delButton" onclick="delItem('')">删除</div>
		</div>
	</div>
	<div id="divFixCnt" class="GridOnly" >
		<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0"	cellspacing="0" cellpadding="0" 
			URLData="{SYSURL.oa}/purview/queryPurviewList.action?t={JSTime}&currentPage={page}&pageSize={pageSize}&{searchParam}"
			EmptyInfo="没有记录" width="100%" Action="editItem('{PURVIEW_ID}')">
			<col boxName="txtID" width="30" boxValue="{PURVIEW_ID}" boxAttr="{boxAttr}">
			<col caption="排序" field="{RN}" align="center" width="20px">
			<col caption="权限类型" field="[=formatStatus('{PURVIEW_TYPE}')]" align="center" width="50px">
			<col caption="权限对象" field="{OBJECT_NAME}" align="center" width="50px">
			<col caption="会议类型数量" field="{meetingTypeCount}" align="center" width="50px">
			<col caption="事项目录数量" field="{catalogCount}" align="center" width="50px">
			<col caption="企业数量" field="{companyCount}" align="center" width="50px">
			<col caption="开始时间" field="{START_TIME}" align="center" width="50px">
			<col caption="结束时间" field="{END_TIME}" align="center" width="50px">
		</table>
	</div>

</body>
<script type="text/javascript">
	function formatStatus(status){
	    if(status==0)return '部门';
	    if(status==1)return '用户';
	    if(status==2)return '角色';
	}
</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>