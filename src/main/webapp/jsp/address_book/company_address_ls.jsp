<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<title>本单位通讯录</title>
</head>
<script type="text/javascript">
	var _ID = "TXL_088";
	var sID = "TXL_088",sNAME = "8·8抗震救灾通讯录";
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
		var sQ='';
		var sSql='';
		for(var i=0;i<oForm.elements.length;i++){
			var obj=oForm.elements[i];
			if ((obj.name=='') || (obj.disabled) ) continue;
			if(typeof(fun_BtnSearchBefore)=='function'){
				var tmpStr=fun_BtnSearchBefore(obj,oForm);
				if(tmpStr){
					if (sQ!='') sQ += '||';
					sQ += tmpStr;
					continue;
				}
			}
			var sField=jt.getAttr(obj,'FieldName');
			var showTime= jt.getAttr(obj,'ShowTime',false);
			var sOper=jt.getAttr(obj,'Oper','=');
			var sValue=obj.value;
			sValue = sValue.replace(/(^\s*)|(\s*$)/g, "");//去除字符串前后空格 
			if(!sField)continue;
			if (sValue=='') continue;
			if (sQ!='') sQ += '||';
			if (sSql!='') sSql += encodeURIComponent('&&');
			sSql += '{'+sField+'}';
			var sType = jt.getAttr(obj,'FieldType','');
			
			if(/Input_DateTime/i.test(obj.className) && !(/Input_DateTimeRange/i.test(obj.className))){
				if(sOper == '<'){sValue +=(showTime?':59':' 23:59:59');}
				else{sValue +=(showTime?':00':' 00:00:00');}
			}
			if(i != oForm.elements.length-1){
				if(obj.name==oForm.elements[i+1].name && (/Input_DateTime/i.test(oForm.elements[i+1].className)) && oForm.elements[i+1].value != ''){//xiaodao
					var endDate = oForm.elements[i+1];
					var eValue = endDate.value;
					eValue +=(showTime?':59':' 23:59:59');
					sQ += sField+';between;'+sValue+','+eValue+';';
					i++;
					continue;
				}
			}
			if(/Input_DateTimeRange/i.test(obj.className)){
				var elem = sValue.split('至');
				if(elem[0] && elem[1]){
					var eValue=elem[0]+(showTime?':00':' 00:00:00')+','+elem[1]+(showTime?':59':' 23:59:59');
					sQ += sField+';between;'+ eValue;
				}else if(elem[0]) sQ += sField+';>;'+ elem[0]+(showTime?':00':' 00:00:00');
				else if(elem[1]) sQ += sField+';<;'+ elem[1]+(showTime?':59':' 23:59:59');
				continue;
			}

			if(jt.getAttr(obj,'SearchKey','')!='' && (/Input_Select/i.test(obj.className))){sValue = oForm.elements[i+1].value;i++;}
			if(sType == 'Company' || sType == 'Dept'){
				i++;
				if(/,/i.test(sValue)){
					sQ += sField+';in;'+encodeURIComponent(sValue);
					continue;
				}
			}
			sQ += sField+';'+sOper+';'+encodeURIComponent(sValue);
		}
		if(jt.getParam ('dq',sURL)!=''){
			var temp = sURL.split("&");
			for(var i= 0,j=temp.length;i<j;i++){
				var tempStr = temp[i];
				if(jt.getParam('dq',tempStr)!=''){
					if (sQ!='') temp[i]  += '||' + sQ;//判断URL是否带参数
				}
			}
			sURL = temp.join("&");
		}else{
			if (sQ!='') sURL += (sURL.indexOf('?')==-1?'?':'&') + 'dq=' + sQ;
		}
		if(jt.getParam ('dqsql',sURL)!=''){
			var temp = sURL.split("&");
			for(var i= 0,j=temp.length;i<j;i++){
				var tempStr = temp[i];
				if(jt.getParam('dqsql',tempStr)!=''){
					if (sSql!='') temp[i]  += encodeURIComponent('&&')+sSql;//判断URL是否带参数
				}
			}
			sURL = temp.join("&");
		}
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
</script>
<style type="text/css">
/** 表格文字超出显示范围时折行,不显示... **/
body .jtGridPar .GridCell{
  white-space:normal;
  height:auto;
}
</style>
<body class="BodyTreeList">
<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div icon="add.png" onclick="editItem('')">添加关联</div>
		<div icon="del.png" onclick="delItem('')">解除关联</div>
		<div id="divSitebar">
			<div id="divCurPosition" style="display:none"></div>
			<div id="divGridSearch" style="display:none" grid="tabMain_List" InputTip="" ></div>
		</div>
	</div>
</div>

<table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
	<tr>
		<td width="250">
			<div id="divFixLeft">
				<div id="divTree" class="TreeViewLite" ExpandLevel="1" TextField="{_NAME}" IconPath="{SYSURL.static}/images/icon16/" TreeStyle="Icon_Folder">
					<xmp class="data">{data:[{_ID:sID, _NAME:sNAME,icon:"org_org.png",childUrl:'{SYSURL.ums}/ums/tree/org.action?childUrl=UMS_OrgTreeUrl&Id={_ID}&maxLevel=100&idType=parentOrgId'}]};</xmp>
				</div>
			</div>
		</td>
		<td class="SplitBar" width="3"><span></span></td>
		<td>
		 <div id="divSearch" ShowCount="0" style12="display:none;">
			<div Caption="姓　　名" FieldName="USERNAME" Oper="like"></div>
		 </div>
			<div id="divFixCnt" class="GridOnly">
				<table class="Grid" style="text-align:center;" FixHead="true" id="tabMain_List" border="0" cellspacing="0" cellpadding="0" Action="editItem('{FID}')"  
					EmptyInfo="没有记录" width="100%">
					  <COL width=30 boxname="txtID" boxvalue="{FID}">
					  <col caption="序号" field="{VIEWNUM}" align="center" width="35px" />
					  <col caption="姓名" field="{USERNAME}" align="center" width="10%" />
					  <col caption="部门" field="{USERDEPT}" align="left" width="15%"/>
					  <col caption="职务" field="{USERTITLE}" width="6%"/>
					  <col caption="办公电话(直拨)" field="{OFFICETEL_CODE}-{OFFICETEL_NUM}"  />
					  <col caption="办公电话(系统)" field="{OFFICETEL_CODE_IN}-{OFFICETEL_NUM_IN}"  />
					  <col caption="住宅电话" field="{HOUSETEL_CODE}-{HOUSETEL_NUM}"  />
					  <col caption="手机号码" field="{MOBILETEL}"  />
					  <col caption="电子邮箱" field="{EMAIL}"  />
				</table>
			</div>
		</td>
	</tr>
</table>
		
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
