package webapi.fixture;

import application.usecase.user.account.CreateAccount;
import application.usecase.user.account.Login;
import domain.entities.user.Customer;
import domain.entities.user.Role;
import webapi.views.user.UserView;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CustomerFixtureUtil {

    public static final long CUSTOMER_CREDIT =  0L;

    public static Customer customer(int index) {
        return Customer.builder()
            .username(name(index))
            .password(password(index))
            .email(email(index))
            .address(AddressFixtureUtil.address(index))
            .role(Role.CUSTOMER)
            .credit(CUSTOMER_CREDIT)
            .build();
    }

    public static UserView view(int index) {
        return new UserView(
            name(index),
            Role.CUSTOMER.getValue(),
            email(index),
            AddressFixtureUtil.address(0),
            CUSTOMER_CREDIT
        );
    }

    public static CreateAccount.AddUserData addUserData(int index) {
        return new CreateAccount.AddUserData(
            Role.CUSTOMER.getValue(),
            name(index),
            password(index),
            email(index),
            AddressFixtureUtil.address(index)
        );
    }

    public static Login.LoginData loginData(int index) {
        Login.LoginData data = new Login.LoginData();
        data.setUsername(name(index));
        data.setPassword(password(index));
        return data;
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

    public static void assertion(Customer c1, Customer c2) {
        assertThat(c1.getUsername()).isEqualTo(c2.getUsername());
        assertThat(c1.getPassword()).isEqualTo(c2.getPassword());
        assertThat(c1.getEmail()).isEqualTo(c2.getEmail());
        assertThat(c1.getRole()).isEqualTo(c2.getRole());
        assertThat(c1.getAddress()).isEqualTo(c2.getAddress());
        assertThat(c1.getCredit()).isEqualTo(c2.getCredit());
    }
}
