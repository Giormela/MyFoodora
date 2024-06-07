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

public class CourierService extends UserService<Courier> {
	private Function<Order, Comparator<Courier>> deliveryPolicy;
	
	public CourierService() {
		super();
		setDeliveryPolicy(DeliveryPolicyType.Fair);
	}

	public void assigneOrderToBestCourier(Order orderToAssign) {
		Optional<Courier> bestCourier = getList().stream()
				.filter(Courier::isOffDuty)
				.sorted(deliveryPolicy.apply(orderToAssign))
				.findFirst();
		if (bestCourier.isPresent()) {
			Courier courierFound = bestCourier.get();
			courierFound.notify(new Notification("You have been assigned to a new order!"));
			courierFound.asssignOrder(orderToAssign);
			orderToAssign.setState(OrderState.Processing);
		}
	}

	/**
	 * Finds the courier with the most deliveries completed.
	 *
	 * @return An {@link Optional} containing the courier with the most deliveries, or empty if no couriers exist.
	 * @throws IllegalStateException if there are no users in the system.
	 */
	public Collection<Courier> getTopCouriers() {
		return getList().stream()
			.sorted(Comparator.comparing(Courier::getOrderCount).reversed())
			.toList();
	}
	
	public void setDeliveryPolicy(DeliveryPolicyType deliveryPolicyType) {
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
