package by.dudko.webproject.controller.command;

import java.util.Optional;

public class CommandProvider {
    public static Optional<CommandType> parseCommand(String commandName) {
        CommandType parsedCommand = null;
        try {
            String command = commandName.toUpperCase();
            parsedCommand = CommandType.valueOf(command);
        } catch (Exception ignored) {
        }
        return Optional.ofNullable(parsedCommand);
    }

    private CommandProvider() {
    }
}
