	var currentClickedDeptId = null;
	var currentClickedItem = null;
	
	function initData() {
		var deptView = document.getElementById("deptView");
		
		for (var i = 0; i < listDepts.length; i++) {				
			var divEl = document.createElement("div");
			var img1 = document.createElement("img");
			img1.setAttribute("class", "deptOn");			
			img1.onclick = function () {companyOnClick(this);}
			var img2 = document.createElement("img");
			img2.setAttribute("class", "companyImg");
			img2.setAttribute("deptId", listDepts[i]["departmentid"]);
			img2.onclick = function () {getDetailData(this);};
			var span = document.createElement("span");
			span.innerHTML = listDepts[i]["departmentname"];
			span.setAttribute("deptId", listDepts[i]["departmentid"]);
			span.setAttribute("style", "cursor: pointer;");
			span.setAttribute("name", listDepts[i]["departmentid"]);
			span.setAttribute("class", "subOff");
			span.onclick = function () {getDetailData(this);};
			
			divEl.appendChild(img1);
			divEl.appendChild(img2);
			divEl.appendChild(span);
			divEl.setAttribute("id", "company" + i);
			divEl.setAttribute("order", i);
			divEl.setAttribute("style", "padding-top: 5px; padding-left: 5px;");								
			deptView.appendChild(divEl);		
			displaySubDept(divEl, listDepts[i]["subDept"]);
			
			
			if (listDepts[i]["departmentid"] == usercompID) {				
				var userDeptElm = document.getElementsByName(userDeptID)[0];
				getDetailData(userDeptElm);
			}
		}			
	}
	
	function refreshView() {
		var currentElement = document.getElementById(currentClickedDeptId);
		currentElement.click();
	}
	
	function getDetailData(obj) {			
		var deptId = obj.getAttribute("deptId");
		
		if (currentClickedDeptId != deptId) {
			//Set previous clicked element class off 
			if (currentClickedDeptId != null) {
				var deptElemt = document.getElementById(currentClickedDeptId);			
				deptElemt.setAttribute("class", "subOff");				
			}
			
			currentClickedDeptId = deptId;
			//Set current clicked element class on
			if (obj.nodeName.toLowerCase() == "span") {
				obj.setAttribute("class", "subOn");
				obj.setAttribute("id", deptId);
			}
			else {
				var spanObj = obj.nextSibling;
				spanObj.setAttribute("class", "subOn");
				spanObj.setAttribute("id", deptId);
			}
		}
		
		$.ajax({			
			type: "POST",
			url: "/admin/organ/getDetailInfo",
			data: {
				"deptID"	: deptId,
				"optionVal" : value
			},
			dataType: "JSON",
			async: true,
			success: function(result) {												
				renderData(result);
			},
			error: function (xhr, status, e){
				alert("Get data failed!");
			}
		});	
		
	}
	
	function renderData(result) {
		var detailDeptView = document.getElementById("detailDeptView");
		
		if (result["result"] == "Error") {			
			deleteOldElement(detailDeptView, 4);			
			document.getElementById("errorDiv").style.display = "block";
		}
		else {
			document.getElementById("errorDiv").style.display = "none";
			var len = result.length;			
			deleteOldElement(detailDeptView, 4);
			
			switch (value) {
				case "muser":
						for (var i = 0; i < len; i++) {
							addUserNode(result[i]["userid"], result[i]["username"], result[i]["departmentname"], result[i]["position"]);
						}
						break;
				case "mdept":						
						for (var i = 0; i < len; i++) {
							addDeptNode(result[i]["departmentid"], result[i]["departmentname"], result[i]["companyname"]);
						}
						break;
				case "mcomp":					
						addCompNode(result["companyid"], result["companyname"]);						
						break;
			}
		}
	}
	
	function setItem(obj) {			
		if (currentClickedItem != null) {
			var previousItem = document.getElementById(currentClickedItem);
			if (previousItem) {				
				previousItem.setAttribute("class", "subDisplayTab");
			}
		}
		
		obj.setAttribute("class", "subDisplayTabSelect");
		currentClickedItem = obj.getAttribute("id");	
		
	}
	
	function addCompNode(compId, companyName) {				
		var detailDeptView = document.getElementById("detailDeptView");			
		var divNewDepart = document.createElement("div");		
		var divCompId = document.createElement("div");
		var divCompanyName = document.createElement("div");		
		
		divNewDepart.setAttribute("id", compId + "-item");
		divNewDepart.onclick = function () {setItem(this);};
		divNewDepart.setAttribute("class", "subDisplayTab");
		divCompId.setAttribute("class", "subDisplayElmTab");		
		divCompanyName.setAttribute("class", "subDisplayElmTab");		
		
		//divNewDepart.setAttribute("id", compId);		
		divCompId.setAttribute("style", "left: 10%;");
		divCompanyName.setAttribute("style", "left: 60%;");
	
		
		divCompId.innerHTML = compId;
		divCompanyName.innerHTML = companyName;	
		
		divNewDepart.appendChild(divCompId);
		divNewDepart.appendChild(divCompanyName);
		
		detailDeptView.appendChild(divNewDepart);
	}
	
	function addDeptNode(deptId, deptName, companyName) {
		var detailDeptView = document.getElementById("detailDeptView");	
		
		var divNewDepart = document.createElement("div");		
		var divDepartName = document.createElement("div");
		var divCompanyName = document.createElement("div");
		
		divNewDepart.setAttribute("id", deptId + "-item");
		divNewDepart.onclick = function () {setItem(this);};
		divNewDepart.setAttribute("class", "subDisplayTab");
		divDepartName.setAttribute("class", "subDisplayElmTab");		
		divCompanyName.setAttribute("class", "subDisplayElmTab");		
		
		//divNewDepart.setAttribute("id", deptId);		
		divDepartName.setAttribute("style", "left: 10%;");
		divCompanyName.setAttribute("style", "left: 60%;");
	
		
		divDepartName.innerHTML = deptName;
		divCompanyName.innerHTML = companyName;
	
		
		divNewDepart.appendChild(divDepartName);
		divNewDepart.appendChild(divCompanyName);
		
		detailDeptView.appendChild(divNewDepart);
	}
	
	function deleteOldElement(detailDeptView, start) {			
		var len = detailDeptView.children.length;
		for (var i = start; i < len; i++) {			
			detailDeptView.removeChild(detailDeptView.children[start]);
		}
	}
	
	function addUserNode(userId, userName, departName, position) {		
		var detailDeptView = document.getElementById("detailDeptView");	
		
		var divNewUser = document.createElement("div");		
		var divUserName = document.createElement("div");
		var divDepartId = document.createElement("div");
		var divPosition = document.createElement("div");
		
		divNewUser.setAttribute("id", userId + "-item");
		divNewUser.onclick = function () {setItem(this);};
		divNewUser.setAttribute("class", "subDisplayTab");
		divUserName.setAttribute("class", "subDisplayElmTab");		
		divDepartId.setAttribute("class", "subDisplayElmTab");
		divPosition.setAttribute("class", "subDisplayElmTab");
		
		//divNewUser.setAttribute("id", userId);		
		divUserName.setAttribute("style", "left: 10%;");
		divDepartId.setAttribute("style", "left: 40%;");
		divPosition.setAttribute("style", "left: 80%;");
		
		divUserName.innerHTML = userName;
		divDepartId.innerHTML = departName;
		divPosition.innerHTML = position;
		
		divNewUser.appendChild(divUserName);
		divNewUser.appendChild(divDepartId);
		divNewUser.appendChild(divPosition);
		detailDeptView.appendChild(divNewUser);
	}
	
	function displaySubDept(mainDeptElm, list) {	
		var pos = null;
		var mainEl = null;
		
		if (mainDeptElm.getAttribute("parent")) {					
			pos = mainDeptElm.getAttribute("parent");
			mainEl = mainDeptElm.parentElement;
		}
		else {
			pos = mainDeptElm.getAttribute("order");
			mainEl = mainDeptElm;
		}							
		
		for (var i = 0; i < list.length; i++) {
			var divEl = document.createElement("div");
			
			var img1 = document.createElement("img");			
			var img2 = document.createElement("img");
			img2.setAttribute("class", "deptImg");
			img2.setAttribute("deptId", list[i]["departmentid"]);
			img2.onclick = function () {getDetailData(this);};
			var span = document.createElement("span");
			span.innerHTML = list[i]["departmentname"];
			span.setAttribute("style", "cursor: pointer;");
			span.setAttribute("deptId", list[i]["departmentid"]);
			span.setAttribute("name", list[i]["departmentid"]);
			span.setAttribute("class", "subOff");
			span.onclick = function () {getDetailData(this);};
			
			divEl.appendChild(img1);
			divEl.appendChild(img2);
			divEl.appendChild(span);					
			divEl.setAttribute("style", "padding-top: 5px; padding-left: 15px;");	
			divEl.setAttribute("order", pos);
			insertAfter(divEl, mainEl);		
			
			if (list[i]["hasSubDept"] == 1) {
				//img1.setAttribute("class", "deptOff");	
				img1.setAttribute("parent", pos);	
				img1.setAttribute("deptId", list[i]["departmentid"]);
				img1.setAttribute("id", list[i]["departmentid"] + "+" + pos);
				img1.onclick = function () {deptOnClick(this);};
				
				if (list[i]["subDept"] != null && list[i]["subDept"] != "null") {
					var uniqueId = img1.getAttribute("id");		
					arrSubDept.push(uniqueId);
					img1.setAttribute("class", "deptOn");
					displaySubDept(divEl, list[i]["subDept"]);
				}
				else {
					img1.setAttribute("class", "deptOff");
				}
				
			}
			else {
				img1.setAttribute("class", "deptNone");
			}
		}					
	}
			
	function companyOnClick(obj) {				
		var position = parseInt(obj.parentElement.getAttribute("order"));				
		var childs = obj.parentElement.childNodes;
		
		if (obj.getAttribute("class") == "deptOn") {									
			for (var i = 3; i < childs.length; i++) {
				childs[i].style.display = "none";
			}					
			obj.setAttribute("class", "deptOff");
		}
		else {
			for (var i = 3; i < childs.length; i++) {
				childs[i].style.display = "";
			}					
			obj.setAttribute("class", "deptOn");
		}
		
	}		
	
	function deptOnClick(obj) {
		var parentElm = obj.parentElement;
		var position = parseInt(parentElm.getAttribute("parent"));
		var childs = parentElm.childNodes;
		
		if (obj.getAttribute("class") == "deptOn") {									
			for (var i = 3; i < childs.length; i++) {
				childs[i].style.display = "none";
			}					
			obj.setAttribute("class", "deptOff");
		}
		else {
			var uniqueId = obj.getAttribute("id");					
			if (arrSubDept.indexOf(uniqueId) > -1) {
				renderSubDept(obj, childs);
			}
			else {
				var deptId = obj.getAttribute("deptId");				
				
				$.ajax({
					type: "POST",
					url: "/admin/organ/getSimpleSubDept",
					data: {
						"deptID": deptId
					},
					dataType: "JSON",
					async: true,
					success: function(result) {							
						var listDept = result["subDept"];
						arrSubDept.push(uniqueId);
						displaySubDept(obj, listDept);	
						renderSubDept(obj, childs);								
					},
					error: function (xhr, status, e){
						alert("Get department data failed");
					}
				});	
			}															
		}									
	}		
	
	function renderSubDept(obj, childList) {
		for (var i = 3; i < childList.length; i++) {
			childList[i].style.display = "";
		}	
		
		obj.setAttribute("class", "deptOn");						
	}
	
	function changeList(obj) {
		value = obj.getAttribute("value");
		
		if (value == "muser") {
			document.getElementById("userOpt").style.display = "block";
			document.getElementById("deptOpt").style.display = "none";
			document.getElementById("compOpt").style.display = "none";
			document.getElementById("employeeList").style.display = "block";
			document.getElementById("deptList").style.display = "none";
			document.getElementById("companyList").style.display = "none";
		}
		else if (value == "mdept") {
			document.getElementById("userOpt").style.display = "none";
			document.getElementById("deptOpt").style.display = "block";
			document.getElementById("compOpt").style.display = "none";
			document.getElementById("employeeList").style.display = "none";
			document.getElementById("deptList").style.display = "block";
			document.getElementById("companyList").style.display = "none";
		}
		else {
			document.getElementById("userOpt").style.display = "none";
			document.getElementById("deptOpt").style.display = "none";
			document.getElementById("compOpt").style.display = "block";
			document.getElementById("employeeList").style.display = "none";
			document.getElementById("deptList").style.display = "none";
			document.getElementById("companyList").style.display = "block";
		}
	}
	
	function insertAfter(newNode, referenceNode) {
		if (referenceNode.getAttribute("parent")) {
			 referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
		}
		else {
			referenceNode.appendChild(newNode);
		}	   
	}
	
	function addUser() {
		if (!currentClickedDeptId && !document.getElementById(currentClickedDeptId)){
			alert("Please select a department!");
			return;
		}
		
		var currentDept = document.getElementById(currentClickedDeptId);
		var deptName = currentDept.innerHTML;
		
		divPopUpShow(810, 400, "/admin/userRegistration?deptId=" + currentClickedDeptId + "&deptName=" + deptName);		
	}
	
	
	function displayUserInfo() {		
		if (!currentClickedItem && !document.getElementById(currentClickedItem)){
			alert("Please select a user!");
			return;
		}
		
		var currentUserId = currentClickedItem.split("-")[0];		
		
		divPopUpShow(810, 400, "/admin/userRegistration?userId=" + currentUserId);
	}
	
	function moveUser() {
		if (!currentClickedItem && !document.getElementById(currentClickedItem)){
			alert("Please select a user!");
			return;
		}
		
		var currentUserId = currentClickedItem.split("-")[0];		
		
		divPopUpShow(400, 400, "/admin/moveUser?userId=" + currentUserId);
	}
	
	function delUser() {
		if (!currentClickedItem && !document.getElementById(currentClickedItem)){
			alert("Please select a user!");
			return;
		}
		
		var result = confirm("Do you want to delete this user?");
		
		if (result == true) {
			var currentUserId = currentClickedItem.split("-")[0];		

			$.ajax({			
				type: "POST",
				url: "/admin/deleteUser",
				data: {
					"userId" : currentUserId					
				},
				dataType: "text",
				async: true,
				success : function(data, textStatus, jqXHR) {											
					alert("Delete user successful!");
					refreshView();
				},
 				error : function(jqXHR, textStatus, errorThrown) {            	    
					alert("Cannot delete this user! Error: " + jqXHR.status + ", " + textStatus);
				}
			});	
		}	
	}
	