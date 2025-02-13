import dataLoader.DataLoader;
import entities.Hotel;
import java.io.IOException;

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
