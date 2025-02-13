package states;

import java.time.LocalDateTime;

import entities.Customer;

public class BookingState {
    private String id;
    private Customer customer;
    private String check_in;
    private String check_out;

    public BookingState(String id, Customer customer, LocalDateTime check_in, LocalDateTime check_out) {
        this.id = id;
        this.customer = customer;
        this.check_in = check_in.toString();
        this.check_out = check_out.toString();
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getCheck_in() {
        return check_in;
    }

    public String getCheck_out() {
        return check_out;
    }
}
