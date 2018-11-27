<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv=“X-UA-Compatible” content=“IE=8″>
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
		<div class="list_all" style="width:100%;">
		<div class="list_title">
		<p style="font-size:16px;">制度清单列表</p>
		<a id="showHide" class="backgroundimg1" onclick="showHideSearch();">显示筛选</a>
		</div>
		<div id="search" class="list_search"> 
		<table>
		<tr>
		<td style="width:100px"><p>企业名称：</p></td><td style="width:220px"><input id="companyName" type="text" placeholder="请输入企业名称"></td>
		<td style="width:100px;"><div class="btn_submit"><a onclick="queryData()">查询</a></div></td>
		<td style="width:100px"><div class="btn_reset" onclick="resetSearchValue()"><a>重置</a></div></td><td>&nbsp;</td>
		</tr>
		</table>
		</div>
		<hr/>
		<div class="list_main">
			<table  class="Grid" style="text-align:center;" FixHead="true" align="right" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
				URLData="{contextPath}/leader/regulation/queryRegulationByPage.action?currentPage={page}&pageSize={pageSize}" EmptyInfo="没有记录" width="100%">
				<col caption="序号" field="{RN}" width="50" align="center">
				<col caption="企业名称" field="<a style='color:blue' href=&quot;javascript:itemView('{COMPANY_ID}','{COMPANY_SHORT_NAME}');void(0);&quot;>{COMPANY_SHORT_NAME}</a>" width="auto" align="center">
				<c:forEach items="${regulationType}" varStatus="i" var="item" > 
					<col caption="${item.TYPE_NAME}" field="[=formatStatus('{typeCode${i.index}}','{uploadFlag${i.index}}')]" width="auto" align="center">
				</c:forEach>
			</table>
			
		</div>
		</div>
	</td>
	</div>
</tr>
</table>	
</div>
</body>
	<script src="<%=SYSURL_static%>/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/select.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/leader_statistics.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
	<script type="text/javascript">
	jQuery(function($) {
	})
		function formatStatus(status,uploadFlag){
	        debugger;
	        if(uploadFlag == 1){
                if(status != 0){
                    return '<img src="<%=SYSURL_static%>/images/tiol/ico_pass.png">';
                }else{
                    return '<img src="<%=SYSURL_static%>/images/tiol/ico_no.png">';
                }
            }else if(uploadFlag == 0){
                if(status != 0){
                    return '<img src="<%=SYSURL_static%>/images/tiol/ico_pass.png">';
                }else{
                    return "";
                }
            }
	    }
		function itemView(companyId,companyName){
			var data = "{\"companyId\":\""+companyId+"\",\"companyName\":\""+companyName+"\"}";
			var href = "/leader/regulation/leaderRegulationDetail.action?parameter="+encodeURIComponent(data);
			window.parent.addpage("企业制度列表",href,'leaderRegulationCompany');
		}
		/**
		 * 列表搜索
		 */
		function queryData(){
			//企业名称
			var companyName = jt._("#companyName").value;
			var data = "{\"companyName\":\""+companyName+"\"}";
			//请求路径
			var sURL = "{contextPath}/leader/regulation/queryRegulationByPage.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
			jt.setAttr(_('#tabMain_List'),'URLData',sURL);
			_('#tabMain_List').loadData();
		}
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>