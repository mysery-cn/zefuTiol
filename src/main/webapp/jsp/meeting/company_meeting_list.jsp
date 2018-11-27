<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<title>单位会议列表</title>
</head>
<script type="text/javascript">
function queryData(){
	var typeID = jt._("#typeID").value;//会议类型
	var meetingName = jt._("#meetingName").value;//会议名称
	var registerType = jt._("#registerType").value;//录入类型
	var uploadStatus = jt._("#uploadStatus").value;//上传状态
    var status = jt._("#status").value;//操作类型
	var data = "{'typeID':'"+typeID+"','meetingName':'"+meetingName+"','registerType':'"+registerType+"','uploadStatus':'"+uploadStatus+"','status':'"+status+"'}";
	//查询
	var sURL = "{SYSURL.oa}/meeting/queryCompanyMeetingList.action?t={JSTime}&currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);;
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
}

function init(){
	//加载会议类型
	var dataURL = "{contextPath}/meetingType/queryList.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i<json.data.length;i++){
				$("#typeID").append("<option value='"+json.data[i].TYPE_ID+"'>"+json.data[i].TYPE_NAME+"</option>");
			}
		}
	},false);
}

function editMeeting(meetingId){
    var sURL = "/meeting/reloadMeeting.action?page=meeting_register&meetingId="+meetingId;
    if(meetingId == "") sURL = "/jsp/meeting/register/meeting_register.jsp";
    //window.parent.addpage("会议集中填报", sURL, "meetingRegister", "reload");
    showDialog('会议集中填报',sURL,'meetingRegister',800,600,true);
}

function editUnionMeeting(meetingId){
    var sURL = "/meeting/reloadMeeting.action?page=meeting_union_register&meetingId="+meetingId;
    if(meetingId == "") sURL = "/jsp/meeting/register/meeting_union_register.jsp";
    //window.parent.addpage("会议协同填报", sURL, "meetingUnionRegister", "reload");
    showDialog('会议协同填报',sURL,'meetingUnionRegister',800,6,true);
}
/**
 * 编辑会议总入口
 */
function editAllMeeting(meetingId, registerType){
    registerType = registerType.trim();
    if(registerType == '2'){
        editUnionMeeting(meetingId);
    }else{
        editMeeting(meetingId);
	}
}
/**
 * 格式化录入类型 0批量导入,1集中填报,2协同填报
 * @param registerType
 */
function formatRegister(registerType){
    if(registerType == '0'){
        return "批量导入";
    }else if(registerType == '1'){
        return "集中填报";
    }else if(registerType == '2'){
        return "协同填报";
    }else{
        return "";
    }
}

/**
 * 格式化上传状态
 * @param uploadStatus 0 待上传、1 已上传、2 再次等待上传
 */
function formatUpload(uploadStatus){
    if(uploadStatus == '0'){
        return "待上传";
    }else if(uploadStatus == '1'){
        return "已上传";
    }else{
        return "";
    }
}

/**
 * 格式化上传操作类型
 * @param status 0删除  1新增  2修改
 */
function formatOpType(status){
    if(status == '0'){
        return "删除";
	}else if(status == '1'){
        return "新增";
	}else if(status == '2'){
		return "修改";
	}else{
        return "";
	}
}
/**
 * 导入会议
 */
function importMeeting(){
    var sURL = "/jsp/meeting/importExcel/meeting_import.jsp";
    top_showDialog('会议导入',sURL,'meetingRegister',500,200);
}
/**
 * 上传会议
 */
function uploadMeeting(){
    var arr = delItem_GetIDs("",'txtID','请选择要上传的数据!','确实要上传吗？');
    if (arr.length==0) return;
    var sURL='{contextPath}/meeting/uploadMeeting.action';
    postJSON(sURL, {'ids':arr.toString()},function (json,o){
        if (json.errorCode != 0){
            showMsg("上传失败");
        }else{
            showMsg(json.errorInfo);
            _('#tabMain_List').loadData();
		}
    });
}

/**
 * 删除会议
 */
