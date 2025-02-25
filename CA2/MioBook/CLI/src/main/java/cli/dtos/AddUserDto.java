package cli.dtos;

import domain.valueobjects.Address;

public record AddUserDto (
    String role,
    String username,
    String password,
    String email,
    Address address
) {}
