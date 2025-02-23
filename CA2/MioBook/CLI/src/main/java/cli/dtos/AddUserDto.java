package cli.dtos;

import domain.valueobject.Address;

public record AddUserDto (
    String role,
    String username,
    String password,
    String email,
    Address address
) {}
