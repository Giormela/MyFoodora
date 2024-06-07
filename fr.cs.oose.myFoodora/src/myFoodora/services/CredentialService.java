package myFoodora.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import myFoodora.entities.Credential;
import myFoodora.exceptions.UserRegistrationException;

/**
 * This class is a service for managing credentials.
 * It allows to register and remove credentials, and to check if a credential is already registered.
 * It also allows to try to login with a username and a password.
 */
public class CredentialService {
	private Map<String, Credential> credentials;

	/**
	 * Constructs a new CredentialService.
	 */
	public CredentialService() {
		super();
		this.credentials = new HashMap<String, Credential>();
	}

	/**
	 * Registers a new credential.
	 *
	 * @param credential the credential to register
	 */
	void registerCredential(Credential credential) {
		credentials.put(credential.getUsername(), credential);
	}

	/**
	 * Removes a credential.
	 *
	 * @param username the username of the credential to remove
	 */
	void removeCredential(String username) {
		credentials.remove(username);
	}

	/**
	 * Checks if a credential is already registered.
	 *
	 * @param credential the credential to check
	 * @throws UserRegistrationException if the credential is already registered
	 */
	void checkCredentialRegistration(Credential credential) throws UserRegistrationException {
		if(credentials.containsKey(credential.getUsername()))
			throw new UserRegistrationException("Username already used");
	}

	/**
	 * Tries to login with a username and a password.
	 *
	 * @param username the username to login with
	 * @param password the password to login with
	 * @return an optional containing the credential if the login is successful, an empty optional otherwise
	 */
	public Optional<Credential> tryLogin(String username, String password) {
		if (credentials.containsKey(username) && credentials.get(username).checkCorrespondance(username, password))
			return Optional.of(credentials.get(username));
		return Optional.empty();
	}
}
