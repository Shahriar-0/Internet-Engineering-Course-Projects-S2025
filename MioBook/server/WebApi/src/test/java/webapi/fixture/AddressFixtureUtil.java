package webapi.fixture;

import domain.valueobjects.Address;

public class AddressFixtureUtil {

    public static Address address(int index) {
        return new Address(country(index), city(index));
    }

    public static String country(int index) {
        return "Country_" + index;
    }

    public static String city(int index) {
        return "City_" + index;
    }
}
