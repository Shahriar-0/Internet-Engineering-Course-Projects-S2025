package domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record Booking(
    String id,
    String roomId,
    String customerId,
    LocalDateTime checkIn,
    LocalDateTime checkOut
) {
    public long getStayDurationInDays() {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }
}
