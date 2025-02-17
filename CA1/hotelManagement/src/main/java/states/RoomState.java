package states;

import java.util.List;

import entities.Room;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RoomState {

	private String room_id;
	private int capacity;
	private List<BookingState> bookings;
}
