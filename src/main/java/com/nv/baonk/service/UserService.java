package com.nv.baonk.service;

import java.util.List;

import com.nv.baonk.vo.Role;
import com.nv.baonk.vo.User;

public interface UserService {
	public User findUserByUseridAndTenantid(String userId, int tenantId);
	public void saveUser(User user);
	public int getTenantId(String serverName);
	public List<Integer> getRoleId(String userID, int tenantID); 
	public Role findByRoleid(int roleId);	
	public Role findByRolename(String roleName);
	public List<User> findUsersInAdminMode(String deptID, int tenantId);
}
