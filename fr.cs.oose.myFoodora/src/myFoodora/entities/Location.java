package myFoodora.entities;

import java.util.Random;

public class Location {
	private Double longitude;
	private Double latitude;

	/**
	 * Converts a string address to geographical coordinates.
	 * @param address The address to convert.
	 * @return A new Location object with longitude and latitude.
	 */
	public static Location convertFromAdressToCoordinates(String address) {
		Random random = new Random();
		return new Location(random.nextDouble(), random.nextDouble());
	}

	/**
	 * Constructs a new Location instance.
	 * @param longitude The geographic longitude.
	 * @param latitude The geographic latitude.
	 */
	public Location(Double longitude, Double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/**
	 * Calculates the Euclidean distance from this location to another.
	 * @param from Another location to which the distance is calculated.
	 * @return The distance between this location and 'from'.
	 */
	public Double getDistanceFrom(Location from) {
		return Math.sqrt(Math.pow(longitude - from.longitude, 2) + Math.pow(latitude - from.latitude, 2));
	}

	@Override
	public String toString() {
		return  String.format("%.2f", longitude) + ", " + String.format("%.2f", latitude);
	}

	public Double getLongitude() {
		return longitude;
	}

	public Double getLatitude() {
		return latitude;
	}
}
