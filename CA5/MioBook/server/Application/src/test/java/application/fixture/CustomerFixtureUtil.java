package application.fixture;

import domain.entities.user.Customer;
import domain.entities.user.Role;

public class CustomerFixtureUtil {

    public static Customer customer(int index) {
        return Customer.builder()
            .id((long) index)
            .username(name(index))
            .password(password(index))
            .email(email(index))
            .address(AddressFixtureUtil.address(index))
            .role(Role.CUSTOMER)
            .credit(100000L)
            .build();
    }

    public static String name(int index) {
        return "CustomerName_" + index;
    }

    public static String password(int index) {
        return "CustomerPassword_" + index;
    }

    public static String email(int index) {
        return "CustomerEmail_" + index + "@gmail.com";
    }
}
