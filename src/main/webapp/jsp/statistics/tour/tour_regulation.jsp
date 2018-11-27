<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
		<div class="list_title"><p>制度清单列表</p><a>隐藏筛选</a></div>
		<div  class="list_search">
		<table>
		<tr>
			<td style="width:100px"><p>企业名称：</p></td><td style="width:220px">
			<input type="text" id="companyName" placeholder="请输入企业名称"></td>
			<td style="width:100px;">
			<div class="btn_submit"><a onclick="queryData()">查询</a></div>
			</td><td style="width:100px">
			<div class="btn_reset"><a onclick="resetSearchValue()">重置</a></div></td><td></td>
		</tr>
		<tr id="danger" style="display:none;height:10px">
			<td></td>
			<td>
				<div style="font-size:10px;color:red">请输入查询内容</div>
			</td>
		</tr>
		</table>
		</div>
		<hr/>
		<div class="h20"></div>
		<div class="list_main_content">
			<table  class="Grid" style="text-align:center;"  FixHead="true" align="right" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
				URLData="{contextPath}/leader/regulation/queryRegulationByPage.action?currentPage={page}&pageSize={pageSize}" EmptyInfo="没有记录" width="100%">
				<col caption="序号" field="{RN}" width="20" align="center">
				<col caption="企业名称" field="<a style='color:blue' href=&quot;javascript:editItem('{COMPANY_ID}');void(0);&quot;>{COMPANY_SHORT_NAME}</a>" width="90" align="center">
				<c:forEach items="${regulationType}" varStatus="i" var="item" > 
					<col caption="${item.TYPE_NAME}" field="[=formatStatus('{typeCode${i.index}}')]" width="50" align="center">
				</c:forEach>
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
	        if(status!=0){
	        	return '<img src="<%=SYSURL_static%>/images/tiol/ico_pass.png">';
	        }else{
	        	return '<img src="<%=SYSURL_static%>/images/tiol/ico_no.png">';
	        }
	    }
		function editItem(companyId){
			var href = "/tiol/url/goRegulationDetail.action?companyId=" + companyId;
			window.parent.addpage("企业制度清单列表",href,'regulationList'+companyId);
		}
		function queryData(){
			var companyName = jt._("#companyName").value;
			if(companyName.length==0)
			{
				$("#danger").show();
			}else{
				var data = "{\"companyName\":\""+companyName+"\"}";
				var sURL = "{contextPath}/leader/regulation/queryRegulationByPage.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
				jt.setAttr(_('#tabMain_List'),'URLData',sURL);
				_('#tabMain_List').loadData();
			}
		}
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>