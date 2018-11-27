<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<%@ include file="/common/inc_head.jsp"%>
	<title>GAP</title>
</head>
<script type="text/javascript">
	
    var role = ","+curUser.roleListStr+",";
    var json = getCfgValueJson('global','cfg_portal_indexurl');
    var def ;
    for(var i=0;i<json.data.length;i++){
		var b=json.data[i].text.split(':');
		if(role.indexOf(b[0])!=-1){
           def = b[1];
           break;
        }else if(b[0]=="default"){
           def = b[1];
        }
	}
    
    self.location= def;
</script>
<body>
</body>
</html>