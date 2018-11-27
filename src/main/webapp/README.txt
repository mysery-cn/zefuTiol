1、二次开发项目需要实现的文件：（外部开发项目提供平台包时，如下配置文件需要删除）
1） common/biz_head.jsp
    平台在inc_head.jsp中加载该文件，二次开发项目创建该文件并引入自定义js等内容。
    oa项目该文件内容如下：

    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <script type="text/javascript" src="<%=SYSURL_static%>/js/biz_common.js"></script>
    <c:if test="${param.moduleId!=null||param.appId!=null}">
    	<script type="text/javascript" src="<%=SYSURL_static%>/js/${param.moduleId}${param.appId}.js"></script>
    	<script type="text/javascript" src="<%=SYSURL_static%>/js/stati/st_${param.moduleId}${param.appId}.js"></script>
    </c:if>
2）static/js/biz_common.js
   该文件为二次开发项目中自定义js文件，非该名称也允许。该js中可以重构平台js方法，可以定义全局公共js方法等。
   oa项目中该文件内容如下：

   //业务应用实现此文件
   var SYSVAR_SystemName='国务院办公厅统一政务运转信息系统';//'国务院办公厅统一政务运转信息系统';
   if (SYSURL.oa) SYSURL.biz=SYSURL.oa;  //当前业务系统域名

   //初始化URL(替换URL中的变量)
   function biz_jtParseURL(sURL){
   	//sURL=sURL.replace(/\{SYSURL.oa\}/ig, SYSURL_oa);
   	//sURL = jtReplaceVAR(sURL, 'appId,bizId');
   	return sURL;
   }
   //业务系统自定义解析变量
   function jtParseURL_before(str){
   	str=str.replace(/\{OA_UMS_OrgTreeUrl\}/ig, "{UMS_OrgTreeUrl}5011&maxLevel=2&idType=parentOrgId&t={JSTime}");
   	str=str.replace(/\{cfg_urger_handleCategory\}/ig, "{CST_URL_SYSCFG}?appId=urger&key=cfg_urger_handleCategory&t={JSTime}");
   	str=str.replace(/\{cfg_urger_donestatus\}/ig, "{CST_URL_SYSCFG}?appId=urger&key=cfg_urger_donestatus&t={JSTime}");
   	return str;
   }