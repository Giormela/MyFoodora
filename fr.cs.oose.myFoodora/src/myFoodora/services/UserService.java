package myFoodora.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import myFoodora.MyFoodora;
import myFoodora.entities.user.*;
import myFoodora.exceptions.ElementNotFoundException;
import myFoodora.exceptions.UserRegistrationException;

/**
 * UserService is an abstract class that provides a blueprint for managing User objects.
 * It is used to register, remove, and retrieve User objects.
 *
 * @param <U> the type of User managed by the service
 */
abstract class UserService <U extends User> {
	protected Map<Integer, U> users;

	/**
	 * Constructs a new UserService.
	 */
	UserService() {
		super();
		this.users = new HashMap<Integer, U>();		
	}
	
	public Collection<U> getList(){
		return users.values();
	}
	
	public U getUserById(Integer userId) {
		return users.get(userId);
	}
	
	public U getUserByName(String name) throws ElementNotFoundException{
		return users.values().stream()
				.filter(u->u.getName().equals(name))
				.findFirst().orElseThrow(()->new ElementNotFoundException("No user has a name such as "+name));
	}

	/**
	 * Registers a new user.
	 *
	 * This method checks if the user is already registered, registers the user, and registers the user's credential.
	 *
	 * @param newUser the user to register
	 * @throws UserRegistrationException if the user is already registered
	 */
	public void registerUser(U newUser) throws UserRegistrationException{
		checkUserRegistration(newUser);
		
		users.put(newUser.getId(), newUser);
		MyFoodora.getInstance().credentialService.registerCredential(newUser.getCredential());
	}

	/**
	 * Checks if a user is already registered.
	 *
	 * @param newUser the user to check
	 * @throws UserRegistrationException if the user is already registered
	 */
	private void checkUserRegistration(U newUser) throws UserRegistrationException{
		if (users.containsKey(newUser.getId()))
			throw new UserRegistrationException("User already registered");
		
		MyFoodora.getInstance().credentialService.checkCredentialRegistration(newUser.getCredential());		
	}
	
	public void removeUser(Integer userId) {
		U removedUser = users.remove(userId);
		if (removedUser != null) 
			MyFoodora.getInstance().credentialService.removeCredential(removedUser.getCredential().getUsername());
	}
	
//	public Optional<U> tryLogin(String username, String password) {
//		return MyFoodora.getInstance().credentialService.tryLogin(username, password).map(id->users.get(id));
//	}
}
