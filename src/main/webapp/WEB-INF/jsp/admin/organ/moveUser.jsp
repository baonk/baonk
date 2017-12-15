<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Move User</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">		
		<!-- <link rel="stylesheet" type="text/css" href="/css/registration.css" /> -->
		<link rel="stylesheet" type="text/css" href="/css/general.css" />
		<link rel="stylesheet" type="text/css" href="/css/popup.css" />	
		<script	src="/js/jquery/jquery.min.js"></script>
	    <script	src="/js/admin/organization/management.js"></script>
	    <script	src="/js/popup.js"></script>	    
		<script type="text/javascript" language="javascript">
			var listDepts  = ${listDepartment};
			var arrSubDept = [];			

			window.onload = function () {				
				initData(0);
			}
			
			function close_Click() {			    
			    parent.divPopUpHidden();
			}
			
			function ok_Click() {				
				
			}
			
	    </script>
	</head>
	<body class="popup">
		<h1>Employee moving</h1>	
				
		<div id="deptView" class="divTable2" style="overflow: auto;" ></div>
				
		<div style="margin: 6px 0px 6px 130px; position:fixed; bottom: 0px; left: 0px;">
		    <a id="btnSave" class="baonBttn2" onClick="ok_Click()"><span>OK</span></a>
		    <a id="btnCancel"class="baonBttn2" onClick="close_Click()"><span>Cancel</span></a>
		</div>	
			
	</body>
</html>