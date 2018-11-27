<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <%@ include file="/common/inc_head.jsp"%>
    <link rel="stylesheet" href="<%=SYSURL_static%>/css/main_index.css" type="text/css"/>
    <meta http-equiv="X-UA-Compatible" content="IE=7,chrome=1"/>
    <title>首页</title>
</head>
<script type="text/javascript" src="<%=SYSURL_static%>/js/tab.js"></script>
<script type="text/javascript">
    if (typeof(SYSVAR_SystemName)=='string') document.title=SYSVAR_SystemName;
    //用户界面方案
    var CST_URL_UserUI= SYSURL.portal+'/portal/uischeme/getUserUIScheme.action?t={JSTime}';
    //右上角系统链接
    var CST_URL_Link = SYSURL.portal+'/portal/uilink/getUserUILink.action?t={JSTime}&UI_ID={UI_ID}';
    //办公快车道
    //var CST_URL_Shortcut = '_shortcut.json';
    //var CST_URL_Shortcut='{contextPath}/portal/uishortcut/listUIShortcutAllByParentId.action?t={JSTime}&id=&UI_ID={UI_ID}';
    //导航树一二级
    var CST_URL_Nav_L12 = SYSURL.portal+'/portal/uinav/getUserUINavTree.action?t={JSTime}&parentId=&loadLevel2=1&UI_ID={UI_ID}';

    var UI_ID = '';

    var tabarr=[
        {
            "title":"首页",
            "link":getIFrameUrl(),
            "type":"main",
            "id":'iframe1'
        }
    ];

    function jtParseURL_Page(sURL){ return jtReplaceVAR(sURL, 'UI_ID'); }

    var oPopSC; //弹出办公快车道
    var oPopBigFont;//大字体

    //从导航栏动作中获取url,导航栏动作格式actOpenView('tiol_special_list'),得到单引号内的url
    function getUrlFromAction(action) {
        if (!action) {
            return "";
        }
        return action.replace(/(^.*')(.*)('.*$)/g, "$2");
    }

    function loadLocalNavigation(){
        getJSON( CST_URL_Nav_L12 ,function (json,o){
            NavJson=json;
            //设置左侧默认图标
            for (var i=0; i<NavJson.data.length; i++){
                NavJson.data[i].NAV_ICON=jt.getDefVal(NavJson.data[i].NAV_ICON,'24_def.png');
                var arr=NavJson.data[i].children
                for (var j=0; j<arr.length; j++){
                    arr[j].NAV_ICON=jt.getDefVal(arr[j].NAV_ICON,'32_def.png');
                    if (jt.getDefVal(arr[j].childUrl)=='') arr[j].childUrl='{URLNodeData}';
                }
            }
            loadTopMenu();
        });
    }

    function init(){
        //checkPwdStatus();
        // checkUserLoginAnotherDevice();

        addResizeEvent(bodyResize);
        jt.FormatUI();

        //判断是否隐藏左侧导航
        if (isHideNavi()) {

            jt("#idx_Nav").css("display","none");
            jt("#idx_Nav").css("idx_SplitBar","none");
        } else {
            getJSON(CST_URL_UserUI, function (json,o){
                ///////TODO  getUserUIScheme.action 方法修改, 返回用户个性设置及方案内容 而不仅仅是方案内容
                if (json.errorCode!=0){showMsg("获取方案错误!","",1000); return;}
                if (json.data.length<=0){
                    if(curUser.roleListStr.indexOf("SYS_9999")!=-1){		//无可使用方案时进入默认方案初始化
                        json.data = [];
                        json.data[0] = {'UI_ID':'[GAP_DEFAULT_SCHEME]','UI_DESKTOP_TYPE':'Block'};
                    }else{
                        showMsg("当前用户无方案!","",1000);
                        return;
                    }
                }
                UI_ID = json.data[0].UI_ID;
                UI_DESKTOP_TYPE = jt.getDefVal(json.data[0].UI_DESKTOP_TYPE,"");
                if(UI_DESKTOP_TYPE!=""){
                    UI_DESKTOP_TYPE = "_"+UI_DESKTOP_TYPE;
                }
                // loadUserUILink();
                // loadUserShortcut(json.data[0]);
                loadUserNavigation();
                //查询待办、已办数量
                getPersonTodoCount();
                // goHash();
            });
        }

        //隐藏待办已办
        if(curUser.companyId == "GZW"){
            $("#doneDiv").hide();
            $("#todoDiv").hide();
        }

        //页签初始化
        var $tab=$("#idx_tab");
        $.tabBorder($tab,tabarr);

        oPopSC = document.createElement('div');
        oPopSC.id = 'divSC_PopSC';
        document.body.appendChild(oPopSC);
        oPopSC.appendChild(jt._('#divSC_Pop'));
        jt._('#divSC_Pop').style.display = '';
        oPopSC.style.position='absolute';
        oPopSC.style.top = jt._('#idxTop').clientHeight+1+'px';
        oPopSC.style.right = 0;
        oPopSC.style.width = jt._('#divSC_Pop').clientWidth+'px';

        jt._('#divSC').style.height = (jt._('#idx_middle').clientHeight-0)+'px';

        oPopSC.style.display='none';
        jt._('#divSC_Pop').style.visibility = 'hidden';
        jt._('#divSC_Top').style.display='none';
        jt._('#divSC_Bottom').style.display='none';

        jt._('#divSC_ShowHide').onmouseover = function(){showHideSC(0)};
        jt._('#divSC_ShowHide').onmouseout = function(){showHideSC(0)};
        jt._('#divSC_Pop').onmouseover = function(){clearTimeout(_Time)};
        jt._('#divSC_Pop').onmouseout = function(){showHideSC(0)};
        jt._('#divShortcut').style.width = oPopSC.style.width;
        //oPopSC = jt.PopFrame.regPopObject(jt._('#divSC_ShowHide'), jt._('#divSC_Pop'), 'onmouseover', 'onmouseout');
        //jt.addClass(oPopSC,'PopFrameTemp'); //临时弹出
        //oPopSC.intDelayShow=100;
        //oPopSC.afterShow = function(){ UpdateUI_SC(100); }

        if (b1024) jt._('#idx_Left').width='174px'; //1024分辨率时，设置左侧树型宽度

        jt.setCookie('BigFont',jt.getCookie('BigFont','1'),'.'+jt.getDomain()); //解决Cookie无法跨子域保存的问题

        var bName;
        if (jt.getCookie('BigFont','1')!='0'){
            bName = jt.getCookie('BigFont','1')=='2'?'大字版':'中字版';
        }else{bName = '小字版';}
        jt._('#lblBigFont').innerHTML = bName;
        //jt._('#lblBigFont').innerHTML = jt.getDefVal(jt.getCookie('BigFont'),true)?'大字版':'标准版';
        oPopBigFont = jt.PopFrame.regPopObject(jt._('#lblBigFont'), jt._('#divBigFont_Pop'), 'onmouseover', 'onmouseout');
        jt.addClass(oPopBigFont,'PopFrameTemp'); //临时弹出
        oPopBigFont.afterShow = function(){ jt._('#lblBigFont').className='lblBigFontOver'; }
        oPopBigFont.afterHide = function(){ jt._('#lblBigFont').className='lblBigFont'; }

        //显示帮助信息
        fun_ShowSupportInfo();
        //判断当前用户的跨单位信息 动态显示切换单位按钮
        if (CrossCompany_Check()) { jt('#aSwitchCompany').show(); }
    }

    //判断是否隐藏左侧导航
    function isHideNavi() {
        var role = ","+curUser.roleListStr+",";
        var json = getCfgValueJson('global','cfg_tiol_portal_hide_nav');
        for(var i=0;i<json.data.length;i++){
            if(role.indexOf(json.data[i].text)!=-1){
                return true;
            }
        }

        return false;
    }
    //获取首页iframe的界面路径
    function getIFrameUrl() {
        var role = ","+curUser.roleListStr+",";
        var json = getCfgValueJson('global','cfg_tiol_indexurl');
        var def = '';
        for(var i=0;i<json.data.length;i++){
            var b=json.data[i].text.split(':');
            if(role.indexOf(b[0])!=-1){
                def = b[1];
                break;
            }else if(b[0]=="default"){
                def = b[1];
            }
        }
        return def;
    }

    function fun_ShowSupportInfo(){
        jt._('#spanCurrentDate').innerHTML = (new Date()).format('yyyy年MM月DD日 WW');
        var json = getCfgValueJson('global','cfg_global_support_info');
        if(json && json.data && json.data.length>0 && json.data[0].text) jt._("#spanSupportTel").innerHTML = json.data[0].text;
        var json = getCfgValueJson('global','cfg_global_system_name');
        if(json && json.data && json.data.length>0 && json.data[0].text) document.title = json.data[0].text;
    }

    //隐藏
    var _Time = null;
    function oPopSC_hide(obj,t){
        if(!t) t=500;
        _Time = setTimeout(function() {
            jt._('#divSC_PopSC').style.display='none';
            jt._('#divSC_Top').style.display='none';
            jt._('#divSC_Bottom').style.display='none';
            obj.style.visibility = 'hidden';
        }, t);
    }

    var timBodyResize;
    function bodyResize(){
        var idx_Nav=jt._('#idx_Nav');
        var iW = document.body.clientWidth<1150?150:195;
        if (iW!=jt.getAttr(idx_Nav,'oldWidth',195)){
            jt.setAttr(idx_Nav,'oldWidth',iW);
            if (idx_Nav.offsetWidth>80) idx_Nav.width= iW;
            if (iW<160){ jt.addClass(idx_Nav,'idx_Nav_1024'); }else{ jt.removeClass(idx_Nav,'idx_Nav_1024'); }
        }

        UpdateUI_LMenu();
        UpdateUI_SC(100);
        ///////////////////TODO  浮动状态下没有updateUI

        //解决IE11下，右侧frameMain高度只有100px的问题
        //jt.log();
        //jt.log(jt.browser,0);
        //jt.log(navigator.userAgent,0);
        //jt.log(/Trident\/7\./ig.test(navigator.userAgent),0);

        //Trident/7.0
        //jt.log(jt.browser.quirks +  ' - '+ (jt.doc.getClientHeight() - jt('#idxTop')[0].offsetHeight));
        //var bIE11 = jt.browser.msie11;
        if (((jt.browser.msie) && (!jt.browser.quirks)) || jt.browser.msie11 ){
            //if ((jt.browser.msie)){
            //jt.log( jt('#idx_Main')[0].offsetHeight + '  ' +jt('#idx_Main').height() + '  ' +jt('#frameMain')[0].offsetHeight + '  ' +jt('#frameMain').height(), 0 )
            //TODO  IE 11下的IE10模式 idx_Main会超出10px
            //jt.log(jt._('#idx_Main').offsetHeight+'XXX')
            //idx_middle

            //jt('#idx_Left,#idx_Main').height('')

            clearTimeout(timBodyResize);
            timBodyResize = setTimeout(function(){
                //jt.log(1)
                var iHeight = jt.doc.getClientHeight() - jt('#idxTop')[0].offsetHeight;
                jt('#idx_Nav,#idx_Left').css({height: iHeight });

                //if ( jt.browser.msie11 ){ //解决IE11下计算错误的问题
                iHeight = iHeight-jt.def(parseInt(jt('#idx_Main').css('padding-top')),0)-jt.def(parseInt(jt('#idx_Main').css('padding-bottom')),0);
                jt('#idx_Main').css({height: iHeight });
                //}
            },1);
        }
    }

    // 显示豆腐块
    function showDesktop(){
        loadLeftMenu(0,0);
        showWindow(contextPath+'/jsp/portal/desktop'+UI_DESKTOP_TYPE+'.jsp?UI_ID={UI_ID}','frameMain');jt.setHash('Tree','');
    }

    //加载用户链接
    function loadUserUILink(){ jt._('#divSysLinks').loadData(CST_URL_Link); }

    //加载办公快车道
    var SCJson={};
    function loadUserShortcut(jsonUI){
        var sIds = jt.getDefVal(jsonUI.UIPersion_USE_SHORTCUT);
        var arrsc = jsonUI.UIShortcut||[];
        var arr=[];
        if ((sIds=='')||(sIds=='null')) {  ////TODO  数据
            arr=arrsc;
        }else{
            var arrID = sIds.split(',');
            for (var i=0; i<arrsc.length; i++){
                if (arrID.indexOfEx(arrsc[i].NAV_ID)>-1) arr.push(arrsc[i]);
            }
        }
        SCJson.data=arr;
        jt._('#divShortcut').loadData(SCJson);
        try{ if (frameMain.loadShortcut) frameMain.loadShortcut(); }catch(e){}
        UpdateUI_SC(500);
    }
    function jtAfterListShowData(oComp){
        if (oComp.id=='idx_Nav2'){
            //jt.log(NavJson.data[menuIdx_Top],0)
            jt._('#idx_Nav2Par').style.display = oComp.json.data.length==1?'none':''; //当左下角的菜单只有一个时, 则不显示
            if(typeof(funAfterIndexNav2_Show)=='function') funAfterIndexNav2_Show(oComp);
        }
    }

    //刷新，调整大小（办公快车道）
    function UpdateUI_SC(iTime){
        //设置右侧办公快车道高度
        var oPar=jt._('#divShortcutPar');
        if (!oPar) return;
        var iH=(jt._('#idx_middle').offsetHeight-100);
//		if ((iH>jt._('#divShortcut').offsetHeight) && (jt._('#divShortcut').offsetHeight!=0)) {
//			iH=jt._('#divShortcut').offsetHeight;
//			jt._('#divShortcut').style.top='0px';
//		}
        oPar.style.height = iH + 'px';
        if (oPar.updateUI){setTimeout(oPar.updateUI,iTime);}
    }

    //刷新，调整大小(设置左边树型高度)
    function UpdateUI_LMenu(iTime){
        jt._('#divLeftMenu').style.height = (document.body.clientHeight - jt._('#idxTop').offsetHeight - jt._('#idx_Left_Title').offsetHeight - jt._('#idx_Nav2Par').offsetHeight ) + 'px';

        jt('#idx_NavPar').height(document.body.clientHeight-jt._('#idxTop').offsetHeight-40);
        jt('#idx_NavPar').css("overflow","hidden");

        funNavParScroll();
    }
    //## 2017/11/10
    //## linshengxiong
    //##是否启用滚动
    function funNavParScroll(){
        if(!jt._('#idx_NavPar_Pack')) return;
        if((jt('#idx_NavPar').height()-jt('#idx_NavPar_Pack').height())<0){
            jt.attr(jt("#idx_NavPar")[0],'NavParScroll','true');
            jt("#idx_NavPar,#idxNavItem_Tip").addEvent('mousewheel',function(event){
                if(jt.attr(jt("#idx_NavPar")[0],'NavParScroll')=='false') return;
                e = event || window.event;//前者ie后者火狐
                var wheelDelta = e.wheelDelta || e[0].wheelDelta;
                if(wheelDelta == 120) scroll_top(jt._('#idx_NavPar_Pack'),120,0,'');//## 向上滚
                //## 向下滚
                if(wheelDelta == -120) scroll_top(jt._('#idx_NavPar_Pack'),-120,'',jt('#idx_NavPar').height()-jt('#idx_NavPar_Pack').height());
            },false);
        }else{
            jt.attr(jt("#idx_NavPar")[0],'NavParScroll','false');
            jt._('#idx_NavPar_Pack').style.marginTop= 0;
        }
    }
    //##判断滚动是否超出最大/最小范围
    function scroll_top_ing(oScroll,_iTop,maxSize,minSize){
        if(_iTop>maxSize && maxSize!=='') oScroll.style.marginTop = maxSize+'px';
        else if(_iTop<minSize && minSize!=='') oScroll.style.marginTop = minSize+'px';
        else oScroll.style.marginTop = _iTop+'px';
    }
    //##滚动过渡效果
    function scroll_top(oScroll,iSize,maxSize,minSize){
        var iTop=parseInt(oScroll.style.marginTop);
        if(!iTop) iTop = 0;
        setTimeout(function() {scroll_top_ing(oScroll,iTop + iSize*30/30,maxSize,minSize);}, 180);
        setTimeout(function() {scroll_top_ing(oScroll,iTop + iSize*26/30,maxSize,minSize);}, 140);
        setTimeout(function() {scroll_top_ing(oScroll,iTop + iSize*22/30,maxSize,minSize);}, 100);
        setTimeout(function() {scroll_top_ing(oScroll,iTop + iSize*18/30,maxSize,minSize);}, 60);
        setTimeout(function() {scroll_top_ing(oScroll,iTop + iSize*10/30,maxSize,minSize);}, 20);
        setTimeout(function() {scroll_top_ing(oScroll,iTop + iSize*5/30,maxSize,minSize);}, 0);
    };


    //加载用户系统导航
    var NavJson={};
    function loadUserNavigation(){
        getJSON( CST_URL_Nav_L12 ,function (json,o){
            NavJson=json;
            //设置左侧默认图标
            for (var i=0; i<NavJson.data.length; i++){
                NavJson.data[i].NAV_ICON=jt.getDefVal(NavJson.data[i].NAV_ICON,'24_def.png');
                var arr=NavJson.data[i].children
                for (var j=0; j<arr.length; j++){
                    arr[j].NAV_ICON=jt.getDefVal(arr[j].NAV_ICON,'32_def.png');
                    if (jt.getDefVal(arr[j].childUrl)=='') arr[j].childUrl='{URLNodeData}';
                }
            }
            loadTopMenu();
        });
    }

    //加载顶部菜单 及效果[Begin]
    function loadTopMenu(){
        var sHTML='';
        var arr=NavJson.data;
        sHTML +='<div id="idx_NavPar_Pack">';
        for (var i=0; i<arr.length; i++){
            var isDesk = ((i==0) && ((arr[i].NAV_NAME=='个人工作台')||arr[i].NAV_NAME=='工作桌面'));
            var arrSub=arr[i].children;
            sHTML += '<div class="idxNavItem" onclick="loadLeftMenu('+i+',0);LeftNavClick('+i+');" title0="'+arr[i].NAV_NAME+'" ';
            sHTML += 'onmouseover="idxNavItemMOver(this)" onmouseout="idxNavItemMOut(this)" ';
            sHTML += '>';
            sHTML += '<img align="absmiddle" src="'+jt.FixImgPngSrc(SYSURL.static+'/images/icon_biz/'+arr[i].NAV_ICON)+'" width="24" height="24">';
            sHTML += '<span class="idxNavItemTitle">'+arr[i].NAV_NAME+'</span>';
            sHTML += '</div>';
        }
        sHTML +='</div>';
        jt._('#idx_NavPar').innerHTML=sHTML;

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        jt._('#idxNavItem_Tip').style.display='none';
        idx=0; idxSub=0;
        var objs=jt._('div.idxNavItem', jt._('#idx_NavPar'));
        if (idx>objs.length-1) idx=objs.length-1;
        menuIdx_Top=idx;

        //处理左侧图标, 是否最大化, 及是否显示树型
        var idx_Nav=jt._('#idx_Nav');
        idx_Nav.width = 50 ;
        jt._('#idx_Left').style.display = 'none';
        jt._('#idx_SplitBar').style.display = '';
        setIdxNavClass();
        bodyResize();

        jt._('#idx_Left_Title').innerHTML = objs[idx].innerHTML;
        for (var i=0; i<objs.length; i++){
            if (idx==i){ jt.addClass(objs[i],'idxNavItemSel'); }else{ jt.removeClass(objs[i],'idxNavItemSel'); }
        }

        var sHTML='';
        sHTML += '<div id="idx_Nav2" class="List" ListStyle="{CST_LISTSTYLE_21}" DefaultSelectItem="'+idxSub+'" ShowPageBar="false">';
        sHTML += '<xmp class="html"><table border="0" cellspacing="0" cellpadding="0"><tr><td class="idx_Nav2_icon"></td><td>{NAV_NAME}</td></tr></table></xmp>';
        sHTML += '</div>';
        jt._('#idx_Nav2Par').innerHTML = sHTML;
        jt.FormatUI(jt._('#idx_Nav2Par'));
        var list=jt._('#idx_Nav2');
        list.loadData(NavJson.data[idx].children);
        UpdateUI_LMenu();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        setIdxNavClass();
    }
    var idxNavItem_Tip_Cur=null;
    function idxNavItemMOver(obj){
        idxNavItem_Tip_Cur=obj;
        jt.addClass(obj,'idxNavItemOver');
        if ((jt._('#idx_Nav').offsetWidth>80) || (jt.hasClass(obj,'idxNavItemSel'))) return;
        //if (jt._('#idx_Nav').offsetWidth>80) return;
        var divTip=jt._('#idxNavItem_Tip');
        divTip.style.left = '0px'; jt._('#idxNavItem_Tip01').width=(obj.offsetWidth-2) + 'px';
        divTip.style.top = jt.getAbsTop(obj) + 'px';
        jt._('#idxNavItem_Tip01').innerHTML = jt._('img',idxNavItem_Tip_Cur)[0].outerHTML;
        jt._('#idxNavItem_Tip02').innerHTML = jt.getAttr(obj,'Title0');
        divTip.style.display='';
    }
    function idxNavItemMOut(obj){
        jt.removeClass(obj,'idxNavItemOver');
        jt._('#idxNavItem_Tip').style.display='none';
    }
    //加载顶部菜单 及效果[End]

    var menuIdx_Top=-1; //当前顶部标签
    //加载左侧菜单
    function loadLeftMenu(idx,idxSub){
        jt._('#idxNavItem_Tip').style.display='none';
        idx=jt.getDefVal(idx,0); idxSub=jt.getDefVal(idxSub,0);
        var objs=jt._('div.idxNavItem', jt._('#idx_NavPar'));
        if (idx>objs.length-1) idx=objs.length-1;

        //处理左侧图标, 是否最大化, 及是否显示树型
        var isDesk = ((idx==0) && ((NavJson.data[idx].NAV_NAME=='个人工作台')||NavJson.data[idx].NAV_NAME=='工作桌面'));
        var idx_Nav=jt._('#idx_Nav');
        idx_Nav.width = isDesk ? jt.getAttr(idx_Nav,'oldWidth',195) : 50 ;
        jt._('#idx_Left').style.display = isDesk?'none':'';
        jt._('#idx_SplitBar').style.display = isDesk?'none':'';
        if (!jt("#idx_NavTog").hasClass("idx_Nav_left")) {
            jt("#idx_NavTog").addClass('idx_Nav_left');
        }

        setIdxNavClass();
        bodyResize();

        if (menuIdx_Top==idx) {  //已经加载了TreeView ///TODO 处理  选择节点;
            return;
        }


        //清空idx_Left内容
        jt("#divLeftMenu").html('');
        jt("#idx_Nav2Par").html('');

        menuIdx_Top=idx;

        jt._('#idx_Left_Title').innerHTML = objs[idx].innerHTML;
        for (var i=0; i<objs.length; i++){
            if (idx==i){ jt.addClass(objs[i],'idxNavItemSel'); }else{ jt.removeClass(objs[i],'idxNavItemSel'); }
        }
        // if (isDesk){
        //     showWindow(contextPath+'/jsp/portal/desktop'+UI_DESKTOP_TYPE+'.jsp?UI_ID={UI_ID}','frameMain');jt.setHash('Tree','');
        //     //alert(jt._('#idx_middle').childNodes[0].tagName);
        //     //jt._('#idx_middle').style.paddingBottom = '0';
        //     return;
        // }else{
        //     //jt._('#idx_middle').style.paddingBottom = '6px';
        // }
        var sHTML='';
        sHTML += '<div id="idx_Nav2" class="List" ListStyle="{CST_LISTSTYLE_21}" DefaultSelectItem="'+idxSub+'" ShowPageBar="false">';
        sHTML += '<xmp class="html"><table border="0" cellspacing="0" cellpadding="0"><tr><td class="idx_Nav2_icon"></td><td>{NAV_NAME}</td></tr></table></xmp>';
        sHTML += '</div>';
        jt._('#idx_Nav2Par').innerHTML = sHTML;
        jt.FormatUI(jt._('#idx_Nav2Par'));
        var list=jt._('#idx_Nav2');
        list.loadData(NavJson.data[idx].children);
        UpdateUI_LMenu();

        //LeftNavClick(idx);
    }
    //按标题路径查找
    function GoNavByTitle(sTitle){
        function findArrIdx(arr,str){
            str=jt.trim(str);
            for (var i=0;i<arr.length;i++){
                if (str==jt.trim(arr[i].NAV_NAME.replace(/<.*>/gi, ''))) return i;
            }
            return 0;
        }
        sTitle=sTitle.replace(/[\\\/]/ig,',');
        var arrPath=sTitle.split(',');
        arrPath[0] = findArrIdx(NavJson.data, arrPath[0]);
        if (arrPath.length<2){
            arrPath[1]=0;
        }else{
            arrPath[1] = findArrIdx(NavJson.data[arrPath[0]].children, arrPath[1]);
        }
        if (arrPath[0]==menuIdx_Top){
            jUI('#idx_Nav2 .jListItem')[arrPath[1]].click();
        }else{
            loadLeftMenu(arrPath[0],arrPath[1]);
        }
    }
    var idx_Nav2_idx=0;  //二级树当前选中行
    //var idx_Nav2_json={}; //获取二级树当前选中行JSON(部分项目使用)
    function jtAfterListItemClick (oComp, oItem, jsonItem, idx){
        if(oComp.id=="selectCompany"){
            fun_changeCompanyAfter(jsonItem);
        }else{
            //debugger;
            if (oComp.id!='idx_Nav2') return;
            var sHTML='';
            sHTML += '<div id="divLeftTree" class="TreeViewLite" ClickExpand="true" TreeStyle="NOIcon" ExpandLevel="0" ';
            sHTML += 'TextField="{NAV_NAME}" ';
            sHTML += 'URLData="{SYSURL.portal}/portal/uinav/listUINavAllByParentId.action?t={JSTime}&id='+jsonItem.NAV_ID+'" ';
            sHTML += '></div>';
            //jt.log(jsonItem,0)
            //idx_Nav2_json=jsonItem;
            //jt.log(idx_Nav2_idx,0)
            jt._('#divLeftMenu').innerHTML = sHTML;
            jt.FormatUI(jt._('#divLeftMenu'));

            idx_Nav2_idx=idx;
            if ((defaultClickTree!='[RETURN]')&&(defaultClickTree!='-1')) return;
            if (jt.getDefVal(jsonItem.NAV_ACTION)=='') return;
            defaultClickTree='[RETURN]';
            saveHash('-1');
            // eval(jt.getDefVal(jsonItem.NAV_ACTION));
            openTab(jsonItem.NAV_NAME,getUrlFromAction(jsonItem.NAV_ACTION),jsonItem.NAV_ID);
        }

    }

    //[Func]导航树跳转 @Tag index
    function openTab(name,sURL,id){
        try{ top.findFocusTreeNodeAction('NavGoURL(\''+sURL+'\')') }catch(e){}
        sURL=jt.parseURL(sURL);

        addpage(name,sURL,id);
    }

    //切换单位使用
    var changeCompanyWin = null;
    function fun_changeCompany() {
        if (null == changeCompanyWin) {
            changeCompanyWin = jt.Window.newWindow({
                id: 'changeCompanyWinInstance',
                obj: 'changeCompanyDiv',
                title:'请选择单位',
                width: 300, height: 150,
                maskLayer: true, sizeable: false, maxButton: false
            });
        }
        changeCompanyWin.show();
    }
    //选择切换单位后
    function fun_changeCompanyAfter(jsonItem) {
        changeCompanyWin.hide();
        if(jsonItem.id==curUser.companyId) return;	//未切换其他单位，直接退出
        CrossCompany_Change(jsonItem.id,function(json){
            if (json && json.errorCode == "0") {
                window.location.reload();
            } else {
                showMsg("请求失败：" + json.errorInfo);
            }
        });
    }


    //	//查找点击二级菜单
    //	function Idx_Nav2Click(sTitle){
    //		jt.log(1);
    //	}
    function LeftNavClick(idx){
        //debugger;
        if ((defaultClickTree!='[RETURN]')&&(defaultClickTree!='-1')) return;
        var jItem=NavJson.data[idx];
        if (jt.getDefVal(jItem.NAV_ACTION)=='') return;
        defaultClickTree='[RETURN]';
        //saveHash('-1');
        //eval(jt.getDefVal(jItem.NAV_ACTION));

        openTab(jItem.NAV_NAME,getUrlFromAction(jItem.NAV_ACTION),jItem.NAV_ID);
    }

    //将当前位置保存在地址栏上
    function saveHash(sTree){
        jt.setHash('TopMenu',menuIdx_Top);
        //idxSub = jt.getDefVal(idxSub,-1);
        //if (idxSub!=-1)
        jt.setHash('LeftMenu',idx_Nav2_idx);
        jt.setHash('Tree',sTree);
    }
    //页面打开后跳转到Hash指定的地址
    var defaultClickTree=jt.getHash('Tree',''); //页面加载完后 默认选择树型
    setTimeout(function(){defaultClickTree='[RETURN]';},2000);
    function goHash(){
        if (defaultClickTree=='[RETURN]') return;
        if (defaultClickTree==''){
            defaultClickTree='[RETURN]';
            NavGoURL('{contextPath}/jsp/portal/desktop'+UI_DESKTOP_TYPE+'.jsp?UI_ID={UI_ID}');
            return;
        }
        if (defaultClickTree=='-1') return;
        //debugger;
        var tree=jt._('#divLeftTree');
        if (!tree) return;
        var oN=tree.findNode('id='+defaultClickTree);
        if (oN==null) return;
        defaultClickTree='[RETURN]';
        oN.click();
    }


    function jtAfterTreeViewLiteNodeClick(oComp, oNode){

        if (oComp.id!='divLeftTree') return;
        //var idxSub=-1;
        //var objs=oComp.getChildNodes();
        //var obj=oComp.getAllParentNodes(oNode)[1];
//		for (var i=0; i<objs.length; i++){
//			if (objs[i]==obj) {idxSub=i; break;}
//		}
        var sID=jt.getDefVal(oNode.jsonItem.NAV_ID);
        if (sID=='') sID=oNode.jsonItem.id;
        // saveHash(sID);
        // eval(jt.getDefVal(oNode.jsonItem.NAV_ACTION))
        openTab(oNode.jsonItem.NAV_NAME,getUrlFromAction(oNode.jsonItem.NAV_ACTION),oNode.jsonItem.id);
    }
    function jtAfterTreeViewLiteLoadData (oComp, oNode, json, sURL){
        if (oComp.id!='divLeftTree') return;
        if (oComp!=oNode) return;
        // setTimeout(goHash,10); //自动跳转到Hash上记录的节点
        //自动展开第一级
        setTimeout(function(){
            var objs=oComp.getChildNodes();
            if ((objs.length==0)||(objs.length>5)) return;
            if (!objs[0].jsonItem.isFolder) return;
            for (var i=0;i<objs.length; i++){
                if (!objs[i].jsonItem.isFolder) continue;
                if (objs[i].jsonItem.isExpand) return;
            }
            oComp.nodeExpand(objs[0],true);
        },300);
    }
    //	function jtAfterTreeViewLiteShowData(oComp,oNode,json){
    //		if (oComp.id!='divLeftTree') return;
    //		if (oComp!=oNode) return;
    //		if(typeof(funAfterIndexLeftMenu_Show)=='function') funAfterIndexLeftMenu_Show(oComp,json);
    //	}

    //查找并选中节点
    function findFocusTreeNode(sText){
        var sExp=(sText.indexOf('=')!=-1)?sText:('NAV_NAME='+sText);
        var tree=jt._('#divLeftTree');
        if (!tree) return;
        var oNode=tree.findNode(sExp);
        if (oNode==null) return;
        tree.nodeFocus(oNode);
        //if(typeof(funAfterIndexLeftMenu_FindFocusNode)=='function') funAfterIndexLeftMenu_FindFocusNode(tree,oNode);
    }
    function findFocusTreeNodeAction(sText){
        try{
            setTimeout(function(){ findFocusTreeNode('NAV_ACTION='+sText); },500)
        }catch(e){}
    }

    function showUserInfo(){ top_showDialog('','{SYSURL.portal}/jsp/system/uiPerson_form.jsp?t={JSTime}','WinEdit',800,500); }

    function setFrameMainClass(bDesk){
        if (typeof(bDesk)!='boolean'){
            bDesk=false;
            try{
                if (/\/desktop(_|\.)/i.test(frameMain.location.href)) bDesk=true;
                //if (frameMain.location.href.indexOf('desktop'+UI_DESKTOP_TYPE+'.jsp')!=-1) {bDesk=true;}
            }catch(e){}
        }
        jt._('#frameMain').className = bDesk?'frameMain_desk':'frameMain';
        jt._('#idx_Main').className = bDesk?'idx_Main_desk':'idx_Main';
        showDivSC(false);
    }

    //设置右侧快捷导航是否显示 (是否浮动)
    function showDivSC(bShow){
        //var oRight=jt._('#idx_Right');
        //if (!oRight) return;
        //oRight.style.display = bShow?'':'none';
        jt._('#divSC_ShowHide').style.display = bShow?'':'none';
//		if (bShow){
//			oRight.appendChild(jt._('#divSC'));
//		}else{
//			jt._('#divSC_Pop').appendChild(jt._('#divSC'));
//		}
        UpdateUI_SC(0);
    }
    //右侧快捷导航(浮动状态)
    function showHideSC(bShow){
        if(bShow){
            clearTimeout(_Time);
            jt._('#divSC_PopSC').style.display='';
            jt._('#divSC_Top').style.display='';
            jt._('#divSC_Bottom').style.display='';
            jt._('#divSC_Pop').style.visibility = 'visible';
        }else{ oPopSC_hide(jt._('#divSC_Pop'));}//xiaodao
    }

    //设置idx_Nav样式
    function setIdxNavClass(){
        var divNav = jt._('#idx_Nav');
        var divLeft = jt._('#idx_Left');
        if (divLeft.style.display=='none'){ jt.addClass(divNav,'idx_Nav_white'); }else{ jt.removeClass(divNav,'idx_Nav_white'); }
        if (divNav.offsetWidth<80){ jt.addClass(divNav,'idx_Nav_Small'); }else{ jt.removeClass(divNav,'idx_Nav_Small'); }
    }

    // 返回工作台
    function goToIndex(){ jt._('div.idxNavItem', jt._('#idx_NavPar'))[0].click(); }

    //函数触发器(接收)
    function funTrigger(oWin, sEvent, param){
        if (sEvent=='AfterViewShowData'){ //视图加载数据后触发
            try{
                if (typeof(frameMain.refreshDeskMsgCount=='function')) frameMain.refreshDeskMsgCount(oWin);
            }catch(e){}
            return;
        }
    }

    function setDomainBigFont(sVal){
        var sDomain = '.'+jt.getDomain();
        if (!/\w/.test(sDomain)) return;
        jt.setCookie('BigFont',sVal,sDomain);
    }

    //设置大字体查看
    function setFontSize(bBig){
        bBig=jt.getDefVal(bBig,'1');  //0:标准版|1:中字版|2:大字版
        var bName;
        if(bBig!='0'){
            jt.setCookie('BigFont',bBig==2?'2':'1');
            setDomainBigFont(bBig==2?'2':'1');
            bName = bBig==1?'中字版':'大字版';
        }else{
            jt.setCookie('BigFont','0');
            setDomainBigFont('0');
            bName = '小字版';
        }

        jt._('#lblBigFont').innerHTML = bName;
        //self.location.reload();

        if (bBig == '2') {
            jt.addClass(document.body,'BigFont');
            jt.addClass(frameMain.document.body,'BigFont');
        } else {
            jt.removeClass(document.body,'BigFont');
            jt.removeClass(frameMain.document.body,'BigFont');
        }
        if (bBig == '1') {
            jt.addClass(document.body,'MediumFont');
            jt.addClass(frameMain.document.body,'MediumFont');
        } else {
            jt.removeClass(document.body,'MediumFont');
            jt.removeClass(frameMain.document.body,'MediumFont');
        }
        try{ oPopBigFont.hide(0); }catch(e){}
    }
    setDomainBigFont(jt.getCookie('BigFont','1'));

    function toggleIdxLeft() {

        jt._('#idxNavItem_Tip').style.display='none';

        //处理左侧图标, 是否最大化, 及是否显示树型
        var isDesk = ((menuIdx_Top==0) && ((NavJson.data[menuIdx_Top].NAV_NAME=='个人工作台')||NavJson.data[menuIdx_Top].NAV_NAME=='工作桌面'));
        var idx_Nav=jt._('#idx_Nav');
        var isShow = !jt("#idx_NavTog").hasClass("idx_Nav_left");//ture:展开左侧导航栏

        if (isShow) {//展开左侧导航栏
            idx_Nav.width = isDesk ? jt.getAttr(idx_Nav,'oldWidth',195) : 50 ;
            jt._('#idx_Left').style.display = isDesk?'none':'';
            jt._('#idx_SplitBar').style.display = isDesk?'none':'';
            jt("#idx_NavTog").addClass('idx_Nav_left');
        } else {//收起左侧导航栏
            idx_Nav.width = 50 ;
            jt._('#idx_Left').style.display = 'none';
            jt._('#idx_SplitBar').style.display = isDesk?'none':'';
            jt("#idx_NavTog").removeClass('idx_Nav_left');
        }

        setIdxNavClass();
        bodyResize();
    }

    /**
     * 点击待办弹窗
     */
    function openSubjectTodoPage(status){
        if(status == "1"){
            window.parent.addpage("填报待办", "/jsp/meeting/register/subjectTodo_list.jsp", "subjectTodo");
        }else{
            window.parent.addpage("填报已办", "/jsp/meeting/register/subjectDone_list.jsp", "subjectDone");
        }

    }

    /**
     * 查询待办数量
     */
    function getPersonTodoCount(){
        var sURL = "{contextPath}/subjectTodo/getPersonalTodoCount.action";
        postJSON(sURL, {} ,function (json,o){
            if(json){
                $("#todoNum").html(json.todoNum);
                $("#doneNum").html(json.doneNum);
                var todoNum = json.todoNum;
                var doneNum = json.doneNum;
            }
        });
    }
