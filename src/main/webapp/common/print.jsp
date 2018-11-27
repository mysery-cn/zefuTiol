<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=8">
	<%@ include file="/common/inc_head.jsp"%>
	<script type="text/javascript" src="<%=contextPath%>/common/print.js"></script>
	<script type="text/javascript" src="<%=SYSURL_static%>/js/content/Content.js"></script>
	<title>打印</title>
</head>
<style type="text/css" id="cssMain">
	body {background-color: #c6cace; margin-top: 30px;}
	.BodyPrint{background-color: white; margin-top: 0px}
	.OnePage{
		page-break-after: always; box-sizing: border-box; background-color: white;
		border:1px solid gray;
		margin-bottom: 20px;
	}
	.OnePageIn{ overflow:hidden;}
	.BodyPrint .OnePage{
		height: auto; 
		padding-left: 0mm; padding-right: 0mm; padding-top: 0mm; padding-bottom: 0mm;
		border:none;
		margin-bottom: 0px;
	}
	.BodyPrint .OnePageIn{height: auto;}
	#divMain {
		padding: 20px; padding-bottom: 0px;
		box-sizing: border-box;
	}
	.BodyPrint #divMain{ padding: 0px; }
	.VIEW_Table { width:100%; }
	.VIEW_Table thead td{
		font-weight: bold; text-align: center; background-color: #EEE;
	}
	#divTop{
		position:fixed; top:0px; left:0px; width:100%;
		_position: absolute; _clear: both;
		_top:expression(eval(document.compatMode && document.compatMode=='CSS1Compat') ?  documentElement.scrollTop-1 : document.body.scrollTop-1); 
	}
	.BodyPrint #divTop{display: none;}
	h1{  }
</style>

<script>
/*
var l=document.getElementsByTagName("link")
for(var i=0;i<l.length;i++){

	if(l[i].getAttribute("href").indexOf("images/style.css")!=-1){
	
		var p=l[i].parentNode;
		p.removeChild(l[i]);
		
		//alert(l[i].getAttribute("href"))
	}

}
*/
	
//add jjb 20171129 添加页面加载完毕后调用事件，页面加载完毕后，把隐藏的图片显示出来
window.onload = function(){
	if(document.getElementById('barCodeImg') != null){
		document.getElementById('barCodeImg').style.display = "";
	}
}
	
	function preprint(){
		if(opener.parent.jFormData.C_ModuleId=="dispatch"){
			PageSetup("0.40","0.15","0.8","0.8")
		}
	}
	function pretaoprint(){
	if(opener.parent.jFormData.C_ModuleId=="dispatch"){
		if(document.all.tao.value=="0")	{
				var tabs=document.getElementsByTagName("TABLE")
				//alert(tabs.length)
				
				for(var i=0;i<tabs.length;i++){
					if(tabs[i].className!="TBLItem_Table"){
						tabs[i].bgColor="white"
						tabs[i].style.border="none"
						for(var r=0;r<tabs[i].rows.length;r++){
							for(var c=0;c<tabs[i].rows[r].cells.length;c++){
								tabs[i].rows[r].cells[c].style.border="none"
								if(tabs[i].rows[r].cells[c].className=="fontSize"&&tabs[i].rows[r].cells[c].id==""&&tabs[i].rows[r].cells[c].innerHTML.indexOf("opinion")==-1){
									//tabs[i].rows[r].cells[c].innerHTML="<div style=display:none>"+tabs[i].rows[r].cells[c].innerHTML+"</div>"
									tabs[i].rows[r].cells[c].innerHTML="&nbsp;"
									
								}else if(tabs[i].rows[r].cells[c].innerHTML.indexOf("opinion")!=-1){
								
									var tmp1=tabs[i].rows[r].cells[c].innerHTML.replace(/[\r\n]/g, "")
									var tmp2=tabs[i].rows[r].cells[c].childNodes[1].outerHTML.replace(/[\r\n]/g, "")
									
									if(tmp2!=""){
										var tmp3=tmp1.substring(0,tmp1.indexOf(tmp2))
										
										//alert(tmp1)
										//alert(tmp2)
										//alert(tmp1.indexOf(tmp2.replace(/[\r\n]/g, "")))
										tabs[i].rows[r].cells[c].innerHTML=tmp1.replace(tmp3,"<div style=display:none>"+tmp3+"</div>")
									}
									//alert(tabs[i].rows[r].cells[c].innerHTML)
								} 
			
								}
							}
					}
				}
				
				
				var spans=document.getElementsByTagName("SPAN")
				for(var s=0;s<spans.length;s++){
					if(spans[s].style.color=="red"){
						var tmp1=spans[s].innerHTML
						var tmp2=spans[s].childNodes[1].outerHTML
						if(tmp2!=""){
							var tmpstr=""
							var n=spans[s].innerHTML.indexOf(tmp2)
							for(var i=0;i<n;i++){
								tmpstr=tmpstr+"&nbsp;&nbsp;"
							}
						
							spans[s].innerHTML=tmpstr+spans[s].innerHTML.substring(spans[s].innerHTML.indexOf(tmp2),spans[s].innerHTML.length)
						}
					} 
					if(spans[s].parentNode.style.fontSize=="34px"){
						spans[s].parentNode.innerHTML="<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>"
					}
				}
			
			document.all.ID_C_DocMark.innerHTML=document.all.ID_C_DocMark.innerHTML.replace("〔","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
			document.all.ID_C_DocMark.innerHTML=document.all.ID_C_DocMark.innerHTML.replace("〕","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
			document.all.ID_C_DocMark.innerHTML=document.all.ID_C_DocMark.innerHTML.replace("号","")
			document.all.ID_C_DocMark.innerHTML=document.all.ID_C_DocMark.innerHTML.replace("川","")
			document.all.ID_C_DocMark.style.marginLeft="300px"
			
			if(document.all.ID_STANDARD_FILE.innerText=="是"){
				document.all.ID_STANDARD_FILE.innerHTML="√"
			}else{
				document.all.ID_STANDARD_FILE.innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;√"
			}
			document.all.tao.value="1"
		}
	
	
	PageSetup("0.80","0.15","0.75","0.85")
	}
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		function PageSetup(top,bottom,left,right) {
            var HKEY_Root, HKEY_Path, HKEY_Key;
            HKEY_Root = "HKEY_CURRENT_USER";
            HKEY_Path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
            
                var Wsh = new ActiveXObject("WScript.Shell");
                HKEY_Key = "header";
                //设置页眉（为空）
                //Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key)可获得原页面设置   
                Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
                HKEY_Key = "footer";
                //设置页脚（为空）   
                Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");

                //这里需要浏览器版本，8.0以下的页边距设置与8.0及以上不一样，注意注册表里的单位是英寸，打印设置中是毫米，1英寸=25.4毫米
               
                    HKEY_Key = "margin_left";
                    //设置左页边距
                    //Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.75");
                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, left);
                    HKEY_Key = "margin_right";
                    //设置右页边距
                    //Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.85");
                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, right);
                    HKEY_Key = "margin_top";
                    //设置上页边距
                    //Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.80");
                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, top);
                    HKEY_Key = "margin_bottom";
                    //设置下页边距   
                    //Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.15");
                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, bottom);
                
            
        }



