package bth.dss.group2.backend.model.dto;

import javax.validation.constraints.NotNull;

import bth.dss.group2.backend.model.MarketplaceItem;
import bth.dss.group2.backend.model.User;

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

	private UserDTO creator;

	public MarketplaceItemDTO() {
	}

	public MarketplaceItemDTO(MarketplaceItem item) {
		this.id = item.getId();
		this.name = item.getName();
		this.price = item.getPrice();
		this.description = item.getDescription();
		this.city = item.getCity();
		this.country = item.getCountry();
		this.type = item.getType();
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

	public static MarketplaceItemDTO create(MarketplaceItem item) {
		MarketplaceItemDTO dto = new MarketplaceItemDTO();
		dto.setId(item.getId());
		dto.setName(item.getName());
		dto.setDescription(item.getDescription());
		dto.setPrice(item.getPrice());
		dto.setType(item.getType());
		dto.setCity(item.getCity());
		dto.setCountry(item.getCountry());
		return dto;
	}

	public static MarketplaceItemDTO createWithReferences(MarketplaceItem item, User user) {
		MarketplaceItemDTO dto = create(item);
		dto.setCreator(UserDTO.create(user));
		return dto;
	}

	public UserDTO getCreator() {
		return creator;
	}

	public MarketplaceItemDTO setCreator(UserDTO creator) {
		this.creator = creator;
		return this;
	}
}
