package myFoodora.entities;

import java.util.Random;

public class Location {
	private Double longitude;
	private Double latitude;
	
	public static Location convertFromAdressToCoordinates(String address) {
		Random random = new Random();
		return new Location(random.nextDouble(), random.nextDouble());
	}
	
	public Location(Double longitude, Double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Double getDistanceFrom(Location from) {
		return Math.sqrt(Math.pow(longitude - from.longitude, 2) + Math.pow(latitude - from.latitude, 2));
	}
}
