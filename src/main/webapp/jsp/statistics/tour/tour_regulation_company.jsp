<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中央企业“三重一大”决策和运行应用系统</title>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/index.css" type="text/css"/>
</head>

<body>
<div class="content_main">
<table>
<tr>
	<td  style="vertical-align:top;" >
		<div class="list_all">
			<input type="hidden" name="companyId" id="companyId" value="${companyId }">
			<div class="list_title"><p>制度清单列表</p><a>隐藏筛选</a></div>
			<div  class="list_search">
				<table>
				<tr>
				<td style="width:100px"><p>企业名称：</p></td><td style="width:220px">
				<input type="text" id="companyName" placeholder="请输入企业名称"></td>
				<td style="width:100px;"><div class="btn_submit"><a onclick="queryData()">查询</a></div></td>
				<td style="width:100px"><div class="btn_reset"><a onclick="resetSearchValue()">重置</a></div></td>
				<td></td>
				</tr>
				</table>
			</div>
		<hr/>
		<div class="h20"></div>
		<div class="list_main_content">
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
				URLData="{contextPath}/leader/regulation/queryRegulationList.action?companyId=${companyId}&currentPage={page}&pageSize={pageSize}" EmptyInfo="没有记录" width="100%">
				<col id="grid-table_rn1" caption="序号" field="{ROWNUM}" width="30" align="center">
				<col caption="制度名称" field="<a style='color:blue' href=&quot;javascript:editItem('{REGULATION_ID}');void(0);&quot;>{REGULATION_NAME}</a>" width="90" align="center">
				<col caption="制度类型" field="{TYPE_NAME}" width="50" align="center">
				<col caption="审批时间" field="{APPROVAL_DATE}" width="50" align="center">
				<col caption="生效时间" field="{EFFECTIVE_DATE}" width="50" align="center">
				<col caption="是否经过合法合规性审查" field="[=formatStatus('{AUDIT_FLAG}')]" width="50" align="center">
				<col caption="附件" field="<a style='color:blue' href=&quot;javascript:showFile('{FILE_ID}');void(0);&quot;>{FILE_NAME}</a>" width="80" align="center">
				<col caption="是否上传" field="[=formatFileStatus('{FILE_ID}')]" width="50" align="center">
			</table>
		</div>
		</div>
	</td>
</tr>
</table>	
</div>
</body>
	<script src="<%=SYSURL_static%>/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/select.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
	<script type="text/javascript">
		function formatStatus(status){
	        if(status=="1")return '<img src="<%=SYSURL_static%>/images/tiol/ico_pass.png">';
	        return '<img src="<%=SYSURL_static%>/images/tiol/ico_no.png">';
	    }
		function formatFileStatus(fileId){
	        if(fileId!="")return '<img src="<%=SYSURL_static%>/images/tiol/ico_pass.png">';
	        return '<img src="<%=SYSURL_static%>/images/tiol/ico_no.png">';
	    }
		function editItem(regulationId){
			var href = "/leader/regulation/leaderRegulationComDetail.action?regulationId="+regulationId;
			window.parent.addpage("企业制度详情",href,'leaderRegulationCompanyDetail');
		}
		function showFile(fileId){
			var href = "/common/show.jsp?fileId="+fileId;
			window.parent.addpage("附件详情",href,'fileDetail');
		}
		
		function queryData(){
			var companyId = $("#companyId").val();
			//企业名称
			var companyName = $("#companyName").val();
			//请求路径
			var data = "{\"companyName\":\""+companyName+"\"}";
			//请求路径
			var sURL = "{contextPath}/leader/regulation/queryRegulationList.action?companyId="+companyId+"&currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
			jt.setAttr(_('#tabMain_List'),'URLData',sURL);
			_('#tabMain_List').loadData();
		}
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>