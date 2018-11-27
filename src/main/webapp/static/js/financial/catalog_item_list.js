/**
 * 加载一级事项目录
 */
function loadingCatalogLevel(){
	var levelCode = jt.getParam('levelCode');
	var dataURL = "{contextPath}/catalog/queryCatalogLevel.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i<json.data.length;i++){
				if (levelCode==json.data[i].CATALOG_CODE) {
					$("#treeDiv a").removeClass();
					$("#typeName").html(json.data[i].CATALOG_NAME+'-企业清单列表');
		        	$("#treeDiv").append("<div class='industry_contents'> <a class='industry_contents_cur' style='width:100%;white-space:nowrap;overflow:hidden;text-overflow:ellipsis; display:block;' title='"+json.data[i].CATALOG_NAME+"' onclick=showView(this,'"+json.data[i].CATALOG_CODE+"','"+json.data[i].CATALOG_NAME+"');>"+json.data[i].CATALOG_NAME+"</a></div>");
		        	break;
				}
			}
		}
	},false);
}

function showView(obj,id,name){
	$("#treeDiv a").removeClass();
	$(obj).addClass("industry_contents_cur");
	resetSearch();
	jt._("#catalogCode").value=id;
	var html = name;
	if (name != '') {
		html = name + '-';
	}
	html = html+'企业清单列表';
	$("#typeName").html(html);
	queryData(id);
}

function itemView(id, catalogId) {
	var href="/tiol/url/goItemList.action?levelCode=F&companyId="+id+"&catalogId="+catalogId;
	window.parent.addpage("事项清单列表",href,id+catalogId);
}

/**
 * 列表搜索
 */
function queryData(id){
	if (id==undefined) {
		id = '';
	}
	id = jt._("#catalogCode").value;
	//企业名称
	var companyName = jt._("#companyName").value;
	var data = "{\"companyName\":\""+companyName+"\"}";
	//请求路径
	var sURL = "{contextPath}/statistics/item/queryCatalogItemByPage.action?currentPage={page}&pageSize={pageSize}&catalogCode="+id+"&parameter="+encodeURIComponent(data);
	jt.setAttr(_('#tabMain_List'),'URLData',sURL);
	_('#tabMain_List').loadData();
}

/**
 * 显示查询条件
 */
function showSearch(){
	$("#hideSearch").css('display','block'); 
	$("#searchDetail").css('display','block'); 
	$("#showSearch").css('display','none'); 
}

/**
 * 隐藏查询条件
 */
function hideSearch(){
	$("#hideSearch").css('display','none'); 
	$("#searchDetail").css('display','none'); 
	$("#showSearch").css('display','block'); 
}

/**
 * 重置查询条件
 */
function resetSearch(){
	jt._("#companyName").value='';
}