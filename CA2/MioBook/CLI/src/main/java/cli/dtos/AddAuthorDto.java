package cli.dtos;

public record AddAuthorDto(
        String username,
        String name,
        String penName,
        String nationality,
        String born,
        String died
) {}
