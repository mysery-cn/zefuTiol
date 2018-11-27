var CST_Paper={ //页面宽高, 边距
	width:210, height:297,
	paddingLeft:19.1, paddingRight:19.1,
	paddingTop:19.1, paddingBottom:19.1
}
var CST_DrawPage=false; //是否用JS计算并显示分页
var hasWebExt=false;  //是否安装WebExt控件

var win=opener;
var jPrint = win.printParam;
var divMain; var divTop;


function init(){
	//setTimeout(funExportExcel_All,500)
	document.body.className=win.document.body.className;
	//复制上级页面样式
	var arr=win.document.styleSheets;
	for (var i=0; i<arr.length; i++) { if (arr[i].href=='') { jt.appendCSS(arr[i].cssText); } }
	//复制上级JS
	if(win.getPrintDependJS){
		var arrJS = win.getPrintDependJS();
		for (var i=0; i<arrJS.length; i++){
			if(arrJS[i].jsText) jt.appendJS(arrJS[i].jsText);
			if(arrJS[i].src) jt.loadJS(arrJS[i].src);
		}
	}

	divTop = jt._('#divTop'); divMain = jt._('#divMain');
	loadWebExt();
	if (!hasWebExt) { InstallWebExtTip(); }

	//设置页眉页脚
	PageSetup_SaveOldValue();
	PageSetup_SetGAP();
	jt.addEvent(window,'onbeforeunload', function(){
		//还原页眉页脚
		PageSetup_Restore();
	});

	///////////////TODO  非IE下横向打印未处理
	if (!jt.bIE){
		jt._('#btn_PrintSetup').style.display = 'none';
		jt._('#btn_PrintPreview').style.display = 'none';
		jt._('#btn_Export').style.display = 'none';
		jt._('#btn_Export_All').style.display = 'none';
		
		CST_DrawPage=true;
		//CST_Paper.paddingTop=25;
		CST_Paper.paddingBottom=38;
	}
	
	setPageStyle(); //设置页面 宽度  高度  边距
	document.title = jPrint.title||'打印';
	if (jPrint.pageType=='VIEW') loadView();
	if (jPrint.pageType=='FORM') loadForm();
	if (jPrint.pageType=='HTML') loadView();
	if (jPrint.pageType=='READDO') loadForm();
	jt('#Toolbar_'+jPrint.pageType).show();
}

// --------------------设置页面 宽度  高度  边距[Begin]----------------------------------------
function getCssRule(sID){ //获取页面样式
	var cssMain = document.styleSheets['cssMain'];
	var arr = (cssMain.cssRules)?cssMain.cssRules:cssMain.rules;
	for (var i=0; i<arr.length; i++){ if (arr[i].selectorText.toLowerCase()==sID.toLowerCase()) return arr[i]; }
}
function setPageStyle(){
	if (hasWebExt) {
		function __getMargin(sMargin){
			var str=doAction('GetPageSetup','margin_'+sMargin);
			if (str=='') return 19.1;
			return parseFloat(str)*25.4;
		}
		CST_Paper.paddingLeft = __getMargin('left');
		CST_Paper.paddingRight = __getMargin('right');
		CST_Paper.paddingTop = __getMargin('top');
		CST_Paper.paddingBottom = __getMargin('bottom');		
	}
	
	var css=getCssRule('.OnePage'); //设置页面 宽度  高度  边距
	try{
		css.style.width = CST_Paper.width+'mm';              css.style.height = CST_DrawPage?(CST_Paper.height+'mm'):'auto';
		css.style.paddingLeft = CST_Paper.paddingLeft+'mm';  css.style.paddingRight = CST_Paper.paddingRight+'mm';
		css.style.paddingTop = CST_Paper.paddingTop+'mm';    css.style.paddingBottom = CST_Paper.paddingBottom+'mm';
	}catch(e){}

	
	css=getCssRule('.BodyPrint .OnePage');
	//var iW=CST_Paper.width - CST_Paper.paddingLeft - CST_Paper.paddingRight - 0.1;
	css.style.width = 'auto';//iW+'mm';
	
	css=getCssRule('.OnePageIn');
	var iH=CST_Paper.height-CST_Paper.paddingTop-CST_Paper.paddingBottom-0.1;
	css.style.height = CST_DrawPage?(iH+'mm'):'auto';
}
function getHeadBGColor(){
	function RGBToHex(rgb){
		if (rgb.charAt(0) == '#') return rgb;
		var regexp = /[0-9]{0,3}/g;  
		var re = rgb.match(regexp);//利用正则表达式去掉多余的部分，将rgb中的数字提取
		var hexColor = "#"; var hex = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'];  
		for (var i = 0; i < re.length; i++) {
			var r = null, c = re[i], l = c; 
			var hexAr = [];
			while (c > 16){  
				r = c % 16;  
				c = (c / 16) >> 0; 
				hexAr.push(hex[r]);  
			} hexAr.push(hex[c]);
			if(l < 16&&l != ""){        
			hexAr.push(0)
			}
			hexColor += hexAr.reverse().join(''); 
		}
		return hexColor;
	}
	var css=getCssRule('.VIEW_Table thead td');
	var str = css.style.backgroundColor;
	return RGBToHex(str);
}
// --------------------设置页面 宽度  高度  边距[End]----------------------------------------

