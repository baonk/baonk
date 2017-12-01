package com.nv.baonk.vo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="department")
@IdClass(Department.DepartmentVOPK.class)
public class Department {
	@Id
	@Column(name = "department_id", length = 100)
	@NotEmpty
	private String departmentid;
	
	@Id
	@Column(name = "tenant_id")
	@NotEmpty
	private int tenantid;
	
	@Column(name = "department_name")
	@NotEmpty
	private String departmentname;
	
	@Column(name = "department_path")
	@NotEmpty
	private String departmentpath;
	
	@Column(name = "parent_department")	
	private String parentdept;
	
	@Column(name = "email")
	private String email;	
	
	@Transient
	private List<Department> subDept;
	
	@Column(name = "company_name")
	private String companyName;	
	
	@Column(name = "company_id")
	private String companyId;	
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}

	public int getTenantid() {
		return tenantid;
	}

	public void setTenantid(int tenantid) {
		this.tenantid = tenantid;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public String getDepartmentpath() {
		return departmentpath;
	}

	public void setDepartmentpath(String departmentpath) {
		this.departmentpath = departmentpath;
	}

	public String getParentdept() {
		return parentdept;
	}

	public void setParentdept(String parentdept) {
		this.parentdept = parentdept;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	

	public List<Department> getSubDept() {
		return subDept;
	}

	public void setSubDept(List<Department> subDept) {
		this.subDept = subDept;
	}

	public static class DepartmentVOPK implements Serializable {
		private static final long serialVersionUID = -8450045342076180478L;
		
		private String departmentid;		
		private int tenantid;
		
		public DepartmentVOPK(String departmentid, int tenantID) {
			this.departmentid = departmentid;			
			this.tenantid = tenantID;
		}
		
		public DepartmentVOPK() {
			
		}
		
		public boolean equals(Object object) {
			if(object instanceof DepartmentVOPK) {
				DepartmentVOPK obj = (DepartmentVOPK) object;
				return departmentid.equals(obj.departmentid) && tenantid == obj.tenantid;
			}
			else {
				return false;
			}
		}
		
		public int hashCode() {
			return departmentid.hashCode() + tenantid;
		}

		public long getSerialversionuid() {
			return serialVersionUID;
		}
		
	}
}