</script>
<style type="text/css">
    .top_search {
        width: 240px;
        height: 28px;
        background: #fff;
        margin-right: 40px;
    }

    .top_search input.search_txt {
        width: 215px;
        height: 28px;
        right: 25px;
        border: 0;
        background: none;
        background-color: #fff;
        font-size: 14px;
        line-height: 32px;
        padding-left: 4px;
        float: left;
    }

    .search_btn {
        width: 20px;
        height: 22px;
        cursor: pointer;
        border: 0;
        background-image: url('<%=SYSURL_static%>/images/tiol/btn_search.png');
        float: left;
        background-color: #fff;
        margin-top: 3px;
    }

    .idx_Nav_right {
        background: url(<%=SYSURL_static%>/images/tiol/arrow_r_a_c.png);height: 36px;background-repeat: no-repeat;
    }
    .idx_Nav_left {
        background: url(<%=SYSURL_static%>/images/tiol/arrow_l_a_c.png);height: 36px;background-repeat: no-repeat;
    }
</style>
<body class="BodyIndex">
<table width="100%" id="idxTableMain" style="height:100%;table-layout:fixed; " border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td style="border-width: 0 !important;" height="64" id="idxTop" class="idx_top">
            <div class="idx_top_In" style="position:relative; width:100%; height:64px;background-color: #417ac0;"><!--用于做双背景-->
                <table class="" width="100%" style="display:none;" border="0" cellspacing="0" cellpadding="0" hidden="hidden" >
                    <tr>
                        <td style="padding-left:10px;"><span id="spanCurrentDate"></span>&nbsp;&nbsp;<span id="spanSupportTel"></span></td>
                        <td align="right" style="padding-right:15px;">
                            <a href="javascript:void(0);" onclick="fun_changeCompany();return false" id="aSwitchCompany" style="display: none;">切换单位</a> |
                            <label id="lblBigFont" class="lblBigFont">标准版</label>
                            <div id="divSysLinks" class="List" style="display:inline;" ListStyle="{CST_LISTSTYLE_10}" html="<a href='javascript:void(0);' onclick=&quot;{NAV_ACTION};return false;&quot;>{NAV_NAME}</a>&nbsp;|&nbsp;"></div>
                        </td>
                    </tr>
                </table>
                <div class="top_main" style="padding-top: 0;">
				<div class="top_logo"><img src="<%=SYSURL_static%>/images/tiol/com_logo.png"></div>
					<div class="top_right" style="height: 64px;float: right;background-color: #417ac0;color: #fff">
						<!-- <div class="message_tips" id="todoDiv" onclick="openSubjectTodoPage(1)" style="cursor: pointer;"><img src="<%=SYSURL_static%>/images/tiol/icon_wait.png"><div class="message_num"><span id="todoNum">0</span></div>待办</div>
						<div class="message_tips" id="doneDiv" onclick="openSubjectTodoPage(2)" style="cursor: pointer;"><img src="<%=SYSURL_static%>/images/tiol/icon_over.png">已办 | </div> -->
				 		<span><%=loginUser==null?"":loginUser.getUserName() %></span>|<a href="javascript:void(0);" onclick="logout();return false;" style="color: #fff;">安全退出</a>
					</div>
				</div>
                <div id="divSC_ShowHide" class="divSC_ShowHide_Show" style="display:none;position:absolute; top:77px; right:18px;" onclick="showHideSC()"></div>
            </div>
        </td>
    </tr>
    <tr>
        <td id="idx_tab" class="main_tab">

        </td>
    </tr>
    <tr>
        <td class="idx_middle" id="idx_middle">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" style="height:100%;table-layout:fixed;">
                <tr height="100%" valign="top">
                    <td class="idx_Nav idx_Nav_white" id="idx_Nav" width="195" style="padding-top:0px;border-bottom:#d5d5d5 1px solid;border-left:#d5d5d5 1px solid;" >
                        <div id="idx_NavTog" onClick="toggleIdxLeft();" class="idx_Nav_right"></div>
                        <div id="idx_NavPar"></div>
                    </td>
                    <td class="idx_Left" id="idx_Left" width="214px" style="display:none;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" style="height:100%;table-layout:fixed;">
                            <tr>
                                <td id="idx_Left_Title" height="39"></td>
                            </tr>
                            <tr>
                                <td style1="position:relative;" valign="top">
                                    <div id="divLeftMenu"></div>
                                    <div id="idx_Nav2Par"></div>
                                </td>
                            </tr>
                        </table>
                    </td>

                    <td id="idx_SplitBar" class="" afterClick="toggleIdxLeft();" AfterResize="setIdxNavClass();" width="6" style1="position:relative;"><span></span></td>
                    <td id="idx_Main" class="idx_Main" style="width: 100%;">

                    </td>
                    <td id="idx_Right" class="idx_Right" width="60px" style="display:none;">
                    </td>
                </tr>
            </table>

        </td>
    </tr>
