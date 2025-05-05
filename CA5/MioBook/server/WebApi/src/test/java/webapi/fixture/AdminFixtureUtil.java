package webapi.fixture;

import application.usecase.user.account.CreateAccount;
import domain.entities.user.Admin;
import domain.entities.user.Customer;
import domain.entities.user.Role;
import webapi.views.user.UserView;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AdminFixtureUtil {

    public static Admin admin(int index) {
        return Admin.builder()
            .username(name(index))
            .password(password(index))
            .email(email(index))
            .address(AddressFixtureUtil.address(index))
            .role(Role.ADMIN)
            .build();
    }

    public static UserView view(int index) {
        return new UserView(
            name(index),
            Role.ADMIN.getValue(),
            email(index),
            AddressFixtureUtil.address(0),
            null
        );
    }

    public static CreateAccount.AddUserData addUserData(int index) {
        return new CreateAccount.AddUserData(
            Role.ADMIN.getValue(),
            name(index),
            password(index),
            email(index),
            AddressFixtureUtil.address(index)
        );
    }

    public static String name(int index) {
        return "AdminName_" + index;
    }

    public static String password(int index) {
        return "AdminPassword_" + index;
    }

    public static String email(int index) {
        return "AdminEmail_" + index + "@gmail.com";
    }

    public static void assertion(Admin a1, Admin a2) {
        assertThat(a1.getUsername()).isEqualTo(a2.getUsername());
        assertThat(a1.getPassword()).isEqualTo(a2.getPassword());
        assertThat(a1.getEmail()).isEqualTo(a2.getEmail());
        assertThat(a1.getRole()).isEqualTo(a2.getRole());
        assertThat(a1.getAddress()).isEqualTo(a2.getAddress());
    }
}
