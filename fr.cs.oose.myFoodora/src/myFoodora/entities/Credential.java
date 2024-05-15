package myFoodora.entities;

import myFoodora.enums.UserType;

public class Credential {
	private String username;
	private String password;
	private Integer userId;
	private UserType permission;
	
	public Credential(String username, String password, Integer userID,  UserType permission) {
		super();
		this.username = username;
		this.password = password;
		this.permission = permission;
	}
	
	public boolean checkCorrespondance(String username, String password) {
		return (this.username == username && this.password == password);
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

	public UserType getPermission() {
		return permission;
	}

	public void setPermission(UserType permission) {
		this.permission = permission;
	}
	
	
	
	
}
