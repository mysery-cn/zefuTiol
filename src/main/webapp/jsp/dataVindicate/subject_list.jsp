<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<title>议题列表</title>
</head>
<script type="text/javascript">

    var status = jt.getParam("status");

    function jtParseURL_Page(sURL){
        sURL=sURL.replace(/\{status\}/ig, status);
        return sURL;
    }

    function init() {
        if(status == 0){
            $('#change').hide();
            $('#vindicate').show();
        }
        if(status == 2){
            $('#vindicate').hide();
            $('#change').show();
        }
    }

    //修改状态
    function changeStatus(){
        var arr=delItem_GetIDs(sID,'txtID','请选择要修改的数据!','确实要修改吗？');
        if (arr.length==0) return;
        var sURL = "{contextPath}/dataVindicate/updateStatus.action?type=subject";
        postJSON(sURL, {'ids':arr.toString()},function (json,o){
            if (json.errorCode!=0){
                showMsg(json.errorInfo,'操作失败');
                return;
            }
            _('#tabMain_List').loadData();
        });
    }

    //重新采集
    function vindicate() {
        showLoading("数据采集中");
        var dataURL = "{contextPath}/job/collectData/collectData.action";
        var jPost= {};
        postJSON(dataURL,jPost,function(json,o){
            showLoading(false);
            return jt.Msg.showMsg("数据采集成功！");
        },false);
    }

    function queryData(){
        //企业名称
        var companyName = jt._("#companyName").value;
        var data = "{\"companyName\":\""+companyName+"\",\"status\":\""+status+"\"}";
        //查询
        var sURL = "{SYSURL.oa}/dataVindicate/querySubjectListByPage.action?t={JSTime}&currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);;
        jt.setAttr(_('#tabMain_List'),'URLData',sURL);
        _('#tabMain_List').loadData();
    }
</script>
<body class="BodyList">
    <div id="divFixTop">
        <div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
            <div icon="info_edit.png" id="vindicate" onclick="vindicate()">重新采集</div>
            <div icon="green.png" id="change" onclick="changeStatus()">更改状态</div>
        </div>
    </div>
	<div id="divSearch" ShowCount="0" >
	  <table>
	  	<tr>
	  		<td style="width: 5%" align="right">企业名称:</td>
	  		<td style="width: 10%">
	  			<input type="text" id="companyName" class="input" />
	  		</td>
	  		<td style="width: 20%">
	  			<input type="button" class="button" id="searchBtn" value="搜索" onclick="queryData()" />
	  		</td>
	  	</tr>
	  </table>
	</div>
	<div id="divFixCnt" class="GridOnly" >
		<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0"	cellspacing="0" cellpadding="0" 
			URLData="{SYSURL.oa}/dataVindicate/querySubjectListByPage.action?t={JSTime}&currentPage={page}&pageSize={pageSize}&status={status}"
			EmptyInfo="没有记录" width="100%">
            <col boxName="txtID" width="30" boxValue="{SUBJECT_ID}" boxAttr="{boxAttr}">
			<col caption="序号" field="{RN}" width="20" align="center">
			<col caption="企业名称" field="{COMPANY_SHORT_NAME}" align="center" width="70px">
			<col caption="会议名称" field="{MEETING_NAME}" align="center" width="100px">
			<col caption="议题名称" field="{SUBJECT_NAME}" align="center" width="100px">
			<col caption="异常信息" field="{ERROR_REMARK}" align="center"  width="30px">
			<col caption="录入时间" field="{CREATE_TIME}" align="center" width="30px">
		</table>
	</div>

</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>