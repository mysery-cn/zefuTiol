/* 
流程用户选择控件
一个页面只允许摆放一个EngineUserSelect控件 
*/
jt.PluginList.push('EngineUserSelect');
jt.EngineUserSelect = {};
jt.EngineUserSelect.TagName = ['div'];

jt.EngineUserSelect.FormatUI = function (oCtl) {
	var iHeight = jt.getDefVal(parseInt(oCtl.style.height),100);
	var oTree=null;
	var oList=null;

	//是否是并发环节 并发环节时不进行当环节只有一个人时默认选择操作
	var isMutil = false;
	
	//[Func] 加载可选用户数据
	oCtl.loadUsers = function(arr){
		/**
		 * 处理数据以满足展现要求
		 */
		for(var i= 0,j=arr.length;i<j;i++){
			var tempObj = arr[i];
			while(tempObj.children !=null && tempObj.children.length==1 && tempObj.children[0].nodeType=="org_company"){
				var children = tempObj.children;
				tempObj.children = children[0].children;
			}
		}
		InitJSONItem(arr);
		if(arr.length>1){
			 isMutil = true;//多个根节点，则为并发环节
			 jt.setAttr(oTree,"ExpandLevel","0");
		}else{
			isMutil = false;//多个根节点，则为并发环节
			jt.setAttr(oTree,"ExpandLevel","1");
		}
		oTree.loadData(arr);
	}
	
	
	
	//[Func] CheckBox点击后触发
	oCtl.AfterInputClick = function(oInput){
		var bChecked=oInput.checked;
		var oNode=jt._('[parent]div.jtTreeNode',oInput);
		var item=oNode.jsonItem;
		
		
		var selOption = item.selectoption; //选择控制
		//1:限选1人    2:多人    3:每部门1人
		
		var assignType = item.assingmentType; //选择候选人展现方式
		// 1:只显示用户id         2:显示人员和完整组织机构     3:显示id和部门
		// 4:显示id和部门下机构    5:显示id和直属机构         6 显示id和处室全结构
		
		if (item.nodeType=='org_user'){ //用户节点
			if (!bChecked){ oCtl.delUser(oInput.value, false); return; }
			oCtl.addUser(oInput.value, item.text, false);
			if (selOption=='2') return;
			var oPar=oTree;
			if (selOption=='3') oPar=oNode.parentNode;
			//清除其他人员
			var objs=oPar.getElementsByTagName('INPUT');
			for (var i=0; i<objs.length; i++){
				if ((objs[i]!=oInput) && (objs[i].checked)) {
					objs[i].checked=false;
					if (jt.getAttr(objs[i],'_nodeType')=='org_user') oCtl.delUser(objs[i].value, false);
				}
			}
		}else{  //选择子节点
			var nodes=oTree.getChildNodes(oNode);
			for (var i=0; i<nodes.length; i++){
				if (nodes[i].jsonItem.nodeType!='org_user') continue;
				var temInput=nodes[i].getElementsByTagName('INPUT')[0];
				if (temInput.checked!=bChecked) {
					temInput.checked=bChecked; oCtl.AfterInputClick(temInput);
					if ((bChecked)&&(selOption!='2')) return;
				}
			}
		}
	}
	
	//[Func] 添加用户
	oCtl.addUser = function(sID, sText, bClickInput){
		var divItem=document.createElement('div');
		divItem.className='EUS_List_Item';
		divItem.innerHTML='<img src="'+SYSURL.static+'/images/icon16/org_user.png" align="absmiddle"> '+sText;
		divItem.onmouseover=new Function('jt.addClass(this,\'EUS_List_Item_Over\')');
		divItem.onmouseout=new Function('jt.removeClass(this,\'EUS_List_Item_Over\')');
		divItem.onclick=new Function('jt._(\'[parent]div.EngineUserSelect\',this).delUser(this,true)');
		jt.setAttr(divItem,'val',sID);
		oList.appendChild(divItem);
		
		if (!jt.getDefVal(bClickInput,true)) return;
		var obj=findInput(sID); if (obj) obj.checked=true;
	}
	//[Func] 删除用户
	oCtl.delUser = function(sID, bClickInput){
		if (typeof(sID)=='object'){
			var obj=sID;
			sID=jt.getAttr(obj,'val');
		}else{
			var objs=jt._('div.EUS_List_Item',oList);
			for (var i=0; i<objs.length; i++){
				if (jt.getAttr(objs[i],'val')==sID) { var obj=objs[i]; break; }
			}
		}
		oList.removeChild(obj);
		if (!jt.getDefVal(bClickInput,true)) return;
		var obj=findInput(sID); if (obj) obj.checked=false;
	}
	
	//[Func] 删除用户
	oCtl.delAllUser = function(){
		var objs=jt._('div.EUS_List_Item',oList);
		for (var i=objs.length-1; i>=0; i--) oCtl.delUser(objs[i],true)
	}
	
	//查找选择框
	function findInput(sValue){
		var objs=jt._('input',oTree);
		for (var i=0; i<objs.length; i++){ if (objs[i].value==sValue) return objs[i]; }
		return null;
	}
	//初始化JSON数据
	function InitJSONItem(arr){
		for (var i=0; i<arr.length; i++){
			var sHTML='<input type="checkbox" onclick="jt._(\'[parent]div.EngineUserSelect\',this).AfterInputClick(this)" value="'+arr[i].id+'" _nodeType="'+arr[i].nodeType+'" class="checkbox" ';
			//if ((arr[i].selectoption=='1'||arr[i].selectoption=='3') && arr[i].nodeType=='org_company') sHTML+=' disabled '
			sHTML += '>';
			sHTML += arr[i].text;
			arr[i].textShow = sHTML;
			if (arr[i].children) InitJSONItem(arr[i].children);
		}
	}
	
	oCtl.setHeight = function(iHeight){
		iHeight = jt.getDefVal(iHeight,oCtl.offsetHeight);
		oCtl.getElementsByTagName('TABLE')[0].style.height=iHeight+'px';
		oTree.style.height=(iHeight-25)+'px';
		oList.style.height=(iHeight-25)+'px';
	}
	
	//[Func] 获取选择的用户
	/*oCtl.getStateUser = function(){
		var arr=[];
		var arrRoot=oTree.getChildNodes();
		for (var i=0; i<arrRoot.length; i++){
			var objs=jt._('input',arrRoot[i].nextSibling);
			var bAddRoot=false;
			var aRoot={user:[],state:{}};
			aRoot.state.id=arrRoot[i].jsonItem.id;
			aRoot.state.name=arrRoot[i].jsonItem.name; //aRoot.state.text=arrRoot[i].jsonItem.text;
			for (var j=0; j<objs.length; j++){
				if (!objs[j].checked) continue;
				if (jt.getAttr(objs[j],'_nodeType')!='org_user') continue;
				if (!bAddRoot){ bAddRoot=true; arr.push(aRoot); }
				var oNode=jt._('[parent]div.jtTreeNode',objs[j]);
				var aUser={};
				debugger
				aUser.text = oNode.jsonItem.text;
				aUser.value = oNode.jsonItem.id;
				aUser.type = oNode.jsonItem.nodeType;//用户类型
				aRoot.user.push(aUser);
			}
		}
		return arr;
	}*/
	oCtl.getStateUser = function(){
		var arr=[];
		var arrRoot=oTree.getChildNodes();
		for (var i=0; i<arrRoot.length; i++){
			var objs=jt._('input',arrRoot[i].nextSibling);
			var bAddRoot=false;
			var aRoot={user:[],};
			aRoot.state=arrRoot[i].jsonItem;
			for (var j=0; j<objs.length; j++){
				if (!objs[j].checked) continue;
				if (jt.getAttr(objs[j],'_nodeType')!='org_user') continue;
				if (!bAddRoot){ bAddRoot=true; arr.push(aRoot); }
				var oNode=jt._('[parent]div.jtTreeNode',objs[j]);
				aRoot.user.push(oNode.jsonItem);
			}
		}
		return arr;
	}

	
	//[Func] 组件初始化
	oCtl.init = function(){
		var sHTML='';
		sHTML += '<table style="table-layout:fixed; height:'+iHeight+'px;" width="100%" border="0" cellspacing="'+jt.getAttr(oCtl,'cellspacing',0)+'" cellpadding="'+jt.getAttr(oCtl,'cellpadding',3)+'">';
		sHTML += '<tr height="25px">';
		sHTML += '<td>请选择流程环节和人员：</td>';
		sHTML += '<td width="1">&nbsp;</td>';
		sHTML += '<td>选择结果：<a href="javascript:void(0);" onclick="jt._(\'[parent]div.EngineUserSelect\',this).delAllUser();return false;">[删除全部]</a></td>';
		sHTML += '</tr>';
		sHTML += '<tr>';
		sHTML += '<td id="EUS_Tree_TD"><div id="EUS_Tree" class="TreeViewLite" style="height:'+(iHeight-25)+'px" TreeStyle="Icon_OrgUser" AutoFocusFirst="true"  ExpandLevel="3" IconPath="{SYSURL.static}/images/icon16/" NodeIcon="{nodeType}.png" TextField="{textShow}"></div></td>';
		sHTML += '<td>&nbsp;</td>';
		sHTML += '<td id="EUS_List_TD"><div id="EUS_List" style="height:'+(iHeight-25)+'px"></div></td>';
		sHTML += '</tr>';
		sHTML += '</table>';
		oCtl.innerHTML=sHTML;
		jt.FormatUI(oCtl);
		oTree=jt._('#EUS_Tree'); oList=jt._('#EUS_List');
		
		
		//追加Tree事件  //点击节点时选中CheckBox
		oTree.afterNodeClick = function(oNode,obj){
			if (!/input/i.test(obj.nodeName)) jt._('input',oNode)[0].click(); 
		}
		
	}
	oCtl.init();

	//清除内存
	jt.addEvent(window,'onunload',function (){
		oTree.afterNodeClick = null;
		oCtl.loadUsers = null;
		oCtl.AfterInputClick = null;
		oCtl.getStateUser = null;
		oCtl.addUser = null;
		oCtl.delUser = null;
		oCtl.delAllUser = null;
		oCtl.setHeight = null;
		oCtl.init = null;
	});
};

