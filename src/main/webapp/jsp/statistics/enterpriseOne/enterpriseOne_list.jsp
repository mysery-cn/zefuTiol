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
<tr><td width="33%" style="padding-right: 20px;">
	<div class="list_all">
	<div class="list_title"><p>重要人事任免会议分布</p></div>
	<div class="chart_img_gai">
		<div id="container_1" style="height: 220px;width: 95%;margin: 0 auto;"></div>
	</div>
	</div>
</td>
<td width="33%" style="padding-right: 20px;">
	<div class="list_all">
	<div class="list_title"><p>2018年董事会议题情况</p></div>
	<div class="chart_img_gai"><div id="container_2" style="height: 220px;width: 95%;margin: 0 auto;"></div></div>
	</div>
</td>	
<td width="33%">
	<div class="list_all">
	<div class="list_title"><p>涉及出资人重大决策议题</p></div>
	<div class="chart_img_gai"><div id="container_3" style="height: 220px;width: 95%;margin: 0 auto;"></div></div>
	</div>
</td>
</tr>
</table>
	<div class="list_all">
		<div class="list_title"><p>所有列表</p><a id="showHide" onclick="showHideSearch()">显示筛选</a></div>
		<div id="search" class="list_search" style="display:none;">
		<table>
		<tr>
		<td width="100px;"><p>企业名称：</p></td><td width="200px;"><input type="text" id="companyName" placeholder="请输入企业名称"></td>
		<td width="100px"><p>时间段：</p></td ><td width="200px">
		<table style="width:100%;">
		    <tr>
		        <td style="width:50%;"><input id="startDate" style="width: 130px;" class="Input_DateTime input">
		        </td>
		        <td>&nbsp;&nbsp;至&nbsp;&nbsp;
		        </td><td style="width:50%;"><input id="endDate" style="width: 130px;" class="Input_DateTime input">
		        </td>
		    </tr>
		</table>
		<td width="100px;"><p>会议类型：</p></td>
		<td style="margin-right: -20px;" width="200px;">
            <div class="select_diy">
                <select id="meetingType" name="meetingType">
                    <option value="">--全部--</option>
                </select>
    		</div>
        </td>
        </tr>
        <tr>
        <td style="width:100px"><p>会议决议内容：</p></td>
        <td style="width:220px">
            <input type="text" id="subjectResult" placeholder="请输入决议情况内容">
        </td>
		<td width="90px;"><div class="btn_submit" onclick=""><a onclick="queryData()">查询</a></div></td>
		<td width="120px;"><div class="btn_reset" onclick="resetSearchValue()"><a>重置</a></div></td>
		<td>&nbsp;</td>
		</tr>
		
		</table>
		</div>
		
		<hr/>
		<div class="list_main">
            <div class="total_num"><p>当前议题总数：</p><span id="total">0</span></div>
            <div class="no_front"><p>有权限查看数：</p><span id="yNumber">0</span></div>
            <div class="no_front"><p>无权限查看数：</p><span id="nNumber">0</span></div>
			<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0" Action="querySubjectDetail('{subjectId}','{meetingId}','{companyId}')"  
				URLData="{contextPath}/enterpriseOne/queryMeetingTypeSubjectListByPage.action?currentPage={page}&pageSize={pageSize}"
                   EmptyInfo="没有记录" width="100%" >
				<col caption="序号" field="{RN}" width="50" align="center">
				<col caption="公司名称" field="{companyName}" width="auto" align="center">
				<col caption="议题名称" field="{subjectName}" width="auto" align="center">
				<col caption="会议类型" field="{typeName}" width="auto" align="center">
				<col caption="会议日期" field="{createDate}" width="auto" align="center">
                <col caption="会议决议结果" field="<a style='color:blue' title='{subjectResult}' href='#'>会议决议结果</a>" width="auto" align="center">
			</table>
		</div>
	</div>
