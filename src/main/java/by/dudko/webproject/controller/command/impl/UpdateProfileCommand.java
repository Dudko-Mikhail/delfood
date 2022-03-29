package by.dudko.webproject.controller.command.impl;

import by.dudko.webproject.controller.Router;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.model.service.UserService;
import by.dudko.webproject.model.service.impl.UserServiceImpl;
import by.dudko.webproject.validator.UserValidator;
import jakarta.servlet.http.HttpServletRequest;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static by.dudko.webproject.controller.command.impl.CommandArgument.*;

public class UpdateProfileCommand implements ParametrizedCommand {
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final EnumSet<CommandArgument> commandParameters = EnumSet.of(FIRST_NAME, LAST_NAME, PHONE_NUMBER);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<CommandArgument, String> parameters = parseArguments(request);
        UserValidator.getInstance().validate(mapKeys(parameters));
        return null;

    }

    @Override
    public Set<CommandArgument> getCommandParameters() {
        return commandParameters;
    }
}
