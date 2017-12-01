package com.nv.baonk.vo;

import java.util.List;
import javax.persistence.NamedQuery;


@NamedQuery (
	    name="Test.SimpleDepartment", 
	    query="SELECT new SimpleDepartment (d.DEPARTMENT_ID, d.DEPARTMENT_NAME)"
	    		+ "FROM DEPARTMENT d WHERE d.DEPARTMENT_ID = ?1 AND d.TENANT_ID = ?2"	    
)

public class SimpleDepartment {	
	private String departmentid;
	private String departmentname;
	private String companyid;
	private String companyname;
	private List<SimpleDepartment> subDept;
	private int hasSubDept;
	
	public SimpleDepartment(String departId, String deptName, String companyId, String companyName) {
		this.departmentid = departId;
		this.departmentname = deptName;
		this.companyid = companyId;
		this.companyname = companyName;
	}
	
	public String getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public List<SimpleDepartment> getSubDept() {
		return subDept;
	}
	public void setSubDept(List<SimpleDepartment> subDept) {
		this.subDept = subDept;
	}
	public int getHasSubDept() {
		return hasSubDept;
	}
	public void setHasSubDept(int hasSubDept) {
		this.hasSubDept = hasSubDept;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}	
}
