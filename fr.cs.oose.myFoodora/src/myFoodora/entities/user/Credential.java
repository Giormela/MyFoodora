package myFoodora.entities.user;

public class Credential {
	private String username;
	private String password;
	private UserType permission;
	
	Credential(String username, String password, UserType permission) {
		super();
		this.username = username;
		this.password = password;
		this.permission = permission;
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
