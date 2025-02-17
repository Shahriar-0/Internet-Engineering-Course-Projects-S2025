package states;

import entities.Booking;
import entities.Customer;
import java.time.format.DateTimeFormatter;

public record BookingState(String id, Customer customer, String check_in, String check_out) {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public BookingState(Booking booking) {
		this(
			booking.getId(),
			booking.getCustomer(),
			booking.getCheckIn().format(FORMATTER),
			booking.getCheckOut().format(FORMATTER)
		);
	}
}
