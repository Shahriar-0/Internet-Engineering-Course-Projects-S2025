package entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Booking {
    private String id;
    private Room room;
    private Customer customer;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("check_in")
    private LocalDateTime checkIn;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("check_out")
    private LocalDateTime checkOut;

    @JsonProperty("room_id")
    public String roomId;

    @JsonProperty("customer_id")
    public String customerId;

    // public Booking(String id, Room room, Customer customer, LocalDateTime checkIn, LocalDateTime checkOut) {
    //     this.id = id;
    //     this.room = room;
    //     this.customer = customer;
    //     this.checkIn = checkIn;
    //     this.checkOut = checkOut;
    //     this.roomId = room.getId();
    //     this.customerId = customer.getSsn();
    // }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }
}
