import java.util.List;
import java.util.stream.Collectors;

public class Hotel {
    private List<Customer> customers;
    private List<Room> rooms;
    private List<Booking> bookings;

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public List<Room> getRooms(int minCapacity) {
        return rooms.stream()
                .filter(room -> room.getCapacity() >= minCapacity)
                .collect(Collectors.toList());
    }

    public String getOldestCustomerName() {
        return customers.stream()
                .max((c1, c2) -> Integer.compare(c1.getAge(), c2.getAge()))
                .map(Customer::getName)
                .orElse("No customers");
    }

    public List<String> getCustomerPhonesByRoomNumber(String roomNumber) {
        return bookings.stream()
                .filter(b -> b.getRoom().getId().equals(roomNumber))
                .map(b -> b.getCustomer().getPhoneNumber())
                .collect(Collectors.toList());
    }
}
