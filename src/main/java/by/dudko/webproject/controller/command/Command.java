package by.dudko.webproject.controller.command;

import by.dudko.webproject.controller.Router;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.EnumSet;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
//    EnumSet<User.Role> getAccessList(); TODO implement this feature
}
