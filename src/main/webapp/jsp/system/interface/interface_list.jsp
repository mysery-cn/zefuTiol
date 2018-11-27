<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<title>调度服务器管理</title>
</head>
<script type="text/javascript">
function editMethod(id){
    top_showDialog('修改服务接口','{baseURL}/jsp/system/interface/interface_update.jsp?t={JSTime}&id='+id,'WinEdit',600,500);
}
function addMethod(){
   top_showDialog('新增服务接口','{baseURL}/jsp/system/interface/interface_update.jsp?t={JSTime}&id','WinEdit',600,500);
}

// function editItem(sID,desktopType,editable){
//     var iW=900;//parseInt(top.document.body.clientWidth*0.9);
//     var iH=550;//parseInt(top.document.body.clientHeight*0.9);
//     if (sID==''){
//         top_showDialog('新增/修改','{contextPath}/jsp/system/uiScheme_update.jsp?t={JSTime}'+'&editable='+editable,'WinEdit',iW,iH);
//     }else{
//         top_showDialog('新增/修改','{contextPath}/jsp/system/uiScheme_info.jsp?t={JSTime}&id='+sID+'&deskType='+desktopType+'&editable='+editable,'WinEdit',iW,iH);
//     }
// }

function delMethod(){
   var arr = jt.getCheckBoxValue('txtID',false,true);
   if(arr.length<1){
      	jtAlert('请选择要删除的数据');
  		return ;
   }
   if (!confirm('确实要删除吗?')) return;
   var sURL= contextPath+'/serviceInterface/deleteInterface.action';
	postJSON(sURL, {'ids':arr.toString()},function (json,o){
		if (json.errorCode==0){
			reloadFrameMainGrid();
		}else{
			showMsg(json.errorInfo,'操作失败');
		}
	});
}
</script>
	
<body class="BodyList">

<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div icon="add.png" onclick="addMethod()">新增</div>
		<div icon="del.png" onclick="delMethod()">删除</div>
	</div>
	<div id="divSitebar">
		<div id="divCurPosition">当前位置 : 服务接口 &gt;&gt; 服务接口配置</div>
		<div id="divGridSearch" grid="tabMain_List" InputTip="查询" ></div>
	</div>
</div>
<div id="divFixCnt" class="GridOnly" >
	<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0" width="100%"
			URLData="{contextPath}/serviceInterface/queryInterfaceByPage.action?currentPage={page}&pageSize={pageSize}&{searchParam}" Action="editMethod('{ID}')" >
			<col boxName="txtID" width="30" style="text-align:center;" boxValue="{INTERFACE_ID}">
			<col caption="接口名称" field="{INTERFACE_NAME}" width="30%">
			<col caption="接口类型" field="{INTERFACE_TYPE}" width="30%">
			<col caption="接口地址" field="{INTERFACE_URL}" >
	</table>
</div>
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
