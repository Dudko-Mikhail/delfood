package by.dudko.webproject.controller.command;

import by.dudko.webproject.controller.command.impl.ChangeLocale;
import by.dudko.webproject.controller.command.impl.SignInCommand;
import by.dudko.webproject.controller.command.impl.SignUpCommand;

import java.util.Optional;

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    SIGN_OUT(null),
    CHANGE_LOCALE(new ChangeLocale());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Optional<Command> parseCommand(String commandText) {
        Command parsedCommand;
        try {
            parsedCommand = CommandType.valueOf(commandText.toUpperCase()).command;
        } catch (IllegalArgumentException e) {
            parsedCommand = null;
        }
        return Optional.ofNullable(parsedCommand);
    }
}
