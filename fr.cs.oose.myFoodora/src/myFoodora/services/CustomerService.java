package myFoodora.services;

import myFoodora.entities.fidelityCard.BasicFidelityCard;
import myFoodora.entities.fidelityCard.FidelityCard;
import myFoodora.entities.fidelityCard.LotteryFidelityCard;
import myFoodora.entities.fidelityCard.PointFidelityCard;
import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;
import myFoodora.enums.FidelityCardType;

public class CustomerService extends UserService<Customer> {

	public void assignFidelityCard(Customer customer, Restaurant restaurant, FidelityCardType cardType) {
		FidelityCard card = createFidelityCard(cardType, restaurant, customer);
		customer.addFidelityCard(restaurant, card);
	}

	private FidelityCard createFidelityCard(FidelityCardType cardType, Restaurant restaurant, Customer customer) {
        return switch (cardType) {
            case Basic -> new BasicFidelityCard(restaurant, customer);
            case Point -> new PointFidelityCard(restaurant, customer);
            case Lottery -> new LotteryFidelityCard(restaurant, customer);
            default -> throw new IllegalArgumentException("Unknown Fidelity Card Type: " + cardType);
        };
	}

	public void removeFidelityCard(Customer customer, Restaurant restaurant) {
		customer.removeFidelityCard(restaurant);
	}
}