function exportToWord(controlId) {
    debugger;
    var control = document.getElementById("printDiv");
    try {
        var oWD = new ActiveXObject("Word.Application");
        var oDC = oWD.Documents.Add("", 0, 1);
        var oRange = oDC.Range(0, 1);
        var sel = document.body.createTextRange();
        try {
            sel.moveToElementText(control);
        } catch (notE) {
            alert("导出数据失败，没有数据可以导出。");
            window.close();
            return;
        }
        sel.select();
        sel.execCommand("Copy");
        oRange.Paste();
        oWD.Application.Visible = true; //window.close();
	}
    catch (e) {
            alert("导出数据失败，需要在客户机器安装Microsoft Office Word(不限版本)，将当前站点加入信任站点，允许在IE中运行ActiveX控件。");
            try { oWD.Quit(); } catch (ex) {}
            //window.close();
        }
    }

		
		
		
		
		</script>		
<body class="BodyView" scroll="YES" style="overflow: auto;" onkeyup="if (event.keyCode!=192) return;;if (jt.hasClass(document.body,'BodyPrint')) {funPrint_After()} else {funPrint_Before()}">
<input id="tao" type=hidden  value="0"/>
<div id="divMain" style=""></div>
	
<div id="divTop" style=" ">
	<div id="divToolbar" class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div icon="close.png" onclick="closeSelf();">关闭</div>
		<div></div>
		<div icon="print.png" onclick="funPrint()">打印</div>
		<div id="btn_PrintSetup" icon="print_setup.png" onclick="funPrintSetup()">打印设置</div>
		<div id="btn_PrintPreview" icon="print_preview.png" onclick="preprint();funPrintPreview()">打印预览</div>

		
		<div style="display:none;" id="btn_TaoPrintPreview" icon="print_preview.png" onclick="pretaoprint();funPrintPreview()">套打印预览</div>
		<div></div>
		<div id="btn_Export" icon="export.png" onclick="">导出</div>
		<div class='TBLSubMenu'>
			<div icon="export_word.png" onclick="exportToWord()">导出WORD</div>
			<div icon="export_word.png" onclick="funExportWord()">导出WORD新</div>
			<div icon="export_excel.png" onclick="funExportExcel()">导出EXCEL</div>
		</div>
		<div style="display:none;" id="btn_Export_All" icon="export.png" onclick="">导出全部</div>
		<div style="display:none;" class='TBLSubMenu'>
			<div icon="export_word.png" onclick="funExportWord_All()">导出WORD</div>
			<div icon="export_excel.png" onclick="funExportExcel_All()">导出EXCEL</div>
		</div>
		<div></div>
		<div class="Toolbar_Right">
			<div id="Toolbar_VIEW" style="padding-left: 10px;">
				打印记录: <input id="txtPageInfo" class="Input_Select input" style="padding: 0px 3px;" size="10" data="100/1|前 100 条,100/2|后 100 条,200/1|前 200 条,200/2|后 200 条,500/1|前 500 条,1000/1|前 1000 条" ListHeight="0" ListWidth="100" onchange="loadView(this.value);">
				<input type="hidden" id="txtViewCol">
				<input type="hidden" id="txtViewColName">
				<input type="button" class="button" value="选择列" onclick="selectViewCol()">
				<!--<label id="lblQuickLoad" style="display: none;" class="spanError">数据过多，已采用快速加载模式</label>-->
			</div>
		</div>
	</div>
</div>

<!--
<object id="factory" style="display:none1;position:absolu1te; top:110px; left:110px;" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="http://www.meadroid.com/scriptx/ScriptX.cab#Version=5,60,0,360"></object>
    factory.printing.header = "";//页眉    
    factory.printing.footer = "";//页脚    
    factory.printing.leftMargin = 1.0;//左边距    
    factory.printing.topMargin = 1.3;//上边距    
    factory.printing.rightMargin = 1.0;//右边距    
    factory.printing.bottomMargin = 1.3;//下边距    

    factory.printing.portrait = true;//打印方向，true:纵向.false:横向    
    factory.DoPrint(false);//设置为false，直接打印    
-->

</body>



</html>
<%@ include file="/common/inc_bottom.jsp"%>
