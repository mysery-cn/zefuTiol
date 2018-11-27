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
	<div class="content_main_right">
		<div class="list_all">
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
                    <td style="width:100px"><p>会议决议内容：</p></td>
                    <td style="width:220px">
                        <input type="text" id="subjectResult" placeholder="请输入决议情况内容">
                    </td>
                    <td style="width:100px"><p>是否需报国资委审批：</p></td>
                    <td style="width:180px">
                        <div class="select_diy">
                            <select name="approvalFlag" id="approvalFlag">
                                <option value="">--请选择--</option>
                                <option value="1">审批</option>
                                <option value="2">备案</option>
                                <option value="0">否</option>
                            </select>
                        </div>
                    </td>
                </tr>
                <tr>
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
		<div class="list_main" style="height: 520px;" >
                <div class="total_num"><p>当前议题总数：</p><span id="total">0</span></div>
                <div class="no_front"><p>有权限查看数：</p><span id="yNumber">0</span></div>
                <div class="no_front"><p>无权限查看数：</p><span id="nNumber">0</span></div>
				<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0" 
					URLData="{contextPath}/enterpriseOne/querySubjectPageList.action?currentPage={page}&pageSize={pageSize}" EmptyInfo="没有记录" width="100%">
					<col caption="序号" field="{NUM}" width="50" align="center">
					<col caption="企业名称" field="{companyName}" width="auto" align="center">
					<col caption="议题名称" field="<a style='color:blue' href=&quot;javascript:showSubjectDetail('{meetingId}','{companyId}','{subjectId}');void(0);&quot;>{subjectName}</a>" width="auto" align="center">
					<col caption="会议名称" field="{meetingName}" width="auto" align="center">
					<col caption="会议日期" field="{createDate}" width="auto" align="center">
					<col caption="是否通过" field="[=formatApprovalStatus('{approvalFlag}')]" width="auto" align="center">
                    <col caption="会议决议结果" field="<a style='color:blue' title='{subjectResult}' href='#'>会议决议结果</a>" width="auto" align="center">
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
	<script type="text/javascript" src="<%=SYSURL_static%>/js/statistics/reform/reform_subject.js"></script>
	<script type="text/javascript">
		//加载会议类型
		loadingCatelog();
		//加载权限查看数量
        loadUserRoleMessage();
		
		function queryData(id){
			//企业名称
			var companyName = jt._("#companyName").value;
			var subjectName = jt._("#subjectName").value;
            //会议决议情况
            var subjectResult = jt._("#subjectResult").value;
			var approvalFlag=$("#approvalFlag option:selected").val();
            //查询时间
            var startTime = jt._("#startTime").value;
            var endTime = jt._("#endTime").value;
            if (jt._("#times").value != '') {
                if (startTime == "" || endTime == "") {
                    return jt.Msg.showMsg("查询起止时间不可为空!");
                }
            }
			var data = "{\"subjectResult\":\""+subjectResult+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"companyName\":\""+companyName+"\",\"subjectName\":\""+subjectName+"\",\"approvalFlag\":\""+approvalFlag+"\"}";
			//请求路径
			var sURL = "{contextPath}/enterpriseOne/querySubjectPageList.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
			jt.setAttr(_('#tabMain_List'),'URLData',sURL);
			_('#tabMain_List').loadData();
            loadUserRoleMessage();
		}

        /**
         * 加载权限查看数量
         */
        function loadUserRoleMessage(){
            //企业名称
            var companyName = jt._("#companyName").value;
            var subjectName = jt._("#subjectName").value;
            //会议决议情况
            var subjectResult = jt._("#subjectResult").value;
            var approvalFlag=$("#approvalFlag option:selected").val();
            //查询时间
            var startTime = jt._("#startTime").value;
            var endTime = jt._("#endTime").value;
            if (jt._("#times").value != '') {
                if (startTime == "" || endTime == "") {
                    return jt.Msg.showMsg("查询起止时间不可为空!");
                }
            }
            //请求路径
            var dataURL = "{contextPath}/enterpriseOne/queryOneSubjectListByRoleNumber.action";
            var jPost= {};
            jPost.startTime = startTime;
            jPost.endTime = endTime;
            jPost.subjectName = subjectName;
            jPost.companyName = companyName;
            jPost.subjectResult = subjectResult;
            jPost.approvalFlag = approvalFlag;
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

        //数据格式化
        function formatApprovalStatus(status) {
            if(status=='1'){
                return '审批';
            }
            if(status=='2'){
                return '备案';
            }
            if(status=='0'){
                return '否';
            }
        }
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>