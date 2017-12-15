package com.nv.baonk.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nv.baonk.common.CommonUtil;
import com.nv.baonk.service.DepartmentService;
import com.nv.baonk.service.UserService;
import com.nv.baonk.vo.Department;
import com.nv.baonk.vo.FailObject;
import com.nv.baonk.vo.Role;
import com.nv.baonk.vo.SimpleDepartment;
import com.nv.baonk.vo.ValidateResponseObject;
import com.nv.baonk.vo.User;

@Controller
public class AdminOrganController {	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private DepartmentService deptService;
	
	@Autowired
	private UserService userService;
	
	private final Logger logger = LoggerFactory.getLogger(AdminOrganController.class);
	
	/*******************************************************************************************************************************
	 ****	 
	 **** 	Map the organization management right board request of administrator privilege users.
	 ****		 
	********************************************************************************************************************************/
	
	@RequestMapping(value="/admin/organ/organRight", method = RequestMethod.GET)
	public String mainMenu(@CookieValue("loginCookie")String loginCookie, Model model, HttpServletRequest request) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		int checkAdmin = 0;
		User user = commonUtil.getUserInfo(loginCookie);
		Set<Role> roleList = user.getRoles();
		int tenantId = user.getTenantid();
		String userDeptId = user.getDepartmentid();
		String userCompId = user.getCompanyid();
		Department dept = deptService.findByDepartmentidAndTenantid(userDeptId, tenantId);
		String[] deptPath = dept.getDepartmentpath().split("::");
		
		//Check user role
		for (Role role: roleList) {
			if (role.getRoleid() == 1) {
				checkAdmin = 1;
				break;
			}
		}
		
		if (checkAdmin == 0) {
			return "access-denied";
		}
		
		//Get all company		
		List<SimpleDepartment> simpleCompanyList = deptService.getAllSimpleSubDepts("self", tenantId);	
		
		for (SimpleDepartment company: simpleCompanyList) {
			if (company.getCompanyid().equals(userCompId)) {
				logger.debug("CompanyID: " + company.getCompanyid() + " || deptPath: " + deptPath);
				getAllSubDepts2(company, tenantId, deptPath, 1);
			}
			else {
				getAllSubDepts(company, tenantId, 1);
			}
		}				
		
		model.addAttribute("listDepartment", om.writeValueAsString(simpleCompanyList));
		model.addAttribute("userdeptID", userDeptId);
		model.addAttribute("usercompID", userCompId);
		
