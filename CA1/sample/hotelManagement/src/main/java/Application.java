import java.io.IOException;

import dataLoader.DataLoader;
import entities.Hotel;

public class Application {
    public static void main(String[] args) {
        try {
            Hotel hotel = DataLoader.loadHotelData();
            System.out.println("Rooms with minimum capacity 3: " + hotel.getRooms(3));
            System.out.println("Oldest customer: " + hotel.getOldestCustomerName());
            System.out.println("Phones in room R001: " + hotel.getCustomerPhonesByRoomNumber("R001"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
