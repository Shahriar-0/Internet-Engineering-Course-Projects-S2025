import dataLoader.DataLoader;
import entities.Hotel;
import java.io.IOException;

public class Application {

	private final static String dataFile = "data.json";

	public static void main(String[] args) {
		try {
			Hotel hotel = DataLoader.loadHotelData(dataFile);
			hotel.logState();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
