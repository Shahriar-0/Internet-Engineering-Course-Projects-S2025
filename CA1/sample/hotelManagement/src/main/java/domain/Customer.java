package domain;

public record Customer(
    String ssn,
    String name,
    String phone,
    int age
) {}
