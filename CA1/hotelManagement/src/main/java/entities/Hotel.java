package entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.*;
import states.BookingState;
import states.RoomState;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Hotel {

	private List<Customer> customers;
	private List<Room> rooms;
	private List<Booking> bookings;

	private final String stateFilePath = "state.json";

	public List<Room> getRooms(int minCapacity) {
		return rooms.stream().filter(room -> room.capacity() >= minCapacity).collect(Collectors.toList());
	}

	public String getOldestCustomerName() {
		return customers
			.stream()
			.max((c1, c2) -> Integer.compare(c1.age(), c2.age()))
			.map(Customer::name)
			.orElse("No customers");
	}

	public List<String> getCustomerPhonesByRoomNumber(String roomNumber) {
		return bookings
			.stream()
			.filter(b -> b.getRoom().id().equals(roomNumber))
			.map(b -> b.getCustomer().phone())
			.collect(Collectors.toList());
	}

	public void logState() throws IOException {
		List<RoomState> roomStates = rooms
			.stream()
			.map(room -> {
				List<BookingState> bookingStates = bookings
					.stream()
					.filter(booking -> booking.getRoom().id().equals(room.id()))
					.map(booking -> new BookingState(booking))
					.collect(Collectors.toList());
				return new RoomState(room.id(), room.capacity(), bookingStates);
			})
			.collect(Collectors.toList());

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		File file = new File(stateFilePath);
		objectMapper.writeValue(file, roomStates);
	}
}
