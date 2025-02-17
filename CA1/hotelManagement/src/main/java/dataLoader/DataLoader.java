package dataLoader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entities.Booking;
import entities.Customer;
import entities.Hotel;
import entities.Room;
import exceptions.HotelCreationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class DataLoader {

	public static Hotel loadHotelData(String dataFile) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		File file = new File(DataLoader.class.getClassLoader().getResource(dataFile).getFile());

		Hotel hotel = objectMapper.readValue(file, Hotel.class);

		hotel
			.getBookings()
			.forEach(booking -> {
				Room room = getRoomForBooking(hotel, booking);
				Customer customer = getCustomerForBooking(hotel, booking);
				validateCheckInOutDates(booking);

				booking.setRoom(room);
				booking.setCustomer(customer);
			});

		return hotel;
	}

	private static Room getRoomForBooking(Hotel hotel, Booking booking) {
		return hotel
			.getRooms()
			.stream()
			.filter(r -> r.id().equals(booking.getRoomId()))
			.findFirst()
			.orElseThrow(() -> new HotelCreationException("Error: Room with ID " + booking.getRoomId() + " not found.")
			);
	}

	private static Customer getCustomerForBooking(Hotel hotel, Booking booking) {
		return hotel
			.getCustomers()
			.stream()
			.filter(c -> c.ssn().equals(booking.getCustomerId()))
			.findFirst()
			.orElseThrow(() ->
				new HotelCreationException("Error: Customer with SSN " + booking.getCustomerId() + " not found.")
			);
	}

	private static void validateCheckInOutDates(Booking booking) {
		LocalDateTime checkIn = booking.getCheckIn();
		LocalDateTime checkOut = booking.getCheckOut();

		if (checkIn.isAfter(checkOut)) {
			throw new HotelCreationException(
				"Error: Check-in date is after check-out date for booking ID " + booking.getId()
			);
		}
	}
}
