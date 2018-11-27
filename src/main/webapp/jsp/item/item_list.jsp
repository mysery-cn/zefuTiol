<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/index.css" type="text/css"/>
	<title>事项目录</title>
</head>
<script type="text/javascript">
	var curNavID = "";
	var curNavCode = "";
	var navLevel = "";
	function jtParseURL_Page(sURL){
		if(curNavCode == "undefind"){
			curNavCode = "";
		}
		if(curNavID == "undefind"){
			curNavID = "";
		}
		sURL=sURL.replace(/\{curNavID\}/ig, curNavID);
		sURL=sURL.replace(/\{curNavCode\}/ig, curNavCode);
		sURL=sURL.replace(/\{navLevel\}/ig, navLevel);
		return sURL;
	}
	
	function init(){ 
		var dataURL = "{contextPath}/catalog/queryCatalogRole.action";
		var jPost= {};
		postJSON(dataURL,jPost,function(json,o){
			if (!json || json.errorCode != "0") {
				return jt.Msg.showMsg("获取数据失败！");
			} else {
				if(json.data[0] == 0){
					$("#addButton").css('display','none'); 
					$("#delButton").css('display','none'); 
				}
			}
		},false);

		//判断同步是否隐藏
        if(curUser.companyId == "GZW"){
            $("#synchronous").hide();
        }
	}
	
	function jtAfterTreeViewLiteNodeFocus (oComp, oNode){
		curNavID = oNode.jsonItem.navId;
		curNavCode = oNode.jsonItem.navCode;
		navLevel = oNode.jsonItem.navLevel;
		jt._('#tabMain_List').loadData();
	}

    /**
     * 解析排序顺序
     */
    function formatStatus(meetingDetail){
        if(meetingDetail == "" || meetingDetail == null || meetingDetail == "undefind"){
            return "";
        }
        var order = meetingDetail.split(",");
        var detail = '';
        for (var i = 0; i < order.length; i++) {
            detail = detail + '<td style="text-align: left;">';
            detail = detail + '<div class="bg_meeting"><div class="bg_meet_left"></div><div class="meeting_name_bg">'+order[i]+'</div><div class="bg_meet_right"></div></div>';
            if(i < order.length - 1){
                detail = detail + '<div class="meetting_line"></div>';
            }
            detail = detail + '</td>';
        }
        return detail;
    }
	
</script>
	
<body class="BodyTreeList">
<div id="divFixTop">
    <div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
        <!-- <div icon="export.png" id="synchronous"  onclick="synchronous('item.xml')">同步</div> -->
    </div>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
	<tr>
		<td width="180">
			<div id="divFixLeft">
				<div id="divTree" class="TreeViewLite" ExpandLevel="1"
					 URLNodeData="{contextPath}/catalog/queryUmsCatalogList.action?t={JSTime}&parentCode={navId}" AutoFocusFirst="true" TextField="{navName}" IconPath="{SYSURL.static}/images/icon16/" TreeStyle="Icon_Folder">
					<xmp class="data">{data:[{ navCode:'ROOT' ,navId:'ROOT', navLevel:'0' ,navName:'事项目录', childUrl:'{URLNodeData}'}]};</xmp>
				</div>
			</div>
		</td>
		<td class="SplitBar" width="3"><span></span></td>
		<td>
			<div id="divFixMain" class="GridOnly">
				<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"
					   URLData="{contextPath}/item/queryItemListByPage.action?t={JSTime}&navLevel={navLevel}&catalogCode={curNavCode}&catalogId={curNavID}&currentPage={page}&pageSize={pageSize}&{searchParam}" EmptyInfo="没有记录" width="100%" AutoLoadData="false">
				<col caption="序号" field="{RN}" width="20" align="center">
				<col caption="事项名称" field="{itemName}" width="50" align="center">
				<col caption="事项编码" field="{itemCode}" width="50" align="center">
				<col caption="决策会议及顺序" field="[=formatStatus('{meetingDetail}')]" width="180" align="left">
				</table>
			</div>
		</td>
	</tr>
</table>
	
</body>
<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>