    var currentGroupSticker 	= -1;
    var stickerIndex 			= null;
    var numberOfGroupSticker 	= 4;
    var lastChattedUser			= null;

    function check_key(event) {
    	if (event.which == 13 && !event.shiftKey) {
    		event.preventDefault();			
			sendMessage();
    	}
    }
    
    function sendMessage() {
    	var textValue = document.getElementById("bnkCmtTxt").value;
    	if (textValue == '') {
    		return;
    	}
    	
    	if (lastChattedUser == null || lastChattedUser != currentUser) {    		
    		document.getElementById("bnkCmtTxt").value = "";
    		showSelfChat1(textValue);    		
    	}
    	else {
    		if (lastChattedUser == currentUser) {    			
    			document.getElementById("bnkCmtTxt").value = "";
    			showSelfChat2(textValue);
    		}
    		else {    			
    			document.getElementById("bnkCmtTxt").value = "";
    			showSelfChat1(textValue);
    		}
    	}
    }
    
    function showSelfChat1(textValue) {
    	var bnkChatTblElmt    = document.getElementById("bnkChatTbl");
		var mainDivElmt       = document.createElement("div");
		mainDivElmt.className = "message";
		
		//process user's avatar
		var avatarDivElmt = document.createElement("div");
		var avatarImgElmt = document.createElement("img");
		avatarDivElmt.className = "messageAvatar1";
		avatarImgElmt.className = "messageAvatar";
		avatarImgElmt.src 		= "https://vi.gravatar.com/userimage/119146805/dcb3ad95a00ec4a4284c36d7c401a156.png"; //tam thoi
		avatarDivElmt.appendChild(avatarImgElmt);
		
		//process messageContent
		var contentDivElmt 		  = document.createElement("div");
		var txtDivElmt 			  = document.createElement("div");
		var paragraphElmt 		  = document.createElement("p");
		txtDivElmt.className 	  = "messageContent";
		contentDivElmt.className  = "messageBody";
		paragraphElmt.textContent = textValue;
		txtDivElmt.appendChild(paragraphElmt);
		contentDivElmt.appendChild(txtDivElmt);
		
		//Add avatar and message content to main div
		mainDivElmt.appendChild(avatarDivElmt);
		mainDivElmt.appendChild(contentDivElmt);
		
		if (bnkChatTblElmt.firstElementChild.getAttribute("id") == "bnkNoData") {
			bnkChatTblElmt.removeChild(bnkChatTblElmt.firstElementChild);
		}
		
		bnkChatTblElmt.appendChild(mainDivElmt);
		lastChattedUser = currentUser;
    }
    
    function showSelfChat2(textValue) {
    	var bnkChatTblElmt = document.getElementById("bnkChatTbl");
    	var currentDivElmt = bnkChatTblElmt.lastElementChild.lastElementChild;
    	
		//process messageContent
		
		var txtDivElmt 			  = document.createElement("div");
		var paragraphElmt 		  = document.createElement("p");
		txtDivElmt.className 	  = "messageContent";		
		paragraphElmt.textContent = textValue;
		txtDivElmt.appendChild(paragraphElmt);		
		
		//Add message content to main div	
		currentDivElmt.appendChild(txtDivElmt);
		
    }
    
    function showOtherChat1() {
    	
    }
    
    function showOtherChat2() {
    	
    }
    
	function checkScrollBars() {		
		if (document.getElementById("_listG" + stickerIndex + "Table").scrollHeight > 320) {
    		document.getElementById("emoticonPanel").style.width = "420px";    		
    	}
    }
    
    function addSticker() {
    	if (document.getElementById("emoticonPanel").style.display == "block") {
    		document.getElementById("emoticonPanel").style.display = "none";
    		document.getElementById("bnkEmoticon").style.backgroundImage = "url('/images/chat/emo3.png')";
    	}
    	else {
    		//baonk added
	    	document.addEventListener("keydown", function handleKeyDown(evt) {	
	    		evt = evt || window.event;
	    		
	    	    if (evt.keyCode == 27) {
		    		document.getElementById("emoticonPanel").style.display = "none";
		    		document.getElementById("bnkEmoticon").style.backgroundImage = "url('/images/chat/emo3.png')";
	    	    }
	    	    
		    	document.removeEventListener("keydown", handleKeyDown);				    	
	    	});	 
    		//end
    		
    		document.getElementById("bnkEmoticon").style.backgroundImage = "url('/images/chat/emo4.png')";
	    	processGroupStickers();
	    	stickerIndex = 1;		    	
	    	document.getElementById("_group1").style.backgroundColor  = "#d9d9d9";
	    	document.getElementById("_listG1").style.display = "block";
	    	
	    	for (var i = 2; i <= numberOfGroupSticker; i++) {
	    		document.getElementById("_group" + i).style.backgroundColor  = "#fff";
	    		document.getElementById("_listG" + i).style.display = "none";
	    	}		    		    	
	    	
	    	document.getElementById("emoticonPanel").style.display = "block";
	    	checkScrollBars();
    	}
    }
    
    function changeStickerGroup(obj) {		    	
    	document.getElementById("_group" + stickerIndex).style.backgroundColor  = "#fff";
    	obj.style.backgroundColor  = "#d9d9d9";
    	document.getElementById("_listG" + stickerIndex).style.display = "none";
    	var imageTag = obj.firstElementChild;
    	
    	if (imageTag.src.indexOf("hackerGirl.png") !== -1) {		    		
    		stickerIndex = 1;		    		
    	}
    	else if (imageTag.src.indexOf("crayonShin.png") !== -1) {
    		stickerIndex = 2;
    	}
    	else if (imageTag.src.indexOf("catEmoticon.png") !== -1) {
    		stickerIndex = 3;
    	}
    	else {
    		stickerIndex = 4;
    	}
    	document.getElementById("_listG" + stickerIndex).style.display = "block";
    	checkScrollBars();
    }
    
    function processGroupStickers() {				
    	if (numberOfGroupSticker > 8) {
    		currentGroupSticker = 8;
    		
    		for (var i = 9; i <= numberOfGroupSticker; i++) {
    			document.getElementById("_group" + i).style.display = "none";
    		}
    		
    		document.getElementById("nextEmoticon").src = "/images/next.png";
    		document.getElementById("nextEmoticon").style.cursor = "pointer";
    		document.getElementById("nextEmoticon").onclick = function () { showNextGroupSticker(); };
    	}
    	else {
    		for (var i = numberOfGroupSticker + 1; i <= 8; i++) {
    			document.getElementById("_group" + i).style.display = "none";
    		}		    		
    	}
    }
    
    function showNextGroupSticker() {
    	currentGroupSticker = currentGroupSticker + 1;
    	document.getElementById("_group" + (currentGroupSticker - 8)).style.display = "none";
    	document.getElementById("_group" + currentGroupSticker).style.display = "block";
    	document.getElementById("previousEmoticon").src = "/images/previous.png";
    	document.getElementById("previousEmoticon").style.cursor = "pointer";
    	document.getElementById("previousEmoticon").onclick = function() { showPreviousGroupSticker(); };
    	
    	if (currentGroupSticker >= numberOfGroupSticker) {
    		document.getElementById("nextEmoticon").src = "/images/next1.png";
    		document.getElementById("nextEmoticon").onclick = null;
    		document.getElementById("nextEmoticon").style.cursor = "default";
    	}
    }
    
    function showPreviousGroupSticker() {
    	document.getElementById("_group" + currentGroupSticker).style.display = "none";
    	document.getElementById("_group" + (currentGroupSticker - 8)).style.display = "block";
    	currentGroupSticker = currentGroupSticker - 1;			    	
    	document.getElementById("nextEmoticon").src = "/images/next.png";
		document.getElementById("nextEmoticon").style.cursor = "pointer";
		document.getElementById("nextEmoticon").onclick = function () { showNextGroupSticker(); };
		
    	if (currentGroupSticker == 8) {
    		document.getElementById("previousEmoticon").src = "/images/previous1.png";
    		document.getElementById("previousEmoticon").onclick = null;
    		document.getElementById("previousEmoticon").style.cursor = "default";
    	}
    }
    