</table>

<div id="idxNavItem_Tip" style="position:absolute; display:none;" onclick="idxNavItem_Tip_Cur.click();" onmouseover="this.style.display=''" onmouseout="this.style.display='none'">
    <table id="idxNavItem_Tip00" style="table-layout:fixed; width:140px;" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td id="idxNavItem_Tip01">&nbsp;</td>
            <td id="idxNavItem_Tip02"></td>
        </tr>
    </table>
</div>
<div id="divSC_Pop" style="display:none; width:80px;">
    <div id="divSC">
        <div id="divSC_Top" onclick="jt._('#divShortcutPar').scroll(65)"></div>
        <div id="divShortcutPar" class="ScrollPanel" ScrollUp="true" ScrollButtonLeft="divSC_Top" ScrollButtonRight="divSC_Bottom" style="width:100%;height:200px;">
            <div id="divShortcut" class="List" style="display:inline; " ListStyle="{CST_LISTSTYLE_10}">
                <xmp class="html">
                    <div class="shortItem" onclick="{NAV_ACTION}">
                        <div class="shortItem_img"><img src="<%=SYSURL_static%>/images/icon_biz/s_{NAV_ICON}"></div>
                        <div class="shortItem_tit">{NAV_NAME}</div>
                    </div>
                </xmp>
            </div>
        </div>
        <!-- <div class="divSC_Add" onclick="showUserInfo()" ><img src="<%=SYSURL_static%>/images/icon/s_add.png"><br>添加</div> -->
        <div id="divSC_Bottom" onclick="jt._('#divShortcutPar').scroll(-95)"></div>
    </div>
