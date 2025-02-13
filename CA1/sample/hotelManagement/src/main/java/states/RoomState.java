package states;

import java.util.List;

public class RoomState {

	private String room_id;
	private int capacity;
	private List<BookingState> bookings;

	public RoomState(String room_id, int capacity, List<BookingState> bookings) {
		this.room_id = room_id;
		this.capacity = capacity;
		this.bookings = bookings;
	}

	public String getRoom_id() {
		return room_id;
	}

	public int getCapacity() {
		return capacity;
	}

	public List<BookingState> getBookings() {
		return bookings;
	}

	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setBookings(List<BookingState> bookings) {
		this.bookings = bookings;
	}
}