</div>
</body>
	<script src="<%=SYSURL_static%>/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/select.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/enterpriseOne/enterpriseOne_statistics.js"></script>
	<script type="text/javascript">
		jQuery(function($) {
			$.ajax({
				url : "/enterpriseOne/queryMeetingDetail.action",
				type : "POST",
				async: false, 
		        success: function (result) {
					names = result.data[0].name;
					values = result.data[0].value;
					if(values==""){
						$("#container_1").html("<img id='img1' src='<%=SYSURL_static%>/images/tiol/no_data.png'>");
						$("#img1").css("padding-top","45px");
					}else{
						showMeeting(names,values);
					}
				}
			});
			$.ajax({
				url : "/enterpriseOne/queryDecisionSubjectDetail.action",
				type : "POST",
				async: false, 
		        success: function (result) {
					var names = result.data[0].name;
					var pass = result.data[0].pass;
					var defer = result.data[0].defer;
					var veto = result.data[0].veto;
					showDecisionDetail(names,pass,defer,veto);
				}
			});
			$.ajax({
				url : "/enterpriseOne/queryInvestorDetail.action",
				type : "POST",
				async: false, 
		        success: function (result) {
					var registerQuantity = result.data[0].registerQuantity[0];
					var approveQuantity = result.data[0].approveQuantity[0];
					var subjectQuantity = result.data[0].subjectQuantity[0];
					if(result.data[0].subjectQuantity==0){
						$("#container_3").html("<img id='img3' src='<%=SYSURL_static%>/images/tiol/no_data.png'>");
						$("#img3").css("padding-top","45px");
					}else{
						showInvestor(Number(registerQuantity),Number(approveQuantity),Number(subjectQuantity));
					}
				}
			});
			loadingMeetingType();
            loadUserRoleMessage();
		});
		function querySubjectDetail(subjectId,meetingId,companyId){
			var href="/subject/subjectDetail.action?meetingId="+meetingId+"&subjectId="+subjectId+"&companyId="+companyId;
			window.parent.addpage("议题详情",href,'subjectDetail'+companyId);
		}
		/**
		 * 列表搜索
		 */
		function queryData(){
			//企业名称
			var companyName = jt._("#companyName").value;
            var subjectResult = jt._("#subjectResult").value;
			var startDate = jt._("#startDate").value;
			var endDate = jt._("#endDate").value;
			var meetingType=$("#meetingType option:selected").val();
			var data = "{\"subjectResult\":\""+subjectResult+"\",\"companyName\":\""+companyName+"\",\"startDate\":\""+startDate+"\",\"endDate\":\""+endDate+"\",\"meetingType\":\""+meetingType+"\"}";
			//请求路径
			var sURL = "{contextPath}/enterpriseOne/queryMeetingTypeSubjectListByPage.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
			jt.setAttr(_('#tabMain_List'),'URLData',sURL);
			_('#tabMain_List').loadData();
            loadUserRoleMessage();
		}

		function loadingMeetingType(){

			var dataURL = "{contextPath}/meetingType/queryList.action";
			var jPost= {};
			postJSON(dataURL,jPost,function(json,o){
				if (!json || json.errorCode != "0") {
					return jt.Msg.showMsg("获取数据失败！");
				} else {
					for(var i=0;i<json.data.length;i++){
						$("#meetingType").append("<option value='"+json.data[i].TYPE_ID+"'>"+json.data[i].TYPE_NAME+"</option>");	        	
			        }
				}
			},false);
		}

        /**
         * 加载权限查看数量
         */
        function loadUserRoleMessage(){
            //企业名称
            var companyName = jt._("#companyName").value;
            var subjectResult = jt._("#subjectResult").value;
            var startDate = jt._("#startDate").value;
            var endDate = jt._("#endDate").value;
            var meetingType=$("#meetingType option:selected").val();
            //请求路径
            var dataURL = "{contextPath}/enterpriseOne/queryOneIndexListByRoleNumber.action";
            var jPost= {};
            jPost.companyName = companyName;
            jPost.subjectResult = subjectResult;
            jPost.startDate = startDate;
            jPost.endDate = endDate;
            jPost.meetingType = meetingType;
            postJSON(dataURL,jPost,function(json,o){
                if (!json || json.errorCode != "0") {
                    return jt.Msg.showMsg("获取数据失败！");
                } else {
                    document.getElementById("total").innerText = json.data[0].all;
                    document.getElementById("yNumber").innerText= json.data[0].have;
                    document.getElementById("nNumber").innerText= json.data[0].not;
                }
            },false);
        }
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>