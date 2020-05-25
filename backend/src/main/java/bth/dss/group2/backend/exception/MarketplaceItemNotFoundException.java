package bth.dss.group2.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MarketplaceItemNotFoundException extends ResponseStatusException {

	public MarketplaceItemNotFoundException() {
		this(HttpStatus.BAD_REQUEST, "Could not find marketplace item.");
	}

	private MarketplaceItemNotFoundException(HttpStatus status, String reason) {
		super(status, reason);
	}
}
