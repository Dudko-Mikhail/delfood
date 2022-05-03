package by.dudko.webproject.controller.command.impl;

import by.dudko.webproject.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public interface ParametrizedCommand extends Command { // TODO remove

    Set<CommandArgument> getCommandParameters();

    default Map<CommandArgument, String> parseArguments(HttpServletRequest request) {
        EnumMap<CommandArgument, String> parsedParameters = new EnumMap<>(CommandArgument.class);
        getCommandParameters().forEach(key -> {
            String parameter = request.getParameter(key.getParameterName());
            parsedParameters.put(key, parameter);
        });
        return parsedParameters;
    }

    default Map<String, String> mapKeys(Map<CommandArgument, String> parameters) {
        return parameters.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getParameterName(),
                        Map.Entry::getValue)
                );
    }
}


enum CommandArgument {
    COMMAND("command"),
    LANGUAGE("language"),
    USER_ID("id"),
    VERIFICATION_CODE("verification_code"),
    LOGIN("login"),
    PASSWORD("password"),
    REPEAT_PASSWORD("repeat_password"),
    EMAIL("email"),
    PHONE_NUMBER("phone_number"),
    FIRST_NAME("first_name"),
    LAST_NAME("last_name"),
    LOGIN_EMAIL("login_email"),
    REMEMBER_ME("remember_me"),

    CATEGORY_ID("category_id"),
    DISH_ID("dish_id"),
    AMOUNT("amount");

    private final String parameterName;

    CommandArgument(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterName() {
        return parameterName;
    }
}