function delItem(){
    var arr = delItem_GetIDs("",'txtID','请选择要删除的数据!','确实要删除吗？');
    if (arr.length==0) return;
    var sURL='{contextPath}/meeting/deleteMeeting.action';
    postJSON(sURL, {'ids':arr.toString()},function (json,o){
        if (json.errorCode != 0){
            showMsg(json.errorInfo,"删除失败");
            return;
        }else{
            showMsg('删除成功');
        }
        _('#tabMain_List').loadData();
    });
}

function getIds(arr){
    var ids = new Array();
	if(arr.length == 0) return;
    for (var i = 0; i < arr.length; i++) {
        var meetingInfo = arr[i].split("@");
        ids.push(meetingInfo[0]);
    }
    return ids;
}
</script>
<body class="BodyList">
	<div id="divFixTop">
		<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
			<div icon="add.png" onclick="editMeeting('')">集中填报</div>
			<div icon="add.png" onclick="editUnionMeeting('')">协同填报</div>
			<div icon="import.png" onclick="importMeeting()">导入会议</div>
			<div icon="export.png" onclick="uploadMeeting()">上传</div>
			<div icon="del.png" onclick="delItem()">删除</div>
		</div>
		<div id="divSitebar">
			<div id="divCurPosition">当前位置 : </div>
		</div>
	</div>
	<div id="divSearch" ShowCount="0" >
	  <table>
	  	<tr>
	  		<td style="width: 5%" align="right">会议名称:</td>
	  		<td style="width: 10%">
	  			<input type="text" id="meetingName" class="input" />
	  		</td>
	  		<td style="width: 5%" align="right">会议类型:</td>
	  		<td style="width: 10%">
	  			<select id="typeID" style="width: 80%">
	  				<option value="">请选择</option>
	  			</select>
	  		</td>
			<td style="width: 5%" align="right">录入类型:</td>
			<td style="width: 10%">
				<select id="registerType" style="width: 80%">
					<option value="">请选择</option>
					<option value="0">批量导入</option>
					<option value="1">集中填报</option>
					<option value="2">协同填报</option>
				</select>
			</td>
			<td style="width: 5%" align="right">上传状态:</td>
			<td style="width: 10%">
				<select id="uploadStatus" style="width: 80%">
					<option value="">请选择</option>
					<option value="0">待上传</option>
					<option value="1">已上传</option>
				</select>
			</td>
			<td style="width: 5%" align="right">操作类型:</td>
			<td style="width: 10%">
				<select id="status" style="width: 80%">
					<option value="">请选择</option>
					<option value="0">删除</option>
					<option value="1">新增</option>
					<option value="2">修改</option>
				</select>
			</td>
	  		<td style="width: 10%">
	  			<input type="button" class="button" id="searchBtn" value="搜索" onclick="queryData()" />
	  		</td>
	  	</tr>
	  </table>
	</div>
	<div id="divFixCnt" class="GridOnly" >
		<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0"	cellspacing="0" cellpadding="0" 
			URLData="{SYSURL.oa}/meeting/queryCompanyMeetingList.action?t={JSTime}&currentPage={page}&pageSize={pageSize}&{searchParam}"
			EmptyInfo="没有记录" width="100%">
			<COL boxname="txtID" boxvalue="{meetingId}" width="5"><%--@{registerType}@{uploadStatus}@{status}--%>
			<col caption="序号" field="{RN}" align="center" width="20">
			<col caption="会议名称" field="<a style='color:blue' href=&quot;javascript:editAllMeeting('{meetingId}','{registerType}');void(0);&quot;>{meetingName}</a>" align="center" width="200">
			<col caption="会议类型" field="{typeName}" align="center"  width="60">
			<col caption="录入类型" field="[=formatRegister({registerType})]" align="center"  width="30">
			<col caption="会议日期" field="{meetingTime}" align="center" width="80">
			<col caption="是否上传" field="[=formatUpload({uploadStatus})]" align="center"  width="30">
			<col caption="操作类型" field="[=formatOpType({status})]" align="center"  width="30">
		</table>
	</div>

</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>