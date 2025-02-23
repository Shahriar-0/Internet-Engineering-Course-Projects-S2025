package cli.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandType {
    ADD_USER("add_user"),
    ADD_BOOK("add_book");
    private final String command;
}
