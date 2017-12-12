<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html style="width:100%;">
	<head>
		<title>Employee Image Upload</title>			
		<link rel="stylesheet" type="text/css" href="/css/popup.css" />		
		<script type="text/javascript">
		    var returnFunction;
		    var retValue;
	    	var imageFile = "";
	    	
			window.onload = function() {
				try {
		            retValue 		= parent.personpicture_cross_dialogArguments[0];
		            returnFunction  = parent.personpicture_cross_dialogArguments[1];
		        }catch(e){
		            try {
		            	retValue		= opener.personpicture_cross_dialogArguments[0];
		            	returnFunction  = opener.personpicture_cross_dialogArguments[1];
		            } catch (e) {
		            	retValue = window.dialogArguments;
		            }
		        }
			}
			
			function close_Click() {			    
			    parent.divPopUpHidden();
			}
			
			function img_search() {				
	            if (document.getElementById("file1").value != "") {  
	            	var fileName = document.getElementById("file1").value;
	            	var extFile  = fileName.split(".");
	            	
            	    if (extFile && extFile.length > 1) {
            	    	extFile = extFile[extFile.length - 1].toLowerCase();
            	    	
    	                if (extFile == "jpg" || extFile == "jpeg" || extFile == "png" || extFile == "gif" || extFile == "bmp") {
    			            var fd = new FormData();	                     
    			            fd.append("fileToUpload", document.getElementById("file1").files[0]);
    			            
    			            xhr = new XMLHttpRequest();
    			            xhr.addEventListener("load", uploadComplete, false);
    			            imageName = document.getElementById("file1").files[0].name;
    			            xhr.open("POST", "/admin/organ/signImageUpload?userID=" + retValue);
    			            xhr.send(fd);
    	                }
    	                else{
    	                    alert("Only jpg/jpeg and png files are allowed!");
    	                }  
            	    }
            	    else {
            	    	alert("File format error!");
            	    }
	            }
	        }
			
			function image_onclick() {
				document.getElementById("file1").click();
			}
			
			function uploadComplete() {									
				var result    = JSON.parse(xhr.responseText).data;	
				var fileName  = JSON.parse(xhr.responseText).fname;
				
 		        if (result == "Fail") {
		        	alert("Upload Image failed!");		        	
		        	document.getElementById("file1").value = "";		        	
		        }
		        else {		        	
		        	document.getElementById("preview").src 		= result;
		        	document.getElementById("imagefile").value  = fileName;
		        	imageFile 								    = result;
		        }	         		       
		    }
			
			function save_Click() {				
				if (!imageFile || !document.getElementById("imagefile").value) {
					alert("No image is uploaded!");
					return;
				}
				else {
					alert("Image is uploaded!");
					if (returnFunction != null){
		                returnFunction(imageFile);
					}
					else {
		                window.returnValue = imageFile;
					}
				}					
			}
			
			
			/* function btnimagefile_onclick(ocx_file){
			    try {
			    	if(CrossYN()){
			        document.form.file1.click();
			    	} else {
			    		var ezUtil = new ActiveXObject("EzUtil.MiscFunc.1");
						var imgName = "";
						if(!ocx_file)
		                {
		                    imgName = ezUtil.OpenLoadDlg("Image Files\0*.jpg;*.gif;*.bmp;*.jpe;*.png;*.emf;*.wmf;*.jpeg;*.jfif;*.dib;*.rle;*.bmz;*.gfa;*.emz;*.pcx;\0All Files (*.*)\0*.*\0\0", "");
		                    if (!imgName)
		                        return;
			            }
			            else
			            {
			                imgName = ocx_file.split("|");
			            }
						var ezUtil = null;
						var fileNamelist = "";
						var fileName = "";
						 document.all.EzHTTPTrans.AddUploadFile("","");
		                
						try {
							 document.all.EzHTTPTrans.AddUploadFile(imgName, "N");                   
						}
						catch (e) 
						{
							alert(imgName + "Fail" + "\n\n" + e.number + " - " + e.description);
							return;
						}	
			            var RemotePath =document.location.protocol + "//" + document.location.hostname + ":" + location.port + "/admin/ezOrgan/signImageUploadIe9.do?mode=PICTURE&userID=" + RetValue;
			            var nCount = document.all.EzHTTPTrans.StartUpload(RemotePath,"",RetValue,"","");
			            if (nCount == 0)
			            {
			                alert(imgName + "Fail");
			                return false;
			            }
			            for (var i = 0; i < nCount; i++) {
				            var fileinfo = EzHTTPTrans.GetReturn(i);
					        var infos = fileinfo.split('/')	;  
					        var pfileName = infos[0].substr(infos[0].lastIndexOf("\\")+1);
					        var fileName = infos[1];
			            }
			            fileupafter(fileName.substr(3,infos[1].length));
			    	}
				}catch(e){
					alert(e.discription);
				}
			}
			
			function fileupafter(value) {
	    		imagefile.value = value;
			}
			
			function imgtemp_onclick() {
	            if (document.form.file1.value != "") {
	            	if (document.getElementById("form").file1.files.length > 1) {
			            alert("Nothing");
			        }
	            	
		            var fd = new FormData();		            
		            fd.append("file1", document.getElementById("form").file1.files[0]);
		          
		            xhr = new XMLHttpRequest();
		            xhr.addEventListener("load", uploadComplete, false);
		            imageName = document.getElementById("form").file1.files[0].name;
		            xhr.open("POST", "/admin/ezOrgan/signImageUpload.do?mode=PICTURE&userID=" + RetValue);
		            xhr.send(fd);
	            }
	        }
			
			function uploadComplete() {		        
		        if(xhr.responseText == "UPLOAD_ERROR"){
		        	alert("Nothing");
		        	
		        	document.getElementById("file1").value = "";
		        	document.getElementById("tempFilePath").value = "";
		        }else{
		        	document.getElementById("tempFilePath").value = xhr.responseText;
		        	document.getElementById("imagefile").value = imageName;
		        }
		        //returnvalue(xhr.responseText);
		    }
			
			function imgConfirm_onclick(obj) {
				if(CrossYN()){
					if (document.getElementById("form").file1.files.length == 0) {
			            alert("Nothing");
			            return;
			        }
					var fileName = document.getElementById("tempFilePath").value;
				} else {
					var fileName = document.getElementById("imagefile").value;
				}
				
				
				$.ajax({
					type : "POST",
					dataType : "text",
					url : "/admin/ezOrgan/saveUserInfo.do",
					data : {parentCn : "", cn : RetValue, prop : "", extensionAttribute2 : fileName},
					success : function(result){
						if(result != "OK"){
							alert("Nothing");
						}else{
							if (ReturnFunction != null){
				                ReturnFunction(fileName);
							}else{
				                window.returnValue = fileName;
							}
				            window.close();
						}
					},
					error : function(){
						alert("Nothing");
					}
				});
			}		
			
			function divImageFile_onclick() {
				if(CrossYN()){
				    if (document.form.file1.value != "") {
				        preview.src = "";
						preview.style.visibility = "hidden";
						preview.src = "/admin/ezOrgan/getPersonalInfo.do?fileName=" + document.getElementById("tempFilePath").value;
						preview.style.visibility = "visible";
					}
				} else {
					if(imagefile.value != "")
					{
						preview.src = "";
						preview.style.visibility = "hidden";
						preview.src = "/admin/ezOrgan/getPersonalInfo.do?fileName=" + document.getElementById("imagefile").value;
						preview.style.visibility = "visible";
					}
				}
			} */
	    </script>
	</head>
	<body>
		<div class="popup">
			<h1>User Image Upload</h1>	
		</div>	
		<table class="content"> 
			<tr>
		    	<th width="125" height="135">
		    		<img id="preview" name="preview" src="" width="119" height="128" alt="" border="0" style="visibility: visible; margin-top: 3px;">
		    	</th>
		    	<td>		    	
			  		<div style="text-align: center;">Image preview <br>Size: 119*128</div>
			  	</td>
		  	</tr>
		</table>
		<table class="content"> 
			<tr style="height: 45px;">
		    	<th style="width: 80px;">Image Name</th>
		    	<td>		    
		    		<input id=imagefile name=imagefile class = "inputImage" readonly="readonly" />
		    		<iframe name="ifrm" src="about:blank" style="display: none"></iframe>
		    		<form method="post" id="form" name="form" enctype="multipart/form-data" target="ifrm" >
		  				<input type="file" name="file1" id="file1" style="width: 0px; height: 0px; float:left;" onchange="img_search()" accept="image/*" />
		    			<input type="hidden" name="mode" id="mode" />		    			
		    		</form>
					<div class="baonBttn" style="width: 25px;"><span id="btnimagefile" onClick="image_onclick()" >Search Image</span></div>
				</td>
		  	</tr>
		</table>
		<div style="margin: 6px 0px 6px 145px;">
		    <a class="baonBttn2"><span onClick="save_Click();">Save</span></a>
		    <a class="baonBttn2"><span onClick="close_Click()">Cancel</span></a>
		</div>		
	</body>
</html>