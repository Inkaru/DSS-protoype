package bth.dss.group2.backend.domain;

import java.time.Instant;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MarketplaceItem")
public class MarketplaceItem {

	@Id
	private String id;
	private String name;
	private Instant created;
	private Instant updated;
	private double price;
	private String description;
	private Location location;
	private MarketplaceItemType type;

	public MarketplaceItem() {
	}

	public MarketplaceItem(String name, Instant created, Instant updated, double price, String description, Location location, MarketplaceItemType type) {
		this.name = name;
		this.created = created;
		this.updated = updated;
		this.price = price;
		this.description = description;
		this.location = location;
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

	public Location getLocation() {
		return location;
	}

	public MarketplaceItem setLocation(Location location) {
		this.location = location;
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
				", location=" + location +
				", type=" + type +
				'}';
	}

	public enum MarketplaceItemType {
		OFFER, REQUEST
	}
}
