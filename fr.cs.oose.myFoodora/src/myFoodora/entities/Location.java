package myFoodora.entities;

public class Location {
	private Double longitude;
	private Double latitude;
	
	public Location(Double longitude, Double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Double getDistanceFrom(Location from) {
		return Math.sqrt(Math.pow(longitude - from.longitude, 2) + Math.pow(latitude - from.latitude, 2));
	}
}
