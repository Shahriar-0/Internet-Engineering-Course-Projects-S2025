package webapi.views.user;

import domain.entities.user.Customer;
import domain.entities.user.User;
import domain.valueobjects.Address;

public record UserView(
	String username,
	String role,
	String email,
	Address address,
	Long balance
) {
	public UserView(User entity) {
		this(
			entity.getUsername(),
			entity.getRole().getValue(),
			entity.getEmail(),
			entity.getAddress(),
			(entity instanceof Customer c) ? c.getBalance() : null
		);
	}
}
