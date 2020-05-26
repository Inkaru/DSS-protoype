package bth.dss.group2.backend.model;

import java.time.Instant;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MarketplaceItem")
public class MarketplaceItem {

	@Id
	@JsonProperty
	private String id;
	@JsonProperty
	private String name;
	@JsonProperty
	private Instant created;
	@JsonProperty
	private Instant updated;
	@JsonProperty
	private double price;
	@JsonProperty
	private String description;
	@JsonProperty
	private String city;
	@JsonProperty
	private String country;
	@JsonProperty
	private MarketplaceItemType type;

	public MarketplaceItem() {
	}

	public MarketplaceItem(String name, Instant created, Instant updated, double price, String description, String city, String country, MarketplaceItemType type) {
		this.name = name;
		this.created = created;
		this.updated = updated;
		this.price = price;
		this.description = description;
		this.city = city;
		this.country = country;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public MarketplaceItem setName(String name) {
		this.name = name;
		return this;
	}

	public Instant getCreated() {
		return created;
	}

	public MarketplaceItem setCreated(Instant created) {
		this.created = created;
		return this;
	}

	public Instant getUpdated() {
		return updated;
	}

	public MarketplaceItem setUpdated(Instant updated) {
		this.updated = updated;
		return this;
	}

	public double getPrice() {
		return price;
	}

	public MarketplaceItem setPrice(double price) {
		this.price = price;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public MarketplaceItem setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getCity() {
		return city;
	}

	public MarketplaceItem setCity(String city) {
		this.city = city;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public MarketplaceItem setCountry(String country) {
		this.country = country;
		return this;
	}

	public MarketplaceItemType getType() {
		return type;
	}

	public MarketplaceItem setType(MarketplaceItemType type) {
		this.type = type;
		return this;
	}

	@Override
	public String toString() {
		return "MarketplaceItem{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", created=" + created +
				", updated=" + updated +
				", price=" + price +
				", description='" + description + '\'' +
				", city='" + city + '\'' +
				", country='" + country + '\'' +
				", type=" + type +
				'}';
	}

	public enum MarketplaceItemType {
		OFFER, REQUEST
	}
}
