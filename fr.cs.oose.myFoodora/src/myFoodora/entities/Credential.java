package myFoodora.entities;

import myFoodora.enums.PermissionType;

public class Credential {
	private String username;
	private String password;
	private Integer userId;
	private PermissionType permission;
	
	public Credential(String username, String password, Integer userId,  PermissionType permission) {
		super();
		this.username = username;
		this.password = password;
		this.userId = userId;
		this.permission = permission;
	}
	
	public boolean checkCorrespondance(String username, String password) {
		return (this.username.equals(username) && this.password.equals(password));
	}

	public Integer getUserId() {
		return userId;
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

	public PermissionType getPermission() {
		return permission;
	}

	public void setPermission(PermissionType permission) {
		this.permission = permission;
	}
}
