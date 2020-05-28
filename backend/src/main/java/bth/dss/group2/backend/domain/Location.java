package bth.dss.group2.backend.domain;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Location")
public class Location {
	@Id
	private String id;
	private String streetAddress;
	private String zipCode;
	private String region;
	private String city;
	private String country;
	private double latitude;
	private double longitude;

	public String getId() {
		return id;
	}

	public Location setId(String id) {
		this.id = id;
		return this;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public Location setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
		return this;
	}

	public String getZipCode() {
		return zipCode;
	}

	public Location setZipCode(String zipCode) {
		this.zipCode = zipCode;
		return this;
	}

	public String getRegion() {
		return region;
	}

	public Location setRegion(String region) {
		this.region = region;
		return this;
	}

	public String getCity() {
		return city;
	}

	public Location setCity(String city) {
		this.city = city;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public Location setCountry(String country) {
		this.country = country;
		return this;
	}

	public double getLatitude() {
		return latitude;
	}

	public Location setLatitude(double latitude) {
		this.latitude = latitude;
		return this;
	}

	public double getLongitude() {
		return longitude;
	}

	public Location setLongitude(double longitude) {
		this.longitude = longitude;
		return this;
	}

	//do not change this!!
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Location location = (Location) o;
		if (id != null && location.id != null) return Objects.equals(id, location.id);
		return (Objects.equals(streetAddress, location.streetAddress) &&
				Objects.equals(zipCode, location.zipCode) &&
				Objects.equals(region, location.region) &&
				Objects.equals(city, location.city) &&
				Objects.equals(country, location.country)) &&
				Objects.equals(latitude, location.latitude) &&
				Objects.equals(longitude, location.longitude);
	}

	//do not change this!!
	@Override
	public int hashCode() {
		if (id != null) {
			return Objects.hash(id);
		}
		else {
			return Objects.hash(streetAddress, zipCode, region, city, country, latitude, longitude);
		}
	}

	@Override
	public String toString() {
		return "Location{" +
				"id='" + id + '\'' +
				", address='" + streetAddress + '\'' +
				", region='" + region + '\'' +
				", city='" + city + '\'' +
				", country='" + country + '\'' +
				", latitude=" + latitude +
				", longitude=" + longitude +
				'}';
	}
}
