package entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;

class HotelTest {

    private Hotel hotel;
    private Customer customer1;
    private Customer customer2;
    private Room room1;
    private Room room2;
    private Booking booking1;
    private Booking booking2;

    @BeforeEach
    void setUp() {
        customer1 = new Customer("12345", "John Doe", "09101231212", 30);
        customer2 = new Customer("67890", "Jane Smith", "09107654321", 25);

        room1 = new Room("R001", 3);
        room2 = new Room("R002", 2);

        booking1 = new Booking("B001", room1, customer1, LocalDateTime.of(2024, 3, 1, 8, 0, 0), LocalDateTime.of(2024, 3, 2, 16, 0, 0));
        booking2 = new Booking("B002", room2, customer2, LocalDateTime.of(2024, 3, 2, 15, 0, 0), LocalDateTime.of(2024, 3, 6, 12, 0, 0));

        hotel = new Hotel();
        hotel.setCustomers(Arrays.asList(customer1, customer2));
        hotel.setRooms(Arrays.asList(room1, room2));
        hotel.setBookings(Arrays.asList(booking1, booking2));
    }

    @AfterEach
    void tearDown() {
        hotel = null;
    }

    @Test
    void testGetRoomsWithMinCapacity() {
        List<Room> rooms = hotel.getRooms(3);

        assertEquals(1, rooms.size());
        assertEquals("R001", rooms.get(0).getId());
    }

    @Test
    void testGetOldestCustomerName() {
        String oldestCustomer = hotel.getOldestCustomerName();

        assertEquals("John Doe", oldestCustomer);
    }

    @Test
    void testGetCustomerPhonesByRoomNumber() {
        List<String> phoneNumbers = hotel.getCustomerPhonesByRoomNumber("R001");

        assertEquals(1, phoneNumbers.size());
        assertEquals("09101231212", phoneNumbers.get(0));
    }

    @Test
    void testGetCustomerPhonesByNonExistentRoom() {
        List<String> phoneNumbers = hotel.getCustomerPhonesByRoomNumber("R003");

        assertTrue(phoneNumbers.isEmpty());
    }

    @Test
    void testLogStateGeneratesCorrectFile() throws Exception {
        hotel.logState();

        File file = new File("state.json");
        assertTrue(file.exists());

        file.delete();
    }

    @Test
    void testGetRoomsWithMinCapacityEdgeCase() {
        List<Room> rooms = hotel.getRooms(0);

        assertEquals(2, rooms.size());
    }

    @Test
    void testGetOldestCustomerNameWithNoCustomers() {
        hotel.setCustomers(Arrays.asList());

        String oldestCustomer = hotel.getOldestCustomerName();

        assertEquals("No customers", oldestCustomer);
    }

    @Test
    void testGetRoomsForNonExistentRoomId() {
        hotel.setRooms(Arrays.asList());

        List<Room> rooms = hotel.getRooms(1);

        assertTrue(rooms.isEmpty());
    }

    @Test
    void testBookingDataLinking() {
        Booking booking = new Booking("B003", room1, customer1, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        hotel.setBookings(Arrays.asList(booking));

        assertNotNull(booking.getRoom());
        assertEquals("R001", booking.getRoom().getId());
        assertNotNull(booking.getCustomer());
        assertEquals("12345", booking.getCustomer().getSsn());
    }

    @Test
    void testRoomWithNoBookings() {
        Room newRoom = new Room("R003", 4);
        hotel.setRooms(Arrays.asList(room1, room2, newRoom));

        List<Room> rooms = hotel.getRooms(4);

        assertEquals(1, rooms.size());
        assertEquals("R003", rooms.get(0).getId());
    }
}
