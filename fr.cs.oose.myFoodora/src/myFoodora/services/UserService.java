package myFoodora.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import myFoodora.entities.Credential;
import myFoodora.entities.user.*;
import myFoodora.exceptions.UserRegistrationException;

abstract class UserService <U extends User> {
	protected Map<Integer, U> users;
	private CredentialService credentialService;

	UserService() {
		super();
		this.users = new HashMap<Integer, U>();
		this.credentialService = new CredentialService();
		
	}
	
	public Collection<U> getList(){
		return users.values();
	}
	
	public U getUserById(Integer userId) {
		return users.get(userId);
	}

	public void registerUser(U newUser) throws UserRegistrationException{
		checkUserRegistration(newUser);
		
		users.put(newUser.getId(), newUser);
		credentialService.registerCredential(newUser.getCredential());
	}
	
	private void checkUserRegistration(U newUser) throws UserRegistrationException{
		if (users.containsKey(newUser.getId()))
			throw new UserRegistrationException("User already registered");
		
		credentialService.checkCredentialRegistration(newUser.getCredential());		
	}
	
	public void removeUser(Integer userId) {
		U removedUser = users.remove(userId);
		if (removedUser != null) 
			credentialService.removeCredential(removedUser.getCredential().getUsername());
	}
	
	public  Optional<U> tryLogin(String username, String password) {
		return credentialService.tryLogin(username, password).map(id->users.get(id));
	}
	
	private class CredentialService {
		private Map<String, Credential> credentials;
		
		private CredentialService() {
			super();
			this.credentials = new HashMap<String, Credential>();
		}

		private void registerCredential(Credential credential) {
			credentials.put(credential.getUsername(), credential);
		}
		
		private void removeCredential(String username) {
			credentials.remove(username);
		}
		
		private void checkCredentialRegistration(Credential credential) throws UserRegistrationException {
			if(credentials.containsKey(credential.getUsername()))
				throw new UserRegistrationException("Username already used");
		}
		
		private Optional<Integer> tryLogin(String username, String password) {
			if (credentials.containsKey(username) && credentials.get(username).checkCorrespondance(username, password))
				return Optional.of(credentials.get(username).getUserId());
			return Optional.empty();

		}
	}
}
