<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%
	boolean isSuccess = false;
	if (null != request.getAttribute("isSuccess")) {
		isSuccess = (Boolean) request.getAttribute("isSuccess");
	}
%>
<html>
<head>
<title>旁路报文认证示例</title>
<link href="css/context.css" rel="stylesheet" type="text/css">
</head>
<body>
	<table class="table" width="100%">
		<tr>
			<td>
				<table class="context_table" width="100%">
					<tr>
						<td colspan="3" class="panel">结果</td>
					</tr>
					<tr>
						<td width="20%">认证结果：</td>
						<td width="80%" colspan="2"><%=isSuccess ? "成功" : "失败"%></td>
					</tr>
					<%
						if (isSuccess) {
					%>
					<tr>
						<td colspan="3" class="panel">证书信息</td>
					</tr>
					<%
						Map certAttributeNodeMap = (Map) request
								.getAttribute("certAttributeNodeMap");
						Map pmsAttributeNodeMap = (Map) request
								.getAttribute("pmsAttributeNodeMap");
						Map umsAttributeNodeMap = (Map) request
								.getAttribute("umsAttributeNodeMap");
						Map customAttributeNodeMap = (Map) request
								.getAttribute("customAttributeNodeMap");
						if ((certAttributeNodeMap == null || certAttributeNodeMap.size() == 0)
								&& (umsAttributeNodeMap == null || umsAttributeNodeMap.size() == 0)
								&& (pmsAttributeNodeMap == null || pmsAttributeNodeMap.size() == 0)
								&& (customAttributeNodeMap == null || customAttributeNodeMap.size() == 0)) {
					%>
					<tr>
						<td width="20%">属性值：</td>
						<td width="80%" colspan="2">无</td>
					</tr>
					<%
						} else {
								if (certAttributeNodeMap != null && certAttributeNodeMap.size() > 0) {
									Iterator iter = certAttributeNodeMap.entrySet().iterator();
									while (iter.hasNext()) {
										Map.Entry entry = (Map.Entry) iter.next();
										Object key = entry.getKey();
										String[] keys = null;
										if (key != null && !key.equals("")) {
											keys = (String[]) key;
											Object val = entry.getValue();
											if (val != null) {
					%>
					<tr>
						<td width="20%">属性名：</td>
						<%
							if (keys[1] != null) {
						%>
						<td width="60%"><%=keys[0]%></td>
						<td width="30%">上级机构：<%=keys[1]%>
						</td>
						<%
							} else {
						%>
						<td colspan="2" width="80%"><%=keys[0]%></td>
						<%
							}
						%>
					</tr>
					<tr>
						<td width="20%">属性值：</td>
						<td width="80%" colspan="2"><%=val.toString()%></td>
					</tr>
					<%
						}
										}
									}
								} else {
					%>
					<tr>
						<td>属性值：</td>
						<td>无</td>
					</tr>
					<%
						}
					%>
					<tr>
						<td colspan="3" class="panel">UMS信息</td>
					</tr>
					<%
						if (umsAttributeNodeMap != null && umsAttributeNodeMap.size() > 0) {
									Iterator umsIter = umsAttributeNodeMap.entrySet().iterator();
									while (umsIter.hasNext()) {
										Map.Entry entry = (Map.Entry) umsIter.next();
										Object key = entry.getKey();
										String[] keys = null;
										if (key != null && !key.equals("")) {
											keys = (String[]) key;
											Object val = entry.getValue();
											if (val != null) {
					%>
					<tr>
						<td width="20%">属性名：</td>
						<%
							if (keys[1] != null) {
						%>
						<td width="60%"><%=keys[0]%></td>
						<td width="30%">上级机构：<%=keys[1]%>
						</td>
						<%
							} else {
						%>
						<td width="80%" colspan="2"><%=keys[0]%></td>
						<%
							}
						%>
					</tr>
					<tr>
						<td width="20%">属性值：</td>
						<td width="80%" colspan="2"><%=val.toString()%></td>
					</tr>
					<%
						}
										}
									}
								} else {
					%>
					<tr>
						<td width="20%">属性值：</td>
						<td width="80%" colspan="2">无</td>
					</tr>
					<%
						}
					%>
					
					<tr>
						<td colspan="3" class="panel">PMS信息</td>
					</tr>
					<%
						if (pmsAttributeNodeMap != null && pmsAttributeNodeMap.size() > 0) {
									Iterator pmsIter = pmsAttributeNodeMap.entrySet().iterator();
									while (pmsIter.hasNext()) {
										Map.Entry entry = (Map.Entry) pmsIter.next();
										Object key = entry.getKey();
										String[] keys = null;
										if (key != null && !key.equals("")) {
											keys = (String[]) key;
											Object val = entry.getValue();
											if (val != null) {
					%>
					<tr>
						<td width="20%">属性名：</td>
						<%
							if (keys[1] != null) {
						%>
						<td width="60%"><%=keys[0]%></td>
						<td width="30%">上级机构：<%=keys[1]%>
						</td>
						<%
							} else {
						%>
						<td width="80%" colspan="2"><%=keys[0]%></td>
						<%
							}
						%>
					</tr>
					<tr>
						<td width="20%">属性值：</td>
						<td width="80%" colspan="2"><%=val.toString()%></td>
					</tr>
					<%
						}
										}
									}
					%>
					<%
						} else {
					%>
					<tr>
						<td width="20%">属性值：</td>
						<td width="80%" colspan="2">无</td>
					</tr>
					<%
						}
					%>
					
					
					<tr>
						<td colspan="3" class="panel">自定义属性信息</td>
					</tr>
					<%
						if (customAttributeNodeMap != null && customAttributeNodeMap.size() > 0) {
									Iterator customIter = customAttributeNodeMap.entrySet().iterator();
									while (customIter.hasNext()) {
										Map.Entry entry = (Map.Entry) customIter.next();
										Object key = entry.getKey();
										String[] keys = null;
										if (key != null && !key.equals("")) {
											keys = (String[]) key;
											Object val = entry.getValue();
											if (val != null) {
					%>
					<tr>
						<td width="20%">属性名：</td>
						<%
							if (keys[1] != null) {
						%>
						<td width="60%"><%=keys[0]%></td>
						<td width="30%">上级机构：<%=keys[1]%>
						</td>
						<%
							} else {
						%>
						<td width="80%" colspan="2"><%=keys[0]%></td>
						<%
							}
						%>
					</tr>
					<tr>
						<td width="20%">属性值：</td>
						<td width="80%" colspan="2"><%=val.toString()%></td>
					</tr>
					<%
						}
										}
									}
					%>
					<%
						} else {
					%>
					<tr>
						<td width="20%">属性值：</td>
						<td width="80%" colspan="2">无</td>
					</tr>
					<%
						}
							}
						} else {
							String errCode = (String) request.getAttribute("errCode");
							String errDesc = (String) request.getAttribute("errDesc");
							if (errCode != null && !errCode.equals("")) {
					%>
					<tr>
						<td>错误码：</td>
						<td><%=errCode%></td>
					</tr>
					<%
						}
							if (errDesc != null && !errDesc.equals("")) {
					%>
					<tr>
						<td>错误描述：</td>
						<td><%=errDesc%></td>
					</tr>
					<%
						}
						}
					%>
					<%-- <tr>
						<td colspan="3" class="panel">自定义属性信息</td>
					</tr>
					<tr>
						<td width="20%">属性值：</td>
						<td width="80%" colspan="2"><%=request.getAttribute("customAttributeNodeMap")%></td>
					</tr> --%>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
