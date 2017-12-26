package com.nv.baonk.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.nv.baonk.vo.Department;

@Repository("departRepository")
public interface DepartmentRepository extends JpaRepository<Department, Long> {
	List<Department> findByParentdeptAndTenantid(String parentDept, int tenantId);	
	Department findByDepartmentidAndTenantid(String deptId, int tenantId);
	List<Department> findByCompanyIdAndTenantid(String companyId, int tenantId);
	
	@Query(value="SELECT * FROM DEPARTMENT WHERE PARENT_DEPARTMENT = ?1 AND TENANT_ID = ?2 AND DEPARTMENT_NAME LIKE %?3%", nativeQuery = true)
	List<Department> searchByDeptName(String dept, int tenantId, String deptName);
	
	@Query(value="SELECT * FROM DEPARTMENT WHERE PARENT_DEPARTMENT = ?1 AND TENANT_ID = ?2 AND DEPARTMENT_ID LIKE %?3%", nativeQuery = true)
	List<Department> searchByDeptID(String dept, int tenantId, String deptID);
	
	@Query(value="SELECT * FROM DEPARTMENT WHERE PARENT_DEPARTMENT = ?1 AND TENANT_ID = ?2 AND EMAIL LIKE %?3%", nativeQuery = true)
	List<Department> searchByDeptEmail(String dept, int tenantId, String email);
	
	@Query(value="SELECT * FROM DEPARTMENT WHERE PARENT_DEPARTMENT = ?1 AND TENANT_ID = ?2 AND COMPANY_ID LIKE %?3%", nativeQuery = true)
	List<Department> searchByCompanyID(String dept, int tenantId, String companyID);
	
	@Query(value="SELECT * FROM DEPARTMENT WHERE PARENT_DEPARTMENT = ?1 AND TENANT_ID = ?2 AND COMPANY_NAME LIKE %?3%", nativeQuery = true)
	List<Department> searchByCompanyName(String dept, int tenantId, String companyName);
	
	@Query(value="SELECT * FROM DEPARTMENT WHERE PARENT_DEPARTMENT = 'self' AND TENANT_ID = ?1 AND DEPARTMENT_NAME LIKE %?2%", nativeQuery = true)
	List<Department> searchByCompanyName2(int tenantId, String deptName);
	
	@Query(value="SELECT * FROM DEPARTMENT WHERE PARENT_DEPARTMENT = 'self' AND TENANT_ID = ?1 AND DEPARTMENT_ID LIKE %?2%", nativeQuery = true)
	List<Department> searchByCompanyID2(int tenantId, String deptID);
	
	@Query(value="SELECT * FROM DEPARTMENT WHERE PARENT_DEPARTMENT = 'self' AND TENANT_ID = ?1 AND EMAIL LIKE %?2%", nativeQuery = true)
	List<Department> searchByCompanyEmail(int tenantId, String email);
	
	@Query(value="SELECT * FROM DEPARTMENT WHERE DEPARTMENT_NAME LIKE %?1% AND TENANT_ID = ?2", nativeQuery = true)
	List<Department> getDeptsBySearchingDeptName(String deptName, int tenantId);
}
