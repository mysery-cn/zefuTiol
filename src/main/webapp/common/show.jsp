<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
	<head>
		<%@ include file="/common/inc_head.jsp"%>
		<title>附件浏览</title>
		<script type="text/javascript"
			src="<%=SYSURL_static%>/js/aip/AIP_MAIN.js"></script>
			
		<!--该事件在AIP引擎初始化完毕之后触发-->
		<script language=javascript for=HWPostil1 event=NotifyCtrlReady>
		 	HWPostil1_NotifyCtrlReady();
		</script>
		
		<script language=javascript>
			//校验控件是否安装，未安装提示安装
		    function valdataObject(){
		    	//是IE浏览器
		    	if(!!window.ActiveXObject || "ActiveXObject" in window){
		    		var AipObj = document.getElementById("HWPostil1");
			        if(AipObj.object == null || AipObj.object == "null"){
			            jt.Msg.confirm("OFD电子签章控件未安装，是否下载？",function(bYes){
			                if(bYes == true){
			                	window.location.href="<%=SYSURL_static%>/js/aip/ocx_install.exe";
			                }else{
			                    self.close();
			                }
			                return;
			            });
			        }
		    	} else {
		    		jt.Msg.showMsg("请使用IE浏览器打开！")
		    	}
		    }
		
		
			var fileId = jt.getParam("fileId");
			var url = SYSURL.fs+"/gapServlet?action=fsManageServlet&fUser=zfos&requesttype=download&fv=1&fid="+fileId;

			function HWPostil1_NotifyCtrlReady(){
				var obj = document.all.HWPostil1;
				obj.ShowDefMenu = 0;						// 隐藏菜单栏 0为隐藏; 1为显示
				obj.ShowScrollBarButton =0;						// 隐藏水平滚动条旁的工具条
				obj.ShowToolBar =0;
				obj.JSEnv=0;

				var IsOpen = obj.LoadFile(url);
				if (IsOpen != 1) {
					alert("打开文档失败！");
				} 
			}
		</script>
	</head>

	<body style="background: #ccc; HEIGHT: 100%; WIDTH: 100%; LEFT: 0px; TOP: 0px" onload="valdataObject();">
		<center>
			<script src="<%=SYSURL_static%>/js/aip/LoadAip.js"></script>
		</center>
	</body>
</html>

