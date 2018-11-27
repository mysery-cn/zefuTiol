<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<title>制度信息</title>
</head>
<script type="text/javascript">
function queryData(){
	//制度类型
	var typeID = jt._("#typeID").value;
	var data = "{\"typeID\":\""+typeID+"\"}";
	//查询
	var sURL = "{SYSURL.oa}/com_regulation/querRegulationPageList.action?t={JSTime}&recycle=1&currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);;
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
}

function init(){
	//加载制度类型
	var dataURL = "{contextPath}/com_regulation/queryRegulationTypeList.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i<json.data.length;i++){
				$("#typeID").append("<option value='"+json.data[i].TYPE_ID+"'>"+json.data[i].TYPE_NAME+"</option>");
			}
		}
	},false);
}

//制度更新
function editRegulation(REGULATION_ID) {
    var href = "/jsp/com_regulation/regulation_update.jsp?regulationID=" + REGULATION_ID;
    top_showDialog("制度详情", href, 'WinEdit', 940, 700);
}


//删除 @author 李晓军
function delRegulation(itemId){
	var arr=delItem_GetIDs(itemId,'txtID','请选择要删除的数据!','确实要删除吗？');
    if (arr.length==0) return;

    var sURL='{contextPath}/com_regulation/delete.action';
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
function restoreRegulation(itemId){
	var arr=delItem_GetIDs(itemId,'txtID','请选择要还原的数据!','确实要还原吗？');
    if (arr.length==0) return;

    var sURL='{contextPath}/com_regulation/restore.action';
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

</script>
<body class="BodyList">
	<div id="divFixTop">
		<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
			<div icon="export2.png" id="" onclick="restoreRegulation()">还原</div>
			<div icon="del.png" id="delButton" onclick="delRegulation('')">删除</div>
		</div>
		<div id="divSitebar">
			<div id="divCurPosition">当前位置 : </div>
		</div>
	</div>
	<div id="divSearch" ShowCount="0" >
	  <table>
	  	<tr>
	  		<td style="width: 5%" align="right">制度类型:</td>
	  		<td style="width: 10%">
	  			<select id="typeID" style="width: 80%">
	  				<option value="">请选择</option>
	  			</select>
	  		</td>
	  		<td style="width: 30%">
	  			<input type="button" class="button" id="searchBtn" value="搜索" onclick="queryData()" />
	  		</td>
	  	</tr>
	  </table>
	</div>
	<div id="divFixCnt" class="GridOnly" >
		<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0"	cellspacing="0" cellpadding="0" 
			URLData="{SYSURL.oa}/com_regulation/querRegulationPageList.action?t={JSTime}&recycle=1&currentPage={page}&pageSize={pageSize}&{searchParam}"
			EmptyInfo="没有记录" width="100%" Action="editRegulation('{REGULATION_ID}')">
			<col boxName="txtID" width="30" boxValue="{REGULATION_ID}" boxAttr="{boxAttr}">
			<col caption="企业名称" field="{COMPANY_SHORT_NAME}" align="center" width="50px">
			<col caption="制度名称" field="{REGULATION_NAME}" align="center" width="100px">
			<col caption="制度类型" field="{TYPE_NAME}" align="center"  width="50px">
			<col caption="审批时间" field="{APPROVAL_DATE}" align="center" width="30px">
			<col caption="生效时间" field="{EFFECTIVE_DATE}" align="center" width="30px">
			<col caption="上传状态" field="{uploadStatus?0|待上传|1|已上传|2|再次等待上传}" width="50" align="center">
			<col caption="操作类型" field="{status?0|删除|1|新增|2|修改}" width="50" align="center">
		</table>
	</div>

</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>