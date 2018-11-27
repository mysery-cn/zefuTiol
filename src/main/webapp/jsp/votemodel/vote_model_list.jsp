<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<title>制度信息</title>
</head>
<script type="text/javascript">
    function init(){
        var role = curUser.adminRoleId;
        var roleList= curUser.roleListStr;
        //配置管理员及超级管理员可以修改
        if(role != "configAdminRole" && roleList.indexOf("SYS_9999") ==-1){
            $('#addButton').hide();
            $('#delButton').hide();
            $('#exportButton').hide();
            $('#exportButton').removeAttr('onclick');
            $('#addButton').removeAttr('onclick');//去掉标签中的onclick事件
            $('#delButton').removeAttr('onclick');//去掉标签中的onclick事件
        }

        //判断同步是否隐藏
        if(curUser.companyId == "GZW"){
            $('#exportButton').hide();
        }
    }
	function editItem(modelId){
		var sURL='{contextPath}/jsp/votemodel/vote_model_update.jsp?t={JSTime}&modelId='+modelId;
		top_showDialog('新增/修改',sURL,'WinEdit',750,550);
	}
	
	function delItem(sID){
		var arr=delItem_GetIDs(sID,'txtID','请选择要删除的数据!','确实要删除吗？');
		if (arr.length==0) return;
		var sURL='{contextPath}/vote/model/deleteVoteModel.action';
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
            <!-- <div icon="export.png" id="exportButton"  onclick="synchronous('source.xml')">同步</div> -->
		</div>
	</div>
	<div id="divFixCnt" class="GridOnly" >
		<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0"	cellspacing="0" cellpadding="0" 
			URLData="{SYSURL.oa}/vote/model/queryVoteModelList.action?t={JSTime}&currentPage={page}&pageSize={pageSize}&{searchParam}"
			EmptyInfo="没有记录" width="100%" Action="editItem('{modelId}')">
			<col boxName="txtID" width="30" boxValue="{modelId}" boxAttr="{boxAttr}">
			<col caption="表决方式名称" field="{modelName}" align="center" width="50px">
			<col caption="表决计算基数" field="{modelRadix}" align="center" width="50px">
			<col caption="表决通过比例" field="{modelRate}" align="center" width="50px">
		</table>
	</div>
</body>
<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>