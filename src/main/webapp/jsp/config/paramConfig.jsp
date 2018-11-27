<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/inc_head.jsp"%>
	<title>参数配置</title>
</head>
<style type="text/css">
	.cfgTitle {font-family:黑体,楷体_GB2312;line-height:30px;font-size:20px;text-align:center;padding:10px 0px;}
</style>
<script type="text/javascript">
 	var appId = jt.getParam('appId');
	
	function jtParseURL_Page(sURL){
		sURL = jtReplaceVAR(sURL, 'sID,appId');
		return sURL;
	}
	
	//表单检测
	function EditPage_CheckForm(aForm){ 
		return true;
	}
	
	//页面初始化后
	function initFormValueAfter(json){
		jt._('#appId').value = jt._('#appId').value==""?appId:"";
	}
	
	//下发xml文件
	function downXml(){
		var dataURL = "{contextPath}/Generate/Xml/GenerateXmlToCompany.action";
		var jPost= {};
		postJSON(dataURL,jPost,function(json,o){
			if (!json || json.errorCode != "0") {
				return jt.Msg.showMsg("下载数据失败！");
			} else {
				return jt.Msg.showMsg("下载数据成功！");
			}
		},false);
	}
	
	//保存并更新缓存
	function updateCache(){
		var dataURL = "{contextPath}/job/collectData/init.action";
		var jPost= {};
		postJSON(dataURL,jPost,function(json,o){
			return jt.Msg.showMsg(json);
		},false);
	}
	
</script>
	
<body class="BodyEdit" pageType="EditPage" 
		actPath="{SYSURL.bam}/bam/bizSystemConfig/"
		actSave="saveOrUpdate.action"
		actUpdate="saveOrUpdate.action"
		actGetData="listByRange.action?appId={appId}"
		>
	
<div id="divFixTop">
	<div class="ToolbarLite" iconPath="{SYSURL.static}/images/icon16/">
		<div id="btnSave" icon="save.png" onclick="EditPage_Save()">保存</div>
		<div id="download" icon="download.png" onclick="downXml()">下发XML</div>
		<div id="update" icon="clear.png" onclick="updateCache()">更新缓存</div>
	</div>
</div>

<div id="divFixCnt" style="padding:3px;">
<form id="frmMain" name="frmMain" class="Validate">
	<input type="hidden" name="appId" value="" />
	<div style="font-size:16px;font-weight:bold;text-align:center;padding:15px">参数配置</div>
	<div class="TabBase" cssTab="tabItem" cssTabSel="tabItemSel" cssTabOver="tabItemOver" cssCnt="tabCnt" style="padding-left:0px;padding-top:0px;">
		<div class="tabItem">参数设置</div>
		<div class="tabCnt">
			<table width="100%" id="tabbasic" class="TableEdit" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tit">领导班子类型：</td>
					<td class="cnt">
						<input type="text" class="input" name="cfg_group_type" value=""/>
					</td>
				</tr>
				<tr>
					<td class="tit">是否报国资委：</td>
					<td class="cnt">
						<input type="text" class="input" name="cfg_report_sasac" value=""/>
					</td>
				</tr>
				<tr>
					<td class="tit">议题通过类型：</td>
					<td class="cnt">
						<input type="text" class="input" name="cfg_pass_name" value=""/>
					</td>
				</tr>
				<tr>
					<td class="tit">议题否决类型：</td>
					<td class="cnt">
						<input type="text" class="input" name="cfg_veto_name" value=""/>
					</td>
				</tr>
				<tr>
					<td class="tit">议题异常类型：</td>
					<td class="cnt">
						<input type="text" class="input" name="cfg_exception_type" value=""/>
					</td>
				</tr>
				<tr>
					<td class="tit">议题缓议类型：</td>
					<td class="cnt">
						<input type="text" class="input" name="cfg_defer_name" value=""/>
					</td>
				</tr>
                <tr>
                    <td class="tit">审议赞成类型：</td>
                    <td class="cnt">
                        <input type="text" class="input" name="cfg_deliberation_agree" value=""/>
                    </td>
                </tr>
				<tr>
					<td class="tit">企业数据下发XML路径：</td>
					<td class="cnt">
						<input type="text" class="input" name="cfg_downloadXml_url" value=""/>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>	
</div>
	
</div>
</body>
</html>
<%@ include file="/common/inc_bottom.jsp"%>
