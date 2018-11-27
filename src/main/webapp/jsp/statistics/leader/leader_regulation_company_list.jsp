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
	<td class="td-content_main_left">
		<div id="treeDiv" class="content_main_left">
			<div class="industry_title">
				<p class="industry-choose">类型</p>
			</div>
			<div class="industry_contents">
				<a style="font-size:15px;"
					onclick="reloadRegulationData(this,'');">全部类型</a>
			</div>
		</div>
	</td>
	<td><div class="w12"></div></td>
	<td  style="vertical-align:top;" >
		<div class="list_all" style="width:100%;">
		<input type="hidden" name="typeCode" id="typeCode" value="${typeCode}">
		<div class="list_title">
		<p style="font-size:16px;" id="title"></p>
		<a id="showHide" class="backgroundimg1" onclick="showHideSearch();">显示筛选</a>
		</div>
		<div id="search" class="list_search"> 
		<table>
		<tr>
		<td style="width:100px"><p>企业名称：</p></td><td style="width:220px">
		<input id="companyName" type="text" placeholder="请输入企业名称"></td>
		<td style="width:100px;"><div class="btn_submit" onclick="queryData()"><a>查询</a></div></td>
		<td style="width:100px"><div class="btn_reset" onclick="resetSearchValue()"><a>重置</a></div></td><td></td>
		</tr>
		</table>
		</div>
		<hr/>
		<div class="list_main">
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
				URLData="{contextPath}/leader/regulation/queryRegulationByType.action?typeCode=${typeCode}&currentPage={page}&pageSize={pageSize}" EmptyInfo="没有记录" width="100%">
				<col id="grid-table_rn1" caption="序号" field="{RN}" width="30" align="center">
				<col caption="企业名称" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}','{COMPANY_SHORT_NAME}');void(0);&quot;>{COMPANY_SHORT_NAME}</a>" width="90" align="center">
				<col caption="制度名称" field="<a style='color:blue' href=&quot;javascript:editItem('{REGULATION_ID}');void(0);&quot;>{REGULATION_NAME}</a>" width="110" align="center">
				<col caption="制度类型" field="{TYPE_NAME}" width="50" align="center">
				<col caption="审批时间" field="{APPROVAL_DATE}" width="50" align="center">
				<col caption="生效时间" field="{EFFECTIVE_DATE}" width="50" align="center">
				<col caption="附件" field="<a style='color:blue' href=&quot;javascript:showFile('{FILE_ID}');void(0);&quot;>{FILE_NAME}</a>" width="80" align="center">
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
		/**
		 * 页面初始化
		 */
		jQuery(function($) {
			loadingRegulationType();
		});
		function queryData(){
			//制度名称
			var companyName = $("#companyName").val();
			var typeCode =  $("#typeCode").val();
			//请求路径
			var data = "{\"companyName\":\""+companyName+"\",\"typeCode\":\""+typeCode+"\"}";
			//请求路径
			var sURL = "{contextPath}/leader/regulation/queryRegulationByType.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
			jt.setAttr(_('#tabMain_List'),'URLData',sURL);
			_('#tabMain_List').loadData();
		}
		function loadingRegulationType(){
			var typeCode =  $("#typeCode").val();
			var dataURL = "{contextPath}/regulation/queryRegulationTypeList.action";
			var jPost= {};
			postJSON(dataURL,jPost,function(json,o){
				if (!json || json.errorCode != "0") {
					return jt.Msg.showMsg("获取数据失败！");
				} else {
					for(var i=0;i<json.data.length;i++){
						if(json.data[i].code==typeCode){
							$("#treeDiv").append("<div class='industry_contents'> <a class='industry_contents_cur' id='"+json.data[i].id+"' onclick=reloadRegulationData(this,'"+json.data[i].id+"');>"+json.data[i].text+"</a></div>");
						}else{
							$("#treeDiv").append("<div class='industry_contents'> <a id='"+json.data[i].id+"' onclick=reloadRegulationData(this,'"+json.data[i].id+"');>"+json.data[i].text+"</a></div>");
						}
			        }
				}
			},false);
		}
		function reloadRegulationData(obj,typeId){
			$("#treeDiv a").removeClass();
			$(obj).addClass("industry_contents_cur");
			//请求路径
			var data = "{\"typeId\":\""+typeId+"\"}";
			//请求路径
			var sURL = "{contextPath}/leader/regulation/queryRegulationByType.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
			jt.setAttr(_('#tabMain_List'),'URLData',sURL);
			_('#tabMain_List').loadData();
		}
		function itemView(companyId,companyName){
			var data = "{\"companyId\":\""+companyId+"\",\"companyName\":\""+companyName+"\"}";
			var href = "/leader/regulation/leaderRegulationDetail.action?parameter="+encodeURIComponent(data);
			window.parent.addpage("企业制度列表",href,'leaderRegulationCompany');
		}
		function editItem(regulationId){
			var href = "/leader/regulation/leaderRegulationComDetail.action?regulationId="+regulationId;
			window.parent.addpage("企业制度详情",href,'leaderRegulationCompanyDetail');
		}
		function showFile(fileId){
			var href = "/common/show.jsp?fileId="+fileId;
			window.parent.addpage("附件详情",href,'fileDetail');
		}
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
