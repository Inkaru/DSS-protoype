package bth.dss.group2.backend.domain.dto;

public class LocationDTO {
	private String streetAddress;

	private String zipCode;
	private String region;
	private String city;
	private String country;
	private double latitude;
	private double longitude;

	public LocationDTO() {
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public LocationDTO setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
		return this;
	}

	public String getZipCode() {
		return zipCode;
	}

	public LocationDTO setZipCode(String zipCode) {
		this.zipCode = zipCode;
		return this;
	}

	public String getRegion() {
		return region;
	}

	public LocationDTO setRegion(String region) {
		this.region = region;
		return this;
	}

	public String getCity() {
		return city;
	}

	public LocationDTO setCity(String city) {
		this.city = city;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public LocationDTO setCountry(String country) {
		this.country = country;
		return this;
	}

	public double getLatitude() {
		return latitude;
	}

	public LocationDTO setLatitude(double latitude) {
		this.latitude = latitude;
		return this;
	}

	public double getLongitude() {
		return longitude;
	}

	public LocationDTO setLongitude(double longitude) {
		this.longitude = longitude;
		return this;
	}
}

