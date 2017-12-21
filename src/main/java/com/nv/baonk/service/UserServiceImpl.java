package com.nv.baonk.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.nv.baonk.repository.RoleRepository;
import com.nv.baonk.repository.UserRepository;
import com.nv.baonk.vo.Role;
import com.nv.baonk.vo.User;


@Service
public class UserServiceImpl implements UserService{
	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPass;
	
	@Override
	public User findUserByUseridAndTenantid(String userId, int tenantId) {
		return userRepository.findByUseridAndTenantid(userId, tenantId);
	}

	@Override
	public void saveUser(User user) {		
		user.setPassword(bCryptPass.encode(user.getPassword()));
		user.setActive(1);		
		
        Role userRole = roleRepository.findByRolename("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	@Override
	public int getTenantId(String serverName) {		
		return userRepository.getTenantId(serverName);
	}
	
	@Override
	public List<Integer> getRoleId(String userID, int tenantID) {		
		return roleRepository.getRoleId(userID, tenantID);
	}

	@Override
	public Role findByRoleid(int roleId) {		
		return roleRepository.findByRoleid(roleId);
	}

	@Override
	public Role findByRolename(String roleName) {		
		return roleRepository.findByRolename(roleName);
	}

	@Override
	public List<User> findUsersInAdminMode(String deptID, int tenantId) {		
		return userRepository.findUsersInAdminMode(deptID, tenantId);
	}

	@Override
	public void updateUser(User user) {		
		userRepository.save(user);
	}

	@Override
	public void deleteUser(User user) {	
		roleRepository.deleteUserRole(user.getUserid(), user.getTenantid());
		userRepository.delete(user);
	}

	@Override
	public List<User> findAllCompanyEmployees(String companyId, int tenantId) {		
		return userRepository.findByCompanyidAndTenantid(companyId, tenantId);
	}

	@Override
	public List<User> getAllUsersOfDepartment(String deptID, int tenantId) {		
		return userRepository.findByDepartmentidAndTenantid(deptID, tenantId);
	}

	@Override
	public List<User> findUsersWithSearchOption(String deptID, String sStr, String field, int tenantId) {	
		List<User> list= new ArrayList<>();
		logger.debug("TEST field: " + field);
		
		switch (field) {
			case "name":			
				logger.debug("1");
				list = userRepository.searchByName(deptID, tenantId, sStr);
				break;
			case "userid":
				logger.debug("2");
				list = userRepository.searchByUserID(deptID, tenantId, sStr);
				break;
			case "position":
				logger.debug("3");
				list = userRepository.searchByPosition(deptID, tenantId, sStr);
				break;
			case "email":
				logger.debug("4");
				list = userRepository.searchByEmail(deptID, tenantId, sStr);
				break;
			case "phone_number":
				logger.debug("5");
				list = userRepository.searchByPhonenumber(deptID, tenantId, sStr);
				break;
			case "homephone":
				logger.debug("6");
				list = userRepository.searchByHomephone(deptID, tenantId, sStr);
				break;
			case "nickname":
				logger.debug("7");
				list = userRepository.searchByNickname(deptID, tenantId, sStr);
				break;
			case "country":
				logger.debug("8");
				list = userRepository.searchByCountry(deptID, tenantId, sStr);
				break;
			case "homeaddress":
				logger.debug("9");
				list = userRepository.searchByHomeAddress(deptID, tenantId, sStr);
				break;
			default:
				list = null;
				break;
		}
		return list;
	}
}
