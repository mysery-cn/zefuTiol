/**
 * 通过企业id获取企业名称
 */
function getCompanyNameById(){
	//获取页面跳转传参的单位ID
	var companyID = jt.getParam('companyId');
	//请求路径
	var dataURL = "{contextPath}/statistics/company/queryCompanyDetail.action?companyID="+companyID;
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			if (json.data[0] != '') {
				$("#companyId").val(json.data[0].COMPANY_ID);
				$("#companyName").html(json.data[0].COMPANY_NAME);
			} else {
				return jt.Msg.showMsg("未查询到企业信息！")
			}
		}
	},false);
}

/**
* 加载大额度资金运二级事项目录
*/
function loadingCatalogLevel(){
	var catalogId = jt.getParam('catalogId');
	var dataURL = "{contextPath}/catalog/queryCatalogFLevel.action";
	var jPost= {};
	postJSON(dataURL,jPost,function(json,o){
		if (!json || json.errorCode != "0") {
			return jt.Msg.showMsg("获取数据失败！");
		} else {
			for(var i=0;i<json.data.length;i++){
				if (json.data[i].CATALOG_ID==catalogId) {
					jt._("#catalogId").value = catalogId;
					$("#typeName").html(json.data[i].CATALOG_NAME+'-事项清单列表');
					$("#treeDiv a").removeClass();
		        	$("#treeDiv").append("<div class='industry_contents'> <a class='industry_contents_cur' style='width:100%;white-space:nowrap;overflow:hidden;text-overflow:ellipsis; display:block;' title='"+json.data[i].CATALOG_NAME+"' onclick=showView(this,'"+json.data[i].CATALOG_ID+"','"+json.data[i].CATALOG_NAME+"');>"+json.data[i].CATALOG_NAME+"</a></div>");
				} else {
		        	$("#treeDiv").append("<div class='industry_contents'> <a style='width:100%;white-space:nowrap;overflow:hidden;text-overflow:ellipsis; display:block;' title='"+json.data[i].CATALOG_NAME+"' onclick=showView(this,'"+json.data[i].CATALOG_ID+"','"+json.data[i].CATALOG_NAME+"');>"+json.data[i].CATALOG_NAME+"</a></div>");
				}
	        }
		}
	},false);
}

function showView(obj,id,name){
	$("#treeDiv a").removeClass();
	$(obj).addClass("industry_contents_cur");
	resetSearch();
	jt._("#catalogId").value=id;
	var html = name;
	if (name != '') {
		html = name + '-';
	}
	html = html+'事项清单列表';
	$("#typeName").html(html);
	queryData(id);
}

/**
 * 列表搜索
 */
function queryData(id){
	id = jt._("#catalogId").value;
	var levelCode = jt._("#levelCode").value;
	var companyId = jt._("#companyId").value;
	//查询事项清单名称
	var itemName = jt._("#itemName").value;
	//请求路径
	var data = "{\"levelCode\":\""+levelCode+"\",\"companyId\":\""+companyId+"\",\"itemName\":\""+itemName+"\",\"catalogId\":\""+id+"\"}";
	var sURL = "{contextPath}/item/queryCatalogItemByPage.action?currentPage={page}&pageSize={pageSize}&parameter="+encodeURIComponent(data);
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
	jt._("#itemName").value='';
}