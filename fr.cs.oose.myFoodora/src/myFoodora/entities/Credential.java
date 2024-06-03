package myFoodora.entities;

import myFoodora.entities.user.User;
import myFoodora.enums.PermissionType;

public class Credential {
	private String username;
	private String password;
	private User user;
	private PermissionType permission;
	
	public Credential(String username, String password, User user,  PermissionType permission) {
		super();
		this.username = username;
		this.password = password;
		this.user = user;
		this.permission = permission;
	}
	
	public boolean checkCorrespondance(String username, String password) {
		return (this.username.equals(username) && this.password.equals(password));
	}

	public User getUser() {
		return user;
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
