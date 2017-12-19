package com.nv.baonk.service;

import java.util.List;
import com.nv.baonk.vo.Department;
import com.nv.baonk.vo.SimpleDepartment;

public interface DepartmentService {
	public List<Department> getAllDepartmentsOfCompany(String companyId, int tenantID);
	public SimpleDepartment getSimpleDeptList(String deptID, int tenantID);	
	public List<SimpleDepartment> getAllSimpleSubDepts(String parentID, int tenantID);	
	public Department findByDepartmentidAndTenantid(String deptID, int tenantID);	
}
