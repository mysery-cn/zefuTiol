//添加页面
function addpage(title,url,id)
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
	if($("#" +id).length > 0){
		$('.tabs_one_cur').addClass('tabs_one') .removeClass('tabs_one_cur');
		$("#main"+id).addClass('tabs_one_cur') .removeClass('tabs_one');
		$("#index>iframe").css('display','none'); 
		$("#" + id).css('display','block'); 
		$('#' + id).attr("src",url);
	}else{
		$.addPage(tabarr,object)
	}
}
//关闭标签
function closeTab(id)
{	
	//移除标签
	$("#main"+id).remove();
	var j=0;
	for(var i in tabarr)
	{
		if(tabarr[i].id==id)
		{
			j=i
		}
	}
	tabarr.splice(j,1);
	//选中样式
	$('.tabs_one_cur').addClass('tabs_one') .removeClass('tabs_one_cur');
	$("#main"+tabarr[j-1].id).addClass('tabs_one_cur').removeClass('tabs_one');
	//页面切换
	$("#index>iframe").css('display','none'); 
	$("#" + tabarr[j-1].id).css('display','block'); 
	//移除Ifram页面
	$("#"+id).remove();
};

//页面跳转
function openLink(link,id)
{
	$('.tabs_one_cur').addClass('tabs_one') .removeClass('tabs_one_cur');
	$("#main"+id).addClass('tabs_one_cur') .removeClass('tabs_one');
	
	var indexHtml = "<iframe src='"+link+"' id='"+id+"' frameborder='0' height='100%;'></iframe>";
	$("#index>iframe").css('display','none'); 
	if($("#" +id).length > 0){
		$("#" + id).css('display','block'); 
	}else{
		$("#index").append(indexHtml); 
	}
}