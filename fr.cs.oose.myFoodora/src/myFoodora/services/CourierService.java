package myFoodora.services;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import myFoodora.entities.Order;
import myFoodora.entities.user.Courier;
import myFoodora.enums.DeliveryPolicyType;

public class CourierService extends UserService<Courier> {
	private Function<Order, Comparator<Courier>> deliveryPolicy;
	
	public CourierService() {
		super();
		selectDeliveryPolicy(DeliveryPolicyType.Fair);
	}

	public Optional<Courier> assigneCourier(Order orderToAssign) {
		return users.values().stream()
			.sorted(deliveryPolicy.apply(orderToAssign))
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
	
	private void setDeliveryPolicy(Function<Order, Comparator<Courier>> deliveryPolicy) {
		this.deliveryPolicy = deliveryPolicy;
	}
	
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
