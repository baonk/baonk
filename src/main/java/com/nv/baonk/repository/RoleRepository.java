package com.nv.baonk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.nv.baonk.vo.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	public static final String getRoleId = "SELECT ROLE_ID FROM USER_ROLE WHERE USER_ID = ?1 AND TENANT_ID = ?2";
	
	@Query(value = getRoleId, nativeQuery = true)
	public List<Integer> getRoleId(String userID, int tenantID); 
	
	Role findByRoleid(int roleId);	
	Role findByRolename(String roleName);
}
