package myFoodora.services;

import java.util.Comparator;
import java.util.Optional;

import myFoodora.entities.user.Courier;

public class CourierService extends UserService<Courier> {
	private Comparator<Courier> deliveryPolicy;
	
	public CourierService() {
		super();
		this.deliveryPolicy = Comparator.comparing(Courier::getCount);
	}

	public Optional<Courier> assigneCourier() {
		return users.values().stream()
			.sorted(deliveryPolicy)
			.findFirst();
	}
	
	public Optional<Courier> getBestCourier() {
		return users.values().stream()
			.sorted(Comparator.comparing(Courier::getCount).reversed())
			.findFirst();
	}
	
	public Optional<Courier> getWorstCourier() {
		return users.values().stream()
			.sorted(Comparator.comparing(Courier::getCount))
			.findFirst();
	}
	
	public void setDeliveryPolicy(Comparator<Courier> deliveryPolicy) {
		this.deliveryPolicy = deliveryPolicy;
	}
}
