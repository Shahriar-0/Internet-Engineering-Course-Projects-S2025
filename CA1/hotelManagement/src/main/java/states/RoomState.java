package states;

import java.util.List;

public record RoomState(String room_id, int capacity, List<BookingState> bookings) {}
