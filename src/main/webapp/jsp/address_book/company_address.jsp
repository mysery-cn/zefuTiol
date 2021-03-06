<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<title>本单位通讯录</title>
</head>
<script type="text/javascript">
	var _ID = jt.getParam('orgid');
	var sID = jt.getParam('orgid');sNAME = jt.getParam('orgname');
	//TXL_000   四川省厅局通讯录
	var sURLData = "{SYSURL.oa}/address_book/address/getDataList.action?USERDEPTID="+_ID+"&currentPage={page}&pageSize=20"
	function jtParseURL_Page(sURL){
		sURL=sURL.replace(/\{_ID\}/ig, _ID);
		return sURL;
	}
	function init(){
		showData();
	}
	function jtAfterTreeViewLiteNodeClick(oComp, oNode){
		_ID=oNode.jsonItem._ID;
		sURLData="{SYSURL.oa}/address_book/address/getDataList.action?USERDEPTID="+_ID+"&currentPage={page}&pageSize=20";
		showData();
	}
	//显示数据
	function showData(){
		var aGrid = jt._('#tabMain_List');
		jt.setAttr(aGrid,'URLData',sURLData);
		aGrid.loadData();
	}
	//触发选中默认的节点
	function jtAfterTreeViewLiteShowData (oComp, oNode){
		//alert(jt.Obj2Str(oComp));
		setTimeout(function(){ oComp.nodeFocus(oComp.findNode('_ID='+_ID)); },100);
	}
	//搜索提交
	function funGridSearch_Custom(aGrid,oForm){
		funJS_GridSearch_Custom(aGrid,oForm);
	}
	//搜索返回
	function funSearchBack_Custom(aGrid,oForm){
		jt.setAttr(aGrid,'URLData',sURLData);
		aGrid.loadData();
	}
	function funJS_GridSearch_Custom(aGrid,oForm){
		var sURL=sURLData;
		sURL +='&USERNAME='+encodeURIComponent(oForm.USERNAME.value);
		jt.setAttr(aGrid,'URLData',sURL);
		aGrid.loadData();
	}
	function resizeFixDivAfter(){
	  jt._('#divFixLeft').style.height = (document.body.clientHeight-jt._('#divFixTop').offsetHeight) + 'px';
	}
	
	function editItem(sID) {
		var type="update";
		if(sID==''){
			sID = _ID;
			type="insert";
		}
		var sURL = '{SYSURL.oa}/jsp/address_book/addressformUpdate.jsp?t={JSTime}&id=' + sID+'&type='+type;
		top_showDialog( '新增/修改', sURL, 'WinEdit', 800, 450);
	}
	
	function delItem(){
		var aForm=jt._('#'+ jt.getAttr(document.body,'mainForm','frmMain'));
		//alert("aForm = " + jt.Obj2Str(aForm));
		var selChk = jt.getCheckBoxValue("txtID", true);
		if(selChk == "" || selChk == null){
			alert("请选择需要删除的通讯录人员！");return;
		}
		parent.jtConfirm("请确认是否解除人员关系！", function (bYes) {
			if (bYes == true) {
				var sURL = "{SYSURL.oa}/address_book/address/updatDatagx.action?FID=" + selChk;
				//alert(sURL);
				getJSON(sURL,null,false);
				reloadFrameMainGrid(aForm);
			}
			parent.showMsg("解除人员关系成功!");
		});
	}
	//-------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------
	function jtInitJtDataItem (oComp, jsonItem, idx){
		if(jsonItem.USE=='0') jsonItem.USE = '<span style="color:red">未授权</span>';
		else jsonItem.USE = '<span style="color:green">已授权</span>';
	}

	//添加人员
	function addPers(sID){
		sID = _ID;
		var sURL = '{SYSURL.oa}/jsp/address_book/addressform.jsp?t={JSTime}&orgid=' + sID;
		top_showDialog( '新增/修改', sURL, 'WinEdit', 800, 400);
	}
	//编辑人员
	function EditPers(sID){
		var sURL = '{SYSURL.oa}/jsp/address_book/addressform.jsp?t={JSTime}&id=' + sID;
		top_showDialog( '新增/修改', sURL, 'WinEdit', 800, 400);
	}
	//删除人员
	function delPers(sID){
		var arr = delItem_GetIDs(sID, 'txtID', '请选择要删除的数据!', '确实要删除吗？');
		if (arr.length == 0)
			return;
		var sURL = '{SYSURL.oa}/address_book/address/deleteAddressPeople.action?ids='+arr;
		postJSON(sURL, {
			'ids': arr.toString()
		}, function(json, o) {
			if (json.errorCode != 0) {
				showMsg(json.errorInfo, '操作失败', 2000);
				return;
			}
			if (json.errorCode==0){ showMsg('删除成功') }
			_('#tabMain_List').loadData();
		});
	}
	//人员授权
	function authorisePers(){
		var arrID = jt.getCheckBoxValue ('txtID');
		var data = {ids:arrID.join(",")}
		var sURL = '{SYSURL.oa}/address_book/address/useAddressPeople.action';
		postJSON(sURL,data,function(json){
			if(json.errorCode ==-1) {showMsg(json.errorInfo, '授权出错！', 2000);return;}
			_('#tabMain_List').loadData();
			jt.Msg.showMsg('授权成功！'); 
		});
	}
	//人员取消授权
	function unauthorisePers(){
		var arrID = jt.getCheckBoxValue ('txtID');
		var data = {ids:arrID.join(",")}
		var sURL = '{SYSURL.oa}/address_book/address/unuseAddressPeople.action';
		postJSON(sURL,data,function(json){
			if(json.errorCode ==-1) {showMsg(json.errorInfo, '取消授权出错！', 2000);return;}
			_('#tabMain_List').loadData();
			jt.Msg.showMsg('操作已保存！'); 
		});
	}

	//导出Excel
	function printExcel(){
		_printGrid('',jt._('#tabMain_List'),'','','excel');
	}
	/**
 * 打印 Grid、视图
 * @param {String=document.title} sTitle 打印标题
 * @param {Element} oGrid 要打印的表格对象 默认取第一个 Grid
 * @param {String} sURLData 表格数据 默认取 oGrid 的 URLData
 * @Tag print view
 */ 
