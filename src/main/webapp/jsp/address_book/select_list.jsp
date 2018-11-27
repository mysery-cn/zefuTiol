<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<title></title>
</head>
<script type="text/javascript">
	var jParam=parent.SelectDialogParam;
	var list, listResult, listSearch;
	
	var offsetFixHeight=45; //扣除顶部30高度
	function init(){
		//debugger;
		jParam.errorType = parent.SelectDialogParam.errorType;
		document.title = jt.getDefVal(jParam.title);
		try{ parent.findDialog(self).setTitle(document.title); }catch(e){}
		
		jt.FormatUI();
		list = jt._('#tabMain_List'); listResult = jt._('#listResult'); listSearch = jt._('#listSearch');
		jt.setAttr(list, 'html', jParam.field_text);
		if (jt.getDefVal(jParam.url_data)!=''){
				jt.setAttr(list, 'URLData', jParam.url_data);
			list.loadData();
		}else{
			if (typeof(jParam.json_data)=='object'){
				list.loadData(jParam.json_data);
			}
		}
		
		//初始化搜索
		if (jt.getDefVal(jParam.url_search)!='') {
			if (/^\[InnerSearch\]$/i.test(jParam.url_search)){
				jt._('#divFilter').style.display='';
				var sFields=jParam.field_InnerSearch;
				if (sFields=='') {
					sFields = unique( (jParam.field_text+jParam.field_id).match(/{.*?}/gi) ).toString();
					sFields = sFields.replace(/\{|\}/g,'');
				}
				jt.setAttr(list,'SearchField',sFields);
			}else{
				jt._('#divGridSearch').style.display='';
				jt.setAttr(listSearch,'URLData',jParam.url_search);
				jt.setAttr(listSearch,'html',jParam.field_text);
			}
		}
		
		//初始化已选项目
		if (jParam.input_id){
			var arrID=jParam.input_id.value.split(',');
			if (jParam.input_text){
				var arrText=jParam.input_text.value.split(',');
			}
			for (var i=0; i<arrID.length; i++){
				if(arrID[i]=='') continue;
				if (jParam.input_text){
					addItem_(arrID[i],arrText[i],false);
				}else{
					addItem_(arrID[i],'',false);
				}
			}
		}else if(jParam.errorType !=''){
			var sID = jParam.errorType;
			var sText = '';
			if(sID == '1'){
				sText = "错字";
			}else if(sID == '2'){
				sText = "迟报";
			}else if(sID == '3'){
				sText = "漏报";
			}else if(sID == '4'){
				sText = "信息有误";
			}else if(sID == '5'){
				sText = "信息不完整";
			}
			addItem_(sID,sText,false);
		}
		setButtonState();
	}
	
	function setButtonState(){
		//var bAdd=list.getSelectedItem().length>0;
		jt._('#btnAdd').disabled = list.getSelectedItem().length==0;
		jt._('#btnAddAll').disabled = list.getSelectedItem('jListItem').length==0;
		//jt._('#btnAdd').disabled = list.getSelectedItem().length==0;
		jt._('#btnDelAll').disabled = jt._('[children]div.item', listResult).length==0;
		//var items=jt._('[children]div.item', listResult);
	}
	
	function jtInitJtDataItem (oComp, jsonItem, idx){
		if (typeof(jParam.funJtInitJtDataItem)=='function') return jParam.funJtInitJtDataItem(oComp, jsonItem, idx);
	}
	function jtAfterListShowData (oComp){setTimeout(setButtonState,500);}
	
	var listCurItem;
	function jtAfterListItemClick(oComp, oItem, jsonItem, idx){

		setButtonState();
		listCurItem=jsonItem;
		addItem();
	}
	
	function findItemText(sID){
		var arr=list.json.data;
		for (var i=0;i<arr.length;i++){
			if (sID == jt.parseField( jParam.field_id, arr[i])) return jt.parseField( jParam.field_text, arr[i]);
		}
		return '';
	}
	function addItem(bAll){
		if (!bAll){
			if (list.getSelectedItem().length==0) return;
			var sID = jt.parseField( jParam.field_id, listCurItem);
			var sText=jt.parseField( jParam.field_text, listCurItem);
			addItem_(sID,sText,true);
			return;
		}
		var arr=list.json.data;
		for (var i=0;i<arr.length;i++){
			var sID = jt.parseField( jParam.field_id, arr[i]);
			var sText=jt.parseField( jParam.field_text, arr[i]);
			addItem_(sID,sText,false);
		}
	}
	function addItem_(sID,sText,bSel){
		if (/[^<]\//.test(sText)) sText = sText.split("/")[0];
		if (sText=='') sText=findItemText(sID);
		if (!jParam.multiSelect) delItem(true);
		var oDiv=findItemFromList(sID);
		if (oDiv){ //已添加
			selItem(oDiv);
			return;
		}
		if (!jParam.multiSelect) {
			var items=jt._('[children]div.item', listResult);
			for (var i=items.length-1; i>=0; i--) removeItemDiv(items[i]);
		}
		var oDiv = document.createElement("div");
		oDiv.className='item';
		oDiv.innerHTML = sText;
		jt.setAttr(oDiv, 'sID', sID);
		jt.setAttr(oDiv, 'sText', sText);
		oDiv.onmouseover=new Function('jt.addClass(this,\'itemOver\')');
		oDiv.onmouseout=new Function('jt.removeClass(this,\'itemOver\')');
		oDiv.ondblclick=new Function('delItem()');
		oDiv.onclick=new Function('selItem(this)');
		listResult.appendChild(oDiv);
		if (bSel) selItem(oDiv);
	}
	
	//删除
	function delItem(bAll){
		var items=jt._('[children]div.item', listResult);
		for (var i=items.length-1; i>=0; i--) {
			if (bAll || jt.hasClass(items[i],'itemSel')) removeItemDiv(items[i]);
		}
		jt._('#btnDel').disabled = true;
		setButtonState();
	}
	function removeItemDiv(oDiv){
		oDiv.onmouseover=null; oDiv.onmouseout=null; oDiv.ondblclick=null; oDiv.onclick=null;
		oDiv.parentNode.removeChild(oDiv);
		jt._('#btnDel').disabled = false;
		setButtonState();
	}
	//选中
	function selItem(obj){
		var items=jt._('[children]div.item', listResult);
		var oDiv=findItemFromList(obj);
		for (var i=0; i<items.length; i++) jt.removeClass(items[i],'itemSel');
		if (oDiv) jt.addClass(oDiv,'itemSel');
		jt._('#btnDel').disabled = false;
		setButtonState();
	}
	//从右侧列表中查找
	function findItemFromList(obj){
		if (typeof(obj)=='object') return obj;
		if (typeof(obj)=='string'){
			var items=jt._('[children]div.item', listResult);
			for (var i=0; i<items.length; i++) {
				if (jt.getAttr(items[i],'sID')==obj) return items[i];
			}
		}
	}
	
	function funOK(){
		var items=jt._('[children]div.item', listResult);
		var sID=''; var sText='';
		for (var i=0; i<items.length; i++){
			if (sID!='') { sID+=','; sText+=','; }
			sID   += jt.getAttr(items[i],'sID');
			sText += jt.getAttr(items[i],'sText');
		}
		if (jParam.input_id) {
			jParam.input_id.value=sID;
			if (jParam.input_id.onchange) jParam.input_id.onchange();
		}
		if (jParam.input_text) {
			jParam.input_text.value=sText;
			if (jParam.input_text.onchange) jParam.input_text.onchange();
		}
		if (typeof(jParam.funAfterSelect)=='function'){
			jParam.funAfterSelect(sID,sText);
			setTimeout(function(){ parent.closeDialog(self); },100);
			return;
		}
		parent.closeDialog(self);
	}
	
	
	function funGridSearch_Custom(oGrid){
		jt._('#tabMain_List').style.display='none';
		listSearch.style.display='';
		listSearch.innerHTML="正在加载...";
		listSearch.loadData();
	}
	function funSearchBack_Custom(oGrid){
		jt._('#tabMain_List').style.display='';
		listSearch.style.display='none';
	}
	
	var timDoFilter;
	function doFilter(){
		clearTimeout(timDoFilter);
		timDoFilter = setTimeout(function(){
			var sKey=jt._('#txtFilter').value;
			list.search(sKey);
		},100);
	}
	
	//删除重复数组
	function unique(arr) {
		var result = [], hash = {};
		for (var i = 0, elem; (elem = arr[i]) != null; i++) {
			if (!hash[elem]) {
				result.push(elem);
				hash[elem] = true;
			}
		}
		return result;
	}
</script>
<style type="text/css">
	#listResult .item{padding: 3px; cursor: pointer; line-height: 21px;}
	#listResult .itemOver{background-color:#FFF3CB;}
	#listResult .itemSel{background-color:#ebebeb;}
	
	.jListItem {line-height: 21px;}
</style>


<body class="BodyTreeList">

<table width="100%" style="table-layout:fixed;" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td class="Select_Caption" style="position:relative;" colspan="1">
			待选项
			<div id="divGridSearch" grid="listSearch" InputTip="搜索" style="display:none; right: auto; left:50px; top:0px;"></div>
			<div id="divFilter" class="divGridSearch" style="display:none; right: auto; left:50px; top:0px;">
				<input type="text" class="input" id="txtFilter" onkeyup="doFilter()" onpaste="doFilter()">
			</div>
		</td>
		<td class="Select_Caption">已选项</td>
	</tr>
	<tr>
		<td bgcolor="white">
			<div id="divFixLeft" class="ScrollBox inputGray">
				<div class="List" id="tabMain_List" ListStyle="{CST_LISTSTYLE_21}" PageStyle="<div style='text-align:center;'>{CST_PAGESTYLE_19}</div>"></div>
				<div id="listSearch" class="List" ListStyle="{CST_LISTSTYLE_21}" style="display: none;"></div>
			</div>
		</td>
		<td bgcolor="white">
			<div id="divFixMain" class="ScrollBox inputGray">
				<div id="listResult"></div>
			</div>
		</td>
	</tr>
</table>
<div style="display:none;">
	<input type="button" id="btnAddAll" value="&gt;&gt;" disabled onclick="addItem(true)" style="display:none;">
	<input type="button" id="btnAdd" value="&gt;" disabled onclick="addItem()" style="display:none;">
	<input type="button" id="btnDel" value="&lt;" disabled onclick="delItem()" style="display:none;">
	<input type="button" id="btnDelAll" value="&lt;&lt;" disabled onclick="delItem(true)" style="display:none;">
</div>

	

<div id="divFixBottom" class="FixBottomButton">
	<input type="button" class="btnOK" value="确定" onclick="funOK()">
	<input type="button" class="btnCancel" value="取消" onclick="closeSelf()">
</div>

</body>
</html>

<%@ include file="/common/inc_bottom.jsp"%>
