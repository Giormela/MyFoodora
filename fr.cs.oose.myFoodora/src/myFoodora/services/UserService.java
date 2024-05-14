package myFoodora.services;

import java.util.HashMap;
import java.util.Map;

import myFoodora.entities.user.User;

public class UserService {
	private Map<Integer, User> users;

	UserService() {
		super();
		this.users = new HashMap<Integer, User>();
	}
	
	public void registerUser(User newUser) {
		if (!this.users.containsKey(newUser.getId())) {
			this.users.put(newUser.getId(), newUser);
		}
	}
	
	public void removeUser(Integer userId) {
		this.users.remove(userId);
	}
	
}