function _printGrid(sTitle, oGrid, sURLData,sShowCol,sExport){
	var json = {};
	json.pageType = 'VIEW';
	json.title = (typeof(sTitle)=='string')?sTitle:document.title;
	json.oGrid = oGrid;
	json.showCol = sShowCol;
	json.sExport = sExport;
	if (typeof(oGrid)!='object') json.oGrid = jt._('Table.Grid')[0];
	if (typeof(sURLData)=='string') json.URLData = sURLData;
	window.printParam=json;
	showWindow('{SYSURL.oa}/jsp/address_book/_print.jsp',"导出excel");
}

</script>
<style type="text/css">
/** 表格文字超出显示范围时折行,不显示... **/
body .jtGridPar .GridCell{
  white-space:normal;
  height:auto;
}

</style>
<body class="BodyTreeList">
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
	<tr>
		<td width="250" Valign=top >
			<div id="divFixLeft">
				<div id="divTree" class="TreeViewLite" ExpandLevel="1" TextField="{_NAME}" IconPath="{SYSURL.static}/images/icon16/" TreeStyle="Icon_Folder">
					<xmp class="data">{data:[{_ID:sID, _NAME:sNAME,icon:"org_org.png",childUrl:'{SYSURL.ums}/ums/tree/org.action?childUrl=UMS_OrgTreeUrl&Id={_ID}&maxLevel=100&idType=parentOrgId'}]};</xmp>
				</div>
			</div>
		</td>
		<td class="SplitBar" width="3"><span></span></td>
		<td>
		<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<!-- <div icon="add.png" onclick="editItem('')">添加关联</div>
		<div icon="del.png" onclick="delItem('')">解除关联</div> -->
		<div icon="add.png" onclick="addPers()">添加</div>
		<div icon="del.png" onclick="delPers()">删除</div>
		<div icon="ok2.png" onclick="authorisePers()">授权</div>
		<div icon="cancel.png" onclick="unauthorisePers()">取消授权</div>
		<div icon="16_65.png" onclick="printExcel()">导出Excel</div>
		<div id="divSitebar">
			<div id="divCurPosition" style="display:none"></div>
			<div id="divGridSearch" style="display:none" grid="tabMain_List" InputTip="" ></div>
		</div>
	</div>
</div>
		 <div id="divSearch" ShowCount="0" style12="display:none;">
			<div Caption="姓　　名" FieldName="USERNAME" Oper="like"></div>
		 </div>
			<div id="divFixCnt" class="GridOnly">
				<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0" Action="EditPers('{FID}')"  
					EmptyInfo="没有记录" width="100%">
					  <COL width=30 boxname="txtID" boxvalue="{FID}" _FID="{FID}">
					  <col caption="序号" field="{VIEWNUM}" align="center" width="35px" />
					  <col caption="姓名" field="{USERNAME}" align="center" width="10%" />
					  <col caption="部门" field="{USERDEPT}" align="left" width="15%"/>
					  <col caption="职务" field="{USERTITLE}" width="6%"/>
					  <col caption="办公电话(直拨)" field="{OFFICETEL_CODE}-{OFFICETEL_NUM}"  />
					  <!-- <col caption="办公电话(系统)" field="{OFFICETEL_CODE_IN}-{OFFICETEL_NUM_IN}"  />
					  <col caption="住宅电话" field="{HOUSETEL_CODE}-{HOUSETEL_NUM}"  /> -->
					  <col caption="手机号码" field="{MOBILETEL}"  />
					  <col caption="电子邮箱" field="{EMAIL}"  />
					  <col caption="是否授权" align="center" field="{USE}"  />
				</table>
			</div>
		</td>
	</tr>
</table>
		
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
