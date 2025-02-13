package states;

import java.util.List;

public class HotelState {

	private List<RoomState> rooms;

	public HotelState(List<RoomState> rooms) {
		this.rooms = rooms;
	}

	public List<RoomState> getRooms() {
		return rooms;
	}

	public void setRooms(List<RoomState> rooms) {
		this.rooms = rooms;
	}
}