</div>
<div id="divBigFont_Pop" style="display:none; width:80px;background-color:#FFFFFF">
    <div onclick="setFontSize(2)" style="cursor:pointer;line-height:20px; padding:3px;" onmouseover="this.style.backgroundColor='#F0F0F0'" onmouseout="this.style.backgroundColor='#FFFFFF'">大字版</div>
    <div onclick="setFontSize(1)" style="cursor:pointer;line-height:20px; padding:3px;" onmouseover="this.style.backgroundColor='#F0F0F0'" onmouseout="this.style.backgroundColor='#FFFFFF'">中字版</div>
    <div onclick="setFontSize(0)" style="cursor:pointer;line-height:20px; padding:3px;" onmouseover="this.style.backgroundColor='#F0F0F0'" onmouseout="this.style.backgroundColor='#FFFFFF'">小字版</div>
</div>
<!--用于切换单位-->
<div id="changeCompanyDiv" style="display: none;text-align:left;">
    <div style="padding:10px">
        <div class="List" id="selectCompany" JSData="CrossCompany_List()">
            <xmp class="html"><span style="font-size:14px;"><img src="<%=SYSURL_static%>/images/icon/list_default.gif">{text}</span></xmp>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">

</script>
</html>
<%@ include file="/common/inc_bottom.jsp"%>