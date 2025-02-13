package entities;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import states.BookingState;
import states.RoomState;

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

    public void logState() throws IOException {
        List<RoomState> roomStates = rooms.stream().map(room -> {
            List<BookingState> bookingStates = bookings.stream()
                    .filter(booking -> booking.getRoom().getId().equals(room.getId()))
                    .map(booking -> new BookingState(
                            booking.getId(),
                            booking.getCustomer(),
                            booking.getCheckIn(),
                            booking.getCheckOut()
                    ))
                    .collect(Collectors.toList());
            return new RoomState(room.getId(), room.getCapacity(), bookingStates);
        }).collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        File file = new File("state.json");
        objectMapper.writeValue(file, roomStates);
    }
}
