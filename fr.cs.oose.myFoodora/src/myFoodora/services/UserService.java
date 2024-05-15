package myFoodora.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import myFoodora.entities.Credential;
import myFoodora.entities.FidelityCard;
import myFoodora.entities.user.*;
import myFoodora.exceptions.UserRegistrationException;

public class UserService {
	private Map<Integer, User> users;
	private CredentialService credentialService;

	public UserService() {
		super();
		this.users = new HashMap<Integer, User>();
		this.credentialService = new CredentialService();
	}
	
	public User getUserById(Integer userId) {
		if (users.containsKey(userId))
			return users.get(userId);
		return null;
	}
	
	public void registerUser(User newUser) throws UserRegistrationException{
		checkUserRegistration(newUser);
		
		this.users.put(newUser.getId(), newUser);
		this.credentialService.registerCredential(newUser.getCredential());
	}
	
	private void checkUserRegistration(User user) throws UserRegistrationException{
		if (this.users.containsKey(user.getId()))
			throw new UserRegistrationException("User already registered");
		
		this.credentialService.checkCredentialRegistration(user.getCredential());		
	}
	
	public void removeUser(Integer userId) {
		User removedUser = this.users.remove(userId);
		if (removedUser != null) 
			this.credentialService.removeCredential(removedUser.getCredential().getUsername());
	}
	
	public void registerFidelityCard(Customer customer, Restaurant restaurant) {
		FidelityCard newFidelityCard = new FidelityCard(customer, restaurant);
		customer.addFidelityCard(newFidelityCard);
		restaurant.addFidelityCard(newFidelityCard);
	}
	
	public void removeFidelityCard(Customer customer, Restaurant restaurant) {
		customer.removeFidelityCard(restaurant);
		restaurant.removeFidelityCard(customer);
	}
	
	private Optional<User> tryLogin(String username, String password) {
		return this.credentialService.tryLogin(username, password).map(id->this.users.get(id));
	}
	
	private class CredentialService {
		private Map<String, Credential> credentials;
		
		
		
		private CredentialService() {
			super();
			this.credentials = new HashMap<String, Credential>();
		}

		private void registerCredential(Credential credential) {
			this.credentials.put(credential.getUsername(), credential);
		}
		
		private void removeCredential(String username) {
			this.credentials.remove(username);
		}
		
		private void checkCredentialRegistration(Credential credential) throws UserRegistrationException {
			if(this.credentials.containsKey(credential.getUsername()))
				throw new UserRegistrationException("Username already used");
		}
		
		private Optional<Integer> tryLogin(String username, String password) {
			if (this.credentials.containsKey(username) && this.credentials.get(username).checkCorrespondance(username, password))
				return Optional.of(this.credentials.get(username).getUserId());
			return Optional.empty();

		}
		
		
	}
	
	
	
}
