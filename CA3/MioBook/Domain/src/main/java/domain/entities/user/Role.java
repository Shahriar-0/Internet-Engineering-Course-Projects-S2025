package domain.entities.user;

import lombok.Getter;

@Getter
public enum Role {
    CUSTOMER("customer"),
    ADMIN("admin"),
    NONE("none");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public static Role getByValue(String value) {
        for (Role role : Role.values()) {
            if (role.value.equals(value))
                return role;
        }
        return NONE;
    }
}
