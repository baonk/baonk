package com.nv.baonk.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nv.baonk.repository.DepartmentRepository;
import com.nv.baonk.vo.Department;
import com.nv.baonk.vo.SimpleDepartment;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	private DepartmentRepository departRepository;
	
    @PersistenceContext
    EntityManager em;
	
	@Override
	public List<Department> getAllCompanies(String parentDept, int tenantID) {		
		return departRepository.findByParentdeptAndTenantid(parentDept, tenantID);
	}

	@Override
	public SimpleDepartment getSimpleDeptList(String deptID, int tenantID) {		
		String sql = "SELECT new com.nv.baonk.vo.SimpleDepartment (d.departmentid, d.departmentname, d.companyId, d.companyName)"
	    		   + "FROM com.nv.baonk.vo.Department d WHERE d.departmentid = :departId AND d.tenantid = :tenantId";
		
		Query query = em.createQuery(sql).setParameter("departId", deptID).setParameter("tenantId", tenantID);		
		
		SimpleDepartment result = (SimpleDepartment) query.getSingleResult();
		return result;
	}

	@Override
	public List<SimpleDepartment> getAllSimpleSubDepts(String parentID, int tenantID) {
		String sql = "SELECT new com.nv.baonk.vo.SimpleDepartment (d.departmentid, d.departmentname, d.companyId, d.companyName)"
	    		   + "FROM com.nv.baonk.vo.Department d WHERE d.parentdept = :parentDept AND d.tenantid = :tenantId";
		
		Query query = em.createQuery(sql).setParameter("parentDept", parentID).setParameter("tenantId", tenantID);		
		
		@SuppressWarnings("unchecked")
		List<SimpleDepartment> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public Department findByDepartmentidAndTenantid(String deptID, int tenantID) {		
		return departRepository.findByDepartmentidAndTenantid(deptID, tenantID);
	}

}
