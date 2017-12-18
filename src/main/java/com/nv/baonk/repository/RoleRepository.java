package com.nv.baonk.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.nv.baonk.vo.Role;

@Transactional
public interface RoleRepository extends JpaRepository<Role, Integer> {
	public static final String getRoleId      = "SELECT ROLE_ID FROM USER_ROLE WHERE USER_ID = ?1 AND TENANT_ID = ?2";
	public static final String deleteUserRole = "DELETE FROM USER_ROLE WHERE USER_ID = ?1 AND TENANT_ID = ?2";
	
	@Query(value = getRoleId, nativeQuery = true)
	public List<Integer> getRoleId(String userID, int tenantID); 
	
	@Modifying
	@Query(value = deleteUserRole, nativeQuery = true)
	public void deleteUserRole(String userID, int tenantID);
	
	Role findByRoleid(int roleId);	
	Role findByRolename(String roleName);
}
