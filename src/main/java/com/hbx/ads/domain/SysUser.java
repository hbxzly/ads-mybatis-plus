package com.hbx.ads.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.Size;

@TableName("sys_user")
public class  SysUser {

	@TableId(type = IdType.AUTO)
	@TableField("id")
	@Size(max=36, message="用户编号过长！")
	private String id;

	@TableField("username")
	@Size(max=64, message="用户名过长！")
    private String username;

	@TableField("password")
	@Size(max=32, message="密码过长！")
	private String password;

	@TableField("real_name")
	private String realName;

	@TableField("role")
	@Size(max=128, message="角色名过长！")
    private String role;

	@TableField("locked")
	@Size(max=1, message="用户状态输入非法！")
    private String locked;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}



//	@Override
//	public String toString() {
//		return "SysUser{" +
//				"id='" + id + '\'' +
//				", username='" + username + '\'' +
//				", password='" + password + '\'' +
//				", locked='" + locked + '\'' +
//				", roleName='" + roleName + '\'' +
//				", roleId='" + roleId + '\'' +
//				'}';
//	}
}
