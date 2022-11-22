package com.hbx.ads.domain;


import java.util.List;


/**
 *
 * 用户身份信息，存入session 由于tomcat将session会序列化在本地硬盘上，
 * 所以使用Serializable接口
 *
 */
public class ActiveUser implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String userid;//用户id（主键）
	private String username;// 用户名称
	private String role;
	private String userStatus;// 用户状态
	private String realName;
	private List<SysPermission> permissions;// 权限

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<SysPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<SysPermission> permissions) {
		this.permissions = permissions;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "ActiveUser{" +
				"userid='" + userid + '\'' +
				", username='" + username + '\'' +
				", role='" + role + '\'' +
				", userStatus='" + userStatus + '\'' +
				", realName='" + realName + '\'' +
				", permissions=" + permissions +
				'}';
	}
}
