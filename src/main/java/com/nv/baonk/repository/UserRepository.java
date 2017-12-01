package com.nv.baonk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nv.baonk.vo.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	public static final String getTenantId = "SELECT TENANT_ID FROM SERVER WHERE SERVER_NAME = ?1";
	public static final String getListUsersByDeptAndOther = "SELECT * FROM USER WHERE (DEPARTMENTID = ?1 OR OTHER_POSITION = ?1) AND TENANTID = ?2";
	
	@Query(value = getTenantId, nativeQuery = true)
	public int getTenantId(String serverName);		
	
	User findByUseridAndTenantid(String userId, int tenantId);
	
	@Query(value = getListUsersByDeptAndOther, nativeQuery = true)
	List<User> findUsersInAdminMode(String deptId, int tenantId);
}
