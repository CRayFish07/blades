<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/WEB-INF/page/common/taglibs-all.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户信息</title>
<link href="${ctx}/resource/sys/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resource/common/css/sys.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}/resource/sys/js/jquery.js"></script>
<style type="">
body
{
	min-width: 500px;
}
</style>
</head>

<body>

    <div class="formbody">
    
	    <div class="formtitle"><span>账号</span></div>
	    <ul class="forminfo">
	    <li><label>账号:</label><label>${account.username}</label></li>
	    <li><label>手机号码:</label><label>${account.mobile}</label></li>
	    <li><label>邮箱:</label><label>${account.email}</label></li>
	    </ul>
	    
	    <div class="formtitle" style="margin-top: 40px;"><span>资料</span></div>
	    <ul class="forminfo">
	    <li><label>姓名:</label><label>${user.name}</label></li>
	    <li><label>身份证号:</label><label>${user.idNo}</label></li>
	    <li><label>性别:</label><label>${user.gender}</label></li>
	    <li><label>警号:</label><label>${user.code}</label></li>
	    <li><label>出生日期:</label><label>${user.birthdate}</label></li>
	    <li><label>办公电话:</label><label>${user.officePhone}</label></li>
	    <li><label>家庭电话:</label><label>${user.phone }</label></li>
	    <li><label>家庭住址:</label><label>${user.address }</label></li>
	    <li><label>备注:</label><label>${user.remark }</label></li>
	    </ul>
	    
	    <div class="formtitle" style="margin-top: 40px;"><span>岗位</span></div>
	    <ul class="forminfo">
	    <c:forEach items="${orgList }" var="org">
	    	<li><label>${org.RELATION }:</label><label style="width: auto;">${org.PNAME }[${org.NAME }]</label></li>
	    </c:forEach>
	    </ul>
	    
	    <div class="formtitle" style="margin-top: 40px;"><span>权限</span></div>
	    <ul class="forminfo">
	    </ul>
    
    </div>


</body>

</html>

