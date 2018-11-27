<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<div class="industry_title"><p class="industry-choose">类型</p></div>
			<div class="industry_contents">
				<a class="industry_contents_cur" id="all" onclick="reloadSubjectData()">全部</a>
			</div>
		</div>
	</td>
	<td><div class="w12"></div></td>
	<td style="vertical-align:top;" >
		<div class="list_all">
			<div class="list_title"><p>企业议题列表</p></div>
			<div  class="list_search">
				<table>
				<tr>
					<td style="width:100px"><p>企业名称：</p></td>
					<td style="width:220px">
						<input type="text" id="companyName" placeholder="请输入企业名称">
					</td>
					<td style="width:100px"><p>议题名称：</p></td>
					<td style="width:220px">
						<input type="text" id="subjectName" placeholder="请输入议题名称">
					</td>
				<td style="width:100px"><p>是否通过：</p></td>
				<td style="width:180px">
		      		<div class="select_diy">
		       		<select name="passFlag" id="passFlag">
		       			<option value="">--请选择--</option>
		        		<option value="1">通过</option>
		        		<option value="2">缓议</option>
		        		<option value="0">否决</option>
		    		</select>
		    		</div>
		    	</td>
				<td style="width:100px;">
					<div class="btn_submit" onclick="queryData()"><a>查询</a></div>
				</td>
				<td style="width:100px">
					<div class="btn_reset" onclick="resetSearchValue()"><a>重置</a></div>
				</td>
				</tr>
				</table>
			</div>
		<hr/>
			<div class="list_main" style="height: 500px;">
				<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0" Action="querySubjectDetail('{subjectId}','{meetingId}','{companyId}')" 
					URLData="{contextPath}/enterpriseOne/querySubjectPageListDsh.action?currentPage={page}&pageSize={pageSize}" EmptyInfo="没有记录" width="100%">
					<col caption="序号" field="{NUM}" width="20" align="center">
					<col caption="企业名称" field="{companyName}" width="50" align="center">
					<col caption="议题名称" field="{subjectName}" width="90" align="center">
					<col caption="会议名称" field="{meetingName}" width="50" align="center">
					<col caption="会议日期" field="{createDate}" width="50" align="center">
					<col caption="是否通过" field="[=formatStatus('{passFlag}')]" width="50" align="center">
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
			//生成事项目录树形
			$.ajax({
				url : "/enterpriseOne/queryCatalogLevel.action",
				type : "POST",
				async: false, 
		        success: function (result) {
					showTree(result);
				}
			});
		});
		function showTree(result){
	        for(var i=0;i<result.data.length;i++){
	        	$("#treeDiv").append("<div class='industry_contents'> <a id='"+result.data[i].CATALOG_CODE+"' onclick=queryData('"+result.data[i].CATALOG_CODE+"');>"+result.data[i].CATALOG_NAME+"</a></div>");
	        }
		}
		function querySubjectDetail(subjectId,meetingId,companyId){
			var href="/subject/subjectDetail.action?meetingId="+meetingId+"&subjectId="+subjectId+"&companyId="+companyId;
			window.parent.addpage("议题详情",href,'subjectDetail'+companyId);
		}
		function formatStatus(status){
			if(status=='1'){
				return '通过';
			}
			if(status=='2'){
				return '缓议';
			}
			if(status=='0'){
				return '否决';
			}
		    return '待定';
		}
		function queryData(typeID){
			alert(typeID);
			//设置选中
			$("#treeDiv a").removeClass();
			if(typeID == null || typeID == "" || typeID == "undefind"){
				document.getElementById("all").className += 'industry_contents_cur';//追加一个class
				typeID = $("#typeID").val();
			}else{
				document.getElementById(typeID).className += 'industry_contents_cur';//追加一个class
				$("#typeID").val(typeID);
			}
			//事项ID
			var passFlag = jt._("#passFlag").value;
			//议题名称
			var subjectName = jt._("#subjectName").value;
			//企业名称
			var companyName = $("#companyName").val();
			//请求路径
			var data = "{\"subjectName\":\""+subjectName+"\",\"passFlag\":\""+passFlag+"\",\"catalogCode\":\""+typeID+"\",\"companyName\":\""+companyName+"\"}";
			//请求路径
			var sURL = "{contextPath}/enterpriseOne/querySubjectPageListDsh.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
			jt.setAttr(_('#tabMain_List'),'URLData',sURL);
			_('#tabMain_List').loadData();
		}
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>