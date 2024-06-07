package myFoodora.services;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import myFoodora.entities.Order;
import myFoodora.entities.user.Courier;
import myFoodora.enums.DeliveryPolicyType;

/**
 * Service class for managing couriers within the myFoodora system.
 * This class handles courier assignments based on specified delivery policies and manages courier records.
 */
public class CourierService extends UserService<Courier> {
	private Function<Order, Comparator<Courier>> deliveryPolicy;

	/**
	 * Constructs a new CourierService and sets the default delivery policy to 'Fair'.
	 */
	public CourierService() {
		super();
		selectDeliveryPolicy(DeliveryPolicyType.Fair);
	}

	/**
	 * Assigns an order to the best available courier based on the current delivery policy.
	 * The best courier is determined as the first one available who is off duty according to the policy criteria.
	 * @param orderToAssign The order that needs to be assigned.
	 */
	public void assignOrderToBestCourier(Order orderToAssign) {
		Optional<Courier> bestCourier = getList().stream()
				.filter(Courier::isActive)
				.filter(Courier::isOffDuty)
				.sorted(deliveryPolicy.apply(orderToAssign))
				.findFirst();
		if (bestCourier.isPresent()) {
			bestCourier.get().assignOrder(orderToAssign);
		}
	}

	/**
	 * Finds the courier with the most deliveries completed.
	 *
	 * @return An {@link Optional} containing the courier with the most deliveries, or empty if no couriers exist.
	 * @throws IllegalStateException if there are no users in the system.
	 */
	public Optional<Courier> getBestCourier() {
		return getList().stream()
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
		return getList().stream()
			.sorted(Comparator.comparing(Courier::getOrderCount))
			.findFirst();
	}
	
	private void setDeliveryPolicy(Function<Order, Comparator<Courier>> deliveryPolicy) {
		this.deliveryPolicy = deliveryPolicy;
	}

	/**
	 * Selects and sets the delivery policy based on the provided policy type.
	 * @param deliveryPolicyType The type of delivery policy to apply (e.g., Fast, Fair).
	 */
	public void selectDeliveryPolicy(DeliveryPolicyType deliveryPolicyType) {
		switch (deliveryPolicyType) {
		case Fast:
			setDeliveryPolicy(o -> Comparator.comparing(c->c.getDistanceFrom(o.getCustomer())));
			break;
		case Fair:
			setDeliveryPolicy(o -> Comparator.comparing(Courier::getOrderCount));
			break;
		default:
			setDeliveryPolicy(o -> Comparator.comparing(Courier::getOrderCount));
			break;
		}
	}
}
