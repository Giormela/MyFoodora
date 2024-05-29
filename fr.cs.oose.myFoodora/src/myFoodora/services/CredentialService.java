package myFoodora.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import myFoodora.entities.Credential;
import myFoodora.exceptions.UserRegistrationException;

public class CredentialService {
	private Map<String, Credential> credentials;
	
	public CredentialService() {
		super();
		this.credentials = new HashMap<String, Credential>();
	}

	void registerCredential(Credential credential) {
		credentials.put(credential.getUsername(), credential);
	}
	
	void removeCredential(String username) {
		credentials.remove(username);
	}
	
	void checkCredentialRegistration(Credential credential) throws UserRegistrationException {
		if(credentials.containsKey(credential.getUsername()))
			throw new UserRegistrationException("Username already used");
	}
	
	public Optional<Credential> tryLogin(String username, String password) {
		if (credentials.containsKey(username) && credentials.get(username).checkCorrespondance(username, password))
			return Optional.of(credentials.get(username));
		return Optional.empty();
	}
}
