package com.nv.baonk.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "server")
public class Server {
	@Column(name = "server_name")
	private String servername;
	
	@Id
	@Column(name = "tenant_id")
	private int tenantid;

	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}

	public int getTenantid() {
		return tenantid;
	}

	public void setTenantid(int tenantid) {
		this.tenantid = tenantid;
	}	
	
}
