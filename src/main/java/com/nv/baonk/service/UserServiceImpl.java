package com.nv.baonk.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nv.baonk.repository.RoleRepository;
import com.nv.baonk.repository.UserRepository;
import com.nv.baonk.vo.Role;
import com.nv.baonk.vo.User;


@Service
public class UserServiceImpl implements UserService{
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
}
