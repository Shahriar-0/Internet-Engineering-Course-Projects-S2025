package dataLoader;

import static org.junit.jupiter.api.Assertions.*;

import entities.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataLoaderTest {

	private Hotel hotel;

	@BeforeEach
	void setUp() throws IOException {
		hotel = DataLoader.loadHotelData("dataTest.json");
	}

	@Test
	void testHotelDataLoadedCorrectly() {
		assertNotNull(hotel);
		assertEquals(4, hotel.getCustomers().size(), "There should be 4 customers");
		assertEquals(4, hotel.getRooms().size(), "There should be 4 rooms");
		assertEquals(4, hotel.getBookings().size(), "There should be 4 bookings");
	}

	@Test
	void testBookingsAreCorrectlyLinked() {
		Booking booking1 = hotel.getBookings().get(0);
		Booking booking2 = hotel.getBookings().get(1);

		assertNotNull(booking1.getRoom());
		assertEquals("R001", booking1.getRoom().id());
		assertNotNull(booking1.getCustomer());
		assertEquals("12345", booking1.getCustomer().ssn());

		assertNotNull(booking2.getRoom());
		assertEquals("R002", booking2.getRoom().id());
		assertNotNull(booking2.getCustomer());
		assertEquals("67890", booking2.getCustomer().ssn());
	}

	@Test
	void testGetRoomsByMinCapacity() {
		List<Room> rooms = hotel.getRooms(3);

		assertEquals(2, rooms.size(), "There should be 2 rooms with capacity >= 3");
		assertTrue(rooms.stream().allMatch(room -> room.capacity() >= 3));
	}

	@Test
	void testGetOldestCustomerName() {
		String oldestCustomerName = hotel.getOldestCustomerName();

		assertEquals("Bob Brown", oldestCustomerName, "The oldest customer should be Bob Brown");
	}

	@Test
	void testGetCustomerPhonesByRoomNumber() {
		List<String> phoneNumbers = hotel.getCustomerPhonesByRoomNumber("R001");

		assertEquals(1, phoneNumbers.size(), "There should be 1 customer in room R001");
		assertEquals("09101231212", phoneNumbers.get(0), "The phone number should match the customer in R001");
	}

	@Test
	void testGetRoomWithNoBookings() {
		Room newRoom = new Room("R005", 5);
		hotel.setRooms(List.of(newRoom));

		List<Room> roomsWithBookings = hotel.getRooms(1);

		assertEquals(1, roomsWithBookings.size(), "Room R005 should be included as it matches the capacity filter");
		assertEquals("R005", roomsWithBookings.get(0).id());
	}

	@Test
	void testBookingDateRanges() {
		Booking booking = hotel.getBookings().get(2);

		assertNotNull(booking.getCheckIn());
		assertNotNull(booking.getCheckOut());
		assertTrue(booking.getCheckOut().isAfter(booking.getCheckIn()), "Check-out should be after check-in");
	}

	@Test
	void testLogStateGeneratesCorrectFile() throws Exception {
		hotel.logState();

		File file = new File("state.json");
		assertTrue(file.exists(), "state.json file should be created after logging the state");
	}
}
