$.extend({
		tabBorder:function($tab,tabarr)
		{
			
			$div1=$("<div class='tab_div'>"+"<div class='tabs_left' onclick=tabLeft()>"+"<a></a></div>"+"<div class='tab_scroll'>"+"<div id='tab_over'>"+"</div>"+"</div><div onclick=tabright() class='tabs_default_right'><a></a></div>");
			$tab.append($div1);
			$width=$("body").width()-87;
			$(".tab_scroll").css({'width':$width+'px'})
			$.myMethod(tabarr)
		},
		myMethod: function (tabarr) {
				for(var i=0;i<tabarr.length;i++)
				{	
					if(tabarr[i].type=="main"){

						//创建页签
                        $div=$("<div class='tabs_one_cur' id='main"+tabarr[i].id+"'>"+"</div>");
                        $a=$("<a onclick=openLink('"+tabarr[i].link+"','"+tabarr[i].id+"')>"+tabarr[i].title+"</a>");
                        $closetab=$("<div class='close_tab'>"+"</div>");
                        $div.append($a);
                        $div.append($closetab);
						//打开页签对应的页面
                        openLink(tabarr[i].link,tabarr[i].id);

					} else if(tabarr[i].type=="else"){
						
						$div=$("<div onmouseleave=mouseleaveImg('"+tabarr[i].id+"')  onmouseover=moseoverImg('"+tabarr[i].id+"') class='tabs_one' id='main"+tabarr[i].id+"'>"+"</div>");
						$a=$("<a  onclick=openLink('"+tabarr[i].link+"','"+tabarr[i].id+"')>"+tabarr[i].title+"</a>");					
						$div.append($a);
						$closetab=$("<div class='close_tab'>"+"</div>");
						$img=$("<img class='tab_close_img' id='img"+tabarr[i].id+"'+ src='"+SYSURL.static+"/images/tiol/close_tab.png' onclick=closeTab('"+tabarr[i].id+"')>")
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
			width=length*155;
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
					break;
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
				 $div=$("<div onmouseleave=mouseleaveImg('"+object.id+"') onmouseover=moseoverImg('"+object.id+"') class='tabs_one_cur' id='main"+object.id+"'>"+"</div>");
					$a=$("<a  onclick=openLink('"+object.link+"','"+object.id+"')>"+object.title+"</a>");					
					$div.append($a);
					$closetab=$("<div class='close_tab'  id='close"+l+"' >"+"</div>");
					$img=$("<img class='tab_close_img' id='img"+tabarr[i].id+"' src='"+SYSURL.static+"/images/tiol/close_tab.png' onclick=closeTab('"+object.id+"')>")
					$closetab.append($img);
				    $div.append($closetab);
				    $('#tab_over').append($div);
				    $.width();
				    openLink(object.link,object.id);
				    var ol=$('#tab_over').width()-$('.tab_scroll').width();
				    if(ol>0)
				    {
				    	$("#tab_over").animate({left:-(ol+5)},1000);
				    }
				    
				    
			}
		}
	});
	function tabLeft()
	{
		var $over = $("#tab_over");//页签栏
        var curLeft = $over.offset().left-$(".tabs_left").width();//页签栏的左偏移
        var datum = $(".tab_scroll").width()-$("#tab_over").width();//页签栏与滚动栏的宽度差

        //如果tab_over的宽度小于tab_scroll的宽度,不进行任何操作
		if (datum>=0) {
			return ;
		}

		//如果向左移动100px,会使左偏移小于宽度差,则将左偏移设为宽度差
        if (curLeft-100<datum) {
            $over.animate({
                left: datum
            });
        } else {//否则向左移动100px
            $over.animate({
                left: curLeft-100
            });
        }
	}
	function tabright()
	{
        var $over = $("#tab_over");//页签栏
        var curLeft = $over.offset().left-$(".tabs_left").width();//页签栏的左偏移

        //如果向右移动100px,会使左偏移大于0,则将左偏移设为0
        if (curLeft+100>0) {
            $over.animate({left:0});
		} else {//否则向右移动100px
            $over.animate({left:curLeft+100});
		}
	}
	

	//添加页面
function addpage(title,url,id,type)
{
	if(tabarr.length >= 30){
		showMsg("打开的窗口数量太多！");
        return;
	}
	var object={
		"title":title,
		"link":url,
		"type":"else",
		"id":id
	}
	if(type != null && type != "" && type != "undefind"){
		//存在itemDetail91110000100009563N
		if($("#frame_" +id).length > 0){
			openLinkByType(url,id);
		}else{
			$.addPage(tabarr,object)
		}
	}else{
		//存在
		if($("#frame_" +id).length > 0){
	        openLink(url,id);
		}else{
			$.addPage(tabarr,object)
		}
	}
}

//鼠标经过
function moseoverImg(id)
{
	$("#img"+id).css({'display':'inline'});
    closeTabFlag=false;
}
//鼠标移除 
function mouseleaveImg(id)
{
    if(closeTabFlag) return;
    $("#img"+id).css({'display':'none'});
    //alert($("#img"+id).closest("[id^='main']").hasClass("tabs_one_cur"))
    if($("#img"+id).closest("[id^='main']").hasClass("tabs_one_cur")){
        $("#img"+id).css({'display':'inline'});
    }

}
//关闭标签
var id_cur='';
function closeTab(id)
{	
	//移除标签
	$("#main"+id).remove();
	var j=0;
	for(var i in tabarr)
	{
		if(tabarr[i].id==id)
		{
			j=i;
			break;
		}
	}
	tabarr.splice(j,1);
	//删除当前 为选中id
	if(id_cur==id)
	{
        openLink(tabarr[j-1].link,tabarr[j-1].id);
	}
	else{
		//未选中

	}
	//设置关闭按扭显示
    $("#tab_over").find("img").hide();
    $("#main"+tabarr[j-1].id).find("img").show();
    closeTabFlag=true;

	//移除Ifram页面
    var iframe = $("#frame_"+id);
	if (iframe.length) {
	  var frm = iframe.get(0);
	  frm.src = 'about:blank';
	  try {
	    frm.contentWindow.document.write('');
	  } catch (e) {
		 console.log(e);
	  }
	  (CollectGarbage || $.noop)();
	}
	iframe.remove();
	$.width();
	var l=$('#tab_over').width()-$('.tab_scroll').width();
	if(l<0)
	{
		$("#tab_over").animate({left:0},1000);
	}
};
//页面跳转
function openLink(link,id)
{
	id_cur=id;
    $('.tabs_one_cur').find("img").css({'display':'none'});  //先隐藏
	$('.tabs_one_cur').addClass('tabs_one').removeClass('tabs_one_cur');
	$("#main"+id).addClass('tabs_one_cur').removeClass('tabs_one');
    $("#img"+id).css({'display':'inline'});
	
	var indexHtml = "<iframe src='"+link+"' id='frame_"+id+"' class='ifram' frameborder='0' height='100%' width='100%'></iframe>";
	$("#idx_Main>iframe").css('display','none');
	if($("#frame_" +id).length > 0){
		$("#frame_" + id).css('display','block');
	}else{
		$("#idx_Main").append(indexHtml);
	}
}

function openLinkByType(link,id)
{
	id_cur=id;
    $('.tabs_one_cur').find("img").css({'display':'none'});  //先隐藏
	$('.tabs_one_cur').addClass('tabs_one').removeClass('tabs_one_cur');
	$("#main"+id).addClass('tabs_one_cur').removeClass('tabs_one');
    $("#img"+id).css({'display':'inline'});
	
	var indexHtml = "<iframe src='"+link+"' id='frame_"+id+"' class='ifram' frameborder='0' height='100%' width='100%'></iframe>";
	$("#idx_Main>iframe").css('display','none');
	if($("#frame_" +id).length > 0){
		$("#frame_" + id).css('display','block');
		$('#frame_' + id).attr("src",link);
	}else{
		$("#idx_Main").append(indexHtml);
	}
}

