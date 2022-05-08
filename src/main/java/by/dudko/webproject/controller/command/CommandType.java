package by.dudko.webproject.controller.command;

import by.dudko.webproject.controller.Router;
import by.dudko.webproject.controller.command.impl.ChangeLocaleCommand;
import by.dudko.webproject.controller.command.impl.ConfirmRegistrationCommand;
import by.dudko.webproject.controller.command.impl.InitialCommand;
import by.dudko.webproject.controller.command.impl.RestorePasswordCommand;
import by.dudko.webproject.controller.command.impl.SignInCommand;
import by.dudko.webproject.controller.command.impl.SignOutCommand;
import by.dudko.webproject.controller.command.impl.SignUpCommand;
import by.dudko.webproject.controller.command.impl.gotopage.GoToAdminPage;
import by.dudko.webproject.controller.command.impl.gotopage.GoToCartPage;
import by.dudko.webproject.controller.command.impl.gotopage.GoToCategoriesPage;
import by.dudko.webproject.controller.command.impl.gotopage.GoToCategoryPage;
import by.dudko.webproject.controller.command.impl.gotopage.GoToHomePage;
import by.dudko.webproject.controller.command.impl.gotopage.GoToProfilePage;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Set;

import static by.dudko.webproject.model.entity.User.Role.GUEST;
import static by.dudko.webproject.model.entity.User.Role.CLIENT;
import static by.dudko.webproject.model.entity.User.Role.ADMIN;

public enum CommandType {
    GO_TO_CATEGORIES_PAGE(new GoToCategoriesPage(), GUEST, CLIENT),
    GO_TO_HOME_PAGE(new GoToHomePage(), GUEST, CLIENT),
    GO_TO_CATEGORY_PAGE(new GoToCategoryPage(), GUEST, CLIENT),
    GO_TO_PROFILE_PAGE(new GoToProfilePage(), ADMIN, CLIENT),
    GO_TO_CART_PAGE(new GoToCartPage(), GUEST, CLIENT),
    GO_TO_ADMIN_PAGE(new GoToAdminPage(), ADMIN),
    SIGN_IN(new SignInCommand(), GUEST),
    SIGN_UP(new SignUpCommand(), GUEST),
    SIGN_OUT(new SignOutCommand(), ADMIN, CLIENT),
    CHANGE_LOCALE(new ChangeLocaleCommand(), User.Role.values()),
    CONFIRM_REGISTRATION(new ConfirmRegistrationCommand(), ADMIN, CLIENT),
    RESTORE_PASSWORD(new RestorePasswordCommand(), ADMIN, CLIENT),
    INITIAL_COMMAND(new InitialCommand(), User.Role.values());

    private final Command command;
    private final Set<User.Role> roleAccessList;

    CommandType(Command command, User.Role...roleAccessList) {
        this.command = command;
        this.roleAccessList = Set.of(roleAccessList);
    }

    public Router execute(HttpServletRequest request) throws CommandException {
        return command.execute(request);
    }

    public boolean isRoutingCommand() {
        return command instanceof RoutingCommand;
    }

    public Command getCommand() {
        return command;
    }

    public Set<User.Role> getRoleAccessList() {
        return roleAccessList;
    }
}
