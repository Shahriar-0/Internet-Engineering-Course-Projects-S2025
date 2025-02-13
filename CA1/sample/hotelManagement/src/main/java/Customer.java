public record Customer(String ssn, String name, String phone, int age) {
    String getName() {
        return name;
    }

    int getAge() {
        return age;
    }

    String getPhoneNumber() {
        return phone;
    }

    String getSsn() {
        return ssn;
    }
}
