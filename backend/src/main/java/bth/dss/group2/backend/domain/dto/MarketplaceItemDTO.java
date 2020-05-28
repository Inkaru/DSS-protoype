package bth.dss.group2.backend.domain.dto;

import javax.validation.constraints.NotNull;

import bth.dss.group2.backend.domain.MarketplaceItem;
import bth.dss.group2.backend.domain.User;
import org.modelmapper.ModelMapper;

public class MarketplaceItemDTO {

	private String id;

	@NotNull
	private String name;

	private double price;

	private String description;

	private LocationDTO location;

	@NotNull
	private MarketplaceItem.MarketplaceItemType type;

	private UserDTO creator;

	public MarketplaceItemDTO() {
		location = new LocationDTO();
	}

	public String getId() {
		return id;
	}

	public static MarketplaceItemDTO create(MarketplaceItem item) {
		ModelMapper mapper = new ModelMapper();
		MarketplaceItemDTO dto = new MarketplaceItemDTO();
		dto.setId(item.getId());
		dto.setName(item.getName());
		dto.setDescription(item.getDescription());
		dto.setPrice(item.getPrice());
		dto.setType(item.getType());
		dto.setLocation(mapper.map(item.getLocation(), LocationDTO.class));
		return dto;
	}

	public String getName() {
		return name;
	}

	public MarketplaceItemDTO setId(String id) {
		this.id = id;
		return this;
	}

	public double getPrice() {
		return price;
	}

	public MarketplaceItemDTO setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public MarketplaceItemDTO setPrice(double price) {
		this.price = price;
		return this;
	}

	public MarketplaceItemDTO setDescription(String description) {
		this.description = description;
		return this;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public MarketplaceItem.MarketplaceItemType getType() {
		return type;
	}

	public MarketplaceItemDTO setLocation(LocationDTO location) {
		this.location = location;
		return this;
	}

	public MarketplaceItemDTO setType(MarketplaceItem.MarketplaceItemType type) {
		this.type = type;
		return this;
	}

	public UserDTO getCreator() {
		return creator;
	}

	public MarketplaceItemDTO setCreator(UserDTO creator) {
		this.creator = creator;
		return this;
	}

	public static MarketplaceItemDTO createWithReferences(MarketplaceItem item, User user) {
		MarketplaceItemDTO dto = create(item);
		dto.setCreator(UserDTO.create(user));
		return dto;
	}
}