		return "/admin/organ/organRight";
	}	
	
	/*******************************************************************************************************************************
	 ****	 
	 **** 	Get all the sub department of a department when user clicks to this department name
	 ****		 
	********************************************************************************************************************************/
	
	@RequestMapping(value="/admin/organ/getSimpleSubDept", method = RequestMethod.POST)
	@ResponseBody
	public String getSimpleSubDept(@CookieValue("loginCookie")String loginCookie, HttpServletRequest request) throws JsonProcessingException {
		logger.debug("======================getSimpleSubDept start======================");
		ObjectMapper om = new ObjectMapper();
		User user = commonUtil.getUserInfo(loginCookie);
		int tenantId = user.getTenantid();
		String deptID = request.getParameter("deptID");
		
		SimpleDepartment dept = deptService.getSimpleDeptList(deptID, tenantId);
		getAllSubDepts(dept, tenantId, 1);	
		
		logger.debug("CHECK: " + om.writeValueAsString(dept));
		logger.debug("======================getSimpleSubDept end======================");
		return om.writeValueAsString(dept);	 		
	}	
	
	/*******************************************************************************************************************************
	 ****	 
	 **** 	Get all the detail information of clicked department
	 ****		 
	********************************************************************************************************************************/
	
	@RequestMapping(value="/admin/organ/getDetailInfo", method = RequestMethod.POST)
	@ResponseBody
	public String getDetailInfo(@CookieValue("loginCookie")String loginCookie, HttpServletRequest request) throws JsonProcessingException {
		logger.debug("======================getDetailInfo start======================");
		ObjectMapper om = new ObjectMapper();
		User user = commonUtil.getUserInfo(loginCookie);
		int tenantId = user.getTenantid();
		String deptID = request.getParameter("deptID");
		String mode = request.getParameter("optionVal");
		FailObject fail = new FailObject();
		
		logger.debug("Check mode: " + mode + " || DeptID: " + deptID);
		
		if (mode.equals("muser")) {
			List<User> listUser = userService.findUsersInAdminMode(deptID, tenantId);
			if (listUser.isEmpty()) {
				logger.debug("======================getDetailInfo end======================");
				return om.writeValueAsString(fail);
			}			
			logger.debug("======================getDetailInfo end======================");			
			return om.writeValueAsString(listUser);
		}
		else if (mode.equals("mdept")) {
			List<SimpleDepartment> listSimpleDept = deptService.getAllSimpleSubDepts(deptID, tenantId);
			if (listSimpleDept.isEmpty()) {
				logger.debug("======================getDetailInfo end======================");
				return om.writeValueAsString(fail);
			}			
			logger.debug("======================getDetailInfo end======================");			
			return om.writeValueAsString(listSimpleDept);
		}	
		else {
			Department dept = deptService.findByDepartmentidAndTenantid(deptID, tenantId);
			if (dept.getParentdept().equals("self")) {
				SimpleDepartment company = deptService.getSimpleDeptList(deptID, tenantId);
				logger.debug("======================getDetailInfo end======================");				
				return om.writeValueAsString(company);
			}
			else {				
				logger.debug("======================getDetailInfo end======================");
				return om.writeValueAsString(fail);
			}
		}
	}	
	
	/*******************************************************************************************************************************
	 ****	Mode: 0 + Get all sub department recursively.
	 ****		  1	+ Get only sub department of current department
	 ****			  then stop.
	********************************************************************************************************************************/
	
	public void getAllSubDepts(SimpleDepartment dept, int tenantId, int mode) {
		List<SimpleDepartment> listSubSimpleDepts = deptService.getAllSimpleSubDepts(dept.getDepartmentid(), tenantId);
		
		if (listSubSimpleDepts.size() > 0) {			
			dept.setSubDept(listSubSimpleDepts);
			dept.setHasSubDept(1);
			
			for (SimpleDepartment subdept: listSubSimpleDepts) {
				if (mode == 0) {
					getAllSubDepts(subdept, tenantId, mode);
				}
				else {
					List<SimpleDepartment> subSimpleDepts = deptService.getAllSimpleSubDepts(subdept.getDepartmentid(), tenantId);
					if (subSimpleDepts.size() > 0) {
						subdept.setHasSubDept(1);
					}
					else {
						subdept.setHasSubDept(0);
					}
				}		
			}
		}
		else {
			dept.setHasSubDept(0);			
		}
		
	}
	
	/*******************************************************************************************************************************
	 ****	Mode: 0 + Get all sub department recursively.
	 ****		  1	+ Get only sub department of current department
	 ****			  then stop.
	********************************************************************************************************************************/
	
	public void getAllSubDepts2(SimpleDepartment dept, int tenantId, String[] deptPath, int order) {
		List<SimpleDepartment> listSubSimpleDepts = deptService.getAllSimpleSubDepts(dept.getDepartmentid(), tenantId);
		
		if (listSubSimpleDepts.size() > 0) {			
			dept.setSubDept(listSubSimpleDepts);
			dept.setHasSubDept(1);
			
			for (SimpleDepartment subdept: listSubSimpleDepts) {				
				List<SimpleDepartment> subSimpleDepts = deptService.getAllSimpleSubDepts(subdept.getDepartmentid(), tenantId);
				if (subSimpleDepts.size() > 0) {
					subdept.setHasSubDept(1);	
					
					if (order < deptPath.length && subdept.getDepartmentid().equals(deptPath[order])) {						
						getAllSubDepts2(subdept, tenantId, deptPath, order + 1);
					}
				}
				else {
					subdept.setHasSubDept(0);
				}						
			}
		}
		else {
			dept.setHasSubDept(0);			
		}
		
	}
	
	/*******************************************************************************************************************************
	 ****	 
	 **** 	Map the organization management request of administrator privilege users.
	 ****		 
	********************************************************************************************************************************/
	
	@RequestMapping(value="/admin/organization", method = RequestMethod.GET)
	public String adminOrganization(Model model, HttpServletRequest request){		
		return "admin/organ/organMainBoard";
	}	
	
	/*******************************************************************************************************************************
	 ****	 
	 **** 	Map the organization management left board request of administrator privilege users.
	 ****		 
	********************************************************************************************************************************/

	@RequestMapping(value="/admin/organLeft", method = RequestMethod.GET)
	public String adminOrganLeft(Model model, HttpServletRequest request){
		logger.debug("admin organization left board is running!");
		return "admin/organ/organLeft";
	}
	
	/*******************************************************************************************************************************
	 ****	 
	 **** 	Map the add user request of administrator privilege users.
	 ****		 
	********************************************************************************************************************************/
	
	@RequestMapping(value="/admin/userRegistration", method = RequestMethod.GET)
	public String registration(@CookieValue("loginCookie")String loginCookie, HttpServletRequest request, Model model){	
		logger.debug("----------------------Add user is running-----------------------!");
		User loginUser	= commonUtil.getUserInfo(loginCookie);
		int tenantId    = loginUser.getTenantid();
		String deptId   = request.getParameter("deptId")   != null ? request.getParameter("deptId")   : "";
		String deptName = request.getParameter("deptName") != null ? request.getParameter("deptName") : "";
		String userId   = request.getParameter("userId")   != null ? request.getParameter("userId")   : "";
		
		logger.debug("BAONK DEPTID: " + deptId);
		
		if (!deptId.equals("")) {
			logger.debug("Haizz");
			User user = new User();
			model.addAttribute("user", user);
			model.addAttribute("deptID", deptId);
			model.addAttribute("deptName", deptName);
			model.addAttribute("mode", "add");
		}
		else {
			User vUser = userService.findUserByUseridAndTenantid(userId, tenantId);
			model.addAttribute("user", vUser);
			model.addAttribute("mode", "view");
		}
		
		logger.debug("-----------------------Add user end-----------------------------!");
		return "admin/organ/addUser";
	}	
	
	/*******************************************************************************************************************************
	 ****	 
	 **** 	Map the move user request of administrator privilege users.
	 ****   	 
	********************************************************************************************************************************/
	
	@RequestMapping(value="/admin/moveUser", method = RequestMethod.GET)
	public String moveUser(@CookieValue("loginCookie")String loginCookie, HttpServletRequest request, Model model) throws JsonProcessingException{	
		logger.debug("----------------------Move user is running-----------------------!");
		ObjectMapper om = new ObjectMapper();
		User loginUser	= commonUtil.getUserInfo(loginCookie);		
		int tenantId = loginUser.getTenantid();
		String userId   = request.getParameter("userId")   != null ? request.getParameter("userId")   : "";
		
		User clickedUser = userService.findUserByUseridAndTenantid(userId, tenantId);		
		SimpleDepartment dept = deptService.getSimpleDeptList(clickedUser.getCompanyid(), tenantId);
		
		getAllSubDepts(dept, tenantId, 1);
		model.addAttribute("listDepartment", om.writeValueAsString(dept));
		model.addAttribute("usercompID", clickedUser.getCompanyid());
		model.addAttribute("userID", userId);		
		
		logger.debug("-----------------------Move user end-----------------------------!");
		return "admin/organ/moveUser";
	}	
	
	/*******************************************************************************************************************************
	 ****	 
	 **** 	Map the save moved user request of administrator privilege users.
	 ****   	 
	********************************************************************************************************************************/
	
	@RequestMapping(value="/admin/saveMovedUser", method = RequestMethod.POST)
	public String saveMovedUser(@CookieValue("loginCookie")String loginCookie, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException{	
		logger.debug("----------------------Save moved user is running-----------------------!");	
		User loginUser	= commonUtil.getUserInfo(loginCookie);		
		int tenantId = loginUser.getTenantid();
		String userId      = request.getParameter("userId")   	!= null ? request.getParameter("userId")      : "";
		String newDeptId   = request.getParameter("newDeptId")  != null ? request.getParameter("newDeptId")   : "";
		
		User movedUser = userService.findUserByUseridAndTenantid(userId, tenantId);		
		Department newDept = deptService.findByDepartmentidAndTenantid(newDeptId, tenantId);
		movedUser.setDepartmentid(newDept.getDepartmentid());
		movedUser.setDepartmentname(newDept.getDepartmentname());		
		userService.updateUser(movedUser);
		
		logger.debug("-----------------------Save moved User end-----------------------------!");
		return "admin/organ/moveUser";
	}	
	
	/*******************************************************************************************************************************
	 ****	 
	 **** 	Map the delete user request of administrator privilege users.
	 ****   	 
	********************************************************************************************************************************/
	
	@RequestMapping(value="/admin/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public String deleteUser(@CookieValue("loginCookie")String loginCookie, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException{	
		logger.debug("----------------------Delete user is running-----------------------!");	
		User loginUser	= commonUtil.getUserInfo(loginCookie);		
		int tenantId 	= loginUser.getTenantid();
		String userId   = request.getParameter("userId") != null ? request.getParameter("userId") : "";		
		
		User deleteUser = userService.findUserByUseridAndTenantid(userId, tenantId);		
		try {
			userService.deleteUser(deleteUser);
			logger.debug("-----------------------Delete user end-----------------------------!");
			return "OK";
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		
		logger.debug("-----------------------Delete user end-----------------------------!");	
		return "FAIL";
	}
	
	/*******************************************************************************************************************************
	 ****	 
	 **** 	Receive new user information from user input form then save new user to database	 
	 ****		 
	********************************************************************************************************************************/
	
	@RequestMapping(value = "/admin/addUser", method = RequestMethod.POST)
	@ResponseBody
	public ValidateResponseObject createNewUser(HttpServletRequest request, @CookieValue("loginCookie")String loginCookie, @Valid User user, BindingResult bindingResult, Model model) throws JsonProcessingException {
		logger.debug("-------------------createNewUser is running---------------------!");
		
		User currentUser = commonUtil.getUserInfo(loginCookie);
		int tenantId = currentUser.getTenantid();		
		ValidateResponseObject response = new ValidateResponseObject();
		
		User userExists = userService.findUserByUseridAndTenantid(user.getUserid(), tenantId);
		
		if (userExists != null) {		
			logger.debug("User Id existed!");
			bindingResult.rejectValue("userid", "error.user", "There is already a user registered with the userID provided");
		}
		
		if (bindingResult.hasErrors()) {		
			logger.debug("Binding result has Error!");
			
			//Get error message
	         Map<String, String> errors = bindingResult.getFieldErrors().stream().collect(
	        		 						  Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
	                 					  );
	         
	         response.setResult(0);
	         response.setErrorMessages(errors);			
		}
		else {
			String deptID = user.getDepartmentid();
			Department dept = deptService.findByDepartmentidAndTenantid(deptID, tenantId);
			Department company = deptService.findByDepartmentidAndTenantid(dept.getCompanyId(), tenantId);
			user.setTenantid(tenantId);
			user.setCompanyid(company.getCompanyId());
			user.setCompanyname(company.getCompanyName());					
			
			logger.debug("++++++++++++++++++Check User Infor++++++++++++++++++!");
			logger.debug("-----------------	User ID				: " + user.getUserid());
			logger.debug("-----------------	User Name			: " + user.getUsername());
			logger.debug("-----------------	User Department Name: " + user.getDepartmentname());
			logger.debug("----------------- User Department ID  : " + user.getDepartmentid());
			logger.debug("----------------- User Company ID		: " + user.getCompanyid());
			logger.debug("----------------- User Company Name   : " + user.getCompanyname());
			logger.debug("----------------- User Tenant ID	    : " + user.getCompanyname());
			logger.debug("++++++++++++++++++User Infor End++++++++++++++++++++!");
			
			userService.saveUser(user);
			response.setResult(1);					
		}	
		
		return response;
	}
	
	/*******************************************************************************************************************************
	 ****	 
	 **** 	Receive update user information from user input form then save updated user information to database	 
	 ****		 
	********************************************************************************************************************************/
	
	@RequestMapping(value = "/admin/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public ValidateResponseObject updateUser(HttpServletRequest request, @CookieValue("loginCookie")String loginCookie, @Valid User user, BindingResult bindingResult, Model model) throws JsonProcessingException {
		logger.debug("-------------------createNewUser is running---------------------!");
		
		User currentUser = commonUtil.getUserInfo(loginCookie);
		int tenantId = currentUser.getTenantid();		
		ValidateResponseObject response = new ValidateResponseObject();
		
		User userExists = userService.findUserByUseridAndTenantid(user.getUserid(), tenantId);
		
		if (userExists == null) {		
			logger.debug("Error: user not found!");
			bindingResult.rejectValue("userid", "error.user", "User information not found in database");
		}
		
		if (bindingResult.hasErrors()) {		
			logger.debug("Binding result has Error!");
			
			//Get error message
	         Map<String, String> errors = bindingResult.getFieldErrors().stream().collect(
	        		 						  Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
	                 					  );
	         
	         response.setResult(0);
	         response.setErrorMessages(errors);			
		}
		else {				
			user.setTenantid(tenantId);
			user.setCompanyid(userExists.getCompanyid());
			user.setCompanyname(userExists.getCompanyname());
			user.setRoles(userExists.getRoles());
			
			logger.debug("++++++++++++++++++Check User Infor++++++++++++++++++!");
			logger.debug("-----------------	User ID				: " + user.getUserid());
			logger.debug("-----------------	User Name			: " + user.getUsername());
			logger.debug("-----------------	User Department Name: " + user.getDepartmentname());
			logger.debug("----------------- User Department ID  : " + user.getDepartmentid());
			logger.debug("----------------- User Company ID		: " + user.getCompanyid());
			logger.debug("----------------- User Company Name   : " + user.getCompanyname());
			logger.debug("----------------- User Tenant ID	    : " + user.getCompanyname());
			logger.debug("++++++++++++++++++User Infor End++++++++++++++++++++!");
			
			userService.updateUser(user);
			response.setResult(1);					
		}	
		
		return response;
	}
	
	/*******************************************************************************************************************************
	 ****	 
	 **** 	Map the add user image request of administrator privilege users.
	 ****		 
	********************************************************************************************************************************/
	
	@RequestMapping(value="/admin/addUserImage", method = RequestMethod.GET)
	public String addImage(HttpServletRequest request, Model model){	
		logger.debug("----------------------Add user image is running-----------------------!");		
		logger.debug("-----------------------Add user image end-----------------------------!");
		return "admin/organ/employeePicture";
	}		
	
	@RequestMapping(value = "/admin/organ/signImageUpload", method = RequestMethod.POST)
	@ResponseBody
	public String signImageUpload(HttpServletRequest req, Model model, MultipartHttpServletRequest request) throws Exception{
		logger.debug("----------------------signImageUpload is running-----------------------!");
		
		String result 				  	= "";					
		List<MultipartFile> multiFile 	= request.getFiles("fileToUpload"); 			
		String realPath 			  	= request.getServletContext().getRealPath("");
		String pFileName			  	= "";                      
        String sGUID 				  	= UUID.randomUUID().toString();        
        String pUploadSN 			  	= sGUID;
        
        if (multiFile.size() == 0) {
        	 result = "{\"data\":\"Fail\"}";
        	 return result;
        }

        if (!multiFile.get(0).getOriginalFilename().isEmpty() && !multiFile.get(0).getOriginalFilename().equals("")) {        	
        	pFileName = multiFile.get(0).getOriginalFilename();
        }          
        
        String pDirPath = "/file/";
        pDirPath 		= realPath + pDirPath;
        logger.debug("pDirPath: " + pDirPath);
        
        if (!pDirPath.substring(pDirPath.length() - 1).equals("/")) {
        	pDirPath = pDirPath + "/";
        }
        
        File file = new File(pDirPath + "uploadFile");

        if (!file.exists()) {
        	file.mkdir();        
        }
        
        String extend 	   = pFileName.substring(pFileName.lastIndexOf(".") + 1);
        String newFileName = pUploadSN + "." + extend;         
        String finalPath = "/file/uploadFile/" + newFileName;

        commonUtil.writeUploadedFile(multiFile.get(0), newFileName, pDirPath + "uploadFile");          
        
        result = "{\"data\":\"" + finalPath + "\",\"fname\":\"" + pFileName + "\"}";        
		logger.debug("-----------------------signImageUpload end-----------------------------!");
		
		return result;
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
}
