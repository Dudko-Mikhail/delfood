package by.dudko.webproject.controller.command;

import by.dudko.webproject.controller.command.impl.AddOrderItemCommand;
import by.dudko.webproject.controller.command.impl.ChangeLocaleCommand;
import by.dudko.webproject.controller.command.impl.ConfirmRegistrationCommand;
import by.dudko.webproject.controller.command.impl.RestorePasswordCommand;
import by.dudko.webproject.controller.command.impl.SignInCommand;
import by.dudko.webproject.controller.command.impl.SignOutCommand;
import by.dudko.webproject.controller.command.impl.SignUpCommand;
import by.dudko.webproject.controller.command.impl.gotopage.GoToCategoriesPage;
import by.dudko.webproject.controller.command.impl.gotopage.GoToCategoryPage;
import by.dudko.webproject.controller.command.impl.gotopage.GoToDishPage;
import by.dudko.webproject.controller.command.impl.gotopage.GoToHomePage;
import by.dudko.webproject.controller.command.impl.gotopage.GoToProfilePage;

import java.util.Optional;

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    SIGN_OUT(new SignOutCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    GO_TO_CATEGORIES_PAGE(new GoToCategoriesPage()),
    GO_TO_HOME_PAGE(new GoToHomePage()),
    GO_TO_CATEGORY_PAGE(new GoToCategoryPage()),
    GO_TO_DISH_PAGE(new GoToDishPage()),
    GO_TO_PROFILE_PAGE(new GoToProfilePage()),
    CONFIRM_REGISTRATION(new ConfirmRegistrationCommand()),
    RESTORE_PASSWORD(new RestorePasswordCommand()),
    SEND_EMAIL_VERIFICATION_MESSAGE(new SendEmailVerificationMessageCommand()),
    ADD_ORDER_ITEM(new AddOrderItemCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Optional<Command> parseCommand(String commandText) {
        Command parsedCommand;
        try {
            parsedCommand = CommandType.valueOf(commandText.toUpperCase()).command;
        } catch (IllegalArgumentException | NullPointerException e) {
            parsedCommand = null;
        }
        return Optional.ofNullable(parsedCommand);
    }

    public Command getCommand() {
        return command;
    }
}
