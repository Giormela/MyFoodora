package myFoodora.services;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

import myFoodora.entities.Notification;
import myFoodora.entities.Order;
import myFoodora.entities.user.Courier;
import myFoodora.enums.DeliveryPolicyType;
import myFoodora.enums.OrderState;

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
				.filter(Courier::isOffDuty)
				.sorted(deliveryPolicy.apply(orderToAssign))
				.findFirst();
		if (bestCourier.isPresent()) {
			Courier courierFound = bestCourier.get();
			courierFound.notify(new Notification("You have been assigned to a new order!"));
			courierFound.assignOrder(orderToAssign);
			orderToAssign.setState(OrderState.Processing);
		}
	}

	/**
	 * Finds the courier with the most deliveries completed.
	 *
	 * @return An {@link Optional} containing the courier with the most deliveries, or empty if no couriers exist.
	 * @throws IllegalStateException if there are no users in the system.
	 */
	public Collection<Courier> getTopCourier() {
		return getList().stream()
			.sorted(Comparator.comparing(Courier::getOrderCount).reversed())
			.toList();
	}
	

	/**
	 * Selects and sets the delivery policy based on the provided policy type.
	 * @param deliveryPolicyType The type of delivery policy to apply (e.g., Fast, Fair).
	 */
	public void selectDeliveryPolicy(DeliveryPolicyType deliveryPolicyType) {
		switch (deliveryPolicyType) {
		case Fast:
			this.deliveryPolicy = (o -> Comparator.comparing(c->c.getDistanceFrom(o.getCustomer())));
			break;
		case Fair:
			this.deliveryPolicy = (o -> Comparator.comparing(Courier::getOrderCount));
			break;
		default:
			this.deliveryPolicy = (o -> Comparator.comparing(Courier::getOrderCount));
			break;
		}
	}
}
