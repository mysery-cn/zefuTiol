<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<title>制度信息</title>
</head>
<script type="text/javascript">
function queryData(){
	//企业名称
	var companyName = jt._("#companyName").value;
	//制度类型
	var typeID = jt._("#typeID").value;
	var data = "{\"companyName\":\""+companyName+"\",\"typeID\":\""+typeID+"\"}";
	//查询
	var sURL = "{SYSURL.oa}/regulation/querRegulationPageList.action?t={JSTime}&currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);;
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
}


function editRegulation(REGULATION_ID){
	if(curUser.orgLevel == 0){
		NavGoURL('{SYSURL_oa}/jsp/regulation/regulation_detail.jsp?regulationID='+REGULATION_ID);
	}else{
		var href = "/jsp/regulation/regulation_detail.jsp?regulationID="+REGULATION_ID;
		window.parent.addpage("制度详情",href,'regulationDetail'+REGULATION_ID);
	}
}

function resetBtn(){
	$("#companyName").val("");
	$("#typeID").val("");
}

</script>
<body class="BodyList">
	<div id="divFixTop">
		<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		</div>
		<div id="divSitebar">
			<div id="divCurPosition">当前位置 : </div>
		</div>
	</div>
	<div id="divSearch" ShowCount="0" >
	  <table>
	  	<tr>
	  		<td style="width: 5%" align="right">企业名称:</td>
	  		<td style="width: 10%">
	  			<input type="text" id="companyName" class="input" />
	  		</td>
	  		<td style="width: 5%" align="right">制度类型:</td>
	  		<td style="width: 10%">
				<select class="DropDown_Select" name="typeID" id="typeID" OptionText="{text}" OptionValue="{id}" URLData="{contextPath}/regulation/queryRegulationTypeList.action?t={JSTime}"  
				 DefaultIndex="0" FirstItem="{text:'请选择',id:''}"></select>
	  		</td>
	  		<td style="width: 20%">
	  			<input type="button" class="button" id="searchBtn" value="搜索" onclick="queryData()" />
	  			<input type="button" class="button" value="重置" onclick="resetBtn()" />
	  		</td>
	  	</tr>
	  </table>
	</div>
	<div id="divFixCnt" class="GridOnly" >
		<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0"	cellspacing="0" cellpadding="0" 
			URLData="{SYSURL.oa}/regulation/querRegulationPageList.action?t={JSTime}&currentPage={page}&pageSize={pageSize}&{searchParam}"
			EmptyInfo="没有记录" width="100%" Action="editRegulation('{REGULATION_ID}')">
			<col caption="序号" field="{RN}" width="20" align="center">
			<col caption="企业名称" field="{COMPANY_SHORT_NAME}" align="center" width="50px">
			<col caption="制度名称" field="{REGULATION_NAME}" align="center" width="100px">
			<col caption="制度类型" field="{TYPE_NAME}" align="center"  width="50px">
			<col caption="审批时间" field="{APPROVAL_DATE}" align="center" width="30px">
			<col caption="生效时间" field="{EFFECTIVE_DATE}" align="center" width="30px">
		</table>
	</div>

</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>