//生成新的分页DIV
function insertOnePage(sHTML){
	//清除HTML中的事件
	sHTML = sHTML.replace(/ (onclick|onmouseover|onmouseout)=/ig,' on__=');
	var div = document.createElement('div');
	divMain.appendChild(div);
	div.className = 'OnePage';
	div.innerHTML = '<div class="OnePageIn">' + sHTML + '</div>';
	if(typeof(win.print_InsertOnePage_After)!='undefined') win.print_InsertOnePage_After(self, div, jt('div.OnePageIn').length);
	return div;
}


// --------------------加载视图[Begin]----------------------------------------
//选择要打印的列
function selectViewCol(){
	var sDef=''; var sDefName='';
	var colGrid=jPrint.oGrid.getElementsByTagName('col');
	var arr=[];
	jt.each(colGrid,function(idx,obj){
		if (jt.getAttr(obj,'boxName')!='') return;
		var item={};
		item.id = idx.toString();//jt.getAttr(obj,'Field');
		item.text=jt.getAttr(obj,'Caption');
		sDef += (sDef==''?'':',') + idx.toString();
		sDefName += (sDefName==''?'':',') + item.text;
		arr.push(item);
	});
	var txtCol=_('#txtViewCol'); var txtColName=_('#txtViewColName');
	if (txtCol.value=='') {
		txtCol.value=sDef;
		txtColName.value=sDefName;
	}
  var jParam = {};
  jParam.input_text = txtColName;
  jParam.input_id = txtCol;
  jParam.json_data = arr;//jt.Str2JsonEx('111|aaaa,222|bbb');
	jParam.funAfterSelect = selectViewCol_After;
  funSelectList(jParam);
}
function selectViewCol_After(sID,sText){
	jPrint.showCol = sID;
	loadView()
}

