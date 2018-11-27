<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中央企业“三重一大”决策和运行应用系统</title>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/index.css" type="text/css"/>
    <script type="text/javascript" src="<%=SYSURL_static%>/js/echarts.js"></script>
</head>

<body>
<div class="content_main">
<table>
<tr>
	<td class="td-content_main_left">
		<input type="hidden" name="typeID" id="typeID">
		<div id="treeDiv" class="meetting_main_left">
			<div class="industry_title"><p class="industry-choose">异常类型</p></div>
			<div class="industry_contents"><a onclick="showRegulationTable()">制度异常</a></div>
			<div class="industry_contents"><a class="industry_contents_cur">议题异常</a></div>
		</div>
	</td>
	<td><div class="w12"></div></td>
	<td style="vertical-align:top;" >
		<div class="list_all" >
		<div class="list_title">
		<p style="font-size:16px;">制度异常列表</p>
		<a id="showHide" class="backgroundimg1" onclick="showHideSearch();">显示筛选</a>
		</div>
		<div id="search" class="list_search"> 
		<table>
		<tr>
		<td style="width:100px"><p>企业名称：</p></td><td style="width:220px"><input id="companyName" type="text" placeholder="请输入企业名称"></td>
		<td style="width:100px;"><div class="btn_submit"><a onclick="queryData()">查询</a></div></td>
		<td style="width:100px"><div class="btn_reset" onclick="resetSearchValue()"><a>重置</a></div></td><td></td>
		</tr>
		</table>
		</div>
		<hr/>
			<div class="list_main" id="subjectDiv" >
				<table  class="Grid" style="text-align:center;" FixHead="true" align="right" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
					URLData="{contextPath}/exception/querySubjectExceptionByPage.action?currentPage={page}&pageSize={pageSize}" EmptyInfo="没有记录" width="100%">
					<col caption="序号" field="{RN}" width="20" align="center">
					<col caption="企业名称" field="<a style='color:blue' href=&quot;javascript:subjectView('{COMPANY_ID}','');void(0);&quot;>{COMPANY_SHORT_NAME}</a>" width="90" align="center">
					<col caption="未经合法合规审查" field="<a style='color:blue' href=&quot;javascript:subjectView('{COMPANY_ID}','1');void(0);&quot;>{type1}</a>" width="50" align="center">
					<col caption="决策会议顺序不当" field="<a style='color:blue' href=&quot;javascript:subjectView('{COMPANY_ID}','2');void(0);&quot;>{type2}</a>" width="50" align="center">
					<col caption="党委会未前置" field="<a style='color:blue' href=&quot;javascript:subjectView('{COMPANY_ID}','3');void(0);&quot;>{type3}</a>" width="50" align="center">
					<col caption="会议召开条件不合规" field="<a style='color:blue' href=&quot;javascript:subjectView('{COMPANY_ID}','4');void(0);&quot;>{type4}</a>" width="50" align="center">
					<col caption="表决结果不一致" field="<a style='color:blue' href=&quot;javascript:subjectView('{COMPANY_ID}','5');void(0);&quot;>{type5}</a>" width="50" align="center">
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
	<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/enterpriseOne/enterpriseOne_statistics.js"></script>
	<script type="text/javascript">
		jQuery(function($) {
			
		});
		function showRegulationTable(){
			window.location.href="/exception/exceptionJump.action";
		}
		function subjectView(companyId,exceptionType){
			var data = "{\"companyId\":\""+companyId+"\",\"exceptionType\":\""+exceptionType+"\"}";
			var href = "/exception/exceptionSubjectDetail.action?parameter="+encodeURIComponent(data);
			window.parent.addpage("企业异常议题列表",href,'exceptionSubjectCompany'+companyId+exceptionType);
		}
		function queryData(){
			//企业名称
			var companyName = jt._("#companyName").value;
			var data = "{\"companyName\":\""+companyName+"\"}";
			//请求路径
			var sURL = "{contextPath}/exception/querySubjectExceptionByPage.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
			jt.setAttr(_('#tabMain_List'),'URLData',sURL);
			_('#tabMain_List').loadData();
		}
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>