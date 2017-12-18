package com.nv.baonk.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nv.baonk.common.CommonUtil;
import com.nv.baonk.repository.UserRepository;
import com.nv.baonk.service.UserService;
import com.nv.baonk.vo.Role;
import com.nv.baonk.vo.User;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private CommonUtil commonUtil;
	
	private final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request, HttpSession session){		
		String error    = "";
		String username = "";		
		
		if (session != null) {
			SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
			
	        if (savedRequest != null) {
	           String redirectUrl = savedRequest.getRedirectUrl();
	           session.setAttribute("prior_url", redirectUrl);	           
	        }
		}
		
		if (request.getParameter("username") != null) {
			username = request.getParameter("username");
		}		
		
		if (request.getParameter("test") != null) {
			error = request.getParameter("test");	
		}
	
		model.addAttribute("error", error);		
		model.addAttribute("username", username);	
		return "login";		
	}
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public String home(@CookieValue("loginCookie") String loginCookie, Model model, HttpServletRequest request) {		
		User userInfo = commonUtil.getUserInfo(loginCookie);
		logger.debug("UserID: " + userInfo.getUserid() + " || Tenant ID: " + userInfo.getTenantid() + " || User password: " + userInfo.getPassword());
		
		return "home";
	}	
	
	@RequestMapping(value="/mainMenu", method = RequestMethod.GET)
	public String mainMenu(Model model){
		return "mainMenu";
	}			
	
	@RequestMapping(value="/topMenu", method = RequestMethod.GET)
	public String topMenu(Model model, HttpServletRequest request){	
		//Get tenant Id from serverName
		String serverName   = request.getServerName();
		int tenantId 	    = userRepository.getTenantId(serverName);		
		int isAdmin 	    = 0;		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user 			= userService.findUserByUseridAndTenantid(auth.getName(), tenantId);
		
		for (Role role: user.getRoles()) {
			if(role.getRolename().equals("ADMIN")) {
				isAdmin = 1;
				break;
			}			
		}
		
		if (isAdmin == 0) {
			logger.debug("Normal user!");
			model.addAttribute("role", "USER");
		}
		else {
			logger.debug("Administrator!");
			model.addAttribute("role", "ADMIN");
		}		
		
		model.addAttribute("userName", user.getUserid());
		model.addAttribute("email", user.getEmail());		
		return "topMenu";
	}	
	
	@RequestMapping(value="/uploadMenu", method = RequestMethod.GET)
	public String uploadMenu(Model model){	
		return "uploadMenu";
	}		
	
	@RequestMapping(value = "/uploadFile", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String uploadFile(MultipartHttpServletRequest request) throws Exception {		
		logger.debug("Upload file is running!");				
		List<MultipartFile> multiFile = request.getFiles("fileToUpload"); 
		int cnt = multiFile.size();
		
		String realPath    	  = request.getServletContext().getRealPath("");
		String[] pFileName 	  = new String[cnt];
        Long[] fileSize 	  = new Long[cnt];        
        String[] resultUpload = new String[cnt];
        String[] sGUID 		  = new String[cnt];
        String[] pUploadSN    = new String[cnt];      

        for (int i = 0; i < cnt; i++) {
            resultUpload[i] = "false";
            sGUID[i]        = UUID.randomUUID().toString();
            pUploadSN[i]    = sGUID[i];
        }

        if (!multiFile.get(0).getOriginalFilename().isEmpty() && !multiFile.get(0).getOriginalFilename().equals("")) {        	
            for (int i = 0; i < cnt; i++) {
                String _pFileName = multiFile.get(i).getOriginalFilename();
                
                if (_pFileName.indexOf("/") > 0) {
                    _pFileName = _pFileName.split("/")[_pFileName.split("/").length - 1];
                }
                
                pFileName[i] = _pFileName;
            }
        }

        /*for (int i = 0; i < cnt; i++) {
            pFileName[i] = pFileName[i].replace("+", "%2b");
            pFileName[i] = pFileName[i].replace(";", "%3b");
        }    */       
        
        String pDirPath = "/file/";
        pDirPath        = realPath + pDirPath;
        
        logger.debug("pDirPath: " + pDirPath);
        
        if (!pDirPath.substring(pDirPath.length() - 1).equals("/")) {
        	pDirPath = pDirPath + "/";
        }
        
        File file = new File(pDirPath + "uploadFile");

        if (!file.exists()) {
        	file.mkdir();        
        }

        StringBuffer strXML = new StringBuffer();
        strXML.append("<ROOT><NODES>");
        
        for (int i = 0; i < cnt; i++) {        	
        	fileSize[i]        = multiFile.get(i).getSize();
            String extend      = pFileName[i].substring(pFileName[i].lastIndexOf(".") + 1);
            String newFileName = pUploadSN[i] + "." + extend;
            
			writeUploadedFile(multiFile.get(i), newFileName, pDirPath + "uploadFile");
			strXML.append("<DATA><![CDATA[" + newFileName + "/" + pFileName[i] + "/" + fileSize[i] + "]]></DATA>");
			strXML.append("<DATA2><![CDATA[]]></DATA2>");
			strXML.append("<DATA3><![CDATA[OK]]></DATA3>");
            
        }
        strXML.append("</NODES></ROOT>");

        logger.debug("Upload file finishes!");        
        return strXML.toString();
    }
	
	@RequestMapping(value="/deleteFile", method = RequestMethod.POST, produces="text/xml; charset=utf-8")
	@ResponseBody
	public String deleteFile(String loginCookie, HttpServletRequest req) throws Exception {
		logger.debug("Delete file is running!");		
		String fileName = "";
		String strXML   = "";
		
		if (req.getParameter("fileToDelete") != null) {
			fileName = req.getParameter("fileToDelete").split("/")[0];
		}		
		
		String realPath = req.getServletContext().getRealPath("");
		String pDirPath = "/file/";
		pDirPath 		= realPath + pDirPath;
		
		if (!pDirPath.substring(pDirPath.length() - 1).equals("/")) {
        	pDirPath = pDirPath + "/";
        }
		String absoluteFilePath = pDirPath + "uploadFile/" + fileName;
		
		try {
			File file = new File(absoluteFilePath);
			
			if (!file.exists()) {
				logger.debug("Wrong folder path!");
				return "<DATA>DELETE_FAIL</DATA>";
			}
			
			file.delete();	
			strXML = "<DATA>DELETE_OK</DATA>";
		}
		catch (Exception e) {
			strXML = "<DATA>DELETE_FAIL</DATA>";
	        e.printStackTrace();
	    }
		
		logger.debug("Delete file finishes!");
		return strXML;
	}	
	
	@RequestMapping(value={"/downloadAttach"}, method = RequestMethod.GET)	
	public void downloadAttachFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("Download attach is running!");
		
		String folderPath = request.getParameter("folderPath");
		String fileName   = request.getParameter("filename");
		File file 		  = null;
		logger.debug("FolderPath:" + folderPath + ", fileName:" + fileName);
		
		if (folderPath == null || fileName == null || folderPath.equals("") || fileName.equals("")) {			
			logger.debug("downloadAttach illegal arguments!");
			return;
		}
		
        //Get absolute path of the application       
        String realPath = request.getServletContext().getRealPath("");
        String pDirPath = "/file/";
        pDirPath        = realPath + pDirPath;
        
        if (!pDirPath.substring(pDirPath.length() - 1).equals("/")) {
        	pDirPath = pDirPath + "/";
        }
       
        String fullPath = pDirPath + "uploadFile" + "/" + folderPath;    
        file 			= new File(fullPath);
        
		if (file == null || !file.exists()) {			
			logger.debug("Folder not found. folderPath=" + folderPath);
			return;			
		}
		
		FileInputStream inputStream = null;
		OutputStream outStream = null;
		
		try {
			inputStream = new FileInputStream(file);
	        
	        //Get mime type of the file
	        String mimeType = request.getServletContext().getMimeType(fullPath);
	        
	        if (mimeType == null) {
	            //Set to binary type if mime mapping not found
	            mimeType = "application/octet-stream";
	        }	        
	        logger.debug("MIME type: " + mimeType);	 
	        	
	        fileName = URLEncoder.encode(fileName, "UTF-8");
	        fileName = fileName.replace("+", " ");	        
	        String browserType = request.getHeader("User-Agent");
	        
	        //Set content attributes and header for the response
	        response.setContentType(mimeType);
	        response.setContentLength((int) file.length());
	        response.setCharacterEncoding("UTF-8");       	    	        
	                             
	        if (browserType.contains("Firefox")) {	
	        	fileName = fileName.replace(" ", "%20");	
	            response.setHeader("Content-Disposition","attachment; filename*=UTF-8''" + fileName );
	        }  
	        else {
	        	response.setHeader("Content-Disposition","attachment; filename=" + "'" + fileName + "'");
	        }

	        //Get output stream of the response
	        outStream = response.getOutputStream();
	 
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	 
	        //Write bytes read from the input stream into the output stream
	        while ((bytesRead = inputStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	 
	        inputStream.close();
	        outStream.close();
		}
		catch (IOException e) {
			
		}
		finally {
			if (inputStream != null) {
				try { inputStream.close(); } catch (IOException e1) {}
			}
			if (outStream != null) {
				try { outStream.flush(); } catch (IOException e1) {}
				try { outStream.close(); } catch (IOException e1) {}
			}
		}
		logger.debug("Download attach finishes!");	
	}
	
	private void writeUploadedFile(MultipartFile file, String newName, String stordFilePath) throws Exception {
		InputStream stream 		 = null;
		OutputStream bos 		 = null;
		String stordFilePathReal = (stordFilePath == null ? "" : stordFilePath);
		int BUFF_SIZE			 = 4096;
		
		try {
		    stream = file.getInputStream();
		    File cFile = new File(stordFilePathReal);
	
		    if (!cFile.isDirectory()) {
				boolean _flag = cFile.mkdirs();
				if (!_flag) {
				    throw new IOException("Directory creation Failed ");
				}
		    }
	
		    bos = new FileOutputStream(stordFilePathReal + File.separator + newName);
	
		    int bytesRead = 0;
		    byte[] buffer = new byte[BUFF_SIZE];
	
		    while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
		    	bos.write(buffer, 0, bytesRead);
		    }
		} 
		catch (FileNotFoundException fnfe) {
			logger.debug("fnfe: {}", fnfe);
		} 
		catch (IOException ioe) {
			logger.debug("ioe: {}", ioe);
		} 
		catch (Exception e) {
			logger.debug("e: {}", e);
		} 
		finally {
		    if (bos != null) {
				try {
				    bos.close();
				} catch (Exception ignore) {
					logger.debug("IGNORED: {}", ignore.getMessage());
				}
		    }
		    if (stream != null) {
				try {
				    stream.close();
				} catch (Exception ignore) {
					logger.debug("IGNORED: {}", ignore.getMessage());
				}
		    }
		}
    }

}