//获取视图URL
function loadView_getURL(iCurPage,iPageSize){
	var oGrid=jPrint.oGrid;
	if (typeof(oGrid)!='object') oGrid=jPrint.oGrid=win.jt._('table.grid')[0];
	var sURL=jt.getDefVal(jPrint.URLData);
	if (sURL==''){
		sURL=jt.getAttr(oGrid,'URLData');
		sURL=delURLParam(sURL, 'currentPage');
		sURL=delURLParam(sURL, 'pageSize');
		if (iCurPage>-1) sURL=addURLParam(sURL, 'currentPage', iCurPage, false);
		if (iPageSize>-1) sURL=addURLParam(sURL, 'pageSize', iPageSize, false);
	};
	if (typeof(sURL)=='string') sURL=win.jt.parseURL(sURL);
	return sURL;
}
//获取视图表格
function loadView_getViewTableInfo(){
	var oGrid=jPrint.oGrid;
	var colGrid=oGrid.getElementsByTagName('col');
	var cols=[];
	function __AddCol(idx){
		var item={};
		item.width = jt.getAttr(colGrid[idx],'width', jt.getAttr(colGrid[idx],'_width'));
		item.cssText = colGrid[idx].style.cssText;
		item.caption = jt.getAttr(colGrid[idx],'caption');
		item.align = jt.getAttr(colGrid[idx],'align');
		item.field = jt.getAttr(colGrid[idx],'Field');
		cols.push(item);
	}
	//var sShowCol=jt('#txtViewCol').val();
	if (jt.def(jPrint.showCol)==''){
		jt.each(colGrid,function(idx,obj){
			if (jt.getAttr(obj,'boxName')!='') return;
			if (!jt.getAttr(obj,'print',true)) return;
			__AddCol(idx);
		});
	}else{
		jt.each(jPrint.showCol.split(','),function(idx,str){
			var intTem=jt.def(str,-1);
			if (intTem<0) return;
			__AddCol(intTem);
		})
	}
	var sHeadColor='';
	try{ sHeadColor=getHeadBGColor(); }catch(e){}
	
	//生成表头模板
	var sTable='<table class="VIEW_Table" width="100%" border="1" cellspacing="0" cellpadding="3" style="border-collapse:collapse">';
	sTable += '<thead style="display:table-header-group; background-color:'+sHeadColor+'" ><tr>';
	for (var i=0; i<cols.length; i++){
		sTable += '<td bgcolor="'+sHeadColor+'" ';
		sTable += addPrefix(' width="', cols[i].width, '" ');
		sTable += addPrefix(' style="', cols[i].cssText, '" ');
		sTable += '>';
		sTable += cols[i].caption;
		sTable += '</td>';
	}
	sTable += '</tr></thead><tbody>';
	var ret={};
	ret.cols = cols;
	ret.tableHTML = sTable;
	ret.tableHTMLEnd = '</tbody></table>';
	return ret;
}

