package com.nv.baonk.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nv.baonk.vo.Department;


@Repository("departRepository")
public interface DepartmentRepository extends JpaRepository<Department, Long> {		
	List<Department> findByParentdeptAndTenantid(String parentDept, int tenantId);	
	Department findByDepartmentidAndTenantid(String deptId, int tenantId);
}
