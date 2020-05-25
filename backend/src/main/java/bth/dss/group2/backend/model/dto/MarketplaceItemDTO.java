package bth.dss.group2.backend.model.dto;

import javax.validation.constraints.NotNull;

import bth.dss.group2.backend.model.MarketplaceItem;

public class MarketplaceItemDTO {

	private String id;

	@NotNull
	private String name;

	private double price;

	private String description;

	private String city;

	private String country;

	@NotNull
	private MarketplaceItem.MarketplaceItemType type;

	public MarketplaceItemDTO() {
	}

	public MarketplaceItemDTO(@NotNull String name, double price, String description, String city, String country, MarketplaceItem.MarketplaceItemType type) {
		this.name = name;
		this.price = price;
		this.description = description;
		this.city = city;
		this.country = country;
		this.type = type;
	}

	public MarketplaceItemDTO(String id, @NotNull String name, double price, String description, String city, String country, MarketplaceItem.MarketplaceItemType type) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.city = city;
		this.country = country;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public MarketplaceItem.MarketplaceItemType getType() {
		return type;
	}

	public void setType(MarketplaceItem.MarketplaceItemType type) {
		this.type = type;
	}
}
