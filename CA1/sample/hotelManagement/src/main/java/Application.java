import java.io.IOException;

import dataLoader.DataLoader;
import entities.Hotel;

public class Application {
    public static void main(String[] args) {
        try {
            Hotel hotel = DataLoader.loadHotelData("data.json");
            hotel.logState();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
