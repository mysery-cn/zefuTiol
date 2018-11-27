var check=false;
$.extend({
    menuBorder: function ($div,data) {
    	
        //获取浏览器高度和宽度
        var height=$("body").height()-64-50;
        var width=$("body").width()-100;
        $('.main_detail').css({"height":height+"px"})
        $('.main_detail_table').css({"width":width+'px'})
        $menuHtml=$("<div class='main_detail_menu2' id='menuborder'></div>");
        $div.append($menuHtml);
        $.menuLeft(data,$menuHtml,$div);
        var $div=$(".main_detail_menu2");
        $.menuRignt(data,$div);
        
    },
    menuLeft:function(data,$menuHtml,$div){
    	var the=JSON.stringify(data).replace(/"/g, '&quot;');
        $menuleft=$("<div class='menu_silder nav_left'>"+"<div class='hide_submenu' onclick=hiheSubmenu('"+data[0].NAV_NAME+"','"+data[0].NAV_ID+"')>"+"<a>"+"<span>"+"<img src='"+SYSURL.static+"/images/tiol/nav_hide.png' width='50px;'>"+"</span></a></div>"+"</div>");
        for(var i=0;i<data.length;i++)
        {
            $menubtn=$("<div class='nav_a' id='menubtn"+data[i].NAV_ID+"' onclick=showRightMenu('"+data[i].NAV_NAME+"','"+data[i].NAV_ID+"')>"+"<a><span>"+"<img src='"+SYSURL.static+"/images/tiol/tab_ico_home.png'>"+"</span></a></div>");
            $menuleft.append($menubtn);
        }
        $menuHtml.append($menuleft)
    },
    menuRignt:function(data,$div)
    {
    	
    	for(var i=0;i<data.length;i++)
    	{
	    	$submenuBorder=$("<div id='submenu"+data[i].NAV_ID+"'>"+"</div>");
	    	$submenu=$("<div class='nav_submenu'>"+"<div class='submenu_tittle'>"+"<img src='"+SYSURL.static+"/images/tiol/tab_ico_man.png'><a>"+data+"</a></div></div>");
	    	$submenuBorder.append($submenu);
	    	var child=data[i].children;
	    	if(child)
			{
	    		$.secondMenu(child,$submenu)
			}
    	}
    	$div.append($submenuBorder);
       
    },
    secondMenu:function(child,$submenu)
    {
   
    	if(child.length>0)
        {
         
            for(var i=0;i<child.length;i++)
            {
            	$ul=$("<ul>"+"<li onclick=addnewPage() id='menuli"+arr[i].NAV_ID+"'>"+"<a>"+arr[i].NAV_NAME+"</a>"+"</li>"+"</ul>");
                $submenu.append($ul);
                if(child.children)
                {
                    var $submenu=$('#menuli'+arr[i].id);
                    $.secondMenu($submenu,child.childre);
                }

            }
        }
    }
   
})
//菜单切换
function showRightMenu(the,data,id)
{
	
	//addpage('haha','haha',id);
    check=true;
    $(".nav_a_cur").addClass('nav_a') .removeClass('nav_a_cur');
    $("#"+id).addClass('nav_a_cur') .removeClass('nav_a');
    $('.nav_submenu').remove();
    var $div=$(".main_detail_menu2");
     $('.main_detail_menu').animate({width:'300'},"slow");
        var width=$("body").width()-310;
        $('.main_detail_table').animate({width:width+'px'},"slow");
    
}
//侧边隐藏显示
function hiheSubmenu(the,data,id)
{    
    if($('.main_detail_menu').width()>100)
    {   //收
        $('.nav_submenu').css({'display':'none'});
        $('.main_detail_menu').animate({width:'61'},"slow");
         var width=$("body").width()-80;
        $('.main_detail_table').animate({width:width+'px'},"slow");
    }
    else{
        //显示
        if(check==false)
        {
            showRightMenu(the,data,id)
        }
        else{
           $('.nav_submenu').css({'display':'block'});
            $('.main_detail_menu').animate({width:'300'},"slow");
            var width=$("body").width()-310;
            $('.main_detail_table').animate({width:width+'px'},"slow"); 
        }
        
    }
}