//var timTemp=0;
//var intPageSize=0;
//var bQuickLoad=false;
//加载json
function loadView(sPage){
	//timTemp=(new Date()).getTime();
	sPage=jt.def(sPage,jt('#txtPageInfo').val());
	if (sPage=='') sPage='100/1';
	jt('#txtPageInfo').val(sPage);
	var iCurPage = jt.def(sPage.split('/')[1],1);
	var iPageSize = jt.def(sPage.split('/')[0],100);
	
	var sURL = loadView_getURL(iCurPage,iPageSize);
	
	divMain.innerHTML = 'Loading';
	
	//采用快速加载模式
//	bQuickLoad = (iPageSize>400)&&(jt.bIE);
//	jt('#lblQuickLoad').toggle(bQuickLoad);

	showLoading(true);
	
	if (typeof(sURL)=='object') {
		loadView_ShowData(sURL);
	}else{
		getJSON(sURL,loadView_ShowData);
	}
}
//画表格
function loadView_ShowData(json){
	//var timTemp2=(new Date()).getTime();
	//jt.log( (timTemp2-timTemp) + '  - 数据加载完成.') ;
	divMain.innerHTML = '';
	var oGrid=jPrint.oGrid;
	var arr=json.data;
	
	var iSeqBase=0;
	if (json.pageSize) {
		iSeqBase=(json.currentPage-1)*json.pageSize;
		ExpAll_iTotalCount=json.totalCount;
	}
	//初始化JSON数据
	for (var i=0; i<arr.length; i++) {
		arr[i].jtSeq = arr[i].jtSeqCurPage = i+1+iSeqBase;
	}
	
	//bQuickLoad=false;
	//快速加载时不初始化数据
	//if ((typeof(win.jtInitJtDataItem)!='undefined') && (!bQuickLoad)) {
	if ((typeof(win.jtInitJtDataItem)!='undefined')) {
		for (var i=0; i<arr.length; i++) {
			try{ win.jtInitJtDataItem(oGrid, arr[i], i); }catch(e){} 
		}
	}
	//jt.log( ((new Date()).getTime()-timTemp2) + '  - 调整数据.',0) ;
	
	//标题
	var sHead = addPrefix('<H1 style="text-align: center;">', jPrint.title, '</H1>');
	//生成表头模板
	var ret=loadView_getViewTableInfo();
	var sTable = ret.tableHTML + ret.tableHTMLEnd;
	var cols = ret.cols;

	
	var div; var divIn; var oTab;
	div = insertOnePage(sHead+sTable);
	divIn=jt._('div',div)[0];
	oTab=jt._('table',div)[0];
	
	if (CST_DrawPage){
		for (var i=0; i<arr.length; i++){
			var nRow=loadView_AddRow(arr[i],oTab.tBodies[0],cols);
			if (divIn.offsetHeight < divIn.scrollHeight - 1){ //超过高度,  分页
				nRow.parentNode.removeChild(nRow); i--;
					div = insertOnePage(sTable);
					divIn=jt._('div',div)[0];
					oTab=jt._('table',div)[0];
			}
		}
	}else{
		var divTem=document.createElement('div');
		divTem.innerHTML='<table><tbody></tbody></table>';
		var tbody=divTem.childNodes[0].tBodies[0];
		
		for (var i=0; i<arr.length; i++){
			//var nRow=loadView_AddRow(arr[i],oTab.tBodies[0],cols);
			var nRow=loadView_AddRow(arr[i],tbody,cols);
		}
		
		oTab.appendChild( tbody);
		document.body.appendChild(divTem);
		document.body.removeChild(divTem);

	}
	var pages=jt._('div.OnePage');
	pages[pages.length-1].style.pageBreakAfter='auto';


	if(typeof(printAfterViewShowData)=='function') printAfterViewShowData();
	//document.createElement('tbody')
	//jt.log( ((new Date()).getTime()-timTemp2) + '  - 数据显示完成.',0) ;
	showLoading(false);
}
//插入行
function loadView_AddRow(item,tbody,cols){
	var temTR=tbody.insertRow(tbody.rows.length);
	var idx=0;
	for (var i=0; i<cols.length; i++){
		var temTD=temTR.insertCell(idx);
		idx++;
		if (cols[i].cssText!='') temTD.style.cssText = cols[i].cssText;
		if (cols[i].align!='') temTD.align=cols[i].align;
		var sVal = jt.parseField(cols[i].field,item);
		temTD.innerHTML=sVal;
	}
	return temTR;
}
// --------------------导出全部视图---------------------
var ExpAll_iPage=0;
var ExpAll_iTotalCount=0;
var ExpAll_HTML='';
var ExpAll_TableInfo={};
//初始化
function funExportAllView(callback){
	ExpAll_iPage=1;
	ExpAll_HTML='';
	ExpAll_TableInfo = loadView_getViewTableInfo();
	funExportAllView_loadData(callback);
}
//加载数据
function funExportAllView_loadData(callback){
	var iPageSize = 1000;
	showMsg('加载数据：' + (ExpAll_iPage*iPageSize) + ' / ' + ExpAll_iTotalCount,'',20);
	
	var sURL = loadView_getURL(ExpAll_iPage,iPageSize);
	getJSON(sURL,function(json){
		ExpAll_iTotalCount=json.totalCount;
		var arr=json.data||[];
		var bEnd=arr.length==0;
		if (!bEnd) {
			bEnd = json.currentPage*json.pageSize >= json.totalCount;
			if (bEnd) debugger;
		}
		funExportAllView_parseHTML(json);
		//return;
		if (bEnd){ //加载结束
			showMsg('加载结束','',1);
			//标题
			var sHead = addPrefix('<H1 style="text-align: center;">', jPrint.title, '</H1>');
			//生成表头模板
			var sHTML='';
			sHTML += sHead;
			sHTML += ExpAll_TableInfo.tableHTML;
			sHTML += ExpAll_HTML;
			sHTML += ExpAll_TableInfo.tableHTMLEnd;
			
			if (typeof(callback)=='function') callback(sHTML);
		}else{
			ExpAll_iPage++;
			funExportAllView_loadData(callback);
		}
	});
}
//生成HTML
function funExportAllView_parseHTML(json){
	var oGrid=jPrint.oGrid;
	var arr=json.data;
	var iSeqBase=0;
	if (json.pageSize) iSeqBase=(json.currentPage-1)*json.pageSize;
	//初始化JSON数据
	for (var i=0; i<arr.length; i++) {
		arr[i].jtSeq = arr[i].jtSeqCurPage = i+1+iSeqBase;
	}
	if ((typeof(win.jtInitJtDataItem)!='undefined')) {
		for (var i=0; i<arr.length; i++) {
			try{ win.jtInitJtDataItem(oGrid, arr[i], i); }catch(e){} 
		}
	}
	
	var cols=ExpAll_TableInfo.cols;
	
	jt.each(arr,function(idx,item){
		ExpAll_HTML += '<tr>';
		jt.each(cols,function(idx0,col){
			var sStyle='';
			var sAlign='';
			if (col.cssText!='') sStyle = ' style="' + col.cssText + '"';
			if (col.align!='') sAlign = ' align="' + col.align + '"';
			var sVal = jt.parseField(col.field,item);
			ExpAll_HTML += '<td'+sAlign+sStyle+'>'+sVal+'</td>';
		});
		ExpAll_HTML += '</tr>';
	});
}

