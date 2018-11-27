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
<div class="title_com">
	<h1>${companyName }</h1>
</div>
<table>
<tr>
	<td class="td-content_main_left" >
		<input type="hidden" name="typeID" id="typeID" value=${typeID }>
		<input type="hidden" name="companyID" id="companyID" value=${companyID }>
		<div id="treeDiv" class="content_main_left">
			<div class="industry_title"><p class="industry-choose" >类型</p></div>
			<div class="industry_contents">
				<a class="industry_contents_cur" id="all" onclick="reloadSubjectData(1)">全部</a>
			</div>
		</div>
	</td>
	<td><div class="w12"></div></td>
	<td  style="vertical-align:top;" >
	<div class="content_main_right">
		<div class="list_all">
			<div class="list_title"><p>企业议题列表</p>
				<a id="showHide" class="backgroundimg1" onclick="showHideSearch();">隐藏筛选</a>
			</div>
			<div id="search"  class="list_search">
				<table>
				<tr>
					<td style="width:100px"><p>议题名称：</p></td>
					<td style="width:220px">
						<input type="text" id="subjectName" placeholder="请输入议题名称">
					</td>
                    <td style="width:100px"><p>会议决议内容：</p></td>
                    <td style="width:220px">
                        <input type="text" id="subjectResult" placeholder="请输入决议情况内容">
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
                </tr>
                <tr>
					<td style="width:100px" name="companySearch"><p>企业名称：</p></td>
					<td style="width:220px" name="companySearch">
						<input type="text" id="companyName" placeholder="请输入企业名称">
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
					<td name="timeDiv" style="display: none;width:220px;">
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
						<div class="btn_reset"><a onclick="resetSearchValue()">重置</a></div>
					</td>
				</tr>
				</table>
			</div>
			<hr/>
			<div class="list_main">
				<div class="total_num"><p>当前议题总数：</p><span id="total">0</span></div>
				<div class="no_front"><p>有权限查看数：</p><span id="yNumber">0</span></div>
				<div class="no_front"><p>无权限查看数：</p><span id="nNumber">0</span></div>
				<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
					URLData="{contextPath}/model/reform/querySubjectPageList.action?currentPage={page}&pageSize={pageSize}" EmptyInfo="没有记录" width="100%">
					<col caption="序号" field="{NUM}" width="50" align="center">
					<col caption="企业名称" field="{companyName}" width="auto" align="center">
					<col caption="议题名称" field="<a style='color:blue' href=&quot;javascript:showSubjectDetail('{meetingId}','{companyId}','{subjectId}');void(0);&quot;>{subjectName}</a>" width="auto" align="center">
					<col caption="会议名称" field="{meetingName}" width="auto" align="center">
					<col caption="会议日期" field="{createDate}" width="auto" align="center">
					<col caption="是否通过" field="[=formatStatus('{passFlag}')]" width="auto" align="center">
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
	</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>