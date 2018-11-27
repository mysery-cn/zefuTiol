<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<script type="text/javascript" src="print.js"></script>
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
<script type="text/javascript">
//	function init(){
//	}
</script>
		
<body class="BodyView" scroll="YES" onkeyup="if (event.keyCode!=192) return;;if (jt.hasClass(document.body,'BodyPrint')) {funPrint_After()} else {funPrint_Before()}">

<div id="divMain" style=""></div>
	
<div id="divTop" style=" ">
	<div id="divToolbar" class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div icon="close.png" onclick="closeSelf();">关闭</div>
		<div></div>
		<!--<div icon="print.png" onclick="funPrint()">打印</div>
		<div id="btn_PrintSetup" icon="print_setup.png" onclick="funPrintSetup()">打印设置</div>
		<div id="btn_PrintPreview" icon="print_preview.png" onclick="funPrintPreview()">打印预览</div>
		<div></div>-->
		<div id="btn_Export" icon="export.png" onclick="">导出</div>
		<div class='TBLSubMenu'>
			<div icon="export_word.png" onclick="funExportWord()">导出WORD</div>
			<div icon="export_excel.png" onclick="funExportExcel()">导出EXCEL</div>
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