//导出WORD（全部）
function funExportWord_All(){
	funExportAllView(function(str){
		//jt.log('Word');
		funExportWord(str)
	})
}

//导出EXCEL（全部）
function funExportExcel_All(){
	funExportAllView(function(str){
		//jt.log('Excel');
		funExportExcel(str)
	})
}
// --------------------加载视图[End]----------------------------------------

// --------------------加载表单[Begin]----------------------------------------
//加载json
function loadForm(){
	var arr=jPrint.pages;
	//divMain.innerHTML = '';
	for (var i=0; i<arr.length; i++) {
		loadForm_ShowPage(arr[i])
	}
	var pages=jt._('div.OnePage');
	pages[pages.length-1].style.pageBreakAfter='auto';
}
//画表格
function loadForm_ShowPage(json){
	var oElement=json.obj;
	var sTemplate = addPrefix('<H1 style="text-align: center;">', json.title, '</H1>');
	sTemplate += '<div class="FORM_DIV"></div>';
	
	var div; var divIn; var divForm;
	div = insertOnePage(sTemplate);
	divIn = jt._('div',div)[0];
	divForm = jt._('div.FORM_DIV',div)[0];
	divForm.innerHTML = oElement.innerHTML.replace(/ (onclick|onmouseover|onmouseout)=/ig,' on__=');
	
	var objs=jt._('input',divForm);
	for (var i=objs.length-1; i>=0; i--) {
		if (/(^button$)|(^hidden$)/i.test(objs[i].type)) objs[i].parentNode.removeChild(objs[i]);
	}
	var objs=jt._('div',divForm);
	for (var i=objs.length-1; i>=0; i--) {
		if (objs[i].style.display=='none') objs[i].parentNode.removeChild(objs[i]);
	}
	var objs=jt._('table',divForm);
	for (var i=objs.length-1; i>=0; i--) {
		if (objs[i].style.display=='none') objs[i].parentNode.removeChild(objs[i]);
	}
	
	objs=jt._('.FormTable',divForm);
	//objs[2].style.borderTop='none';
	for (var i=0; i<objs.length; i++) {
		objs[i].style.borderCollapse = 'collapse';
		objs[i].border=1;
	}
	
	objs=jt._('.FormTableTitle',divForm);
	for (var i=0; i<objs.length; i++) {
		var obj=jt._('table',objs[i])[0];
		obj.rows[0].cells[1].innerHTML = '<h3>'+obj.rows[0].cells[1].innerHTML+'</h3>'
		obj.rows[0].removeChild(obj.rows[0].cells[2]);
		obj.rows[0].removeChild(obj.rows[0].cells[0]);
		//objs[i].className='';
	}
	
	
}
// --------------------加载表单[End]----------------------------------------

// --------------------控件相关[Begin]----------------------------------------


//提示下载安装ActiveX
function InstallWebExtTip(){
	if (!jt.bIE) return;
	showMsg('未安装平台控件');
//	jtConfirm("未安装平台控件，某些功能将无法正常使用？", function(bDefYes) {
//		if (!bDefYes) return;
//		alert(1);
//		//这写下载安装代码
//	},'',30)
	loadWebExt();
}


