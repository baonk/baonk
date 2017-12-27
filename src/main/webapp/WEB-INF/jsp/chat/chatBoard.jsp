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
<!-- 				    		<div class="message">
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
							</div> -->
							<c:choose>
								<c:when test="${hasChat != 0}">
									<c:set var="currCluster" value="${0}" />
									<c:forEach var="list" items="${listMessage}" varStatus="loop">
										<div class="${list.senderId == userId ? 'message' : 'message messageOther'}">
											<div class="messageAvatar1">
												<img class="messageAvatar" src="https://vi.gravatar.com/userimage/119146805/dcb3ad95a00ec4a4284c36d7c401a156.png">
											</div>
											<div class="messageBody">
												<div class="${list.senderId == userId ? 'messageContent messagSelfBody' : 'messageContent messagOtherBody'}">
													<c:choose >
														<c:when test="${list.textMessage != ''}">
															<p><c:out value="${list.textMessage}" /></p>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${list.stickerSrc != ''}">
																	<img src="${list.stickerSrc}" style="height: 80px; width: 80px;">
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when test="${list.fileName != ''}">
																			<img src="${list.fileSrc}" style="max-height: 500px; max-width: 500px; cursor: pointer;">
																		</c:when>
																		<c:otherwise>
																			<img src="${list.fileSrc}" style="height: 60px; width: 60px; cursor: pointer;">
																			<span _path=<c:out value="${list.filePath}"/>> 
																				<c:out value="${list.fileName}"/>
																			</span>
																		</c:otherwise>
																	</c:choose>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>									
												</div>
											</div>									
										</div>
										
										
										<c:if test="${list.clusterId > currCluster}">
											<c:set var="currCluster" value="${list.clusterId}"/>
										</c:if>								
									</c:forEach>
								</c:when>
								<c:otherwise>
									<div style="min-height: 500px;">No data</div>
								</c:otherwise>
							</c:choose>					
				    	</div>
				    	
				    	<div class="bnkChatTool" id="bnkChatDiv">
				    		<div class="bnkCmtInput">
				    			<textarea cols="20" rows="1" id="" oninput="auto_grow(this)" style="height: 50px; outline: none; border: none; resize: none; overflow: hidden; font-size: 12px; line-height: 15px; width: 100%; margin: 0px; padding: 16px 10px;"></textarea>
				    		</div>
				    		<div class="bnkCmtTool">
				    			<div id="emoticonPanel" style="display: block; width:400px; height:356.5px; margin-top: -357px;margin-left: -319px; background-color: #fff; border:1px solid #3399ff;; position: absolute;">
									<div id="emoticonGroup" style="display:block;width:100%; height: 45px;background-color: #fff; border-bottom:1px solid #3399ff;;">
										<div style="float:left; display:block; height: 45px;">
											<img id="previousEmoticon" src="/images/previous1.png" height=40 width=30 style="padding-top: 3px; ">
										</div>
										<div id="_ePresentors" style="float:left; display:block; ">
											<div id="_group1" style="background-color: #d9d9d9; float:left; display: block; height:45px; width:45px; cursor: pointer; " onclick="changeStickerGroup(this);"><img src="/images/emoticon/hackerGirl.png" height=30 width=30 style="padding-top: 7px; padding-left: 7px; "></div>
											<div id="_group2" style="float:left; display: block; height:45px; width:45px; cursor: pointer;" onclick="changeStickerGroup(this);"><img src="/images/emoticon/crayonShin.png" height=30 width=30 style="padding-top: 7px; padding-left: 7px; "></div>
											<div id="_group3" style="float:left; display: block; height:45px; width:45px; cursor: pointer;" onclick="changeStickerGroup(this);"><img src="/images/emoticon/catEmoticon.png" height=30 width=30 style="padding-top: 7px; padding-left: 7px; "></div>
											<div id="_group4" style="float:left; display: block; height:45px; width:45px; cursor: pointer;" onclick="changeStickerGroup(this);"><img src="/images/emoticon/student.png" height=30 width=30 style="padding-top: 7px; padding-left: 7px; "></div>
											<!--<div id="_group5" style="float:left; display: block; height:45px; width:45px; cursor: pointer; " onclick="changeStickerGroup(this);"><img src="/images/emoticon/hackerGirl.png" height=30 width=30 style="padding-top: 7px; padding-left: 7px; "></div>
											<div id="_group6" style="float:left; display: block; height:45px; width:45px; cursor: pointer;" onclick="changeStickerGroup(this);"><img src="/images/emoticon/crayonShin.png" height=30 width=30 style="padding-top: 7px; padding-left: 7px; "></div>
											<div id="_group7" style="float:left; display: block; height:45px; width:45px; cursor: pointer;" onclick="changeStickerGroup(this);"><img src="/images/emoticon/catEmoticon.png" height=30 width=30 style="padding-top: 7px; padding-left: 7px; "></div>
											<div id="_group8" style="float:left; display: block; height:45px; width:45px; cursor: pointer;" onclick="changeStickerGroup(this);"><img src="/images/emoticon/student.png" height=30 width=30 style="padding-top: 7px; padding-left: 7px; "></div>
									  		 <div id="_group9" style="float:left; display: block; height:45px; width:45px; cursor: pointer; " onclick="changeStickerGroup(this);"><img src="/images/emoticon/hackerGirl.png" height=30 width=30 style="padding-top: 7px; padding-left: 7px; "></div>
											<div id="_group10" style="float:left; display: block; height:45px; width:45px; cursor: pointer;" onclick="changeStickerGroup(this);"><img src="/images/emoticon/crayonShin.png" height=30 width=30 style="padding-top: 7px; padding-left: 7px; "></div>  -->
										</div>
										<div style="float: right; display:block; height: 45px;">
											<img id="nextEmoticon" src="/images/next1.png" height=40 width=30 style="padding-top: 3px; ">
										</div>
									</div>	
				    				<div id="emoticonList" style="display:inline-block;width:100%; background-color: #fff;">
										<div id="_listG1" style="height:310px; overflow-y: auto; overflow-x: hidden; display: block;">
											<table id="_listG1Table">
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/45.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/65.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/75.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/85.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/95.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/105.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/118.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/119.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/125.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/135.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/145.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/155.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/165.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/172.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/182.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/192.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/202.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/215.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/216.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/222.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/232.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/242.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/252.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/262.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/272.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/282.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/292.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/302.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/314.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/315.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/322.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/332.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/341.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/351.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/361.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/371.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/381.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/391.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/401.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/girl/431.png);" onclick="displaySticker(this);"></div></td>
												</tr>
											</table>
										</div>
										<div id="_listG2" style="height:310px; overflow-y: auto; overflow-x: hidden; display: none;">
											<table id="_listG2Table">
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/2.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/3.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/4.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/5.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/6.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/7.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/8.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/9.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/10.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/11.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/12.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/13.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/14.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/15.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/16.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/17.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/18.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/19.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/20.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/21.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/22.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/23.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/24.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/25.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/26.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/27.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/28.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/29.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/30.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/31.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/32.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/shin/33.png);" onclick="displaySticker(this);"></div></td>
												</tr>
											</table>
										</div>
										<div id="_listG3" style="height:310px; overflow-y: auto; overflow-x: hidden; display: none;">
											<table id="_listG3Table">
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/1.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/2.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/3.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/4.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/5.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/6.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/7.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/8.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/9.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/10.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/11.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/12.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/13.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/14.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/15.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/16.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/17.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/18.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/19.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/20.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/21.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/22.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/23.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/cat/24.png);" onclick="displaySticker(this);"></div></td>
												</tr>
											</table>
										</div>
										<div id="_listG4" style="height:310px; overflow-y: auto; overflow-x: hidden; display: none;">
											<table id="_listG4Table">
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/1.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/2.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/3.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/4.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/5.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/6.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/7.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/8.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/9.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/10.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/11.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/12.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/13.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/14.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/15.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/16.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/17.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/18.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/19.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/20.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/21.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/22.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/23.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/24.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/25.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/26.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/27.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/28.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/29.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/30.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/31.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/32.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/33.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/34.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/35.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/36.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/37.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/38.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/39.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/40.png);" onclick="displaySticker(this);"></div></td>
												</tr>
												<tr style="width:100%; height:45px;">
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/41.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/42.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/43.png);" onclick="displaySticker(this);"></div></td>
													<td><div class="emoticon" style="background-image: url(/images/emoticon/boy/44.png);" onclick="displaySticker(this);"></div></td>
												</tr>
											</table>
										</div>
									</div>
								</div>
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