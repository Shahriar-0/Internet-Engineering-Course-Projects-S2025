package domain;

import java.util.List;

public record Hotel(
    List<Customer> customers,
    List<Room> rooms,
    List<Booking> bookings
) {}
