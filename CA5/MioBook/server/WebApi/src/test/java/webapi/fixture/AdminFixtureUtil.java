package webapi.fixture;

import domain.entities.user.Admin;
import domain.entities.user.Role;

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

    public static String name(int index) {
        return "AdminName_" + index;
    }

    public static String password(int index) {
        return "AdminPassword_" + index;
    }

    public static String email(int index) {
        return "AdminEmail_" + index + "@gmail.com";
    }
}
