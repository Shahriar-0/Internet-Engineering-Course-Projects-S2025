package states;

import entities.Booking;
import entities.Customer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BookingState {

	private String id;
	private Customer customer;
	private String check_in;
	private String check_out;

	public BookingState(Booking booking) {
		this.id = booking.getId();
		this.customer = booking.getCustomer();
		this.check_in = booking.getCheckIn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.check_out = booking.getCheckOut().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public BookingState(String id, Customer customer, LocalDateTime check_in, LocalDateTime check_out) {
		this.id = id;
		this.customer = customer;
		this.check_in = check_in.toString();
		this.check_out = check_out.toString();
	}
}
