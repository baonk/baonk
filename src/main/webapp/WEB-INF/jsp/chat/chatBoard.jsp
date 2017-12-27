<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
	<link rel="stylesheet" type="text/css" href="/css/home.css" />
	<link rel="stylesheet" type="text/css" href="/css/chat/chat.css" />
	<link rel="stylesheet" href="/css/bootstrap.min.css">
	<script	src="/js/jquery/jquery.min.js"></script>
	<script	src="/js/bootstrap.min.js"></script>	
	<script type="text/javascript">
 		function auto_grow(element) {	    	
		    element.style.height = "50px"; 			        
		    var value = element.scrollHeight;
		    
	        element.style.height = (element.scrollHeight) + "px";	              
	        document.getElementById("bnkChatDiv").style.height = value + "px";	           	
	    }
	</script>
	
</head>

<body>
	<div class="container">	
		<div class="bnkpanel-group">
			<div class="bnkpanel bnkpanel-primary">				
				<div class="bnkContainer">
				    <div class="bnk bnkLeft">
				    	<div class="leftHeaderMenu">
					    	<div class="settingImg"><img src="/images/chat/sss.png" class ="chatImage" /></div>
					    	<div class="txtMessage">Chat history list</div>
					    	<div class="editImg"><img src="/images/chat/34.png" class ="chatImage" /></div>
				    	</div>
				    	<div class="bnkContent">
				    		<div class="bnkSearch">
				    			<img src="/images/chat/search.png" style="height: 20px; width: 20px; margin: 2px;"/>
				    			<input class="bnkbttnSearch" placeholder="Search chat history"  maxlength="50"; autocomplete="off"/>
				    		</div>
				    		<div class="bnkHistory"></div>
				    	</div>	
					</div>
				    <div class="bnk bnkCenter" style="margin-left: 0px; margin-right: 0px;">				    	
			    		<div class="centerHeaderMenu">
			    		</div>
				    	<div class="bnkChatContent">
				    		<div class="message">
							  <div class="messageAvatar1">
							    <img class="messageAvatar" src="https://vi.gravatar.com/userimage/119146805/dcb3ad95a00ec4a4284c36d7c401a156.png">
							  </div>
							  <div class="messageBody">
							  	<div class="messageContent messagSelfBody">
							  		<p>Chào bạn 1, làm quen với mình nhé! :D</p>
							  	</div>
							    <div class="messageContent messagSelfBody">
							    	<p>Chào bạn 2, làm quen với mình nhé! :D</p>
							    </div>				    
							  </div>
							</div>
							<div class="message messageOther">
							  <div class="messageAvatar1">
							    <img class="messageAvatar" src="https://vi.gravatar.com/userimage/119146805/21b7e614de27cdf36dcff3c48b15f54e.png">
							  </div>
							  <div class="messageBody">
							  	<div class="messageContent messagOtherBody">
							    	 <p>Hi hi <3</p>
							     </div>
							  </div>
							</div>
				    	</div>
				    	<div class="bnkChatTool" id="bnkChatDiv">
				    		<div class="bnkCmtInput">
				    			<textarea cols="20" rows="1" id="" oninput="auto_grow(this)" style="height: 50px; outline: none; border: none; resize: none; overflow: hidden; font-size: 12px; line-height: 15px; width: 100%; margin: 0px; padding: 16px 10px;"></textarea>
				    		</div>
				    		<div class="bnkCmtTool">
				    			<ul style="padding-left: 0px; position: absolute; bottom: -7px;">
				    				<li><div style="border-left: 1px solid #DDD;"><img id="bnkEmoticon" src="/images/chat/upload.png" style="display: block; height: 30px; width: 35px; cursor: pointer; margin-top: 11px; padding-left: 5px;" onclick=""></div></li>
				    				<li><div><img id="bnkEmoticon" src="/images/chat/emo3.png" style="display: block; height:40px; width:40px; cursor: pointer; margin-top: 6px;" onclick=""></div></li>				    				
				    			</ul>
				    		</div>
				    	</div>				    	
					</div>
				    <div class="bnk bnkRight">right</div>
				</div>		
			</div>
		</div>		
	</div>
</body>
</html>