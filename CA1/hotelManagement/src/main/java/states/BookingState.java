package states;

import entities.Customer;
import java.time.LocalDateTime;
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

	public BookingState(String id, Customer customer, LocalDateTime check_in, LocalDateTime check_out) {
		this.id = id;
		this.customer = customer;
		this.check_in = check_in.toString();
		this.check_out = check_out.toString();
	}
}
