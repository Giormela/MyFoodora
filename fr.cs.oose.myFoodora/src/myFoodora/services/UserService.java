package myFoodora.services;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import myFoodora.entities.Credential;
import myFoodora.entities.FidelityCard;
import myFoodora.entities.user.*;
import myFoodora.exceptions.UserRegistrationException;

public class UserService {
	private Map<Integer, User> users;
	private CredentialService credentialService;
	private Comparator<Courier> deliveryPolicy;

	public UserService() {
		super();
		this.users = new HashMap<Integer, User>();
		this.credentialService = new CredentialService();
		this.deliveryPolicy = Comparator.comparing(Courier::getCount);
	}
	
	public void setDeliveryPolicy(Comparator<Courier> deliveryPolicy) {
		this.deliveryPolicy = deliveryPolicy;
	}
	
	public User getUserById(Integer userId) {
		return users.get(userId);
	}
	
	public <T extends User> Stream<T> getUsersOfType(Class<T> type){
		return users.values().stream()
			      .filter(type::isInstance)
			      .map(u->(T)u);
	}
	
	public void registerUser(User newUser) throws UserRegistrationException{
		checkUserRegistration(newUser);
		
		users.put(newUser.getId(), newUser);
		credentialService.registerCredential(newUser.getCredential());
	}
	
	private void checkUserRegistration(User user) throws UserRegistrationException{
		if (users.containsKey(user.getId()))
			throw new UserRegistrationException("User already registered");
		
		this.credentialService.checkCredentialRegistration(user.getCredential());		
	}
	
	public void removeUser(Integer userId) {
		User removedUser = users.remove(userId);
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
	
	public Courier assigneCourier() throws NoSuchElementException {
		return getUsersOfType(Courier.class)
			.sorted(deliveryPolicy)
			.findFirst().get();
	}
	
	public Restaurant getBestRestaurant() throws NoSuchElementException {
		return getUsersOfType(Restaurant.class)
			.sorted(Comparator.comparing(Restaurant::getProfit))
			.findFirst().get();
	}
	
	public Restaurant getWorstRestaurant() throws NoSuchElementException {
		return getUsersOfType(Restaurant.class)
			.sorted(Comparator.comparing(Restaurant::getProfit).reversed())
			.findFirst().get();
	}
	
	private Optional<User> tryLogin(String username, String password) {
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
			this.credentials.remove(username);
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
