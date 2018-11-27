<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
    <%@ include file="/common/inc_head.jsp"%>
    <title>方案管理</title>
</head>
<script type="text/javascript">

    //添加服务
    function addRegulation(){
        var sURL = '{contextPath}/integrationCfg/toEditServiceConfigPage.action';
        top_showDialog('添加服务', sURL, 'WinEdit');
    }

    //编辑服务
    function editServiceConfig(serviceConfigId){
        var href = "{contextPath}/jsp/config/editServiceConfig.jsp?serviceConfigId=" + serviceConfigId;
        top_showDialog("服务编辑", href, 'WinEdit');
    }

    //删除服务
    function deleteServiceConfig(itemId){
        var arr = delItem_GetIDs(itemId,'txtID','请选择要删除的数据!','');
        var sURL = '{contextPath}/integrationCfg/deleteServiceConfig.action';
        postJSON(sURL, {ids: arr.toString()}, function (json, o) {
            if(json > 0) {
                jt.Msg.alert("删除成功", function () {
                    _('#tabMain_List').loadData();
                    closeSelf();
                });
            }else{
                jt.Msg.alert("删除失败");
            }
        });
    }

</script>

<body class="BodyTreeList">

<div id="divFixTop">
    <div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
        <div icon="add.png" onclick="addRegulation();">新建</div>
        <div icon="del.png" onclick="deleteServiceConfig('');">删除</div>
    </div>
</div>

<div id="divFixCnt" class="GridOnly">
    <table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List"" border="0"	cellspacing="0" cellpadding="0"
           URLData="{SYSURL.oa}/integrationCfg/querServiceCfgPageList.action?t={JSTime}&recycle=0&currentPage={page}&pageSize={pageSize}&{searchParam}"
           EmptyInfo="没有记录" width="100%" Action="editServiceConfig('{id}')">
        <col boxName="txtID" width="30" boxValue="{id}" boxAttr="{boxAttr}">
        <col caption="系统名称" field="{system_name}" align="center" width="100px">
        <col caption="对接单位" field="{organization_name}" align="center"  width="100px">
        <col caption="用户名" field="{user_name}" align="center" width="50px">
    </table>
</div>

</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
