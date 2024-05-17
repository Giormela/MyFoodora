package myFoodora.services;

import java.util.Comparator;
import java.util.Optional;

import myFoodora.entities.user.Courier;

public class CourierService extends UserService<Courier> {
	private Comparator<Courier> deliveryPolicy;
	
	public CourierService() {
		super();
		this.deliveryPolicy = Comparator.comparing(Courier::getOrderCount);
	}

	public Optional<Courier> assigneCourier() {
		return users.values().stream()
			.sorted(deliveryPolicy)
			.findFirst();
	}

	/**
	 * Finds the courier with the most deliveries completed.
	 *
	 * @return An {@link Optional} containing the courier with the most deliveries, or empty if no couriers exist.
	 * @throws IllegalStateException if there are no users in the system.
	 */
	public Optional<Courier> getBestCourier() {
		return users.values().stream()
			.sorted(Comparator.comparing(Courier::getOrderCount).reversed())
			.findFirst();
	}
	
	/**
	 * Finds the courier with the fewest deliveries completed.
	 *
	 * @return An {@link Optional} containing the courier with the fewest deliveries, or empty if no couriers exist.
	 * @throws IllegalStateException if there are no users in the system.
	 */
	public Optional<Courier> getWorstCourier() {
		return users.values().stream()
			.sorted(Comparator.comparing(Courier::getOrderCount))
			.findFirst();
	}
	
	public void setDeliveryPolicy(Comparator<Courier> deliveryPolicy) {
		this.deliveryPolicy = deliveryPolicy;
	}
}
