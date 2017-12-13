<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="ezOrgan.t248" /></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">		
		<script	src="/js/jquery/jquery.min.js"></script>
	    <script	src="/js/admin/organization/management.js"></script>
	    <script	src="/js/popup.js"></script>	    
		<script type="text/javascript" language="javascript">
			var listDepts = ${listDepartment};
			var arrSubDept = [];			
			
			window.onload = function () {
				initData();
			}
	    </script>
	</head>
	<body class="popup">
		<h1 id="subtitle">Employee moving</h1>		
		<div id="deptView" class="divTable" style="overflow: auto;" ></div>
		<div class="btnposition">
		    <a id="btnSave" class="imgbtn" onClick="OK_Click()"><span><spring:message code='ezOrgan.t124' /></span></a>
		    <a id="btnCancel"class="imgbtn" onClick="window.close()"><span><spring:message code='ezOrgan.t125' /></span></a>
		</div>
	</body>
</html>