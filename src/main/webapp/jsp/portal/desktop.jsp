<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/portal.js"></script>
	<title>我的工作台</title>
</head>
<script type="text/javascript">
	var UI_ID = jt.getParam('UI_ID');
	var jConfig={data:[]};
	var par; //容器
	var arrCell=[]; 
	function jtParseURL_Page(sURL){
		//sURL=sURL.replace(/\{appId\}/ig, appId);
		return sURL;
	}
	function init(){
		par = _('#divMain');
		loadConfig();
		loadShortcut();
		addResizeEvent(bodyResize);
	}
	
	function bodyResize(){
		var oPar=jt._('#divShortcutPar');
		if (oPar.updateUI) setTimeout(oPar.updateUI,100);
	}

	function loadShortcut(){
		if (!top.SCJson) return;
		var arr=top.SCJson.data||[];
		if (arr.length==0) return;
		jt._('#divShortcut').loadData(top.SCJson);
		var oPar=jt._('#divShortcutPar');
		if (oPar.updateUI) setTimeout(oPar.updateUI,100);
	}
	function loadConfig(){  //加载桌面配置文件
		var sURL='{contextPath}/portal/uischeme/getUserUIScheme.action?t={JSTime}&UI_ID='+UI_ID;
		getJSON(sURL,function (json,o){
			var str=json.data[0].UI_DESKTOP;
			str=str.replace(/(^\")|(\"$)/g,"");
			if (str!='') jConfig = jt.Str2Json(str);
			loadConfigCnt();
  	});
	}
	function loadConfigCnt(){ //加载桌面配置文件内容
		if (jConfig.data.length==0){
			jt._('#divMain').innerHTML = '<div style="color:gray; padding:10px;">[未配置人个工作台]</div>'
			return;
		}
		var ids='';
		for (var i=0; i<jConfig.data.length; i++){ ids += (i>0?',':'') + jConfig.data[i].resid; }
		var sURL='{contextPath}/portal/DesktopDatasource/listByIDs.action?t={JSTime}&ids='+ids;
		getJSON(sURL,function (json,o){
			var arr=jConfig.data;
			var arrItem=json.data;
			for (var i=0; i<arr.length; i++){
				for (var j=0; j<arrItem.length; j++){
					if(arrItem[j].ID==arr[i].resid){
						//arr[i].ID = arrItem[j].ID;
						//arr[i].NAME = arrItem[j].NAME;
						arr[i].ICON = arrItem[j].ICON;
						arr[i].TYPE = arrItem[j].TYPE;
						var strTem = addURLParam(arrItem[j].URL, 'pageSize', jt.getDefVal(arr[i].SHOWCOUNT,10), false);
						arr[i].URL = strTem;
						arr[i].URL_MORE = arrItem[j].URL_MORE;
						arr[i].URL_MESSAGES = arrItem[j].URL_MESSAGES;
						arr[i].STYLE = arrItem[j].STYLE;
						break;
					}
				}
			}
			initDesktop();
  	});
	}
	
	
	function initDesktop(){ //初始化桌面
		arrCell=[];
		var sHTML = '';
		var arr = jt.getDefVal(jConfig.cells,'50%,50%').split('|');
		for (var i=0; i<arr.length; i++){
			var arrItem=arr[i].split(',');
			var sTab='<table class="MainTable" width="100%" style="table-layout:fixed;" border="0" cellspacing="8" cellpadding="0">';
			sTab+='<tr>';
			for (var j=0; j<arrItem.length; j++){
				sTab+='<td class="MainCell" valign="top" width="'+arrItem[j]+'"></td>';
			}
			sTab+='</tr></table>';
			sHTML += sTab;
		}
		sHTML += '';
		par.innerHTML = sHTML;
		arrCell=jt._('td.MainCell',par);
		var arr=jConfig.data;
		for (var i=0; i<arr.length; i++){
			addDeskItem(i);
		}
		
	}
	//添加桌面项
	function addDeskItem(idx){
		var jsonItem=jConfig.data[idx];
		var oDiv = document.createElement("div");
		oDiv.className = 'deskItem';
		oDiv.idx=idx;
		var oPar=arrCell[0];
		var idx=jt.getDefVal(jsonItem.cell,0);
		if ((idx>=0) && (idx<arrCell.length)) oPar=arrCell[idx];
		oPar.appendChild(oDiv);
		oDiv.innerHTML = '';
		loadDeskItem(oDiv);
	}
	//刷新桌面项
	function loadDeskItem(oDiv){
		var sImg=jt.TransparentImg;
		var idx=oDiv.idx;
		var jsonItem=jConfig.data[idx];
		var sHTML='';
		sHTML += '';
		sHTML += '<table class="deskItem" width="100%" style="table-layout:fixed;" border="0" cellspacing="0" cellpadding="0">';
		sHTML += '	<tr><td class="f_LT">'+sImg+'</td>';
		sHTML += '		<td class="f_T">';
		
		var sURLMore = jt.parseURL(jt.getDefVal(jsonItem.URL_MORE,''));
		if (sURLMore!='') sHTML += '<div class="tit_more" onclick="self.location=\''+sURLMore+'\'">'+sImg+'</div>';
		sHTML += '<div class="tit_title"><label>';
		//if (jt.getDefVal(jsonItem.ICON)!='') 
			sHTML += '<img align="absmiddle" src="'+jt.FixImgPngSrc(SYSURL.static+'/images/icon_biz/'+jsonItem.ICON)+'" width="24" height="24"> ';
		sHTML += jsonItem.NAME;
		sHTML += '</label></div>';
		
		sHTML +='</td>';
		sHTML += '		<td class="f_RT">'+sImg+'</td>';
		sHTML += '	</tr>';
		sHTML += '	<tr>';
		sHTML += '		<td class="f_L">'+sImg+'</td>';
		sHTML += '		<td class="f_CNT">';
		
		var TYPE_Judge = jsonItem.TYPE == 'IFRAME';
		if(TYPE_Judge){sHTML += '<iframe src='+ jt.parseURL(jsonItem.URL) +' frameborder="0" style="width:100%;height:'+ jsonItem.HEIGHT +'px"></iframe>';}
		else{
			sHTML +='<div class="cnt" style="height:'+ jsonItem.HEIGHT +'px">';
			sHTML += '	<table action="actOpenForm(this)" class="Grid" style="text-align:center;" border="0" cellspacing="0" cellpadding="0" URLData="'+jsonItem.URL+'" width="100%"  TableStyle="NoBorder NoHead NoInterlaced" ShowPageBar="false">';
			sHTML += jsonItem.STYLE;
			sHTML += '	</table>';
			sHTML +='</div>';
			
		}
		
		sHTML +='</td>';
		sHTML += '		<td class="f_R">'+sImg+'</td>';
		sHTML += '	</tr>';
		sHTML += '	<tr><td class="f_LB">'+sImg+'</td><td class="f_B">'+sImg+'</td><td class="f_RB">'+sImg+'</td></tr>';
		sHTML += '</table>';
		
		oDiv.innerHTML = sHTML;

		jt.FormatUI();
	}
	
	function funSearch(){
		var sKey = jt._("#txtSearch").value;
		if(!sKey){ top.showMsg("请输入搜索内容!"); return false; }
		globalSearch(sKey);
	}
	
	function jtInitJtDataItem (oComp, jsonItem, idx){
		if(typeof(fun_PortalInitDataItem)=='function') fun_PortalInitDataItem(oComp, jsonItem, idx);
	}
</script>
	
<body class="BodyDesktop" scroll="no">
<div id="divBox" class="ScrollBox" style="padding-right:5px;">
	<table width="100%" style="height:85px; table-layout:fixed; margin-bottom:-5px;" border="0" cellspacing="0" cellpadding="0">
		<tr valign="top">
			<td width="28"><div id="divSC_Left" onclick="jt._('#divShortcutPar').scroll(80)"></div></td>
			<td>
				<div id="divShortcutPar" class="ScrollPanel" ScrollButtonLeft="divSC_Left" ScrollButtonRight="divSC_Right" style="">
					<div id="divShortcut" class="List" style="display:inline; " ListStyle="{CST_LISTSTYLE_10}">
						<xmp class="html"><div class="shortItem" onclick="{NAV_ACTION}">
								<div class="shortItem_img"><img src="<%=SYSURL_static%>/images/icon_biz/{NAV_ICON}" onload="fixPNG(this,32,32)"></div>
								<div class="shortItem_tit">{NAV_NAME}</div>
							</div></xmp>
					</div>
				</div>
			</td>
			<td width="28"><div id="divSC_Right" onclick="jt._('#divShortcutPar').scroll(-80)"></div></td>
			<td width="330" align="right">
				<div class="divDeskSearchBlock">
					<table border="0" cellspacing="0" cellpadding="0" align="center">
						<form onsubmit="funSearch(); return false;">
						<tr>
							<td><input type="text" id="txtSearch" class="deskSearch_Input"></td>
							<td align="right"><input type="image" src="<%=SYSURL_static%>/images/t.gif" class="deskSearch_Button"></td>
						</tr>
						</form>
					</table>
				</div>
			</td>
		</tr>
	</table>
	<div id="divMain"></div>
</div>
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>