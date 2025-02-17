package entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Booking {

	private String id;
	private Room room;
	private Customer customer;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonProperty("check_in")
	private LocalDateTime checkIn;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonProperty("check_out")
	private LocalDateTime checkOut;

	public String roomId;
	public String customerId;

	public Booking(String id, Room room, Customer customer, LocalDateTime checkIn, LocalDateTime checkOut) {
		this.id = id;
		this.room = room;
		this.customer = customer;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.roomId = room.id();
		this.customerId = customer.ssn();
	}

	@JsonCreator
	public Booking(
		@JsonProperty("id") String id,
		@JsonProperty("check_in") LocalDateTime checkIn,
		@JsonProperty("check_out") LocalDateTime checkOut,
		@JsonProperty("room_id") String roomId,
		@JsonProperty("customer_id") String customerId
	) {
		this.id = id;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.roomId = roomId;
		this.customerId = customerId;
	}
}
