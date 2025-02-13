import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public class DataLoader {
    public static Hotel loadHotelData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        File file = new File(DataLoader.class.getClassLoader().getResource("data.json").getFile());

        Hotel hotel = objectMapper.readValue(file, Hotel.class);

        hotel.getBookings().forEach(booking -> {
            Room room = hotel.getRooms().stream()
                    .filter(r -> r.getId().equals(booking.roomId))
                    .findFirst()
                    .orElse(null);
            Customer customer = hotel.getCustomers().stream()
                    .filter(c -> c.getSsn().equals(booking.customerId))
                    .findFirst()
                    .orElse(null);
            booking.setRoom(room);
            booking.setCustomer(customer);
        });

        return hotel;
    }
}
