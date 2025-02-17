package entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import states.BookingState;
import states.RoomState;

public class Hotel {

	private List<Customer> customers;
	private List<Room> rooms;
	private List<Booking> bookings;

	public Hotel(List<Customer> customers, List<Room> rooms, List<Booking> bookings) {
		this.customers = customers;
		this.rooms = rooms;
		this.bookings = bookings;
	}

	public Hotel() {}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public List<Room> getRooms(int minCapacity) {
		return rooms.stream().filter(room -> room.getCapacity() >= minCapacity).collect(Collectors.toList());
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public String getOldestCustomerName() {
		return customers
			.stream()
			.max((c1, c2) -> Integer.compare(c1.getAge(), c2.getAge()))
			.map(Customer::getName)
			.orElse("No customers");
	}

	public List<String> getCustomerPhonesByRoomNumber(String roomNumber) {
		return bookings
			.stream()
			.filter(b -> b.getRoom().getId().equals(roomNumber))
			.map(b -> b.getCustomer().getPhoneNumber())
			.collect(Collectors.toList());
	}

	public void logState() throws IOException {
		List<RoomState> roomStates = rooms
			.stream()
			.map(room -> {
				List<BookingState> bookingStates = bookings
					.stream()
					.filter(booking -> booking.getRoom().getId().equals(room.getId()))
					.map(booking ->
						new BookingState(
							booking.getId(),
							booking.getCustomer(),
							booking.getCheckIn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
							booking.getCheckOut().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
						)
					)
					.collect(Collectors.toList());
				return new RoomState(room.getId(), room.getCapacity(), bookingStates);
			})
			.collect(Collectors.toList());

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		File file = new File("state.json");
		objectMapper.writeValue(file, roomStates);
	}
}
