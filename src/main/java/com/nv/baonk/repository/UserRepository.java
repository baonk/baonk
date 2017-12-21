package com.nv.baonk.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nv.baonk.vo.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {	
	public static final String getTenantId 				  = "SELECT TENANT_ID FROM SERVER WHERE SERVER_NAME = ?1";
	public static final String getListUsersByDeptAndOther = "SELECT * FROM USER WHERE (DEPARTMENTID = ?1 OR OTHER_POSITION = ?1) AND TENANTID = ?2";
	
	
	@Query(value = getTenantId, nativeQuery = true)
	int getTenantId(String serverName);		
	
	User findByUseridAndTenantid(String userId, int tenantId);
	
	@Query(value = getListUsersByDeptAndOther, nativeQuery = true)
	List<User> findUsersInAdminMode(String deptId, int tenantId);
	
	List<User> findByCompanyidAndTenantid(String companyId, int tenantId);
	
	List<User> findByDepartmentidAndTenantid(String companyId, int tenantId);

	@Query(value="SELECT * FROM USER WHERE DEPARTMENTID = ?1 AND TENANTID = ?2 AND NAME LIKE %?3%", nativeQuery = true)
	List<User> searchByName(String deptId, int tenantId, String strName);	
	
	@Query(value="SELECT * FROM USER WHERE DEPARTMENTID = ?1 AND TENANTID = ?2 AND USERID LIKE %?3%", nativeQuery = true)
	List<User> searchByUserID(String deptId, int tenantId, String userID);	
	
	@Query(value="SELECT * FROM USER WHERE DEPARTMENTID = ?1 AND TENANTID = ?2 AND POSITION LIKE %?3%", nativeQuery = true)
	List<User> searchByPosition(String deptId, int tenantId, String position);	
	
	@Query(value="SELECT * FROM USER WHERE DEPARTMENTID = ?1 AND TENANTID = ?2 AND EMAIL LIKE %?3%", nativeQuery = true)
	List<User> searchByEmail(String deptId, int tenantId, String email);	
	
	@Query(value="SELECT * FROM USER WHERE DEPARTMENTID = ?1 AND TENANTID = ?2 AND PHONE_NUMBER LIKE %?3%", nativeQuery = true)
	List<User> searchByPhonenumber(String deptId, int tenantId, String phone);	
	
	@Query(value="SELECT * FROM USER WHERE DEPARTMENTID = ?1 AND TENANTID = ?2 AND HOMEPHONE LIKE %?3%", nativeQuery = true)
	List<User> searchByHomephone(String deptId, int tenantId, String homephone);
	
	@Query(value="SELECT * FROM USER WHERE DEPARTMENTID = ?1 AND TENANTID = ?2 AND NICKNAME LIKE %?3%", nativeQuery = true)
	List<User> searchByNickname(String deptId, int tenantId, String nickname);	
	
	@Query(value="SELECT * FROM USER WHERE DEPARTMENTID = ?1 AND TENANTID = ?2 AND COUNTRY LIKE %?3%", nativeQuery = true)
	List<User> searchByCountry(String deptId, int tenantId, String country);	
	
	@Query(value="SELECT * FROM USER WHERE DEPARTMENTID = ?1 AND TENANTID = ?2 AND HOMEADDRESS LIKE %?3%", nativeQuery = true)
	List<User> searchByHomeAddress(String deptId, int tenantId, String homeaddress);	
}
