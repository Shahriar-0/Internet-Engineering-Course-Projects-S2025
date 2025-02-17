package states;

import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class HotelState {

	private List<RoomState> rooms;
}
