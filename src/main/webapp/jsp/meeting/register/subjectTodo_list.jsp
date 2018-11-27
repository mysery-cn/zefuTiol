<!DOCTYPE >
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <%@ include file="/common/inc_head.jsp"%>
    <title>协同填报待办页面</title>
    <link type="text/css" href="<%=SYSURL_static%>/css/index.css" rel="stylesheet" />
</head>
<body>
<div class="content_main">
    <table>
        <tr>
            <input type="hidden" id="industryId" name="industryId" value="">
            <td style="vertical-align:top;" colspan="4">
                <div class="list_all" style="width:99%;margin-left:16px;">
                    <div class="list_title"><p style="font-size:16px;">议题填报待办信息</p>
                        <a id="showHide" herf="javascript:void(0);" onclick="showHideSearch();">隐藏筛选</a></div>
                    <div  class="list_search">
                        <table>
                            <tr id="search">
                                <td style="width:100px;"><p  style="font-size:16px;">会议名称：</p></td>
                                <td style="width:220px;"><input type="text" id="meetingName" placeholder="请输入企业名称"></td>
                                <td style="width:100px;"><p  style="font-size:16px;">议题名称：</p></td>
                                <td style="width:220px;"><input type="text" id="subjectName" placeholder="请输入企业名称"></td>
                                <td style="width:100px;"><div class="btn_submit" onclick="queryData();"><a>查询</a></div></td>
                                <td style="width:100px;"><div class="btn_reset"><a onclick="resetSearchValue()">重置</a></div></td>
                                <td>&nbsp;</td>
                            </tr>
                        </table>
                    </div>
                    <div class="dividing_line"></div>
                    <div class="h20"></div>
                    <div class="list_main_content">
                        <table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0" action="getEditMeeting('{meetingId}')"
                               URLData="{contextPath}/subjectTodo/querySubjectList.action?currentPage={page}&pageSize={pageSize}&status=1" EmptyInfo="没有记录" width="100%">
                            <col caption="会议名称" field="{meetingName}" width="120" align="center">
                            <col caption="议题名称" field="{subjectName}" width="120" align="center">
                            <col caption="发起人" field="{sendUserName}" width="80" align="center">
                            <col caption="发起时间" field="{createTime}" width="80" align="center">
                        </table>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</div>
</body>
<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
<script type="text/javascript">
    /**
     * 查询方法
     */
    function queryData(){
        //议题名称
        var subjectName = jt._("#subjectName").value;
        //企业名称
        var meetingName = $("#meetingName").val();
        //请求参数
        var data = "{\"subjectName\":\""+subjectName+"\",\"meetingName\":\""+meetingName+"\"}";
        //请求路径
        var sURL = "{contextPath}/subjectTodo/querySubjectList.action?currentPage={page}&pageSize={pageSize}&status=1&parameter="+encodeURIComponent(data);
        jt.setAttr(_('#tabMain_List'),'URLData',sURL);
        _('#tabMain_List').loadData();
    }

    /**
     * 编辑议题
     * @param meetingId
     */
    function getEditMeeting(meetingId){
        var sURL = "/meeting/reloadMeeting.action?page=meeting_union_register&isTodo=1&meetingId="+meetingId;
        showDialog('会议协同填报',sURL,'meetingTodo',800,600,true);
    }
</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
