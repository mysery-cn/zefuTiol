<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/inc_head.jsp"%>
<script type="text/javascript">
	//定义全局变量：行业ID
	var instFlag = "";
	/**
	 * tab标签页
	 */
	var tabarr=[
		{
			"title":"首页",
			"link":"leader/leader_list.jsp",
			"type":"main",
			"id":'iframe'
		}
	];
	
	$.extend({
		myMethod: function (tabarr) {
				for(var i=0;i<tabarr.length;i++)
				{	
					if(tabarr[i].type=="main"){
						$div=$("<div class='tabs_one' id='main"+tabarr[i].id+"'>"+"</div>");
						$a=$("<a onclick=openLink('"+tabarr[i].link+"','"+tabarr[i].id+"')>"+tabarr[i].title+"</a>");					
						$closetab=$("<div class='close_tab'>"+"</div>");
						$div.append($a);
						$div.append($closetab);
						
					}
					
					if(tabarr[i].type=="else"){	
						
						
						$div=$("<div class='tabs_one' id='main"+tabarr[i].id+"'>"+"</div>");
						$a=$("<a  onclick=openLink('"+tabarr[i].link+"','"+tabarr[i].id+"')>"+tabarr[i].title+"</a>");					
						$div.append($a);
						$closetab=$("<div class='close_tab'>"+"</div>");
						$img=$("<img src='<%=SYSURL_static%>/images/icon_biz/close_tab.png'>")
						$closetab.append($img);
						//$span2=$("<span class='tab_close'  id='close"+i+"' onclick=closeTab('"+tabarr[i].id+"')>"+"</span>");
						$div.append($closetab);
					}		
					$.width();
					$('#tab_over').append($div);
				}			
				
		},
		width:function()
		{
			var length=tabarr.length;
			width=length*200;
			$("#tab_over").css({
				'width':width+'px'
			})
		},
		left:function()
		{
			var i=0;
			var width=$("#tab_over").width();
			i++;
			//var offset=-100*i;
			//$("#tab_over").animate({left:offset},1000);
			$("#tab_over").stop().animate({left:-i*100},1000);
			console.log(i)
			
			
		},
		addPage:function(tabarr,object){
			var a = false;
			for(var i=0 ; i < tabarr.length;i++)
			{
				var key= tabarr[i].id;
				if(object.id == key){
					a = true;//存在
				}
			}
			if(a==true)
			{
				var i=0;
				for(var i in tabarr)
			 	{
			 		if(tabarr[i].id==object.id)
			 		{
			 			j=i
			 		}
			 	}
			 	tabarr.splice(j,1,object);
			 	//删除
			 	openLink(object.link,object.id);
			}else{
				 tabarr.push(object);
				 var l=tabarr.length-1;
				 
				 
				 $div=$("<div class='tabs_one' id='main"+object.id+"'>"+"</div>");
					$a=$("<a  onclick=openLink('"+object.link+"','"+object.id+"')>"+object.title+"</a>");					
					$div.append($a);
					$closetab=$("<div class='close_tab'  id='close"+l+"' onclick=closeTab('"+object.id+"')>"+"</div>");
					$img=$("<img src='<%=SYSURL_static%>/images/icon_biz/close_tab.png'>")
					$closetab.append($img);
				    $div.append($closetab);
				    $('#tab_over').append($div);
				    $.width();
				    openLink(object.link,object.id);
			}
		}
	});
	var i=0;
	function tabLeft()
	{
		var l=$('#tab_over').width()-$('#tab_p').width();
		i++;
		if(100*i>l)
		{
			i--;
		return;
		}
		else{
			$("#tab_over").animate({left:-100*i},1000);
		}
		
	}
	
	function tabright()
	{
		i--;
		if(i<0)
		{
			i++;
			return;
		}
		else{
			$("#tab_over").animate({left:-100*i},1000);
		}
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中央企业“三重一大”决策和运行应用系统</title>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/index.css" type="text/css"/>
    <script type="text/javascript" src="<%=SYSURL_static%>/js/echarts.js"></script>
</head>

<body id="body" style="overflow-y:hidden;overflow-x:hidden">
<div style="width:100%;height:100%">
<div style="height:100%" id="index">
	<iframe src="leader/leader_list.jsp" id="iframe" frameborder="0" height="100%;"></iframe>
</div>
</div>
</body>

</html>
<%@ include file="/common/inc_bottom.jsp"%>
<script type="text/javascript" src="<%=SYSURL_static%>/js/tiol_common.js"></script>
<script type="text/javascript" src="<%=SYSURL_static%>/js/indexMain.js"></script>
<script type="text/javascript">
	function autodivheight() { //函数：获取尺寸 
	    var height = $(window).height();
	    $("#body").css({
	        'height': height- 120 + 'px'
	    });
	}

	//初始化
	$.myMethod(tabarr);
	$.width();
	autodivheight();
	window.onresize = autodivheight;
</script>