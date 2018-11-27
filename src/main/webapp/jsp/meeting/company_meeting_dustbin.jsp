<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/common/inc_head.jsp"%>
    <title>单位会议回收站</title>
</head>
<script type="text/javascript">
    function queryData(){
        //会议类型
        var typeID = jt._("#typeID").value;
        //会议名称
        var meetingName = jt._("#meetingName").value;
        var data = "{\"typeID\":\""+typeID+"\",\"meetingName\":\""+meetingName+"\"}";
        //查询
        var sURL = "{SYSURL.oa}/meeting/queryCompanyMeetingList.action?t={JSTime}&currentPage={page}&pageSize={pageSize}&isDustbin=1&parameter="+encodeURIComponent(data);
        jt.setAttr(_('#tabMain_List'),'URLData',sURL);
        _('#tabMain_List').loadData();
    }

    function init(){
        //加载制度类型
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
     * 还原删除会议
     */
    function recover(){
        var arr = delItem_GetIDs("",'txtID','请选择要还原的数据!','确实要还原吗？');
        if (arr.length==0) return;
        var sURL='{contextPath}/meeting/recoverMeeting.action';
        postJSON(sURL, {'ids':arr.toString()},function (json,o){
            if (json.errorCode != 0){
                showMsg(json.errorInfo,"还原失败");
                return;
            }else{
                showMsg('还原成功');
            }
            _('#tabMain_List').loadData();
        });
    }
</script>
<body class="BodyList">
<div id="divFixTop">
    <div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
        <div icon="add.png" onclick="recover('')">还原</div>
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
            <td style="width: 30%">
                <input type="button" class="button" id="searchBtn" value="搜索" onclick="queryData()" />
            </td>
        </tr>
    </table>
</div>
<div id="divFixCnt" class="GridOnly" >
    <table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0"	cellspacing="0" cellpadding="0"
           URLData="{SYSURL.oa}/meeting/queryCompanyMeetingList.action?t={JSTime}&currentPage={page}&pageSize={pageSize}&isDustbin=1&{searchParam}" EmptyInfo="没有记录" width="100%">
        <COL boxname="txtID" boxvalue="{meetingId}" width="10">
        <col caption="序号" field="{RN}" width="30" align="center">
        <col caption="会议名称" field="<a style='color:blue' href=&quot;javascript:editMeeting('{meetingId}');void(0);&quot;>{meetingName}</a>" align="center" width="200">
        <col caption="会议类型" field="{typeName}" align="center"  width="80">
        <col caption="录入类型" field="[=formatRegister({registerType})]" align="center"  width="80">
        <col caption="会议日期" field="{meetingTime}" align="center" width="80">
    </table>
</div>

</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>