//"header"="&w&b页码，&p/&P"
//"footer"="&u&b&d"
//"margin_bottom"="0.750000"
//"margin_left"="0.750000"
//"margin_right"="0.750000"
//"margin_top"="0.750000"
//"Print_Background"="no"
//"Shrink_To_Fit"="yes"
var PS_header='&w&b页码，&p/&P';
var PS_footer='&u&b&d';
var PS_Print_Background='no';
//读取旧打印设置
function PageSetup_SaveOldValue(){
	if (!hasWebExt) return;
	PS_header = doAction('GetPageSetup','header');
	PS_footer = doAction('GetPageSetup','footer');
	PS_Print_Background = doAction('GetPageSetup','Print_Background');
}
//还原打印设置
function PageSetup_Restore(){ 
	if (!hasWebExt) return;
	doAction('SetPageSetup','header',PS_header);
	doAction('SetPageSetup','footer',PS_footer);
	doAction('SetPageSetup','Print_Background',PS_Print_Background);
}
//调整打印设置
function PageSetup_SetGAP(){ 
	if (!hasWebExt) return;
	if ((PS_header!='&w&b页码，&p/&P') || (PS_footer!='&u&b&d')) return; //用户已经自行调整过默认打印值,  系统不再进行设置
	doAction('SetPageSetup','header','');
	doAction('SetPageSetup','footer','&b&p/&P');
	doAction('SetPageSetup','Print_Background','yes');
}
// --------------------控件相关[End]----------------------------------------


//打印前处理
function funPrint_Before(){
	jt.addClass(document.body,'BodyPrint');
}
//打印后处理
function funPrint_After(){
	jt.removeClass(document.body,'BodyPrint');
}

//打印
function funPrint(){
	funPrint_Before();
	window.print();
	funPrint_After();
}

//打印设置
function funPrintSetup(){
	if (!hasWebExt) { InstallWebExtTip(); return; }
	doAction('PrintSetup');
	setPageStyle();
}

//打印预览
function funPrintPreview(){
	if (!hasWebExt) { InstallWebExtTip(); return; }
	funPrint_Before();
	doAction('PrintPreview');
	setPageStyle();
	funPrint_After();
}

function getExportHeadHTML(){
	var sCSS='';
	//debugger;
	var arr=document.styleSheets;
	jt.each(arr,function(idx,item){
		//if (item.href!='') return;
		if (item.cssText=='') return;
		//sCSS += item.cssText;
	});
	if (sCSS!=''){
		sCSS = '<style type="text/css">'+sCSS+'</style>';
	}
	//for (var i=0; i<arr.length; i++) { if (arr[i].href=='') { jt.appendCSS(arr[i].cssText); } }
	var sHTML='';
	sHTML+='<HTML>\n';
	sHTML+='<HEAD>\n';
	sHTML+='<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>\n';
	sHTML+=sCSS;
	sHTML+='</HEAD>\n';
	sHTML+='<body class="'+document.body.className+'">\n';
	//jt.log(sHTML,0)
	return sHTML;
}

//导出WORD
function funExportWord(str){
	if (!hasWebExt) { InstallWebExtTip(); return; }
	var sHTML='';
	sHTML += getExportHeadHTML();
	if ((typeof(str)=='string')&&(str!='')){
		sHTML += str;
	}else{
		sHTML += divMain.innerHTML;
	}
	sHTML += '</body><HTML>';
	doAction('InsertToWord',sHTML);
}

//导出EXCEL
function funExportExcel(str){
	if (!hasWebExt) { InstallWebExtTip(); return; }
	var sHTML='';
	sHTML += getExportHeadHTML();
	if ((typeof(str)=='string')&&(str!='')){
		sHTML += str;
	}else{
		sHTML += divMain.innerHTML;
	}
	sHTML += '</body><HTML>';
	doAction('InsertToExcel',sHTML);
}

//---------------------------打印表格显示完成执行[begin]------------------

function printAfterViewShowData(){
	if(/word/i.test(jPrint.sExport)) funExportWord();
	if(/excel/i.test(jPrint.sExport)) funExportExcel();
	if(/word/i.test(jPrint.sExport) || /excel/i.test(jPrint.sExport)) window.close();
}


//---------------------------打印表格显示完成执行[end]------------------