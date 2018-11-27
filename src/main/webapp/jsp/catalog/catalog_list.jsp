<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
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
		sURL=sURL.replace(/\{curNavCode\}/ig, curNavID);
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
		//计算高度
		console.log();
		var doHeight = $(document).height();
		var topHeight = document.getElementById("divFixTop").offsetHeight;
		$("#catalogMain").height(doHeight - topHeight);
		loadTableData();
	}
	
	function jtAfterTreeViewLiteNodeFocus (oComp, oNode){
		curNavID = oNode.jsonItem.navId;
		curNavCode = oNode.jsonItem.navCode;
		navLevel = oNode.jsonItem.navLevel;
		if(navLevel == -1){
			$('#newTable').show();
			$('#oldTable').hide();
		}else{
			jt._('#tabMain_List').loadData();
			$('#newTable').hide();
			$('#oldTable').show();
		}
	}
	
	function editItem(catalogId){
		if(navLevel == -1){
			return;
		}
		var sURL='{contextPath}/jsp/catalog/catalog_update.jsp?t={JSTime}&catalogId='+catalogId+'&navLevel='+navLevel;
		if (catalogId=='') sURL += '&parId='+curNavID ;
		top_showDialog('新增/修改',sURL,'WinEdit',750,550);
	}

	function delItem(sID){
		var arr=delItem_GetIDs(sID,'txtID','请选择要删除的数据!','确实要删除吗？');
		if (arr.length==0) return;
		var sURL='{contextPath}/catalog/deleteCatalog.action';
		postJSON(sURL, {'ids':arr.toString()},function (json,o){
			if (json.errorCode!=0){ showMsg(json.errorInfo,'操作失败'); return; }
			_('#tabMain_List').loadData();
		});
	}
	
	jQuery.fn.rowspan = function(colIdx) { //封装的一个JQuery小插件
		return this.each(function() {
			var that;
			$('tr', this).each(function(row) {
				$('td:eq(' + colIdx + ')', this).filter(':visible').each(function(col) {
					if (that != null && $(this).html() == $(that).html()) {
						rowspan = $(that).attr("rowSpan");
						if (rowspan == undefined) {
							$(that).attr("rowSpan", 1);
							rowspan = $(that).attr("rowSpan");
						}
						rowspan = Number(rowspan) + 1;
						$(that).attr("rowSpan", rowspan);
						$(this).hide();
					} else {
						that = this;
					}
				});
			});
		});
	}

	function loadTableData(){
		var dataURL = "{contextPath}/catalog/queryTableCatalogMessage.action";
		var jPost= {};
		postJSON(dataURL,jPost,function(json,o){
			if (!json || json.errorCode != "0") {
				return jt.Msg.showMsg("获取数据失败！");
			} else {
				console.log(new Date());
				$("#catalogTable").empty();
				var level = json.data[0].level;
				var data = json.data[0].cataLog;
				var tableHtml = "";
				tableHtml = tableHtml + "<tr class='GridHead' style='text-align: center;'>";
				tableHtml = tableHtml + "<td style='border-left: 0px solid #c8c8c8;border-right: 1px solid #c8c8c8; border-bottom: 1px solid #c8c8c8;background-color: #ededed;'>序号</td>";
				tableHtml = tableHtml + "<td style='border-left: 0px solid #c8c8c8;border-right: 1px solid #c8c8c8; border-bottom: 1px solid #c8c8c8;background-color: #ededed;' colspan='"+level+"'>事项目录</td>";
				tableHtml = tableHtml + "<td style='border-right: none;border-left: 0px solid #c8c8c8;border-bottom: 1px solid #c8c8c8;background-color: #ededed;'>事项目录编码</td>";
				tableHtml = tableHtml + "</tr>";
				
				for (var i = 0; i < data.length; i++) {
					tableHtml = tableHtml + "<tr style='text-align: center;'>";
					tableHtml = tableHtml + "<td style='border-left: 0px solid #c8c8c8;border-right: 1px solid #c8c8c8; border-bottom: 1px solid #c8c8c8;background-color: #ededed;'>"+data[i].ROWNUM+"</td>";
					tableHtml = tableHtml + "<td style='border-left: 0px solid #c8c8c8;border-right: 1px solid #c8c8c8; border-bottom: 1px solid #c8c8c8;'>"+data[i].FNAME+"</td>";
					if(data[i].CNAME == 2 || data[i].CNAME == "2"){
						tableHtml = tableHtml + "<td style='border-left: 0px solid #c8c8c8;border-right: 1px solid #c8c8c8; border-bottom: 1px solid #c8c8c8;' colspan='2'>"+data[i].SNAME+"</td>";
					}else{
						tableHtml = tableHtml + "<td style='border-left: 0px solid #c8c8c8;border-right: 1px solid #c8c8c8; border-bottom: 1px solid #c8c8c8;'>"+data[i].SNAME+"</td>";
						tableHtml = tableHtml + "<td style='border-left: 0px solid #c8c8c8;border-right: 1px solid #c8c8c8; border-bottom: 1px solid #c8c8c8;'>"+data[i].CNAME+"</td>";
					}
					tableHtml = tableHtml + "<td style='border-right: none;border-left: 0px solid #c8c8c8;border-bottom: 1px solid #c8c8c8;background-color: #ededed;'>"+data[i].CATALOGCODE+"</td>";
					tableHtml = tableHtml + "</tr>";
				}
				$("#catalogTable").append(tableHtml);
				for (var i = 1; i <= level; i++) {
					$("#catalogTable").rowspan(i);
				}
				console.log(new Date());
			}
		},false);
	}
</script>
	
<body class="BodyTreeList">
	
<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div icon="add.png" id="addButton" onclick="editItem('')">新建</div>
		<div icon="del.png" id="delButton" onclick="delItem('')">删除</div>
        <!-- <div icon="export.png"  onclick="synchronous('catalog.xml')">同步</div> -->
	</div>
</div>
	
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
	<tr>
		<td width="180">
			<div id="divFixLeft">
					<div id="divTree" class="TreeViewLite" ExpandLevel="1"
						 URLNodeData="{contextPath}/catalog/queryUmsCatalogList.action?t={JSTime}&parentCode={navId}" AutoFocusFirst="true" TextField="{navName}" IconPath="{SYSURL.static}/images/icon16/" TreeStyle="Icon_Folder">
						<xmp class="data">{data:[{ navCode:'parent' ,navId:'parent',navLevel:'-1' , navName:'全部', childUrl:'{URLNodeData}'}]};</xmp>
					</div>
			</div>
		</td>
		<td class="SplitBar" width="3"><span></span></td>
		<td id="oldTable" style="display: none;">
			<div id="divFixMain" class="GridOnly">
				<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0"  Action="editItem('{navId}')"
					   URLData="{contextPath}/catalog/queryUmsCatalogList.action?t={JSTime}&parentCode={curNavCode}&currentPage={page}&pageSize={pageSize}&{searchParam}" EmptyInfo="没有记录" width="100%" AutoLoadData="false">
					<col boxName="txtID" width="30" boxValue="{navId}" boxAttr="{boxAttr}">
					<col caption="事项目录编码" field="{navCode}" width="40px" align="center">
					<col caption="事项目录名称" field="{navName}" width="100px" align="center" UseAction="1">
				</table>
			</div>
		</td>
		<td id="newTable" style="height:100%;">
			<div id="catalogMain" class="GridOnly" style="overflow-x:auto;overflow-y:auto;">
				<table id="catalogTable" border="0" cellspacing="0" cellpadding="3" style="width: 100%;">
					
				</table>
			</div>
		</td>
	</tr>
</table>
	
</body>
<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
