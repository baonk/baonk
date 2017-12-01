package com.nv.baonk.vo;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "user")
@IdClass(User.UserVOPK.class)
public class User {
	@Id
	@Column(name = "userid", length = 100)
	@NotEmpty(message = "*Please provide a user ID")
	private String userid;
	
	@Column(name = "email")
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
	private String email;
	
	@Column(name = "name")
	@NotEmpty(message = "*Please provide your name")
	private String username;
	
	@Column(name = "password")
	@Length(min = 5, message = "*Password must have at least 5 characters")	
	private String password;
	
	@Column(name = "active")
	private int active;	
	
	@Column(name = "other_position")
	private String otherpos;
	
	@Column(name="phone_number")
	private String phone;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "birthday")
	@NotEmpty(message = "*Please provide user birthday")
	private String birthday;
	
	@Column(name = "lastlogin")
	private String lastlogin;
	
	@Id
	@Column(name = "companyid", length = 100)
	private String companyid;
	
	@Column(name = "companyname")
	private String companyname;
	
	@Id
	@Column(name = "tenantid")
	private int tenantid;
	
	@Column(name = "departmentid")	
	private String departmentid;
	
	@Column(name = "departmentname")
	private String departmentname;
	
	@Column(name = "position")
	private String position;
	
	@Column(name="hobby")
	private String hobby;
	
	@Column(name="homephone", length = 50)
	private String homephone;
	
	@Column(name="fax", length = 50)
	private String fax;
	
	@Column(name="homeaddress", length = 250)
	private String homeaddress;
	
	@Column(name="nickname", length = 50)
	private String nickname;
	
	@Column(name="postcode", length = 50)
	private String postcode;
	
	@Column(name="sex", length = 20)
	private String sex;
	
	@Column(name="country", length = 100)
	private String country;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "company_id"), @JoinColumn(name = "tenant_id"), @JoinColumn(name = "user_id")}, inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;	
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getHomephone() {
		return homephone;
	}

	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getHomeaddress() {
		return homeaddress;
	}

	public void setHomeaddress(String homeaddress) {
		this.homeaddress = homeaddress;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}		
	
	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}	

	public int getTenantid() {
		return tenantid;
	}

	public void setTenantid(int tenantid) {
		this.tenantid = tenantid;
	}

	public String getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}

	public String getOtherpos() {
		return otherpos;
	}

	public void setOtherpos(String otherpos) {
		this.otherpos = otherpos;
	}	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public static class UserVOPK implements Serializable {
		private static final long serialVersionUID = -8274004534207618049L;
		
		private String userid;
		private String companyid;
		private int tenantid;
		
		public UserVOPK(String userID, String companyID, int tenantID) {
			this.userid = userID;
			this.companyid = companyID;
			this.tenantid = tenantID;
		}
		
		public UserVOPK() {
			
		}
		
		public boolean equals(Object object) {
			if(object instanceof UserVOPK) {
				UserVOPK obj = (UserVOPK) object;
				return userid.equals(obj.userid) && companyid.equals(obj.companyid) && tenantid == obj.tenantid;
			}
			else {
				return false;
			}
		}
		
		public int hashCode() {
			return userid.hashCode() + companyid.hashCode() + tenantid;
		}

		public long getSerialversionuid() {
			return serialVersionUID;
		}
		
	}
}
