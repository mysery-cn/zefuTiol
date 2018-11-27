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
<input type="hidden" name="companyId" id="companyId" value="${companyId}">
<input type="hidden" name="exceptionType" id="exceptionType" value="${exceptionType}">
<div class="title_com"><h1 id="companyName"></h1></div>
<table>
<tr>
	<td class="td-content_main_left">
		<input type="hidden" name="catalogCode" id="catalogCode">
		<div id="treeDiv" class="content_main_left">
			<div class="industry_title"><p class="industry-choose">异常类型</p></div>
			<div class="industry_contents">
				<a id="all" onclick="reloadSubjectData(this,'')">全部类型</a>
			</div>
		</div>
	</td>
	<td><div class="w12"></div></td>
	<td  style="vertical-align:top;" >
	<div class="content_main_right">
		<div class="list_all">
			<div class="list_title"><p>企业异常议题列表</p>
			</div>
			<div  class="list_search">
				<table>
				<tr>
				<td style="width:100px"><p>议题名称：</p></td>
				<td style="width:220px">
					<input type="text" id="subjectName" placeholder="请输入议题名称">
				</td>
				<td width="100px;"><p>时间区间：</p></td>
				<td width="200px;">
					<div class="select_diy">
			       		<select name="times" id="times" onchange="clickTime()">
			       			<option value="">--请选择--</option>
			    		</select>
				    </div>
				</td>
				<td name="timeDiv" style="width:100px;display: none;"><p>查询时间：</p></td>
				<td name="timeDiv" style="display: none;">
					<table style="width: 220px" border="0" cellspacing="0" cellpadding="0">
						<tbody>
							<tr>
								<td style="width: 110px"><input name="opeDate" id="startTime"
									class="Input_DateTime input" style="width: 110px;"
									onchange="TimeJudge(this)" type="text"
									readonly="readonly" value="" oper=">"
									fieldname="opeDate"></td>
								<td>至</td>		
								<td style="width: 110px"><input name="opeDate" id="endTime"
									class="Input_DateTime input" style="width: 110px;"
									onchange="" type="text"
									readonly="readonly" value="" oper="<" fieldname="opeDate"></td>
							</tr>
						</tbody>
					</table>
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
			<div class="list_main">
				<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0" 
					URLData="{contextPath}/exception/querySubjectListByPage.action?currentPage={page}&pageSize={pageSize}&companyId=${companyId}&exceptionType=${exceptionType}" EmptyInfo="没有记录" width="100%">
					<col caption="序号" field="{ROWNUM}" width="20" align="center">
					<col caption="议题名称" field="<a style='color:blue' href=&quot;javascript:subjectDetail('{subjectId}','{meetingId}','{companyId}');void(0);&quot;>{subjectName}</a>" width="90" align="center">
					<col caption="会议名称" field="{meetingName}" width="90" align="center">
					<col caption="会议日期" field="{createDate}" width="50" align="center">
					<col caption="异常原因" field="{exceptionCause}" width="50" align="center">
				</table>
			</div>
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
		jQuery(function($) {
			loadingExceptionType();
		});
		//加载异常类型
		function loadingExceptionType(){
			var exceptionType =  $("#exceptionType").val();
			var dataURL = "{contextPath}/exception/queryExceptionType.action";
			var jPost= {};
			postJSON(dataURL,jPost,function(json,o){
				if (!json || json.errorCode != "0") {
					return jt.Msg.showMsg("获取数据失败！");
				} else {
					for(var i=0;i < json.data.length;i++){
						if(exceptionType==""||exceptionType==null){
							$("#all").addClass("industry_contents_cur");
						}
						if(json.data[i].exceptionType==exceptionType){
							$("#treeDiv").append("<div class='industry_contents'> <a class='industry_contents_cur' id='"+json.data[i].exceptionType+"' onclick=reloadSubjectData(this,'"+json.data[i].exceptionType+"');>"+json.data[i].exceptionCause+"</a></div>");
						}else{
							$("#treeDiv").append("<div class='industry_contents'> <a id='"+json.data[i].exceptionType+"' onclick=reloadSubjectData(this,'"+json.data[i].exceptionType+"');>"+json.data[i].exceptionCause+"</a></div>");
						}
			        }
				}
			},false);
		};
		function reloadSubjectData(obj,exceptionType){
			$("#treeDiv a").removeClass();
			$(obj).addClass("industry_contents_cur");
			var companyId = jt._("#companyId").value;
			$('#exceptionType').val(exceptionType);
			var data = "{\"companyId\":\""+companyId+"\",\"exceptionType\":\""+exceptionType+"\"}";
			var sURL = "{contextPath}/exception/querySubjectListByPage.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
			jt.setAttr(_('#tabMain_List'),'URLData',sURL);
			_('#tabMain_List').loadData();
		}
		function subjectDetail(subjectId,meetingId,companyId){
			var href = "/subject/subjectDetail.action?meetingId="+meetingId+"&subjectId="+subjectId+"&companyId="+companyId;
			window.parent.addpage("议题详情",href,'subjectDetail'+subjectId);
		}
		function queryData(){
		    var exceptionType = $('#exceptionType').val();
			var companyID = jt._("#companyId").value;
			//议题名称
			var subjectName = jt._("#subjectName").value;
			//查询时间
			var startTime = jt._("#startTime").value;
			var endTime = jt._("#endTime").value;
			if (jt._("#times").value != '') {
				if (startTime == "" || endTime == "") {
					return jt.Msg.showMsg("查询起止时间不可为空!");
				}
			}
			//请求路径
			var data = "{\"subjectName\":\""+subjectName+"\",\"companyID\":\""+companyID+"\",\"exceptionType\":\""+exceptionType+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\"}";
			//请求路径
			var sURL = "{contextPath}/exception/querySubjectListByPage.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
			jt.setAttr(_('#tabMain_List'),'URLData',sURL);
			_('#tabMain_List').loadData();
		}
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>