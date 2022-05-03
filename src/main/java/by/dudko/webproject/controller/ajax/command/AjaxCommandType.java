package by.dudko.webproject.controller.ajax.command;

import by.dudko.webproject.controller.ajax.command.impl.ChangeOrderItemQuantityCommand;
import by.dudko.webproject.controller.ajax.command.impl.RemoveOrderItemCommand;
import by.dudko.webproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public enum AjaxCommandType {
    CHANGE_ORDER_ITEM_QUANTITY(new ChangeOrderItemQuantityCommand()),
    REMOVE_ORDER_ITEM(new RemoveOrderItemCommand());

    private final ActionCommand command;

    AjaxCommandType(ActionCommand command) {
        this.command = command;
    }

    public static Optional<ActionCommand> parseCommand(String commandText) {
        ActionCommand parsedCommand;
        try {
            parsedCommand = AjaxCommandType.valueOf(commandText.toUpperCase()).command;
        } catch (IllegalArgumentException | NullPointerException e) {
            parsedCommand = null;
        }
        return Optional.ofNullable(parsedCommand);
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, CommandException {
        command.execute(request, response);
    }
}
