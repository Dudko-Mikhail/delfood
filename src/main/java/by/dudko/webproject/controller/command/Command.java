package by.dudko.webproject.controller.command;

import by.dudko.webproject.controller.Router;
import by.dudko.webproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
}
